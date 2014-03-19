package rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import db.manager.DB;
import db.manager.RMIDBManager;


public class Server{
	
	private Registry reg;
	private int port;

	public Server(int p){
		this.port = p;
	}
	
	public void init()
	{
		try 
		{
			reg = LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	//Met l'objet sur le bus
	public boolean export(Remote o, String name)
	{
		try 
		{
			reg.rebind(name, UnicastRemoteObject.exportObject(o, 0));
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Remote lookup(String ch)
	{
		try {
			return reg.lookup(ch);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String [] args)
	{
		Server srv = new Server(2000);
		srv.init();
		System.out.println("---------------------------SERVEUR-----------------------------");
		System.out.println("Serveur RMI Started");
		RMIDBManager o = new RMIDBManager();
		srv.export(o, "RMIDBManager");
		System.out.println("Serveur publish RMIDBManager Object");
		System.out.println("---------------------------------------------------------------");
		
	}
}
