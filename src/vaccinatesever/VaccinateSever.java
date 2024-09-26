
package vaccinatesever;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

public class VaccinateSever {

    public static void main(String[] args) throws IOException, SQLException {
        // TODO code application logic here
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("Sever started at port:"+ss.getLocalPort());
        Socket s =ss.accept();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())),true);
        BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));
        String userId = in.readLine();
      //  System.out.println(userId);
        //System.out.println(checkVaccinated(userId));
        if(checkVaccinated(userId)==true){
            if(numOfTick()<=2000){
                Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/VaccinatedDB", "PAT", "123");
                Random r = new Random();
                int num = r.nextInt(10000-1000)+1000;
                String sql = "Insert into ticket values(?,?,?)";
                PreparedStatement ps=conn.prepareStatement(sql);
                
                ps.setString(1,String.valueOf(num) ); 
                ps.setString(2, userId);
                
                ps.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
                ps.executeUpdate();
                out.println(String.valueOf(num));
                
                
                
            }
            
        }else {
            out.println("\nYou are not vaccinated.");
        }
        
    }
    public static boolean checkVaccinated(String id) throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/VaccinatedDB", "PAT", "123");
        String sql = "Select idnum from vaccinatedtbl where idnum = ?";
         PreparedStatement ps=conn.prepareStatement(sql);
         ps.setString(1, id);
         ResultSet rs=ps.executeQuery();
         boolean flag=false;
        if(rs.next()){
           if(rs.getString(1)!=null){
               flag=true;
           }
        }
       return flag; 
    }
   public static int numOfTick() throws SQLException{
       Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/VaccinatedDB", "PAT", "123");
       String sql ="Select count(ticnum) from ticket";
        PreparedStatement ps=conn.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        int num=0;
        if(rs.next()){
             num =rs.getInt(1);
        }
       return num;
   }
    
}
