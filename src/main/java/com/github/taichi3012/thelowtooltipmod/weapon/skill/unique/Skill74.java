package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Skill74 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        double activeTime = "45".equals(weaponData.getSkillSetId()) ? 5.0d : 0.0d;
        return 60.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + activeTime;
    }

    @Override
    public String getId() {
        return "wskill74";
    }

    @Override
    public String getSkillSetId() {
        return "19,45";
    }

    @Override
    public String getName(WeaponData weaponData) {
        if (weaponData.getSkillSetId() == null) {
            return "";
        }

        switch (weaponData.getSkillSetId()) {
            case "19":
                return "血の渇望";
            case "45":
                return "魔力吸収";
            default:
                return "";
        }
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return weaponData.getSkillSetId() != null && getSkillSetId().contains(weaponData.getSkillSetId());
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        return new ArrayList<>();
    }
}
