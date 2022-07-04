package double_way;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
//import java.io.InputStreamReader;



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
		
		String inbox="",sent="";
		while(!inbox.equals("stop")) {
			inbox = br2.readLine();
			System.out.println("Client: "+inbox);
			sent = br.readLine();
			p.println(sent);
		}
		//din.close();
		sock.close();
		server_socket.close();
		
	}

}
