package it.univaq.agilehub.model;

public class TimeSlot {

    private int id;

    private String time_slot;

    public TimeSlot() {
    }

    public TimeSlot(int id, String time_slot) {
        this.id = id;
        this.time_slot = time_slot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    @Override
    public String toString() {
        return time_slot ;
    }
}
