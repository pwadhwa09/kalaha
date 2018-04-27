package com.puneet.test.game;

import java.util.Scanner;

import com.puneet.test.game.exception.GameException;
import com.puneet.test.game.exception.IllegalMoveException;
import com.puneet.test.game.model.GameStatus;
import com.puneet.test.game.model.Pit;
import com.puneet.test.game.model.Player;

public class App 
{
	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			try {
				Game game = new Game();
				game.start();
				print(game);
				while (true) {
					int n = in.nextInt();
					try {
						game.move(n);
					} catch (IllegalMoveException illegalMoveException) {
						System.out.println("Illegal Move");
						continue;
					}
					print(game);
					if (game.getStatus() == GameStatus.FINISHED) {
						break;
					}
				}
			} catch (GameException ex) {
				ex.printStackTrace();
			}
		}
	}    
    
	private static void print(Game game) {
		switch (game.getStatus()) {
			case INITIATED: {
				System.out.println("Enter a number to start...");
				break;
			}
			case PLAYER_1: {
				printGame(game);
				System.out.println("Player1's turn	:");
				break;
			}
			case PLAYER_2: {
				printGame(game);
				System.out.println("Player2's trun	:");
				break;
			}
			case FINISHED: {
				printGame(game);
				Player winer = game.getWinner();
				if (winer != null) {
					System.out.println(String.format("Finished. %s wins.", winer.getName()));
				} else {
					System.out.println(String.format("Finished with a tie."));
				}
				break;
			}
		}
	}

	private static void printGame(Game game) {
		String holeA = String.format("[%s]", String.format("%02d", game.getPlayer1().getHole().size()));
		String pitsA = "";
		for (Pit pit : game.getPlayer1().getPits()) {
			pitsA = pitsA + String.format("(%d)", pit.size());
		}
		String holeB = String.format("[%s]", String.format("%02d", game.getPlayer2().getHole().size()));
		String pitsB = "";
		for (Pit pit : game.getPlayer2().getPits()) {
			pitsB = String.format("(%d)", pit.size()) + pitsB;
		}

		System.out.println(String.format("%s %s %s", game.getPlayer2().getName(), holeB, pitsB));
		System.out.println(String.format("%s %s %s", game.getPlayer1().getName(), pitsA, holeA));
	}
}
