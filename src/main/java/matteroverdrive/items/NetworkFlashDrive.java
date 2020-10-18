/*
 * This file is part of MatterOverdrive: Legacy Edition
 * Copyright (C) 2019, Horizon Studio <contact@hrznstudio.com>, All rights reserved.
 *
 * MatterOverdrive: Legacy Edition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MatterOverdrive: Legacy Edition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Matter Overdrive.  If not, see <http://www.gnu.org/licenses>.
 */

package matteroverdrive.items;

import matteroverdrive.api.matter_network.IMatterNetworkConnection;
import matteroverdrive.api.network.IMatterNetworkFilter;
import matteroverdrive.items.includes.MOBaseItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class NetworkFlashDrive extends MOBaseItem implements IMatterNetworkFilter {

    public NetworkFlashDrive(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addDetails(ItemStack itemstack, EntityPlayer player, @Nullable World worldIn, List<String> infos) {
        super.addDetails(itemstack, player, worldIn, infos);

//        if (itemstack.hasTagCompound()) {
//            NBTTagList list = itemstack.getTagCompound().getTagList(IMatterNetworkFilter.CONNECTIONS_TAG, Constants.NBT.TAG_LONG);
//            for (int i = 0; i < list.tagCount(); i++) {
//                BlockPos pos = BlockPos.fromLong(((NBTTagLong) list.get(i)).getLong());
//                IBlockState block = player.world.getBlockState(pos);
//
//                player.sendMessage(new TextComponentString("The address on the flash drive is: [" + pos.toString() + "] " + block.getBlock().getLocalizedName()));
//                System.out.printf("The address on the flash drive is: [%s] %s", pos.toString(), block.getBlock().getLocalizedName());
//
//                infos.add(String.format("[%s] %s", pos.toString(), block.getBlock().getLocalizedName()));
//            }
//        }

        if (itemstack.hasTagCompound()) {
            assert(itemstack.getTagCompound() != null);

            long value = itemstack.getTagCompound().getLong(IMatterNetworkFilter.CONNECTIONS_TAG);

            BlockPos pos = BlockPos.fromLong(value);
            IBlockState block = player.world.getBlockState(pos);

            player.sendMessage(new TextComponentString("The address on the flash drive is: [" + pos.toString() + "] " + block.getBlock().getLocalizedName()));

            infos.add(String.format("[%s] %s", pos.toString(), block.getBlock().getLocalizedName()));
        }
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);

        TileEntity tileEntity = worldIn.getTileEntity(pos);

//        // Only allow shift-clicking on air.
//        if (playerIn.isSneaking()) {
//            if (!worldIn.isRemote) {
//                playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Network flash drive cleared."));
//            }
//
//            stack.setTagCompound(null);
//
//            return EnumActionResult.SUCCESS;
//        }
//
        if (!worldIn.isRemote) {
            playerIn.sendMessage(new TextComponentString("We're here."));
        }

        if (tileEntity instanceof IMatterNetworkConnection) {
//            BlockPos connectionPosition = tileEntity.getPos();

            if (!worldIn.isRemote) {
                playerIn.sendMessage(new TextComponentString("We're here, too."));
            }

            if (!stack.hasTagCompound()) {
                stack.setTagCompound(new NBTTagCompound());
            }

//            boolean hasPos = false;

            stack.getTagCompound().setTag(IMatterNetworkFilter.CONNECTIONS_TAG, new NBTTagLong(pos.toLong()));

            if (!worldIn.isRemote) {
                IBlockState block = playerIn.world.getBlockState(pos);

                playerIn.sendMessage(new TextComponentString("Setting the flash drive to: " + block.getBlock().getLocalizedName()));
            }

//            BlockPos p = BlockPos.fromLong(value);
//
//            if (p.equals(connectionPosition)) {
//                hasPos = true;
//            }

//            boolean hasPos = false;
//            NBTTagList list = stack.getTagCompound().getTagList(IMatterNetworkFilter.CONNECTIONS_TAG, Constants.NBT.TAG_LONG);
//            for (int i = 0; i < list.tagCount(); i++) {
//                BlockPos p = BlockPos.fromLong(((NBTTagLong) list.get(i)).getLong());
//                if (p.equals(connectionPosition)) {
//                    hasPos = true;
//                    list.removeTag(i);
//                    break;
//                }
//            }

//            if (!hasPos) {
//                list.appendTag(new NBTTagLong(pos.toLong()));
//            }
//
//            IBlockState block = playerIn.world.getBlockState(pos);
//
//            if (!worldIn.isRemote) {
//                playerIn.sendMessage(new TextComponentString("Setting the flash drive to: " + block.getBlock().getLocalizedName()));
//            }
//
//            stack.getTagCompound().setTag(IMatterNetworkFilter.CONNECTIONS_TAG, list);

            return EnumActionResult.SUCCESS;
        } else {
            if (!worldIn.isRemote) {
                playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Network flash drive cleared."));
            }

            stack.setTagCompound(null);

            return EnumActionResult.SUCCESS;
        }

//        return EnumActionResult.FAIL;
    }

    @Override
    public NBTTagCompound getFilter(ItemStack stack) {
        return stack.getTagCompound();
    }

    public BlockPos getTargetPosition(ItemStack stack) {
//        NBTTagList list = stack.getTagCompound().getTagList(IMatterNetworkFilter.CONNECTIONS_TAG, Constants.NBT.TAG_LONG);

//        return BlockPos.fromLong(((NBTTagLong) list.get(0)).getLong());

        long value = stack.getTagCompound().getLong(IMatterNetworkFilter.CONNECTIONS_TAG);

        return BlockPos.fromLong(value);
    }

    public boolean hasTarget(ItemStack itemStack) {
        return itemStack.hasTagCompound();

//        if (!itemStack.hasTagCompound()) {
//            return false;
//        }
//
//        NBTTagList list = itemStack.getTagCompound().getTagList(IMatterNetworkFilter.CONNECTIONS_TAG, Constants.NBT.TAG_LONG);
//
//        return !list.isEmpty();

//        return itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(IMatterNetworkFilter.CONNECTIONS_TAG, Constants.NBT.TAG_LONG);
    }
}
