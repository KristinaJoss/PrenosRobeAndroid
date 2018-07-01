package com.example.toshiba.prenosrobe.activities;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.adapters.DriverOfferAdapter;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.dto.HomeSearchDto;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;
import com.example.toshiba.prenosrobe.fragments.NavigationFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private ApiInterface apiInterface;
    private ListView listViewHome;
    private Fragment navigationFragment;
    private EditText inputDepLoc, inputArrLoc, inputDate;
    private Date date;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Button buttonSearch, buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("JOVU - onCreate() MAIN");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        listViewHome = findViewById(R.id.ListViewHome);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.driver_offer_single_row, R.id.labelMsgListView, data); //layout koji opisuje posebni red u ListView, zatim gde da smesta upisane podatke
//        l.setAdapter(adapter);

        inputDepLoc = findViewById(R.id.editText1);
        inputArrLoc = findViewById(R.id.editText2);
        inputDate = findViewById(R.id.editText3);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonReset = findViewById(R.id.buttonReset);

        registerListeners();

        navigationFragment = new NavigationFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.bottom_navigation, navigationFragment);
        ft.commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        System.out.println("JOVU - onResume() MAIN");

        ((NavigationFragment) navigationFragment).setectItem(R.id.action_home);

        clearAllEditTexts();

        // Get all driver offers
        Call<RestRespondeDto<List<DriverOffer>>> call = apiInterface.getAllDriverOffers();
        call.enqueue(new Callback<RestRespondeDto<List<DriverOffer>>>()
        {
            @Override
            public void onResponse(Call<RestRespondeDto<List<DriverOffer>>> call, Response<RestRespondeDto<List<DriverOffer>>> response)
            {
                if(response.code() == 200)
                {
                    initListViewHome(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<RestRespondeDto<List<DriverOffer>>> call, Throwable t)
            {
                t.printStackTrace();
                ((TextView) findViewById(R.id.textView2)).setText("neeeeeee");
            }
        });
    }

    private void initListViewHome(List<DriverOffer> driverOffers)
    {
        listViewHome.setAdapter(new DriverOfferAdapter(MainActivity.this, driverOffers));

        int labelNonOffersVisibility = driverOffers.isEmpty()? View.VISIBLE : View.INVISIBLE;
        findViewById(R.id.labelNoOffers).setVisibility(labelNonOffersVisibility);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("JOVU - onPause() MAIN");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("JOVU - onDestroy() MAIN");
    }

    private void registerListeners()
    {
        inputDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                date = new Date(year - 1900, month , day);
                month++;
                inputDate.setText(day + "." + month + "." + year + ".");
                inputDate.setError(null);

                enableButtonReset();
            }
        };

        buttonReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clearAllEditTexts();
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String departureLocation = inputDepLoc.getText().toString();
                String arrivalLocation = inputArrLoc.getText().toString();

                HomeSearchDto homeSearchDto = new HomeSearchDto(departureLocation, arrivalLocation, date);
                Call<RestRespondeDto<List<DriverOffer>>> call = apiInterface.getDriverOffersByLocationAndDate(homeSearchDto);
                call.enqueue(new Callback<RestRespondeDto<List<DriverOffer>>>()
                {
                    @Override
                    public void onResponse(Call<RestRespondeDto<List<DriverOffer>>> call, Response<RestRespondeDto<List<DriverOffer>>> response)
                    {
                        if(response.code() == 200)
                        {
                            initListViewHome(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<RestRespondeDto<List<DriverOffer>>> call, Throwable t)
                    {
                        t.printStackTrace();
                        ((TextView) findViewById(R.id.textView2)).setText("neeeeeee");
                    }
                });
            }
        });

        inputDepLoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                enableButtonSearch();
                enableButtonReset();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        inputArrLoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                enableButtonSearch();
                enableButtonReset();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void enableButtonSearch()
    {
        if(!inputDepLoc.getText().toString().isEmpty() && !inputArrLoc.getText().toString().isEmpty())
        {
            buttonSearch.setEnabled(true);
        }
        else
        {
            buttonSearch.setEnabled(false);
        }
    }

    private void enableButtonReset()
    {
        if(!inputDepLoc.getText().toString().isEmpty() || !inputArrLoc.getText().toString().isEmpty() || date != null)
        {
            buttonReset.setEnabled(true);
        }
        else
        {
            buttonReset.setEnabled(false);
        }
    }

    private void clearAllEditTexts()
    {
        inputDepLoc.setText("");
        inputArrLoc.setText("");
        inputDate.setText("");
        buttonSearch.setEnabled(false);
        buttonReset.setEnabled(false);
        date = null;
    }
}
