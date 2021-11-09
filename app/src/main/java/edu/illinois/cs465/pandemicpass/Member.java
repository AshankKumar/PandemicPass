package edu.illinois.cs465.pandemicpass;

import com.google.firebase.database.Exclude;

public class Member {
    @Exclude
    public String id;

    public String name;
    public String vaccinationRecordFileName;
    public String testResultFileName;

    public Member() {}

    public Member(String name, String vaccinationRecordFileName, String testResultFileName) {
        this.name = name;
        this.vaccinationRecordFileName = vaccinationRecordFileName;
        this.testResultFileName = testResultFileName;
    }
}
