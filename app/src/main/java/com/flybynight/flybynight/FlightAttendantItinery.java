package com.flybynight.flybynight;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Everett on 10/22/2015.
 */
public class FlightAttendantItinery extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        ListView list = (ListView)findViewById(R.id.list_view);

        list.addHeaderView(new View(this));
        list.addFooterView(new View(this));

        BaseInflaterAdapter<Card> adapter = new BaseInflaterAdapter<Card>(new CardInflater());
        for (int i = 0; i < 5; i++)
        {
            Card data = new Card("Flight:  " + "0000", "Departure:  " + "00:00", "Arrival: " + "00:00");
            adapter.addItem(data, false);
        }

        list.setAdapter(adapter);
    }


}
