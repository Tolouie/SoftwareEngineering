package guiMoving;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.Timer;

import ocsf.server.ConnectionToClient;

public class GameLogicControlServer {
  // store the clients so we can send message to them individually
  ConnectionToClient player1;
  ConnectionToClient player2;

  // store the player size so we can determine when there is two players
  private int players_size = 1;

  Timer timer;
  int timeLeft = 60;

  GameData dataToSendToClient;
  ChatServer server;
  JTextArea log;

  public GameLogicControlServer() {
    dataToSendToClient  = new GameData();
    dataToSendToClient.generatePelletsCoordinates();
  }

  public void handleClientConnection(ConnectionToClient client) {
    if (player1 == null) {
      // first client that connects is player 1
      player1 = client;
      // we print this information to the textarea on the server
      log.append("Player " + players_size + " Connected\n");
      // increase the players_size so we know that player 2 is coming
      players_size++;
    } else {
      // second client is player 2
      player2 = client;
      // we append that information to the textarea as well
      log.append("Player " + players_size + " Connected\n");
    }
    if (player1 != null && player2 != null) {
      // after there are two players we append that the game is starting
      log.append("Game Starting... \n");
      // stop listening for more clients since there are already two players.
      server.stopListening();

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }

      // send GameData to client1 and tell them they are player 1
      GameData gd = new GameData();
      gd.isPlayer1 = true;
      try {
        player1.sendToClient(gd);
        gd.isPlayer1 = false;
        player2.sendToClient(gd);
        timer = new Timer(1000, new DecrementTask());
        timer.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
     
    }
  }

  public void handleDataFromClient(String msg) {
    String[] data = msg.split(",");
    if(data[0].equals("GameDataFromClient")) {
      int x = Integer.parseInt(data[1]);
      int y = Integer.parseInt(data[2]);
      boolean isPlayer1 = Boolean.parseBoolean(data[3]);
      if(isPlayer1) {
        //check legal move
        //update player 1 coordinates and send it back
        if(dataToSendToClient.legalMove(x, y)) {
          dataToSendToClient.player1X = Integer.parseInt(data[1]);
          dataToSendToClient.player1Y = Integer.parseInt(data[2]);
          //check for pellets collison
          dataToSendToClient.checkForPelletCollision(dataToSendToClient.player1X, dataToSendToClient.player1Y, isPlayer1);
        }
      } else if(dataToSendToClient.legalMove(x, y)) { 
        //update player 2 coordinates and send it back
        dataToSendToClient.player2X = Integer.parseInt(data[1]);
        dataToSendToClient.player2Y = Integer.parseInt(data[2]);
        //check for pellets collison
        dataToSendToClient.checkForPelletCollision(dataToSendToClient.player2X, dataToSendToClient.player2Y, isPlayer1);
      }
    }
    server.sendToAllClients(dataToSendToClient);
  }

  public void resetState() {
    player1 = null;
    player2 = null;
    players_size = 1;
  }

  private class DecrementTask implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e) {
      dataToSendToClient.timeLeft--;
      server.sendToAllClients(dataToSendToClient);
      System.out.print(timeLeft);
      if(dataToSendToClient.timeLeft <= 0) {
        timer.stop();
      }
    }
  }
}
