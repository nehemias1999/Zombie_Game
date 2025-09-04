package com.zombie.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

import com.zombie.model.entities.Card;
import com.zombie.model.entities.Player;
import com.zombie.resources.Message;

public interface IModel extends Remote {
	List<Player> getPlayers() throws RemoteException;
	int getActualPlayerPosition() throws RemoteException;
	Stack<Card> getCouplesDeck() throws RemoteException;
	int getMinimumNumberPlayer() throws RemoteException;
	int getMaximumNumberPlayer() throws RemoteException;
	UUID getActualPlayerId() throws RemoteException;
	boolean isQuantityPlayersDefined() throws RemoteException;
	Message defineMaximumPlayersQuantity(Integer playersQuantity) throws RemoteException;
	Message addPlayer(String playerName) throws RemoteException;
	Message startRound() throws RemoteException;
	List<String> getPlayersNames() throws RemoteException;
	String getActualPlayerName() throws RemoteException;
	List<Card> getLastTwoCardsFromCouplesDeck() throws RemoteException;
	List<Card> getPlayerDeck(UUID id) throws RemoteException;
	int getCardsQuantityFromRightPlayer() throws RemoteException;
	Message getRightPlayerCard(int indexRightPlayerCardDeck) throws RemoteException;
	String getLostPlayerName() throws RemoteException;
	boolean isPersistedGame() throws RemoteException;
	Message endGame() throws RemoteException;
	void persistGame() throws RemoteException;
	Message continuePersistedGame() throws RemoteException;
	List<Player> getGamePersistedPlayers() throws RemoteException;
	Message reassignGamePersistPlayer(UUID id) throws RemoteException;
}
