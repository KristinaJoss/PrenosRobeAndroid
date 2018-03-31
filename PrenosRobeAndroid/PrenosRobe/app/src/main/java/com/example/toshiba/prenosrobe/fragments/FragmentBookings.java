package com.example.toshiba.prenosrobe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.activities.RegistrationActivity;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.ClaimerOffer;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBookings extends Fragment
{
    private ApiInterface apiInterface;
    private List<ClaimerOffer> claimerOffers;
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_fragment_bookings, container, false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        String token = RegistrationActivity.getUser().getToken();
        Call<RestRespondeDto<List<ClaimerOffer>>> call = apiInterface.getMyClaimerOffers(token);
        call.enqueue(new Callback<RestRespondeDto<List<ClaimerOffer>>>()
        {
            @Override
            public void onResponse(Call<RestRespondeDto<List<ClaimerOffer>>> call, Response<RestRespondeDto<List<ClaimerOffer>>> response) {
                if(response.code() == 200)
                {
                    claimerOffers = response.body().getData();
                    lv .setAdapter(new FragmentBookings.ClaimerOfferAdapter(getActivity(), claimerOffers));
                }
            }

            @Override
            public void onFailure(Call<RestRespondeDto<List<ClaimerOffer>>> call, Throwable t)
            {
                t.printStackTrace();
            }
        });

        lv = (ListView) view.findViewById(R.id.ListViewBookings);

        return view;
    }

    class ClaimerOfferAdapter extends BaseAdapter
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
            FragmentBookings.ViewHolder viewHolder;

            final ClaimerOffer tempClaimerOffer = claimerOffers.get(index);

            if (view == null)
            {
                view = LayoutInflater.from(context).inflate(R.layout.single_row, null);
                viewHolder = new FragmentBookings.ViewHolder();
                viewHolder.labelMsgListView = (TextView) view.findViewById(R.id.labelMsgListView);
                viewHolder.listViewDate = (TextView) view.findViewById(R.id.ListViewDate);
                viewHolder.listViewUsername = (TextView) view.findViewById(R.id.ListViewUsername);
                viewHolder.ImageView = (ImageView) view.findViewById(R.id.ImageView);

                view.setTag(viewHolder);
            }
            else
                viewHolder = (FragmentBookings.ViewHolder) view.getTag();

            viewHolder.labelMsgListView.setText(tempClaimerOffer.getDepartureLocation() + " - " + tempClaimerOffer.getArrivalLocation());
            Date date = tempClaimerOffer.getDriverOffer().getDate();
            int year = date.getYear() + 1900;
            viewHolder.listViewDate.setText(date.getDate() + "." + date.getMonth()+ "." + year + ".");
            viewHolder.listViewUsername.setText(tempClaimerOffer.getDriverOffer().getUserVehicle().getUser().getUsername());
            viewHolder.ImageView.setImageResource(R.drawable.profile_icon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TextView) getActivity().findViewById(R.id.textView2)).setText((CharSequence) tempClaimerOffer.getArrivalLocation());
                }
            });

            return view;
        }
    }

    static class ViewHolder
    {
        ImageView ImageView;
        TextView labelMsgListView, listViewDate, listViewUsername;
    }
}
