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

package matteroverdrive.util;

import matteroverdrive.api.quest.QuestStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

public class MOQuestHelper {
    public static BlockPos getPosition(QuestStack questStack) {
        if (questStack.getTagCompound() != null) {
            if (questStack.getTagCompound().hasKey("pos", Constants.NBT.TAG_LONG)) {
                return BlockPos.fromLong(questStack.getTagCompound().getLong("pos"));
            } else if (questStack.getTagCompound().hasKey("pos", Constants.NBT.TAG_INT_ARRAY) && questStack.getTagCompound().getIntArray("pos").length >= 3) {
                int[] s = questStack.getTagCompound().getIntArray("pos");
                return new BlockPos(s[0], s[1], s[2]);
            }
        }
        return null;
    }
}
