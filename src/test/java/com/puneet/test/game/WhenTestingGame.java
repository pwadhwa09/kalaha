package com.puneet.test.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.puneet.test.game.exception.GameException;
import com.puneet.test.game.exception.IllegalMoveException;
import com.puneet.test.game.model.GameStatus;

public class WhenTestingGame {

	private Game game;
	
	@Before
	public void setup() {
		game = new Game();
	}
	
	@Test(expected = GameException.class)
	public void should_throw_exception_if_game_not_been_started_and_we_check_winner() {
		game.getWinner();
	}
	
	@Test
	public void should_return_initiated_status_initially() {
		assertThat(game.getStatus()).isEqualByComparingTo(GameStatus.INITIATED);
	}

	@Test
	public void should_return_player1_as_status_after_game_has_been_started() {
		game.start();
		assertThat(game.getStatus()).isEqualByComparingTo(GameStatus.PLAYER_1);
	}

	@Test(expected = GameException.class)
	public void should_throw_exception_if_we_try_to_start_a_game_already_in_progress() {
		game.start();
		game.start();
	}
	
	
	@Test(expected = IllegalMoveException.class)
	public void should_throw_exception_illegal_move_negative() {
		game.start();
		game.move(-1);
	}
	
	@Test(expected = IllegalMoveException.class)
	public void should_throw_exception_illegal_move_higher() {
		game.start();
		game.move(10);
	}

	@Test(expected = IllegalMoveException.class)
	public void should_throw_exception_illegal_move_empty_pit() {
		game.start();
		game.move(0);
		game.move(0);
	}
}
