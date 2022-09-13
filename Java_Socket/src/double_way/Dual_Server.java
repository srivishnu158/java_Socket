package double_way;
import java.io.BufferedReader;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;


 class Sensing1 extends Thread{
	 BufferedReader br2,br;
	 String inbox;
	 Socket sock;
	 PrintStream p;
	static ArrayList <Sensing1> users = new ArrayList<>();
	 String username;
	 void broadcast(ArrayList<Sensing1> users, String removed) {
		 Dual_Server.limit_increaser();
		 
		 for(int i = 0; i < users.size();i++) {
		 	Sensing1 val = users.get(i);
			 val.p.println(removed + " has left the chat");
			 val.p.flush();
		 }
	 }
	 
	 void closeeverything(BufferedReader br2,Socket sock,PrintStream p){
	 	String name = this.username;
	 	users.remove(this);
	 	broadcast(users,name);
	 	try{
	 		if(this.br2 != null)
	 			this.br2.close();
	 		if(this.p != null)
	 			this.p.close();
	 		if(this.sock != null)
	 			this.sock.close();
	 	}
	 	catch(IOException io){
	 		io.printStackTrace();
	 	}
	 }

	 public void usercount(){
	 	boolean flag = true;
	 	for(Sensing1 in:users){
	 		System.out.println(in.username);
	 		flag = false;
	 	}
	 	if(flag)
	 		System.out.println("no users are connected");
	 }

	 public Sensing1(){
	 }

	public Sensing1(Socket sock)throws Exception {
		this.sock = sock;
		br2 = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		p = new PrintStream(sock.getOutputStream());
		p.println("Enter Username");
		username = br2.readLine();
		boolean flag;
	do {
		flag = false;
		for(Sensing1 cli:users) {
			if(cli.username.equals(this.username) ) {
				this.p.println("already exists try another: ");
				flag = true;
			}
		}
		if(flag)
		username = br2.readLine();
	}while(flag == true);
		users.add(this);

		for(Sensing1 cli:users)
			if(cli.username != this.username) {
			cli.p.println(username+" joined");
			}
	}
	public void run() {
		while(sock.isConnected()) {
			try {
				inbox = br2.readLine();
				for(Sensing1 i:users) {
					if(i.username != this.username && ! inbox.contentEquals("") ) {
						i.p.println(this.username+": "+inbox);
						i.p.flush();
					}
				}
				
			}
			catch(Exception e) {
				closeeverything(br2,sock,p);
				break;
			}

		}
	}
}
 
 


public class Dual_Server {
	 static int limit;
	 
	static void limit_increaser(){
		limit ++;
	}
	static void limit_increaser(int value) {
		limit = value;
	}
	public static void initial(int num) throws Exception {
		limit = num;
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
		Scanner scan = new Scanner(System.in);
		new Thread(() -> {
			while(! server_socket.isClosed()) {
				System.out.println("press L to get the list of connected clients");
				String str = scan.next();
				if( str.equals("L") || str.equals("l") ){
					Sensing1 cou = new Sensing1();
					cou.usercount();
				}
			}
		}).start();
		
		while(! server_socket.isClosed()) {
			if(limit > 0) {
				--limit;
				sock = server_socket.accept();
				
				Sensing1 th = new Sensing1(sock);
				th.start();
			}
			else {
				Socket temp_sock = server_socket.accept();
				p = new PrintStream(temp_sock.getOutputStream());
				p.println("Limit exceeded ="+limit);
				
			}

		}
		scan.close();
		server_socket.close();
	}
	}
}
