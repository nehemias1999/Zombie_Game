package com.zombie.view.administrators;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.zombie.interfaces.IController;
import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.model.dto.CardDTO;
import com.zombie.model.dto.PlayerDTO;
import com.zombie.view.console.MainJFrame;
import com.zombie.view.console.panels.DefinePlayersQuantityPanel;
import com.zombie.view.console.panels.EndOfRoundPanel;
import com.zombie.view.console.panels.LoadPlayerNamePanel;
import com.zombie.view.console.panels.LoadedPlayersNamesPanel;
import com.zombie.view.console.panels.MainMenuPanel;
import com.zombie.view.console.panels.PersistedGamePanel;
import com.zombie.view.console.panels.PersistedGamePlayersNamesPanel;
import com.zombie.view.console.panels.SpectatorPlayerPanel;
import com.zombie.view.console.panels.TurnPlayerPanel;
import com.zombie.view.console.panels.WaitingPlayersPanel;

public class ConsoleViewAdministrator implements IView {
		
	private final Map<String, Object> panels = new LinkedHashMap<>();
	private IController controller;
	private MainJFrame mainFrame;
	private IPanel actualPanel;
	private MainMenuPanel mainMenuPanel;
	private DefinePlayersQuantityPanel definePlayersQuantityPanel;
	private WaitingPlayersPanel waitingPlayerPanel;
	private LoadPlayerNamePanel loadPlayerNamePanel;
	private LoadedPlayersNamesPanel loadedPlayersNamesPanel;
	private TurnPlayerPanel turnPlayerPanel;
	private SpectatorPlayerPanel spectatorPlayerPanel;
	private EndOfRoundPanel endOfRoundPanel;
	private PersistedGamePanel persistedGamePanel;
	private PersistedGamePlayersNamesPanel persistedGamePlayersNamesPanel;
	
	public ConsoleViewAdministrator() {
		this.mainFrame = new MainJFrame();
		
		mainMenuPanel = new MainMenuPanel(this, mainFrame);
		definePlayersQuantityPanel = new DefinePlayersQuantityPanel(this, mainFrame);
		waitingPlayerPanel = new WaitingPlayersPanel(this, mainFrame);
		loadPlayerNamePanel = new LoadPlayerNamePanel(this, mainFrame);
		loadedPlayersNamesPanel = new LoadedPlayersNamesPanel(this, mainFrame);
		turnPlayerPanel = new TurnPlayerPanel(this, mainFrame);
		spectatorPlayerPanel = new SpectatorPlayerPanel(this, mainFrame);	
		endOfRoundPanel = new EndOfRoundPanel(this, mainFrame);
		persistedGamePanel = new PersistedGamePanel(this, mainFrame);
		persistedGamePlayersNamesPanel = new PersistedGamePlayersNamesPanel(this, mainFrame);
		
		addPanel("Main Menu Panel", mainMenuPanel);
		addPanel("Define Players Quantity Panel", definePlayersQuantityPanel);
		addPanel("Waiting Player Panel", waitingPlayerPanel);
		addPanel("Load Player Name Panel", loadPlayerNamePanel);
		addPanel("Loaded Players Names Panel", loadedPlayersNamesPanel);
		addPanel("Turn Player Panel", turnPlayerPanel);
		addPanel("Spectator Player Panel", spectatorPlayerPanel);
		addPanel("End Of Round Panel", endOfRoundPanel);
		addPanel("Persisted Game Panel", persistedGamePanel);				
		addPanel("Persisted Game Players Names Panel", persistedGamePlayersNamesPanel);		
		
		showFrame();
							        
	}
	
	public IPanel getPanelActual() {
		return this.actualPanel;
	}
		
    public void addPanel(String nombre, Object panel) {
    	panels.put(nombre, panel);
    }

    public void showPanel(String nombre) {
        IPanel panel = (IPanel) panels.get(nombre);
        actualPanel = panel;
        panel.showPanel();
    }

    public void showFrame() {
        mainFrame.showFrame();
    }
	                        
	@Override
	public void setController(IController controller) {
		this.controller = controller;		
	}	
	
	@Override
	public int getMinimumNumberPlayer() {
		return controller.getMinimumNumberPlayer();
	}
	
	@Override
	public int getMaximumNumberPlayer() {
		return controller.getMaximumNumberPleyer();
	}
		
	@Override
	public void showMainMenuPanel() {
		showPanel("Main Menu Panel");
	}
	
	@Override
	public void startGame() {
		controller.startGame();
	}
	
	@Override
	public void quitGame() {
		System.exit(0);
	}
	
	@Override
	public void showDefinePlayersQuantityPanel() {
		showPanel("Define Players Quantity Panel");
	}
	
	@Override
	public void getDataDefinePlayersQuantity(String playersQuantity) {
		controller.getDataDefinePlayersQuantity(playersQuantity);
	}
	
	@Override
	public void showWaitingPlayersPanel() {
		showPanel("Waiting Player Panel");
	}
	
	@Override
	public void showLoadPlayerPanel() {
		showPanel("Load Player Name Pane");
	}
	
	@Override
	public void getDataLoadPlayer(String playerName) {
		controller.getDataLoadPlayer(playerName);
	}
		
	@Override
	public void showLoadedPlayersPanel() {
		showPanel("Loaded Players Names Panel");
	}
	
	@Override
	public List<String> getPlayersNames() {
		return controller.getPlayersNames();
	}
	
	@Override
	public void startRound() {
		controller.startRound();
	}	
			
	@Override
	public void showTurnPlayerPanel() {
		showPanel("Turn Player Panel");
	}
	
	@Override
	public void showSpectatorPlayerPanel() {
		showPanel("Spectator Player Panel");
	}
	
	@Override
	public String getActualPlayerName() {
		return controller.getActualPlayerName();
	}

	@Override
	public List<CardDTO> getLastTwoCardsFromCouplesDeck() {
		return controller.getLastTwoCardsFromCouplesDeck();
	}

	@Override
	public List<CardDTO> getPlayerDeck() {
		return controller.getPlayerDeck();
	}

	@Override
	public int getCardsQuantityFromRightPlayer() {
		return controller.getCardsQuantityFromRightPlayer();
	}
	
	@Override
	public void getDataTurnPlayer(String indexRightPlayerCardDeck) {
		controller.getDataTurnPlayer(indexRightPlayerCardDeck);
	}
	
	@Override
	public void showEndRoundPanel() {
		showPanel("End Of Round Panel");
	}
	
	@Override
	public String getLostPlayerName() {
		return controller.getLostPlayerName();
	}
	
	@Override
	public void returnToMainMenu() {
		controller.returnToMainMenu();
	}
	
	/*
	 * SERIALIZACION
	 */
	
	@Override
	public void persistGame() {
		controller.persistGame();
	}
	
	@Override
	public void showPersistGamePanel() {
		showPanel("Persisted Game Panel");
	}
	
	@Override
	public boolean isPersistedGame() {
		return controller.isPersistedGame();
	}
	
	@Override
	public void continuePersistedGame() {
		controller.continuePersistedGame();
	}
	
	@Override
	public void showPersistedGamePlayersNamesPanel() {
		showPanel("Persisted Game Players Names Panel");
	}
		
	@Override
	public List<PlayerDTO> getPersistedGamePlayers() {
		return controller.getPersistedGamePlayers();
	}
	
	@Override
	public void getDataPersistedGamePlayers(UUID id) {
		controller.getDataPersistedGamePlayers(id);
	}
	
	@Override
	public void showErrorMessage(String mensaje) {
		actualPanel.showErrorMessage(mensaje);
		actualPanel.showPanel();
	}

}

