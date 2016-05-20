package com.alex.androidone;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 16-5-20.
 */
public class RecyclerViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        mDataset = new ArrayList<>();
        mDataset.add("One");
        mDataset.add("Two");
        mDataset.add("Thr");
        mDataset.add("Fou");
        mDataset.add("Fiv");
        mDataset.add("Six");
        mDataset.add("Sev");
        mDataset.add("Eig");
        mDataset.add("Nin");
        mDataset.add("Ten");
        mDataset.add("One");
        mDataset.add("Two");
        mDataset.add("Thr");
        mDataset.add("Fou");
        mDataset.add("Fiv");
        mDataset.add("Six");
        mDataset.add("Sev");
        mDataset.add("Eig");
        mDataset.add("Nin");
        mDataset.add("Ten");
        mDataset.add("One");
        mDataset.add("Two");
        mDataset.add("Thr");
        mDataset.add("Fou");
        mDataset.add("Fiv");
        mDataset.add("Six");
        mDataset.add("Sev");
        mDataset.add("Eig");
        mDataset.add("Nin");
        mDataset.add("Ten");
        mDataset.add("One");
        mDataset.add("Two");
        mDataset.add("Thr");
        mDataset.add("Fou");
        mDataset.add("Fiv");
        mDataset.add("Six");
        mDataset.add("Sev");
        mDataset.add("Eig");
        mDataset.add("Nin");
        mDataset.add("Ten");
        mDataset.add("One");
        mDataset.add("Two");
        mDataset.add("Thr");
        mDataset.add("Fou");
        mDataset.add("Fiv");
        mDataset.add("Six");
        mDataset.add("Sev");
        mDataset.add("Eig");
        mDataset.add("Nin");
        mDataset.add("Ten");
        mDataset.add("One");
        mDataset.add("Two");
        mDataset.add("Thr");
        mDataset.add("Fou");
        mDataset.add("Fiv");
        mDataset.add("Six");
        mDataset.add("Sev");
        mDataset.add("Eig");
        mDataset.add("Nin");
        mDataset.add("Ten");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // improve performance
        recyclerView.setHasFixedSize(true);

        // set layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager = new GridLayoutManager(this, 1);

//        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        // add Animation
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new RecyclerAdapter(mDataset);
        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "onItemClick # " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "onItemLongClick # " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void onClick(View view) {
        if(view.getId() == R.id.addItem) {
            mDataset.add(0, "Ran");
            mAdapter.notifyItemInserted(0);
        }
    }

    private List<String> mDataset;
    private RecyclerAdapter mAdapter;
}

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public RecyclerAdapter(List<String> mDataset) {
        this.mDataset = mDataset;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.mTextView, holder.getLayoutPosition());
            }
        });

        holder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onItemLongClick(holder.mTextView, holder.getLayoutPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            this.mTextView = (TextView) view.findViewById(R.id.textView);
        }

        public TextView mTextView;
    }

    private List<String> mDataset;
    private OnItemClickListener mOnItemClickListener;
}
