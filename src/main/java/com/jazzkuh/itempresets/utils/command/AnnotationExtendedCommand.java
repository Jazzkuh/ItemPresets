package com.jazzkuh.itempresets.utils.command;

import com.jazzkuh.itempresets.utils.ChatUtils;
import com.jazzkuh.itempresets.utils.command.annotations.Completion;
import com.jazzkuh.itempresets.utils.command.resolvers.CompletionResolver;
import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public abstract class AnnotationExtendedCommand {
    protected final AnnotationCommand baseCommand;
    protected final Method method;

    public AnnotationExtendedCommand(AnnotationCommand baseCommand, Method method) {
        this.baseCommand = baseCommand;
        this.method = method;
    }

    public boolean execute(CommandSender sender, String[] args, boolean isMainCommand) {
        List<Parameter> parameters = Arrays.stream(method.getParameters()).toList();
        Object[] resolvedParameters = new Object[parameters.size()];
        resolvedParameters[0] = sender;

        int size = parameters.stream().filter(parameter -> !parameter.isAnnotationPresent(com.jazzkuh.itempresets.utils.command.annotations.Optional.class)).toList().size();
        int paramSize = isMainCommand ? size - 1 : size;
        if (args.length < paramSize) return false;
        if (!method.getParameterTypes()[0].isInstance(sender)) {
            sender.sendMessage("This command isn't available to you.");
            return true;
        }

        for (int i = 1; i < parameters.size(); i++) {
            Parameter parameter = parameters.get(i);
            Class<?> paramClass = parameter.getType();
            ContextResolver<?> contextResolver = ContextResolverRegistry.getResolver(paramClass);
            if (contextResolver == null) {
                ChatUtils.sendMessage(sender, "<error>There is no context resolver for " + paramClass.getSimpleName());
                return true;
            }

            if (args.length <= (isMainCommand ? i - 1 : i) && parameter.isAnnotationPresent(com.jazzkuh.itempresets.utils.command.annotations.Optional.class)) {
                resolvedParameters[i] = null;
            } else {
                if (paramClass.isArray() && paramClass.getComponentType() == String.class) {
                    String[] array = Arrays.copyOfRange(args, isMainCommand ? i - 1 : i, args.length);
                    resolvedParameters[i] = array;
                    continue;
                }

                Object resolvedObject = contextResolver.resolve(sender, isMainCommand ? args[i - 1] : args[i]);
                if (resolvedObject == null) {
                    ChatUtils.sendMessage(sender, "<error>Could not resolve parameter " + parameter.getName() + " of type " + paramClass.getSimpleName() + ".");
                    return true;
                }

                resolvedParameters[i] = resolvedObject;
            }
        }

        try {
            method.setAccessible(true);
            method.invoke(this.baseCommand, resolvedParameters);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            ChatUtils.sendMessage(sender, "<error>Something went wrong while executing this subcommand: " + exception.getMessage());
            return true;
        }
    }

    public List<String> tabComplete(CommandSender sender, String[] args, boolean isMainCommand) {
        List<Parameter> parameters = Arrays.stream(method.getParameters()).toList();
        List<String> options = new ArrayList<>(Collections.emptyList());

        int paramSize = isMainCommand ? parameters.size() - 1 : parameters.size();
        if (args.length > paramSize) return options;
        int index = args.length < 1 ? 0 : args.length - 1;

        Parameter parameter = parameters.get(isMainCommand ? index + 1 : index);
        Class<?> paramClass = parameter.getType();
        String arg = args[index];

        if (parameter.isAnnotationPresent(Completion.class)) {
            Completion completion = parameter.getAnnotation(Completion.class);
            CompletionResolver resolver = CompletionResolverRegistry.getCompletion(completion.value());
            if (resolver != null) {
                return getApplicableTabCompleters(arg, resolver.resolve(sender, arg));
            }
        }

        CompletionResolver completionResolver = CompletionResolverRegistry.getResolver(paramClass);
        if (completionResolver != null) {
            return getApplicableTabCompleters(arg, completionResolver.resolve(sender, arg));
        }

        return options;
    }

    protected List<String> getApplicableTabCompleters(String arg, Collection<String> completions) {
        return StringUtil.copyPartialMatches(arg, completions, new ArrayList<>(completions.size()));
    }
}
