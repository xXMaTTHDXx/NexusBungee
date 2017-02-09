package io.matthd.nexusbungee.util;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupManager {
	
	private List<Group> allGroups = new ArrayList<>();
	
	public void createGroup(Group group) {
		if (groupExists(group.getName()))
			return;
		
		push();
	}
	
	public void deleteGroup(Group group) {
		if (!groupExists(group.getName()))
			return;
		group.delete();
		
		this.allGroups.remove(group);
		push();
	}
	
	public boolean groupExists(String name) {
		for (Group group : allGroups) {
			if (group.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public abstract void push();
}
