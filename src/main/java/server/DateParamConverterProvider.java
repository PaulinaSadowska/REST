package server;

import javax.ws.rs.ext.Provider;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

@Provider
public class DateParamConverterProvider implements ParamConverterProvider
{

    private final String format;

    public DateParamConverterProvider(String dateFormat)
    {
        this.format = dateFormat;
    }

    public <T> ParamConverter<T> getConverter(Class<T> rawType,
                                              Type genericType,
                                              Annotation[] annotations)
    {

        if (rawType != Date.class)
        {
            return null;
        }

        return (ParamConverter<T>) new ParamConverter<Date>()
        {

            public Date fromString(String value)
            {
                if (value == null)
                    return null;

                SimpleDateFormat formatter = new SimpleDateFormat(format);
                try
                {
                    return formatter.parse(value);
                } catch (Exception ex)
                {
                    throw new WebApplicationException("Bad formatted date", 400);
                }
            }

            public String toString(Date date)
            {
                return new SimpleDateFormat(format).format(date);
            }
        };
    }
}