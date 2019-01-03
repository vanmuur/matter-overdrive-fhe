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

import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.weapon.IWeaponModule;
import matteroverdrive.api.weapon.IWeaponStat;
import matteroverdrive.items.includes.MOBaseItem;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WeaponModuleBase extends MOBaseItem implements IWeaponModule {

    private Map<Integer, Map<IWeaponStat, Float>> metaStatMap = new HashMap<>();

    private int slot = -1;

    public WeaponModuleBase(String name) {
        super(name);
        setCreativeTab(MatterOverdrive.TAB_OVERDRIVE_MODULES);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
    }

    public void applySlot(int slot) {
        this.slot = slot;
    }

    public void applyWeaponStat(int meta, IWeaponStat stat, float value) {
        if (!metaStatMap.containsKey(meta)) {
            metaStatMap.put(meta, new HashMap<>());
        }
        Map<IWeaponStat, Float> statMap = metaStatMap.get(meta);
        statMap.put(stat, value);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public boolean hasDetails(ItemStack stack) {
        return true;
    }

    @Override
    public void addDetails(ItemStack itemstack, EntityPlayer player, @Nullable World worldIn, List<String> infos) {
        super.addDetails(itemstack, player, worldIn, infos);
        Map<IWeaponStat, Float> statMap = metaStatMap.get(itemstack.getMetadata());
        if (statMap != null && !statMap.isEmpty()) {
            statMap.forEach((stat, value) -> infos.add(MOStringHelper.weaponStatToInfo(stat, value)));
        }
        infos.add(MOStringHelper.translateToLocal("moduleslot." + getSlot(itemstack) + ".name"));
    }

    @Override
    public float modifyWeaponStat(IWeaponStat stat, ItemStack module, ItemStack weapon, float originalStat) {
        Map<IWeaponStat, Float> statMap = metaStatMap.get(module.getMetadata());
        if (statMap == null || statMap.isEmpty())
            return originalStat;
        return originalStat * statMap.getOrDefault(stat, 1f);
    }

    @Override
    public int getSlot(ItemStack module) {
        return slot;
    }
}
