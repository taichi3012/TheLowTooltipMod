package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import java.util.List;

import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponGekokujo;

public class WeaponSkill141 extends WeaponSkillUniqueBase {

    public WeaponSkill141() {
        super("wskill141", "下剋上", "36");
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 0.0d;
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
        return new WeaponGekokujo(weaponData).generateResultContext();
    }
}
