package single_way;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server {

	public static void main(String[] args) throws Exception {
		ServerSocket server_socket = new ServerSocket(9998);
		System.out.println("Server has started");
		Socket sock = server_socket.accept();
		BufferedReader buff = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		System.out.println(buff.readLine());
		sock.close();
		server_socket.close();
		
	}

}
