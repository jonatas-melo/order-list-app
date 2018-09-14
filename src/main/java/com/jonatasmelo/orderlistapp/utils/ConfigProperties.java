package com.jonatasmelo.orderlistapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ConfigProperties {
    public static final String API_URL_KEY = "api-url";
    public static final String ENDPOINT_LOGIN = "/login";
    public static final String ENDPOINT_CUSTOMERS = "/customers";
    public static final String ENDPOINT_CUSTOMERS_ORDERS = "/customers/%d/orders";
    public static final String HEADER_ACCESS_TOKEN = "access_token";

    private static ConfigProperties ourInstance = null;
    private Properties properties;

    public static ConfigProperties getInstance() {
        if (ourInstance == null) {
            synchronized (ConfigProperties.class) {
                if (ourInstance == null) {
                    ourInstance = new ConfigProperties();
                }
            }
        }
        return ourInstance;
    }

    private ConfigProperties() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("com/jonatasmelo/config.properties");
            properties = new Properties();
            properties.load(input);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public String getApiUrl() throws Exception {
        return getValue(API_URL_KEY);
    }

    public String getValue(String key) throws Exception {
        if (properties == null) {
            throw new Exception("Properties not loaded properly");
        }

        return StringUtils.defaultString(properties.getProperty(key));
    }

    public static Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json1, type, jsonDeserializationContext) -> {
            String strValue = json1.getAsJsonPrimitive().getAsString();
            if (null == strValue) {
                return null;
            }
            return LocalDateTime.parse(strValue.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()));
        }).create();
    }
}
