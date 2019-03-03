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

package matteroverdrive.util;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;

public class MOEnumHelper {
    public static <E extends Enum<E>> int encode(EnumSet<E> set) {
        int ret = 0;

        for (E val : set) {
            ret |= 1 << val.ordinal();
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> EnumSet<E> decode(int code, Class<E> enumType) {
        try {
            E[] values = (E[]) enumType.getMethod("values").invoke(null);
            EnumSet<E> result = EnumSet.noneOf(enumType);
            while (code != 0) {
                int ordinal = Integer.numberOfTrailingZeros(code);
                code ^= Integer.lowestOneBit(code);
                result.add(values[ordinal]);
            }
            return result;
        } catch (IllegalAccessException | NoSuchMethodException ex) {
            // Shouldn't happen
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            // Probably a NullPointerException, caused by calling this method
            // from within E's initializer.
            throw (RuntimeException) ex.getCause();
        }
    }
}
