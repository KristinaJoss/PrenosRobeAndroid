package com.example.toshiba.prenosrobe.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.activities.RegistrationActivity;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.ClaimerOffer;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;
import com.example.toshiba.prenosrobe.adapters.ClaimerOfferAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBookings extends Fragment
{
    private ApiInterface apiInterface;
    private ListView listViewBookings;
    private TextView labelNoBookings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_fragment_bookings, container, false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        listViewBookings = view.findViewById(R.id.ListViewBookings);
        labelNoBookings = view.findViewById(R.id.labelNoBookings);

        String token = RegistrationActivity.getUser().getToken();
        Call<RestRespondeDto<List<ClaimerOffer>>> call = apiInterface.getMyClaimerOffers(token);
        call.enqueue(new Callback<RestRespondeDto<List<ClaimerOffer>>>()
        {
            @Override
            public void onResponse(Call<RestRespondeDto<List<ClaimerOffer>>> call, Response<RestRespondeDto<List<ClaimerOffer>>> response) {
                if(response.code() == 200)
                {
                    List<ClaimerOffer> claimerOffers = response.body().getData();
                    listViewBookings.setAdapter(new ClaimerOfferAdapter(getActivity(), claimerOffers));

                    int labelNonOffersVisibility = claimerOffers.isEmpty()? View.VISIBLE : View.INVISIBLE;
                    labelNoBookings.setVisibility(labelNonOffersVisibility);
                }
            }

            @Override
            public void onFailure(Call<RestRespondeDto<List<ClaimerOffer>>> call, Throwable t)
            {
                t.printStackTrace();
            }
        });

        return view;
    }
}
