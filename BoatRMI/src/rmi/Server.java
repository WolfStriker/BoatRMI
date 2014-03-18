package rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


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
		System.out.println("Serveur RMI Started");
		RMIObject o = new RMIObject();
		srv.export(o, "hello");
		
		try 
		{
			Remote r = srv.lookup("hello");
			if(r != null)
			{
				RMIObject test = (RMIObject) r;
				System.out.println(test.hello());
			}
			else throw new Exception("lookup hello null");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
