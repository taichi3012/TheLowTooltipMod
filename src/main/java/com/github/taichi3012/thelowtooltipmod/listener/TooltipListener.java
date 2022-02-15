package com.github.taichi3012.thelowtooltipmod.listener;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.*;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.SkillManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static com.github.taichi3012.thelowtooltipmod.api.TheLowAPI.requestPlayerStatus;

/**
 * @author taichi1230
 */
public class TooltipListener {
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (TheLowTooltipModConfig.isTooltipEnable() && GuiScreen.isCtrlKeyDown()) {
            addResult(event);
        }
    }

    private void addResult(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;

        if (stack.getTagCompound() == null) {
            return;
        }

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

            if (weapon == null) {
                weapon = new WeaponBasic(stack);
            }

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
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player == null) {
                return;
            }

            IWeaponSkillAble skill = SkillManager.getSkill(stack.getTagCompound().getString("view_weapon_skill_id"));
            ItemStack heldStack = player.getCurrentEquippedItem();
            requestPlayerStatus(false);

            if (skill != null && heldStack != null && TheLowNBTUtil.hasDamage(heldStack) && skill.isActive(new WeaponData(heldStack))) {
                List<String> result = new ArrayList<>();
                WeaponData weaponData = new WeaponData(heldStack);

                List<String> resultContext = skill.getResultContext(weaponData);
                if (TheLowTooltipModConfig.isSkillResultContextEnable() && !resultContext.isEmpty()) {
                    result.addAll(skill.getResultContext(weaponData));
                }

                List<String> coolTimeContext = skill.getCoolTimeContext(weaponData);
                if (TheLowTooltipModConfig.isSkillCoolTimeContextEnable() && !coolTimeContext.isEmpty()) {
                    if (!result.isEmpty()) {
                        result.add("");
                    }
                    result.addAll(coolTimeContext);
                }

                //結果がないときは返す。
                if (result.isEmpty()) {
                    return;
                }

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
