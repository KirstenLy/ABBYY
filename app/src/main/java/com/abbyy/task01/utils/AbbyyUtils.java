package com.abbyy.task01.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.abbyy.task01.R;
import com.abbyy.task01.beans.ArticleNode;

import java.util.ArrayList;

public class AbbyyUtils {
    //TODO:Simplify it if you understand API

    public static int TYPE_COMMENT = 0;
    public static int TYPE_PARAGRAPH = 1;
    public static int TYPE_TEXT = 2;
    public static int TYPE_LIST = 3;
    public static int TYPE_LIST_ITEM = 4;
    public static int TYPE_EXAMPLES = 5;
    public static int TYPE_EXAMPLE_ITEM = 6;
    public static int TYPE_EXAMPLE = 7;
    public static int TYPE_CARDREFS = 8;
    public static int TYPE_CARDREFITEM = 9;
    public static int TYPE_CARDREF = 10;
    public static int TYPE_TRANSCRIPTION = 11;
    public static int TYPE_ABBREV = 12;
    public static int TYPE_CAPTION = 13;
    public static int TYPE_SOUND = 14;
    public static int TYPE_REF = 15;
    public static int TYPE_UNSUPPORTED = 16;

    public static String TYPE_STRING_COMMENT = "Comment";
    public static String TYPE_STRING_PARAGRAPH = "Paragraph";
    public static String TYPE_STRING_TEXT = "Text";
    public static String TYPE_STRING_LIST = "List";
    public static String TYPE_STRING_LIST_ITEM = "ListItem";
    public static String TYPE_STRING_EXAMPLES = "Examples";
    public static String TYPE_STRING_EXAMPLE_ITEM = "ExampleItem";
    public static String TYPE_STRING_EXAMPLE = "Example";
    public static String TYPE_STRING_CARDREFS = "CardRefs";
    public static String TYPE_STRING_CARDREFITEM = "CardRefItem";
    public static String TYPE_STRING_CARDREF = "CardRef";
    public static String TYPE_STRING_TRANSCRIPTION = "Transcription";
    public static String TYPE_STRING_ABBREV = "Abbrev";
    public static String TYPE_STRING_CAPTION = "Caption";
    public static String TYPE_STRING_SOUND = "Sound";
    public static String TYPE_STRING_REF = "Ref";
    public static String TYPE_STRING_UNSUPPORTED = "Unsupported";

    public static int getNodeTypeValue(String nodeType) {
        switch (nodeType) {
            case "Comment":
                return TYPE_COMMENT;
            case "Paragraph":
                return TYPE_PARAGRAPH;
            case "Text":
                return TYPE_TEXT;
            case "List":
                return TYPE_LIST;
            case "ListItem":
                return TYPE_LIST_ITEM;
            case "Examples":
                return TYPE_EXAMPLES;
            case "ExampleItem":
                return TYPE_EXAMPLE_ITEM;
            case "Example":
                return TYPE_EXAMPLE;
            case "CardRefs":
                return TYPE_CARDREFS;
            case "CardRefItem":
                return TYPE_CARDREFITEM;
            case "CardRef":
                return TYPE_CARDREF;
            case "Transcription":
                return TYPE_TRANSCRIPTION;
            case "Abbrev":
                return TYPE_ABBREV;
            case "Caption":
                return TYPE_CAPTION;
            case "Sound":
                return TYPE_SOUND;
            case "Ref":
                return TYPE_REF;
            case "Unsupported":
                return TYPE_UNSUPPORTED;
        }
        return TYPE_UNSUPPORTED;
    }

    public static boolean isNodeBelongToType(ArticleNode node, String type) {
        if (node != null && node.getNodeType() != null) {
            return node.getNodeType().equals(type);
        } else return false;
    }

    public static void createParagraphString(Context context, LinearLayout container, ArticleNode paragraphNode) {
        container.removeAllViews();
        for (int i = 0; i < paragraphNode.getInnerNodes().size(); i++) {
            ArticleNode innerNode = paragraphNode.getInnerNodes().get(i);

            boolean isTextAdded = false;

            String innerNodeText = innerNode.getText();

            TextView nodeTextView = new TextView(context);
            ImageView nodeImageView = new ImageView(context);

            //if text is transcription - we change color and add braces
            if (isNodeBelongToType(innerNode, TYPE_STRING_TRANSCRIPTION) && innerNodeText != null) {
                nodeTextView.setTextColor(context.getResources().getColor(R.color.design_default_color_primary_dark));
                innerNodeText = "[" + innerNodeText + ']';
                nodeTextView.setText(innerNodeText);
                isTextAdded = true;
            }//if node is sound - we add image
            else if (isNodeBelongToType(innerNode, TYPE_STRING_SOUND) && innerNodeText == null) {
                nodeImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sound));
            } else {
                nodeTextView.setText(innerNodeText);
                isTextAdded = true;
            }

            if (isTextAdded) {
                container.addView(nodeTextView);
            } else {
                container.addView(nodeImageView);
            }
        }
    }

    //Find and return all nodes with "List" type
    public static ArrayList<ArticleNode> getListTypeNodes(ArrayList<ArticleNode> body) {
        ArrayList<ArticleNode> listNodes = new ArrayList<>();

        for (int i = 0; i < body.size(); i++) {
            if (body.get(i).getNodeType().equals(AbbyyUtils.TYPE_STRING_LIST)) {
                listNodes.add(body.get(i));
            }
        }
        return listNodes;
    }
}