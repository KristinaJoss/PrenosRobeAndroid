package com.example.toshiba.prenosrobe.activities;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.DriverOffer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentMyOffers extends Fragment {

    private ApiInterface apiInterface;
    private List<DriverOffer> driverOffers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        String token = Registration.getUser().getToken();
        Call<List<DriverOffer>> call = apiInterface.getMyDriverOffers(token);
        call.enqueue(new Callback<List<DriverOffer>>() {
            @Override
            public void onResponse(Call<List<DriverOffer>> call, Response<List<DriverOffer>> response) {
                if(response.code() == 200) {
                    driverOffers = response.body();
                }
            }

             @Override
             public void onFailure(Call<List<DriverOffer>> call, Throwable t) {
                 t.printStackTrace();
             }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_my_offers, container, false);
    }

}
