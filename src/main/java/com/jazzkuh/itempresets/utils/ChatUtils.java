package com.jazzkuh.itempresets.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ChatUtils {
    public static Component fromLegacy(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public static Component color(String message) {
        MiniMessage extendedInstance = MiniMessage.builder()
                .editTags(tags -> {
                    tags.resolver(TagResolver.resolver("primary", Tag.styling(TextColor.fromHexString("#27FB6B"))));
                    tags.resolver(TagResolver.resolver("secondary", Tag.styling(TextColor.fromHexString("#14CC60"))));
                    tags.resolver(TagResolver.resolver("wwmc_red", Tag.styling(TextColor.fromHexString("#CC0001"))));
                    tags.resolver(TagResolver.resolver("error", Tag.styling(TextColor.fromHexString("#f83737"))));
                })
                .build();
        
        return extendedInstance.deserialize(message);
    }

    public static void sendMessage(CommandSender sender, String input) {
        sender.sendMessage(color(input));
    }

    public static void sendBroadcast(String input) {
        Bukkit.broadcast(color(input));
    }
}
