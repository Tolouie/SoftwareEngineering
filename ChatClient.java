package guiMoving;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.*;

import ocsf.client.AbstractClient;

public class ChatClient extends AbstractClient
{
//	public boolean userValidated = false;
	private LoginControl loginControl = null;
	private CreateAccountControl createAccountControl = null;
	private GameOverControl gameOverControl = null;
	GamePanelClient gp;
	GameOverPanel gameOver;
	JPanel container;
	CardLayout cardLayout;
	boolean start = true;
  
  public ChatClient()
  {
    //10.252.112.231
    super("localhost",8300);
  }

  @Override
  public void handleMessageFromServer(Object arg0)
  {
	  if (arg0 instanceof String) {
		  String msg = (String)(arg0);
		  if(msg.equals("login successful")) {
			  loginControl.loginSuccess();
		  }
		  else if (msg.equals("login unsuccessful")) {
			  loginControl.loginNotValid();
		  }
		  else if (msg.equals("not unique")) {
			  createAccountControl.notUniqueUsername();
		  }
		  else if (msg.equals("created")) {
			  createAccountControl.createdUser();
		  }
	  }
	  else if (arg0 instanceof GameData) {
		  GameData data = (GameData)arg0;
		  if(start) {
			  //initialize the game
			  JPanel view5 = new GamePanelClient(data);
			  gp = (GamePanelClient) view5; //client storage of gameclient
			  container.add(view5, "5");
			  cardLayout.show(container, "5");
			  view5.requestFocus();
			  gp.client = this;
			  start = false;
		  } else {
			  //update our game
        gp.updateGame(data);
		    if(data.timeLeft <= 55) {
          //initialize the game
          JPanel view6 = new GameOverPanel(data, null);
          gameOver = (GameOverPanel) view6; //client storage of gameclient
          container.add(view6, "6");
          cardLayout.show(container, "6");
          gameOver.client = this;
          start = false;
        }
		  }
	  }
  }
  
  public void connectionException (Throwable exception) 
  {
    //Add your code here
  }
  public void connectionEstablished()
  {
    //Add your code here
  }
  
  public LoginControl getLoginControl() {
	  return loginControl;
  }
  
  public void setLoginControl(LoginControl loginControl) {
	  this.loginControl = loginControl;
  }

  public CreateAccountControl getCreateAccountControl() {
	  return createAccountControl;
  }
  
  public void setCreateAccountControl(CreateAccountControl createAccountControl) {
	  this.createAccountControl = createAccountControl;
  }
  
  public GameOverControl getGameOverControl() {
    return gameOverControl;
  }
  
  public void setGameOverControl(GameOverControl gameOverControl) {
    this.gameOverControl = gameOverControl;
  }

}
