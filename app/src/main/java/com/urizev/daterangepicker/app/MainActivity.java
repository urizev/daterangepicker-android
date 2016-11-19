package com.urizev.daterangepicker.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.urizev.daterangepicker.lib.DateRange;
import com.urizev.daterangepicker.lib.DateRangePickerDialog;
import com.urizev.daterangepicker.lib.DateRangeView;

public class MainActivity extends AppCompatActivity {

    private DateRangeView dateRangeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DateRangePickerDialog.Builder(MainActivity.this)
                        .initialDateRange(DateRange.today())
                        .onDateRangeSelected(new DateRangePickerDialog.OnDateRangeSelectedListener() {
                            @Override
                            public void onDateRangeSelected(MaterialDialog dialog, DateRange dateRange) {

                            }
                        }).show();
            }
        });
    }

}
