package rest.rest;

public class SockerHelperForWebService {
	
	public String recordDetails(String rfc)

	{

		SimpleClientSocket client = new SimpleClientSocket();
		client.establishNetwork(rfc);

		while (client.isRead() != true) {
			// till response is got, keep looping
		}
		return client.getRecord();
	}

}
