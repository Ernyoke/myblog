package com.ervin.myblog.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.util.Calendar;

public class Utils {

    public static Date getCurrentDate() {
        return new Date(Calendar.getInstance().getTimeInMillis());
    }

    public static <E> String convertToJsonString(E object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(object);
        return jsonString;
    }
}
