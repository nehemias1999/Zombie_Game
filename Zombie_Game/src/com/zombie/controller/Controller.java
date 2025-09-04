package com.zombie.controller;

import java.rmi.RemoteException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.zombie.interfaces.IController;
import com.zombie.interfaces.IModel;
import com.zombie.interfaces.IView;
import com.zombie.model.dto.CardDTO;
import com.zombie.model.dto.PlayerDTO;
import com.zombie.model.entities.Card;
import com.zombie.model.enumerations.GeneralEvent;
import com.zombie.model.enumerations.PlayerEvent;
import com.zombie.resources.Message;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public class Controller implements IController, IControladorRemoto {
	
	private IModel model;
	private IView view;
	private UUID assignedPlayer;
	
	public Controller () {
	}

	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.model = (IModel) modeloRemoto;
	}
	
	@Override
	public void setView(IView view) {
		this.view = view;
	}
	
	public void setAssignedPlayer(UUID playerId) {
		this.assignedPlayer = playerId;
	}
	
	@Override
	public int getMinimumNumberPlayer() {
		try {
			return model.getMinimumNumberPlayer();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public int getMaximumNumberPleyer() {
		try {
			return model.getMaximumNumberPlayer();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public void showMainMenuPanel() {
		view.showMainMenuPanel();
	}
	
	@Override
	public void startGame() {
		try {
			
			if(!model.isQuantityPlayersDefined()) {
				view.showDefinePlayersQuantityPanel();
			} else {
				view.showLoadPlayerPanel();
			}
			
		} catch (RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}
	}
		
	@Override
	public void showDefinePlayersQuantityPanel() {
		view.showDefinePlayersQuantityPanel();
	}

	@Override
	public void getDataDefinePlayersQuantity(String playersQuantity) {
		try {
			
			if(playersQuantity == null || playersQuantity.isBlank()) {
				view.showErrorMessage("The number of players cannot be empty");
				return;
			}			
			
			int playersQuantityInt = Integer.parseInt(playersQuantity.trim());
						
			Message message = model.defineMaximumPlayersQuantity(playersQuantityInt);
			
			PlayerEvent playerEvent = message.get("PlayerEvent", PlayerEvent.class);
			 
			if(playerEvent == PlayerEvent.ERROR_MINIMUM_PLAYERS_LIMIT) {
				view.showErrorMessage("The number of players must be greater than " + getMinimumNumberPlayer());
				return;
			}
			
			if(playerEvent == PlayerEvent.ERROR_MAXIMUM_PLAYERS_LIMIT) {
				view.showErrorMessage("The number of players must be less than " + getMaximumNumberPleyer());
				return;
			}
			
			if(playerEvent == PlayerEvent.SHOW_LOADED_PLAYER_PANEL) {
				view.showLoadPlayerPanel();
			}
			
		} catch (NumberFormatException e) {
			view.showErrorMessage("Invalid number entered");
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}
	}	
	
	@Override
	public void showWaitingPlayersPanel() {
		view.showWaitingPlayersPanel();
	}
	
	@Override
	public void showLoadPlayerPanel() {
		view.showLoadPlayerPanel();
	}

	@Override
	public void getDataLoadPlayer(String playerName) {
		try {
			
			if(playerName == null || playerName.isBlank()) {
				view.showErrorMessage("The name cannot be empty");
				return;
			}			
						
			Message message = model.addPlayer(playerName.trim().toLowerCase());
						
			PlayerEvent playerEvent = message.get("PlayerEvent", PlayerEvent.class);
			
			if(playerEvent == PlayerEvent.ERROR_MAXIMUM_PLAYERS_LIMIT) {
				view.showErrorMessage("No more players can enter");
				return;
			}
			
			if(playerEvent == PlayerEvent.ERROR_PLAYER_VALIDATION) {
				view.showErrorMessage("The name entered is not valid");
				return;
			}
			
			UUID playerId = message.get("id", UUID.class);
			setAssignedPlayer(playerId);
						
			if(playerEvent == PlayerEvent.SHOW_WAITING_PLAYERS_PANEL) {
				view.showWaitingPlayersPanel();
			}
					
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}
	}
		
	@Override
	public void showLoadedPlayersPanel() {
		view.showLoadedPlayersPanel();
	}
	
	@Override
	public List<String> getPlayersNames() {
		try {
			return model.getPlayersNames();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return List.of();
		}
	}
	
	@Override
	public void startRound() {
		try {
			
			Message message = model.startRound();
			
			PlayerEvent eventoJugador = message.get("PlayerEvent", PlayerEvent.class);
			
			if(eventoJugador == PlayerEvent.SHOW_WAITING_PLAYERS_PANEL) {
				view.showWaitingPlayersPanel();
				return;
			}
			
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}
	}
	
	@Override
	public void showRoundPanel() {
		try {

			if(model.getActualPlayerId().equals(assignedPlayer)) {
				view.showTurnPlayerPanel();
			} else {
				view.showSpectatorPlayerPanel();
			}	
			
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}	
	}
	
	@Override
	public String getActualPlayerName() {
		try {
			return model.getActualPlayerName();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return "";
		}
	}

	@Override
	public List<CardDTO> getLastTwoCardsFromCouplesDeck() {
		try {
			return model.getLastTwoCardsFromCouplesDeck()
						.stream()
						.map(card -> new CardDTO(
								card.getId(), 
								card.getType(), 
								card.getNumber()))
						.toList();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return List.of();
		}
	}

	@Override
	public List<CardDTO> getPlayerDeck() {
		try {
			
			List<Card> playerDeck = model.getPlayerDeck(assignedPlayer);
			
			return playerDeck
					.stream()
					.map(card -> new CardDTO(
							card.getId(), 
							card.getType(), 
							card.getNumber()))
					.toList();

		} catch(NoSuchElementException e) {
			view.showErrorMessage("The requested user was not found");
			return List.of();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return List.of();
		}
	}

	@Override
	public int getCardsQuantityFromRightPlayer() {
		try {
			return model.getCardsQuantityFromRightPlayer();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public void getDataTurnPlayer(String indexRightPlayerCardDeck) {
		try {
			
			if(indexRightPlayerCardDeck == null || indexRightPlayerCardDeck.isBlank()) {
				view.showErrorMessage("The index of the chosen card cannot be empty");
				return;
			}	
			
			Integer index = Integer.parseInt(indexRightPlayerCardDeck.trim());
			
			model.getRightPlayerCard(index);
						
		} catch (NumberFormatException e) {
			view.showErrorMessage("Invalid number entered");	
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}
	}
	
	@Override
	public void showEndRoundPanel() {
		view.showEndRoundPanel();
	}	
	
	@Override
	public String getLostPlayerName() {
		try {
			return model.getLostPlayerName();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return "";
		}
	}
	
	@Override
	public void returnToMainMenu() {
		try {
			
			Message message = model.endGame();
			
			PlayerEvent playerEvent = message.get("PlayerEvent", PlayerEvent.class);
			
			if(playerEvent == PlayerEvent.SHOW_WAITING_PLAYERS_PANEL) {
				view.showWaitingPlayersPanel();
				return;
			}
			
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}
	}
	
	/*
	 * SERIALIZACION
	 */
	
	@Override
    public void persistGame() {   	
    	try {
    		this.model.persistGame();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}
    }
       
	@Override
	public void showPersistGamePanel() {
		view.showPersistGamePanel();
	}
	
	@Override
	public boolean isPersistedGame() {
		try {
    		return model.isPersistedGame();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return false;
		}
	}
	
	@Override
	public void continuePersistedGame() {
		try {
			
			Message message = model.continuePersistedGame();
			
			PlayerEvent playerEvent = message.get("PlayerEvent", PlayerEvent.class);
			
			if(playerEvent == PlayerEvent.SHOW_WAITING_PLAYERS_PANEL) {
				view.showWaitingPlayersPanel();
				return;
			}
			
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}
	}
		
	@Override
	public void showPersistedGamePlayersNamesPanel() {
		view.showPersistedGamePlayersNamesPanel();
	}
	
	@Override
	public List<PlayerDTO> getPersistedGamePlayers() {
		try {
    		return model.getPlayers()
	    				.stream()
	    				.map(player -> new PlayerDTO(
	    						player.getId(), 
	    						player.getName()))
	    				.toList();
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
			return List.of();
		}
	}
	
	@Override
	public void getDataPersistedGamePlayers(UUID id) {
		try {
			
			if(id == null) {
				view.showErrorMessage("The identifier cannot be empty");
				return;
			}			
						
			setAssignedPlayer(id);
			
			Message message = model.reassignGamePersistPlayer(id);

			PlayerEvent playerEvent = message.get("PlayerEvent", PlayerEvent.class);
			
			if(playerEvent == PlayerEvent.SHOW_WAITING_PLAYERS_PANEL) {
				view.showWaitingPlayersPanel();
				return;
			}
	
		} catch(RemoteException e) {
			view.showErrorMessage("Error: Remote Exception " + e.getMessage());
		}
	}
	
	@Override
	public void actualizar(IObservableRemoto arg0, Object object) throws RemoteException {
		
		GeneralEvent generalEvent = (GeneralEvent) object;
		
		switch(generalEvent) {				
			case SHOW_LOADED_PLAYER_PANEL:
				showLoadedPlayersPanel();
				break;
			case SHOW_ROUND_PLAYER_PANEL:
				showRoundPanel();
				break;
			case CONTINUE_NEXT_ROUND_TURN:
				showRoundPanel();
				break;
			case END_ROUND:
				showEndRoundPanel();
				break;
			case SHOW_MAIN_MENU_PANEL:
				showMainMenuPanel();
				break;
			case GAME_SAVED:
				showPersistGamePanel();
				break;
			case SHOW_GAME_RECOVERED_PLAYERS_NAMES_PANEL:
				if(this.assignedPlayer == null) {
					showPersistedGamePlayersNamesPanel();
				}
				break;
			default:
				break;				
		}	
	}
	
}
