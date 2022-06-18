package com.github.taichi3012.thelowtooltipmod.weapon.skill;

import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.normal.WeaponNormalSkillAttack;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.normal.WeaponNormalSkillBase;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.normal.WeaponNormalSkillBuff;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.unique.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SkillManager {

    private static final Map<String, WeaponNormalSkillBase> normalSkillMap = new HashMap<>();
    private static final Map<String, Map<String, WeaponSkillUniqueBase>> uniqueSkillMap = new HashMap<>();

    public static void registerAll() {
        //スキル情報の登録,たくさん項目があると見にくいので出来るだけ3つ以下に絞る

        //register(new SkillRemoveFire());
        register(new WeaponNormalSkillAttack("n_s_NEAR_RANGE_THUNDER", "ライゴウ", 0.8d, 28.0d));
        register(new WeaponNormalSkillAttack("n_s_NEAR_RANGE_ICE", "アイスエイジ", 0.8d, 29.0d));
        register(new WeaponNormalSkillAttack("n_s_NEAR_RANGE_FIRE", "陽炎 -かげろう", 1.1d, 30.0d));
        register(new WeaponNormalSkillAttack("n_s_NEAR_RANGE_DARKNESS", "リベイション", 0.6d, 26.0d));
        register(new WeaponNormalSkillAttack("n_s_FAR_RANGE_THUNDER", "エル・トール", 1.2d, 30.0d));
        register(new WeaponNormalSkillAttack("n_s_FAR_RANGE_ICE", "フェザントアロー", 1.2d, 25.0d));
        register(new WeaponNormalSkillAttack("n_s_FAR_RANGE_FIRE", "メイゴウ", 1.6d, 30.0d));
        register(new WeaponNormalSkillAttack("n_s_FAR_RANGE_DARKNESS", "ブラッククラッシャー", 0.8d, 28.0d));
        register(new WeaponNormalSkillAttack("n_s_FAR_SINGLE_LEVEL1", "ミラクルストーム", 3.0d, 20.0d));
        register(new WeaponNormalSkillAttack("n_s_FAR_SINGLE_LEVEL2", "サイクロンスター", 4.4d, 35.0d));
        register(new WeaponNormalSkillAttack("n_s_FAR_SINGLE_LEVEL3", "クリスタルエアー", 6.4d, 40.0d));
        register(new WeaponNormalSkillAttack("n_s_FAR_SINGLE_LEVEL4", "エンジェルリバース", 8.5d, 50.0d));

        register(new WeaponNormalSkillBuff("n_skill_r_fire", "天使の囁き", 10.0d));
        register(new WeaponNormalSkillBuff("n_skill_5", "姫の応援", 60.0d));
        register(new WeaponNormalSkillBuff("n_skill_1", "天狗の加護", 70.0d));
        register(new WeaponNormalSkillBuff("n_skill_11", "鋼の鎧", 60.0d));
        register(new WeaponNormalSkillBuff("n_skill_22", "羊飼いの一声", 60.0d));
        register(new WeaponNormalSkillBuff("n_skill_21", "騎士の喚き声", 60.0d));

        register(new WeaponSkillHasExplain("wskill1", "華麗なる剣技", "1", 0.0d, 0.0d,
                new String[]{"成功時", "失敗時"}, true, new double[]{1.2d, 0.8d}));
        register(new WeaponSkillBasic("wskill3", "パリィ", "1", 2.0d, 0.0d, false, 0.4d));
        register(new WeaponSkillBasic("wskill4", "デスダンス", "1", 70.0d, 0.0d));

        register(new WeaponSkillBasic("wskill5", "禁忌の力", "2", true, 0.05d));
        register(new WeaponSkillBasic("wskill8", "覚醒", "2", 120.0d, 0.0d));

        register(new WeaponSkillHasExplain("wskill10", "焼却", "3", 25.0d, 0.0d, "炎上時", false, 4.0d));
        register(new WeaponSkillBasic("wskill11", "炎の舞", "3", 30.0d, 0.0d));
        register(new WeaponSkillHasExplain("wskill12", "ラヴァネス", "3", 60.0d, 0.0d,
                new String[]{"通常", "炎上時"}, false, new double[]{3.0d, 6.0d}));

        register(new WeaponSkillBasic("wskill13", "ヘッドショット", "4", true, 1.2));
        register(new WeaponSkillBasic("wskill14", "トラップ", "4", 20.0d, 0.0d));
        register(new WeaponSkillHasExplain("wskill15", "ロックオン", Collections.singletonList("4"), 20.0d, 0.0d,
                new String[]{"ヒット", "通常攻撃", "通常攻撃(スタンあり)"}, new boolean[]{false, true, true}, new double[]{1.5d, 1.5d, 1.8d}));
        register(new WeaponSkillHasExplain("wskill16", "遠距離スナイプ", "4", 50.0d, 0.0d,
                new String[]{"通常", "スタンあり", "ロックオンあり", "ロックオン＋スタン"}, false, new double[]{6.0d, 13.0d, 9.0d, 19.5d}));

        register(new WeaponSkillBasic("wskill17", "闘争本能", "5", true, 2.0d));
        register(new WeaponSkillHasExplain("wskill18", "狂気", "5", 60.0d, 0.0d,
                new String[]{"通常", "パッシブ有効"}, true, new double[]{1.5d, 3.0d}));
        register(new WeaponSkillHasExplain("wskill19", "レイジ", "5", 15.0d, 0.0d,
                new String[]{"通常", "パッシブ有効", "バーサーク有効"}, false, new double[]{2.0d, 4.0d, 10.0d}));
        register(new WeaponSkillHasExplain("wskill20", "バーサーク", "5", 70.0d, 0.0d,
                new String[]{"通常", "狂気有効"}, true, new double[]{5.0d, 7.5d}));

        register(new WeaponSkillHasExplain("wskill21", "詠唱", "6", 0.0d, 0.0d, "通常攻撃時", true, 2.0d));
        register(new WeaponSkillHasExplain("wskill22", "マジックボール", "6", 10.0d, 0.0d,
                new String[]{"通常", "詠唱"}, false, new double[]{4.0d, 8.0d}));
        register(new WeaponSkillHasExplain("wskill23", "ライトニングボルト", "6", 20.0d, 0.0d,
                new String[]{"通常", "詠唱"}, false, new double[]{3.0d, 6.0d}));
        register(new WeaponSkillBasic("wskill24", "メテオストライク", "6", 80.0d, 0.0d));
        register(new WeaponSkillBasic("wskill24-2", "ファイヤーボルケーノ", "6", 45.0d, 12.0d, false, 22.0d));

        register(new WeaponSkill26());
        register(new WeaponSkillBasic("wskill27", "ブラインドアイ", "7", 20.0d, 0.0d));
        register(new WeaponSkillHasExplain("wskill28", "フロストアロー", "7", 40.0d, 3.0d,
                new String[]{"通常", "アイスショットあり", "アイスショットあり(鈍足)"}, false, new double[]{5.0d, 7.5d, 15.0d}));

        register(new WeaponSkillBasic("wskill30", "硬化", "8", 20.0d, 0.0d));
        register(new WeaponSkillBasic("wskill31", "挑発", "8", 20.0d, 0.0d, false, 1.0d));
        register(new WeaponSkillBasic("wskill32", "英雄伝", "8", 60.0d, 0.0d));

        register(new WeaponSkillBasic("wskill34", "みんながんばって！", "9", 15.0d, 0.0d));
        register(new WeaponSkillBasic("wskill35", "平和のために！", "9", 30.0d, 0.0d));
        register(new WeaponSkillBasic("wskill36", "戦姫の号令", "9", 140.0d, 0.0d));

        register(new WeaponSkillBasic("wskill43", "トゥルーロック", "11", 20.0d, 0.0d, false, 4.0d));
        register(new WeaponSkillBasic("wskill44", "ショックストーン", "11", 60.0d, 0.0d, false, 7.0d));

        register(new WeaponSkillBasic("wskill45", "光ある場所に", "12", true, 1.2d));
        register(new WeaponSkillBasic("wskill46", "ライトスパーク", "12", 20.0d, 0.0d));
        register(new WeaponSkillBasic("wskill47", "触れられざるもの", "12", 30.0d, 0.0d));
        register(new WeaponSkill48());

        register(new WeaponSkillHasExplain("wskill50", "死を操るもの", "13", 30.0d, 0.0d, "5秒経過後", true, 3.0d));
        register(new WeaponSkillHasExplain("wskill51", "迫りくる恐怖", "13", 25.0d, 0.0d,
                new String[]{"ヒット", "終了時"}, false, new double[]{1.0d, 3.0d}));
        register(new WeaponSkillBasic("wskill52", "死の宣告", "13", 70.0d, 0.0d));

        register(new WeaponSkillBasic("wskill74", "血の渇望", "19", 60.0d, 0.0d));
        register(new WeaponSkillBasic("wskill75", "血の流動", "19", 45.0d, 0.0d));
        register(new WeaponSkillBasic("wskill76", "血の斬撃", "19", 80.0d, 0.0d, true, 2.5d));

        register(new WeaponSkillBasic("wskill85", "-黒竜- ヘイロン", "22", 300.0d, 0.0d, false, 4.0d));

        register(new WeaponSkillBasic("wskill89", "-黒竜- ヘイロン -滅-", "23", 150.0d, 0.0d, false, 8.0d));
        register(new WeaponSkillHasExplain("wskill90", "龍の刻印", "23", 45.0d, 0.0d, "ヒット", false, 1.0d));
        register(new WeaponSkillBasic("wskill92", "竜の契約", "23", true, 1.5d));

        register(new WeaponSkillHasExplain("wskill101", "炎帝 ~バジリスクの炎息~", "26", 45.0d, 0.0d,
                new String[]{"通常", "猛火斬り有効"}, false, new double[]{8.0d, 8.0d * 1.44d}));
        register(new WeaponSkillBasic("wskill102", "猛火斬り", "26", 20.0d, 10.0d, true, 1.44));
        register(new WeaponSkillBasic("wskill103", "鎮火", "26,33", 20.0d, 0.0d));
        register(new WeaponSkillBasic("wskill104", "火傷", "26", true, 1.2d));

        register(new WeaponSkillBasic("wskill105", "ブリザードストライク", "27", 60.0d, 0.0d));
        register(new WeaponSkillBasic("wskill106", "アイスヒール", "27", 25.0d, 0.0d));
        register(new WeaponSkillBasic("wskill107", "アイススロウ", "27", 30.0d, 5.0d, false, 5.0d));

        register(new WeaponSkillHasExplain("wskill109", "カオスブリザード", "28", 120.0d, 5.0d,
                new String[]{"全弾ヒット", "1発あたり"}, false, new double[]{(1.1d * 7.0d), 1.1d}));
        register(new WeaponSkillBasic("wskill110", "アイススタンプ", "28", 45.0d, 0.0d));
        register(new WeaponSkillBasic("wskill111", "雪柱", "28", 50.0d, 0.0d, false, 4.0d));

        register(new WeaponSkill113());
        register(new WeaponSkill114());
        register(new WeaponSkill116());

        register(new WeaponSkillHasExplain("wskill117", "オーバーシュート", "30", 35.0d, 0.0d,
                new String[]{"通常", "シャドウパワーあり"}, false, new double[]{12.5d, 18.75d}));
        register(new WeaponSkillHasExplain("wskill118", "シャドウパワー", "30", 20.0d, 0.0d,
                new String[]{"直撃", "炸裂", "直撃(パッシブあり)", "炸裂(パッシブあり)"}, true, new double[]{(1.7d * 1.5d), (0.7d * 1.5d), (1.7d * 1.5d * 1.5d), (0.7d * 1.5d * 1.5d)}));
        register(new WeaponSkillBasic("wskill119", "ポイズンキラー", "30", 10.0d, 0.0d));
        register(new WeaponSkillHasExplain("wskill120", "エレメンタルパワー", "30", 0.0d, 0.0d,
                new String[]{"直撃", "炸裂"}, true, new double[]{1.7d * 1.5d, 0.7d * 1.5d}));

        register(new WeaponSkillBasic("wskill121", "戦姫の号令", "31", 140.0d, 0.0d));
        register(new WeaponSkillBasic("wskill122", "氷の鎧", "31", 210.0d, 30.0d));
        register(new WeaponSkillBasic("wskill123", "ホーリーキュア", "31", 80.0d, 5.0d));

        register(new WeaponSkill125());
        register(new WeaponSkillBasic("wskill126", "隠密", "32", 10.0d, 20.0d));
        register(new WeaponSkillBasic("wskill127", "ホーリーブラッド", "32", 15.0d, 0.0d));

        register(new WeaponSkillBasic("wskill129", "炎の極意", "33", true, 1.5d));
        register(new WeaponSkillBasic("wskill131", "大火炎", "33", 30.0d, 10.0d, false, 3.0d));
        register(new WeaponSkillBasic("wskill132", "炎の神殿", "33", 130.0d, 0.0d, false, 1.1d));

        register(new WeaponSkillBasic("wskill117", "オーバーシュート", "34", 35.0d, 0.0d, false, 12.5d));
        register(new WeaponSkillBasic("wskill134", "矢筒み", "34", 40.0d, 0.0d));
        register(new WeaponSkillBasic("wskill135", "自動射撃", "34", 60.0d, 7.0d, true, 1.0d));

        register(new WeaponSkillBasic("wskill137", "サテライトキャノン", "35", 50.0d, 10.0d, false, 2.5d));
        register(new WeaponSkillBasic("wskill138", "ダークサイクロン", "35", 45.0d, 5.0d, false, 0.8d));
        register(new WeaponSkillBasic("wskill139", "インフェライズ", "35", 30.0d, 0.0d, false, 2.0d));
        //register(new SkillHasExplain("wskill140", "グロウ", "35", 0.0d, 0.0d, new String[]{"直撃がヒット", "炸裂がヒット"}, true, new double[]{3.4d, 1.4d}));

        register(new WeaponSkill141());
        register(new WeaponSkillBasic("wskill143", "集中", "36", 35.0d, 20.0d));
        register(new WeaponSkillBasic("wskill144", "天下無双", "36", 60.0d, 15.0d));

        register(new WeaponSkillBasic("wskill149", "召喚術式 ~魔~", "37", 350.0d, 30.0d, false, 0.75d));
        register(new WeaponSkillBasic("wskill150", "召喚術式 ~弓~", "38", 350.0d, 30.0d, false, 1.0d));
        register(new WeaponSkillBasic("wskill151", "召喚術式 ~剣~", "39", 350.0d, 30.0d, false, 0.8d));

        register(new WeaponSkillBasic("wskill156", "激昂乱舞", "40", 20.0d, 20.0d));
        register(new WeaponSkillBasic("wskill157", "才色兼備", "40", 30.0d, 1.0d,
                false, 1.0d));
        register(new WeaponSkill158());
        register(new WeaponSkillBasic("wskill159", "錦上添花", "40", true, 2.0d));

        register(new WeaponSkillBasic("wskill3", "攻撃破壊", "45", 2.0d, 0.0d, false, 0.4d));
        register(new WeaponSkillBasic("wskill44", "魔力解放", "45", 70.0d, 0.0d, false, 6.0d));
        register(new WeaponSkillBasic("wskill74", "魔力吸収", "45", 60.0d, 5.0d));

        register(new WeaponSkillHasExplain("wskill161", "冥雷", "46", 0.0d, 0.0d,
                new String[]{"発動時", "通常"}, true, new double[]{9.0d, 0.1d}));
        register(new WeaponSkillBasic("wskill165", "イモータルスフィア", "46", 70.0d, 6.0d, false, 3.0d));
        register(new WeaponSkillBasic("wskill166", "アタナイトキューブ", "46", 50.0d, 10.0d));
        register(new WeaponSkillBasic("wskill167", "咒力爆弾", "46", 1.0d, 1.0d, false, 1.05d));

        register(new WeaponSkillBasic("wskill169", "獄陽炎", "47", 35.0d, 0.0d, false, 4.0d));
        register(new WeaponSkillBasic("wskill170", "ブラックホール", "47", 25.0d, 0.0d));
        register(new WeaponSkillHasExplain("wskill171", "グラビティエンド", "47", 110.0d, 15.0d, "範囲攻撃", false, 1.0d));

        register(new WeaponSkillBasic("wskill172", "パニッシュメント", "48", 30.0d, 0.0d, false, 2.0d));
        register(new WeaponSkillBasic("wskill173", "士気高揚", "48", 40.0d, 30.0d));
        register(new WeaponSkillBasic("wskill174", "ステッドショック", "48", 60.0d, 8.0d, false, 32.0d));
    }

    public static void register(WeaponNormalSkillBase skill) {
        normalSkillMap.put(skill.id, skill);
    }

    public static void register(WeaponSkillUniqueBase skill) {
        for (String s : skill.skillSetIds) {
            uniqueSkillMap.computeIfAbsent(s, k -> new HashMap<>())
                    .put(skill.id, skill);
        }
    }

    public static WeaponNormalSkillBase getNormalSkill(String id) {
        return normalSkillMap.get(id);
    }

    public static WeaponSkillUniqueBase getUniqueSkill(String skillSetId, String id) {
        Map<String, WeaponSkillUniqueBase> skillSet = uniqueSkillMap.get(skillSetId);
        return skillSet != null ? skillSet.get(id) : null;
    }

    public static WeaponSkillBase getSkill(WeaponData weaponData, String id) {
        String skillSetId = weaponData.getSkillSetId();
        Map<String, WeaponSkillUniqueBase> skillSet = uniqueSkillMap.get(skillSetId);

        if (skillSet != null) {
            return skillSet.get(id);
        }

        return normalSkillMap.get(id);
    }

}
