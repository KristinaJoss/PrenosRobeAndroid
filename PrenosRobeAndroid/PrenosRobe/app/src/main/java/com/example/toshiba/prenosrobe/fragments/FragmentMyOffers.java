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
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;
import com.example.toshiba.prenosrobe.adapters.DriverOfferAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentMyOffers extends Fragment
{
    private ApiInterface apiInterface;
    private ListView listViewMyOffers;
    private TextView labelNoMyOffers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_fragment_my_offers, container, false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        listViewMyOffers = view.findViewById(R.id.ListViewMyOffers);
        labelNoMyOffers = view.findViewById(R.id.labelNoMyOffers);

        String token = RegistrationActivity.getUser().getToken();
        Call<RestRespondeDto<List<DriverOffer>>> call = apiInterface.getMyDriverOffers(token);
        call.enqueue(new Callback<RestRespondeDto<List<DriverOffer>>>()
        {
            @Override
            public void onResponse(Call<RestRespondeDto<List<DriverOffer>>> call, Response<RestRespondeDto<List<DriverOffer>>> response)
            {
                if(response.code() == 200)
                {
                    List<DriverOffer> driverOffers = response.body().getData();
                    listViewMyOffers.setAdapter(new DriverOfferAdapter(getActivity(), driverOffers));

                    int labelNonOffersVisibility = driverOffers.isEmpty()? View.VISIBLE : View.INVISIBLE;
                    labelNoMyOffers.setVisibility(labelNonOffersVisibility);
                }
            }

             @Override
             public void onFailure(Call<RestRespondeDto<List<DriverOffer>>> call, Throwable t)
             {
                 t.printStackTrace();
             }
        });

        return view;
    }
}
