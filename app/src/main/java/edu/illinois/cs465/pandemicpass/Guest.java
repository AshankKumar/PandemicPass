package edu.illinois.cs465.pandemicpass;

public class Guest {

    public String account_id;
    public String member_id;
    public String approval_status;

    public Guest() {}

    public Guest(String account_id, String member_id, String approval_status) {
        this.account_id = account_id;
        this.member_id = member_id;
        this.approval_status = approval_status;
    }
}
