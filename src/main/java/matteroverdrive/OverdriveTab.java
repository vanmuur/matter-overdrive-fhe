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

package matteroverdrive;

import matteroverdrive.util.MOLog;
import matteroverdrive.util.StackUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.concurrent.Callable;

public class OverdriveTab extends CreativeTabs {
    private ItemStack itemstack = ItemStack.EMPTY;
    private Callable<ItemStack> stackCallable;

    public OverdriveTab(String label, Callable<ItemStack> stackCallable) {
        super(label);
        this.stackCallable = stackCallable;
    }

    @Override
    public ItemStack createIcon() {
        return null;
    }

    @Override
    public ItemStack getIcon() {
        if (StackUtils.isNullOrEmpty(itemstack)) {
            if (stackCallable != null) {
                try {
                    itemstack = stackCallable.call();
                } catch (Exception e) {
                    MOLog.error(e.getMessage(), e);
                }
            } else {
                itemstack = new ItemStack(MatterOverdrive.ITEMS.matter_scanner);
            }
        }
        return itemstack;
    }
}