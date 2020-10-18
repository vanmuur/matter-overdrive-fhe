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

import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.api.network.MatterNetworkTaskState;
import matteroverdrive.container.MOBaseContainer;
import matteroverdrive.container.matter_network.ContainerPatternMonitor;
import matteroverdrive.data.matter_network.ItemPattern;
import matteroverdrive.data.matter_network.ItemPatternMapping;
import matteroverdrive.gui.element.*;
import matteroverdrive.gui.pages.MatterNetworkConfigPage;
import matteroverdrive.gui.pages.PageTasks;
import matteroverdrive.machines.components.ComponentMatterNetworkConfigs;
import matteroverdrive.machines.pattern_monitor.TileEntityMachinePatternMonitor;
import matteroverdrive.machines.replicator.TileEntityMachineReplicator;
import matteroverdrive.network.packet.server.pattern_monitor.PacketPatternMonitorAddRequest;
import matteroverdrive.network.packet.server.task_queue.PacketRemoveTask;
import matteroverdrive.proxy.ClientProxy;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiPatternMonitor extends MOGuiNetworkMachine<TileEntityMachinePatternMonitor> {
    MOElementButton refreshButton;
    MOElementButton requestButton;
    ElementPatternsGrid elementGrid;
    PageTasks pageTasks;
    MOElementTextField searchField;

    public GuiPatternMonitor(InventoryPlayer inventoryPlayer, TileEntityMachinePatternMonitor machine) {
        super(new ContainerPatternMonitor(inventoryPlayer, machine), machine);
        name = "pattern_monitor";
        refreshButton = new MOElementButton(this, this, 6, 45, "Refresh", 0, 0, 22, 0, 22, 22, "");
        refreshButton.setTexture(Reference.PATH_GUI_ITEM + "refresh.png", 44, 22);
        refreshButton.setToolTip(MOStringHelper.translateToLocal("gui.tooltip.button.refresh"));
        requestButton = new MOElementButton(this, this, 6, 75, "Request", 0, 0, 22, 0, 22, 22, "");
        requestButton.setTexture(Reference.PATH_GUI_ITEM + "request.png", 44, 22);
        requestButton.setToolTip(MOStringHelper.translateToLocal("gui.tooltip.button.request"));
        elementGrid = new ElementPatternsGrid(this, 48, 40, 160, 110);
        searchField = new MOElementTextField(this, 41, 26, 167, 14);

        slotsList.addElement(refreshButton);
        slotsList.addElement(requestButton);
        //elementGrid.updateStackList(((ContainerPatternMonitor)inventorySlots).getGuiPatterns());
    }

    @Override
    public void registerPages(MOBaseContainer container, TileEntityMachinePatternMonitor machine) {
        super.registerPages(container, machine);

        pageTasks = new PageTasks(this, 0, 0, xSize, ySize, machine.getTaskQueue(0));
        pageTasks.setName("Tasks");
        AddPage(pageTasks, ClientProxy.holoIcons.getIcon("page_icon_tasks"), MOStringHelper.translateToLocal("gui.tooltip.page.tasks")).setIconColor(Reference.COLOR_MATTER);
    }

    @Override
    public void initGui() {
        super.initGui();

        this.addElement(refreshButton);
        //this.addElement(requestButton);
        pages.get(0).addElement(elementGrid);
        pages.get(0).addElement(searchField);

        AddHotbarPlayerSlots(inventorySlots, this);
    }

    @Override
    public void handleElementButtonClick(MOElementBase element, String buttonName, int mouseButton) {
        super.handleElementButtonClick(element, buttonName, mouseButton);
        if (element == requestButton) {
            for (int i = 0; i < elementGrid.getElements().size(); i++) {
                if (elementGrid.getElements().get(i) instanceof ElementMonitorItemPattern) {
                    ElementMonitorItemPattern itemPattern = (ElementMonitorItemPattern) elementGrid.getElements().get(i);

                    if (itemPattern.getAmount() > 0) {
                        ItemPattern pattern = itemPattern.getPatternMapping().getItemPattern().copy();

                        ComponentMatterNetworkConfigs componentMatterNetworkConfigs = this.getMachine().getComponent(ComponentMatterNetworkConfigs.class);

                        String destFilter = componentMatterNetworkConfigs.getDestinationFilter();

                        if (destFilter == null || destFilter.equals("")) {
                            MatterOverdrive.NETWORK.sendToServer(new PacketPatternMonitorAddRequest(machine, pattern, itemPattern.getAmount()));
                        } else {
                            Scanner s = new Scanner(destFilter);

                            s.useDelimiter(", ");

                            int x = s.nextInt();
                            int y = s.nextInt();
                            int z = s.nextInt();

                            BlockPos pos = new BlockPos(x, y, z);

                            System.out.println("Sending request with position of: " + pos);

                            MatterOverdrive.NETWORK.sendToServer(new PacketPatternMonitorAddRequest(machine, pattern, itemPattern.getAmount(), pos));
                        }

                        itemPattern.setAmount(0);
                    } else {
                        itemPattern.setExpanded(false);
                    }
                }
            }
        } else if (element instanceof ElementTaskList) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger("TaskID", mouseButton);
            MatterOverdrive.NETWORK.sendToServer(new PacketRemoveTask(machine, mouseButton, (byte) 0, MatterNetworkTaskState.INVALID));
        }
    }

    @Override
    protected void updateElementInformation() {
        super.updateElementInformation();

        //if (machine.needsRefresh())
        //{
        //elementGrid.updateStackList(machine.getGuiPatterns());
        //machine.forceSearch(false);
        //

        elementGrid.setFilter(searchField.getText());
    }

    public void setPattern(ItemPatternMapping pattern) {
        elementGrid.setPattern(pattern);
    }

    public void clearPatterns(BlockPos database, int storageId) {
        elementGrid.removePatterns(database, storageId);
    }

    public void clearPatterns(BlockPos database) {
        elementGrid.removePatterns(database);
    }

    public void clearPatterns() {
        elementGrid.clearElements();
    }
}
