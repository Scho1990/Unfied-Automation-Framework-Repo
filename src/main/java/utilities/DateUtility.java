package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtility {
    private static final Logger logger = LogManager.getLogger(DateUtility.class);
    private DateUtility(){}

    private static final String[] MONTHS = {
            "Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec"
    };

    public static LocalDate getFutureDate(int days){
        System.out.println("getFutureDate"+LocalDate.now().plusDays(days));
        logger.info("getFutureDate"+LocalDate.now().plusDays(days));
        return LocalDate.now().plusDays(days);
    }
    public static String getMonthYear(LocalDate date){
        return MONTHS[date.getMonthValue()-1]+"-"+String.format("%02d",date.getYear()%100);
    }

    public static int getDay(LocalDate date){
        return date.getDayOfMonth();
    }

}
