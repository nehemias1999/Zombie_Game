package com.zombie.interfaces;

import java.util.List;
import java.util.UUID;

import com.zombie.model.dto.CardDTO;
import com.zombie.model.dto.PlayerDTO;

public interface IView {
	void setController(IController controller);
	int getMinimumNumberPlayer();
	int getMaximumNumberPlayer();
	void showMainMenuPanel();
	void startGame();
	void quitGame();
	void showDefinePlayersQuantityPanel();
	void getDataDefinePlayersQuantity(String playersQuantity);
	void showWaitingPlayersPanel();
	void showLoadPlayerPanel();
	void getDataLoadPlayer(String playerName);
	void showLoadedPlayersPanel();
	List<String> getPlayersNames();
	void startRound();
	void showTurnPlayerPanel();
	void showSpectatorPlayerPanel();
	String getActualPlayerName();
	List<CardDTO> getLastTwoCardsFromCouplesDeck();
	List<CardDTO> getPlayerDeck();
	int getCardsQuantityFromRightPlayer();
	void getDataTurnPlayer(String indexRightPlayerCardDeck);
	void showEndRoundPanel();
	String getLostPlayerName();
	void returnToMainMenu();
	void persistGame();
	void showPersistGamePanel();
	boolean isPersistedGame();
	void continuePersistedGame();
	void showPersistedGamePlayersNamesPanel();
	List<PlayerDTO> getPersistedGamePlayers();
	void getDataPersistedGamePlayers(UUID id);
	void showErrorMessage(String message);
}
