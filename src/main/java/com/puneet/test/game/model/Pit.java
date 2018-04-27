package com.puneet.test.game.model;

import java.util.ArrayList;
import java.util.List;

public class Pit {
	
	private final int STONES_PER_PIT = 6;
	private final List<Stone> stones;
	
	public Pit() {
		stones = new ArrayList<>();
		for(int i = 0; i < STONES_PER_PIT; i++) {
			stones.add(new Stone());
		}
	}
	
	public int size() {
		return this.stones.size();
	}
	
	public void addStone(Stone stone) {
		this.stones.add(stone);
	}
	
	public List<Stone> takeStones() {
		List<Stone> cleared = new ArrayList<>(this.stones);
		this.stones.clear();
		return cleared;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public boolean isNotEmpty() {
		return !isEmpty();
	}
}
