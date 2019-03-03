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

package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector2f;

public class TileEntityRendererMatterPipe extends TileEntityRendererPipe {
    public TileEntityRendererMatterPipe() {
        texture = new ResourceLocation(Reference.PATH_BLOCKS + "matter_pipe.png");
    }

    @Override
    protected Vector2f getCoreUV(TileEntity entity) {
        return new Vector2f(0, 0);
    }

    @Override
    protected Vector2f getSidesUV(TileEntity entity, EnumFacing dir) {
        return new Vector2f(1, 0);
    }
}
