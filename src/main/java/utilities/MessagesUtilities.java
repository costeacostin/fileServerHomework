package utilities;

import helpers.StringsHelpers;
import requests.RequestType;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class MessagesUtilities {

    //this function prepare error messages that will be send to the client based on the errorCode
    public static void handleErrorMessage(int errorCode, OutputStream output, Socket socket) {
        PrintStream pout = new PrintStream(output);
        switch (errorCode) {
            case 400:
                reportMessage(pout, socket, errorCode, StringsHelpers.MSG_BAD_FORMAT, StringsHelpers.MSG_D_BAD_FORMAT);
                break;
            case 403:
                reportMessage(pout, socket, errorCode, StringsHelpers.MSG_BAD_FORMAT, StringsHelpers.MSG_D_BAD_FORMAT);
                break;
            case 301:
                reportMessage(pout, socket, errorCode, StringsHelpers.MSG_BAD_FORMAT, StringsHelpers.MSG_D_WRONG_REQ);
                break;
            case 302:
                reportMessage(pout, socket, errorCode, StringsHelpers.MSG_FILE_FOUND, StringsHelpers.MSG_D_FILE_EXISTS);
                break;
            case 305:
                reportMessage(pout, socket, errorCode, StringsHelpers.MSG_DIRECTORY_FOUND, StringsHelpers.MSG_D_DIRECTORY_FOUND);
                break;
            case 404:
                reportMessage(pout, socket, errorCode, StringsHelpers.MSG_FILE_NOT_FOUND, StringsHelpers.MSG_D_FILE_NOT_EXIST);
                break;
            case 500:
                reportMessage(pout, socket, errorCode, StringsHelpers.MSG_INTERNAL_ERROR, StringsHelpers.MSG_D_FILE_PROTECTED);
                break;
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this function prepare success messages that will be send to the client based on the code
    public static void handleSuccessMessage(int code, OutputStream output, Socket socket, RequestType requestType) {
        PrintStream pout = new PrintStream(output);
        if (code == 200) {
            reportMessage(pout, socket, code, requestType.toString(), StringsHelpers.MSG_SUCCESS);
        }
        if (code == 201) {
            reportMessage(pout, socket, code, requestType.toString(), StringsHelpers.MSG_FILE_CREATED);
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this function create a HTML structured STRING with the injected info that will be pretty printed on the client side
    private static void reportMessage(PrintStream pout, Socket connection,
                                      int code, String title, String msg) {
        pout.print("HTTP/1.0 " + code + " " + title + "\r\n" +
                "\r\n" +
                "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\r\n" +
                "<TITLE>" + code + " " + title + "</TITLE>\r\n" +
                "</HEAD><BODY>\r\n" +
                "<H1>" + title + "</H1>\r\n" + msg + "<P>\r\n" +
                "<HR><ADDRESS>FileServer 1.0 at " +
                connection.getLocalAddress().getHostName() +
                " Port " + connection.getLocalPort() + "</ADDRESS>\r\n" +
                "</BODY></HTML>\r\n");
    }

}
