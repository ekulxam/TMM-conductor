package survivalblock.tmm_conductor.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import dev.doctor4t.trainmurdermystery.game.GameFunctions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.tmm_conductor.common.ConductorVoicechatPlugin;
import survivalblock.tmm_conductor.common.init.TMMConductorRoles;

@Mixin(value = GameFunctions.class, remap = false)
public class GameFunctionsMixin {

	@Inject(method= "initializeGame", at = @At("RETURN"), remap = true)
	private static void initConductor(ServerWorld serverWorld, CallbackInfo ci) {
        ConductorVoicechatPlugin.reset();
        GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(serverWorld);

        for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
            if (TMMConductorRoles.CONDUCTOR.equals(gameWorldComponent.getRole(serverPlayer))) {
                continue;
            }
            ConductorVoicechatPlugin.addReceiver(serverPlayer);
        }
    }

    @WrapMethod(method = "finalizeGame", remap = true)
    private static void resetHaunters(ServerWorld world, Operation<Void> original) {
        original.call(world);
        ConductorVoicechatPlugin.reset();
    }
}