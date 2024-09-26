
package vaccinatedclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class VaccinatedClient {

    public static void main(String[] args) throws UnknownHostException, IOException, SQLException {
        // TODO code application logic here
        Scanner input = new Scanner(System.in);
        Socket s = new Socket(InetAddress.getLocalHost(),8080);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())),true);
        BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.print("Enter your Id:");
        String id = input.nextLine();
        out.println(id);
        System.out.println("the ticket num generated is "+in.readLine());
        
    }
    
    
}
