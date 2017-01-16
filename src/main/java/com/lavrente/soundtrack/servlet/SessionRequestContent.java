package com.lavrente.soundtrack.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 123 on 30.12.2016.
 */
public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
     public void extractValues(HttpServletRequest request) {
        requestParameters = request.getParameterMap();
        requestAttributes = new HashMap<>();
        Enumeration<String> attr = request.getAttributeNames();
        while (attr.hasMoreElements()) {
            String name = attr.nextElement();
            requestAttributes.put(name, request.getAttribute(name));
        }
        sessionAttributes = new HashMap<>();
        HttpSession session = request.getSession(true);
        attr = session.getAttributeNames();
        while (attr.hasMoreElements()) {
            String name = attr.nextElement();
            sessionAttributes.put(name, session.getAttribute(name));
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
    }


    public String getRequestParameter( String key) {
        if(!requestParameters.isEmpty()){
            String[] parameters=requestParameters.get(key);
            return  parameters!=null ? parameters[0]: null;
        }else{
            return null;
        }
    }

    public void setRequestParameters(Map<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public Object getRequestAttribute(String key) {
        return requestAttributes.get(key);
    }

    public void setRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }


    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    public void setSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }
}
