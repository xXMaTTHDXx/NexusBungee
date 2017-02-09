package io.matthd.nexusbungee.listener;

import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import io.matthd.nexusbungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyJoin implements Listener {
	
	private Main plugin;
	
	public ProxyJoin(Main plugin) {
		this.plugin = plugin;
	}
	
	// UUID, Rank, Coins, Statistics, Achievements, LastName, LastJoin
	
	@EventHandler
	public void onProxyJoin(PostLoginEvent event) {
		ProxiedPlayer player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		final String name = player.getName();
		join(uuid, name);
	}
	
	public void join(UUID uuid, String name) {
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
