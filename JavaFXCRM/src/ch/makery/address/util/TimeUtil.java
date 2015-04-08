package ch.makery.address.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Helper functions for handling dates.
 * 
 * @author Marco Jakob
 */
public class TimeUtil {

    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String TIME_PATTERN = "HH:mm:ss";

    /** The date formatter. */
    private static final DateTimeFormatter TIME_FORMATTER = 
            DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * Returns the given date as a well formatted String. The above defined 
     * {@link TimeUtil#TIME_PATTERN} is used.
     * 
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String format(LocalTime date) {
        if (date == null) {
            return null;
        }
        return TIME_FORMATTER.format(date);
    }

    /**
     * Converts a String in the format of the defined {@link TimeUtil#TIME_PATTERN} 
     * to a {@link LocalDate} object.
     * 
     * Returns null if the String could not be converted.
     * 
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalTime parse(String dateString) {
        try {
            return TIME_FORMATTER.parse(dateString, LocalTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     * 
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return TimeUtil.parse(dateString) != null;
    }
}