package com.github.taichi3012.thelowtooltipmod.api;

import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class TheLowAPI {
    public static final int API_VERSION = 1;

    private static LocalDateTime lastReqTime = null;
    private static final Map<UUID, PlayerStatus> statusResponse = new HashMap<>();

    public static void requestPlayerStatus(Boolean isForce) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        if (player == null) {
            return;
        }

        if (lastReqTime == null || lastReqTime.isBefore(LocalDateTime.now().minusMinutes(1)) || isForce) {
            lastReqTime = LocalDateTime.now();
            player.sendChatMessage("/thelow_api player");
        }
    }

    public static boolean processResponse(ClientChatReceivedEvent event) throws RuntimeException {
        String message = event.message.getUnformattedText();

        if (!message.startsWith("$api") ) {
            return false;
        }

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(message.replace("$api", ""), JsonObject.class);

        if (!Objects.equals(jsonObj.get("apiType").getAsString(), "player_status")) {
            return true;
        }

        if (!Objects.equals(jsonObj.get("version").getAsInt(), API_VERSION)) {
            return true;
        }

        PlayerStatus playerStatus = gson.fromJson(jsonObj.get("response"), PlayerStatus.class);

        if (playerStatus.uuid == null) {
            return true;
        }

        playerStatus.jobType = JobType.getJobByName(playerStatus.jobName);
        statusResponse.put(playerStatus.uuid, playerStatus);

        return true;
    }

    public static PlayerStatus getPlayerStatus(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        return statusResponse.get(uuid);
    }

    public static class PlayerStatus {
        public UUID uuid;
        public String mcid;
        public int mainLevel;

        public ArtStatus swordStatus;
        public ArtStatus bowStatus;
        public ArtStatus magicStatus;

        public ClanInfo clanInfo;

        public long galions;
        public long unit;
        public String jobName;

        @Expose(serialize = false, deserialize = false)
        public JobType jobType;

        public static class ArtStatus {
            public int leve;
            public int exp;
            public int maxLevel;
            public int reincCount;
        }

        public static class ClanInfo {
            public String clanId;
            public String clanName;
            public String clanRank;
        }
    }
}
