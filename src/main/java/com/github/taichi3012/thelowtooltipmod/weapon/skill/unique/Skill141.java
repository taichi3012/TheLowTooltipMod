package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponGekokujo;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Skill141 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 0.0d;
    }

    @Override
    public String getId() {
        return "wskill141";
    }

    @Override
    public String getSkillSetId() {
        return "36";
    }

    @Override
    public String getName(WeaponData weaponData) {
        return isActive(weaponData) ? "下剋上" : "";
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return getSkillSetId().equals(weaponData.getSkillSetId());
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        return new WeaponGekokujo(weaponData).generateResultContext();
    }
}
