package requests;

import java.util.Arrays;

/**
 * This class is a repository for useful parsed information that is present in the request
 */
public class RequestParameters {
    private RequestType requestType = RequestType.UNKNOWN;
    private String filePath;
    private String httpVersion;
    private boolean isKeepAlive;
    private int bodyLength;
    private byte[] body;
    private boolean isValidReq = true;

    public boolean isValidReq() {
        return isValidReq;
    }

    public void setValidReq(boolean validReq) {
        isValidReq = validReq;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public boolean isKeepAlive() {
        return isKeepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        isKeepAlive = keepAlive;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RequestParameters{" +
                "requestType=" + requestType +
                ", filePath='" + filePath + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                ", isKeepAlive=" + isKeepAlive +
                ", bodyLength=" + bodyLength +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
