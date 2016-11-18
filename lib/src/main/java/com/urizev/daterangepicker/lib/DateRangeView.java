package com.urizev.daterangepicker.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Creado por jcvallejo en 14/11/16.
 */

public class DateRangeView extends ViewPager implements MonthView.MonthViewListener, MonthView.MonthViewRenderer {
    public static final int DEFAULT_MONTHS_BEFORE = 0;
    public static final int DEFAULT_MONTHS_AFTER = 24;

    private final MonthViewPagerAdapter adapter;
    private DateRange dateRange;

    private DateRangeRenderer renderer;
    private DateRangeMutator mutator;

    public DateRangeView(Context context) {
        this(context, null);
    }

    public DateRangeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        dateRange = new DateRange();
        mutator = new DefaultDateRangeMutator();

        adapter = new MonthViewPagerAdapter(context, DEFAULT_MONTHS_BEFORE, DEFAULT_MONTHS_AFTER);
        adapter.setListener(this);
        adapter.setRenderer(this);
        setCurrentItem(0);
        this.setAdapter(adapter);
    }

    protected void invalidateMonthViews() {
        adapter.notifyDataSetChanged();
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = getChildAt(i);
            if (view instanceof  MonthView) {
                view.invalidate();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if (child != null && child instanceof MonthView) {
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height = Math.max(child.getMeasuredHeight(), height);
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void onDayClicked(Calendar day) {
        mutator.mutateRangeWithDay(dateRange, day);
        invalidateMonthViews();
    }

    @Override
    public void drawMonthDay(Canvas canvas, MonthView.CellInfo cellInfo) {
        if (renderer == null) {
            renderer = new DefaultDateRangeRenderer(getContext());
        }
        renderer.drawMonthDay(canvas, cellInfo, dateRange);
    }

    @Override
    public void drawWeekDay(Canvas canvas, MonthView.CellInfo cellInfo) {
        if (renderer == null) {
            renderer = new DefaultDateRangeRenderer(getContext());
        }
        renderer.drawWeekDay(canvas, cellInfo);
    }

    public interface DateRangeRenderer {
        void drawMonthDay(Canvas canvas, MonthView.CellInfo cellInfo, DateRange dateRange);
        void drawWeekDay(Canvas canvas, MonthView.CellInfo cellInfo);
    }

    public interface DateRangeMutator {
        void mutateRangeWithDay(DateRange dateRange, Calendar day);
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
        invalidateMonthViews();
    }
}
