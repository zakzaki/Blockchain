package p2p;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThreadThread extends Thread{

	private ServerThread serverThread;
	private Socket socket;
	private PrintWriter printWriter;
	
	public ServerThreadThread(Socket socket, ServerThread s) {
		this.socket=socket;
		serverThread=s;
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public void setPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}
	
	public void run() {
		try {
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			//String message = org.apache.commons.io.IOUtils.toString(bufferedReader);
			//System.out.println("MMMSSSS "+message);
			this.printWriter= new PrintWriter(socket.getOutputStream(),true);
			
			while(true) serverThread.sendmessage(bufferedReader.readLine());
			
		}catch(Exception e) {
			serverThread.getServerThreadThreads().remove(this);
			
		}
	}
}
