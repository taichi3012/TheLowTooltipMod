package com.github.taichi3012.thelowtooltipmod.gui;

import com.github.taichi3012.thelowtooltipmod.TheLowTooltipMod;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

public class UpdatePark {
    @SubscribeEvent
    public void onGuiScreen(GuiScreenEvent event) {
        if (!(event.gui instanceof GuiChest)) return;
        GuiChest gui = (GuiChest) event.gui;

        if (!(gui.inventorySlots instanceof ContainerChest)) return;
        ContainerChest container = (ContainerChest) gui.inventorySlots;
        IInventory inv = container.getLowerChestInventory();

        if (!inv.getDisplayName().getUnformattedText().contains("Perk Selector")) return;
        List<ItemStack> stack = container.getInventory();

        for (WeaponType type : WeaponType.values()) {
            OptionalDouble op = stack.stream()
                    .filter(Objects::nonNull)
                    .filter(item -> item.getDisplayName().contains("Over Strength of " + type.name()))
                    .mapToDouble(TheLowUtil::getParkGainByItem)
                    .max();

            if (op.isPresent() && op.getAsDouble() != 0d && op.getAsDouble() != type.getOverStrength()) {
                double gain = op.getAsDouble();
                TheLowTooltipModConfig.setOverStrengthValue(type, gain);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(String.format("§a[%s]§7攻撃力増加パーク(%s)の値を更新しました。Value=%s", TheLowTooltipMod.MOD_NAME, type, gain)));
            }
        }
    }
}

