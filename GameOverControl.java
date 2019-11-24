package guiMoving;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class GameOverControl implements ActionListener
{
  // Private data fields for the container and chat client.
  private JPanel container;
  private ChatClient client;
  
  
  // Constructor for the login controller.
  public GameOverControl(JPanel container, ChatClient client)
  {
    this.container = container;
    this.client = client;
    client.setGameOverControl(this);
   
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

    // The Cancel button takes the user back to the initial panel.
    if (command == "Play Again")
    {
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "1");
    }

    // The Submit button submits the create account information to the server.
    else if (command == "Exit Game")
    {
      System.exit(0);
    }
  }
}
