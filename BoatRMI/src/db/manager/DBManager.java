package db.manager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Boat;
import bean.Groupe;
import bean.User;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

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
	
	/**
	 * Create new connection to BDD
	 * @return
	 */
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
	
	/**
	 * Close connection to BDD
	 * @return
	 */
	public boolean closeConnection(){
		try {
			DBManager.conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * List all tables in BDD
	 * @return
	 */
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
	
	/**
	 * List all element of table in BDD
	 * @param table
	 * @return
	 */
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
	
	/**
	 * Search 'note' on Table bateau
	 * @param note
	 * @return
	 */
	public ArrayList<String> search(String note){
		ArrayList<String> list = new ArrayList<String>(0);
		try{
			String search = "SELECT * FROM bateau WHERE notice LIKE '%?%' OR nom LIKE '%?%'";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(search, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, note);
			pst.setString(2, note);
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
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Add boat on BDD
	 * @param b
	 * @return
	 */
	public boolean addBoat(Boat b){
		try {
			String insertBoat = "INSERT INTO projetboat.bateau(nom,notice,photo,groupe) VALUES (?, ?, ?, ?)";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(insertBoat, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, b.getNom());
			pst.setString(2, b.getNotice());
			pst.setString(3, b.getPhoto());
			pst.setInt(4, b.getGroupe());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Add group on BDD
	 * @param g
	 * @return
	 */
	public boolean addGroupe(Groupe g){
		try {
			String insertGroup = "INSERT INTO projetboat.groupe(nom) VALUES (?)";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(insertGroup, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, g.getNom());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Add user on BDD
	 * @param u
	 * @return
	 */
	public boolean addUser(User u){
		try {
			String insertUser = "INSERT INTO projetboat.utilisateur(login,password) VALUES (?, ?)";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, u.getLogin());
			pst.setString(2, u.getPassword());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * Remove Boat on BDD
	 * @param notice
	 * @return
	 */
	public boolean removeBoat(String nom){
		try {
			String deleteBoat = "DELETE FROM projetboat.bateau WHERE bateau.nom =?";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(deleteBoat, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, nom);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Remove Group on BDD
	 * @param nom
	 * @return
	 */
	public boolean removeGroupe(String nom){
		try {
			String deleteGroup = "DELETE FROM projetboat.groupe WHERE groupe.nom =?";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(deleteGroup, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, nom);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Remove User on BDD
	 * @param login
	 * @return
	 */
	public boolean removeUser(String login){
		try {
			String deleteUser = "DELETE FROM projetboat.utilisateur WHERE utilisateu.login =?";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(deleteUser, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, login);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Check if user exist
	 * @param login
	 * @param password
	 * @return
	 */
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
	
	/**
	 * Get connection Object
	 * @return
	 */
	public Connection getConnection(){
		return DBManager.conn;
	}
}
