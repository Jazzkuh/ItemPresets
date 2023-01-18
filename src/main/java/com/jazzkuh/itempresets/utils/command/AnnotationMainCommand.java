package com.jazzkuh.itempresets.utils.command;

import com.jazzkuh.itempresets.utils.command.annotations.Description;
import com.jazzkuh.itempresets.utils.command.annotations.Permission;
import com.jazzkuh.itempresets.utils.command.annotations.Usage;
import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public final class AnnotationMainCommand extends AnnotationExtendedCommand {
    private @Getter String usage = "";
    private @Getter String description = "No description provided";
    private @Getter String permission = null;
    private final @Getter CommandArgument commandArgument;

    public AnnotationMainCommand(AnnotationCommand baseCommand, Method method) {
        super(baseCommand, method);

        if (method.isAnnotationPresent(Description.class)) description = method.getAnnotation(Description.class).value();
        if (method.isAnnotationPresent(Permission.class)) permission = method.getAnnotation(Permission.class).value();
        if (method.isAnnotationPresent(Usage.class)) usage = method.getAnnotation(Usage.class).value();

        List<Parameter> parameters = Arrays.stream(method.getParameters()).toList();
        if (!method.isAnnotationPresent(Usage.class)) {
            StringBuilder paramUsage = new StringBuilder();
            for (int i = 1; i < parameters.size(); i++) {
                Parameter parameter = parameters.get(i);
                paramUsage.append("<").append(parameter.getName());
                if (parameter.getType().isArray()) {
                    paramUsage.append("...");
                }
                paramUsage.append(">");

                if (i != parameters.size() - 1) {
                    paramUsage.append(" ");
                }
            }

            this.usage = paramUsage.toString();
        }

        String commandUsage = usage.length() > 0 ? " " + usage : "";
        if (permission != null) {
            this.commandArgument = new CommandArgument(commandUsage, description, permission);
        } else {
            this.commandArgument = new CommandArgument(commandUsage, description);
        }
    }
}
