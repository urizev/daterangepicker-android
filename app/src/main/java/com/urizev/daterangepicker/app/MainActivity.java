package com.urizev.daterangepicker.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.urizev.daterangepicker.lib.DateRangeView;

public class MainActivity extends AppCompatActivity {

    private DateRangeView dateRangeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
