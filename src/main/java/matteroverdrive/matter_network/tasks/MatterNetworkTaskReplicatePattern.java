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

package matteroverdrive.matter_network.tasks;

import matteroverdrive.api.network.MatterNetworkTask;
import matteroverdrive.data.matter_network.ItemPattern;
import matteroverdrive.util.MOStringHelper;
import matteroverdrive.util.MatterHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MatterNetworkTaskReplicatePattern extends MatterNetworkTask {
    ItemPattern pattern;
    int amount;
    long destination;
    String message;

    public MatterNetworkTaskReplicatePattern() {
        super();
        pattern = new ItemPattern();
    }

    public MatterNetworkTaskReplicatePattern(short itemID, short itemMetadata, byte amount, long destination) {
        pattern = new ItemPattern(itemID, itemMetadata);
        this.amount = amount;
        this.destination = destination;
    }

    public MatterNetworkTaskReplicatePattern(short itemID, short itemMetadata, byte amount) {
        this(itemID, itemMetadata, amount, -1);
    }

    public MatterNetworkTaskReplicatePattern(ItemPattern pattern, int amount, long destination, String message) {
        this.pattern = pattern;
        this.amount = amount;
        this.destination = destination;
        this.message = message;

        System.out.println("Setting message to: " + this.message);
    }

    public MatterNetworkTaskReplicatePattern(ItemPattern pattern, int amount, long destination) {
        this.pattern = pattern;
        this.amount = amount;
        this.destination = destination;
    }

    public MatterNetworkTaskReplicatePattern(ItemPattern pattern, int amount) {
        this(pattern, amount, -1);
    }

    @Override
    protected void init() {
        setUnlocalizedName("replicate_pattern");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound != null) {
            pattern.readFromNBT(compound.getCompoundTag("Pattern"));
            amount = compound.getShort("amount");
            destination = compound.getLong("destination");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setShort("amount", (short) amount);
        compound.setLong("destination", (long) destination);
        if (compound != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            pattern.writeToNBT(tagCompound);
            compound.setTag("Pattern", tagCompound);
        }
    }


    @Override
    public String getName() {
        return String.format("[%s] %s", amount, MOStringHelper.translateToLocal(pattern.getItem().getTranslationKey() + ".name"));
    }

    @Override
    public void addInfo(List<String> list) {
        super.addInfo(list);

        list.add("OK, we're here.");

        if (message != null) {
            list.add("");
            list.add(message);
        }
    }

    public ItemPattern getPattern() {
        return pattern;
    }

    public boolean isValid(World world) {
        if (!super.isValid(world)) {
            return false;
        }

        return MatterHelper.getMatterAmountFromItem(pattern.toItemStack(false)) > 0;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getDestination() {
        return this.destination;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    public BlockPos getDestinationBlockPos() {
        return BlockPos.fromLong(this.destination);
    }

    public boolean hasDestination() {
        return this.destination != -1;
    }

}
