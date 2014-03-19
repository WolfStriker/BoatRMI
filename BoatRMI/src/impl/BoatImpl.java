package impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import bean.Boat;
import interfaces.IBoat;
import interfaces.IGroup;

public class BoatImpl extends UnicastRemoteObject implements IBoat{

	public BoatImpl() throws RemoteException {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConnBDDImpl connBDD;
	private Connection conn;
	private Statement st;

	@Override
	public ArrayList<Boat> afficheAllBoat() throws RemoteException {
		if(connBDD.connection()){
			conn = connBDD.getConnection();
			ArrayList<Boat> list = new ArrayList<Boat>(0);
			try {
				ResultSet rs = (ResultSet) st.executeQuery("SELECT * FROM bateau");
				Boat b = null;
				while(rs.next()){
					b = new Boat(rs.getInt("id"), rs.getString("nom"), rs.getString("notice"), rs.getString("photo"), rs.getInt("groupe"));
					list.add(b);
					b= null;
				}
				connBDD.closeConnection();
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				connBDD.closeConnection();
				return null;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Boat> searchBoat(String note) throws RemoteException {
		ArrayList<Boat> list = new ArrayList<Boat>(0);
		if(connBDD.connection()){
			conn = connBDD.getConnection();
			try{
				String search = "SELECT * FROM bateau WHERE notice LIKE '%?%' OR nom LIKE '%?%'";
				PreparedStatement pst = (PreparedStatement) conn.prepareStatement(search, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, note);
				pst.setString(2, note);
				ResultSet rs = pst.executeQuery(search);
				Boat b = null;
				while(rs.next()){
					b = new Boat(rs.getInt("id"), rs.getString("nom"), rs.getString("notice"), rs.getString("photo"), rs.getInt("groupe"));
					list.add(b);
					b= null;
				}
				connBDD.closeConnection();
				return list;
			}catch (SQLException e) {
				e.printStackTrace();
				connBDD.closeConnection();
				return null;
			}
		}
		return null;
	}

	@Override
	public ArrayList<String> searchBoatByGroup(String group)
			throws RemoteException {
		ArrayList<String> list = new ArrayList<String>(0);
		IGroup g = new GroupImpl();
		if(connBDD.connection()){
			conn = connBDD.getConnection();
			try{
				String search = "SELECT * FROM bateau WHERE groupe LIKE '%?%'";
				PreparedStatement pst = (PreparedStatement) conn.prepareStatement(search, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1, g.getIdGroup(group));
				ResultSet rs = pst.executeQuery(search);
				ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
				int nbCol = rsmd.getColumnCount();
				String colName = "";
				for(int i=1 ; i<=nbCol ; i++){
					colName += "- "+rsmd.getColumnName(i);
				}
				list.add(colName);
				String l = "";
				while(rs.next()){
					for(int i = 1 ; i<=nbCol ; i++){
						l += "- "+rs.getString(i);
					}
					list.add(l);
					l = "";
				}
				connBDD.closeConnection();
				return list;
			}catch (SQLException e) {
				e.printStackTrace();
				connBDD.closeConnection();
				return null;
			}
		}
		return null;
	}

	@Override
	public boolean addBoat(Boat b) throws RemoteException {
		try {
			String insertBoat = "INSERT INTO projetboat.bateau(nom,notice,photo,groupe) VALUES (?, ?, ?, ?)";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(insertBoat, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, b.getNom());
			pst.setString(2, b.getNotice());
			pst.setString(3, b.getPhoto());
			pst.setInt(4, b.getGroupe());
			pst.executeUpdate();
			connBDD.closeConnection();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeBoat(String nom) throws RemoteException {
		try {
			String deleteBoat = "DELETE FROM projetboat.bateau WHERE bateau.nom =?";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(deleteBoat, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, nom);
			pst.executeUpdate();
			connBDD.closeConnection();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateBoat(int id, String notice, String nom, String photo)
			throws RemoteException {
		try {
			String updateBoat = "UPDATE bateau SET notice=?,nom=?,photo=?,groupe=? WHERE id=?";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(updateBoat);
		    ps.setString(1, notice);
		    ps.setString(2, nom);
		    ps.setString(3, photo);
		    ps.setInt(4, id);
		    ps.executeUpdate();
		    connBDD.closeConnection();
		    return true;
		} catch (Exception e) {
		    e.printStackTrace();
		    return false;
		}
	}

}
