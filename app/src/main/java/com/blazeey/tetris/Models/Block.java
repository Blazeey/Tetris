package com.blazeey.tetris.Models;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private int block,width,minLevel,maxLevel;
    private List<Integer> boxList;

    public Block(int block, int[] boxList) {
        this.block = block;
        this.boxList = new ArrayList();
        for (int i:boxList) this.boxList.add(i);
        calculateWidth();
        calculateLevel();
    }

    public Block(int block, List<Integer> boxList) {
        this.block = block;
        this.boxList = boxList;
        calculateWidth();
        calculateLevel();
    }

    void calculateWidth(){
        int minVal = Integer.MAX_VALUE,maxVal = Integer.MAX_VALUE;
        for (int i=0;i<boxList.size();i++){
            minVal = Math.min(boxList.get(i)%6,minVal);
            maxVal = Math.max(boxList.get(i)%6,maxVal);
        }
        width = maxVal-minVal;
    }

    void calculateLevel(){
        int minLevel = Integer.MAX_VALUE,maxLevel = Integer.MIN_VALUE;
        for(int i:boxList){
            minLevel = Math.min(minLevel,(i/6)+1);
            maxLevel = Math.max(maxLevel,(i/6)+1);
        }
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public List<Integer> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<Integer> boxList) {
        this.boxList = boxList;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static List<Block> initialConfiguration(){
        List<Block> blockList = new ArrayList<>();

        blockList.add(new Block(1,new int[]{2,3,8,14,15}));
        blockList.add(new Block(2,new int[]{9,10,16,21,22}));
        blockList.add(new Block(3,new int[]{17}));
        blockList.add(new Block(4,new int[]{23}));
        blockList.add(new Block(5,new int[]{26,27,28}));
        blockList.add(new Block(6,new int[]{29}));
        blockList.add(new Block(7,new int[]{35}));
        blockList.add(new Block(8,new int[]{34,39,40}));
        blockList.add(new Block(9,new int[]{38}));
        blockList.add(new Block(10,new int[]{41,42}));
        blockList.add(new Block(11,new int[]{44}));
        blockList.add(new Block(12,new int[]{45,46,47,51,56,57,58}));
        blockList.add(new Block(13,new int[]{48}));
        blockList.add(new Block(14,new int[]{52}));
        blockList.add(new Block(15,new int[]{50}));
        blockList.add(new Block(16,new int[]{54}));
        blockList.add(new Block(17,new int[]{19,25,31,37,43,49,55}));
        blockList.add(new Block(18,new int[]{59}));
        blockList.add(new Block(19,new int[]{60}));


        return blockList;
    }
}
