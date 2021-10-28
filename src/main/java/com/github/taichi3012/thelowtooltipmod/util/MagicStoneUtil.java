package com.github.taichi3012.thelowtooltipmod.util;

import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.MagicStoneLevelType.*;
import static com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.OtherMagicStoneType.UNKNOWN_MAGIC_STONE;

public class MagicStoneUtil {
    public static MagicStoneData getDataByNBTValue(String value) {
        MagicStone resultMS = null;
        List<MagicStone> allMagicStone = new ArrayList<>();
        allMagicStone.addAll(Arrays.asList(SpecialMagicStoneType.values()));
        allMagicStone.addAll(Arrays.asList(PassiveMagicStoneType.values()));

        for (MagicStone ms : allMagicStone) {
            if (value.contains(ms.getID())) {
                resultMS = ms;
                break;
            }
        }

        if (resultMS == null) return new MagicStoneData(UNKNOWN_MAGIC_STONE, UNKNOWN_LEVEL);
        return new MagicStoneData(resultMS, getLevelByID(value.replace(resultMS.getID(), "")));
    }
}
