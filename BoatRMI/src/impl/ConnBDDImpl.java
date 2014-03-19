package impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import bean.Boat;

import com.mysql.jdbc.Statement;

import interfaces.IBoat;
import interfaces.IConnBDD;
import interfaces.IPDF;
import interfaces.IUser;

public class ConnBDDImpl implements IConnBDD{

	private String url;
	private String user;
	private String pwd;
	private String db;
	private Statement st;
	private static Connection conn = null;

	@Override
	public void test() throws RemoteException {
		if(getProperties()){
			ConnBDDImpl dbConn = new ConnBDDImpl();
			if(dbConn.connection()){
				IBoat b = new BoatImpl();
				ArrayList<Boat> listBoat = b.afficheAllBoat();
				System.out.println("Bateau :");
				if(listBoat != null){
					for(int i = 0; i<listBoat.size(); i++){
						System.out.println("\tnom : '"+listBoat.get(i).getNom()+"' - notice : '"+listBoat.get(i).getNotice()+"'");
					}
				}
			
				if(b.addBoat(new Boat("32 Riviera", "Super bateau", "", 2))){
					System.out.println("Ajout Bateau OK!");
				}
				/*
				if(dbM.addGroupe(new Groupe("test"))){
					System.out.println("Ajout Groupe OK!");
				}
				
				if(dbM.addUser(new User("toto", "test"))){
					System.out.println("Ajout User OK!");
				}
				*/
				if(b.removeBoat("32 Riviera")){
					System.out.println("Suppression OK!");
				}
				
				IUser u = new UserImpl();
				if(u.userExist("antoine")){
					System.out.println("User exist !");
				}
				else{
					System.out.println("User Not Exist!");
				}
				
				IPDF p = new PDFImpl();
				p.generatePDF(listBoat);
			}
			else{
				System.out.println("Connection failed");
			}
		}
	}

	@Override
	public boolean getProperties() throws RemoteException {
		Properties p = null;
		try {
			p = new Properties();
			p.load(ConnBDDImpl.class.getResourceAsStream("/DB.properties"));
		} catch (FileNotFoundException e) {
		    System.out.println("500 - INTERNAL SERVER ERROR (CAN'T FOUND .PROPERTIES)\n" + e.getMessage());
		    return false;
		} catch (IOException e) {
		    System.out.println("500 - INTERNAL SERVER ERROR (CAN'T LOAD .PROPERTIES)\n" + e.getMessage());
		    return false;
		}
		  
		if(p != null){
			url = p.getProperty("url", "jdbc:mysql://localhost:3306/");
			user = p.getProperty("user", "root");
			pwd = p.getProperty("pwd", "");
			db = p.getProperty("db", "livre");
		}
		return true;
	}

	@Override
	public boolean connection() throws RemoteException {
		try {
			if(getProperties()){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				this.conn = DriverManager.getConnection(url+db, user, pwd);
				st = (Statement) this.conn.createStatement();
				System.out.println("Connection DB Successfull\nDataBase : '"+url+db+"'");
				return true;
			}
			else{
				return false;
			}
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

	@Override
	public boolean closeConnection() throws RemoteException {
		try {
			this.conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Connection getConnection() throws RemoteException {
		return this.conn;
	}

}
