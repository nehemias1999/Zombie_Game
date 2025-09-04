package com.zombie.model.entities;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String name;
	private List<Card> deck;
	private Boolean isActive;
	
	public Player(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.deck = new ArrayList<Card>();
		this.isActive = true;
	}
	
	public UUID getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public List<Card> getDeck() {
		return this.deck;
	}
	
	public Boolean isActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public void addCard(Card card) {
		this.deck.add(card);
	}
	
	public Card removeCard(int index) {
		return deck.remove(index);
	}
	
	public void removeCard(Card card) {
		deck.remove(card);
	}
		
	public Boolean isOnlyAJokerLeftInDeck() {
		return (this.deck.size() == 1) && (this.deck.get(0).isJoker());
	}
	
	public List<Card> discard() {
	    if (deck.isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<Card> auxDeck = new ArrayList<>(deck);
	    auxDeck.sort(Comparator.comparingInt(Card::getNumber));

	    List<Card> parejas = new ArrayList<>();

	    int i = 0;
	    while (i < auxDeck.size() - 1) {
	        Card actual = auxDeck.get(i);
	        if (actual.isJoker()) {
	            i++;
	            continue;
	        }
	        Card siguiente = auxDeck.get(i + 1);
	        if (!siguiente.isJoker() && actual.getNumber().equals(siguiente.getNumber())) {
	            parejas.add(actual);
	            parejas.add(siguiente);
	            i += 2; 
	        } else {
	            i++;
	        }
	    }
	    
	    deck.removeAll(parejas);

	    return parejas;
	}
	
}
