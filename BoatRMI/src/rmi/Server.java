package rmi;

import impl.BoatImpl;
import impl.GroupImpl;
import impl.UserImpl;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Server{
	
	public static void main(String [] args) 
	{
		try{
			LocateRegistry.createRegistry(2000);
			System.out.println("---------------------------SERVEUR-----------------------------");
			System.out.println("Serveur RMI Started");
			
			BoatImpl boatImpl = new BoatImpl();
			String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/Boat";
			System.out.println("Enregistrement de l'objet avec l'url : " + url);
			Naming.rebind(url, boatImpl);
			
			GroupImpl groupImpl = new GroupImpl();
			url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/Group";
			System.out.println("Enregistrement de l'objet avec l'url : " + url);
			Naming.rebind(url, groupImpl);
			
			UserImpl userImpl = new UserImpl();
			url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/User";
			System.out.println("Enregistrement de l'objet avec l'url : " + url);
			Naming.bind(url, userImpl);
			
			System.out.println("---------------------------------------------------------------");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
