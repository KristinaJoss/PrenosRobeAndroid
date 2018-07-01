package com.example.toshiba.prenosrobe.util;

import android.content.Context;
import android.content.res.ColorStateList;
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

    public TextView getTextView(Context context, String text, ColorStateList textColor)
    {
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(16);
        textView.setTextColor(textColor);
        textView.setMaxEms(15);
        return textView;
    }

    public TextView getTextView(Context context, String text)
    {
        return getTextView(context, text, ColorStateList.valueOf(Color.BLACK));
    }

    public EditText getEditText(Context context)
    {
        final EditText editText = new EditText(context);
        int id = 0;
        editText.setId(id);
        editText.setMinEms(10);
        editText.setTextColor(Color.BLACK);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        return editText;
    }
}
