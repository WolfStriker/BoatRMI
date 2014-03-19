package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;

public interface IConnBDD extends Remote{
	
	public void test() throws RemoteException;
	
	public boolean getProperties() throws RemoteException;

	public boolean connection()throws RemoteException;
	public boolean closeConnection() throws RemoteException;
	public Connection getConnection() throws RemoteException;
}
