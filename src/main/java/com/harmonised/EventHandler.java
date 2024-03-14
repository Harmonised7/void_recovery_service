package com.harmonised;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class EventHandler
{
    @SubscribeEvent
    public static void worldLoad(WorldEvent.Load worldLoadEvent)
    {
        worldLoadEvent.getWorld().addEventListener(new VoidRecoveryServiceWorldEventListener(worldLoadEvent.getWorld()));
    }

    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent worldTickEvent)
    {
        if(worldTickEvent.world.getWorldTime() % 3 != 0)
        {
            return;
        }

        final VoidRecoveryServiceData data = VoidRecoveryServiceData.get(worldTickEvent.world);
        if(data.hasSavedItems())
        {
            final List<EntityPlayerMP> players = Util.getValidPlayers(worldTickEvent.world);

            if(players.size() == 0)
            {
                return;
            }

            final EntityPlayerMP player = Util.getRandomFromList(players);
            final Vec3d spawnPos = Util.getLastNonSolidAbovePos(player.world, player.getPositionVector());
            Util.spawnItemStack(data.getAndRemoveNextSavedItem(), player.world, spawnPos);
            player.world.playSound(null, spawnPos.x, spawnPos.y, spawnPos.z, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1, Util.getRandomFloat(0.8f, 1.2f));

//            do
//            {
//
//            }
//            while(data.hasSavedItems());
        }
    }

    @SubscribeEvent
    public static void itemExpire(ItemExpireEvent itemExpireEvent)
    {
        VoidRecoveryServiceData.get(itemExpireEvent.getEntity().world).saveItem(itemExpireEvent.getEntityItem().getItem());
    }
}
