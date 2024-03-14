package com.harmonised;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Util
{
    private static final Random RAND = new Random();

    public static <T> T getRandomFromList(List<T> list, Random random)
    {
        return getRandomFromList(list, () -> null, random);
    }

    public static <T> T getRandomFromList(List<T> list)
    {
        return getRandomFromList(list, () -> null);
    }

    public static <T> T getRandomFromList(List<T> list, Supplier<T> orElse)
    {
        return list.isEmpty() ? orElse.get() : list.get(getRandomInt(0, list.size() - 1));
    }

    public static <T> T getRandomFromList(List<T> list, Supplier<T> orElse, Random random)
    {
        return list.isEmpty() ? orElse.get() : list.get(getRandomInt(0, list.size() - 1, random));
    }

    public static double getRandom(double a)
    {
        return Math.random() * a;
    }

    public static double getRandom(double a, double b)
    {
        return a + Math.random() * (b - a);
    }

    public static double getRandom(double a, double b, Random random)
    {
        return a + random.nextDouble() * (b - a);
    }


    public static int getRandomInt(int a)
    {
        return getRandomInt(0, a);
    }

    public static int getRandomInt(int a, int b)
    {
        if (a > b)
    {
            int temp = b;
            b = a;
            a = temp;
        }
        return (int) (a + Math.random() * (b - a + 1));
    }

    public static int getRandomInt(int a, int b, Random random)
    {
        if (a > b)
    {
            int temp = b;
            b = a;
            a = temp;
        }
        return (int) (a + random.nextDouble() * (b - a + 1));
    }

    public static long getRandomLong(long a)
    {
        return getRandomLong(0, a);
    }

    public static long getRandomLong(long a, long b)
    {
        if (a > b)
    {
            long temp = b;
            b = a;
            a = temp;
        }
        return (long) (a + Math.random() * (b - a + 1));
    }

    public static long getRandomLong(long a, long b, Random random)
    {
        if (a > b)
        {
            long temp = b;
            b = a;
            a = temp;
        }
        return (long) (a + random.nextDouble() * (b - a + 1));
    }

    public static float getRandomFloat()
    {
        return (float) Math.random();
    }

    public static float getRandomFloat(float a)
    {
        return getRandomFloat(0, a);
    }

    public static float getRandomFloat(float a, float b)
    {
        if (a > b)
        {
            float temp = b;
            b = a;
            a = temp;
        }
        return a + RAND.nextFloat() * (b - a);
    }

    public static float getRandomFloat(float a, float b, Random random)
    {
        if (a > b)
        {
            float temp = b;
            b = a;
            a = temp;
        }
        return a + random.nextFloat() * (b - a);
    }

    public static boolean getRandomBoolean()
    {
        return Math.random() < 0.5;
    }

    public static boolean getRandomBoolean(Random random)
    {
        return random.nextBoolean();
    }

    public static NBTTagCompound makeTag()
    {
        return new NBTTagCompound();
    }

    public static NBTTagCompound makeTag(Consumer<NBTTagCompound> init)
    {
        return make(makeTag(), init);
    }

    public static NBTTagList makeListTag()
    {
        return new NBTTagList();
    }

    public static NBTTagList makeListTag(Consumer<NBTTagList> init)
    {
        return make(makeListTag(), init);
    }

    public static <T> T make(T item, Consumer<T> init)
    {
        init.accept(item);
        return item;
    }

    public static List<EntityPlayerMP> getValidPlayers(World world)
    {
        return world.getPlayers(EntityPlayerMP.class, player -> player.isAddedToWorld() && !player.isDead);
    }

    public static void print(Object message)
    {
        print(message, TextFormatting.WHITE);
    }

    public static void print(Object message, TextFormatting color)
    {
        final ITextComponent comp = new TextComponentString(message.toString()).setStyle(new Style().setColor(color));
        for (EntityPlayerMP player : VoidRecoveryServiceMod.getServer().getPlayerList().getPlayers())
        {
            player.sendStatusMessage(comp, false);
        }
    }

    public static EntityItem spawnItemStack(ItemStack itemStack, World world, Vec3d position)
    {
        final EntityItem itemEntity = new EntityItem(world, position.x, position.y, position.z, itemStack);
        world.spawnEntity(itemEntity);
        return itemEntity;
    }

    public static Vec3d getLastNonSolidAbovePos(World world, Vec3d pos)
    {
        BlockPos blockPos = new BlockPos(pos);
        Material material = world.getBlockState(blockPos).getMaterial();
        int i = 0;
        while(!material.blocksMovement() && i < 15 && !world.isOutsideBuildHeight(blockPos))
        {
            blockPos = blockPos.up();
            material = world.getBlockState(blockPos).getMaterial();
            ++i;
        }
        return new Vec3d(blockPos.getX() + 0.5, blockPos.getY() - 0.5, blockPos.getZ() + 0.5);
    }

    public static BlockPos getLastNonSolidAboveBlockPos(World world, BlockPos blockPos)
    {
        Material material = world.getBlockState(blockPos).getMaterial();
        int i = 0;
        while(!material.blocksMovement() && i < 15 && !world.isOutsideBuildHeight(blockPos))
        {
            blockPos = blockPos.up();
            material = world.getBlockState(blockPos).getMaterial();
            ++i;
        }
        return blockPos.down();
    }
}
