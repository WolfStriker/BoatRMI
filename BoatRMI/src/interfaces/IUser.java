package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import bean.User;

public interface IUser extends Remote{

	public boolean addUser(User u) throws RemoteException;
	
	public boolean removeUser(String login) throws RemoteException;
	
	public boolean userExist(String login) throws RemoteException;
}
