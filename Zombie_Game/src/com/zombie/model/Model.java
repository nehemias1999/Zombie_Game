package com.zombie.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.UUID;

import com.zombie.interfaces.IModel;
import com.zombie.model.entities.Card;
import com.zombie.model.entities.Deck;
import com.zombie.model.entities.Player;
import com.zombie.model.enumerations.GeneralEvent;
import com.zombie.model.enumerations.PlayerEvent;
import com.zombie.model.serialization.SerializationManager;
import com.zombie.resources.Message;

import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Model extends ObservableRemoto implements IModel, Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 4;

	private final ArrayList<IObservadorRemoto> observers;
	
	private int actualPlayersQuantity = -1;
	private List<Player> players;
	private Deck deck;
	private Stack<Card> couplesDeck;
	
	private Integer actualPlayerPosition = 0;
	
	private int waitingPlayers = 0;
	
	private SerializationManager serializationManager;
	private List<Player> playersToBeReassigned;
		
	public Model() {
		this.observers = new ArrayList<>();
		this.players = new ArrayList<Player>();
		this.serializationManager = new SerializationManager();
	}
	
	// ---
	
	@Override
	public void agregarObservador(IObservadorRemoto remoteObserver) throws RemoteException {
		this.observers.add((IObservadorRemoto) remoteObserver);
	}
	
	@Override
	public void notificarObservadores(Object objeto) throws RemoteException {
		for(IObservadorRemoto observadorRemoto: observers) {
			observadorRemoto.actualizar(null, objeto);
		}
	}
	
	// ---
	
	@Override
	public List<Player> getPlayers() throws RemoteException {
		return this.players;
	}
	
	@Override
	public int getActualPlayerPosition() throws RemoteException {
		return this.actualPlayerPosition;
	}
	
	@Override
	public Stack<Card> getCouplesDeck() throws RemoteException {
		return this.couplesDeck;
	}
			
	@Override
	public int getMinimumNumberPlayer() throws RemoteException {
		return Model.MIN_PLAYERS;
	}
	
	@Override
	public int getMaximumNumberPlayer() throws RemoteException {
		return Model.MAX_PLAYERS;
	}
	
	@Override
	public UUID getActualPlayerId() throws RemoteException {
		return players.get(actualPlayerPosition).getId();
	}
			
	private Player getActualPlayer() {
		return players.get(actualPlayerPosition);
	}
		
	@Override
	public boolean isQuantityPlayersDefined() throws RemoteException {
		return !(actualPlayersQuantity == -1);
	}
	
	/*
	 * DEFINE PLAYERS QUANTITY
	 */
	
	@Override
	public Message defineMaximumPlayersQuantity(Integer playersQuantity) throws RemoteException {
		if(playersQuantity < MIN_PLAYERS) {
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.ERROR_MINIMUM_PLAYERS_LIMIT)
				    .build();
		} 
				
		if(playersQuantity > MAX_PLAYERS) {
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.ERROR_MAXIMUM_PLAYERS_LIMIT)
				    .build();
		}
		
		this.actualPlayersQuantity = playersQuantity;
		
		return new Message
				.Builder()
			    .put("PlayerEvent", PlayerEvent.SHOW_LOADED_PLAYER_PANEL)
			    .build();
	}

	/*
	 * LOAD PLAYER NAME
	 */
	
 	private Boolean playerNameValidation(String playerName) {
	    if (playerName == null || playerName.isBlank()) {
	        return false;
	    }

	    return players
	        .stream()
	        .map(player -> player.getName().trim().toLowerCase())
	        .noneMatch(name -> name.equals(playerName));
	}
 	
 	private void dealCards() {
        while (!this.deck.isEmpty()) {
            for (Player player : players) {
                if (this.deck.isEmpty()) {
                    break;
                } 
                player.addCard(deck.getTopCard());        
            }
        }
    }
 	
	private void initialPlayerDiscard() {
		for(Player player: players) {
			couplesDeck.addAll(player.discard());
		}
	}

	@Override
	public Message addPlayer(String playerName) throws RemoteException {
		if (this.players.size() == Model.MAX_PLAYERS) { 
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.ERROR_MAXIMUM_PLAYERS_LIMIT)
				    .build();
		}
		
		if (!this.playerNameValidation(playerName)) { 
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.ERROR_PLAYER_VALIDATION)
				    .build();
		}
		
		Player player = new Player(playerName);
		players.add(player); 
		
		if(this.players.size() < actualPlayersQuantity) {
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.SHOW_WAITING_PLAYERS_PANEL)
				    .put("id", player.getId())
				    .build();
		}
		
		deck = new Deck();
		couplesDeck = new Stack<>();
		dealCards();
		initialPlayerDiscard();
		this.notificarObservadores(GeneralEvent.SHOW_LOADED_PLAYER_PANEL);
		return new Message
				.Builder()
			    .put("PlayerEvent", PlayerEvent.GLOBAL_EVENT)
			    .put("id", player.getId())
			    .build();

	}
		
	/*
	 * START OF ROUND
	 */

	@Override
	public Message startRound() throws RemoteException {
		if((waitingPlayers + 1) < actualPlayersQuantity) {
			waitingPlayers ++;
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.SHOW_WAITING_PLAYERS_PANEL)
				    .build();
		}
		waitingPlayers = 0;
		this.notificarObservadores(GeneralEvent.SHOW_ROUND_PLAYER_PANEL);
		return new Message
				.Builder()
			    .put("PlayerEvent", PlayerEvent.GLOBAL_EVENT)
			    .build();
	}
	
	@Override
	public List<String> getPlayersNames() throws RemoteException {
		return players
				.stream()
				.map(player -> player.getName())
				.toList();
	}
	    
	@Override
	public String getActualPlayerName() throws RemoteException {
		return getActualPlayer().getName();
	}

	@Override
	public List<Card> getLastTwoCardsFromCouplesDeck() throws RemoteException {
	    if (couplesDeck.size() >= 2) {
	        Card lastCard = couplesDeck.getLast(); 
	        Card beforeLastCard = couplesDeck.get(couplesDeck.size() - 1); 

	        return List.of(beforeLastCard, lastCard);
	    } 
	        
	    return List.of(); 
	    
	}
	
	private Player getPlayer(UUID id) {
    return players.stream()
                  .filter(player -> player.getId().equals(id))
                  .findFirst()
                  .orElseThrow(() ->
                      new NoSuchElementException("There is no player with id " + id)
                  );
}	

	@Override
	public List<Card> getPlayerDeck(UUID id) throws RemoteException {
		return getPlayer(id).getDeck();
	}
	
	private int rightPlayerPosition() {
    	int rightPlayerPosition = ((actualPlayerPosition - 1) != -1)? (actualPlayerPosition - 1): (actualPlayerPosition - 1);
    	
    	while(!players.get(rightPlayerPosition).isActive()) {
    		rightPlayerPosition = ((actualPlayerPosition - 1) != -1)? (actualPlayerPosition - 1): (actualPlayerPosition - 1);
    	}
    	
        return rightPlayerPosition;
	}
		
	private Player getRightPlayer() {
		return players.get(rightPlayerPosition());
	}

	@Override
	public int getCardsQuantityFromRightPlayer() throws RemoteException {
		return getRightPlayer().getDeck().size();
	}
		
    private Integer nextActivePlayerPosition(Integer actualPosition) {
    	
    	int nextPosition = (actualPosition + 1) % actualPlayersQuantity;
    	
    	while(!players.get(nextPosition).isActive()) {
    		nextPosition = (actualPosition + 1) % actualPlayersQuantity;
    	}
    	
        return nextPosition;
    }
    
    private Boolean isMoreThanOneActivePlayer() {
        long activePlayersQuantity = players.stream()
		                              		.filter(Player::isActive)
		                              		.count();
        return activePlayersQuantity > 1;
    }
	    
    @Override
	public Message getRightPlayerCard(int indexRightPlayerCardDeck) throws RemoteException {
	
		Card cardToRemove = getRightPlayer().getDeck().get(indexRightPlayerCardDeck - 1);
		getRightPlayer().removeCard(cardToRemove);
		
		getActualPlayer().addCard(cardToRemove);
		couplesDeck.addAll(getActualPlayer().discard());
				
		if(getActualPlayer().getDeck().isEmpty()) {
			getActualPlayer().setIsActive(false);
		}
		
		if(isMoreThanOneActivePlayer()) {
			this.actualPlayerPosition = nextActivePlayerPosition(actualPlayerPosition);
			notificarObservadores(GeneralEvent.CONTINUE_NEXT_ROUND_TURN);
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.GLOBAL_EVENT)
				    .build();
		}		
		
		notificarObservadores(GeneralEvent.END_ROUND);
		return new Message
				.Builder()
			    .put("PlayerEvent", PlayerEvent.GLOBAL_EVENT)
			    .build();
	}
	
	/*
	 * END OF ROUND
	 */
	
	@Override
	public String getLostPlayerName() throws RemoteException {
	    Player lastActivePlayer = players.stream()
	                                     .filter(Player::isActive)
	                                     .findFirst()
	                                     .orElseThrow(() ->
		                                     new NoSuchElementException("There is no active player")
		                                 );

	    return lastActivePlayer.getName();
	}
	
	@Override
	public boolean isPersistedGame() throws RemoteException {
		return this.serializationManager.isObjectSaved();
	}
	
	private void resetGame() throws RemoteException {
		this.actualPlayersQuantity = -1;
		this.players.clear();
		this.couplesDeck.clear();
		this.actualPlayerPosition = 0;
		this.waitingPlayers = 0;
	}
	
	@Override
	public Message endGame() throws RemoteException {
		if((waitingPlayers ++) < actualPlayersQuantity) {
			waitingPlayers ++;
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.SHOW_WAITING_PLAYERS_PANEL)
				    .build();
		}
		
		waitingPlayers = 0;
		resetGame();
		this.notificarObservadores(GeneralEvent.SHOW_MAIN_MENU_PANEL);
		return new Message
				.Builder()
			    .put("PlayerEvent", PlayerEvent.GLOBAL_EVENT)
			    .build();
	}
	
	/*
	 * SERIALIZATION
	 */
	
	@Override
	public void persistGame() throws RemoteException {
		if(this.serializationManager.persistObject(this)) {
			this.notificarObservadores(GeneralEvent.GAME_SAVED);
		} else {
			this.notificarObservadores(GeneralEvent.ERROR_GAME_SAVED);
		}
	}
	
	private void recoverGame() throws RemoteException {
		IModel recoveredGame = this.serializationManager.recoverObject();
		
		if(recoveredGame == null) {
			this.notificarObservadores(GeneralEvent.ERROR_GAME_RECOVERED);
			return;
		}
			
		this.players = recoveredGame.getPlayers();
		this.playersToBeReassigned = this.players;
		this.actualPlayersQuantity = this.players.size();
		this.actualPlayerPosition = recoveredGame.getActualPlayerPosition();
		this.waitingPlayers = 0;
		this.couplesDeck = recoveredGame.getCouplesDeck();
				
	}
		
	@Override
	public Message continuePersistedGame() throws RemoteException {
		
		if(!isPersistedGame()) {
			recoverGame();
			//this.administradorSerializacion.eliminarPartida();
		}
		
		if((waitingPlayers ++) < actualPlayersQuantity) {
			waitingPlayers ++;
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.SHOW_WAITING_PLAYERS_PANEL)
				    .build();
		}
		
		waitingPlayers = 0;
		this.notificarObservadores(GeneralEvent.SHOW_GAME_RECOVERED_PLAYERS_NAMES_PANEL);
		return new Message
				.Builder()
			    .put("PlayerEvent", PlayerEvent.GLOBAL_EVENT)
			    .build();
	}

	@Override
	public List<Player> getGamePersistedPlayers() throws RemoteException {
		return this.playersToBeReassigned;
	}
	
	@Override
	public Message reassignGamePersistPlayer(UUID id) throws RemoteException {
		
		Player playerToEliminate = players.stream()
									      .filter(player -> player.getId().equals(id))
									      .findFirst()
									      .orElse(null);
		
	    playersToBeReassigned.remove(playerToEliminate);
		
		if(!playersToBeReassigned.isEmpty()) {
			this.notificarObservadores(GeneralEvent.SHOW_GAME_RECOVERED_PLAYERS_NAMES_PANEL);
			return new Message
					.Builder()
				    .put("PlayerEvent", PlayerEvent.SHOW_WAITING_PLAYERS_PANEL)
				    .build();
		}
		
		this.notificarObservadores(GeneralEvent.SHOW_ROUND_PLAYER_PANEL);
		return new Message
				.Builder()
			    .put("PlayerEvent", PlayerEvent.GLOBAL_EVENT)
			    .build();
	}
	
}
