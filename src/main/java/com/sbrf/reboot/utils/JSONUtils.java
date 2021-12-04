package com.sbrf.reboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;

import java.io.Serializable;

public class JSONUtils {
    public static String toJSON(Serializable serializable) throws JsonProcessingException {
        ObjectMapper mapper = new JsonMapper();
        return mapper.writeValueAsString(serializable);
    }

    public static Request JSONtoRequest(String s) throws JsonProcessingException {
        ObjectMapper mapper = new JsonMapper();
        return mapper.readValue(s, Request.class);
    }

    public static Response JSONtoResponse(String s) throws JsonProcessingException {
        ObjectMapper mapper = new JsonMapper();
        return mapper.readValue(s, Response.class);
    }
}
