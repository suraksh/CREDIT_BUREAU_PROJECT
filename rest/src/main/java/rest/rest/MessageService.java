package rest.rest;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/hello")
public class MessageService {
	
	public MessageService(){
		
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{rfc}")
	public String sayHello(@PathParam("rfc") String rfc){
		Logger log = Logger.getLogger("MessageService");
		rfc = rfc.replaceAll("\\+", " ");
		log.info("MESSAGERFC received by Communication adapter :" + rfc);
		SockerHelperForWebService socket = new SockerHelperForWebService();
		log.info(rfc);
		String recordDetails = socket.recordDetails(rfc);
		return recordDetails;
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello(){
		return "<?xml version=\"1.0\" ?>"+
				"<hello> Hello from RESTServer!!</hello>";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHelloHTML(){
		
		SockerHelperForWebService socket = new SockerHelperForWebService();
		String recordDetails = socket.recordDetails("READ:12345QWERT");
		return recordDetails;
		
		
//		return new StringBuilder("")
//		.append("<html>")
//		.append("\t<head><title>Hello</title></head>")
//		.append("\t<body><h1> Hello from RESTServer! </h1></body>")
//		.append("/<html>").toString();
	}
}
