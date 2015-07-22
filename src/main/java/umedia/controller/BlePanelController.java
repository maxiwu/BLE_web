package umedia.controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import umedia.BLE.*;

@Controller
@RequestMapping("/ble")
public class BlePanelController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView addDevice(Model model, HttpSession session) {

		CentralDevice device = new CentralDevice();
		ArrayList<GattProfile> profiles = new ArrayList<GattProfile>();
		device.setProfiles(profiles);

		ArrayList<GattService> services = new ArrayList<GattService>();
		ArrayList<GattCharacteristic> chts = new ArrayList<GattCharacteristic>();

		// initial a value
		GattCharacteristic gchts1 = new GattCharacteristic();
		gchts1.setValue(2.534f);
		gchts1.setValueType("float");
		GattCharacteristic gchts2 = new GattCharacteristic();
		gchts2.setValue(3.14f);
		gchts2.setValueType("float");

		chts.add(gchts1);
		chts.add(gchts2);

		// reverse add items into device
		GattService service1 = new GattService();
		service1.setCharacteristics(chts);
		services.add(service1);

		GattProfile profile1 = new GattProfile();
		profile1.setServices(services);

		profiles.add(profile1);

		// try add clone service 2, clone fail with reference type arraylist
		// try {
		GattProfile profile2 = new GattProfile();
		GattService service2 = new GattService();

		GattCharacteristic gchts3 = new GattCharacteristic(new float[] {
				2022.34f, 39213.11f }, false);

		ArrayList<SimpleEntry<String, Boolean>> states = new ArrayList<SimpleEntry<String, Boolean>>();
		states.add(new SimpleEntry<String, Boolean>("key1", true));
		states.add(new SimpleEntry<String, Boolean>("key2", false));
		states.add(new SimpleEntry<String, Boolean>("key3", false));
		states.add(new SimpleEntry<String, Boolean>("key4", false));
		GattCharacteristic gchtstt = new GattCharacteristic(states, true);

		service2.setCharacteristics(new ArrayList<GattCharacteristic>());
		service2.getCharacteristics().add(gchts3);
		service2.getCharacteristics().add(gchtstt);
		
		profile2.setServices(new ArrayList<GattService>());
		profile2.getServices().add(service2);

		profiles.add(profile2);
		// } catch (CloneNotSupportedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		device.setProfiles(profiles);

		// try a new service 3
		device.setProfiles(profiles);

		ArrayList<GattService> services4 = new ArrayList<GattService>();
		ArrayList<GattCharacteristic> chts4 = new ArrayList<GattCharacteristic>();

		// initial a value
		GattCharacteristic gchts4 = new GattCharacteristic();
		gchts4.setValue(4.434f);
		gchts4.setValueType("float");

		chts4.add(gchts4);
		chts4.add(gchts2);
		// two boolean, 1 on, 1 off
		GattCharacteristic gchtsb1 = new GattCharacteristic(true, true);
		GattCharacteristic gchtsb2 = new GattCharacteristic(false, true);
		chts4.add(gchtsb1);
		chts4.add(gchtsb2);

		// test configuration
		GattCharacteristic gchtst2 = new GattCharacteristic(
				"enter configuration", false);
		chts4.add(gchtst2);

		// reverse add items into device
		GattService service4 = new GattService();
		service4.setCharacteristics(chts4);
		services4.add(service4);

		GattProfile profile4 = new GattProfile();
		profile4.setServices(services4);

		profiles.add(profile4);

		// test text characteristic
		GattCharacteristic gchtst1 = new GattCharacteristic();
		gchtst1.setText("this is a test string characteristic");
		gchtst1.setValueType("text");
		service1.getCharacteristics().add(gchtst1);

		model.addAttribute("device", device);

		return new ModelAndView("blecpanel");
	}
}
