package com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone;

import javax.annotation.Nonnull;

public class MagicStoneData {
    private final MagicStone magicStone;
    private final MagicStoneLevelType level;

    public MagicStoneData(@Nonnull MagicStone magicStone, @Nonnull MagicStoneLevelType level) {
        this.magicStone = magicStone;
        this.level = level;
    }

    public MagicStone getMagicStone() {
        return magicStone;
    }

    public MagicStoneLevelType getLevel() {
        return level;
    }

    public boolean isSpecialType() {
        return magicStone instanceof SpecialMagicStoneType;
    }
}
