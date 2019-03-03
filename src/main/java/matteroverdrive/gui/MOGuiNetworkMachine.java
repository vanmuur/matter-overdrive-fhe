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

package matteroverdrive.gui;

import matteroverdrive.api.matter_network.IMatterNetworkConnection;
import matteroverdrive.container.ContainerMachine;
import matteroverdrive.container.MOBaseContainer;
import matteroverdrive.gui.pages.MatterNetworkConfigPage;
import matteroverdrive.machines.MOTileEntityMachine;

public abstract class MOGuiNetworkMachine<T extends MOTileEntityMachine & IMatterNetworkConnection> extends MOGuiMachine<T> {

    public MOGuiNetworkMachine(ContainerMachine<T> container, T machine) {
        super(container, machine);
    }

    public MOGuiNetworkMachine(ContainerMachine<T> container, T machine, int width, int height) {
        super(container, machine, width, height);
    }

    public void registerPages(MOBaseContainer container, T machine) {
        super.registerPages(container, machine);
        MatterNetworkConfigPage configPage = new MatterNetworkConfigPage(this, 48, 32, xSize - 76, ySize);
        configPage.setName("Configurations");

        pages.set(1, configPage);
    }
}
