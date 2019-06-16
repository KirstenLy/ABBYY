package com.abbyy.task01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abbyy.task01.R;
import com.abbyy.task01.beans.ArticleNode;

import java.util.ArrayList;


public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationVH> {
    private ArrayList<ArticleNode> body;

    public TranslationAdapter(ArrayList<ArticleNode> body) {
        this.body = body;
    }

    @NonNull
    @Override
    public TranslationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TranslationVH(inflater.inflate(R.layout.cell_translation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationVH holder, int position) {
        holder.bind(body.get(position));
    }

    @Override
    public int getItemCount() {
        return body.size();
    }

    class TranslationVH extends RecyclerView.ViewHolder {

        private TextView title;

        TranslationVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
        }

        void bind(ArticleNode node) {
            title.setText(node.getText());
        }
    }
}
