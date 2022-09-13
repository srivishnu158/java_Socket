package single_way;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class Client2 {
	public static void main(String[] args)throws Exception {
		Socket client_2 = new Socket("localhost",9998);
		String str = "world";
		OutputStreamWriter os = new OutputStreamWriter(client_2.getOutputStream());
		os.write(str);
		os.flush();
		client_2.close();
	}
}
