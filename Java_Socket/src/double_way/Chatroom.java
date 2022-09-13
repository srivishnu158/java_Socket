package double_way;
import java.util.Scanner;
public class Chatroom {
	public static void main(String[] args) throws Exception{
		Scanner scan = new Scanner(System.in);
		System.out.println("1. Enter C to create chat room \n2. Enter J to join chat room");
		String str;
		str = scan.next();
		if(str.equals("c") || str.equals("C")) {
			//Dual_Server server = new Dual_Server();
			System.out.println("Enter max no.of users");
			int num = scan.nextInt();
			Dual_Server.initial(num);
		}
		else if(str.equals("j")|| str.equals("J")) {
			System.out.println("Joining");
			Dual_Client.starter();
		}
		else {
			System.out.println("Invalid entry");
		}
		scan.close();
	}
}
