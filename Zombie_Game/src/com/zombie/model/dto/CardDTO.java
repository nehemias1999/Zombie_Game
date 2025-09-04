package com.zombie.model.dto;

import java.io.Serializable;
import java.util.UUID;

import com.zombie.model.enumerations.CardType;

public class CardDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private CardType type;
	private Integer number;
	
	public CardDTO(
			UUID id, 
			CardType type, 
			int number) {
		this.id = id;
		this.type = type;
		this.number = number;
	}
	
	public UUID getId() {
		return id;
	}

	public CardType getType() {
		return type;
	}
	
	public Integer getNumber() {
		return number;
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
