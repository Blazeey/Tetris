package com.blazeey.tetris.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.blazeey.tetris.Adapters.GridAdapter;
import com.blazeey.tetris.Models.Block;
import com.blazeey.tetris.Models.Box;
import com.blazeey.tetris.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GridAdapter.BoxTouchListener{

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    GridAdapter gridAdapter;

    Context context;
    Map<Integer,Box> boxMap;
    List<Block> blockList;
    GridAdapter.BoxTouchListener listener;

    private static final int TEMP_BLOCK = 477;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        listener = this;

        recyclerView = findViewById(R.id.grid);
        gridLayoutManager = new GridLayoutManager(context,6,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        boxMap = Box.initialConfiguration();
        blockList = Block.initialConfiguration();
        gridAdapter = new GridAdapter(this,boxMap,listener);
        recyclerView.setAdapter(gridAdapter);

//        remove(8);
//        remove(5);
//        isMovable(blockList.get(4));
//        isMovable(blockList.get(0));
//        isMovable(blockList.get(0));
//        isMovable(blockList.get(0));
//        isMovable(blockList.get(0));

    }

    void move(Block block){

//        if(block.getBlock()==1)
//            return;
        Log.v("move",block.getBlock()+"");
        List<Integer> boxes = block.getBoxList();
        Collections.sort(boxes);
        Collections.reverse(boxes);
        for(int i:boxes){
//            Log.v("Move",i+"");
            //Change box
            Box newBox = boxMap.get(i+6);
            Box currentBox = boxMap.get(i);
            if(currentBox.getBlock()==0) {
                Log.v("Moveeee", "Curr : " + i);
                return;
            }
            Block blockInGrid = blockList.get(currentBox.getBlock()-1);

            currentBox.setBlock(0);
            newBox.setBlock(blockInGrid.getBlock());
            boxMap.remove(i);
            boxMap.remove(i+6);
            boxMap.put(i,currentBox);
            boxMap.put(i+6,newBox);

            List<Integer> boxesInBlock = new ArrayList<>(blockInGrid.getBoxList());
            int index = boxesInBlock.indexOf(i);
            boxesInBlock.remove(index);
            boxesInBlock.add(i+6);

            blockList.remove(blockInGrid.getBlock()-1);
            Block newBlock = new Block(blockInGrid.getBlock(),boxesInBlock);
            blockList.add(blockInGrid.getBlock()-1,newBlock);

        }

        gridAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(gridAdapter);
    }

    boolean hasLastLine(Block block){
        for(int i:block.getBoxList()){
            if(i>54&&i<=60)
                return true;
        }
        return false;
    }

    boolean isMovable(Block block){

        Log.v("isMovable",block.getBlock()+"");
        if(hasLastLine(block)){
            return false;
        }
//        Block block = blockList.get(blockNum-1);
        List<Block> fuseBlock = new ArrayList<>();
        boolean isAllMovable = true;
        for (int i:block.getBoxList()){
            int nextBox = i+6;
//            Log.v("NextBox",nextBox+"");
            if(nextBox>60) {
                Log.v("NextBox>60",block.getBlock()+"");
                return false;
            }
//            Log.v("Box",boxMap.get(nextBox).getBlock()+"");
            Box next = boxMap.get(nextBox);
            if(block.getBlock()==TEMP_BLOCK){
                if(!block.getBoxList().contains(nextBox) && next.getBlock()!=0){
                    fuseBlock.add(blockList.get(boxMap.get(nextBox).getBlock()-1));
                    Log.v("Fusing",boxMap.get(nextBox).getBlock()+"");
                    isAllMovable = false;
                }
            }
            else if(next.getBlock()!=0 && (next.getBlock()!=block.getBlock())) {
//                Log.v("Box","Not empty");
                if(!fuseBlock.contains(blockList.get(next.getBlock()-1))) {
                    Log.v("Fusing", boxMap.get(nextBox).getBlock() + "");
                    fuseBlock.add(blockList.get(next.getBlock() - 1));
                }
                isAllMovable = false;
            }
        }
        if(isAllMovable) {
            move(block);
            return true;
        }
        else {
            Log.v("Box","Fusing : "+block.getBlock());
            fuseBlock.add(block);
//            Log.v("Box","Size : "+fuseBlock.size());
            Block newBlock = fuseBlocks(fuseBlock);
//            Log.v("Block","Size : "+newBlock.getBoxList().size());
            if(isMovable(newBlock)) {
                Log.v("MOVE", "New block can be moved");
                return true;
            }
            else
                return false;
        }
    }

    Block fuseBlocks(List<Block> blocks){
        int count = 0;
        for(Block  i:blocks)
            for(int j:i.getBoxList())
                count++;
        int k=0;
        int[] blocksArray = new int[count];
        for(Block  i:blocks)
            for(int j:i.getBoxList())
                blocksArray[k++]=j;
        Block block = new Block(TEMP_BLOCK,blocksArray);
        return block;
    }

    void remove(int blockNum){
        for(int i:blockList.get(blockNum-1).getBoxList()){
            Box box = boxMap.get(i);
            box.setBlock(0);
            boxMap.put(box.getId(),box);
            gridAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(gridAdapter);
        }
//        blockList.remove(blockNum-1);
    }

    void moveBlocks(int blockNum){
        Log.v("moveBlocks",blockNum+"");
        Block removedBlock = blockList.get(blockNum-1);
        boolean isAnyBoxMovable = false;
        List<Block> tempBlockList = new ArrayList<>(blockList);
        Collections.reverse(tempBlockList);
//        Collections.reverse(blockList);
        for(Block block:tempBlockList){
//            Collections.reverse(blockList);
            if(block.getMinLevel()<removedBlock.getMinLevel() && block.getMaxLevel()>=removedBlock.getMaxLevel()){
                if(isMovable(block)) {
//                    move(block);
                    isAnyBoxMovable = true;
//                    tempBlockList = blockList;
                    break;
                }
            }
            else if (block.getMinLevel()<removedBlock.getMinLevel() && block.getMaxLevel()<removedBlock.getMaxLevel()){
                if(isMovable(block)) {
//                    move(block);
                    isAnyBoxMovable = true;
//                    tempBlockList = blockList;
                    break;
                }
            }
            else if(block.getMinLevel()<removedBlock.getMaxLevel()){
                if(isMovable(block)) {
//                    move(block);
                    isAnyBoxMovable = true;
//                    tempBlockList = blockList;
                    break;
                }
            }
        }
        if(isAnyBoxMovable){
            moveBlocks(blockNum);
        }
    }

    @Override
    public void onTouch(GridAdapter.GridHolder holder, int position) {
        int blockNum = boxMap.get(position+1).getBlock();
        Log.v("onTouch",blockNum+"");
        remove(blockNum);
        moveBlocks(blockNum);
    }
}
