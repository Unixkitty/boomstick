package com.unixkitty.boomstick.init;

import com.unixkitty.boomstick.Boomstick;
import com.unixkitty.boomstick.client.BoomRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Boomstick.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents
{
    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModRegistry.BOOM.get(), BoomRenderer::new);
    }

}
