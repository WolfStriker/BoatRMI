package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {

	public static void main(String[] args) {
		
		try 
		{
			Registry reg = LocateRegistry.getRegistry(2000);
			Remote r = reg.lookup("RMIDBManager");
			if(r != null)
			{
				IRMIDB test = (IRMIDB) r;
				test.test();
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
