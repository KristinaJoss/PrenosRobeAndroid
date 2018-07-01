package com.example.toshiba.prenosrobe.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.data.DriverOfferStation;
import com.example.toshiba.prenosrobe.data.User;
import com.example.toshiba.prenosrobe.data.UserLanguage;
import com.example.toshiba.prenosrobe.util.DynamicViews;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriverOfferDialog
{
    public static void openedFromMainActivity(final Context context, final DriverOffer driverOffer)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.activity_driver_offer, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        alertDialogBuilder.setView(dialogView);

        ((TextView) dialogView.findViewById(R.id.labelLocations)).setText(driverOffer.getDepartureLocation() + " - " + driverOffer.getArrivalLocation());
        Date offerDate = driverOffer.getDate();
        int year = offerDate.getYear() + 1900;
        int month = offerDate.getMonth() + 1;
        ((TextView) dialogView.findViewById(R.id.labelDateValue)).setText(offerDate.getDate() + "." + month + "." + year + ".");
        ((TextView) dialogView.findViewById(R.id.labelTimeValue)).setText(driverOffer.getTime().toString());
        ((TextView) dialogView.findViewById(R.id.labelVehicleValue)).setText(driverOffer.getUserVehicle().getVehicle().getVehicleType().getName());
        initStations(context, driverOffer, dialogView);
        initLanguages(context, driverOffer, dialogView);

        User driver = driverOffer.getUserVehicle().getUser();
        ((TextView) dialogView.findViewById(R.id.labelDriverID)).setText(driver.getName() + " " + driver.getSurname());
        ((TextView) dialogView.findViewById(R.id.labelDriverUsername)).setText(driver.getUsername());

        alertDialogBuilder.setCancelable(false);
        final Dialog dialog = alertDialogBuilder.create();

        dialogView.findViewById(R.id.buttonBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "Book", Toast.LENGTH_SHORT).show();
            }
        });

        dialogView.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void openedFromProfileActivity(final Context context, final DriverOffer driverOffer)
    {
        Toast.makeText(context, "My offer :D", Toast.LENGTH_SHORT).show();
    }

    private static void initStations(final Context context, final DriverOffer driverOffer, View dialogView)
    {
        List<String> stationNames = new ArrayList<>();
        List<DriverOfferStation> driverOfferStations = driverOffer.getDriverOfferStations();
        int numberOfStations = driverOfferStations.size();

        // Skipping departure and arrival locations
        for (int i = 1; i < numberOfStations - 1; i++)
        {
            stationNames.add(driverOfferStations.get(i).getStation().getName());
        }

        if (stationNames.isEmpty())
        {
            stationNames.add(context.getResources().getString(R.string.noStations));
        }

        for (String stationName : stationNames)
        {
            DynamicViews dynamicViews = new DynamicViews(context);
            ((GridLayout) dialogView.findViewById(R.id.gridAllStations)).addView(dynamicViews.getTextView(context, stationName, ((TextView) dialogView.findViewById(R.id.labelVehicleValue)).getTextColors()));
        }
    }

    private static void initLanguages(final Context context, final DriverOffer driverOffer, View dialogView)
    {
        List<UserLanguage> userLanguages = driverOffer.getUserVehicle().getUser().getUserLanguages();

        for (UserLanguage userLanguage : userLanguages)
        {
            DynamicViews dynamicViews = new DynamicViews(context);
            ((GridLayout) dialogView.findViewById(R.id.gridUserLanguages)).addView(dynamicViews.getTextView(context, userLanguage.getLanguage().getName(), ((TextView) dialogView.findViewById(R.id.labelVehicleValue)).getTextColors()));
        }
    }
}
