package com.webpromo.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText username, password, name, mobile;
    private Button register;
    private ProgressBar progressBar;
    private String susername,spassword,sname,smobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        username = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_password);
        name = findViewById(R.id.reg_name);
        mobile = findViewById(R.id.reg_mobile);
        register = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        //to autoclick after putting mobile no
        mobile.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                if (c.length() == 9)
                {
                    Toast.makeText(MainActivity.this, "Mobile Insert", Toast.LENGTH_SHORT).show();
                }
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        //to register the user and start progressbar
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                sname = name.getText().toString().trim();
                smobile = mobile.getText().toString().trim();
                susername = username.getText().toString().trim();
                spassword = password.getText().toString().trim();
                if (susername.isEmpty())
                {
                    username.setError("Please Enter Username");
                    username.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (spassword.isEmpty())
                {
                    password.setError("Please Enter Password");
                    password.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (sname.isEmpty())
                {
                    name.setError("Please Enter Name");
                    name.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (smobile.isEmpty())
                {
                    mobile.setError("Please Enter Mobile");
                    mobile.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                register();

            }
        });

    }

    private void register() {
        Call<ResponseBody> call=MyClient.getInstance().getMyApi().registeruser(sname,
                smobile,susername,spassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                String data = null;
                try {
                    data = response.body().string();
                    Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                name.setText("");
                username.setText("");
                password.setText("");
                mobile.setText("");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
