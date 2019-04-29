package com.example.accels.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.accels.R;

public class LogInPage extends AppCompatActivity {
    TextView log_link_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        log_link_signup = findViewById(R.id.log_link_signup);

        log_link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInPage.this,SignUpPage.class));
            }
        });

    }
}
