package com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone;

public class MagicStoneData {
    private final IMagicStone magicStone;
    private final MagicStoneLevelType level;

    public MagicStoneData(IMagicStone magicStone, MagicStoneLevelType level) {
        this.magicStone = magicStone;
        this.level = level;
    }

    public IMagicStone getMagicStone() {
        return magicStone;
    }

    public MagicStoneLevelType getLevel() {
        return level;
    }

    public boolean isSpecialType() {
        return magicStone instanceof SpecialMagicStoneType;
    }

    public boolean isPassiveType() {
        return magicStone instanceof PassiveMagicStoneType;
    }
}
