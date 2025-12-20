package com.cerofour.MiniGram.shared.infrastructure;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {
    public static boolean isOlderThan18(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);

        // Check if the number of full years in the period is 18 or greater
        return period.getYears() >= 18;
    }
}
