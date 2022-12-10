package ch.brouchoud.androiddev_badmintoncourtreservation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ReservationHelper {

    /**
     * Check if the date and time slot are in the past.
     * @param reservationDate to check.
     * @param timeSlot to check.
     * @return true if they are before. False if they are in the futur.
     */
    public static boolean checkLaterDate(String reservationDate, String timeSlot) {
        try {
            LocalDate today = LocalDate.now();
            String beginTimeSlot = timeSlot.substring(0, 2);
            int beginHour = Integer.parseInt(beginTimeSlot);

            Date reservationInputDate = new SimpleDateFormat("dd.MM.yyyy").parse(reservationDate);
            Date timeNow = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

            //Check date
            if(reservationInputDate.before(timeNow)){
                return true;
            }
            //Check timeslot if same day
            if (reservationInputDate.equals(timeNow) && beginHour <= LocalDateTime.now(ZoneId.of("Europe/Zurich")).getHour()) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }
}
