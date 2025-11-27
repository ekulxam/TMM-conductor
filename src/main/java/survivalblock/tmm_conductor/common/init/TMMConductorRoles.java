package survivalblock.tmm_conductor.common.init;

import dev.doctor4t.trainmurdermystery.api.Role;
import dev.doctor4t.trainmurdermystery.api.TMMRoles;
import dev.doctor4t.trainmurdermystery.cca.PlayerMoodComponent;
import dev.doctor4t.trainmurdermystery.client.gui.RoleAnnouncementTexts;
import dev.doctor4t.trainmurdermystery.game.GameConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import survivalblock.tmm_conductor.common.TMMConductor;
import survivalblock.tmm_conductor.common.cca.BroadcastWorldComponent;

public class TMMConductorRoles {

    public static final Role CONDUCTOR = TMMRoles.registerRole(
            new Role(
                    TMMConductor.id("conductor"),
                    0x5E0DE0,
                    true,
                    false,
                    Role.MoodType.REAL,
                    GameConstants.getInTicks(0, 10),
                    true
            )
    );

    public static final RoleAnnouncementTexts.RoleAnnouncementText CONDUCTOR_TEXT = RoleAnnouncementTexts.registerRoleAnnouncementText(
            new RoleAnnouncementTexts.RoleAnnouncementText("conductor", 0x5E0DE0)
    );

    public static void init() {
        // NO-OP
    }

    public static class BroadcastTask implements PlayerMoodComponent.TrainTask {

        public static PlayerMoodComponent.Task taskType = null;

        @Override
        public boolean isFulfilled(PlayerEntity player) {
            BroadcastWorldComponent broadcast = BroadcastWorldComponent.KEY.get(player.getWorld());
            return broadcast.isBroadcasting() || broadcast.isOnCooldown();
        }

        @Override
        public String getName() {
            return "broadcast";
        }

        @Override
        public PlayerMoodComponent.Task getType() {
            return taskType;
        }

        @Override
        public NbtCompound toNbt() {
            NbtCompound nbt = new NbtCompound();
            nbt.putString("type", "broadcast");
            return nbt;
        }
    }
}
