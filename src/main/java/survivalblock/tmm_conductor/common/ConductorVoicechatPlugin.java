package survivalblock.tmm_conductor.common;

import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.audiochannel.StaticAudioChannel;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.packets.MicrophonePacket;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import dev.doctor4t.trainmurdermystery.compat.TrainVoicePlugin;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import survivalblock.tmm_conductor.common.cca.BroadcastWorldComponent;
import survivalblock.tmm_conductor.common.init.TMMConductorRoles;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static dev.doctor4t.trainmurdermystery.compat.TrainVoicePlugin.SERVER_API;

/**
 * @see TrainVoicePlugin
 */
public class ConductorVoicechatPlugin implements VoicechatPlugin {
    public static final Identifier ID = TMMConductor.id("voicechat_plugin");
    public static final UUID BROADCAST_UUID = UUID.randomUUID();
    public static StaticAudioChannel announcementChannel;
    public static Set<ServerPlayerEntity> receivers = new HashSet<>();
    public static UUID announcerUUID;

    public static void addReceiver(ServerPlayerEntity player) {
        if (TrainVoicePlugin.isVoiceChatMissing()) {
            return;
        }

        VoicechatConnection playerConnection = SERVER_API.getConnectionOf(player.getUuid());
        if (playerConnection == null) {
            return;
        }

        if (announcementChannel == null) {
            announcementChannel = SERVER_API.createStaticAudioChannel(BROADCAST_UUID);
        }

        if (announcementChannel == null) {
            return;
        }

        receivers.add(player);
        announcementChannel.addTarget(playerConnection);
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, ConductorVoicechatPlugin::play);
    }

    public static void play(MicrophonePacketEvent event) {
        if (TrainVoicePlugin.isVoiceChatMissing()) {
            return;
        }

        VoicechatConnection playerConnection = event.getSenderConnection();
        if (playerConnection == null || !(playerConnection.getPlayer().getPlayer() instanceof ServerPlayerEntity serverPlayer)) {
            return;
        }

        if (receivers.contains(serverPlayer)) {
            return;
        }

        GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(serverPlayer.getWorld());
        if (gameWorldComponent.isRunning()) {
            if (!gameWorldComponent.isRole(serverPlayer, TMMConductorRoles.CONDUCTOR)) {
                return;
            }
        } else if (!serverPlayer.getUuid().equals(announcerUUID)) {
            return;
        }

        MicrophonePacket microphonePacket = event.getPacket();
        if (microphonePacket.getOpusEncodedData().length <= 0 || microphonePacket.isWhispering()) {
            return;
        }

        announcementChannel.send(microphonePacket);
    }

    public static void vanillaBroadcast(ServerPlayerEntity broadcaster) {
        ServerWorld serverWorld = broadcaster.getServerWorld();
        BroadcastWorldComponent.KEY.get(serverWorld).setAnnouncerUuid(broadcaster.getUuid());
        for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
            if (serverPlayer.equals(broadcaster)) {
                continue;
            }

            addReceiver(serverPlayer);
        }
    }

    public static void reset() {
        announcementChannel.clearTargets();
        receivers.clear();
    }

    @Override
    public String getPluginId() {
        return ID.toString();
    }
}
