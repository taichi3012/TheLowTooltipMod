package com.github.taichi3012.thelowtooltipmod.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.IMagicStone;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.MagicStoneData;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.PassiveMagicStoneType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.SpecialMagicStoneType;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;

import static com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.MagicStoneLevelType.*;
import static com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.OtherMagicStoneType.UNKNOWN_MAGIC_STONE;

public class MagicStoneUtil {
    public static MagicStoneData getDataByNBTValue(String value) {
        IMagicStone resultMS = null;
        List<IMagicStone> allMagicStone = new ArrayList<>();
        allMagicStone.addAll(Arrays.asList(SpecialMagicStoneType.values()));
        allMagicStone.addAll(Arrays.asList(PassiveMagicStoneType.values()));

        for (IMagicStone ms : allMagicStone) {
            if (value.contains(ms.getId())) {
                resultMS = ms;
                break;
            }
        }

        if (resultMS == null) {
            return new MagicStoneData(UNKNOWN_MAGIC_STONE, UNKNOWN_LEVEL);
        }

        return new MagicStoneData(resultMS, getLevelByID(value.replace(resultMS.getId(), "")));
    }

    public static double getCasterMultiply(WeaponData weaponData) {
        return weaponData.getMagicStoneList().stream()
                .filter(magicStoneData -> magicStoneData.getMagicStone().equals(PassiveMagicStoneType.CASTER_MAGIC_STONE))
                .mapToDouble(magicStoneData -> magicStoneData.getLevel().getCoolTimeMultiply())
                .reduce(1.0d, (a, b) -> a * b);
    }

    public static int getLegendValue(WeaponData weaponData) {
        return weaponData.getMagicStoneList().stream()
                .filter(magicStoneData -> magicStoneData.getLevel().equals(LEGEND))
                .mapToInt(lv -> 1)
                .sum();
    }
}
