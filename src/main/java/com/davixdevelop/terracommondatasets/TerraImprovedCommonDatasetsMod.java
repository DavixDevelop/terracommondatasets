package com.davixdevelop.terracommondatasets;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = TerraImprovedCommonDatasetsMod.MODID,
name = TerraImprovedCommonDatasetsMod.NAME,
version = TerraImprovedCommonDatasetsMod.VERSION,
dependencies = "required-after:terraplusplus@[0.1.519,)",
acceptableRemoteVersions = "*")
public class TerraImprovedCommonDatasetsMod {
    public static final String MODID = "terracommondatasets";
    public static final String NAME = "Terra++: Improved Common Datasets Addon";
    public static final String VERSION = "O.2";

    public static Logger LOGGER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        LOGGER = event.getModLog();
        MinecraftForge.TERRAIN_GEN_BUS.register(new CommonDatasetHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event){

    }
}