package com.github.taichi3012.thelowtooltipmod.weapon.skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.taichi3012.thelowtooltipmod.util.JavaUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;

public abstract class WeaponSkillBase {

    public final String id;
    public final String name;

    protected WeaponSkillBase(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract boolean isActive(WeaponData weaponData);

    public abstract List<String> getResultContext(WeaponData weaponData);

    public List<String> getCoolTimeContext(WeaponData weaponData) {
        double ct = getCoolTime(weaponData);

        if (ct <= 0) {
            return new ArrayList<>();
        }

        return Collections.singletonList(String.format("§3[クールタイム]§e -> %1$s秒", JavaUtil.digitRound(ct, 2.0d)));
    }

    public abstract double getCoolTime(WeaponData weaponData);
}
