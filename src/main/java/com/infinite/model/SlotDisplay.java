package com.infinite.model;

public class SlotDisplay {
	private int slotNumber;
	private String startTime;
	private String endTime;

	public SlotDisplay(int slotNumber, String startTime, String endTime) {
		this.slotNumber = slotNumber;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getDisplayLabel() {
		return "Slot " + slotNumber + " (" + startTime + " - " + endTime + ")";
	}
}
