package survivalblock.tmm_conductor.client.datagen;

import dev.doctor4t.trainmurdermystery.datagen.TMMModelGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import survivalblock.tmm_conductor.common.init.TMMConductorBlocks;
import survivalblock.tmm_conductor.mixin.TMMModelGenAccessor;

public class TMMConductorModelGenerator extends TMMModelGen {

    public TMMConductorModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        ((TMMModelGenAccessor) this).tmm_conductor$invokeRegisterButton(blockStateModelGenerator, TMMConductorBlocks.BROADCAST_BUTTON);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
