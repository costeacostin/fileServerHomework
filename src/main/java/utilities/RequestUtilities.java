package utilities;

import helpers.HTTPServerHelper;
import helpers.StringsHelpers;
import requests.RequestParameters;
import requests.RequestType;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestUtilities {
    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    //This function detect the request method that was used
    public static RequestType detectRequestType(String requestMessage) {
        if (requestMessage.startsWith(StringsHelpers.GET)) {
            return RequestType.GET;
        } else if (requestMessage.startsWith(StringsHelpers.POST)) {
            return RequestType.POST;
        } else if (requestMessage.startsWith(StringsHelpers.DELETE)) {
            return RequestType.DELETE;

        } else if (requestMessage.startsWith(StringsHelpers.PUT)) {
            return RequestType.PUT;
        }
        return RequestType.UNKNOWN;
    }

    //this function fill the requestparameters instance with info extracted from the client request
    public static RequestParameters processRequest(BufferedReader in) {
        RequestParameters requestParameters = new RequestParameters();
        int postDataI = 0;
        String line;
        try {
            line = in.readLine();
            if (line != null) {
                requestParameters.setRequestType(detectRequestType(line));
                requestParameters.setFilePath(getFilePath(line, requestParameters.getRequestType()));

                logger.log(Level.INFO, StringsHelpers.HEADER + line);
                while ((line = in.readLine()) != null && (line.length() != 0)) {
                    logger.log(Level.INFO, StringsHelpers.HEADER + line);
                    if (line.indexOf(StringsHelpers.CONTENT_LENGHT) > -1) {
                        postDataI = new Integer(line.substring(line.indexOf(StringsHelpers.CONTENT_LENGHT) + 16
                        )).intValue();
                    }
                    if (line.startsWith(StringsHelpers.CONNECTION) && line.contains(StringsHelpers.KEEP_ALIVE)) {
                        requestParameters.setKeepAlive(true);
                    }
                }

                String postData = "";
                if (postDataI > 0) {
                    char[] charArray = new char[postDataI];
                    in.read(charArray, 0, postDataI);
                    postData = new String(charArray);
                }
                logger.log(Level.INFO, StringsHelpers.DATA_RECEIVED + postData);
                requestParameters.setBody(postData.getBytes());
            }
            else{
                requestParameters.setValidReq(false);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestParameters;
    }

    //this function extract filepath from the client request
    public static String getFilePath(String request, RequestType requestType) {
        String path = request.substring((request.indexOf(requestType.toString()) + requestType.toString().length()),
                request.length() - 9).trim();
        if (path.equals("/")) {
            path = StringsHelpers.INDEX;
        }
        return path;
    }
}
