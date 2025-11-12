package com.aurigo.masterworks.testframework.webUI.constants.enums;

import java.util.ArrayList;

public enum WorkFlowStatus {

    Submitted("Submitted"),
    Approved("Approved"),
    Drafted("Drafted"),
    Committed("Committed"),
    Received("Received"),
    Completed("Completed"),
    Published("Published"),
    UnPublished("Un-Published"),
    Closed("Closed"),
    Issued("Issued"),
    New("New"),
    Draft("Draft"),
    Awarded("Awarded"),
    PendingApproval("Pending Approval"),
    Complete("Complete"),
    ReviewResponse("ReviewResponse"),
    AwaitingRFIResponse("Awaiting RFI Response"),
    ReOpened("Re-Opened"),
    Released("Released"),
    Resolved("Resolved"),
    Rejected("Rejected"),
    ResponseReceived("Response Received"),
    PreAward("Pre-Award"),
    Created("Created"),
    PartiallyRecovered("Partially Recovered"),
    FullyRecovered("Fully Recovered"),
    Returned("Returned"),
    AwaitingResponse("Awaiting Response"),
    InProgress("In Progress");

    private String value;

    WorkFlowStatus(String value) {
        this.value = value;
    }

    public ArrayList<Object> getList(){
        var listToReturn = new ArrayList<>();
        for (var item : listToReturn){
            listToReturn.add(item);
        }
        return listToReturn;
    }

    public String getValue() {
        return this.value;
    }
}
