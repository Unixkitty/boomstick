package com.unixkitty.boomstick;

import com.unixkitty.boomstick.init.ModRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Boomstick.MODID)
public class Boomstick
{
    // The MODID value here should match an entry in the META-INF/mods.toml file
    public static final String MODID = "boomstick";
    public static final String MODNAME = "Boomstick";

    private static final Logger LOG = LogManager.getLogger(MODNAME);

    public Boomstick()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(ModRegistry.class);

        ModRegistry.BLOCKS.register(modEventBus);
        ModRegistry.ITEMS.register(modEventBus);
        ModRegistry.ENTITIES.register(modEventBus);
    }

    public static Logger log()
    {
        return LOG;
    }
}
