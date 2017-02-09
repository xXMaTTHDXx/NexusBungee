package io.matthd.nexusbungee.listener;

import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import io.matthd.nexusbungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.event.EventHandler;

public class ProxyQuit {
private Main plugin;
	
	public ProxyQuit(Main plugin) {
		this.plugin = plugin;
	}
	
	// UUID, Rank, Coins, Statistics, Achievements, LastName, LastJoin
	
	@EventHandler
	public void onProxyJoin(PlayerDisconnectEvent event) {
		ProxiedPlayer player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		final String name = player.getName();
		quit(uuid, name);
	}
	
	public void quit(UUID uuid, String name) {
		plugin.getProxy().getScheduler().runAsync(plugin, () -> {
			BasicDBObject obj = new BasicDBObject("_id", uuid.toString());
			
			DBCursor cursor = plugin.getManager().getCollection("players").find(obj);
			DBObject found = cursor.one();
			
			if (found != null) {
				found.put("LastName", name);
				found.put("LastJoin", System.currentTimeMillis());
				plugin.getManager().getCollection("players").update(obj, found);
			}
		});
	}
}
