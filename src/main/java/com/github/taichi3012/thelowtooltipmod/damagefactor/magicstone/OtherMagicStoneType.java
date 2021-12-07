package com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone;

public enum OtherMagicStoneType implements IMagicStone {
    UNKNOWN_MAGIC_STONE("Unknown", "");

    private final String name;
    private final String id;

    OtherMagicStoneType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
