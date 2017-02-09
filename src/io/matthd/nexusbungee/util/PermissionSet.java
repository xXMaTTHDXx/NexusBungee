package io.matthd.nexusbungee.util;

import java.util.ArrayList;

public class PermissionSet extends ArrayList<String> {

	
	public void combine(PermissionSet set) {
		for (String node : set) {
			if (this.contains(node))
				continue;
			this.add(node);
		}
	}
}
