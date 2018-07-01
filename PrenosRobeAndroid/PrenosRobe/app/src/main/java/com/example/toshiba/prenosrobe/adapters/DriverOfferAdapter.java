package com.example.toshiba.prenosrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.activities.MainActivity;
import com.example.toshiba.prenosrobe.activities.ProfileActivity;
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.data.DriverOfferStation;
import com.example.toshiba.prenosrobe.dialogs.DriverOfferDialog;
import com.example.toshiba.prenosrobe.util.DynamicViews;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriverOfferAdapter extends BaseAdapter
{
    private List<DriverOffer> driverOffers = new ArrayList<>();
    private int count;
    private Context context;

    public DriverOfferAdapter(Context context, List<DriverOffer> driverOffers)
    {
        this.context = context;
        this.driverOffers = driverOffers;
        this.count = driverOffers.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return driverOffers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int index, View view, ViewGroup viewGroup)
    {
        DriverOfferViewHolder viewHolder;

        final DriverOffer tempDriverOffer = driverOffers.get(index);

        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.driver_offer_single_row, null);
            viewHolder = new DriverOfferViewHolder();
            viewHolder.labelMsgListView = view.findViewById(R.id.labelMsgListView);
            viewHolder.listViewDate = view.findViewById(R.id.ListViewDate);
            viewHolder.listViewUsername = view.findViewById(R.id.ListViewUsername);
            viewHolder.ImageView = view.findViewById(R.id.ImageView);
            viewHolder.gridAllStations = view.findViewById(R.id.gridAllStations);

            view.setTag(viewHolder);
        }
        else
            viewHolder = (DriverOfferViewHolder) view.getTag();

        viewHolder.labelMsgListView.setText(tempDriverOffer.getDepartureLocation() + " - " + tempDriverOffer.getArrivalLocation());
        Date offerDate = tempDriverOffer.getDate();
        int year = offerDate.getYear() + 1900;
        int month = offerDate.getMonth() + 1;
        viewHolder.listViewDate.setText(offerDate.getDate() + "." + month + "." + year + ".");
        viewHolder.listViewUsername.setText(tempDriverOffer.getUserVehicle().getUser().getUsername());
        viewHolder.ImageView.setImageResource(R.drawable.profile_icon);
        initStations(index, viewHolder);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MainActivity)
                    DriverOfferDialog.openedFromMainActivity(context, tempDriverOffer);
                else if (context instanceof ProfileActivity)
                    DriverOfferDialog.openedFromProfileActivity(context, tempDriverOffer);
            }
        });

        return view;
    }

    private void initStations(final int index, DriverOfferViewHolder viewHolder)
    {
        List<String> stationNames = getStationNames(index);
        viewHolder.gridAllStations.removeAllViews();

        if (stationNames.isEmpty())
        {
            stationNames.add(context.getResources().getString(R.string.noStations));
        }

        for (String stationName : stationNames)
        {
            DynamicViews dynamicViews = new DynamicViews(context);
            viewHolder.gridAllStations.addView(dynamicViews.getTextView(context, stationName));
        }
    }

    private List<String> getStationNames(final int index)
    {
        List<String> stationNames = new ArrayList<>();
        List<DriverOfferStation> driverOfferStations = driverOffers.get(index).getDriverOfferStations();
        int numberOfStations = driverOfferStations.size();

        // Skipping departure and arrival locations
        for (int i = 1; i < numberOfStations - 1; i++)
        {
            stationNames.add(driverOfferStations.get(i).getStation().getName());
        }

        return stationNames;
    }
}

class DriverOfferViewHolder
{
    ImageView ImageView;
    TextView labelMsgListView, listViewDate, listViewUsername;
    GridLayout gridAllStations;
}