package com.github.taichi3012.thelowtooltipmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import java.util.UUID;

public class MCPlayerUtil {

    public static UUID getPlayerUUID() {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        return player != null ? player.getUniqueID() : null;
    }
}
