package com.example.toshiba.prenosrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.data.ClaimerOffer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClaimerOfferAdapter extends BaseAdapter
{
    private List<ClaimerOffer> claimerOffers = new ArrayList<>();
    private int count;
    private Context context;

    public ClaimerOfferAdapter(Context context, List<ClaimerOffer> claimerOffers)
    {
        this.context = context;
        this.claimerOffers = claimerOffers;
        this.count = claimerOffers.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return claimerOffers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup)
    {
        ClaimerOfferViewHolder viewHolder;

        final ClaimerOffer tempClaimerOffer = claimerOffers.get(index);

        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.claimer_offer_single_row, null);
            viewHolder = new ClaimerOfferViewHolder();
            viewHolder.labelLocations = view.findViewById(R.id.labelLocations);
            viewHolder.labelOfferDate = view.findViewById(R.id.labelOfferDate);
            viewHolder.labelDriverUsername = view.findViewById(R.id.labelDriverUsername);
            viewHolder.imageDriver = view.findViewById(R.id.imageDriver);

            view.setTag(viewHolder);
        }
        else
            viewHolder = (ClaimerOfferViewHolder) view.getTag();

        viewHolder.labelLocations.setText(tempClaimerOffer.getDepartureLocation() + " - " + tempClaimerOffer.getArrivalLocation());
        Date date = tempClaimerOffer.getDriverOffer().getDate();
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        viewHolder.labelOfferDate.setText(date.getDate() + "." + month + "." + year + ".");
        viewHolder.labelDriverUsername.setText(tempClaimerOffer.getDriverOffer().getUserVehicle().getUser().getUsername());
        viewHolder.imageDriver.setImageResource(R.drawable.profile_icon);

        return view;
    }
}

class ClaimerOfferViewHolder
{
    ImageView imageDriver;
    TextView labelLocations, labelOfferDate, labelDriverUsername;
}
