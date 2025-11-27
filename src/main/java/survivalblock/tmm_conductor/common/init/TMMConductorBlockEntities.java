package survivalblock.tmm_conductor.common.init;

import dev.doctor4t.ratatouille.util.registrar.BlockEntityTypeRegistrar;
import dev.doctor4t.ratatouille.util.registrar.BlockRegistrar;
import dev.doctor4t.trainmurdermystery.TMM;
import dev.doctor4t.trainmurdermystery.block_entity.SprinklerBlockEntity;
import dev.doctor4t.trainmurdermystery.index.TMMBlocks;
import dev.doctor4t.trainmurdermystery.index.TMMItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.jetbrains.annotations.ApiStatus;
import survivalblock.tmm_conductor.common.TMMConductor;
import survivalblock.tmm_conductor.common.block.BroadcastButtonBlock;
import survivalblock.tmm_conductor.common.block.entity.BroadcastButtonBlockEntity;

@ApiStatus.NonExtendable
public interface TMMConductorBlockEntities {

    BlockEntityTypeRegistrar registrar = new BlockEntityTypeRegistrar(TMMConductor.MOD_ID);

    BlockEntityType<BroadcastButtonBlockEntity> BROADCAST_BUTTON = registrar.create("broadcast_button", BlockEntityType.Builder.create(BroadcastButtonBlockEntity::new, TMMConductorBlocks.BROADCAST_BUTTON));

    static void init() {
        registrar.registerEntries();
    }
}
