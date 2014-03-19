package rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;

import bean.Boat;

public class RMIObject implements IMethod{

	@Override
	public boolean addBoat() throws RemoteException {
		return false;
	}

	@Override
	public ArrayList<Boat> listAllBoat() throws RemoteException {
		ArrayList<Boat> list = new ArrayList<Boat>(0);
		return list;
	}

	@Override
	public ArrayList<Boat> listBoat(String groupe) throws RemoteException {
		ArrayList<Boat> list = new ArrayList<Boat>(0);
		return list;
	}

	@Override
	public String hello() throws RemoteException {
		return "hello world !";
	}

}
