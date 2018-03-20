package com.example.toshiba.prenosrobe.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
//import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.DriverOffer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    private List<DriverOffer> driverOffers;

    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<DriverOffer>> call = apiInterface.getAllDriverOffers();
        call.enqueue(new Callback<List<DriverOffer>>() {
            @Override
            public void onResponse(Call<List<DriverOffer>> call, Response<List<DriverOffer>> response) {
                if(response.code() == 200){
                    driverOffers = response.body();
                    l.setAdapter(new DriverOfferAdapter(MainActivity.this, driverOffers));
                }
            }

            @Override
            public void onFailure(Call<List<DriverOffer>> call, Throwable t) {
                t.printStackTrace();
                ((TextView) findViewById(R.id.textView2)).setText("neeeeeee");
            }
        });

        l = (ListView) findViewById(R.id.ListViewHome);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.single_row, R.id.labelMsgListView, data); //layout koji opisuje posebni red u ListView, zatim gde da smesta upisane podatke
//        l.setAdapter(adapter);


//        ((Button) findViewById(R.id.buttonOffer)).setOnClickListener(this);
//        ((Button) findViewById(R.id.buttonSearch)).setOnClickListener(this);
//        ((Button) findViewById(R.id.buttonProfile)).setOnClickListener(this);
//        ((Button) findViewById(R.id.buttonBack)).setOnClickListener(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Intent i;
                switch (item.getItemId())
                {
                    case R.id.action_home:
                        i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Action Search Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_offer:
                        if(RegistrationActivity.getUser() == null){
                            i = new Intent(MainActivity.this, LogInActivity.class);
                        }
                        else {
                            i = new Intent(MainActivity.this, OfferActivity.class);
                        }

                        startActivity(i);
                        break;

                    case R.id.action_notification:
                        Toast.makeText(MainActivity.this, "Action Notifications Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_profile:
                        if(RegistrationActivity.getUser() == null){
                            i = new Intent(MainActivity.this, LogInActivity.class);
                        }
                        else {
                            i = new Intent(MainActivity.this, ProfileActivity.class);
                        }

                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }



    class DriverOfferAdapter extends BaseAdapter{

        private List<DriverOffer> driverOffers = new ArrayList<>();
        private int count;
        private Context context;

        public DriverOfferAdapter(Context context, List<DriverOffer> driverOffers){
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
        public View getView(int index, View view, ViewGroup viewGroup) {

            ViewHolder viewHolder;

            final DriverOffer tempDriverOffer = driverOffers.get(index);

            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.single_row, null);
                viewHolder = new ViewHolder();
                viewHolder.labelMsgListView = (TextView) view.findViewById(R.id.labelMsgListView);
                viewHolder.ImageView = (ImageView) view.findViewById(R.id.ImageView);

                view.setTag(viewHolder);
            }
            else
                viewHolder = (ViewHolder) view.getTag();

            viewHolder.labelMsgListView.setText(tempDriverOffer.getArrivalLocation());
            viewHolder.ImageView.setImageResource(R.drawable.ic_email_black_24dp);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TextView) findViewById(R.id.textView2)).setText((CharSequence) tempDriverOffer.getArrivalLocation());
                }
            });

            return view;
        }
    }

    static class ViewHolder {
        ImageView ImageView;
        TextView labelMsgListView;
    }
}
