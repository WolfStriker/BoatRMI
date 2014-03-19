package interfaces;

import java.rmi.RemoteException;
import java.util.ArrayList;

import bean.Boat;

public interface IBoat {

	public ArrayList<Boat> afficheAllBoat() throws RemoteException;
	
	public ArrayList<Boat> searchBoat(String note) throws RemoteException;
	public ArrayList<String> searchBoatByGroup(String group) throws RemoteException;
	
	public boolean addBoat(Boat b) throws RemoteException;

	public boolean removeBoat(String nom) throws RemoteException;
	
	public boolean updateBoat(int id, String notice, String nom, String photo) throws RemoteException;
}
