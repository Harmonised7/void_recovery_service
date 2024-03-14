package com.harmonised;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VoidRecoveryServiceWorldEventListener implements IWorldEventListener
{
    public final World world;

    public VoidRecoveryServiceWorldEventListener(World world)
    {
        this.world = world;
    }

    @Override
    public void notifyBlockUpdate(World world, BlockPos blockPos, IBlockState iBlockState, IBlockState iBlockState1, int i)
    {

    }

    @Override
    public void notifyLightSet(BlockPos blockPos)
    {

    }

    @Override
    public void markBlockRangeForRenderUpdate(int i, int i1, int i2, int i3, int i4, int i5)
    {

    }

    @Override
    public void playSoundToAllNearExcept(@Nullable EntityPlayer entityPlayer, SoundEvent soundEvent, SoundCategory soundCategory, double v, double v1, double v2, float v3, float v4)
    {

    }

    @Override
    public void playRecord(SoundEvent soundEvent, BlockPos blockPos)
    {

    }

    @Override
    public void spawnParticle(int i, boolean b, double v, double v1, double v2, double v3, double v4, double v5, int... ints)
    {

    }

    @Override
    public void spawnParticle(int i, boolean b, boolean b1, double v, double v1, double v2, double v3, double v4, double v5, int... ints)
    {

    }

    @Override
    public void onEntityAdded(Entity entity)
    {

    }

    @Override
    public void broadcastSound(int i, BlockPos blockPos, int i1)
    {

    }

    @Override
    public void playEvent(EntityPlayer entityPlayer, int i, BlockPos blockPos, int i1)
    {

    }

    @Override
    public void sendBlockBreakProgress(int i, BlockPos blockPos, int i1)
    {

    }

    @Override
    public void onEntityRemoved(Entity entity)
    {
        if(entity.world.isRemote)
        {
            return;
        }

        if(!(entity instanceof EntityItem))
        {
            return;
        }

        if(entity.posY >= -64)
        {
            return;
        }

        VoidRecoveryServiceData.get(world).saveItem(((EntityItem) entity).getItem());
    }
}
