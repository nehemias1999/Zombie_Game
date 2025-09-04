package com.zombie.model.entities;

import java.io.Serializable;
import java.util.UUID;

import com.zombie.model.enumerations.CardType;

public class Card implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private CardType type;
	private Integer number;
	
	public Card(
			CardType type, 
			int number) {
		this.id = UUID.randomUUID();
		this.type = type;
		this.number = number;
	}
	
	public UUID getId() {
		return this.id;
	}

	public CardType getType() {
		return this.type;
	}
	
	public Integer getNumber() {
		return this.number;
	}
		
	public boolean isJoker() {
		return (this.type == CardType.JOKER);
	}
		
	@Override
	public String toString() {
		if(type != CardType.JOKER) {
			return "" + type + " " + number;
		} else {
			return type.toString();
		}
	}
		
}

