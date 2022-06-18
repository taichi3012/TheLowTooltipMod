package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.WeaponSkillBase;

import java.util.Collections;
import java.util.List;

public abstract class WeaponSkillUniqueBase extends WeaponSkillBase {

    public final List<String> skillSetIds;

    protected WeaponSkillUniqueBase(String id, String name, List<String> skillSetIds) {
        super(id, name);
        this.skillSetIds = Collections.unmodifiableList(skillSetIds);
    }

    protected WeaponSkillUniqueBase(String id, String name, String skillSetId) {
        this(id, name, Collections.singletonList(skillSetId));
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return skillSetIds.contains(weaponData.getSkillSetId());
    }
}
