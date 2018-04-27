package com.puneet.test.game;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.puneet.test.game.exception.GameException;
import com.puneet.test.game.exception.IllegalMoveException;
import com.puneet.test.game.model.GameStatus;
import com.puneet.test.game.model.Hole;
import com.puneet.test.game.model.Move;
import com.puneet.test.game.model.Pit;
import com.puneet.test.game.model.Player;
import com.puneet.test.game.model.Stone;

public class Game {
	private final Player player1;
	private final Player player2;
	private GameStatus status;
	private static final String NAME1 = "Player 1";
    private static final String NAME2 = "Player 2";	

    /**
     * Initiate the game
     */
    public Game() {
		player1 = new Player(NAME1);
		player2 = new Player(NAME2);
		status = GameStatus.INITIATED;
	}

    /**
     * Start and set the Player1 as active player
     */
	public void start() {
		if(status != GameStatus.INITIATED) {
			throw new GameException("Game already started");
		}
		status = GameStatus.PLAYER_1;
	}
	
	/**
	 * Make a move and set the status of game accordingly
	 * @param index - Pit index from which stones have to be picked (0-5)
	 */
	public void move(int index) {
		Move move = processMove(index);
		switch(move) {
		case NORMAL:
		case CAPTURE:
			changePlayer();
			break;
		case ONE_MORE_TURN:
			break;
		case NOT_ALLOWED:
			throw new IllegalMoveException("Wrong move!");
		}
		
		if(gameFinished()) {
			status = GameStatus.FINISHED; 
		}
	}

	/**
	 * Method which actually makes the move and moves the stone from selected pit
	 * @param index - Index of the pit
	 * @return - Outcome of that move
	 */
	private Move processMove(int index) {
		if (!validMove(index)) {
		    return Move.NOT_ALLOWED;
		}
		// take stones from the given pit
		Player currentPlayer = getCurrentPlayer();
		Pit selectedPit = currentPlayer.getPit(index);
		List<Stone> stones = selectedPit.takeStones();
		Move move = Move.NORMAL;
		boolean isOponentArea = false;
		
		Iterator<Stone> stonesIterator = stones.iterator();
		while (stonesIterator.hasNext()) {
			Stone stone = stonesIterator.next();
			stonesIterator.remove();
			
			index++;
			move = Move.NORMAL;
			// find the next pit
			selectedPit = currentPlayer.getPit(index);
			if (selectedPit != null) {
				if (!isOponentArea && selectedPit.isEmpty()) {
					move = Move.CAPTURE;
				}
				selectedPit.addStone(stone);
			} else {
				// First time you move out of your pits, you will get your big hole. After that it would be opponent's pits.
				if (!isOponentArea) {
					Hole bigHole = currentPlayer.getHole();
					bigHole.addStone(stone);
					move = Move.ONE_MORE_TURN;
				} else {
					// add the stone back to play at opponent's area in next iteration
					stones.add(stone);
				}
				currentPlayer = getOpponent(currentPlayer);
				isOponentArea = !isOponentArea;
				index = -1;
			}
		}
		if (move == Move.CAPTURE) {
		    capture(index);
		}
		return move;
	}

	private void changePlayer() {
		if(status == GameStatus.PLAYER_1) {
			status = GameStatus.PLAYER_2;
		}
		else if(status == GameStatus.PLAYER_2) {
			status = GameStatus.PLAYER_1;
		}
	}

	//Check if game is finished?
	private boolean gameFinished() {
		return !(this.player1.hasAnyStonesLeft() && this.player2.hasAnyStonesLeft());
	}
    
	// Check the validity of move
	private boolean validMove(int index) {
		// Index out of bounds
		if (index < 0 || index >= 6) {
			return false;
		}
		//Empty pit?
		return getCurrentPlayer().getPit(index).isNotEmpty();
	}
	
	/**
	 * 
	 * @param index - Index of the pit
	 */
	private void capture(int index) {
		//Get all the stones from current player's pit (1 in this case of capture)
		Player currentPlayer = getCurrentPlayer();
		Hole currentPlayerHole = currentPlayer.getHole();
		List<Stone> stones = currentPlayer.getPit(index).takeStones();
		
		// Get Opponent's stone from opposite pit
		Player oponentPlayer = getOpponent(currentPlayer);
		List<Stone> opponentStones = oponentPlayer.getPit(5 - index).takeStones();

		//Add all the collected stones into current player's hole
		Stream.of(stones, opponentStones)
		.flatMap(List::stream)
		.forEach(currentPlayerHole::addStone);
	}

	private Player getCurrentPlayer() {
		switch (status) {
		case PLAYER_1: {
			return player1;
		}
		case PLAYER_2: {
			return player2;
		}
		default:
			return null;
		}
	}
	
	private Player getOpponent(Player player) {
		if (player.getName().equals(NAME1)) {
			return player2;
		}
		return player1;
	}

	/**
	 *  Find the winner of game
	 * @return - Player who has won the game
	 */
	public Player getWinner() {
		if (status != GameStatus.FINISHED) {
			throw new GameException("Game not finished yet, still in progress");
		}
		if (player1.getHole().size() > player2.getHole().size()) {
			return player1;
		}
		if (player2.getHole().size() > player1.getHole().size()) {
			return player2;
		}
		// it's a tie!
		return null;
	}
	
    public GameStatus getStatus() {
    	return this.status;
    }
    
    public Player getPlayer1() {
    	return this.player1;
    }

    public Player getPlayer2() {
    	return this.player2;
    }

}
