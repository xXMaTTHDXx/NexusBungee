package io.matthd.nexusbungee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.io.ByteStreams;

import io.matthd.nexusbungee.database.MongoDatabaseManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import redis.clients.jedis.Jedis;

public class Main extends Plugin {

	private Configuration config;
	private File configFile;

	private Jedis jedis;
	
	private static Main instance;

	private MongoDatabaseManager manager;

	@Override
	public void onEnable() {
		instance = this;
		init();
	}

	public void onDisable() {

	}
	
	public static Main getInstance() {
		return instance;
	}

	public void init() {
		createFile();

		jedis = new Jedis("localhost");
		jedis.auth("Nexus");
		
		this.manager = new MongoDatabaseManager(this, "localhost", 27017);
	}
	
	public Jedis getRedis() {
		return jedis;
	}

	public MongoDatabaseManager getManager() {
		return manager;
	}

	public void createFile() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		this.configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				try (InputStream is = getResourceAsStream("config.yml");
						OutputStream os = new FileOutputStream(configFile)) {
					ByteStreams.copy(is, os);
				}
			} catch (IOException e) {
				throw new RuntimeException("Unable to create configuration file", e);
			}
		}
		try {
			this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public Configuration getConfig() {
		return config;
	}

	public void saveConfig() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
