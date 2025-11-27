package survivalblock.tmm_conductor.common.block;

import dev.doctor4t.trainmurdermystery.block.ElevatorButtonBlock;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import survivalblock.tmm_conductor.common.ConductorVoicechatPlugin;
import survivalblock.tmm_conductor.common.block.entity.BroadcastButtonBlockEntity;
import survivalblock.tmm_conductor.common.cca.BroadcastWorldComponent;
import survivalblock.tmm_conductor.common.init.TMMConductorRoles;

public class BroadcastButtonBlock extends ElevatorButtonBlock implements BlockEntityProvider {

    public BroadcastButtonBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BroadcastButtonBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        boolean client = world.isClient;

        if (state.get(POWERED)) {
            // if powered, deactivate announcements
            if (!client) {
                BroadcastWorldComponent broadcast = BroadcastWorldComponent.KEY.get(world);
                broadcast.setBroadcasting(false);
                if (broadcast.getAnnouncerUuid() != null) {
                    ConductorVoicechatPlugin.reset();
                    broadcast.setAnnouncerUuid(null);
                }
            }
            return ActionResult.success(client);
        }

        if (!client) {
            GameWorldComponent game = GameWorldComponent.KEY.get(world);
            if (game.isRunning() && player != null && TMMConductorRoles.CONDUCTOR.equals(game.getRole(player))) {
                BroadcastWorldComponent.KEY.get(world).setBroadcasting(true);
            }
        }

        this.powerOn(state, world, pos, player);

        return ActionResult.success(client);
    }
}
