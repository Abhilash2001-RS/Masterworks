package com.aurigo.masterworks.testframework.webUI.constants.enums;

public enum WorkFlowActions {

    Submit("Submit"),
    Approve("Approve"),
    Draft("Draft"),
    Commit("Commit"),
    Receive("Receive"),
    Resubmit("Resubmit"),
    Complete("Complete"),
    Issued("Issued"),
    Award("Award"),
    Issue("Issue"),
    Close("Close"),
    CloseOut("Close Out"),
    ForwardForApproval("Forward for Approval"),
    Transmit("Transmit"),
    Publish("Publish"),
    UnPublish("Un-Publish"),
    SubmitRFP("Submit RFP"),
    ReviewResponse("Review Response"),
    CloseRFP("Close RFP"),
    InitiateCO("Initiate CO"),
    SubmitRequest("Submit Request"),
    InitiateRFP("Initiate RFP"),
    ReOpen("Re-open"),
    Release("Release"),
    Resolve("Resolve"),
    ReDraft("Re-Draft"),
    Redraft("Redraft"),
    WorkOrderReDraft("ReDraft"),
    Reject("Reject"),
    RePublish("Re-Publish"),
    SendResponse("Send Response"),
    ReSubmitRequest("Re-Submit Request"),
    MarkRFIClosed("Mark RFI Closed"),
    Revert("Revert"),
    PreAward("Pre-Award"),
    UnApprove("Undo Approve"),
    Respond("Respond"),
    Execute("Execute"),
    UndoCommit("Undo-Commit");

    private String value;

    WorkFlowActions(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
