package com.zombie.model.enumerations;

import java.io.Serializable;

public enum GeneralEvent implements Serializable {	
	SHOW_LOADED_PLAYER_PANEL,
	SHOW_ROUND_PLAYER_PANEL,
	CONTINUE_NEXT_ROUND_TURN,
	END_ROUND,
	SHOW_MAIN_MENU_PANEL,
	GAME_SAVED,
	ERROR_GAME_SAVED,
	SHOW_GAME_RECOVERED_PLAYERS_NAMES_PANEL,
	ERROR_GAME_RECOVERED
}
