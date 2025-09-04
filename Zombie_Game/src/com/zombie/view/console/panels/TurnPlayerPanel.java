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
                viewAdministrator.showErrorMessage("Debes ingresar una carta a elegir del jugador de la derecha.");
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
                        viewAdministrator.showErrorMessage("Opción inválida. Elige entre y " + rightPlayerCardsQuantity);
            		}            		
                               		
            	} catch (NumberFormatException ex) {
					 viewAdministrator.showErrorMessage("Formato inválido: ingresa un número.");
				}
                
            }
            
            frame.clearInput();
            
        });  
    }
        
    private void getPanel() {
    	frame.appendLine("=== TURNO JUGADOR === (Ingrese 0 para pausar la partida)");
        frame.appendLine("");
        frame.appendLine("Turno del jugador: " + actualPlayerName);
        frame.appendLine("");
        frame.appendLine("");
        if(!couplesDeck.isEmpty()) {
        	frame.appendLine("Mazo parejas: " + couplesDeck.get(0).toString() + ", " + couplesDeck.get(1).toString() + "");
        } else {
        	frame.appendLine("Mazo parejas: No hay cartas");
        }
        frame.appendLine("");
        frame.appendLine("");
        frame.appendLine("Cartas jugador: " + playerDeck);
        frame.appendLine("");
        frame.appendLine("Ingrese un numero de la carta a obtener del jugador derecho: (Entre 1 y " + rightPlayerCardsQuantity + ")");
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
