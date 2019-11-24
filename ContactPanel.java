package guiMoving;

import java.awt.GridLayout;

import javax.swing.*;

public class ContactPanel extends JPanel{
	  public ContactPanel(/*ContactPanelControl cc*/)
	  {
	    	    
	    // Create the login button.
	    JButton loginButton = new JButton("Delete Contact");
//	    loginButton.addActionListener(cc);
	    JPanel loginButtonBuffer = new JPanel();
	    loginButtonBuffer.add(loginButton);
	    
	    // Create the create account button.
	    JButton createButton = new JButton("Add Contact");
//	    createButton.addActionListener(cc);
	    JPanel createButtonBuffer = new JPanel();
	    createButtonBuffer.add(createButton);
	    
	    // Create the create account button.
	    JButton logoutButton = new JButton("Log Out");
//	    createButton.addActionListener(cc);
	    JPanel logoutButtonBuffer = new JPanel();
	    logoutButtonBuffer.add(logoutButton);
	    
		//================
		JPanel textAreas = new JPanel();
		textAreas.setLayout(new BoxLayout(textAreas, BoxLayout.PAGE_AXIS));

		JLabel label = new JLabel("Contacts",JLabel.CENTER);
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		textAreas.add(label);
		
		JTextArea log = new JTextArea(12,25);	
		JScrollPane logScroll = new JScrollPane(log);
		JPanel logScrollWrapper = new JPanel();
		logScrollWrapper.add(logScroll);
		textAreas.add(logScrollWrapper);
		
	    // Arrange the components in a grid.
	    JPanel grid = new JPanel(new GridLayout(1, 2, 5, 5));
	    grid.add(loginButtonBuffer);
	    grid.add(createButtonBuffer);
	    textAreas.add(grid);
	    
	    JPanel grid2 = new JPanel(new GridLayout(1, 1, 5, 5));
	    grid2.add(logoutButtonBuffer);
	    textAreas.add(grid2);
	    
	    this.add(textAreas);
	  }
}
