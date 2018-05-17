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

    public TextView descriptionTextView (Context context, String text)
    {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(lparams);
        textView.setTextSize(10);
        textView.setTextColor(Color.BLACK);
        textView.setMaxEms(10);
        return textView;
    }

    public EditText receivedQuantityEditText (Context context)
    {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(context);
        int id = 0;
        editText.setId(id);
        editText.setMinEms(2);
        editText.setTextColor(Color.BLACK);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        return editText;
    }
}
