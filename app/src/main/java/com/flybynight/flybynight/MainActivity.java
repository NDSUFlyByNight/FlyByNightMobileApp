package com.flybynight.flybynight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.flybynight.flybynight.api.FlyByNightApi;
import com.flybynight.flybynight.api.response.FlightAttendantFlightsResponse;
import com.flybynight.flybynight.api.response.SignInResponse;
import com.flybynight.flybynight.utils.AsyncTaskHandler;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void example(final String username, final String password) {

        AsyncTaskHandler<SignInResponse> signInTask = new AsyncTaskHandler<SignInResponse>() {
            @Override
            public SignInResponse doInBackground() throws Exception {
                return FlyByNightApi.getApi().signInUser(username, password);
            }

            @Override
            public void onSuccess(SignInResponse result) {
                super.onSuccess(result);
                // Check you signed in
                if(result.success) {
                    // Success

                    // Cache Token
                    Preferences.getPrefs(MainActivity.this).setToken(result.token);

                    // Go to flight attendant page.
                } else {
                    // Error
                }
            }

            @Override
            public void onFinally() {
                super.onFinally();
                // The call is over. Stop loading bars etc here
            }
        };
        signInTask.execute();



    }

}
