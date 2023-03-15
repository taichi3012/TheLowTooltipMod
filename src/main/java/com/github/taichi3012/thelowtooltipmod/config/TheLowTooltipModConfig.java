package com.github.taichi3012.thelowtooltipmod.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.github.taichi3012.thelowtooltipmod.TheLowTooltipMod;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;

public class TheLowTooltipModConfig {
    public static final String GENERAL_CATEGORY = "General";
    public static final String TOOLTIP_CATEGORY = GENERAL_CATEGORY + ".Tooltip";
    public static final String PARK_CATEGORY = GENERAL_CATEGORY + ".Park";
    public static final String CALC_URL_CATEGORY = GENERAL_CATEGORY + ".CalcURL";

    private static Configuration config;

    private static boolean isTooltipEnable = true;
    private static boolean isSkillCoolTimeContextEnable = true;
    private static boolean isSkillResultContextEnable = true;
    private static boolean isOnlyDisplayResultAndName = false;

    private static double overStrengthSwordValue = 0d;
    private static double overStrengthBowValue = 0d;
    private static double overStrengthMagicValue = 0d;
    private static int quickTalkSpellStage = 0;

    private static boolean isURLGenerateEnable = true;
    private static boolean isURLCopyEnable = true;
    private static boolean isURLComponentPrintEnable = true;

    public static void loadConfig(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile(), TheLowTooltipMod.VERSION, true);
        initConfig();
        syncConfig();
    }

    private static void initConfig() {
        if (config == null) {
            return;
        }

        config.setCategoryLanguageKey(GENERAL_CATEGORY, "config.thelowtooltipmod.category.general")
                .setCategoryComment(GENERAL_CATEGORY, TheLowTooltipMod.MOD_NAME + "の設定")
                .setCategoryLanguageKey(TOOLTIP_CATEGORY, "config.thelowtooltipmod.category.general.tooltip")
                .setCategoryComment(TOOLTIP_CATEGORY, "ツールチップ表示の設定")
                .setCategoryLanguageKey(PARK_CATEGORY, "config.thelowtooltipmod.category.general.park")
                .setCategoryComment(PARK_CATEGORY, "計算に使用するパークの値の設定")
                .setCategoryLanguageKey(CALC_URL_CATEGORY, "config.thelowtooltipmod.category.general.calcurl")
                .setCategoryComment(CALC_URL_CATEGORY, "WEBダメージ計算機、URL生成機能の設定");
    }

    public static void syncConfig() {
        isTooltipEnable = config.getBoolean("enabletooltipdisplay", TOOLTIP_CATEGORY, true, "ツールチップでダメージなどの表記を行うかどうかの設定", "config.thelowtooltipmod.prop.tooltip.enabletooltipdisplay");
        isOnlyDisplayResultAndName = config.getBoolean("onlydisplayresultandname", TOOLTIP_CATEGORY, false, "ツールチップでアイテムの名前と計算結果のみ表示するかどうかの設定", "config.thelowtooltipmod.prop.tooltip.onlydisplayresultandname");
        isSkillResultContextEnable = config.getBoolean("enableskillresultcontext", TOOLTIP_CATEGORY, true, "ツールチップでスキルダメージの表記を行うかどうかの設定", "config.thelowtooltipmod.prop.tooltip.enableskillresultcontext");
        isSkillCoolTimeContextEnable = config.getBoolean("enableskillcooltimecontext", TOOLTIP_CATEGORY, true, "ツールチップでスキルクールタイムの表記を行うかどうかの設定", "config.thelowtooltipmod.prop.tooltip.enableskillcooltimecontext");

        overStrengthSwordValue = config.get(PARK_CATEGORY, "overstrengthsword", 0.0d, "剣のダメージ計算時に使うOSの値", 0.0d, 200.0d)
                .setLanguageKey("config.thelowtooltipmod.prop.park.overstrengthsword")
                .getDouble();
        overStrengthBowValue = config.get(PARK_CATEGORY, "overstrengthbow", 0.0d, "弓のダメージ計算時に使うOSの値", 0.0d, 200.0d)
                .setLanguageKey("config.thelowtooltipmod.prop.park.overstrengthbow")
                .getDouble();
        overStrengthMagicValue = config.get(PARK_CATEGORY, "overstrengthmagic", 0.0d, "魔法のダメージ計算時に使うOSの値", 0.0d, 200.0d)
                .setLanguageKey("config.thelowtooltipmod.prop.park.overstrengthmagic")
                .getDouble();
        quickTalkSpellStage = config.getInt("quicktalkspell", PARK_CATEGORY, 0, 0, 10, "CTの計算に使うCT減少パークの値", "config.thelowtooltipmod.prop.park.quicktalkspell");

        isURLGenerateEnable = config.getBoolean("enablegenerateurl", CALC_URL_CATEGORY, true, "ThelowDamageCalculationのURL生成機能を使用するかどうかの設定", "config.thelowtooltipmod.prop.url.enablegenerateurl");
        isURLCopyEnable = config.getBoolean("enablecopyurl", CALC_URL_CATEGORY, true, "URL生成時、クリップボードに結果をコピーするかどうかの設定", "config.thelowtooltipmod.prop.url.enablecopyurl");
        isURLComponentPrintEnable = config.getBoolean("enableprintcomponent", CALC_URL_CATEGORY, true, "URL生成時、コンポーネントを表示するかどうかの設定", "config.thelowtooltipmod.prop.url.enableprintcomponent");

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void setOverStrengthValue(WeaponType type, double value) {
        switch (type) {
            case SWORD:
                config.get(PARK_CATEGORY, "overstrengthsword", 0.0d, "剣のダメージ計算時に使うOSの値", 0.0d, 200.0d).set(value);
                break;
            case BOW:
                config.get(PARK_CATEGORY, "overstrengthbow", 0.0d, "弓のダメージ計算時に使うOSの値", 0.0d, 200.0d).set(value);
                break;
            case MAGIC:
                config.get(PARK_CATEGORY, "overstrengthmagic", 0.0d, "魔法のダメージ計算時に使うOSの値", 0.0d, 200.0d).set(value);
                break;
        }
        syncConfig();
    }

    public static void setQuickTalkSpellStage(int stage) {
        config.get(PARK_CATEGORY, "quicktalkspell", 0, "CTの計算に使うCT減少パークの値", 0, 10).set(stage);
        syncConfig();
    }

    public static Configuration getConfig() {
        return config;
    }

    public static boolean isTooltipEnable() {
        return isTooltipEnable;
    }

    public static boolean isOnlyDisplayResultAndNameEnable() {
        return isOnlyDisplayResultAndName;
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

    public static boolean isURLGenerateEnable() {
        return isURLGenerateEnable;
    }

    public static boolean isURLCopyEnable() {
        return isURLCopyEnable;
    }

    public static boolean isIsURLComponentPrintEnable() {
        return isURLComponentPrintEnable;
    }

    public static double getQuickSpellTalkMultiply() {
        return 1.0d - quickTalkSpellStage * 0.05d;
    }
}
