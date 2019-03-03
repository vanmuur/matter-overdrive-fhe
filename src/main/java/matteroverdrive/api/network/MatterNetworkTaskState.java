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

package matteroverdrive.api.network;

public enum MatterNetworkTaskState {
    INVALID,
    UNKNOWN,
    WAITING,
    QUEUED,
    PROCESSING,
    FINISHED;

    public static MatterNetworkTaskState get(int ordinal) {
        return MatterNetworkTaskState.values()[ordinal];
    }

    public boolean below(MatterNetworkTaskState state) {
        return ordinal() < state.ordinal();
    }

    public boolean belowOrEqual(MatterNetworkTaskState state) {
        return ordinal() <= state.ordinal();
    }

    public boolean above(MatterNetworkTaskState state) {
        return ordinal() > state.ordinal();
    }
}
