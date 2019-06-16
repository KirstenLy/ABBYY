package com.abbyy.task01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abbyy.task01.R;
import com.abbyy.task01.beans.ArticleModel;
import com.abbyy.task01.beans.ArticleNode;

import timber.log.Timber;

public class TranslationBaseInfoAdapter extends RecyclerView.Adapter<TranslationBaseInfoAdapter.TranslationBaseInfoVH> {
    private ArticleNode node;

    public TranslationBaseInfoAdapter(ArticleNode node) {
        this.node = node;
    }

    @NonNull
    @Override
    public TranslationBaseInfoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TranslationBaseInfoVH(inflater.inflate(R.layout.cell_translation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationBaseInfoVH holder, int position) {
        holder.bind(node);
    }

    @Override
    public int getItemCount() {
        return node.getInnerNodes().size();
    }

    class TranslationBaseInfoVH extends RecyclerView.ViewHolder {

        private TextView title;

        TranslationBaseInfoVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
        }

        void bind(ArticleNode node) {
            title.setText(node.getText());
        }
    }
}
