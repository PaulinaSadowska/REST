package server;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
public class GrizzlyHttpServer
{

    public static void main(String[] args) throws Exception
    {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8100).build();
        ResourceConfig config = new ResourceConfig(StudentsDataResource.class, SubjectsDataResource.class, CustomHeaders.class);
        config.register(new DateParamConverterProvider("yyyy-MM-dd"));
        config.packages("org.glassfish.jersey.examples.linking", "com.fasterxml.jackson.jaxrs").
                register(DeclarativeLinkingFeature.class);
        GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    }
}
