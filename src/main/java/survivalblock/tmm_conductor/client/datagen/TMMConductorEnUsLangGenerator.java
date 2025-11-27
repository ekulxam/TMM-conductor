package survivalblock.tmm_conductor.client.datagen;

import dev.doctor4t.trainmurdermystery.index.TMMBlocks;
import dev.doctor4t.trainmurdermystery.index.TMMItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.tmm_conductor.common.init.TMMConductorBlocks;

import java.util.concurrent.CompletableFuture;

public class TMMConductorEnUsLangGenerator extends FabricLanguageProvider {

    public TMMConductorEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("announcement.role.conductor", "Conductor!");
        translationBuilder.add("announcement.title.conductor", "Conductors");
        translationBuilder.add("announcement.goal.conductor", "Stay safe and survive till the end of the ride.");
        translationBuilder.add("announcement.goals.conductor", "Stay safe and survive till the end of the ride.");
        translationBuilder.add("announcement.win.conductor", "Passengers Win!");
        translationBuilder.add("task.broadcast", "giving an announcement via broadcast button.");

        TMMConductorBlocks.registrar.generateLang(wrapperLookup, translationBuilder);
    }
}
