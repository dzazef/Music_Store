package model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "status_logs", schema = "music_store", catalog = "")
public class StatusLogsEntity {
    private int logId;
    private Timestamp dateTime;
    private Object statusOld;
    private Object statusNew;

    @Id
    @Column(name = "log_id")
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    @Basic
    @Column(name = "date_time")
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Basic
    @Column(name = "status_old")
    public Object getStatusOld() {
        return statusOld;
    }

    public void setStatusOld(Object statusOld) {
        this.statusOld = statusOld;
    }

    @Basic
    @Column(name = "status_new")
    public Object getStatusNew() {
        return statusNew;
    }

    public void setStatusNew(Object statusNew) {
        this.statusNew = statusNew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusLogsEntity that = (StatusLogsEntity) o;

        if (logId != that.logId) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (statusOld != null ? !statusOld.equals(that.statusOld) : that.statusOld != null) return false;
        if (statusNew != null ? !statusNew.equals(that.statusNew) : that.statusNew != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = logId;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (statusOld != null ? statusOld.hashCode() : 0);
        result = 31 * result + (statusNew != null ? statusNew.hashCode() : 0);
        return result;
    }
}
