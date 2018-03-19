package com.example.toshiba.prenosrobe.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.data.OfferStatus;
import com.example.toshiba.prenosrobe.data.UserVehicle;
import com.example.toshiba.prenosrobe.data.Vehicle;
import com.example.toshiba.prenosrobe.data.VehicleType;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Offer extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        inputDepLoc = (EditText) findViewById(R.id.inputDepLoc);
        inputArrLoc = (EditText) findViewById(R.id.inputArrLoc);
        inputDate = (EditText) findViewById(R.id.inputDate);
        inputTime = (EditText) findViewById(R.id.inputTime);
        inputVehicleNumber = (EditText) findViewById(R.id.inputVehicleNumber);
        labelVehicleType = (TextView) findViewById(R.id.labelVehicleType);
        spinnerVehicleType = (Spinner) findViewById(R.id.spinnerVehicleType);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month ++;
                date = new Date(year - 1900, month , day);
                inputDate.setText(day + "." + month + "." + year + ".");
            }
        };

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = new Time(hour, minute,0);
                inputTime.setText(hour + ":" + minute);
            }
        };

        spinnerVehicleType.setOnItemSelectedListener(this);
        ((Button) findViewById(R.id.buttonGo)).setOnClickListener(this);
        ((Button) findViewById(R.id.buttonProfile2)).setOnClickListener(this);
        ((Button) findViewById(R.id.buttonBack2)).setOnClickListener(this);

        getInitData();
        registerListeners();
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()) {
            case R.id.buttonGo:
                sendDriverOffer();
                break;

            case R.id.buttonProfile2:
                i = new Intent(this, Profile.class);
                startActivity(i);
                break;

            case R.id.buttonBack2:
                break;
        }

    }

    private DriverOffer createDriverOffer() {
        DriverOffer newDriverOffer = new DriverOffer();
        newDriverOffer.setDepartureLocation(inputDepLoc.getText().toString());
        newDriverOffer.setArrivalLocation(inputArrLoc.getText().toString());
        newDriverOffer.setDate(date);
        newDriverOffer.setTime(time);
        newDriverOffer.setOfferStatus(offerStatuses.get(1));
        newDriverOffer.setUserVehicle(createUserVehicle());

        return newDriverOffer;
    }

    private UserVehicle createUserVehicle(){
        if (userVehicle == null) {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleType(selectedVehicleTypes);
            vehicle.setRegistrationNumber(inputVehicleNumber.getText().toString());

            userVehicle = new UserVehicle();
            userVehicle.setUser(Registration.getUser());
            userVehicle.setVehicle(vehicle);
        }

        return userVehicle;
    }

    private void sendDriverOffer()
    {
        DriverOffer newDriverOffer = createDriverOffer();

        Call<DriverOffer> call = apiInterface.addDriverOffer(Registration.getUser().getToken(), newDriverOffer);
        call.enqueue(new Callback<DriverOffer>() {
            @Override
            public void onResponse(Call<DriverOffer> call, Response<DriverOffer> response) {
                if(response.code() == 200){
                    Intent i = i = new Intent(Offer.this, Home.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<DriverOffer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
      // Toast.makeText(getApplicationContext(),vehicleTypes.get(i).getName(), Toast.LENGTH_LONG).show();
        selectedVehicleTypes = vehicleTypes.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static List<OfferStatus> getOfferStatuses() { return offerStatuses; }

    private void getInitData()
    {
        // Get all vehicle types
        Call<List<VehicleType>> call = apiInterface.getAllVehicleTypes(Registration.getUser().getToken());
        call.enqueue(new Callback<List<VehicleType>>() {
            @Override
            public void onResponse(Call<List<VehicleType>> call, Response<List<VehicleType>> response) {
                if(response.code() == 200){
                    vehicleTypes = response.body();
                    List<String> vehicleTypesNames = new ArrayList<>();

                    for (VehicleType vehicleType : vehicleTypes)
                        vehicleTypesNames.add(vehicleType.getName());

                    ArrayAdapter aa = new ArrayAdapter(Offer.this, android.R.layout.simple_spinner_item, vehicleTypesNames);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerVehicleType.setAdapter(aa);
                }
            }

            @Override
            public void onFailure(Call<List<VehicleType>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // Get all offer statuses
        Call<List<OfferStatus>> call2 = apiInterface.getAllOfferStatuses();
        call2.enqueue(new Callback<List<OfferStatus>>() {
            @Override
            public void onResponse(Call<List<OfferStatus>> call, Response<List<OfferStatus>> response) {
                if(response.code() == 200)
                    offerStatuses = response.body();
            }

            @Override
            public void onFailure(Call<List<OfferStatus>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void registerListeners()
    {
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Offer.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        inputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(Offer.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onTimeSetListener, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        inputVehicleNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (!text.toString().isEmpty()) {
                    Call<UserVehicle> call = apiInterface.getUserVehicleByRegistrationNumber(Registration.getUser().getToken(), inputVehicleNumber.getText().toString());
                    call.enqueue(new Callback<UserVehicle>() {
                        @Override
                        public void onResponse(Call<UserVehicle> call, Response<UserVehicle> response) {
                            if (response.code() == 200){
                                userVehicle = response.body();
                                visualizeVehicleTypeWidgets(View.GONE);
                            } else if (response.code() == 204) {
                                visualizeVehicleTypeWidgets(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<UserVehicle> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                else {
                    visualizeVehicleTypeWidgets(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void visualizeVehicleTypeWidgets(int newView) {
        spinnerVehicleType.setVisibility(newView);
        labelVehicleType.setVisibility(newView);
    }
}
