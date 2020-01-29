package domain.virtual_machine;

import java.util.Date;

public class DateRange {
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
}
