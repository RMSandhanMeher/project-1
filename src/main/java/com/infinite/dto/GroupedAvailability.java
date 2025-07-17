package com.infinite.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import com.infinite.model.DoctorAvailability;

public class GroupedAvailability implements Serializable {
    private Date date;
    private List<DoctorAvailability> slots;

    public GroupedAvailability(Date date, List<DoctorAvailability> slots) {
        this.date = date;
        this.slots = slots;
    }

    public Date getDate() {
        return date;
    }

    public List<DoctorAvailability> getSlots() {
        return slots;
    }
}
