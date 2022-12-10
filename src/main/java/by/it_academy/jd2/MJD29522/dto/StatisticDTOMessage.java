package by.it_academy.jd2.MJD29522.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticDTOMessage {
    private final long time;
    private final String message;

    public StatisticDTOMessage(long time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getDateToString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date(time));
        return date;
    }

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}