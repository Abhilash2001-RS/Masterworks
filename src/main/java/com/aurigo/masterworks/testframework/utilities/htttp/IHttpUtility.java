package com.aurigo.masterworks.testframework.utilities.htttp;

import java.util.HashMap;
import java.util.Map;

public interface IHttpUtility {

    int getResponseCode(String address);

    boolean getAllowHttpFailover();

    void setAllowHttpFailover(boolean doHttp);

    void setupProxy(String proxyHost, String proxyPort);

    boolean getDisableSSLandHostNameVerification();

    void setDisableSSLandHostNameVerification(boolean disableSSLandHostNameVerification);

    boolean disableSSLandHostNameVerification();

    void setUseBearerHeader(boolean useBearerHeader);

    int getConnectionTimeoutMsec();

    void setConnectionTimeoutMsec(int connectionTimeoutMsec);

    /**
     * talkHttps
     * This performs HTTPs requests without using any authentication. It does the work of communicating
     * but does not know what it sends or how to interpret the results.
     * Failover to HTTP is allowed through setAllowHttpFailover().  Default is to NOT failover to HTTP.
     *
     * @param httpMethod        - HTTP Method to use (GET, POST, PUT, DELETE, etc.)
     * @param urlString         - URL to send the request to.
     * @param message           - Message data to include in the request body. (Optional; can be set to null)
     * @param contentType       - Content type of the message body. (Optional; can be set to null)
     * @param acceptType        - Data type requested for the response data. (Optional; can be set to null)
     * @param additionalHeaders -   Additional header which needs to be part of Request.
     * @return HttpResult - Data collection representing the communication result;
     */
    HttpResult talkHttp(HttpMethodsEnum httpMethod, String urlString, String queryString, String message, String contentType,
                        String acceptType, Map<String, String> additionalHeaders);

    /**
     * talkHttp
     * This performs HTTP requests. It does the work of communicating but does not know what it sends or how to interpret the results.
     *
     * @param httpMethod        - HTTP Method to use (GET, POST, PUT, DELETE, etc.)
     * @param urlString         - URL to send the request to.
     * @param queryString       - Query string params.
     * @param message           - Message data to include in the request body. (Optional; can be set to null)
     * @param contentType       - Content type of the message body. (Optional; can be set to null)
     * @param acceptType        - Data type requested for the response data. (Optional; can be set to null)
     * @param additionalHeaders -   Additional header which needs to be part of Request.
     * @param mpEntityBuilder   -   Multipart Entity builder for Multipart file upload.
     * @return HttpResult - Data collection representing the communication result;
     */
    HttpResult talkHttp(HttpMethodsEnum httpMethod, String urlString, String queryString, String message, String contentType,
                        String acceptType, Map<String, String> additionalHeaders, HashMap<String, String> mpEntityBuilder);
}


