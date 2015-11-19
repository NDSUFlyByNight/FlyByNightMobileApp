package com.flybynight.flybynight;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flybynight.flybynight.api.FlyByNightApi;
import com.flybynight.flybynight.api.FlyByNightService;
import com.flybynight.flybynight.api.objects.Flight;
import com.flybynight.flybynight.api.response.FlightAttendantFlightsResponse;
import com.flybynight.flybynight.api.response.SignInResponse;
import com.flybynight.flybynight.utils.AsyncTaskHandler;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FlightDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    enum DisplayPage {List, Airframe};
    DisplayPage displaying = DisplayPage.List;

    MenuItem miDisplayList;
    MenuItem miDisplayAirframe;

    FlightDetailsListFragment listFrag;
    FlightDetailsAirframeFragment airframeFrag;

    public static Intent getFlightDetailsActivity(Context context, Flight flight) {
        Intent intent = new Intent(context, FlightDetailsActivity.class);
        intent.putExtra("flight",flight);
        return intent;
    }

    Flight flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);
        ButterKnife.inject(this);

        flight = (Flight)getIntent().getSerializableExtra("flight");

        getSupportActionBar().setTitle("Flight: " + flight.flight_num);

        listFrag = FlightDetailsListFragment.newInstance();
        airframeFrag = FlightDetailsAirframeFragment.newInstance();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.flContent, listFrag);
        transaction.commit();

        changeView(DisplayPage.List, true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flight_details, menu);

        miDisplayList = menu.findItem(R.id.action_list);
        miDisplayList.setVisible(false);
        miDisplayAirframe = menu.findItem(R.id.action_airframe);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_list:
                changeView(DisplayPage.List, false);
                return true;
            case R.id.action_airframe:
                changeView(DisplayPage.Airframe, false);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    private void changeView(DisplayPage page, boolean override) {
        if(page != displaying || override) {
            displaying = page;

            if(miDisplayList != null) {
                miDisplayList.setVisible(displaying == DisplayPage.Airframe);
            }
            if(miDisplayAirframe != null) {
                miDisplayAirframe.setVisible(displaying == DisplayPage.List);
            }

            setUpContent();

        }
    }

    private void setUpContent() {


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment frag = displaying == DisplayPage.Airframe ? airframeFrag : listFrag;
        transaction.replace(R.id.flContent, frag);
        transaction.commit();

    }

}
