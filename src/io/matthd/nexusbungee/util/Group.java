package io.matthd.nexusbungee.util;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import io.matthd.nexusbungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class Group {

	private String name;
	private PermissionSet permissions;
	private String prefix;
	
	public List<Group> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(List<Group> subGroups) {
		this.subGroups = subGroups;
	}

	private List<Group> subGroups; 
	
	public PermissionSet getPermissions() {
		return permissions;
	}

	public void setPermissions(PermissionSet permissions) {
		this.permissions = permissions;
	}

	public Group(String name) {
		this(name, "", new PermissionSet());
	}
	
	public Group(String name, String prefix, PermissionSet set) {
		this(name, prefix, set, new Group[]{});
	}
	
	public Group(String name, String prefix, PermissionSet set, Group[] subGroups) {
		this.setName(name);
		this.permissions = set;
		this.subGroups = Arrays.asList(subGroups);
	}
	
	public PermissionSet getSubPermissions() {
		PermissionSet set = new PermissionSet();
		for (Group child : subGroups) {
			set.combine(child.getPermissions());
		}
		return set;
	}
	
	public void addPermission(String node) {
		if (hasPermission(node))
			return;
		
		this.permissions.add(node);
		push();
		updatePlayers("permissions");
	}
	
	public void removePermission(String node) {
		if (!hasPermission(node))
			return;
		
		this.permissions.remove(node);
		push();
		updatePlayers("permissions");
	}
	
	public abstract void delete();
	
	public boolean hasPermission(String node) {
		return getPermissions().contains(node) || getSubPermissions().contains(node);
	}
	
	public void updatePlayers(String msg) {
		Main.getInstance().getRedis().publish("group", msg);
	}
	
	public abstract void push();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		push();
	}

	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', prefix);
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
		push();
		updatePlayers("prefix");
	}
}
