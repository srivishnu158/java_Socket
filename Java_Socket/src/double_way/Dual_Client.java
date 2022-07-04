package double_way;
import java.io.*;
//import java.io.InputStreamReader;
import java.net.Socket;


class Sensing2 extends Thread{
	String inbox;
	BufferedReader br2;
	
	 public Sensing2(BufferedReader br2){
		this.br2 = br2;
	}
	 
	public void run(){
		while(true) {
			try {
				inbox = br2.readLine();
				System.out.println("\t\tServer: "+inbox);
			}
			catch(Exception e){
				System.exit(1);
			}
		}
	}
}
public class Dual_Client {
	public static void main(String[] args) throws Exception {
		Socket sock = new Socket("localhost",9998);
		
//		DataInputStream din  = new DataInputStream(sock.getInputStream());
		DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		//PrintStream p = new PrintStream(sock.getOutputStream());
		
		String inbox="",sent="";
		Sensing2 th = new Sensing2(br2);
		th.start();
		while(!inbox.equals("stop")) {	
			sent = br.readLine();
			dout.writeBytes(sent + "\n");
			
//			inbox = br2.readLine();
//			System.out.println("Server: "+inbox);
		}
		// din.close();
		
		sock.close();
	}
}
