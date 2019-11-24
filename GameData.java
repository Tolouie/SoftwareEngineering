package guiMoving;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable {
	final int WIDTH = 750, HEIGHT = 500;

	int pacmanWidth = 30;
	int pacmanHeight = 30;
	int speed = 8;

	int player1X = WIDTH / 2 - (2*pacmanWidth);
	int player1Y = HEIGHT / 2 - (2*pacmanHeight);
	int player2X = WIDTH / 2 + pacmanWidth;
	int player2Y = HEIGHT / 2 - (2*pacmanHeight);
	
	int player1Score = 0;
	int player2Score = 0;

	//when client sends it back to the server this matters
	boolean isPlayer1;
	
	int timeLeft = 60;
	
	Timer timer;
	
	ArrayList<Point> pellets;
	int pellet_size = 8;
	Color pellet_color = new Color(235, 52, 110);
	Color gd = new Color (0,0,0);
	public void generatePelletsCoordinates() {
		ArrayList<Point> pellets = new ArrayList<>();
		int num_pellets = 20;
		for(int i = 0; i < 20; i++) {
			int x = (int)(Math.random()*WIDTH);
			int y = (int)(Math.random()*HEIGHT);
			pellets.add(new Point(x,y));
		}
		this.pellets = pellets;
	}
	
	public boolean legalMove(int x, int y) {
		boolean insideXAxis = (x + (pacmanWidth/2)> 0) && ((x + pacmanWidth) < WIDTH);
		boolean insideYAxis = (y + (pacmanHeight/2) > 0) && ((y + (2*pacmanHeight)) < HEIGHT);
		return insideXAxis && insideYAxis;
	}
	
	public void checkForPelletCollision(int x, int y, boolean isPlayer1) {
//		int player_x = isPlayer1? player1X : player2X;
//		int player_y = isPlayer1? player1Y : player2Y;
		
		int x2 = x + pacmanWidth;
		int y2 = y + pacmanHeight;
		
		//x and x2 and y and y2 are pacmanWidth coordinates for left, top and bottom, right.
		
		for(int i = 0; i < pellets.size(); i++) {
			int pellet_x = pellets.get(i).x;
			int pellet_y = pellets.get(i).y;
//			|| 
//			(pellet_x > player_x && pellet_x < player_x  + pacmanWidth) && (y2 > player_y && y2 < player_y  + pacmanHeight))
			if(	(pellet_x > x && pellet_x < x  + pacmanWidth) && (pellet_y > y && pellet_y < y  + pacmanHeight)) {
				int notImportant = isPlayer1 ? player1Score++ : player2Score++;
				pellets.remove(i);
			}
		}
	}
}
