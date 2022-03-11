package com.github.taichi3012.thelowtooltipmod.weapon.skill;

import com.github.taichi3012.thelowtooltipmod.weapon.skill.normal.SkillAttackNormal;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.normal.SkillBuffNormal;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.normal.SkillRemoveFire;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.unique.*;

import java.util.HashMap;
import java.util.Map;

public class SkillManager {
    private static final Map<String, IWeaponSkillAble> skillMap = new HashMap<>();

    public static void registerAll() {
        //スキル情報の登録,たくさん項目があると見にくいので出来るだけ3つ以下に絞る
        register(new SkillRemoveFire());
        register(new SkillAttackNormal("n_s_NEAR_RANGE_THUNDER", "ライゴウ", 0.8d, 28.0d));
        register(new SkillAttackNormal("n_s_NEAR_RANGE_ICE", "アイスエイジ", 0.8d, 29.0d));
        register(new SkillAttackNormal("n_s_NEAR_RANGE_FIRE", "陽炎 -かげろう", 1.1d, 30.0d));
        register(new SkillAttackNormal("n_s_NEAR_RANGE_DARKNESS", "リベイション", 0.6d, 26.0d));
        register(new SkillAttackNormal("n_s_FAR_RANGE_THUNDER", "エル・トール", 1.2d, 30.0d));
        register(new SkillAttackNormal("n_s_FAR_RANGE_ICE", "フェザントアロー", 1.2d, 25.0d));
        register(new SkillAttackNormal("n_s_FAR_RANGE_FIRE", "メイゴウ", 1.6d, 30.0d));
        register(new SkillAttackNormal("n_s_FAR_RANGE_DARKNESS", "ブラッククラッシャー", 0.8d, 28.0d));
        register(new SkillAttackNormal("n_s_FAR_SINGLE_LEVEL1", "ミラクルストーム", 3.0d, 20.0d));
        register(new SkillAttackNormal("n_s_FAR_SINGLE_LEVEL2", "サイクロンスター", 4.4d, 35.0d));
        register(new SkillAttackNormal("n_s_FAR_SINGLE_LEVEL3", "クリスタルエアー", 6.4d, 40.0d));
        register(new SkillAttackNormal("n_s_FAR_SINGLE_LEVEL4", "エンジェルリバース", 8.5d, 50.0d));

        register(new SkillBuffNormal("n_skill_5", "姫の応援", 60.0d));
        register(new SkillBuffNormal("n_skill_1", "天狗の加護", 70.0d));
        register(new SkillBuffNormal("n_skill_11", "鋼の鎧", 60.0d));
        register(new SkillBuffNormal("n_skill_22", "羊飼いの一声", 60.0d));
        register(new SkillBuffNormal("n_skill_21", "騎士の喚き声", 60.0d));

        register(new SkillHasExplain("wskill1", "華麗なる剣技", "1", 0.0d, 0.0d,
                new String[]{"成功時", "失敗時"}, true, new double[]{1.2d, 0.8d}));
        register(new Skill3());
        register(new SkillBasic("wskill4", "デスダンス", "1", 70.0d, 0.0d));

        register(new SkillBasic("wskill5", "禁忌の力", "2", true, 0.05d));
        register(new SkillBasic("wskill8", "覚醒", "2", 120.0d, 0.0d));

        register(new SkillHasExplain("wskill10", "焼却", "3", 25.0d, 0.0d, "炎上時", false, 4.0d));
        register(new SkillBasic("wskill11", "炎の舞", "3", 30.0d, 0.0d));
        register(new SkillHasExplain("wskill12", "ラヴァネス", "3", 60.0d, 0.0d,
                new String[]{"通常", "炎上時"}, false, new double[]{3.0d, 6.0d}));

        register(new SkillBasic("wskill13", "ヘッドショット", "4", true, 1.2));
        register(new SkillBasic("wskill14", "トラップ", "4", 20.0d, 0.0d));
        register(new SkillHasExplain("wskill15", "ロックオン", "4", 20.0d, 0.0d,
                new String[]{"ヒット", "通常攻撃", "通常攻撃(スタンあり)"}, new boolean[]{false, true, true}, new double[]{1.5d, 1.5d, 1.8d}));
        register(new SkillHasExplain("wskill16", "遠距離スナイプ", "4", 50.0d, 0.0d,
                new String[]{"通常", "スタンあり", "ロックオンあり", "ロックオン＋スタン"}, false, new double[]{6.0d, 13.0d, 9.0d, 19.5d}));

        register(new SkillBasic("wskill17", "闘争本能", "5", true, 2.0d));
        register(new SkillHasExplain("wskill18", "狂気", "5", 60.0d, 0.0d,
                new String[]{"通常", "パッシブ有効"}, true, new double[]{1.5d, 3.0d}));
        register(new SkillHasExplain("wskill19", "レイジ", "5", 15.0d, 0.0d,
                new String[]{"通常", "パッシブ有効", "バーサーク有効"}, false, new double[]{2.0d, 4.0d, 10.0d}));
        register(new SkillHasExplain("wskill20", "バーサーク", "5", 70.0d, 0.0d,
                new String[]{"通常", "狂気有効"}, true, new double[]{5.0d, 7.5d}));

        register(new SkillHasExplain("wskill21", "詠唱", "6", 0.0d, 0.0d, "通常攻撃時", true, 2.0d));
        register(new SkillHasExplain("wskill22", "マジックボール", "6", 10.0d, 0.0d,
                new String[]{"通常", "詠唱"}, false, new double[]{4.0d, 8.0d}));
        register(new SkillHasExplain("wskill23", "ライトニングボルト", "6", 20.0d, 0.0d,
                new String[]{"通常", "詠唱"}, false, new double[]{3.0d, 6.0d}));
        register(new SkillBasic("wskill24", "メテオストライク", "6", 80.0d, 0.0d));
        register(new SkillBasic("wskill24-2", "ファイヤーボルケーノ", "6", 45.0d, 12.0d, false, 22.0d));

        register(new Skill26());
        register(new SkillBasic("wskill27", "ブラインドアイ", "7", 20.0d, 0.0d));
        register(new SkillHasExplain("wskill28", "フロストアロー", "7", 40.0d, 3.0d,
                new String[]{"通常", "アイスショットあり", "アイスショットあり(鈍足)"}, false, new double[]{5.0d, 7.5d, 15.0d}));

        register(new SkillBasic("wskill30", "硬化", "8", 20.0d, 0.0d));
        register(new SkillBasic("wskill31", "挑発", "8", 20.0d, 0.0d, false, 1.0d));
        register(new SkillBasic("wskill32", "英雄伝", "8", 60.0d, 0.0d));

        register(new SkillBasic("wskill34", "みんながんばって！", "9", 15.0d, 0.0d));
        register(new SkillBasic("wskill35", "平和のために！", "9", 30.0d, 0.0d));
        register(new SkillBasic("wskill36", "戦姫の号令", "9", 140.0d, 0.0d));

        register(new SkillBasic("wskill43", "トゥルーロック", "11", 20.0d, 0.0d, false, 4.0d));
        register(new Skill44());

        register(new SkillBasic("wskill45", "光ある場所に", "12", true, 1.2d));
        register(new SkillBasic("wskill46", "ライトスパーク", "12", 20.0d, 0.0d));
        register(new SkillBasic("wskill47", "触れられざるもの", "12", 30.0d, 0.0d));
        register(new Skill48());

        register(new SkillHasExplain("wskill50", "死を操るもの", "13", 30.0d, 0.0d, "5秒経過後", true, 3.0d));
        register(new SkillHasExplain("wskill51", "迫りくる恐怖", "13", 25.0d, 0.0d,
                new String[]{"ヒット", "終了時"}, false, new double[]{1.0d, 3.0d}));
        register(new SkillBasic("wskill52", "死の宣告", "13", 70.0d, 0.0d));

        register(new Skill74());
        register(new SkillBasic("wskill75", "血の流動", "19", 45.0d, 0.0d));
        register(new SkillBasic("wskill76", "血の斬撃", "19", 80.0d, 0.0d, true, 2.5d));

        register(new SkillBasic("wskill85", "-黒竜- ヘイロン", "22", 300.0d, 0.0d, false, 4.0d));

        register(new SkillBasic("wskill89", "-黒竜- ヘイロン -滅-", "23", 150.0d, 0.0d, false, 8.0d));
        register(new SkillHasExplain("wskill90", "龍の刻印", "23", 45.0d, 0.0d, "ヒット", false, 1.0d));
        register(new SkillBasic("wskill92", "竜の契約", "23", true, 1.5d));

        register(new SkillHasExplain("wskill101", "炎帝 ~バジリスクの炎息~", "26", 45.0d, 0.0d,
                new String[]{"通常", "猛火斬り有効"}, false, new double[]{8.0d, 8.0d * 1.44d}));
        register(new SkillBasic("wskill102", "猛火斬り", "26", 20.0d, 10.0d, true, 1.44));
        register(new SkillBasic("wskill103", "鎮火", "26,33", 20.0d, 0.0d));
        register(new SkillBasic("wskill104", "火傷", "26", true, 1.2d));

        register(new SkillBasic("wskill105", "ブリザードストライク", "27", 60.0d, 0.0d));
        register(new SkillBasic("wskill106", "アイスヒール", "27", 25.0d, 0.0d));
        register(new SkillBasic("wskill107", "アイススロウ", "27", 30.0d, 5.0d, false, 5.0d));

        register(new SkillHasExplain("wskill109", "カオスブリザード", "28", 120.0d, 5.0d,
                new String[]{"全弾ヒット", "1発あたり"}, false, new double[]{(1.1d * 7.0d), 1.1d}));
        register(new SkillBasic("wskill110", "アイススタンプ", "28", 45.0d, 0.0d));
        register(new SkillBasic("wskill111", "雪柱", "28", 50.0d, 0.0d, false, 4.0d));

        register(new Skill113());
        register(new Skill114());
        register(new Skill116());

        register(new Skill117());
        register(new SkillHasExplain("wskill118", "シャドウパワー", "30", 20.0d, 0.0d,
                new String[]{"直撃", "炸裂", "直撃(パッシブあり)", "炸裂(パッシブあり)"}, true, new double[]{(1.7d * 1.5d), (0.7d * 1.5d), (1.7d * 1.5d * 1.5d), (0.7d * 1.5d * 1.5d)}));
        register(new SkillBasic("wskill119", "ポイズンキラー", "30", 10.0d, 0.0d));
        register(new SkillHasExplain("wskill120", "エレメンタルパワー", "30", 0.0d, 0.0d,
                new String[]{"直撃", "炸裂"}, true, new double[]{1.7d * 1.5d, 0.7d * 1.5d}));

        register(new SkillBasic("wskill121", "戦姫の号令", "31", 140.0d, 0.0d));
        register(new SkillBasic("wskill122", "氷の鎧", "31", 210.0d, 30.0d));
        register(new SkillBasic("wskill123", "ホーリーキュア", "31", 80.0d, 5.0d));

        register(new Skill125());
        register(new SkillBasic("wskill126", "隠密", "32", 10.0d, 20.0d));
        register(new SkillBasic("wskill127", "ホーリーブラッド", "32", 15.0d, 0.0d));

        register(new SkillBasic("wskill129", "炎の極意", "33", true, 1.5d));
        register(new SkillBasic("wskill131", "大火炎", "33", 30.0d, 10.0d, false, 3.0d));
        register(new SkillBasic("wskill132", "炎の神殿", "33", 130.0d, 0.0d, false, 1.1d));

        register(new SkillBasic("wskill134", "矢筒み", "34", 40.0d, 0.0d));
        register(new SkillBasic("wskill135", "自動射撃", "34", 60.0d, 7.0d, true, 1.0d));

        register(new SkillBasic("wskill137", "サテライトキャノン", "35", 50.0d, 10.0d, false, 2.5d));
        register(new SkillBasic("wskill138", "ダークサイクロン", "35", 45.0d, 5.0d, false, 0.8d));
        register(new SkillBasic("wskill139", "インフェライズ", "35", 30.0d, 0.0d, false, 2.0d));
        //register(new SkillHasExplain("wskill140", "グロウ", "35", 0.0d, 0.0d, new String[]{"直撃がヒット", "炸裂がヒット"}, true, new double[]{3.4d, 1.4d}));

        register(new Skill141());
        register(new SkillBasic("wskill143", "集中", "36", 35.0d, 20.0d));
        register(new SkillBasic("wskill144", "天下無双", "36", 60.0d, 15.0d));

        register(new SkillBasic("wskill149", "召喚術式 ~魔~", "37", 350.0d, 30.0d, false, 0.75d));
        register(new SkillBasic("wskill150", "召喚術式 ~弓~", "38", 350.0d, 30.0d, false, 1.0d));
        register(new SkillBasic("wskill151", "召喚術式 ~剣~", "39", 350.0d, 30.0d, false, 0.8d));

        register(new SkillBasic("wskill156", "激昂乱舞", "40", 20.0d, 20.0d));
        register(new SkillBasic("wskill157", "才色兼備", "40", 30.0d, 1.0d,
                false, 1.0d));
        register(new Skill158());
        register(new SkillBasic("wskill159", "錦上添花", "40", true, 2.0d));

        register(new SkillHasExplain("wskill161", "冥雷", "46", 0.0d, 0.0d,
                new String[]{"発動時", "通常"}, true, new double[]{9.0d, 0.1d}));
        register(new SkillBasic("wskill165", "イモータルスフィア", "46", 70.0d, 6.0d, false, 3.0d));
        register(new SkillBasic("wskill166", "アタナイトキューブ", "46", 50.0d, 10.0d));
        register(new SkillBasic("wskill167", "咒力爆弾", "46", 1.0d, 1.0d, false, 1.05d));

        register(new SkillBasic("wskill169", "獄陽炎", "47", 35.0d, 0.0d, false, 4.0d));
        register(new SkillBasic("wskill170", "ブラックホール", "47", 25.0d, 0.0d));
        register(new SkillHasExplain("wskill171", "グラビティエンド", "47", 110.0d, 15.0d, "範囲攻撃", false, 1.0d));

        register(new SkillBasic("wskill172", "パニッシュメント", "48", 30.0d, 0.0d, false, 2.0d));
        register(new SkillBasic("wskill173", "士気高揚", "48", 40.0d, 30.0d));
        register(new SkillBasic("wskill174", "ステッドショック", "48", 60.0d, 8.0d, false, 28.0d));
    }

    public static void register(IWeaponSkillAble skill) {
        skillMap.put(skill.getId(), skill);
    }

    public static IWeaponSkillAble getSkill(String id) {
        return skillMap.getOrDefault(id, null);
    }
}
