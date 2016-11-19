package com.urizev.daterangepicker.lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Creado por jcvallejo en 18/11/16.
 */

public class DateRangePickerDialog extends MaterialDialog implements DateRangeView.DateRangeViewListener {
    private static final SimpleDateFormat CALENDAR_HEADER_DATE_FORMAT = new SimpleDateFormat("MMMM y", Locale.getDefault());
    private static final SimpleDateFormat DAY_DATE_FORMAT = new SimpleDateFormat("d", Locale.getDefault());
    private static final SimpleDateFormat MONTH_DATE_FORMAT = new SimpleDateFormat("MMM", Locale.getDefault());
    private static final SimpleDateFormat YEAR_DATE_FORMAT = new SimpleDateFormat("y", Locale.getDefault());

    private final TextView fromDay;
    private final TextView fromMonth;
    private final TextView fromYear;
    private final TextView toDay;
    private final TextView toMonth;
    private final TextView toYear;
    private final View calendarFrame;
    private final DateRangeView calendarRange;
    private final TextView calendarHeader;
    private DateRange dateRange;

    protected DateRangePickerDialog(Builder builder) {
        super(builder);

        fromDay = (TextView) this.customViewFrame.findViewById(R.id.selection_from_day);
        fromMonth = (TextView) this.customViewFrame.findViewById(R.id.selection_from_month);
        fromYear = (TextView) this.customViewFrame.findViewById(R.id.selection_from_year);
        toDay = (TextView) this.customViewFrame.findViewById(R.id.selection_to_day);
        toMonth = (TextView) this.customViewFrame.findViewById(R.id.selection_to_month);
        toYear = (TextView) this.customViewFrame.findViewById(R.id.selection_to_year);

        calendarFrame = this.customViewFrame.findViewById(R.id.calendar_frame);
        calendarRange = (DateRangeView) calendarFrame.findViewById(R.id.calendar_range);
        calendarHeader = (TextView) calendarFrame.findViewById(R.id.calendar_header);
        calendarRange.setOnMonthChangeListener(this);
        calendarFrame.findViewById(R.id.calendar_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarRange.nextMonth();
            }
        });
        calendarFrame.findViewById(R.id.calendar_prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarRange.previousMonth();
            }
        });

        recyclerView = (RecyclerView) this.customViewFrame.findViewById(R.id.recycler);

        this.dateRange = builder.dateRange;
        calendarRange.setDateRange(builder.dateRange);
        onCurrentMonthChanged(calendarRange, calendarRange.getCurrentMonth());
        updateHeader();
    }

    private void updateHeader() {
        Date from = dateRange.getFrom().getTime();
        Date to = dateRange.getTo().getTime();

        this.fromDay.setText(DAY_DATE_FORMAT.format(from));
        this.fromMonth.setText(MONTH_DATE_FORMAT.format(from).toUpperCase(Locale.getDefault()).replace(".", ""));
        this.fromYear.setText(YEAR_DATE_FORMAT.format(from));

        this.toDay.setText(DAY_DATE_FORMAT.format(to));
        this.toMonth.setText(MONTH_DATE_FORMAT.format(to).toUpperCase(Locale.getDefault()).replace(".", ""));
        this.toYear.setText(YEAR_DATE_FORMAT.format(to));
    }

    @Override
    public void onCurrentMonthChanged(DateRangeView view, Calendar calendarMonth) {
        calendarHeader.setText(CALENDAR_HEADER_DATE_FORMAT.format(calendarMonth.getTime()));
    }

    @Override
    public void onDateRangeChanged(DateRangeView dateRangeView, DateRange dateRange) {
        this.dateRange = dateRange;
        this.updateHeader();
    }

    public static class Builder extends MaterialDialog.Builder {

        private OnDateRangeSelectedListener onDateRangeSelected;
        private DateRange dateRange;

        public Builder(@NonNull Context context) {
            super(context);
            this.customView(R.layout.dialog_date_range, false);
            this.positiveText(android.R.string.ok);
            this.negativeText(android.R.string.cancel);
            this.onPositive(new SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    DateRangePickerDialog dateRangeDialog = (DateRangePickerDialog) dialog;
                    onDateRangeSelected.onDateRangeSelected(dialog,  dateRangeDialog.calendarRange.getDateRange());
                    dialog.dismiss();
                }
            });

            dateRange = new DateRange();
            onDateRangeSelected = new OnDateRangeSelectedListener() {
                @Override
                public void onDateRangeSelected(MaterialDialog dialog, DateRange dateRange) {
                    dialog.dismiss();
                }
            };
        }

        public Builder initialDateRange(@NonNull DateRange dateRange) {
            this.dateRange = dateRange;
            return this;
        }

        public Builder onDateRangeSelected(@NonNull OnDateRangeSelectedListener dateRangeSelected) {
            this.onDateRangeSelected = dateRangeSelected;
            return this;
        }

        @Override
        public MaterialDialog build() {
            return new DateRangePickerDialog(this);
        }
    }

    public interface OnDateRangeSelectedListener {
        void onDateRangeSelected(MaterialDialog dialog, DateRange dateRange);
    }
}
