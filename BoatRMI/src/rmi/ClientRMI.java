package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {

	public static void main(String[] args) {
		
		try 
		{
			System.out.println("---------------------------CLIENT-----------------------------");
			Registry reg = LocateRegistry.getRegistry(2000);
			Remote r = reg.lookup("RMIDBManager");
			if(r != null)
			{
				IRMIDB irmdb = (IRMIDB) r;
				System.out.println("Execute Test");
				irmdb.test();
			}
			else throw new Exception("lookup hello null");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------------------------------------");
		System.exit(0);

	}

}
