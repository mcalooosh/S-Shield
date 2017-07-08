package com.hackathon.s_shield.Admin.Adapter;

/**
 * Created by Aloush on 4/29/2017.
 */
public class Report {

    String caseId,byWho;

    public Report(String caseId, String byWho) {
        this.caseId = caseId;
        this.byWho = byWho;

    }

    public String getcaseId() {
        return caseId;
    }

    public void setcaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getbyWho() {
        return byWho;
    }

    public void setbyWho(String byWho) {
        this.byWho = byWho;
    }


}
