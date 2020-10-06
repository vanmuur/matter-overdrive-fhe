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

package matteroverdrive.items.weapon.module;

import matteroverdrive.Reference;
import matteroverdrive.api.weapon.IWeaponScope;
import matteroverdrive.items.IAdvancedModelProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class WeaponModuleHoloSights extends WeaponModuleBase implements IWeaponScope, IAdvancedModelProvider {
    public static final String[] subItemNames = {"normal", "wide", "small"};

    public WeaponModuleHoloSights(String name) {
        super(name);
        applySlot(Reference.MODULE_SIGHTS);
        this.setHasSubtypes(true);
    }

    @Override
    public String[] getSubNames() {
        return subItemNames;
    }

//    @SideOnly(Side.CLIENT)
//    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
//        if (isInCreativeTab(tab)) {
//            for (int i = 0; i < 3; i++) {
//                subItems.add(new ItemStack(itemIn, 1, i));
//            }
//        }
//    }

    @Override
    public void getSubItems(CreativeTabs tab, List<ItemStack> subItems) {
        if (isInCreativeTab(tab)) {
            for (int i = 0; i < subItemNames.length; i++) {
                subItems.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public float getZoomAmount(ItemStack scopeStack, ItemStack weaponStack) {
        return 0.3f;
    }

    @Override
    public float getAccuracyModify(ItemStack scopeStack, ItemStack weaponStack, boolean zoomed, float originalAccuracy) {
        if (zoomed) {
            return originalAccuracy * 0.6f;
        }
        return originalAccuracy * 0.8f;
    }

    @Override
    public String getModelPath() {
        return null;
    }

    @Override
    public ResourceLocation getModelTexture(ItemStack module) {
        System.out.println("Getting texture for model texture with item damage of: " + module.getItemDamage());

        return new ResourceLocation(Reference.PATH_ELEMENTS + String.format("holo_sight_%s.png", module.getItemDamage()));
    }

    @Override
    public String getModelName(ItemStack module) {
        return null;
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    @Override
    public String getTranslationKey(ItemStack stack) {
        int i = MathHelper.clamp(stack.getItemDamage(), 0, 2);

        return super.getTranslationKey() + "." + subItemNames[i];
    }
}
