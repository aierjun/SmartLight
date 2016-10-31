package com.gizwits.framework.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xtremeprog.xpgconnect.XPGWifiSubDevice;

public class GroupDevice implements Serializable {
	public GroupDevice(){
		
	}
	public GroupDevice(XPGWifiSubDevice subDevice, boolean onOff, int lightness){
		this.subDevice = subDevice;
		this.onOff = onOff;
		this.lightness = lightness;
	}
	
	private XPGWifiSubDevice subDevice;
	private boolean onOff = false;
	private int lightness = 0;
	private int sdid;
	public XPGWifiSubDevice getSubDevice() {
		return subDevice;
	}
	public void setSubDevice(XPGWifiSubDevice subDevice) {
		this.subDevice = subDevice;
	}
	public boolean isOnOff() {
		return onOff;
	}
	public void setOnOff(boolean onOff) {
		this.onOff = onOff;
	}
	public int getLightness() {
		return lightness;
	}
	public void setLightness(int lightness) {
		this.lightness = lightness;
	}
	public int getSdid() {
		return sdid;
	}
	public void setSdid(int sdid) {
		this.sdid = sdid;
	}
	
	
	public static List<GroupDevice> getGroupDeviceByList(List<XPGWifiSubDevice> subDevices){
		List<GroupDevice> gDevices = new ArrayList<GroupDevice>();
		for (int i = 0; i < subDevices.size(); i++) {
			GroupDevice gDevice = new GroupDevice();
			gDevice.setSubDevice(subDevices.get(i));
			gDevices.add(gDevice);
		}
		return gDevices;
	}
	
	public static ArrayList<String> getAllName(List<GroupDevice> groupDevices){
		ArrayList<String> sdids = new ArrayList<String>();
		for (int i = 0; i < groupDevices.size(); i++) {
			sdids.add(groupDevices.get(i).getSubDevice().getSubDid());
		}
		return sdids;
	}
	
}
