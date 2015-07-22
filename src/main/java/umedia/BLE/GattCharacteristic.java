package umedia.BLE;

import java.util.ArrayList;
import java.util.UUID;
import java.util.AbstractMap.SimpleEntry;

public class GattCharacteristic implements Cloneable {

	private UUID specification;
	private byte handle;
	
	//should use some kind of Polymorphism
	private String name;
	private String valueType;
	private float value;
	private float[] csv;
	private String text;
	private ArrayList<SimpleEntry<String, Boolean>> state;
	//private byte flag;
	//private StateMachine state;   key : bool, single state
	private boolean status;
	
	private boolean readonly = true;
	
	public GattCharacteristic()
	{
		this.setValueType("default");
	}
	
	public GattCharacteristic(boolean initial, boolean isReadonly)
	{
		this.setValueType("boolean");
		this.setReadonly(isReadonly);
		this.setStatus(initial);
	}
	
	public GattCharacteristic(String initial, boolean isReadonly)
	{
		this.setValueType("text");
		this.setReadonly(isReadonly);
		this.setText(initial);
	}
	
	public GattCharacteristic(float[] initial, boolean isReadonly)
	{
		this.setValueType("csv");
		this.setReadonly(isReadonly);
		this.setCsv(initial);
	}
	public GattCharacteristic(ArrayList<SimpleEntry<String,Boolean>> initial, boolean isReadonly)
	{
		this.setValueType("state");
		this.setReadonly(isReadonly);
		this.setState(initial);
	}
	
	public UUID getSpecification() {
		return specification;
	}
	public void setSpecification(UUID specification) {
		this.specification = specification;
	}
	public byte getHandle() {
		return handle;
	}
	public void setHandle(byte handle) {
		this.handle = handle;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public float[] getCsv() {
		return csv;
	}
	public void setCsv(float[] csv) {
		this.csv = csv;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Object clone() throws CloneNotSupportedException {
		// 直接使用父類別的clone()方法,傳回複製副本
		return super.clone();
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public ArrayList<SimpleEntry<String, Boolean>> getState() {
		return state;
	}

	public void setState(ArrayList<SimpleEntry<String, Boolean>> state) {
		this.state = state;
	}

}
