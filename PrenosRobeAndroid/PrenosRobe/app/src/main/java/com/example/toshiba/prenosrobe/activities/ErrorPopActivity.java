package com.example.toshiba.prenosrobe.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;

import java.util.ArrayList;
import java.util.List;

public class ErrorPopActivity extends AppCompatActivity
{
    private Button buttonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_pop);

        buttonOK = (Button) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        initErrorTextViews();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.95),(int)(height*.6)); //sirina 80% ekrana, visina 60% ekrana

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }

    private void initErrorTextViews()
    {
        RestRespondeDto<?> restRespondeDto = (RestRespondeDto<?>) getIntent().getSerializableExtra("errors");
        if (restRespondeDto != null)
        {
            List<String> errorList = restRespondeDto.getErrorList();
            List<TextView> errorTextViews = getErrorTextViews();

            int numberOfShownErrors = Math.min(errorTextViews.size(), errorList.size());
            for (int i = 0; i < numberOfShownErrors; i++)
            {
                errorTextViews.get(i).setText(errorList.get(i));
            }
        }
    }

    private List<TextView> getErrorTextViews()
    {
        List<TextView> errorTextViews = new ArrayList<>();

        errorTextViews.add((TextView) findViewById(R.id.labelError1));
        errorTextViews.add((TextView) findViewById(R.id.labelError2));
        errorTextViews.add((TextView) findViewById(R.id.labelError3));
        errorTextViews.add((TextView) findViewById(R.id.labelError4));
        errorTextViews.add((TextView) findViewById(R.id.labelError5));

        return errorTextViews;
    }
}
