package com.guryanov.handler;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailCheck {
    public static boolean check(String email) {
        boolean emailCheckOne;
        boolean emailCheckTwo;
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        if (matcher.matches()) {
            emailCheckOne = true;
        } else
            emailCheckOne = false;
        try {
            new InternetAddress(email);
            emailCheckTwo = true;
        } catch (AddressException ex) {
            emailCheckTwo = false;
        }
        return emailCheckOne & emailCheckTwo;
    }
}
