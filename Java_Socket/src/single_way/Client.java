package single_way;
import java.net.Socket;
// import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class Client {

	public static void main(String[] args) throws Exception
	{
		String ip = "localhost";
		int port = 9999;
		Socket client_socket = new Socket(ip,port);
		String msg = "hello";
		OutputStreamWriter os = new OutputStreamWriter(client_socket.getOutputStream());
		os.write(msg);
		os.flush();
		client_socket.close();
	}

}
