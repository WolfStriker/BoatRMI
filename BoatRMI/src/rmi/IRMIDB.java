package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;

import bean.Boat;
import bean.Groupe;
import bean.User;

public interface IRMIDB extends Remote{
	
	public String hello() throws RemoteException;
	public void test() throws RemoteException;
	
	public boolean getProperties() throws RemoteException;

	public boolean connection()throws RemoteException;
	public boolean closeConnection() throws RemoteException;
	public Connection getConnection() throws RemoteException;
	
	public ArrayList<String> afficheTables() throws RemoteException;
	
	public ArrayList<String> afficheElementTable(String table) throws RemoteException;
	
	public ArrayList<String> search(String note) throws RemoteException;
	
	public boolean addBoat(Boat b) throws RemoteException;
	public boolean addGroupe(Groupe g) throws RemoteException;
	public boolean addUser(User u) throws RemoteException;
	
	public boolean removeBoat(String nom) throws RemoteException;
	public boolean removeGroupe(String nom) throws RemoteException;
	public boolean removeUser(String login) throws RemoteException;
	
	public boolean userExist(String login, String password) throws RemoteException;
	
	
	
}