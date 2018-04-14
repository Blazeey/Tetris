package com.blazeey.tetris.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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
    List<Integer> removedBlockList = new ArrayList<>();
    GridAdapter.BoxTouchListener listener;
    ImageButton refresh;

    private static final int TEMP_BLOCK = 477;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        listener = this;

        //Recycler View And Adapter
        recyclerView = findViewById(R.id.grid);
        refresh = findViewById(R.id.refresh);
        gridLayoutManager = new GridLayoutManager(context,6,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        boxMap = Box.initialConfiguration();
        blockList = Block.initialConfiguration();
        gridAdapter = new GridAdapter(this,boxMap,listener);
        recyclerView.setAdapter(gridAdapter);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boxMap = Box.initialConfiguration();
                blockList = Block.initialConfiguration();
                removedBlockList = new ArrayList<>();
                gridAdapter = new GridAdapter(context,boxMap,listener);
                recyclerView.setAdapter(gridAdapter);

            }
        });

//        remove(8);
//        remove(5);
//        isMovable(blockList.get(4));
//        isMovable(blockList.get(0));
//        isMovable(blockList.get(0));
//        isMovable(blockList.get(0));
//        isMovable(blockList.get(0));

    }

    /**
     * Moves the block downward
     * @param block Block which is to be moved
     */
    void move(Block block){

        Log.v("move",block.getBlock()+"");

        List<Integer> boxes = block.getBoxList();
        Collections.sort(boxes);
        Collections.reverse(boxes);
        for(int i:boxes){

            //Getting the box and block details
            Box newBox = boxMap.get(i+6);
            Box currentBox = boxMap.get(i);
            if(currentBox.getBlock()==0) {
                Log.v("Moveeee", "Curr : " + i);
                return;
            }
            Block blockInGrid = blockList.get(currentBox.getBlock()-1);

            //Updating the box details
            currentBox.setBlock(0);
            newBox.setBlock(blockInGrid.getBlock());
            boxMap.remove(i);
            boxMap.remove(i+6);
            boxMap.put(i,currentBox);
            boxMap.put(i+6,newBox);

            //Updating the block details
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

    /**
     * Returns if the block has boxes in the last line of the grid
     * @param block Block that needs to be checked
     * @return <tt>true</tt> if block spans last row. <ff>false</ff>if block does not span last row.
     */
    boolean hasLastLine(Block block){
        for(int i:block.getBoxList()){
            if(i>54&&i<=60)
                return true;
        }
        return false;
    }

    /**
     * Function checks if the given block can be moved downwards and moves if it can.
     * @param block Required block that needs to be checked
     * @return <tt>true</tt> if the block can be moved downwards. <ff>false</ff>if block cannot be moved.
     */
    boolean isMovable(Block block){

        Log.v("isMovable",block.getBlock()+"");
        if(hasLastLine(block)){
            return false;
        }
        List<Block> fuseBlock = new ArrayList<>();
        boolean isAllMovable = true;
        for (int i:block.getBoxList()){
            int nextBox = i+6;
            if(nextBox>60) {
                Log.v("NextBox>60",block.getBlock()+"");
                return false;
            }
            Box next = boxMap.get(nextBox);
            if(block.getBlock()==TEMP_BLOCK){
                //Check Fusing
                if(!block.getBoxList().contains(nextBox) && next.getBlock()!=0){
                    fuseBlock.add(blockList.get(boxMap.get(nextBox).getBlock()-1));
                    Log.v("Fusing",boxMap.get(nextBox).getBlock()+"");
                    isAllMovable = false;
                }
            }
            else if(next.getBlock()!=0 && (next.getBlock()!=block.getBlock())) {
                //Check Fusing
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
            //Fusing the blocks
            Log.v("Box","Fusing : "+block.getBlock());
            fuseBlock.add(block);
            Block newBlock = fuseBlocks(fuseBlock);
            if(isMovable(newBlock)) {
                Log.v("MOVE", "New block can be moved");
                return true;
            }
            else
                return false;
        }
    }

    /**
     * Merge the list of blocks to provide a single block which contains all the boxes of the blocks
     * @param blocks list of blocks which need to be merged
     * @return The block containing all the merger blocks
     */
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

    /**
     * Remove the given block from the grid.
     * @param blockNum the block number to be removed
     */
    void remove(int blockNum){
        for(int i:blockList.get(blockNum-1).getBoxList()){
            Box box = boxMap.get(i);
            box.setBlock(0);
            boxMap.put(box.getId(),box);
            gridAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(gridAdapter);
        }
    }

    /**
     * Compares all the blocks with the given block to find the blocks which are above it. Then move those blocks
     * downwards till which they cannot be moved.
     * @param blockNum block number which has been removed
     */
    void moveBlocks(int blockNum){
        Log.v("moveBlocks",blockNum+"");
        Block removedBlock = blockList.get(blockNum-1);
        boolean isAnyBoxMovable = false;
        List<Block> tempBlockList = new ArrayList<>(blockList);
        Collections.reverse(tempBlockList);
        for(Block block:tempBlockList){
            if(!removedBlockList.contains(block.getBlock())) {
                if (block.getMinLevel() < removedBlock.getMinLevel() && block.getMaxLevel() >= removedBlock.getMaxLevel()) {
                    if (isMovable(block)) {
                        isAnyBoxMovable = true;
                        break;
                    }
                } else if (block.getMinLevel() < removedBlock.getMinLevel() && block.getMaxLevel() < removedBlock.getMaxLevel()) {
                    if (isMovable(block)) {
                        isAnyBoxMovable = true;
                        break;
                    }
                } else if (block.getMinLevel() < removedBlock.getMaxLevel()) {
                    if (isMovable(block)) {
                        isAnyBoxMovable = true;
                        break;
                    }
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
        if(boxMap.get(position+1).getBlock()!=0) {
            remove(blockNum);
            removedBlockList.add(blockNum);
            moveBlocks(blockNum);
        }
    }
}
