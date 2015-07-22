package umedia.BLE;

import java.util.ArrayList;
import java.util.List;

public class GattService implements Cloneable {

	private ArrayList<GattCharacteristic> characteristics;

	private ArrayList<GattCharacteristic> floatCharacteristic;
	private ArrayList<GattCharacteristic> csvCharacteristic;
	private ArrayList<GattCharacteristic> textCharacteristic;
	private ArrayList<GattCharacteristic> booleanCharacteristic;
	private ArrayList<GattCharacteristic> stateCharacteristic;

	// help jsp & jstl..., not neccessary
	public ArrayList<GattCharacteristic> getFloatCharacteristic() {
		return this.GetCharacteristicByType("float");
	}

	public ArrayList<GattCharacteristic> getCsvCharacteristic() {
		return this.GetCharacteristicByType("csv");
	}

	public ArrayList<GattCharacteristic> getTextCharacteristic() {
		return this.GetCharacteristicByType("text");
	}

	public ArrayList<GattCharacteristic> getBooleanCharacteristic() {
		return this.GetCharacteristicByType("boolean");
	}

	public ArrayList<GattCharacteristic> getStateCharacteristic() {
		return this.GetCharacteristicByType("state");
	}

	public ArrayList<GattCharacteristic> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(ArrayList<GattCharacteristic> characteristics) {
		// sort by type before returning!
		this.characteristics = characteristics;
	}

	public Object clone() throws CloneNotSupportedException {
		// 直接使用父類別的clone()方法,傳回複製副本
		return super.clone();
	}

	public ArrayList<GattCharacteristic> GetCharacteristicByType(String type) {
		// support 4/6 types, float, float[], boolean, text
		ArrayList<GattCharacteristic> filteredList = new ArrayList<GattCharacteristic>();
		for (GattCharacteristic item : this.characteristics) {
			if (item.getValueType().equals(type)) {
				filteredList.add(item);
			}
		}

		return filteredList;
	}

}
