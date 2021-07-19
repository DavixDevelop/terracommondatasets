package com.davixdevelop.terracommondatasets;

import com.davixdevelop.terracommondatasets.builtin.ContinentsDataset;
import com.davixdevelop.terracommondatasets.builtin.KoppenClimate;
import net.buildtheearth.terraplusplus.event.InitDatasetsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonDatasetHandler {
    public static final String KEY_KOPPEN = "KEY_DAVIXDEVELOP_KOPPEN_DATASET";
    public static final String KEY_CONTINENT = "KEY_DAVIXDEVELOP_CONTINETS_DATASET";

    @SubscribeEvent
    public void datasets(InitDatasetsEvent event){
        event.register(KEY_KOPPEN, new KoppenClimate());
        event.register(KEY_CONTINENT, new ContinentsDataset());
    }
}
