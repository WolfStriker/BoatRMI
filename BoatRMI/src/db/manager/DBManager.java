package db.manager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import db.Boat;
import db.Groupe;
import db.User;

public class DBManager {

	private static Connection conn = null;
	private static Statement st = null;
	private static String url;
	private static String user;
	private static String pwd;
	private static String db;
	
	public DBManager(String url, String user, String pwd, String db){
		DBManager.url = url;
		DBManager.user = user;
		DBManager.pwd = pwd;
		DBManager.db = db;
	}
	
	public boolean connection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			DBManager.conn = DriverManager.getConnection(url+db, user, pwd);
			st = (Statement) DBManager.conn.createStatement();
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean closeConnection(){
		try {
			DBManager.conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<String> afficheTables(){
		DatabaseMetaData md;
		try {
			md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			ArrayList<String> listTables = new ArrayList<String>(0);
			while (rs.next()) {
				listTables.add(rs.getString(3));
			}
			return listTables;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<String> afficheElementTable(String table){
		ArrayList<String> list = new ArrayList<String>(0);
		try {
			ResultSet rs = (ResultSet) st.executeQuery("SELECT * from "+table);
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
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean addBoat(Boat b){
		try {
			String insertBoat = "INSERT INTO projetboat.bateau(notice,photo,groupe) VALUES ('"+b.getNotice()+"', '"+b.getPhoto()+"', '"+b.getGroupe()+"')";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(insertBoat, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addGroupe(Groupe g){
		try {
			String insertGroup = "INSERT INTO projetboat.groupe(nom) VALUES ('"+g.getNom()+"')";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(insertGroup, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addUser(User u){
		try {
			String insertUser = "INSERT INTO projetboat.utilisateur(login,password) VALUES ('"+u.getLogin()+"','"+u.getPassword()+"')";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeBoat(String notice){
		try {
			String deleteBoat = "DELETE FROM projetboat.bateau WHERE bateau.notice ='"+notice+"'";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(deleteBoat, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeGroupe(String nom){
		try {
			String deleteGroup = "DELETE FROM projetboat.groupe WHERE groupe.nom ='"+nom+"'";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(deleteGroup, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeUser(String login){
		try {
			String deleteUser = "DELETE FROM projetboat.utilisateur WHERE utilisateu.login ='"+login+"'";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(deleteUser, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean userExist(String login, String password){
		try {
			String select = "SELECT COUNT(*) AS COUNT FROM utilisateur WHERE (login=? AND password=?)";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(select);
			pst.setString(1, login);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			int nb = 0;
			if(rs.next()){
				nb = rs.getInt("COUNT");
			}
			if(nb != 0){
				return true;
			}
			else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Connection getConnection(){
		return DBManager.conn;
	}
}