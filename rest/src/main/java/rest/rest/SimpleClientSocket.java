package rest.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClientSocket {
	
	private Socket sock = null;
	private PrintWriter writer = null;
	private String record = "";
	private boolean read = false;
	
	public String getRecord() {
		return record;
	}
	
	public void setRecord(String record) {
		this.record = record;
	}
	
	public boolean isRead() {
		return read;
	}
	
	public void setRead(boolean read) {
		this.read = read;
	}
	
	public SimpleClientSocket() {
		
	}
	
	public void establishNetwork(String rfc){
		
		try{
			
			sock = new Socket("127.0.0.1", 4515);
			writer = new PrintWriter(sock.getOutputStream());
			String line = null;
			Thread t = new Thread(new IncomingMessageReader(sock));
			t.start();
			writer.println(rfc);
			writer.flush();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

	class IncomingMessageReader implements Runnable{
		
		BufferedReader reader = null;
		Socket sock = null;
		
		public IncomingMessageReader(Socket sock){
			try {
				reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				this.sock = sock;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
			String line = null;
			boolean run = true;
			try {
					while((line = reader.readLine())!= null)
					{
						record = line;
						read = true;
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				writer.close();
			}
		}
		
	}


}
