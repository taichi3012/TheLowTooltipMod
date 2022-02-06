package com.github.taichi3012.thelowtooltipmod.weapon.skill;

import com.github.taichi3012.thelowtooltipmod.util.JavaUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface IWeaponSkillAble {
    String getId();

    String getSkillSetId();

    String getName(WeaponData weaponData);

    boolean isActive(WeaponData weaponData);

    @NotNull List<String> getResultContext(WeaponData weaponData);

    default @NotNull List<String> getCoolTimeContext(WeaponData weaponData) {
        double ct = getCoolTime(weaponData);

        if (ct <= 0) {
            return new ArrayList<>();
        }

        return new ArrayList<>(
                Collections.singleton(
                        String.format("§3[クールタイム]§e -> %1$s秒", JavaUtil.digitRound(getCoolTime(weaponData), 2.0d)))
                );
    }

    double getCoolTime(WeaponData weaponData);
}
