package edu.illinois.cs465.pandemicpass;
import java.util.Date;
import java.util.List;

public class Event {

    public String host_id;
    public String host_name;
    public String event_code;
    public String event_name;
    public String date;
    public String location;
    public String description;
    public List<Guest> guest_list;

    public Event() {}

    public Event(String host_id, String host_name, String event_code, String event_name,
                 String date, String location, String description,
                 List<Guest> guest_list) {
        this.host_id = host_id;
        this.host_name = host_name;
        this.event_code = event_code;
        this.event_name = event_name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.guest_list = guest_list;
    }

}
