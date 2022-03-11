package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Skill48 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 70.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }

    @Override
    public String getId() {
        return "wskill48";
    }

    @Override
    public String getSkillSetId() {
        return "12";
    }

    @Override
    public String getName(WeaponData weaponData) {
        return isActive(weaponData) ? "神の鉄槌" : "";
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return getSkillSetId().equals(weaponData.getSkillSetId());
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        double normalDamage = new WeaponBasic(weaponData).generateCategorizedDamage(false).get(ResultCategoryType.NO_SPECIAL_CATEGORY) * 3.0d;
        double passiveAbleDamage = normalDamage * 1.2d;
        JobType job = TheLowUtil.getPlayerJobType();

        result.add("§4[ダメージ]");

        if (!(job.equals(JobType.SKULL_PIERCER) || job.equals(JobType.DARK_BLASTER))) {
            result.add(String.format(StringUtils.repeat(' ', 2) + "§7通常:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
            result.add(String.format(StringUtils.repeat(' ', 2) + "§7パッシブ有効:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(passiveAbleDamage), DamageCalcUtil.roundCriticalDamage(passiveAbleDamage)));
            return result;
        }

        List<ResultCategoryType> showCategory = new ArrayList<>();
        showCategory.add(ResultCategoryType.NO_SPECIAL_CATEGORY);
        showCategory.add(job.equals(JobType.SKULL_PIERCER) ? ResultCategoryType.ZOMBIE_CATEGORY : ResultCategoryType.SKELETON_CATEGORY);

        showCategory.forEach(type -> {
            double mul = type.equals(ResultCategoryType.NO_SPECIAL_CATEGORY) ? 1.0d : 0.9d;
            result.add(StringUtils.repeat(' ', 2) + type.getDisplayColor() + type.getName() + ":");
            result.add(String.format(
                    StringUtils.repeat(' ', 4) + "§7通常:§6+%1$s§c§o(+%2$s)",
                    DamageCalcUtil.roundDamage(normalDamage * mul),
                    DamageCalcUtil.roundCriticalDamage(normalDamage * mul)
            ));
            result.add(String.format(
                    StringUtils.repeat(' ', 4) + "§7パッシブ有効:§6+%1$s§c§o(+%2$s)",
                    DamageCalcUtil.roundDamage(passiveAbleDamage * mul),
                    DamageCalcUtil.roundCriticalDamage(passiveAbleDamage * mul)
            ));
        });
        return result;
    }
}
