package double_way;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
//import java.io.InputStreamReader;

 class Sensing1 extends Thread{
	 BufferedReader br2;
	 String inbox;
	public Sensing1(BufferedReader br2) {
		this.br2 = br2;
	}
	public void run() {
		while(true) {
			try {
				String inbox = br2.readLine();
				System.out.println("\t\tClient: "+inbox);
			}
			catch(Exception e) {
				System.exit(1);
			}
		}
	}
}

public class Dual_Server {

	public static void main(String[] args) throws Exception {
		ServerSocket server_socket = new ServerSocket(9998);
		System.out.println("Server has started");
		Socket sock = server_socket.accept();
		
		//DataInputStream din  = new DataInputStream(sock.getInputStream());
		//DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		PrintStream p = new PrintStream(sock.getOutputStream());
		
		
		String inbox="",send="";
		Sensing1 th = new Sensing1(br2);
		th.start();
		while(!inbox.equals("stop")) {
//			inbox = br2.readLine();
//			System.out.println("Client: "+inbox);
			
			send = br.readLine();
			p.println(send);
		}
		//din.close();
		sock.close();
		server_socket.close();
		
	}

}
