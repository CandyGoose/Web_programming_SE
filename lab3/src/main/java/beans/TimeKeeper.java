package beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a time-keeping component for managing and displaying date and time information.
 */
@Getter
@Setter
@ToString
public class TimeKeeper implements Serializable {
    private String time;

    /**
     * Formats the given date into the "dd-MM-yyyy HH:mm:ss" format.
     *
     * @param date The date to be formatted.
     * @return A formatted date and time string.
     */
    private String formatDate(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
    }

    /**
     * Initializes a new instance of TimeKeeper and sets the time to the current date and time.
     */
    public TimeKeeper() {
        this.time = formatDate(new Date());
    }

    /**
     * Sets the time to the specified value.
     *
     * @param time The time string to be set.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Updates the time to the current date and time.
     */
    public void updateTime() {
        time = formatDate(new Date());
    }
}
