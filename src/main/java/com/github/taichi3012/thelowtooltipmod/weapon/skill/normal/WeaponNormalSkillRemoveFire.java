package com.github.taichi3012.thelowtooltipmod.weapon.skill.normal;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;

import java.util.ArrayList;
import java.util.List;

public class WeaponNormalSkillRemoveFire extends WeaponNormalSkillBase {

    public WeaponNormalSkillRemoveFire() {
        super("n_skill_r_fire", "天使の囁き");
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
        return new ArrayList<>();
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 10.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }
}
