package domain.virtual_machine;

import java.util.Date;

public class DateRange implements Comparable<DateRange>{
    private static final int MILLISECONDS_IN_HOUR = 60 * 60 * 1000;

    private Date startDate;
    private Date endDate;

    public DateRange(Date startDate) {
        this.startDate = startDate;
        this.endDate = null;
    }

    public DateRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getHours() {
        Date endDate = (this.endDate == null || this.endDate.after(new Date())) ? new Date() : this.endDate;
        return (endDate.getTime() - startDate.getTime()) / (MILLISECONDS_IN_HOUR);
    }

    public long getHours(DateRange other) {
        Date thisEndDate = (this.endDate == null || this.endDate.after(new Date())) ? new Date() : this.endDate;
        Date otherEndDate = (other.endDate == null || other.endDate.after(new Date())) ? new Date() : other.endDate;

        if (otherEndDate.before(this.startDate) ||
                other.startDate.after(thisEndDate))
            return 0;

        if (other.startDate.after(this.startDate) &&
                otherEndDate.before(thisEndDate))
            return other.getHours();

        if (otherEndDate.after(thisEndDate))
            return (thisEndDate.getTime() - other.startDate.getTime()) / (MILLISECONDS_IN_HOUR);

        return (otherEndDate.getTime() - this.startDate.getTime()) / (MILLISECONDS_IN_HOUR);
    }

    @Override
    public int compareTo(DateRange other) {
        return this.startDate.compareTo(other.startDate);
    }
}
