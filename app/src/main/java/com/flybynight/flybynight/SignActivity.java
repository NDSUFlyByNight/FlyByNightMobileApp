package com.flybynight.flybynight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flybynight.flybynight.api.FlyByNightApi;
import com.flybynight.flybynight.api.response.SignInResponse;
import com.flybynight.flybynight.utils.AsyncTaskHandler;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignActivity extends AppCompatActivity implements View.OnClickListener{


    @InjectView(R.id.etUsername)
    EditText etUsername;
    @InjectView(R.id.etPassword)
    EditText etPassword;

    @InjectView(R.id.tvSignIn)
    TextView tvSignIn;
    @InjectView(R.id.pbLoading)
    ProgressBar pbLoading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.inject(this);

        tvSignIn.setOnClickListener(this);

    }

    void signIn(final String username, final String password) {


        pbLoading.setVisibility(View.VISIBLE);
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
                    Preferences.getPrefs(SignActivity.this).setToken(result.token);

                    // Go to flight attendant page.


                } else {
                    // Error
                    etUsername.setError("Invalid Combination");
                    etPassword.setError("Invalid Combination");
                }
            }

            @Override
            public void onFinally() {
                super.onFinally();
                pbLoading.setVisibility(View.INVISIBLE);
                // The call is over. Stop loading bars etc here
            }
        };
        signInTask.execute();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSignIn:

                if (etUsername.getText().length() == 0) {
                    etUsername.setError("Enter Username");
                    return;
                }
                etUsername.setError(null);

                if (etPassword.getText().length() == 0) {
                    etPassword.setError("Enter Password");
                    return;
                }
                etPassword.setError(null);

                signIn(etUsername.getText().toString(), etPassword.getText().toString());

                break;
        }
    }
}
