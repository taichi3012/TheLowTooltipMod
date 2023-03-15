package com.github.taichi3012.thelowtooltipmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import com.github.taichi3012.thelowtooltipmod.TheLowTooltipMod;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;

public class InventoryParkSelector extends InventoryBasic {
    public InventoryParkSelector(InventoryBasic inventoryBasic) {
        super(inventoryBasic.getDisplayName().getUnformattedText(), inventoryBasic.hasCustomName(), inventoryBasic.getSizeInventory());
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (stack != null) {
            if (stack.getDisplayName().contains("Over Strength of ")) {
                for (WeaponType type : WeaponType.values()) {
                    if (!stack.getDisplayName().contains(type.name())) {
                        continue;
                    }

                    double gain = TheLowUtil.generateOSParkGain(stack);
                    if (gain != 0d && gain != TheLowTooltipModConfig.getOSParkGainByWeaponType(type)) {
                        TheLowTooltipModConfig.setOverStrengthValue(type, gain);
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(
                                String.format("§a[%1$s]§7攻撃力増加パーク(%2$s)の値を更新しました。Value=%3$s", TheLowTooltipMod.MOD_NAME, type, gain))
                        );
                        break;
                    }
                }
            } else if (stack.getDisplayName().contains("Quick Talk Spell")) {
                int stage = TheLowUtil.generateParkStage(stack);
                if (stage != TheLowTooltipModConfig.getQuickTalkSpellStage()) {
                    TheLowTooltipModConfig.setQuickTalkSpellStage(TheLowUtil.generateParkStage(stack));
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(
                            String.format("§a[%1$s]§7CT減少パークの値を更新しました。Value=%2$s", TheLowTooltipMod.MOD_NAME, stage))
                    );
                }
            }
        }

        super.setInventorySlotContents(index, stack);
    }
}
