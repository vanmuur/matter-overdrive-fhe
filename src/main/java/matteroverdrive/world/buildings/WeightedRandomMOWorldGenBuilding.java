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

package matteroverdrive.world.buildings;

import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WeightedRandomMOWorldGenBuilding extends WeightedRandom.Item {
    public final MOWorldGenBuilding worldGenBuilding;

    public WeightedRandomMOWorldGenBuilding(MOWorldGenBuilding worldGenBuilding, int weight) {
        super(weight);
        this.worldGenBuilding = worldGenBuilding;
    }

    public int getWeight(Random random, World world, BlockPos pos) {
        return worldGenBuilding.shouldGenerate(random, world, pos) && worldGenBuilding.isLocationValid(world, pos) ? itemWeight : Math.max(1, (int) (itemWeight * 1));
    }
}
