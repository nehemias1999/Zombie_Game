package com.zombie.model.entities;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.io.Serializable;
import java.util.Stack;
import java.util.stream.Collectors;

import com.zombie.model.enumerations.CardType;

public class Deck implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final int VALOR_MIN = 1;
    private static final int VALOR_MAX = 13;
	private Stack<Card> mazo = new Stack<Card>();
	
	public Deck() {
		setDeck();
		shuffleDeck();
	}
	
	public void setDeck() {
        for (CardType suit : EnumSet.complementOf(EnumSet.of(CardType.JOKER))) {
            for (int value = VALOR_MIN; value <= VALOR_MAX; value ++) {
                mazo.add(new Card(suit, value));
            }
        }
        
        mazo.add(new Card(CardType.JOKER, 0));
    }
		
	public void shuffleDeck() {
		Collections.shuffle(this.mazo); 
	}
	
	public Boolean isEmpty() {
		return mazo.isEmpty();
	}
	
	public Card getTopCard() {
		return mazo.pop();
	}
			
	public List<String> getDeckStringList() {
	    return mazo.stream()
	    		.map(Card::toString)
	            .collect(Collectors.toList());
	}
	
}
