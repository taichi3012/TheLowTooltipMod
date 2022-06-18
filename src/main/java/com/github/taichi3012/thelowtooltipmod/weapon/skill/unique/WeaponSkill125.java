package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.util.JavaUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WeaponSkill125 extends WeaponSkillUniqueBase {

    public WeaponSkill125() {
        super("wskill125", "恵みの泉", "32");
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        double ct = TheLowUtil.getPlayerJobType().equals(JobType.PRIEST) ? 132.0d : 220.0d;
        return ct * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + 30.0d;
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        return new ArrayList<>();
    }

    @Override
    public @NotNull List<String> getCoolTimeContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        double CTParkMul = TheLowTooltipModConfig.getQuickSpellTalkMultiply();
        double casterMul = MagicStoneUtil.getCasterMultiply(weaponData);

        double normalCoolTime = 220.0d * CTParkMul * casterMul + 30.0d;
        double priestCoolTime = 132.0d * CTParkMul * casterMul + 30.0d;

        result.add("§3[クールタイム]");
        result.add(String.format(StringUtils.repeat(' ', 2) + "§7通常:§e%1$s秒", JavaUtil.digitRound(normalCoolTime, 2.0d)));
        result.add(String.format(StringUtils.repeat(' ', 2) + "§7プリースト:§e%1$s秒", JavaUtil.digitRound(priestCoolTime, 2.0d)));

        return result;
    }
}
