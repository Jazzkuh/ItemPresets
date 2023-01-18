package com.jazzkuh.itempresets.common.commands;

import com.jazzkuh.itempresets.ItemPresets;
import com.jazzkuh.itempresets.common.framework.ConfiguredPreset;
import com.jazzkuh.itempresets.common.framework.PresetEditType;
import com.jazzkuh.itempresets.utils.ChatUtils;
import com.jazzkuh.itempresets.utils.ItemBuilder;
import com.jazzkuh.itempresets.utils.command.AnnotationCommand;
import com.jazzkuh.itempresets.utils.command.annotations.*;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PresetCommand extends AnnotationCommand {
    public PresetCommand() {
        super("preset");
    }

    @Main
    @Permission("itempresets.commands.preset")
    public void main(CommandSender sender) {
        sendNotEnoughArguments(sender);
    }

    @Subcommand("create")
    @Description("Create a new item preset.")
    @Usage("<identifier>")
    @Permission("itempresets.commands.preset.create")
    public void onCreate(Player player, String identifier) {
        if (ItemPresets.getInstance().findPreset(identifier).isPresent()) {
            ChatUtils.sendMessage(player, "<error>An item preset with this identifier already exists.");
            return;
        }
        
        ItemPresets.getInstance().createPreset(identifier);
        ChatUtils.sendMessage(player, "<primary>Successfully created an item preset with the identifier <secondary>" + identifier + "<primary>.");
        ChatUtils.sendMessage(player, "<primary>Now use <secondary>/preset edit <identifier> <type> <value> <primary>to edit the preset.");
    }
    
    @Subcommand("delete")
    @Description("Delete an item preset.")
    @Usage("<identifier>")
    @Permission("itempresets.commands.preset.delete")
    public void onDelete(Player player, @Completion("presets") String identifier) {
        if (ItemPresets.getInstance().findPreset(identifier).isEmpty()) {
            ChatUtils.sendMessage(player, "<error>An item preset with this identifier does not exist.");
            return;
        }
        
        ItemPresets.getInstance().deletePreset(identifier);
        ChatUtils.sendMessage(player, "<primary>Successfully deleted an item preset with the identifier <secondary>" + identifier + "<primary>.");
    }

    @Subcommand("edit")
    @Description("Edit an item preset.")
    @Usage("<identifier> <type> <value>")
    @Permission("itempresets.commands.preset.edit")
    public void onEdit(Player player, @Completion("presets") String identifier, PresetEditType presetEditType, String[] value) {
        if (ItemPresets.getInstance().findPreset(identifier).isEmpty()) {
            ChatUtils.sendMessage(player, "<error>An item preset with this identifier does not exist.");
            return;
        }

        PropertyEditor editor = PropertyEditorManager.findEditor(presetEditType.getTypeClass());
        editor.setAsText(String.join(" ", value));
        Object objectValue = editor.getValue();

        try {
            ItemPresets.getInstance().editPreset(identifier, presetEditType, objectValue);
            ChatUtils.sendMessage(player, "<primary>Successfully edited an item preset with the identifier <secondary>" + identifier + "<primary>.");
        } catch (Exception e) {
            ChatUtils.sendMessage(player, "<error>An error occurred while editing the item preset.");
        }
    }

    @Subcommand("load")
    @Description("Load an item preset on an item.")
    @Usage("<identifier>")
    @Permission("itempresets.commands.preset.load")
    public void onLoad(Player player, @Completion("presets") String identifier) {
        if (ItemPresets.getInstance().findPreset(identifier).isEmpty()) {
            ChatUtils.sendMessage(player, "<error>An item preset with this identifier does not exist.");
            return;
        }

        if (player.getInventory().getItemInMainHand().getType().isAir()) {
            ChatUtils.sendMessage(player, "<error>You must be holding an item in your main hand.");
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ConfiguredPreset itemPreset = ItemPresets.getInstance().findPreset(identifier).get();
        ItemBuilder itemBuilder = new ItemBuilder(itemStack).makeUnbreakable(itemPreset.isUnbreakable());

        if (itemPreset.getName() != null) itemBuilder.setName(itemPreset.getName());
        if (itemPreset.getLore() != null) {
            List<String> lore = itemPreset.getLore().contains("||") ? Arrays.asList(itemPreset.getLore().split("\\|\\|")) : Collections.singletonList(itemPreset.getLore());
            itemBuilder.setLore(lore.stream().map(line -> ChatUtils.color(line).decoration(TextDecoration.ITALIC, false)).toList());
        }

        player.getInventory().setItemInMainHand(itemBuilder.toItemStack());
        ChatUtils.sendMessage(player, "<primary>Successfully loaded an item preset with the identifier <secondary>" + identifier + "<primary>.");
    }
}