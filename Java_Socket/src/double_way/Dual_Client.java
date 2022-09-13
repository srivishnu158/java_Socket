package double_way;
import java.io.*;
import java.net.Socket;



class Sensing2 extends Thread{
	String inbox;
	BufferedReader br2;
	
	 public Sensing2(BufferedReader br2){
		this.br2 = br2;
	}
	 
	 void closeeverything(BufferedReader br2){
	 	try{
	 		if(this.br2 != null)
	 			this.br2.close();
	 	}
	 	catch(IOException io){
	 		io.printStackTrace();
	 	}
	 }

	public void run(){
		while(!(br2 == null) ) {
			try {
				if(br2 != null){
					inbox = br2.readLine();
					System.out.println("\t\t"+inbox);
				}
			}
			catch(Exception e){
				closeeverything(br2);
			}
		}
	}
}

public class Dual_Client {
	public static void starter() throws Exception {
		Socket sock = new Socket("localhost",9998);
		
		DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(sock.getInputStream()));

		//PrintStream p = new PrintStream(sock.getOutputStream());
		
		String inbox="",sent="";
		while(!inbox.equals("stop")) {	
			sent = br.readLine();
			dout.writeBytes(sent + "\n");
			inbox = br2.readLine();
			System.out.println("Server: "+inbox);

		String send;
		Sensing2 th = new Sensing2(br2);
		th.start();
		while(sock.isConnected()) {	
			try {
				send = br.readLine();
				dout.writeBytes(send + "\n");
			}
			catch(Exception e) {
				System.out.println("Server is offline!!");
			}

		}
		
		sock.close();
		}
	}
}