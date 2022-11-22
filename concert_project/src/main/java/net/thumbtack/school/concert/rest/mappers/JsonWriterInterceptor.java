package net.thumbtack.school.concert.rest.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.*;

public class JsonWriterInterceptor implements WriterInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWriterInterceptor.class);

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)	throws IOException, WebApplicationException {
        OutputStream originalStream = context.getOutputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        context.setOutputStream(baos);
        try {
            context.proceed();
        } finally {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));
            String json = reader.readLine();
            if(json != null)
                LOGGER.debug("Json output " + json);
            baos.writeTo(originalStream);
            baos.close();
            reader.close();
            context.setOutputStream(originalStream);
        }
    }
}
