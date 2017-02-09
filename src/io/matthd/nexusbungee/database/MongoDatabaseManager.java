package io.matthd.nexusbungee.database;

import java.util.Collections;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import io.matthd.nexusbungee.Main;

public class MongoDatabaseManager implements DatabaseManager {

	private Main plugin;
	private MongoClient mongoClient;
	
	private DB database;
	
	private String host;
	private int port;
	
	public MongoDatabaseManager(Main plugin, String host, int port) {
		this.plugin = plugin;
		this.host = host;
		this.port = port;
	}
	
	@Override
	public void connect() {
		MongoCredential credentials = MongoCredential.createCredential(plugin.getConfig().getString("user"), plugin.getConfig().getString("db"), plugin.getConfig().getString("pass").toCharArray());
		this.mongoClient = new MongoClient(new ServerAddress(host, port), Collections.singletonList(credentials));
		this.database = mongoClient.getDB(plugin.getConfig().getString("db"));
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
	}
	
	public DBCollection getCollection(String col) {
		return database.getCollection(col);
	}
}
