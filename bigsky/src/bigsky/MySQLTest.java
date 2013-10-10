package bigsky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MySQLTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu/db30901", "adm309", "EXbDqudt4");
		Statement stmt = con.createStatement();
		
		//stmt.executeUpdate("insert into testTable (phoneNumber,lastName,firstName, password) values ('1111111111','Johnson','Missy','mypassword')");
		ResultSet rs = con.createStatement().executeQuery("select * from testTable");
	
		rs.last();
		int numRows = rs.getRow();
		rs.first();
		for(int i =0; i <numRows; i++){
		
		
		
		if(rs.getString("phoneNumber") != null){
			System.out.println(rs.getString("phoneNumber"));
		}
		
		if(rs.getString("lastName") != null){
			System.out.println(rs.getString("lastName"));
		}
		
		if(rs.getString("firstName") != null){
			System.out.println(rs.getString("firstName"));
		}
		
		if(rs.getString("password") != null){
			System.out.println(rs.getString("password"));
		}
			
		rs.next();
		System.out.println("--------------------");
		}
		
		
		
		
		rs.close();		
		
		con.close();
		
	}

}
