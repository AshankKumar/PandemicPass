package edu.illinois.cs465.pandemicpass;

import java.io.Serializable;

public class EventSingleGuestInfo {

    public String userId;
    public String memberId;
    public String approvalStatus;

    public EventSingleGuestInfo() {}

    public EventSingleGuestInfo(String userId, String memberId, String approvalStatus) {
        this.userId = userId;
        this.memberId = memberId;
        this.approvalStatus = approvalStatus;
    }
}
