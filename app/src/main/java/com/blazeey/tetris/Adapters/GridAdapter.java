package com.blazeey.tetris.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blazeey.tetris.Models.Box;
import com.blazeey.tetris.R;

import java.util.Map;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridHolder> {

    private Context context;
    private Map<Integer,Box> boxList;
    private BoxTouchListener listener;

    public GridAdapter(Context context, Map<Integer, Box> boxList, BoxTouchListener listener) {
        this.context = context;
        this.boxList = boxList;
        this.listener = listener;
    }

    public GridAdapter(Context context, Map<Integer,Box> boxList) {
        this.context = context;
        this.boxList = boxList;
    }

    @NonNull
    @Override
    public GridHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new GridHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridHolder holder, int position) {
        Box box = boxList.get(position+1);
        holder.linearLayout.setBackgroundColor(context.getResources().getColor(box.getBackgroundColor()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTouch(holder,holder.getAdapterPosition());
            }
        });
        if (box.getBlock() != 0)
            holder.textView.setText(String.valueOf(box.getBlock()));
    }

    @Override
    public int getItemCount() {
        return boxList.size();
    }

    public class GridHolder extends RecyclerView.ViewHolder {

        TextView textView;
        LinearLayout linearLayout;

        public GridHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            linearLayout = itemView.findViewById(R.id.back);
        }
    }

    public interface BoxTouchListener{
        public void onTouch(GridHolder holder,int position);
    }
}
