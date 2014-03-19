package impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import bean.User;
import interfaces.IUser;

public class UserImpl extends UnicastRemoteObject implements IUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConnBDDImpl connBDD;
	private Connection conn;
	
	public UserImpl() throws RemoteException {
		super();
	}
	
	@Override
	public boolean addUser(User u) throws RemoteException {
		if(connBDD.connection()){
			conn = connBDD.getConnection();
			try {
				if(!(userExist(u.getLogin()))){
					String insertUser = "INSERT INTO projetboat.utilisateur(login,password) VALUES (?, ?)";
					PreparedStatement pst = (PreparedStatement) conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
					pst.setString(1, u.getLogin());
					pst.setString(2, u.getPassword());
					pst.executeUpdate();
					connBDD.closeConnection();
					return true;
				}
				else{
					connBDD.closeConnection();
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				connBDD.closeConnection();
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean removeUser(String login) throws RemoteException {
		if(connBDD.connection()){
			conn = connBDD.getConnection();
			try {
				String deleteUser = "DELETE FROM projetboat.utilisateur WHERE utilisateu.login =?";
				PreparedStatement pst = (PreparedStatement) conn.prepareStatement(deleteUser, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, login);
				pst.executeUpdate();
				connBDD.closeConnection();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				connBDD.closeConnection();
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean userExist(String login) throws RemoteException {
		if(connBDD.connection()){
			conn = connBDD.getConnection();
			try {
				String select = "SELECT COUNT(*) AS COUNT FROM utilisateur WHERE login=?";
				PreparedStatement pst = (PreparedStatement) conn.prepareStatement(select);
				pst.setString(1, login);
				ResultSet rs = pst.executeQuery();
				int nb = 0;
				if(rs.next()){
					nb = rs.getInt("COUNT");
				}
				if(nb != 0){
					connBDD.closeConnection();
					return true;
				}
				else{
					connBDD.closeConnection();
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				connBDD.closeConnection();
				return false;
			}
		}
		return false;
	}

}
