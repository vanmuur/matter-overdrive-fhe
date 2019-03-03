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

package matteroverdrive.machines.configs;

import net.minecraft.nbt.NBTTagCompound;

public class ConfigPropertyInteger extends ConfigPropertyAbstract {
    private final int min;
    private final int max;
    private int value;

    public ConfigPropertyInteger(String name, String unlocalizedName, int min, int max, int def) {
        super(name, unlocalizedName);
        this.min = min;
        this.max = max;
        this.value = def;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (int) value;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger(getUnlocalizedName(), value);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        value = nbt.getInteger(getUnlocalizedName());
    }

    @Override
    public Class getType() {
        return Integer.class;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
