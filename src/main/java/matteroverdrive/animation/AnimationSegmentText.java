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

package matteroverdrive.animation;

import matteroverdrive.util.MOStringHelper;

public class AnimationSegmentText extends AnimationSegment {
    private String string;
    private int animationType;

    public AnimationSegmentText(String string, int begin, int length, int animationType) {
        super(begin, length);
        this.string = string;
        this.animationType = animationType;
    }

    public AnimationSegmentText(String string, int length, int animationType) {
        this(string, 0, length, animationType);
    }

    public AnimationSegmentText setLengthPerCharacter(int length) {
        this.length = string.length() * length;
        return this;
    }

    public String getText(int time) {
        if (animationType == 1) {
            return MOStringHelper.typingAnimation(string, (time - begin), length);
        }
        return string;
    }
}
