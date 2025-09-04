package com.zombie.view.console.panels;

import java.util.List;

import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.model.dto.CardDTO;
import com.zombie.view.console.MainJFrame;

public class TurnPlayerPanel implements IPanel {
	
	private IView viewAdministrator;
	private MainJFrame frame;
	
	private String actualPlayerName;
	private List<CardDTO> couplesDeck;
	private List<CardDTO> playerDeck;
	private int rightPlayerCardsQuantity;
	
	public TurnPlayerPanel(
			IView viewAdministrator, 
			MainJFrame frame) {
		this.viewAdministrator = viewAdministrator;
		this.frame = frame;        
	}
	
    private void obtenerDatosPanel() {
    	actualPlayerName = viewAdministrator.getActualPlayerName();
    	couplesDeck = viewAdministrator.getLastTwoCardsFromCouplesDeck();
    	playerDeck = viewAdministrator.getPlayerDeck();
    	rightPlayerCardsQuantity = viewAdministrator.getCardsQuantityFromRightPlayer();
    }
		
    private void startEnterActions() {
        frame.setEnterAction(e -> {
        	String raw = frame.getInputText();
            String input = raw == null ? "" : raw.trim();
            
            if (input.isEmpty()) {
                viewAdministrator.showErrorMessage("You must enter a card of the player's choice on your right.");
            } else {
            	
            	frame.appendLine("> " + input);
            	
            	try {
            		
            		int opcion = Integer.parseInt(input);
            		if(opcion == 0) {
            			viewAdministrator.persistGame();
            		}
            		if((opcion >= 1) && (opcion <= rightPlayerCardsQuantity)) {
            			viewAdministrator.getDataTurnPlayer(input);
            		} else {
                        viewAdministrator.showErrorMessage("Invalid option. Choose between and " + rightPlayerCardsQuantity);
            		}            		
                               		
            	} catch (NumberFormatException ex) {
					 viewAdministrator.showErrorMessage("Invalid format: Please enter a number.");
				}
                
            }
            
            frame.clearInput();
            
        });  
    }
        
    private void getPanel() {
    	frame.appendLine("=== PLAYER'S TURN === (Enter 0 to pause the game)");
        frame.appendLine("");
        frame.appendLine("Player's turn: " + actualPlayerName);
        frame.appendLine("");
        frame.appendLine("");
        if(!couplesDeck.isEmpty()) {
        	frame.appendLine("Couples deck: " + couplesDeck.get(0).toString() + ", " + couplesDeck.get(1).toString() + "");
        } else {
        	frame.appendLine("Couples deck: there're no cards");
        }
        frame.appendLine("");
        frame.appendLine("");
        frame.appendLine("Player's deck: " + playerDeck);
        frame.appendLine("");
        frame.appendLine("Enter a number of the card to be obtained from the right player: (Between 1 and " + rightPlayerCardsQuantity + ")");
	}
    
	@Override
    public void showPanel() {
		obtenerDatosPanel();
		startEnterActions();
        getPanel();
        frame.clearInput();
        frame.setEditabledInput(true);
    }
	
	@Override
	public void showErrorMessage(String message) {
		frame.appendLine(message);
	}
    
}
