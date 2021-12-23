package com.github.taichi3012.thelowtooltipmod;

import com.github.taichi3012.thelowtooltipmod.api.TheLowAPI;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.gui.GuiListener;
import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.*;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.SkillManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.github.taichi3012.thelowtooltipmod.api.TheLowAPI.requestPlayerStatus;

@Mod(modid = TheLowTooltipMod.MOD_ID, version = TheLowTooltipMod.VERSION, name = TheLowTooltipMod.MOD_NAME, guiFactory = "com.github.taichi3012.thelowtooltipmod.gui.TheLowTooltipModGuiFactory")
public class TheLowTooltipMod {
    public static final String MOD_ID = "thelowtooltipmod";
    public static final String VERSION = "1.1.1";
    public static final String MOD_NAME = "TheLowTooltipMod";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TheLowTooltipModConfig.loadConfig(event);
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new GuiListener());

        SkillManager.registerAll();
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        try {
            if (TheLowAPI.processResponse(event))
                event.setCanceled(true);
        } catch (RuntimeException e) {
            logger.warn("APIの解析に失敗しました。");
            e.printStackTrace();
        }

        if (event.message.getUnformattedText().matches("職業「.*」を選択しました。"))
            TheLowAPI.requestPlayerStatus(true);
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(TheLowTooltipMod.MOD_ID))
            TheLowTooltipModConfig.syncConfig();
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (TheLowTooltipModConfig.isTooltipEnable() && GuiScreen.isCtrlKeyDown()) {
            addResult(event);
        }
    }

    private void addResult(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;

        if (stack.getTagCompound() == null)
            return;

        if (TheLowNBTUtil.hasDamage(event.itemStack)) {
            WeaponBasic weapon = null;
            requestPlayerStatus(false);

            String skillSetId = TheLowNBTUtil.getSkillSetID(stack);
            if (TheLowUtil.isMagicMixtureWeapon(event.itemStack)) {
                weapon = new WeaponMagicalMixture(stack);
            } else if (skillSetId != null) {
                switch (skillSetId) {
                    case "12":
                        weapon = new WeaponPlaceLight(stack);
                        break;
                    case "36":
                        weapon = new WeaponGekokujo(stack);
                        break;
                }
            }

            if (weapon == null)
                weapon = new WeaponBasic(stack);

            //アイテムの名前と結果のみ表示する設定が有効なときはここで返す。
            if (TheLowTooltipModConfig.isOnlyDisplayResultAndNameEnable()) {
                String itemDisplayName = event.toolTip.get(0);
                event.toolTip.clear();
                event.toolTip.add(itemDisplayName);
                event.toolTip.addAll(weapon.generateResultContext());
                return;
            }

            int cursor = event.showAdvancedItemTooltips ? event.toolTip.size() - 2 : event.toolTip.size();
            for (String str : event.toolTip) {
                if (str.contains("[SLOT]")) {
                    cursor = event.toolTip.indexOf(str);
                    break;
                }
            }

            event.toolTip.addAll(cursor, weapon.generateResultContext());
            cursor += weapon.generateResultContext().size();
            event.toolTip.add(cursor, "");

            return;
        }

        if (stack.getTagCompound().hasKey("view_weapon_skill_id")) {
            IWeaponSkillAble skill = SkillManager.getSkill(stack.getTagCompound().getString("view_weapon_skill_id"));
            ItemStack heldStack = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
            requestPlayerStatus(false);

            if (skill != null && heldStack != null && TheLowNBTUtil.hasDamage(heldStack) && skill.isActive(new WeaponData(heldStack))) {
                List<String> result = new ArrayList<>();
                WeaponData weaponData = new WeaponData(heldStack);

                List<String> resultContext = skill.getResultContext(weaponData);
                if (TheLowTooltipModConfig.isSkillResultContextEnable() && resultContext != null)
                    result.addAll(skill.getResultContext(weaponData));

                List<String> coolTimeContext = skill.getCoolTimeContext(weaponData);
                if (TheLowTooltipModConfig.isSkillCoolTimeContextEnable() && coolTimeContext != null) {
                    if (result.size() > 0)
                        result.add("");
                    result.addAll(coolTimeContext);
                }

                //結果がないときは返す。
                if (result.size() == 0)
                    return;

                //アイテムの名前と結果のみ表示する設定が有効なときはここで返す。
                if (TheLowTooltipModConfig.isOnlyDisplayResultAndNameEnable()) {
                    String itemDisplayName = event.toolTip.get(0);
                    event.toolTip.clear();
                    event.toolTip.add(itemDisplayName);
                    event.toolTip.addAll(result);
                    return;
                }

                int cursor = event.showAdvancedItemTooltips ? event.toolTip.size() - 2 : event.toolTip.size();
                for (String str : event.toolTip) {
                    if (str.contains("スキル発動中") || str.contains("クリックしてスキルを")) {
                        cursor = event.toolTip.indexOf(str);
                        break;
                    }
                }

                event.toolTip.addAll(cursor, result);
                cursor += result.size();
                event.toolTip.add(cursor, "");
            }
        }
    }
}