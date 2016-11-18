package com.urizev.daterangepicker.lib;

import java.util.Calendar;

/**
 * Creado por jcvallejo en 17/11/16.
 */

public class DateRange {
    private Calendar from;
    private Calendar to;

    public DateRange() {
        this.setFrom(Calendar.getInstance());
        this.setTo(Calendar.getInstance());
    }

    public void setFrom(Calendar from) {
        if (from == null) {
            from = to;
        }
        this.from = Calendar.getInstance();
        this.from.set(from.get(Calendar.YEAR), from.get(Calendar.MONTH), from.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    }

    public void setTo(Calendar to) {
        if (to == null) {
            to = from;
        }
        this.to = Calendar.getInstance();
        this.to.set(to.get(Calendar.YEAR), to.get(Calendar.MONTH), to.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
    }


    public boolean isInRange(Calendar day) {
        return !day.before(from) && !day.after(to);
    }

    public Calendar getFrom() {
        return from;
    }

    public Calendar getTo() {
        return to;
    }
}
