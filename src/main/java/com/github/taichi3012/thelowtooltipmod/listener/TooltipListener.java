package com.github.taichi3012.thelowtooltipmod.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.taichi3012.thelowtooltipmod.api.TheLowAPI;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.util.ItemNBTUtil;
import com.github.taichi3012.thelowtooltipmod.util.MCPlayerUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.*;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.SkillManager;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.WeaponSkillBase;

/**
 * @author taichi1230
 */
public class TooltipListener {

    private static final String SKILL_SELECTOR_SKILL_ID_KEY = "view_weapon_skill_id";

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (TheLowTooltipModConfig.isTooltipEnable() && GuiScreen.isCtrlKeyDown()) {
            addResult(event);
        }
    }

    private void addResult(ItemTooltipEvent event) {
        final ItemStack stack = event.itemStack;

        if (stack.getTagCompound() == null) {
            return;
        }

        final List<String> toolTip = event.toolTip;
        List<String> context = new ArrayList<>();
        int cursor = toolTip.size() - (event.showAdvancedItemTooltips ? 2 : 0);

        if (TheLowNBTUtil.hasDamage(event.itemStack)) {
            AbstractWeapon weapon = new WeaponBasic(stack);

            if (TheLowUtil.isMagicMixtureWeapon(event.itemStack)) {
                weapon = new WeaponMagicalMixture(stack);
            }

            switch (TheLowNBTUtil.getSkillSetID(stack)) {
                case "12":
                    weapon = new WeaponPlaceLight(stack);
                    break;
                case "29":
                    weapon = new WeaponAmrudad(stack);
                    break;
                case "36":
                    weapon = new WeaponGekokujo(stack);
                    break;
            }

            context = weapon.generateResultContext();
            cursor = toolTip.stream()
                    .filter(str -> str.contains("[SLOT]"))
                    .mapToInt(toolTip::indexOf)
                    .findFirst()
                    .orElse(cursor);

        } else if (this.isSkillSelector(stack)) {
            Optional<ItemStack> opt = MCPlayerUtil.getPlayerHeldStack();

            if (!opt.isPresent()) {
                return;
            }

            ItemStack heldStack = opt.get();

            if (!TheLowNBTUtil.hasDamage(heldStack)) {
                return;
            }

            WeaponData weaponData = new WeaponData(heldStack);
            WeaponSkillBase skill = SkillManager.getSkill(weaponData, ItemNBTUtil.getStringTag(stack, SKILL_SELECTOR_SKILL_ID_KEY));

            if (skill == null || !skill.isActive(weaponData)) {
                return;
            }

            //スキルダメージ表記など
            if (TheLowTooltipModConfig.isSkillResultContextEnable()) {
                context.addAll(skill.getResultContext(weaponData));
            }

            //スキルクールタイム表記
            if (TheLowTooltipModConfig.isSkillCoolTimeContextEnable()) {
                //仕切りのために改行する
                if (!context.isEmpty()) {
                    context.add("");
                }
                context.addAll(skill.getCoolTimeContext(weaponData));
            }

            cursor = toolTip.stream()
                    .filter(str -> str.contains("スキル発動中") || str.contains("クリックしてスキルを"))
                    .mapToInt(toolTip::indexOf)
                    .findFirst()
                    .orElse(cursor);
        } else {
            return;
        }

        TheLowAPI.requestPlayerStatus(false);

        context.add("");
        toolTip.addAll(cursor, context);
    }

    private boolean isSkillSelector(ItemStack stack) {
        return stack.getTagCompound().hasKey(SKILL_SELECTOR_SKILL_ID_KEY);
    }

}
