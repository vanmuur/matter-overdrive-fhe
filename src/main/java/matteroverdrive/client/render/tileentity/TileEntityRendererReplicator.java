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

import matteroverdrive.machines.replicator.TileEntityMachineReplicator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class TileEntityRendererReplicator extends TileEntitySpecialRenderer<TileEntityMachineReplicator> {
    EntityItem itemEntity;

    @Override
    public void render(TileEntityMachineReplicator replicator, double x, double y, double z, float ticks, int destoryStage, float a) {
        if (!replicator.shouldRender())
            return;
        GlStateManager.pushMatrix();
        renderItem(replicator, x, y, z);
        GlStateManager.popMatrix();
    }

    private void renderItem(TileEntityMachineReplicator replicator, double x, double y, double z) {
        ItemStack stack = replicator.getStackInSlot(replicator.OUTPUT_SLOT_ID);
        if (!stack.isEmpty()) {
            if (itemEntity == null) {
                itemEntity = new EntityItem(replicator.getWorld(), x, y, z, stack);
            } else if (!ItemStack.areItemStacksEqual(itemEntity.getItem(), stack)) {
                itemEntity.setItem(stack);
            }

            itemEntity.hoverStart = (float) (Math.PI / 2);
            Minecraft.getMinecraft().getRenderManager().renderEntity(itemEntity, x + 0.5d, y + 0.05, z + 0.5, 0, 0, false);
        }
    }
}