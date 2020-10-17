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

package matteroverdrive.blocks;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.blocks.includes.MOBlockMachine;
import matteroverdrive.machines.analyzer.TileEntityMachineMatterAnalyzer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockMatterAnalyzer extends MOBlockMachine<TileEntityMachineMatterAnalyzer> {
    public static float MACHINE_VOLUME;

    private static boolean keepInventory;

    public static final PropertyBool RUNNING = PropertyBool.create("running");

    public BlockMatterAnalyzer(Material material, String name) {
        super(material, name);
        setHasRotation();
        setHardness(20.0F);
        this.setResistance(5.0f);
        this.setHarvestLevel("pickaxe", 2);
        this.setDefaultState(getBlockState().getBaseState().withProperty(RUNNING, false).withProperty(PROPERTY_DIRECTION, EnumFacing.NORTH));
        setHasGui(true);
    }

    @Override
    public Class<TileEntityMachineMatterAnalyzer> getTileEntityClass() {
        return TileEntityMachineMatterAnalyzer.class;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityMachineMatterAnalyzer();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PROPERTY_DIRECTION, RUNNING);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        IBlockState blockState = worldIn.getBlockState(pos);

        worldIn.setBlockState(pos, blockState.withProperty(RUNNING, false));
    }

    public static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (active) {
            worldIn.setBlockState(
                pos,
                MatterOverdrive.BLOCKS.matter_analyzer.getDefaultState()
                    .withProperty(PROPERTY_DIRECTION, state.getValue(PROPERTY_DIRECTION))
                    .withProperty(RUNNING, true),
                3
            );
        } else {
            worldIn.setBlockState(
                    pos,
                    MatterOverdrive.BLOCKS.matter_analyzer.getDefaultState()
                        .withProperty(PROPERTY_DIRECTION, state.getValue(PROPERTY_DIRECTION))
                        .withProperty(RUNNING, false),
                    3
            );
        }

        if (tileEntity != null) {
            tileEntity.validate();

            worldIn.setTileEntity(pos, tileEntity);
        }
    }
}
