package com.harmonised;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VoidRecoveryServiceData extends WorldSavedData
{
    public static final String ID = VoidRecoveryServiceMod.MOD_ID + "_data";
    
    private final List<ItemStack> savedItems = new ArrayList<>();

    public VoidRecoveryServiceData()
    {
        super(ID);
    }

    @Override
    public void readFromNBT(NBTTagCompound baseDataNbt)
    {
        System.out.println("started loading " + savedItems.size());

        if(baseDataNbt.hasKey(Finals.ITEMS))
        {
            final NBTTagList itemsListNbt = baseDataNbt.getTagList(Finals.ITEMS, Constants.NBT.TAG_COMPOUND);
            for (NBTBase nbtBase : itemsListNbt)
            {
                try
                {
                    if(nbtBase instanceof NBTTagCompound)
                    {
                        final NBTTagCompound itemNbt = (NBTTagCompound) nbtBase;
                        final ItemStack itemStack = new ItemStack(itemNbt);
                        savedItems.add(itemStack);
                    }
                }
                catch(Exception e)
                {
                    VoidRecoveryServiceMod.logger.warn("Failed to load item from NBT:\n" + nbtBase);

                    e.printStackTrace();
                }
            }
        }

        System.out.println("finished loading " + savedItems.size());
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound baseDataNbt)
    {
        baseDataNbt.setTag(Finals.ITEMS, Util.makeListTag(itemsListNbt ->
        {
            int i = 0;
            for (ItemStack savedItem : savedItems)
            {
                itemsListNbt.appendTag(savedItem.writeToNBT(new NBTTagCompound()));
                ++i;
            }

            System.out.println("saved " + itemsListNbt.tagCount());
        }));

        return baseDataNbt;
    }

    public boolean hasSavedItems()
    {
        return savedItems.size() != 0;
    }

    public ItemStack getAndRemoveNextSavedItem()
    {
        final ItemStack stack = savedItems.remove(0);

        setDirty(true);
        return stack;
    }

    public void saveItem(ItemStack itemStack)
    {
        if(itemStack.isEmpty())
        {
            return;
        }

        final int stackCount = itemStack.getCount();
        for(int i = 0; i < stackCount; ++i)
        {
            final ItemStack copyStack = itemStack.copy();
            copyStack.setCount(1);
            savedItems.add(copyStack);
        }

        setDirty(true);
    }

    public static VoidRecoveryServiceData get(World world)  //Only available on Server Side, after the Server has Started.
    {
        MapStorage storage = world.getMapStorage();
        VoidRecoveryServiceData instance = (VoidRecoveryServiceData) storage.getOrLoadData(VoidRecoveryServiceData.class, ID);
        if (instance == null)
        {
            instance = new VoidRecoveryServiceData();
            storage.setData(ID, instance);
        }
        return instance;
    }
}
