package guiMoving;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{
 
  
  // Constructor that creates the client GUI.
  public ClientGUI()
  {
    
    // Set the title and default close operation.
    this.setTitle("Client");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    ChatClient client = new ChatClient();
    try
		{
			client.openConnection();
			//make a game panel
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        		TODO ADD BACK AFTER FIGURING OUT CLIENT SIDE
		    // Create the card layout container.
		    CardLayout cardLayout = new CardLayout();
		    JPanel container = new JPanel(cardLayout);
		    
		    //Create the Controllers next
		    //Next, create the Controllers
		    InitialControl ic = new InitialControl(container); 
		    LoginControl lc = new LoginControl(container, client); //Probably will want to pass in ChatClient here
		    CreateAccountControl cc = new CreateAccountControl(container, client);
		    //Make a gameover controller
		    //    ContactControl cp = new ContactControl(container, client);
		    
//		    GamePanelControl gpc = new GamePanelControl(container, client);
		    
		    
		    // Create the four views. (need the controller to register with the Panels
		    JPanel view1 = new InitialPanel(ic);
		    JPanel view2 = new LoginPanel(lc);
		    JPanel view3 = new CreateAccountPanel(cc);
//		    JPanel view4 = new ContactPanel(/*cp*/);
		    
		    client.container = container;
		    client.cardLayout = cardLayout;
		    
		    
		    // Add the views to the card layout container.
		    container.add(view1, "1");
		    container.add(view2, "2");
		    container.add(view3, "3");
		   
		    
		    // Show the initial view in the card layout.
		    cardLayout.show(container, "5");
		    
		    // Add the card layout container to the JFrame.
		    this.add(container, BorderLayout.CENTER);

    // Show the JFrame.
    this.setSize(750, 500);
    this.setVisible(true);
    this.setResizable(false);
  }

  // Main function that creates the client GUI when the program is started.
  public static void main(String[] args)
  {
    new ClientGUI();
  }
}
