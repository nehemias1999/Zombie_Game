module Zombie_Game {

	requires LibreriaRMIMVC;
	requires java.rmi;
	requires java.desktop;
	
	exports com.zombie.interfaces to java.rmi;
	exports com.zombie.model to java.rmi;

	opens com.zombie.interfaces to java.rmi;
    opens com.zombie.model to java.rmi;

}