package com.example.toshiba.prenosrobe.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.ClaimerOffer;
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.data.DriverOfferStation;
import com.example.toshiba.prenosrobe.data.OfferStatus;
import com.example.toshiba.prenosrobe.data.User;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;
import com.example.toshiba.prenosrobe.fragments.NavigationFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateClaimerOfferActivity extends AppCompatActivity
{
    private ApiInterface apiInterface;
    private Fragment navigationFragment;
    private DriverOffer driverOffer;
    private Spinner spinnerDepartureLocation, spinnerArrivalLocation;
    private Button buttonBook, buttonCancel;
    private String selectedDepartureLocation, selectedArrivalLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_claimer_offer);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        driverOffer = (DriverOffer) getIntent().getSerializableExtra("driver");

        spinnerDepartureLocation = findViewById(R.id.spinnerDepartureLocation);
        spinnerArrivalLocation = findViewById(R.id.spinnerArrivalLocation);
        buttonBook = findViewById(R.id.buttonBook);
        buttonCancel = findViewById(R.id.buttonCancel);

        getInitData();
        initActivity();
        registerListeners();

        navigationFragment = new NavigationFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.bottom_navigation, navigationFragment);
        ft.commit();

        ((NavigationFragment) navigationFragment).setectItem(R.id.action_home);
    }

    private void initActivity()
    {
        ((TextView) findViewById(R.id.labelLocations)).setText(driverOffer.getDepartureLocation() + " - " + driverOffer.getArrivalLocation());
        Date offerDate = driverOffer.getDate();
        int year = offerDate.getYear() + 1900;
        int month = offerDate.getMonth() + 1;
        ((TextView) findViewById(R.id.labelDateValue)).setText(offerDate.getDate() + "." + month + "." + year + ".");
        ((TextView) findViewById(R.id.labelTimeValue)).setText(driverOffer.getTime().toString());
        ((TextView) findViewById(R.id.labelVehicleValue)).setText(driverOffer.getUserVehicle().getVehicle().getVehicleType().getName());

        User driver = driverOffer.getUserVehicle().getUser();
        ((TextView) findViewById(R.id.labelDriverID)).setText(driver.getName() + " " + driver.getSurname());
        ((TextView) findViewById(R.id.labelDriverUsername)).setText(driver.getUsername());

        initSpinnerDepartureLocation();
        initSpinnerArrivalLocation();
    }

    private void initSpinnerDepartureLocation()
    {
        List<String>  departureLocations = new ArrayList<>();
        List<DriverOfferStation> driverOfferStations = driverOffer.getDriverOfferStations();

        for (int i = 0; i < driverOfferStations.size() - 1; i++)
        {
            departureLocations.add(driverOfferStations.get(i).getStation().getName());
        }

        ArrayAdapter adapter = new ArrayAdapter(CreateClaimerOfferActivity.this, android.R.layout.simple_spinner_item, departureLocations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartureLocation.setAdapter(adapter);
        spinnerDepartureLocation.setSelection(0);
    }

    private void initSpinnerArrivalLocation()
    {
        List<String>  arrivalLocations = new ArrayList<>();
        List<DriverOfferStation> driverOfferStations = driverOffer.getDriverOfferStations();

        for (int i = 1; i < driverOfferStations.size(); i++)
        {
            arrivalLocations.add(driverOfferStations.get(i).getStation().getName());
        }

        ArrayAdapter adapter = new ArrayAdapter(CreateClaimerOfferActivity.this, android.R.layout.simple_spinner_item, arrivalLocations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrivalLocation.setAdapter(adapter);
        spinnerArrivalLocation.setSelection(arrivalLocations.size() - 1);
    }

    private void getInitData()
    {
        // Get all offer statuses
        Call<RestRespondeDto<List<OfferStatus>>> call2 = apiInterface.getAllOfferStatuses();
        call2.enqueue(new Callback<RestRespondeDto<List<OfferStatus>>>()
        {
            @Override
            public void onResponse(Call<RestRespondeDto<List<OfferStatus>>> call, Response<RestRespondeDto<List<OfferStatus>>> response)
            {
                if(response.code() == 200)
                    CreateDriverOfferActivity.setOfferStatuses(response.body().getData());
            }

            @Override
            public void onFailure(Call<RestRespondeDto<List<OfferStatus>>> call, Throwable t)
            {
                t.printStackTrace();
            }
        });
    }

    private void registerListeners()
    {
        buttonBook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               ClaimerOffer newClaimerOffer = createClaimerOffer();
               if (validateNewClaimerOffer(newClaimerOffer))
               {
                    // TODO: send offer
               }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        spinnerDepartureLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedDepartureLocation =  driverOffer.getDriverOfferStations().get(position).getStation().getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerArrivalLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedArrivalLocation =  driverOffer.getDriverOfferStations().get(position).getStation().getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private ClaimerOffer createClaimerOffer()
    {
        ClaimerOffer newClaimerOffer = new ClaimerOffer();
        newClaimerOffer.setData(""); // TODO: add widget for setting data
        newClaimerOffer.setArrivalLocation(selectedArrivalLocation);
        newClaimerOffer.setDepartureLocation(selectedDepartureLocation);
        newClaimerOffer.setDriverOffer(driverOffer);
        newClaimerOffer.setOfferStatus(CreateDriverOfferActivity.getOfferStatuses().get(1));
        newClaimerOffer.setUser(RegistrationActivity.getUser());

        return newClaimerOffer;
    }

    private boolean validateNewClaimerOffer(final ClaimerOffer newClaimerOffer)
    {
        boolean isValid = true;

        // TODO: add logic
        return false;
    }
}
