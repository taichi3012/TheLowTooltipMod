package com.github.taichi3012.thelowtooltipmod.config;

import com.github.taichi3012.thelowtooltipmod.TheLowTooltipMod;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TheLowTooltipModConfig {
    public static final String GENERAL = "General";
    public static Configuration config;
    public static boolean isTooltipEnable = true;
    public static double overStrengthSwordValue = 0d;
    public static double overStrengthBowValue = 0d;
    public static double overStrengthMagicValue = 0d;

    public static void loadConfig(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile(), TheLowTooltipMod.VERSION, true);
        initConfig();
        syncConfig();

    }

    private static void initConfig() {
        config.addCustomCategoryComment(GENERAL, "TheLowTooltipModの設定");
    }

    public static void syncConfig() {
        isTooltipEnable = config.getBoolean("enableTooltipDamage", GENERAL, true, "ツールチップでダメージの表記を行うかどうかの設定");
        overStrengthSwordValue = config.get(GENERAL, "overStrengthSword", 0d, "剣のダメージ計算時に使うOSの値", 0d, 200d).getDouble();
        overStrengthBowValue = config.get(GENERAL, "overStrengthBow", 0d, "弓のダメージ計算時に使うOSの値", 0d, 200d).getDouble();
        overStrengthMagicValue = config.get(GENERAL, "overStrengthMagic", 0d, "魔法のダメージ計算時に使うOSの値", 0d, 200d).getDouble();

        if (config.hasChanged()) config.save();
    }

    public static void setOverStrengthValue(WeaponType type, double value) {
        switch (type) {
            case SWORD:
                config.get(GENERAL, "overStrengthSword", 0d, "剣のダメージ計算時に使うOSの値", 0d, 200d).set(value);
                break;
            case BOW:
                config.get(GENERAL, "overStrengthBow", 0d, "弓のダメージ計算時に使うOSの値", 0d, 200d).set(value);
                break;
            case MAGIC:
                config.get(GENERAL, "overStrengthMagic", 0d, "魔法のダメージ計算時に使うOSの値", 0d, 200d).set(value);
                break;
        }
        syncConfig();
    }
}
