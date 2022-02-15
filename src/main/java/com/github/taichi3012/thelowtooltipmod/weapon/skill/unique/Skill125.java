package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.api.TheLowAPI;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.util.JavaUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Skill125 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return  0.0d;
        }

        double ct = TheLowAPI.getPlayerJobByUUID(player.getUniqueID().toString()).equals(JobType.PRIEST) ? 132.0d : 220.0d;
        return ct * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + 30.0d;
    }

    @Override
    public String getId() {
        return "wskill125";
    }

    @Override
    public String getSkillSetId() {
        return "32";
    }

    @Override
    public String getName(WeaponData weaponData) {
        return isActive(weaponData) ? "恵みの泉" : "";
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return getSkillSetId().equals(weaponData.getSkillSetId());
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
