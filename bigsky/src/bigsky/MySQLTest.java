package bigsky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class MySQLTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu/db30901", "adm309", "EXbDqudt4");
		
		ResultSet rs = con.createStatement().executeQuery("select * from testTable");
		//stmt.executeUpdate("insert into testTable (phoneNumber, password) values ('1231234567','mypassword')");
		rs.next();
		System.out.println(rs.getString("phoneNumber"));
		System.out.println(rs.getString("password"));
		rs.close();		
		
		con.close();
		
	}

}
