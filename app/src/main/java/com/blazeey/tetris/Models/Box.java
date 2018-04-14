package com.blazeey.tetris.Models;

import com.blazeey.tetris.R;

import java.util.HashMap;
import java.util.Map;

public class Box {

    int colorList[] = {R.color.white,
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
            R.color.color10,
            R.color.color11,
            R.color.color12,
            R.color.color13,
            R.color.color14,
            R.color.color15,
            R.color.color16,
            R.color.color17,
            R.color.color18,
            R.color.color19,
            R.color.color20};

    private int id, backgroundColor, block;

    public Box(int id, int block) {
        this.id = id;
        this.backgroundColor = colorList[block];
        this.block = block;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
        this.backgroundColor = colorList[block];
    }

    /**
     * Method to get the initial configuration
     * @return a map of 10x6 with each <Box>Box</Box> details
     */
    public static Map<Integer,Box> initialConfiguration() {
        Map<Integer,Box> boxes = new HashMap<>();
        boxes.put(1,new Box(1, 0));
        boxes.put(2,new Box(2, 1));
        boxes.put(3,new Box(3, 1));
        boxes.put(4,new Box(4, 0));
        boxes.put(5,new Box(5, 0));
        boxes.put(6,new Box(6, 0));
        boxes.put(7,new Box(7, 0));
        boxes.put(8,new Box(8, 1));
        boxes.put(9,new Box(9, 2));
        boxes.put(10,new Box(10, 2));
        boxes.put(11,new Box(11, 0));
        boxes.put(12,new Box(12, 0));
        boxes.put(13,new Box(13, 0));
        boxes.put(14,new Box(14, 1));
        boxes.put(15,new Box(15, 1));
        boxes.put(16,new Box(16, 2));
        boxes.put(17,new Box(17, 3));
        boxes.put(18,new Box(18, 0));
        boxes.put(19,new Box(19, 17));
        boxes.put(20,new Box(20, 0));
        boxes.put(21,new Box(21, 2));
        boxes.put(22,new Box(22, 2));
        boxes.put(23,new Box(23, 4));
        boxes.put(24,new Box(24, 0));
        boxes.put(25,new Box(25, 17));
        boxes.put(26,new Box(26, 5));
        boxes.put(27,new Box(27, 5));
        boxes.put(28,new Box(28, 5));
        boxes.put(29,new Box(29, 6));
        boxes.put(30,new Box(30, 0));
        boxes.put(31,new Box(31, 17));
        boxes.put(32,new Box(32, 0));
        boxes.put(33,new Box(33, 0));
        boxes.put(34,new Box(34, 8));
        boxes.put(35,new Box(35, 7));
        boxes.put(36,new Box(36, 0));
        boxes.put(37,new Box(37, 17));
        boxes.put(38,new Box(38, 9));
        boxes.put(39,new Box(39, 8));
        boxes.put(40,new Box(40, 8));
        boxes.put(41,new Box(41, 10));
        boxes.put(42,new Box(42, 10));
        boxes.put(43,new Box(43, 17));
        boxes.put(44,new Box(44, 11));
        boxes.put(45,new Box(45, 12));
        boxes.put(46,new Box(46, 12));
        boxes.put(47,new Box(47, 12));
        boxes.put(48,new Box(48, 13));
        boxes.put(49,new Box(49, 17));
        boxes.put(50,new Box(50, 15));
        boxes.put(51,new Box(51, 12));
        boxes.put(52,new Box(52, 14));
        boxes.put(53,new Box(53, 0));
        boxes.put(54,new Box(54, 16));
        boxes.put(55,new Box(55, 17));
        boxes.put(56,new Box(56, 12));
        boxes.put(57,new Box(57, 12));
        boxes.put(58,new Box(58, 12));
        boxes.put(59,new Box(59, 18));
        boxes.put(60,new Box(60, 19));

        return boxes;
    }
}
