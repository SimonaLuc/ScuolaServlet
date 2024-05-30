package librerie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageDb {
	public Connection myConn;
	
	public boolean Connect(String sIpServer, int iPort, String sNomeDb, String sNomeUtente, String sPass)
	{
		String sConnectString;
		
		sConnectString = "jdbc:mysql://" + sIpServer + ":" + iPort + "/" + sNomeDb +
				"?user=" + sNomeUtente + "&password=" + sPass;
		try {
			myConn = DriverManager.getConnection(sConnectString);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean WriteInDB(String sSqlQuery)
	{
		try {
			Statement stm = myConn.createStatement();
			return stm.execute(sSqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ResultSet ReadInDB(String sSqlQuery)
	{
		ResultSet res;
		try {
			Statement stm = myConn.createStatement();
			res = stm.executeQuery(sSqlQuery);
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean Disconnect()
	{
		try {
			myConn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
}
