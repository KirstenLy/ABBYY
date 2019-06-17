package com.abbyy.task01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abbyy.task01.R;
import com.abbyy.task01.beans.ArticleNode;
import com.abbyy.task01.utils.AbbyyUtils;

import java.util.ArrayList;

import static com.abbyy.task01.utils.AbbyyUtils.TYPE_ABBREV;
import static com.abbyy.task01.utils.AbbyyUtils.TYPE_LIST;
import static com.abbyy.task01.utils.AbbyyUtils.TYPE_PARAGRAPH;
import static com.abbyy.task01.utils.AbbyyUtils.isNodeBelongToType;


public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationVH> {
    private ArrayList<ArticleNode> contentNode;

    public TranslationAdapter(ArrayList<ArticleNode> contentNode) {
        this.contentNode = contentNode;
    }

    @NonNull
    @Override
    public TranslationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TranslationVH(inflater.inflate(R.layout.cell_translation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationVH holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return contentNode.size();
    }

    public void setData(ArrayList<ArticleNode> articleNodes) {
        this.contentNode = articleNodes;
        notifyDataSetChanged();
    }

    public void clear() {
        contentNode.clear();
        notifyDataSetChanged();
    }

    class TranslationVH extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView position;

        TranslationVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            position = itemView.findViewById(R.id.txt_position);
        }

        void bind(int pos) {
            ArticleNode node = contentNode.get(pos);
            StringBuilder builder = new StringBuilder();

            bindCell(node, builder, 0);

            position.setText(itemView.getContext().getString(R.string.main_card_position, ++pos));
            title.setText(builder.toString());
        }

        //We will call it until we can find node with List type
        private void bindCell(ArticleNode node, StringBuilder builder, int counter) {
            ArrayList<ArticleNode> innerNodes = (ArrayList<ArticleNode>) node.getInnerNodes();

            for (ArticleNode innerNode : innerNodes) {
                if (isNodeBelongToType(innerNode, TYPE_PARAGRAPH)) {
                    setTextViewFromParagraphNode(innerNode, builder, counter);
                    counter++;
                } else if (isNodeBelongToType(innerNode, TYPE_LIST)) {
                    ArrayList<ArticleNode> itemListInnerNodeList = (ArrayList<ArticleNode>) innerNode.getItems();
                    for (ArticleNode listItemNode : itemListInnerNodeList) {
                        bindCell(listItemNode, builder, counter++);
                    }
                }
            }
        }

        private void setTextViewFromParagraphNode(ArticleNode node, StringBuilder builder, int counter) {
            //Nods with CardRef type breaking current logic, we need to ignore lines with it.
            for (int i = 0; i < node.getInnerNodes().size(); i++) {
                ArticleNode innerNode = node.getInnerNodes().get(i);
                if (isNodeBelongToType(innerNode, AbbyyUtils.TYPE_CARDREF)) {
                    return;
                }
            }
            if (counter != 0) {
                builder.append(counter).append('.').append(" ");
            }
            for (int i = 0; i < node.getInnerNodes().size(); i++) {
                ArticleNode innerNode = node.getInnerNodes().get(i);
                if (isNodeBelongToType(innerNode, AbbyyUtils.TYPE_TEXT) || isNodeBelongToType(innerNode, TYPE_ABBREV)) {
                    builder.append(innerNode.getText());
                }
            }
            builder.append('\n');
        }
    }
}
