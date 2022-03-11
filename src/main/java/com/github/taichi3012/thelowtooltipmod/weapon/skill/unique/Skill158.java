package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Skill158 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        double ct = TheLowUtil.getPlayerJobType().equals(JobType.BUTTERFLY_SEEKER) ? 105.0d : 120.0d;
        return ct * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + 10.0d;
    }

    @Override
    public String getId() {
        return "wskill158";
    }

    @Override
    public String getSkillSetId() {
        return "40";
    }

    @Override
    public String getName(WeaponData weaponData) {
        return isActive(weaponData) ? "百花繚乱" : "";
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return getSkillSetId().equals(weaponData.getSkillSetId());
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        return new ArrayList<>();
    }
}
