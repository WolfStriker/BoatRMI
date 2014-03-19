package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import bean.Groupe;

public interface IGroup extends Remote{

	public int getIdGroup(String group)throws RemoteException;
	
	public boolean addGroupe(Groupe g) throws RemoteException;

	public boolean removeGroupe(String nom) throws RemoteException;
}
