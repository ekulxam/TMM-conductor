package survivalblock.tmm_conductor.common.init;

import dev.doctor4t.trainmurdermystery.api.Role;
import dev.doctor4t.trainmurdermystery.api.TMMRoles;
import dev.doctor4t.trainmurdermystery.game.GameConstants;
import survivalblock.tmm_conductor.common.TMMConductor;

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

    public static void init() {
        // NO-OP
    }
}
