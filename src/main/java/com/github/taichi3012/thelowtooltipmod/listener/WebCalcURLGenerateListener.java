package com.github.taichi3012.thelowtooltipmod.listener;

import com.github.taichi3012.thelowtooltipmod.api.TheLowDamageCalculationURLBuilder;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static com.github.taichi3012.thelowtooltipmod.TheLowTooltipMod.MOD_NAME;
import static com.github.taichi3012.thelowtooltipmod.TheLowTooltipMod.keyCopyWebCalculationURL;

public class WebCalcURLGenerateListener {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!keyCopyWebCalculationURL.isPressed() || !TheLowTooltipModConfig.isURLGenerateEnable()) {
            return;
        }

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }

        execution(player.getCurrentEquippedItem());
    }

    @SubscribeEvent
    public void onGuiKeyInput(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        if (!TheLowTooltipModConfig.isURLGenerateEnable() || Keyboard.getEventKey() != keyCopyWebCalculationURL.getKeyCode() || Keyboard.isRepeatEvent() || !Keyboard.getEventKeyState()) {
            return;
        }

        if (!(event.gui instanceof GuiContainer)) {
            return;
        }

        GuiContainer gui = (GuiContainer) event.gui;
        Slot slot = gui.getSlotUnderMouse();

        if (slot == null) {
            return;
        }

        execution(gui.inventorySlots.getInventory().get(slot.slotNumber));
    }

    private void execution(ItemStack stack) {
        if (stack == null || !TheLowNBTUtil.hasDamage(stack)) {
            return;
        }

        GuiNewChat chatGui = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        WeaponData weaponData = new WeaponData(stack);
        String url = TheLowDamageCalculationURLBuilder.getInstance(weaponData, 1).getAppliedParamShortUrl();

        //クリップボードにコピーする設定が有効な場合のみコピー
        if (TheLowTooltipModConfig.isURLCopyEnable()) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(url);
            clipboard.setContents(selection, null);

            chatGui.printChatMessage(
                    new ChatComponentText(String.format("§a[%1$s]§7クリップボードにURLをコピーしました。", MOD_NAME))
            );
        }

        //コンポーネントを表示しない場合はここで返す
        if (!TheLowTooltipModConfig.isIsURLComponentPrintEnable()) {
            return;
        }

        IChatComponent showItemComp = new ChatComponentText("[Show Item]").setChatStyle(
                new ChatStyle()
                        .setColor(EnumChatFormatting.GREEN)
                        .setBold(true)
                        .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(stack.writeToNBT(new NBTTagCompound()).toString())))
        );

        IChatComponent suggestUrlComp = new ChatComponentText(" [Put URL]").setChatStyle(
                new ChatStyle()
                        .setColor(EnumChatFormatting.YELLOW)
                        .setBold(true)
                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, url))
                        .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to put URL in the text field.")))
        );

        IChatComponent openUrlComp = new ChatComponentText(" [Open URL]").setChatStyle(
                new ChatStyle()
                        .setColor(EnumChatFormatting.BLUE)
                        .setBold(true)
                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://" + url))
                        .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to open URL.")))
        );

        chatGui.printChatMessage(
                new ChatComponentText("§3Components : ")
                        .appendSibling(showItemComp)
                        .appendSibling(suggestUrlComp)
                        .appendSibling(openUrlComp)
        );
    }
}