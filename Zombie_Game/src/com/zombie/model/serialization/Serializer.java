package com.zombie.model.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer implements Serializable {

	private static final long serialVersionUID = 1L;
	private String archiveName;
	
	public Serializer(String archiveName) {
		this.archiveName = archiveName;
	}
	
	public Boolean isObjectSaved() {
		File archivo = new File(this.archiveName);
		
		return archivo.exists();
	}
		
	public Boolean persistObject(Object object) {
		try {
			
			new File(this.archiveName).delete();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(archiveName)); 
			objectOutputStream.writeObject(object);
			objectOutputStream.close();
			return true;
			
		} catch(Exception e) {
			return false;
		}
	}
	
	public Object recoverObject() {
		try {
			
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(archiveName)); 
			Object objetoRecuperado = objectInputStream.readObject();
			objectInputStream.close();

			return objetoRecuperado;
			
		} catch(Exception e) {
			return null;
		}
	}
	
	public void removeObject() {
		new File(this.archiveName).delete();
	}

}
