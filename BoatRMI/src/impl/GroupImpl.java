package impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import bean.Groupe;
import interfaces.IGroup;

public class GroupImpl extends UnicastRemoteObject implements IGroup{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConnBDDImpl connBDD;
	private Connection conn;

	public GroupImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public int getIdGroup(String group) throws RemoteException {
		if(connBDD.connection()){
			conn = connBDD.getConnection();
			try{
				String search = "SELECT nom FROM groupe WHERE id LIKE '%?%'";
				PreparedStatement pst = (PreparedStatement) conn.prepareStatement(search, Statement.RETURN_GENERATED_KEYS); 
				pst.setMaxRows(1); 
				ResultSet rs = pst.executeQuery();
				connBDD.closeConnection();
				return rs.getInt(0);
			}catch (SQLException e) {
				e.printStackTrace();
				connBDD.closeConnection();
				return -1;
			}
		}
		else{
			return -1;
		}
	}

	@Override
	public boolean addGroupe(Groupe g) throws RemoteException {
		if(connBDD.connection()){
			conn = connBDD.getConnection();
			try {
				String insertGroup = "INSERT INTO projetboat.groupe(nom) VALUES (?)";
				PreparedStatement pst = (PreparedStatement) conn.prepareStatement(insertGroup, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, g.getNom());
				pst.executeUpdate();
				connBDD.closeConnection();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				connBDD.closeConnection();
				return false;
			}
		}
		else{
			return false;
		}
	}

	@Override
	public boolean removeGroupe(String nom) throws RemoteException {
		if(connBDD.connection()){
			conn = connBDD.getConnection();
			try {
				String deleteGroup = "DELETE FROM projetboat.groupe WHERE groupe.nom =?";
				PreparedStatement pst = (PreparedStatement) conn.prepareStatement(deleteGroup, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, nom);
				pst.executeUpdate();
				connBDD.closeConnection();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				connBDD.closeConnection();
				return false;
			}
		}
		else{
			return false;
		}
	}

}
