package com.sbrf.reboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;

import java.io.Serializable;

public class XMLUtils {

    public static String toXML(Serializable serializable) throws JsonProcessingException {
        return new XmlMapper().writeValueAsString(serializable);
    }

    public static Request XMLtoRequest(String s) throws JsonProcessingException {
        return new XmlMapper().readValue(s, Request.class);
    }

    public static Response XMLtoResponse(String s) throws JsonProcessingException {
        return new XmlMapper().readValue(s, Response.class);
    }
}
