package guiMoving;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatServer extends AbstractServer {
	private JTextArea log;
	private JLabel status;
	private Database database;

	// we want to store instance of the server gui so we can manipulate
	private ServerGUI gui;
	
	private GameLogicControlServer glcs;
	
	
//	// store the player size so we can determine when there is two players
//	private int players_size = 1;
//
//	// store the clients so we can send message to them individually
//	ConnectionToClient player1;
//	ConnectionToClient player2;
	
	//create synchonized GameData object to hold source of truth
//	GameData dataToSendToClient;
	
//	ChatServer serverInstance;
	
//	Timer timer;
//	int timeLeft = 60;

	public ChatServer() {
		super(8300);
//		dataToSendToClient  = new GameData();
//		serverInstance = this;
		glcs = new GameLogicControlServer();
		glcs.server = this;
	}

	public ChatServer(int port) {
		super(port);
	}

	// just setting the server gui on this game server instance
	public void setGui(ServerGUI gui) {
		this.gui = gui;
	}

	void setDatabase(Database database) {
		this.database = database;
	}

	public void setLog(JTextArea log) {
		this.log = log;
		glcs.log = this.log;
		log.append(log.getText());
	}

	public JTextArea getLog() {
		return log;
	}

	public void setStatus(JLabel status) {
		this.status = status;
		status.setText(status.getText());
	}

	public JLabel getStatus() {
		return status;
	}
	
	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {		
		if (arg0 instanceof String) {
			String msg = (String)arg0;
			glcs.handleDataFromClient(msg);
		}

//    if (arg0 instanceof LoginData)
//    {
//       LoginData loginData = (LoginData)arg0;
//       
//       String db_password_for_user_query = String.format("select aes_decrypt(password, 'uca') from user where username = '%s';", loginData.getUsername());
//       System.out.println(db_password_for_user_query);
//       ArrayList<String> password = database.query(db_password_for_user_query);
//       
//
//       if(loginData.getPassword().equals(password.get(0))) {
//    	   try {
//			arg1.sendToClient("login successful");
//    	   } catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//    	   }
//       } else {
//    	   try {
//			arg1.sendToClient("login unsuccessful");
//    	   } catch (IOException e) {
//			
//			e.printStackTrace();
//    	   }
//       }
//    } else if (arg0 instanceof CreateAccountData){
//	    {
//	    	CreateAccountData createData = (CreateAccountData)arg0;
//	    	String username = createData.getUsername();
//	    	String password = createData.getPassword();
//	    	
//	    	ArrayList<String> existing_users = database.query("select username from user");
//	
//	    	//not unique username
//	    	if(existing_users.contains(username)) {
//	     	   try {
//	   			arg1.sendToClient("not unique");
//	       	   } catch (IOException e) {
//	   			// TODO Auto-generated catch block
//	   			e.printStackTrace();
//	       	   }
//	    	} 
//	    	//it is unique so we create the user
//	    	else {
//	     	   try {	  
//	     		    String dml = String.format("INSERT INTO user (username, password) VALUES ('%s', aes_encrypt('%s', 'uca'));", username, password);
//	     		    database.executeDML(dml);
//	     		    
//		   			arg1.sendToClient("created");
//		       	   }  catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	    	}
//	    }
//    }

	}

	protected void listeningException(Throwable exception) {
		log.append(exception.getMessage() + "\n");
		status.setText("Exception Occurred when Listening");
		status.setForeground(Color.RED);
		log.append("Click Listen again to restart the server.\nMake sure all invalid requests are stopped\n");

	}

	protected void serverStarted() {
		log.append("Server Started\n");
		status.setText("Listening");
		status.setForeground(Color.GREEN);
	}

	protected void serverStopped() {
		try {
			Thread.sleep(10);// this will allow serverStopped to process first
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.append("Server and all current clients are closed - Press Listen to Restart\n");
		status.setText("Close");
		status.setForeground(Color.RED);
	}

	protected void serverClosed() {
		log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
		status.setText("Stopped");
		status.setForeground(Color.RED);
		glcs.resetState();
	}

	// when a client connects
	protected void clientConnected(ConnectionToClient client) {
		glcs.handleClientConnection(client);
	}
}
