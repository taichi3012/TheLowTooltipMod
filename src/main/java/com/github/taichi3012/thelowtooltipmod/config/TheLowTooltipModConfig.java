package com.github.taichi3012.thelowtooltipmod.config;

import com.github.taichi3012.thelowtooltipmod.TheLowTooltipMod;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TheLowTooltipModConfig {
    public static final String GENERAL = "General";
    private static Configuration config;
    private static boolean isTooltipEnable = true;
    private static boolean isSkillCoolTimeContextEnable = true;
    private static boolean isSkillResultContextEnable = true;
    private static double overStrengthSwordValue = 0d;
    private static double overStrengthBowValue = 0d;
    private static double overStrengthMagicValue = 0d;
    private static int quickTalkSpellStage = 0;

    public static void loadConfig(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile(), TheLowTooltipMod.VERSION, true);
        initConfig();
        syncConfig();
    }

    private static void initConfig() {
        config.addCustomCategoryComment(GENERAL, "TheLowTooltipModの設定");
    }

    public static void syncConfig() {
        isTooltipEnable = config.getBoolean("ツールチップ表示", GENERAL, true, "ツールチップでダメージなどの表記を行うかどうかの設定");
        isSkillCoolTimeContextEnable = config.getBoolean("スキルクールタイム表示", GENERAL, true, "ツールチップでダメージの表記を行うかどうかの設定");
        isSkillResultContextEnable = config.getBoolean("スキルダメージ表示", GENERAL, true, "ツールチップでダメージの表記を行うかどうかの設定");
        overStrengthSwordValue = config.get(GENERAL, "剣攻撃力増加パークの値", 0.0d, "剣のダメージ計算時に使うOSの値", 0.0d, 200.0d).getDouble();
        overStrengthBowValue = config.get(GENERAL, "弓攻撃力増加パークの値", 0.0d, "弓のダメージ計算時に使うOSの値", 0.0d, 200.0d).getDouble();
        overStrengthMagicValue = config.get(GENERAL, "魔法攻撃力増加パークの値", 0.0d, "魔法のダメージ計算時に使うOSの値", 0.0d, 200.0d).getDouble();
        quickTalkSpellStage = config.get(GENERAL, "スキルCT減少パークの値", 0, "CTの計算に使うCT減少パークの値", 0, 10).getInt();

        if (config.hasChanged())
            config.save();
    }

    public static void setOverStrengthValue(WeaponType type, double value) {
        switch (type) {
            case SWORD:
                config.get(GENERAL, "剣攻撃力増加パークの値", 0.0d, "剣のダメージ計算時に使うOSの値", 0.0d, 200.0d).set(value);
                break;
            case BOW:
                config.get(GENERAL, "弓攻撃力増加パークの値", 0.0d, "弓のダメージ計算時に使うOSの値", 0.0d, 200.0d).set(value);
                break;
            case MAGIC:
                config.get(GENERAL, "魔法攻撃力増加パークの値", 0.0d, "魔法のダメージ計算時に使うOSの値", 0.0d, 200.0d).set(value);
                break;
        }
        syncConfig();
    }

    public static void setQuickTalkSpellStage(int stage) {
        config.get(GENERAL, "スキルCT減少パークの値", 0, "CTの計算に使うCT減少パークの値", 0, 10).set(stage);
        syncConfig();
    }

    public static Configuration getConfig() {
        return config;
    }

    public static boolean isTooltipEnable() {
        return isTooltipEnable;
    }

    public static boolean isSkillCoolTimeContextEnable() {
        return isSkillCoolTimeContextEnable;
    }

    public static boolean isSkillResultContextEnable() {
        return isSkillResultContextEnable;
    }

    public static int getQuickTalkSpellStage() {
        return quickTalkSpellStage;
    }

    public static double getOSParkGainByWeaponType(WeaponType type) {
        switch (type) {
            case SWORD:
                return overStrengthSwordValue;
            case BOW:
                return overStrengthBowValue;
            case MAGIC:
                return overStrengthMagicValue;
            default:
                return 0.0d;
        }
    }

    public static double getQuickSpellTalkMultiply() {
        return 1.0d - quickTalkSpellStage * 0.05d;
    }
}
