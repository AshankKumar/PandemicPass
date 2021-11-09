package edu.illinois.cs465.pandemicpass;

public class Guest {

    public String accountId;
    public String memberId;
    public String approvalStatus;

    public Guest() {}

    public Guest(String accountId, String memberId, String approvalStatus) {
        this.accountId = accountId;
        this.memberId = memberId;
        this.approvalStatus = approvalStatus;
    }
}
