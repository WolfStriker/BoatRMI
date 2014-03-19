package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import bean.Boat;

public interface IMethod extends Remote{
	
	public String hello() throws RemoteException;

	public boolean addBoat() throws RemoteException;
	
	public ArrayList<Boat> listAllBoat() throws RemoteException;
	
	public ArrayList<Boat> listBoat(String groupe) throws RemoteException;
	
}
