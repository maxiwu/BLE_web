package umedia.BLE;

import java.util.ArrayList;
import java.util.List;

public class CentralDevice {

	private List<GattProfile> profiles;
//	private GattProfile[] aprofile = new GattProfile[1];

	public List<GattProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<GattProfile> profiles) {
		this.profiles = profiles;		
	}

/*	public GattProfile[] getAprofile() {
		return aprofile;
	}

	public void setAprofile(GattProfile[] aprofile) {
		this.aprofile = aprofile;
	}*/
}
