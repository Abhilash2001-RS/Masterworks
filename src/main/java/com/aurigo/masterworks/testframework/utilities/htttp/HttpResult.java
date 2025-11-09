package com.aurigo.masterworks.testframework.utilities.htttp;

public class HttpResult {

    public String url = null;
    public Integer responseCode = null;
    public String output = null;
    public Exception exception = null;
    public boolean success = false;
    public boolean usedSsl = false;
    public String location = null;

    public HttpResult() {
        //Initialize to no info, and a failed result so that the caller can mark success
        this.url = null;
        this.responseCode = null;
        this.output = null;
        this.responseCode=null;
        this.exception = null;
        this.success = false;
        this.usedSsl = false;
        this.location = null;
    }
}
