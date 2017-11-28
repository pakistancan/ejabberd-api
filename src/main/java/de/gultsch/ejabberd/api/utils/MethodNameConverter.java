package de.gultsch.ejabberd.api.utils;

import de.gultsch.ejabberd.api.Request;

import java.util.Locale;

public class MethodNameConverter {

    public static String convert(Request request) {
       return String.join("_",request.getClass().getSimpleName().split("(?=\\p{Lu})")).toLowerCase(Locale.ENGLISH);
    }
}
