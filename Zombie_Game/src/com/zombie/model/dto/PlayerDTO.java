package com.zombie.model.dto;

import java.util.UUID;

public class PlayerDTO {

	private UUID id;
	private String name;
	
	public PlayerDTO(
			UUID id, 
			String name) {
		this.id = id;
		this.name = name;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
