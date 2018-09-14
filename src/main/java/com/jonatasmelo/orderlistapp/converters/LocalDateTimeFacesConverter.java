package com.jonatasmelo.orderlistapp.converters;

import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@FacesConverter("localDataTimeFacesConverter")
public class LocalDateTimeFacesConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return null;
        }

        LocalDateTime localDateTime = null;

        try {
            localDateTime = LocalDateTime.parse(strValue.trim(), DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mm:ss").withZone(ZoneId.systemDefault()));
        } catch (DateTimeParseException e) {
            throw new ConverterException("Unparseable date", e);
        }

        return localDateTime;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object obj) {
        if (null == obj || !(obj instanceof LocalDateTime)) {
            return "";
        }

        return ((LocalDateTime) obj).format(DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mm:ss").withZone(ZoneId.systemDefault()));
    }
}
