package com.github.taichi3012.thelowtooltipmod.util;

import java.util.Optional;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

import java.util.Optional;
import java.util.UUID;

public class MCPlayerUtil {

    public static Optional<UUID> getPlayerUUID() {
        return getPlayer().map(Entity::getUniqueID);
    }

    public static Optional<EntityPlayerSP> getPlayer() {
        return Optional.ofNullable(Minecraft.getMinecraft().thePlayer);
    }

}
