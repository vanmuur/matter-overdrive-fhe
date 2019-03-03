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

package matteroverdrive.gui.config;

import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.Map;
import java.util.TreeMap;

public class EnumConfigProperty extends GuiConfigEntries.SelectValueEntry {
    public EnumConfigProperty(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
        super(owningScreen, owningEntryList, configElement, getSelectableValues(configElement));
    }

    private static Map<Object, String> getSelectableValues(IConfigElement configElement) {
        Map<Object, String> selectableValues = new TreeMap<>();

        for (int i = 0; i < configElement.getValidValues().length; i++) {
            selectableValues.put(i, configElement.getValidValues()[i]);
        }

        return selectableValues;
    }

    @Override
    public void updateValueButtonText() {
        super.updateValueButtonText();
        btnValue.displayString = configElement.getValidValues()[Integer.parseInt(getCurrentValue())];
    }
}
