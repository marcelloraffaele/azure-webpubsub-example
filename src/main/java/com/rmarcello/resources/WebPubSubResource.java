package com.rmarcello.resources;

import java.time.Duration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.messaging.webpubsub.WebPubSubServiceClient;
import com.azure.messaging.webpubsub.WebPubSubServiceClientBuilder;
import com.azure.messaging.webpubsub.models.GetClientAccessTokenOptions;
import com.azure.messaging.webpubsub.models.WebPubSubClientAccessToken;
import com.azure.messaging.webpubsub.models.WebPubSubContentType;
import com.rmarcello.beans.TokenCreation;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/webpubsub")
public class WebPubSubResource {

    @ConfigProperty(name = "webpubsub.accesskey") 
    String accessKey;

    @ConfigProperty(name = "webpubsub.endpoint") 
    String endpoint;

    @ConfigProperty(name = "webpubsub.token-expiration-in-minutes") 
    Long tokenExpirationMinutes;

    private final String HUB = "test";


    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public TokenCreation token() {
        TokenCreation t = new TokenCreation();

        WebPubSubServiceClient webPubSubServiceClient = new WebPubSubServiceClientBuilder()
            .credential(new AzureKeyCredential( accessKey ))
            .endpoint(endpoint)
            .hub( HUB )
            .buildClient();

        
        GetClientAccessTokenOptions options = new GetClientAccessTokenOptions();
        options.setExpiresAfter(Duration.ofMinutes(tokenExpirationMinutes));
        WebPubSubClientAccessToken cat = webPubSubServiceClient.getClientAccessToken(options);
        t.setToken( cat.getToken() );
        t.setUrl( cat.getUrl() );

        return t;
    }

    @GET
    @Path("/sendtoall")
    @Produces(MediaType.TEXT_PLAIN)
    public String sendtoall() {
        

        WebPubSubServiceClient webPubSubServiceClient = new WebPubSubServiceClientBuilder()
            .credential(new AzureKeyCredential( accessKey ))
            .endpoint(endpoint)
            .hub( HUB )
            .buildClient();

        webPubSubServiceClient.sendToAll( "Just a message", WebPubSubContentType.TEXT_PLAIN );
        
        return "Message sent";
    }

    @GET
    @Path("/send/{text}")
    @Produces(MediaType.TEXT_PLAIN)
    public String send(@PathParam("text") String text) {
        

        WebPubSubServiceClient webPubSubServiceClient = new WebPubSubServiceClientBuilder()
            .credential(new AzureKeyCredential( accessKey ))
            .endpoint(endpoint)
            .hub( HUB )
            .buildClient();

        webPubSubServiceClient.sendToAll( text , WebPubSubContentType.TEXT_PLAIN );
        
        return String.format("Message: %s has been sent", text);
    }
}