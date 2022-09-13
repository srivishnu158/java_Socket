package double_way;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class Sensing4 extends Thread {
	BufferedReader br2, br;
	String inbox;
	Socket sock;
	PrintStream p;
	static ArrayList<Sensing1> users = new ArrayList<>();
	String username;

	void broadcast(ArrayList<Sensing1> users, String removed) {
		Dual_Server.limit_increaser();
		for (int i = 0; i < users.size(); i++) {
			Sensing1 val = users.get(i);
			val.p.println(removed + " has left the chat");
			val.p.flush();
		}
		System.out.println("No of users" + Dual_Server.limit);
	}

	void closeeverything(BufferedReader br2, Socket sock, PrintStream p) {
		String name = this.username;
		users.remove(this);
		broadcast(users, name);
		try {
			if (this.br2 != null)
				this.br2.close();
			if (this.p != null)
				this.p.close();
			if (this.sock != null)
				this.sock.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public void usercount() {
		boolean flag = true;
		for (Sensing1 in : users) {
			System.out.println(in.username);
			flag = false;
		}
		if (flag)
			System.out.println("no users are connected");
	}

	public Sensing4() {
	}

	public Sensing4(Socket sock) throws Exception {
		this.sock = sock;
		br2 = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		p = new PrintStream(sock.getOutputStream());
		p.println("Enter Username");
		username = br2.readLine();
		boolean flag;
		do {
			flag = false;
			for (Sensing1 cli : users) {
				if (cli.username.equals(this.username)) {
					this.p.println("already exists try another: ");
					flag = true;
				}
			}
			if (flag)
				username = br2.readLine();
		} while (flag == true);
		//users.add(this);

		for (Sensing1 cli : users)
			if (cli.username != this.username) {
				cli.p.println(username + " joined");
			}
	}

	public void run() {
		while (sock.isConnected()) {
			try {
				inbox = br2.readLine();
				for (Sensing1 i : users) {
					if (i.username != this.username && !inbox.contentEquals("")) {
						i.p.println(this.username + ": " + inbox);
						i.p.flush();
					}
				}

			} catch (Exception e) {
				closeeverything(br2, sock, p);
				break;
			}

		}
	}
}