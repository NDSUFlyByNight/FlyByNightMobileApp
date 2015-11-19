package com.flybynight.flybynight;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.flybynight.flybynight.api.FlyByNightApi;
import com.flybynight.flybynight.api.objects.Flight;
import com.flybynight.flybynight.api.response.FlightAttendantFlightsResponse;
import com.flybynight.flybynight.api.response.SignInResponse;
import com.flybynight.flybynight.utils.AsyncTaskHandler;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Everett on 10/22/2015.
 */
public class FlightAttendantItineryActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    BaseInflaterAdapter<Flight> adapter;
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        ButterKnife.inject(this);

        list = (ListView)findViewById(R.id.list_view);

        list.addHeaderView(new View(this));
        list.addFooterView(new View(this));

        adapter = new BaseInflaterAdapter<Flight>(new CardInflater());

        list.setAdapter(adapter);

        list.setOnItemClickListener(this);

        getData();
    }


    void getData() {

        Log.d("Sign In", "Get Data");

        progressBar.setVisibility(View.VISIBLE);
        AsyncTaskHandler<FlightAttendantFlightsResponse> signInTask = new AsyncTaskHandler<FlightAttendantFlightsResponse>() {
            @Override
            public FlightAttendantFlightsResponse doInBackground() throws Exception {
                return FlyByNightApi.getApi().getFlightAttendantFlights();
            }

            @Override
            public void onSuccess(FlightAttendantFlightsResponse result) {
                super.onSuccess(result);


                adapter.clear(false);

                for(int i=0;i<result.flights.length;i++) {
                    adapter.addItem(result.flights[i], false);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }


            @Override
            public void onFinally() {
                super.onFinally();
                progressBar.setVisibility(View.INVISIBLE);
                // The call is over. Stop loading bars etc here
            }
        };
        signInTask.execute();



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Flight flight = (Flight)parent.getItemAtPosition(position);
        startActivity(FlightDetailsActivity.getFlightDetailsActivity(this, flight));
    }

}
