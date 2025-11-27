package survivalblock.tmm_conductor.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import survivalblock.tmm_conductor.common.init.TMMConductorBlockEntities;

public class BroadcastButtonBlockEntity extends BlockEntity {

    public BroadcastButtonBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public BroadcastButtonBlockEntity(BlockPos pos, BlockState state) {
        this(TMMConductorBlockEntities.BROADCAST_BUTTON, pos, state);
    }
}
