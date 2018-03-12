package com.example.toshiba.prenosrobe.activities;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.ClaimerOffer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBookings extends Fragment {

    private ApiInterface apiInterface;
    private List<ClaimerOffer> claimerOffers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        String token = Registration.getUser().getToken();
        Call<List<ClaimerOffer>> call = apiInterface.getMyClaimerOffers(token);
        call.enqueue(new Callback<List<ClaimerOffer>>() {
            @Override
            public void onResponse(Call<List<ClaimerOffer>> call, Response<List<ClaimerOffer>> response) {
                if(response.code() == 200) {
                    claimerOffers = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<ClaimerOffer>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_bookings, container, false);
    }

}
