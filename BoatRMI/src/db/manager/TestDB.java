package db.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import bean.Boat;
import bean.Groupe;
import bean.User;

public class TestDB {

	private static String url;
	private static String user;
	private static String pwd;
	private static String db;
	private static ArrayList<String> listTableBDD = null;
	private static ArrayList<String> listBoat = null;
	
	public static void main(String[] args) {
		Properties p = null;
		try {
			p = new Properties();
			p.load(DBManager.class.getResourceAsStream("/DB.properties"));
		} catch (FileNotFoundException e) {
		    System.out.println("500 - INTERNAL SERVER ERROR (CAN'T FOUND .PROPERTIES)\n" + e.getMessage());
		} catch (IOException e) {
		    System.out.println("500 - INTERNAL SERVER ERROR (CAN'T LOAD .PROPERTIES)\n" + e.getMessage());
		}
		  
		if(p != null){
			url = p.getProperty("url", "jdbc:mysql://localhost:3306/");
			user = p.getProperty("user", "root");
			pwd = p.getProperty("pwd", "");
			db = p.getProperty("db", "livre");
		}
		DBManager dbM = new DBManager(url, user, pwd, db);
		if(dbM.connection()){
			System.out.println("Connection Successfull\nDataBase : '"+url+db+"'\nTables :");
			listTableBDD = dbM.afficheTables();
			if(listTableBDD != null){
				for(int i=0; i<listTableBDD.size(); i++){
					System.out.println("\t'"+listTableBDD.get(i)+"'");
				}
			}
			listBoat = dbM.afficheElementTable("bateau");
			System.out.println("Bateau :");
			if(listBoat != null){
				for(int i = 0; i<listBoat.size(); i++){
					System.out.println("\t'"+listBoat.get(i)+"'");
				}
			}
			if(dbM.addBoat(new Boat("32 Riviera", "Super bateau", "", 2))){
				System.out.println("Ajout Bateau OK!");
			}
			
			if(dbM.addGroupe(new Groupe("test"))){
				System.out.println("Ajout Groupe OK!");
			}
			
			if(dbM.addUser(new User("toto", "test"))){
				System.out.println("Ajout User OK!");
			}
			
			if(dbM.removeBoat("32 Riviera")){
				System.out.println("Suppression OK!");
			}
			
			if(dbM.userExist("antoine", "fouque")){
				System.out.println("User exist !");
			}
			else{
				System.out.println("User Not Exist!");
			}
			
			if(dbM.closeConnection()){
				System.out.println("---------------------------\nConnection closed");
			}
			
		}
		else{
			System.out.println("Connection failed");
		}
	}

}
