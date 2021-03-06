package com.example.toshiba.prenosrobe.activities;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.toshiba.prenosrobe.util.DynamicViews;
import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.data.DriverOfferStation;
import com.example.toshiba.prenosrobe.data.OfferStatus;
import com.example.toshiba.prenosrobe.data.Station;
import com.example.toshiba.prenosrobe.data.UserVehicle;
import com.example.toshiba.prenosrobe.data.Vehicle;
import com.example.toshiba.prenosrobe.data.VehicleType;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;
import com.example.toshiba.prenosrobe.fragments.NavigationFragment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateDriverOfferActivity extends AppCompatActivity
{
    private ApiInterface apiInterface;
    private EditText inputDepLoc, inputArrLoc, inputDate, inputTime, inputVehicleNumber;
    private TextView labelDepLoc, labelArrLoc, labelDate, labelTime, labelVehicleNumber, labelVehicleType, labelText1;
    private List<VehicleType> vehicleTypes;
    private VehicleType selectedVehicleTypes;
    private UserVehicle userVehicle;
    private Date date;
    private Time time;
    private Spinner spinnerVehicleType;
    private static List<OfferStatus> offerStatuses;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private Fragment navigationFragment;
    private GridLayout gridStations;
    private Context context;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_driver_offer);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        inputDepLoc = findViewById(R.id.inputDepLoc);
        inputArrLoc = findViewById(R.id.inputArrLoc);
        inputDate = findViewById(R.id.inputDate);
        inputTime = findViewById(R.id.inputTime);
        inputVehicleNumber = findViewById(R.id.inputVehicleNumber);
        labelVehicleType = findViewById(R.id.labelVehicleType);
        spinnerVehicleType = findViewById(R.id.spinnerVehicleType);
        gridStations = findViewById(R.id.gridStations);
        buttonAdd = findViewById(R.id.buttonAdd);

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

        getInitData();
        clearAllEditTexts();
        ((NavigationFragment) navigationFragment).setectItem(R.id.action_offer);
    }

    private DriverOffer createDriverOffer()
    {
        DriverOffer newDriverOffer = new DriverOffer();
        newDriverOffer.setDepartureLocation(inputDepLoc.getText().toString());
        newDriverOffer.setArrivalLocation(inputArrLoc.getText().toString());
        newDriverOffer.setDate(date);
        newDriverOffer.setTime(time);
        newDriverOffer.setOfferStatus(offerStatuses.get(1));
        newDriverOffer.setUserVehicle(createUserVehicle());
        newDriverOffer.setDriverOfferStations(createDriverOfferStations(newDriverOffer));

        return newDriverOffer;
    }

    private UserVehicle createUserVehicle()
    {
        if (userVehicle == null || userVehicle.getId() == null)
        {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleType(selectedVehicleTypes);
            vehicle.setRegistrationNumber(inputVehicleNumber.getText().toString());

            userVehicle = new UserVehicle();
            userVehicle.setUser(RegistrationActivity.getUser());
            userVehicle.setVehicle(vehicle);
        }

        return userVehicle;
    }

    private List<DriverOfferStation> createDriverOfferStations(final DriverOffer driverOffer)
    {
        List<DriverOfferStation> driverOfferStations = new ArrayList<>();
        List<String> stationsNames = getEnteredStationsNames();
        int serialNumber = 1;

        DriverOfferStation departureDriverOfferStation = createDriverOfferStation(driverOffer.getDepartureLocation(), serialNumber);
        driverOfferStations.add(departureDriverOfferStation);
        serialNumber++;

        for (String stationName : stationsNames)
        {
            DriverOfferStation newDriverOfferStation = createDriverOfferStation(stationName, serialNumber);
            driverOfferStations.add(newDriverOfferStation);
            serialNumber++;
        }

        DriverOfferStation arrivalDriverOfferStation = createDriverOfferStation(driverOffer.getArrivalLocation(), serialNumber);
        driverOfferStations.add(arrivalDriverOfferStation);

        return driverOfferStations;
    }

    private DriverOfferStation createDriverOfferStation(final String stationName, final int serialNumber)
    {
        Station station = new Station();
        station.setName(stationName);

        DriverOfferStation driverOfferStation = new DriverOfferStation();
        driverOfferStation.setSerialNumber(serialNumber);
        driverOfferStation.setStation(station);

        return driverOfferStation;
    }

    private List<String> getEnteredStationsNames()
    {
        List<String> stations = new ArrayList<>();

        for (int i = gridStations.getChildCount(); i > 0; i -=2)
        {
            EditText editText = (EditText) gridStations.getChildAt(i - 1);
            stations.add(editText.getText().toString());
        }

        return stations;
    }

    private boolean validateNewDriverOffer(final DriverOffer newDriverOffer)
    {
        boolean isValid = true;

        if (newDriverOffer.getDepartureLocation().isEmpty())
        {
            inputDepLoc.setError(getResources().getString(R.string.labelDepLoc_empty));
            isValid = false;
        }
        if (newDriverOffer.getArrivalLocation().isEmpty())
        {
            inputArrLoc.setError(getResources().getString(R.string.labelArrLoc_empty));
            isValid = false;
        }
        if (newDriverOffer.getDate() == null)
        {
            inputDate.setError(getResources().getString(R.string.labelDate_empty));
            isValid = false;
        }
        if (newDriverOffer.getTime() == null)
        {
            inputTime.setError(getResources().getString(R.string.labelTime_empty));
            isValid = false;
        }
        if (newDriverOffer.getUserVehicle().getVehicle().getRegistrationNumber().isEmpty())
        {
            inputVehicleNumber.setError(getResources().getString(R.string.vehiclenumber_empty));
            isValid = false;
        }

        return  isValid;
    }

    private void sendDriverOffer()
    {
        DriverOffer newDriverOffer = createDriverOffer();

        if (validateNewDriverOffer(newDriverOffer))
        {
            Call<RestRespondeDto<DriverOffer>> call = apiInterface.addDriverOffer(RegistrationActivity.getUser().getToken(), newDriverOffer);
            call.enqueue(new Callback<RestRespondeDto<DriverOffer>>()
            {
                @Override
                public void onResponse(Call<RestRespondeDto<DriverOffer>> call, Response<RestRespondeDto<DriverOffer>> response)
                {
                    if(response.code() == 201)
                    {
                        Intent i = new Intent(CreateDriverOfferActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<RestRespondeDto<DriverOffer>> call, Throwable t)
                {
                    t.printStackTrace();
                }
            });
        }
    }

    private void getInitData()
    {
        // Get all vehicle types
        Call<RestRespondeDto<List<VehicleType>>> call = apiInterface.getAllVehicleTypes(RegistrationActivity.getUser().getToken());
        call.enqueue(new Callback<RestRespondeDto<List<VehicleType>>>() {
            @Override
            public void onResponse(Call<RestRespondeDto<List<VehicleType>>> call, Response<RestRespondeDto<List<VehicleType>>> response) {
                if(response.code() == 200)
                {
                    vehicleTypes = response.body().getData();
                    List<String> vehicleTypesNames = new ArrayList<>();

                    for (VehicleType vehicleType : vehicleTypes)
                        vehicleTypesNames.add(vehicleType.getName());

                    ArrayAdapter aa = new ArrayAdapter(CreateDriverOfferActivity.this, android.R.layout.simple_spinner_item, vehicleTypesNames);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerVehicleType.setAdapter(aa);
                }
            }

            @Override
            public void onFailure(Call<RestRespondeDto<List<VehicleType>>> call, Throwable t)
            {
                t.printStackTrace();
            }
        });

        // Get all offer statuses
        Call<RestRespondeDto<List<OfferStatus>>> call2 = apiInterface.getAllOfferStatuses();
        call2.enqueue(new Callback<RestRespondeDto<List<OfferStatus>>>()
        {
            @Override
            public void onResponse(Call<RestRespondeDto<List<OfferStatus>>> call, Response<RestRespondeDto<List<OfferStatus>>> response)
            {
                if(response.code() == 200)
                    offerStatuses = response.body().getData();
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
        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendDriverOffer();
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
            }
        };

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute)
            {
                time = new Time(hour, minute,0);
                inputTime.setText(hour + ":" + minute);
                inputTime.setError(null);
            }
        };

        inputDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateDriverOfferActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        inputTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Belgrade"));
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(CreateDriverOfferActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onTimeSetListener, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        inputVehicleNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count)
            {
                if (!text.toString().isEmpty())
                {
                    Call<RestRespondeDto<UserVehicle>> call = apiInterface.getUserVehicleByRegistrationNumber(RegistrationActivity.getUser().getToken(), inputVehicleNumber.getText().toString());
                    call.enqueue(new Callback<RestRespondeDto<UserVehicle>>()
                    {
                        @Override
                        public void onResponse(Call<RestRespondeDto<UserVehicle>> call, Response<RestRespondeDto<UserVehicle>> response)
                        {
                            if (response.code() == 200)
                            {
                                userVehicle = response.body().getData();
                                visualizeVehicleTypeWidgets(View.GONE);
                            }
                            else if (response.code() == 204)
                            {
                                visualizeVehicleTypeWidgets(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<RestRespondeDto<UserVehicle>> call, Throwable t)
                        {
                            t.printStackTrace();
                        }
                    });
                }
                else
                    visualizeVehicleTypeWidgets(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        spinnerVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedVehicleTypes = vehicleTypes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DynamicViews dnv = new DynamicViews(context);
                gridStations.addView(dnv.getTextView(getApplicationContext(), getResources().getString(R.string.station)),0);
                gridStations.addView(dnv.getEditText(getApplicationContext()),1);
            }
        });
    }

    private void visualizeVehicleTypeWidgets(int newView)
    {
        spinnerVehicleType.setVisibility(newView);
        labelVehicleType.setVisibility(newView);
    }

    private void clearAllEditTexts()
    {
        inputDepLoc.setText("");
        inputDepLoc.setError(null);
        inputArrLoc.setText("");
        inputArrLoc.setError(null);
        inputDate.setText("");
        inputDate.setError(null);
        inputTime.setText("");
        inputTime.setError(null);
        inputVehicleNumber.setText("");
        inputVehicleNumber.setError(null);
        gridStations.removeAllViews();

        userVehicle = null;
        time = null;
        date = null;
        if (vehicleTypes != null && vehicleTypes.size() > 0)
            selectedVehicleTypes = vehicleTypes.get(0);
    }

    public static List<OfferStatus> getOfferStatuses()
    {
        return offerStatuses;
    }

    public static void setOfferStatuses(List<OfferStatus> newOfferStatuses)
    {
        offerStatuses = newOfferStatuses;
    }
}
