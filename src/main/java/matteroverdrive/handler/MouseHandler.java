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

package matteroverdrive.handler;

import matteroverdrive.api.weapon.IWeapon;
import matteroverdrive.gui.GuiAndroidHud;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MouseHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        if (GuiAndroidHud.showRadial) {
            GuiAndroidHud.radialDeltaX -= event.getDx() / 100D;
            GuiAndroidHud.radialDeltaY += event.getDy() / 100D;

            double mag = Math.sqrt(GuiAndroidHud.radialDeltaX * GuiAndroidHud.radialDeltaX + GuiAndroidHud.radialDeltaY * GuiAndroidHud.radialDeltaY);
            if (mag > 1.0D) {
                GuiAndroidHud.radialDeltaX /= mag;
                GuiAndroidHud.radialDeltaY /= mag;
            }
        }
        if (Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND) != null && Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IWeapon) {
            if (event.getButton() == 0 && event.isButtonstate()) {
                if (((IWeapon) Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem()).onLeftClick(Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND), Minecraft.getMinecraft().player)) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
