package com.abbyy.task01.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abbyy.task01.R;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryVH> {
    private ArrayList<String> history;

    public HistoryAdapter(ArrayList<String> history) {
        this.history = history;
    }

    public void clearHistory() {
        history.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new HistoryVH(inflater.inflate(R.layout.cell_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryVH holder, int position) {
        holder.bind(history.get(position));
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    class HistoryVH extends RecyclerView.ViewHolder {

        private TextView title;

        HistoryVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_history_text);
        }

        void bind(String historyItem) {
            title.setText(historyItem);
        }
    }
}
