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

package matteroverdrive.gui.pages;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.container.slot.MOSlot;
import matteroverdrive.gui.MOGuiMachine;
import matteroverdrive.gui.element.*;
import matteroverdrive.gui.events.ITextHandler;
import matteroverdrive.machines.components.ComponentMatterNetworkConfigs;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.math.BlockPos;

public class MatterNetworkConfigPage extends AutoConfigPage implements ITextHandler {
    private ComponentMatterNetworkConfigs componentMatterNetworkConfigs;
    private ElementInventorySlot filterSlot;
    private MOElementTextField destinationTextField;
    private MOElementButtonScaled importButton;
    private MOElementButtonScaled clearButton;

    public MatterNetworkConfigPage(MOGuiMachine gui, int posX, int posY, int width, int height) {
        super(gui, posX, posY, width, height);

        destinationTextField = new MOElementTextField(gui, this, 4, 42, 96, 16);
        destinationTextField.setName("Destination");
        destinationTextField.setBackground(MOElementButton.HOVER_TEXTURE_DARK);
        destinationTextField.setTextOffset(4, 3);

        importButton = new MOElementButtonScaled(gui, this, 0, 28 + 18 * 2, "Import", 50, 18);
        importButton.setNormalTexture(MOElementButton.NORMAL_TEXTURE);
        importButton.setOverTexture(MOElementButton.HOVER_TEXTURE);
        importButton.setDisabledTexture(MOElementButton.HOVER_TEXTURE_DARK);
        importButton.setText(MOStringHelper.translateToLocal("gui.label.button.import"));

        clearButton = new MOElementButtonScaled(gui, this, 50, 28 + 18 * 2, "Clear", 50, 18);
        clearButton.setNormalTexture(MOElementButton.NORMAL_TEXTURE);
        clearButton.setOverTexture(MOElementButton.HOVER_TEXTURE);
        clearButton.setDisabledTexture(MOElementButton.HOVER_TEXTURE_DARK);
        clearButton.setText(MOStringHelper.translateToLocal("gui.label.button.clear"));

        this.componentMatterNetworkConfigs = gui.getMachine().getComponent(ComponentMatterNetworkConfigs.class);
        filterSlot = new ElementInventorySlot(gui, (MOSlot) machineGui.inventorySlots.getSlot(componentMatterNetworkConfigs.getDestinationFilterSlot()), 104, 37, 22, 22, "big");
    }

    @Override
    public void init() {
        super.init();
        addElement(destinationTextField);

        if (componentMatterNetworkConfigs != null) {
            if (componentMatterNetworkConfigs.getDestinationFilter() != null) {
                destinationTextField.setText(componentMatterNetworkConfigs.getDestinationFilter());
            }

            addElement(filterSlot);
            addElement(importButton);
            addElement(clearButton);

            if (destinationTextField.getText().equals("")) {
                clearButton.setEnabled(false);
            }
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
        getFontRenderer().drawString("Destination Address:", posX, posY + 28, 0xFFFFFF);
    }

    @Override
    public void textChanged(String elementName, String text, boolean typed) {
        if (elementName.equals("Destination")) {
            if (componentMatterNetworkConfigs != null) {
                componentMatterNetworkConfigs.setDestinationFilter(text);
                machineGui.getMachine().sendConfigsToServer(false);
            }
        }
    }

    @Override
    public void update(int mouseX, int mouseY, float partialTicks) {
        super.update(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleElementButtonClick(MOElementBase element, String buttonName, int mouseButton) {
        if (buttonName.equals("Import")) {
            ItemStack usb = filterSlot.getSlot().getStack();

            if (usb != null) {
                if (MatterOverdrive.ITEMS.networkFlashDrive.hasTarget(usb)) {
                    BlockPos target = MatterOverdrive.ITEMS.networkFlashDrive.getTargetPosition(usb);

                    componentMatterNetworkConfigs.setDestinationFilter(String.format("%d, %d, %d", target.getX(), target.getY(), target.getZ()));
                    machineGui.getMachine().sendConfigsToServer(false);

                    destinationTextField.setText(String.format("%d, %d, %d", target.getX(), target.getY(), target.getZ()));

//                    machine.setSelectedLocation(MatterOverdrive.ITEMS.transportFlashDrive.getTarget(usb).add(0, 1, 0), name.getText());
//                    machine.sendConfigsToServer(true);

                    clearButton.setEnabled(true);

                    updateInfo();
                }
            }
        } else if (buttonName.equals("Clear")) {
            componentMatterNetworkConfigs.setDestinationFilter("");
            machineGui.getMachine().sendConfigsToServer(false);

            destinationTextField.setText("");

            clearButton.setEnabled(false);
        }
    }

    protected void updateElementInformation() {
        ItemStack usb = filterSlot.getSlot().getStack();

        if (usb != null) {
            BlockPos target = MatterOverdrive.ITEMS.transportFlashDrive.getTarget(usb);

            boolean hasTarget = MatterOverdrive.ITEMS.transportFlashDrive.hasTarget(usb);
        }
    }
}
