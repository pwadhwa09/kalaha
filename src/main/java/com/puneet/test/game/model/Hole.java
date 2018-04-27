package com.puneet.test.game.model;

import java.util.ArrayList;
import java.util.List;

public class Hole {
	private List<Stone> stones = new ArrayList<>();
	
	public int size() {
		return stones.size();
	}
	
	public void addStone(Stone stone) {
		this.stones.add(stone);
	}
}
