package com.github.taichi3012.thelowtooltipmod.listener;

import com.github.taichi3012.thelowtooltipmod.gui.InventoryParkSelector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiParkSelectorListener {

    /**
     * 開いたGuiがパーク選択画面だった場合にInventoryを入れ替える。
     */
    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event) {
        if (!(event.gui instanceof GuiChest)) {
            return;
        }

        GuiChest gui = (GuiChest) event.gui;

        if (!(gui.inventorySlots instanceof ContainerChest)) {
            return;
        }

        ContainerChest container = (ContainerChest) gui.inventorySlots;

        if (!(container.getLowerChestInventory() instanceof InventoryBasic)) {
            return;
        }

        InventoryBasic inv = (InventoryBasic) container.getLowerChestInventory();

        if (!inv.getDisplayName().getUnformattedText().contains("Perk Selector")) {
            return;
        }

        gui.inventorySlots = new ContainerChest(
                Minecraft.getMinecraft().thePlayer.inventory,
                new InventoryParkSelector(inv),
                Minecraft.getMinecraft().thePlayer
        );
    }
}

