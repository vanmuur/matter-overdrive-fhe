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

package matteroverdrive.client.render;

import matteroverdrive.api.android.IAndroidStatRenderRegistry;
import matteroverdrive.api.android.IBioticStat;
import matteroverdrive.api.events.MOEventRegisterAndroidStatRenderer;
import matteroverdrive.api.renderer.IBioticStatRenderer;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AndroidStatRenderRegistry implements IAndroidStatRenderRegistry {
    private final Map<Class<? extends IBioticStat>, Collection<IBioticStatRenderer>> map;

    public AndroidStatRenderRegistry() {
        map = new HashMap<>();
    }

    @Override
    public Collection<IBioticStatRenderer> getRendererCollection(Class<? extends IBioticStat> stat) {
        return map.get(stat);
    }

    @Override
    public Collection<IBioticStatRenderer> removeAllRenderersFor(Class<? extends IBioticStat> stat) {
        return map.remove(stat);
    }

    @Override
    public boolean registerRenderer(Class<? extends IBioticStat> stat, IBioticStatRenderer renderer) {
        if (!MinecraftForge.EVENT_BUS.post(new MOEventRegisterAndroidStatRenderer(stat, renderer))) {
            Collection<IBioticStatRenderer> collection = map.get(stat);
            if (collection == null) {
                collection = new ArrayList<>();
                map.put(stat, collection);
            }
            return collection.add(renderer);
        }
        return false;
    }
}
