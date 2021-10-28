package com.github.taichi3012.thelowtooltipmod.api;

import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TheLowAPI {
    private static LocalDateTime lastGetTime = null;
    private static final Map<String, JsonObject> statusResponse = new HashMap<>();

    public static void updatePlayerStatus(Boolean isForce) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        if (lastGetTime == null || lastGetTime.isBefore(LocalDateTime.now().minusMinutes(1)) || isForce) {
            lastGetTime = LocalDateTime.now();
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/thelow_api player");
        }
    }

    public static boolean processResponse(ClientChatReceivedEvent event) throws RuntimeException {
        String message = event.message.getUnformattedText();

        if (!message.startsWith("$api") ) return false;

        message = message.replace("$api", "");
        JsonObject jsonObj = new Gson().fromJson(message, JsonObject.class);

        if (!Objects.equals(jsonObj.get("apiType").getAsString(), "player_status")) return true;

        String uuid = jsonObj.get("response").getAsJsonObject().get("uuid").getAsString();
        statusResponse.put(uuid, jsonObj.get("response").getAsJsonObject());

        return true;
    }

    public static JobType getPlayerJobByUUID(String uuid) {
        if (statusResponse.size() == 0 || !statusResponse.containsKey(uuid)) return JobType.UNKNOWN_JOB;
        return JobType.getJobByName(statusResponse.get(uuid).get("jobName").getAsString());
    }
}
