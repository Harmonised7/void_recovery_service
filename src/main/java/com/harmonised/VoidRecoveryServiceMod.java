package com.harmonised;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = VoidRecoveryServiceMod.MOD_ID, name = VoidRecoveryServiceMod.NAME, version = VoidRecoveryServiceMod.VERSION)
public class VoidRecoveryServiceMod
{
    public static final String MOD_ID = "void_recovery_service";
    public static final String NAME = "Void Recovery Service";
    public static final String VERSION = "1.0";
    private static MinecraftServer server;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public static void serverStart(FMLServerStartingEvent event)
    {
        server = event.getServer();
    }

    public static MinecraftServer getServer()
    {
        return server;
    }
}
