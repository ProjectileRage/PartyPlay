package com.projectilerage.runelite.partyplay.ui;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@Data
@AllArgsConstructor
public class Padding {
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

    public int getVertical() {
        return top + bottom;
    }

    public int getHorizontal() {
        return left + right;
    }
}
