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

package matteroverdrive.api.inventory;

/**
 * Created by Simeon on 4/11/2015.
 * All the stat that machines have, and can be changed by {@link IUpgrade}.
 */
public enum UpgradeTypes {
    /**
     * The speed of the Machine
     */
    Speed,
    /**
     * The Power Usage of the Machine
     */
    PowerUsage,
    /**
     * The Main Output of the Machine
     */
    Output,
    /**
     * The Secondary output of the machine
     */
    SecondOutput,
    /**
     * The fail rate of the machine
     */
    Fail,
    /**
     * The range of the Machine
     */
    Range,
    /**
     * The Power Storage of the machine
     */
    PowerStorage,
    /**
     * The Power Transfer speed of the machine
     */
    PowerTransfer,
    /**
     * The Matter Storage of the machine
     */
    MatterStorage,
    /**
     * The Matter Transfer Speed of the machine
     */
    MatterTransfer,
    /**
     * The Matter Usage of the machine
     */
    MatterUsage,
    Other
}
