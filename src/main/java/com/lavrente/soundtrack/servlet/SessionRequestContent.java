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

    /** The request attributes. */
    private HashMap<String, Object> requestAttributes;

    /** The request parameters. */
    private Map<String, String[]> requestParameters;

    /** The session attributes. */
    private HashMap<String, Object> sessionAttributes;

    /**
     * Extract values.
     *
     * @param request the request
     */
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

    /**
     * Insert attributes.
     *
     * @param request the request
     */
    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Gets the request parameter.
     *
     * @param key the key
     * @return the request parameter
     */
    public String getRequestParameter( String key) {
        if(!requestParameters.isEmpty()){
            String[] parameters=requestParameters.get(key);
            return  parameters!=null ? parameters[0]: null;
        }else{
            return null;
        }
    }

    /**
     * Sets the request parameters.
     *
     * @param requestParameters the request parameters
     */
    public void setRequestParameters(Map<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    /**
     * Gets the request attribute.
     *
     * @param key the key
     * @return the request attribute
     */
    public Object getRequestAttribute(String key) {
        return requestAttributes.get(key);
    }

    /**
     * Sets the request attribute.
     *
     * @param key the key
     * @param value the value
     */
    public void setRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    /**
     * Gets the session attribute.
     *
     * @param key the key
     * @return the session attribute
     */
    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    /**
     * Sets the session attribute.
     *
     * @param key the key
     * @param value the value
     */
    public void setSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }
}
