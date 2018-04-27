package com.puneet.test.game.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private final int PITS_PER_PLAYER = 6;
	private final List<Pit> pits;
	private final Hole hole;
	private final String name;
	
	public Player(String name) {
		this.name = name;
		this.hole = new Hole();
		pits = new ArrayList<>();
		for(int i = 0; i < PITS_PER_PLAYER; i++) {
			pits.add(new Pit());
		}
	}
	
	public Pit getPit(int index) {
		if (index < 0 || index >= pits.size()) {
		    return null;
		}
		return pits.get(index);
	}
	
	public Hole getHole() {
		return hole;
	}

	public String getName() {
		return name;
	}
	
	public List<Pit> getPits() {
		return pits;
	}
	
	public boolean hasAnyStonesLeft() {
		return this.pits.stream()
			.filter(pit -> pit.size() > 0)
			.findAny()
			.isPresent();
	}
}
