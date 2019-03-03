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

package matteroverdrive.guide;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class GuideElementTooltip extends GuideElementAbstract {
    ItemStack itemStack;
    List<String> lines;

    public GuideElementTooltip() {
        lines = new ArrayList<>();

    }

    @Override
    protected void loadContent(MOGuideEntry entry, Element element, int width, int height) {
        if (element.hasAttribute("item")) {
            itemStack = shortCodeToStack(decodeShortcode(element.getAttribute("item")));
        } else {
            itemStack = entry.getStackIcons()[0];
        }

        itemStack.getItem().addInformation(itemStack, Minecraft.getMinecraft().world, lines, ITooltipFlag.TooltipFlags.ADVANCED);
        this.height = lines.size() * getFontRenderer().FONT_HEIGHT;
    }

    @Override
    public void drawElement(int width, int mouseX, int mouseY) {
        for (int i = 0; i < lines.size(); i++) {
            getFontRenderer().drawString(lines.get(i), x, y + i * getFontRenderer().FONT_HEIGHT, color.getColor());
        }
    }
}
