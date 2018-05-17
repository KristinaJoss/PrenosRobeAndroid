package com.example.toshiba.prenosrobe;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class DynamicViews {

    Context ctx;

    public DynamicViews(Context ctx)
    {
        this.ctx = ctx;
    }

    public TextView descriptionStations (Context context, String text)
    {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(lparams);
        textView.setText(text);
        textView.setTextSize(16);
        textView.setTextColor(Color.BLACK);
        textView.setMaxEms(15);
        return textView;
    }

    public EditText enterStations (Context context)
    {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(context);
        int id = 0;
        editText.setId(id);
        editText.setMinEms(10);
        editText.setTextColor(Color.BLACK);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        return editText;
    }

//    public TextView stations (Context context, String text)
//    {
//        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        final TextView textView = new TextView(context);
//        textView.setLayoutParams(lparams);
//        textView.setTextColor(Color.BLACK);
//        textView.setText(text);
//        return textView;
//    }
}
