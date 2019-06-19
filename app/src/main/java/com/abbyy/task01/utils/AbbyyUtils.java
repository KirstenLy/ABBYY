package com.abbyy.task01.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.abbyy.task01.R;
import com.abbyy.task01.beans.ArticleNode;

import java.util.ArrayList;

public final class AbbyyUtils {

    private AbbyyUtils() {
        // This class is not publicly instantiable
    }

    public static String TYPE_COMMENT = "Comment";
    public static String TYPE_PARAGRAPH = "Paragraph";
    public static String TYPE_TEXT = "Text";
    public static String TYPE_LIST = "List";
    public static String TYPE_LIST_ITEM = "ListItem";
    public static String TYPE_EXAMPLES = "Examples";
    public static String TYPE_EXAMPLE_ITEM = "ExampleItem";
    public static String TYPE_EXAMPLE = "Example";
    public static String TYPE_CARDREFS = "CardRefs";
    public static String TYPE_CARDREFITEM = "CardRefItem";
    public static String TYPE_CARDREF = "CardRef";
    public static String TYPE_TRANSCRIPTION = "Transcription";
    public static String TYPE_ABBREV = "Abbrev";
    public static String TYPE_CAPTION = "Caption";
    public static String TYPE_SOUND = "Sound";
    public static String TYPE_REF = "Ref";
    public static String TYPE_UNSUPPORTED = "Unsupported";

    public static boolean isNodeBelongToType(ArticleNode node, String type) {
        if (node != null && node.getNodeType() != null) {
            return node.getNodeType().equals(type);
        } else return false;
    }

    //We get body top level nodes, create own LI for each of them and fill it with it's inner nodes content.
    public static void fillTopParagraphLayout(Context context, LinearLayout container, ArrayList<ArticleNode> paragraphNodeList) {
        container.removeAllViews();

        for (ArticleNode paragraphNode : paragraphNodeList) {

            LinearLayout nodeLinearLayout = new LinearLayout(context);

            for (ArticleNode innerNode : paragraphNode.getInnerNodes()) {
                boolean isTextAdded = false;

                String innerNodeText = innerNode.getText();

                TextView nodeTextView = new TextView(context);
                ImageView nodeImageView = new ImageView(context);

                //if text is transcription we change color and add braces
                if (isNodeBelongToType(innerNode, TYPE_TRANSCRIPTION) && innerNodeText != null) {
                    nodeTextView.setTextColor(context.getResources().getColor(R.color.design_default_color_primary_dark));
                    innerNodeText = "[" + innerNodeText + ']';
                    nodeTextView.setText(innerNodeText);
                    isTextAdded = true;
                }//if node is sound - we add image
                else if (isNodeBelongToType(innerNode, TYPE_SOUND) && innerNodeText == null) {
                    nodeImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sound));
                } else if (!isNodeBelongToType(innerNode, TYPE_CARDREF)) {
                    nodeTextView.setText(innerNodeText);
                    isTextAdded = true;
                }
                nodeLinearLayout.addView(isTextAdded ? nodeTextView : nodeImageView);
            }
            container.addView(nodeLinearLayout);
        }
    }

    //Find and return all nodes with "List" type
    public static ArrayList<ArticleNode> getListTypeNodes(ArrayList<ArticleNode> body) {
        ArrayList<ArticleNode> listNodes = new ArrayList<>();

        for (int i = 0; i < body.size(); i++) {
            if (body.get(i).getNodeType().equals(AbbyyUtils.TYPE_LIST)) {
                listNodes.add(body.get(i));
            }
        }
        return listNodes;
    }
}