package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import bean.Boat;

public interface IPDF extends Remote{

	public void generatePDF(ArrayList<Boat> boats) throws RemoteException;
}
