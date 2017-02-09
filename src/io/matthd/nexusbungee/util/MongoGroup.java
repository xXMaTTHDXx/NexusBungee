package io.matthd.nexusbungee.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoGroup extends Group {
		
	private DBObject groupObject;
	
	public MongoGroup(String name, String prefix, PermissionSet set, Group[] subGroups) {
		super(name, prefix, set, subGroups);
		this.groupObject = new BasicDBObject("_id", name)
				.append("permissions", set.toArray().toString())
				.append("subGroups", subGroups.toString());
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void push() {
		// TODO Auto-generated method stub
		
	}
}
