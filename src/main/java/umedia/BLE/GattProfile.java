package umedia.BLE;

import java.util.ArrayList;
import java.util.List;

public class GattProfile implements Cloneable {

	private ArrayList<GattService> services;

	// private String test;

	public ArrayList<GattService> getServices() {
		return services;
	}

	public void setServices(ArrayList<GattService> services) {
		this.services = services;
	}

	/*
	 * public String getTest() { return "test ok"; }
	 */

	public Object clone() throws CloneNotSupportedException {
		// 直接使用父類別的clone()方法,傳回複製副本
		GattProfile gp = (GattProfile) super.clone();
		
		List<GattService> clonelist = new ArrayList<GattService>(this.services.size());
	    for(GattService item: this.services) clonelist.add((GattService)item.clone());
	    
	    return gp;
	}
}
