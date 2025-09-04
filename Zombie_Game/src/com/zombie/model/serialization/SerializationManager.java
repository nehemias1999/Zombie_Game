package com.zombie.model.serialization;

import java.io.Serializable;

import com.zombie.interfaces.IModel;

public class SerializationManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Serializer  serializedGame = new Serializer("zombie.dat");

	public SerializationManager() {
	}
			
	public Boolean isObjectSaved() {
		return serializedGame.isObjectSaved();
	}
	
 	public Boolean persistObject(IModel model) {
		return serializedGame.persistObject(model);
	}
	
	public IModel recoverObject() {
		return (IModel) serializedGame.recoverObject();
	}
	
	public void removeObject() {
		serializedGame.removeObject();
	}
	
}
