package guiMoving;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import java.sql.*;

public class Database
{
  private Connection conn;
  private String user;
  private String pass;
  private String url;
  //Add any other data fields you like – at least a Connection object is mandatory
  public void setConnection(String fn) throws IOException, SQLException
  {
    //Add your code here
    Properties prop = new Properties();
    FileInputStream fis = new FileInputStream(fn);
    prop.load(fis);
	url = prop.getProperty("url");
	user = prop.getProperty("user");
	pass = prop.getProperty("password");
	 
	conn = DriverManager.getConnection(url,user,pass);
  }

  public Connection getConnection()
  {
    return conn;
  }

  public ArrayList<String> query(String query) throws SQLException 
  {
    //Add your code here
	Statement stmt;
    ResultSet rs;
    ResultSetMetaData rmd;

    //Create a statement
    stmt=conn.createStatement();  
    
	rs=stmt.executeQuery(query);
		
    //Get metadata about the query
    rmd = rs.getMetaData();
		
	ArrayList<String> al = new ArrayList<>();
		
    //Get the # of columns
    int no_columns = rmd.getColumnCount();
		
    while(rs.next()) {
    	String s = "";
    	for(int i = 1; i <= no_columns; i++) {
    		s += rs.getString(i) + ",";
    	}
    	//remove last ,
    	s = s.substring(0, s.length() - 1);
    	al.add(s);
    }
		
	//if arraylist is empty return null
	if(al.size() == 0) {
		return null;
	} else {
		return al;
	}
  }
  
  public void executeDML(String dml) throws SQLException
  {
    //Add your code here
   	Statement stmt;
  	
    //Create a statement
    stmt=conn.createStatement();
    
    //Execute a DML statement
    stmt.execute(dml);
  }
  
}