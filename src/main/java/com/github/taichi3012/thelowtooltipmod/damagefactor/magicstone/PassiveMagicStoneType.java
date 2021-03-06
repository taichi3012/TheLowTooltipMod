package com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone;

public enum PassiveMagicStoneType implements IMagicStone {
    CASTER_MAGIC_STONE("キャスター", "reduce_cooltime_magic_stone"),
    POSING_MAGIC_STONE("ポージング", "add_mp_magicstone");

    private final String name;
    private final String id;

    PassiveMagicStoneType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }
}
