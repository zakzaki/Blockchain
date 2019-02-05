package p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread{
	
	private ServerSocket serversocket;
	private Set<ServerThreadThread> serverThreadThreads = new HashSet<ServerThreadThread>();
	
	
	public ServerThread(int port) throws IOException {
		
		serversocket=new ServerSocket(port);
	}
		
	public void run() {
			try {
				
				while(true) {
					
					ServerThreadThread serverThreadThread=new ServerThreadThread(serversocket.accept(), this);
					serverThreadThreads.add(serverThreadThread);
					serverThreadThread.start();
				}				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	public void sendmessage(String msg) {
		try {
			
			serverThreadThreads.forEach(t->t.getPrintWriter().println(msg));
		}catch(Exception e) {
			//e.printStackTrace();
		}
	}

	
	/*** 	GETTERS AND SETTERS ***/
	public ServerSocket getServersocket() {
		return serversocket;
	}


	public void setServersocket(ServerSocket serversocket) {
		this.serversocket = serversocket;
	}


	public Set<ServerThreadThread> getServerThreadThreads() {
		return serverThreadThreads;
	}


	public void setServerThreadThreads(Set<ServerThreadThread> serverThreadThreads) {
		this.serverThreadThreads = serverThreadThreads;
	}
	
	/*******************************************/
	
	

}
