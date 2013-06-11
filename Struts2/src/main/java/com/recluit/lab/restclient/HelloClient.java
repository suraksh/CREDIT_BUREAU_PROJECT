package com.recluit.lab.restclient;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import freemarker.log.Logger;

public class HelloClient {

	public String getMessage(String rfc){
		
		rfc = rfc.replaceAll(" ", "\\+");
		Client client = Client.create();
		Logger log = Logger.getLogger("HelloClient");
		log.info("http://localhost:8080/rest/rest/hello/"+rfc);
		
		WebResource webResource = client.resource("http://localhost:8080/rest/rest/hello/"+rfc);
		ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
		
		if(response.getStatus() != 200){
			throw new RuntimeException("Failed! : " + response.getStatus());
		}
		
		return response.getEntity(String.class);
	}

}
