package edu.illinois.cs465.pandemicpass;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Event {

    public String hostId;
    public String hostName;
    public String eventCode;
    public String eventName;
    public String date;
    public String time;
    public String location;
    public String description;
    public HashMap<String, Guest> guestList;
    public boolean acceptVaccinationRecord;
    public boolean acceptTestResult;

    public Event() {}

    public Event(String hostId, String hostName, String eventCode, String eventName,
                 String date, String time, String location, String description, HashMap<String, Guest> guestList,
                 boolean acceptVaccinationRecord, boolean acceptTestResult) {
        this.hostId = hostId;
        this.hostName = hostName;
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.guestList = guestList;
        this.acceptVaccinationRecord = acceptVaccinationRecord;
        this.acceptTestResult = acceptTestResult;
    }

}
