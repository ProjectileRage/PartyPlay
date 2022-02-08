package com.projectilerage.runelite.partyplay;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@Data
@AllArgsConstructor
class Padding {
    private int top;
    private int bottom;
    private int left;
    private int right;

    Padding(Dimension dimension) {
        int indivHeight = dimension.height / 2;
        int indivWidth = dimension.width / 2;
        top = indivHeight;
        bottom = indivHeight;
        left = indivWidth;
        right = indivWidth;
    }

    int getVertical() {
        return top + bottom;
    }

    int getHorizontal() {
        return left + right;
    }
}
