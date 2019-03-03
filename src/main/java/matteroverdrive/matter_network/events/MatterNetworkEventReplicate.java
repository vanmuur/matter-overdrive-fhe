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

package matteroverdrive.matter_network.events;

import matteroverdrive.data.matter_network.IMatterNetworkEvent;
import matteroverdrive.data.matter_network.ItemPattern;
import net.minecraft.item.ItemStack;

public class MatterNetworkEventReplicate implements IMatterNetworkEvent {
    public final ItemPattern pattern;
    public int amount;

    public MatterNetworkEventReplicate(ItemPattern itemPattern, int amount) {
        this.pattern = itemPattern;
        this.amount = amount;
    }

    public static class Request extends MatterNetworkEventReplicate {
        private boolean accepted;

        public Request(ItemPattern itemPattern, int amount) {
            super(itemPattern, amount);
        }

        public void markAccepted() {
            this.accepted = true;
        }

        public boolean isAccepted() {
            return accepted;
        }
    }

    public static class Ccomplete extends MatterNetworkEventReplicate {
        public final ItemStack itemStack;

        public Ccomplete(ItemStack itemStack, ItemPattern itemPattern, int amount) {
            super(itemPattern, amount);
            this.itemStack = itemStack;
        }
    }
}
