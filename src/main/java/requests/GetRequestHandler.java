package requests;

import helpers.HTTPServerHelper;
import utilities.MessagesUtilities;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/*
This class process and implement HTTP GET behaviour
 */
public class GetRequestHandler extends Connection implements RequestHandlerStrategy {

    @Override
    public void handleRequest() {
        File f;
        String path = HTTPServerHelper.fileHome + "/" + reqParams.getFilePath();
        try {
            f = new File(path);
            if (f.exists() && !f.isDirectory()) {

                InputStream file = null;

                file = new FileInputStream(f);
                pout.print("HTTP/1.0 200 OK\r\n" +
                        "Content-Type: " + guessContentType(path) + "\r\n" +
                        "Date: " + new Date() + "\r\n" +
                        "Server: FileServer 1.0\r\n\r\n");
                sendFile(file); // send raw file
            } else if (!f.exists()) {
                MessagesUtilities.handleErrorMessage(404, out, clientSocket);
            } else if (f.isDirectory()) {
                MessagesUtilities.handleErrorMessage(305, out, clientSocket);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MessagesUtilities.handleErrorMessage(404, out, clientSocket);
        }

    }


    public GetRequestHandler(Socket clientSocket, RequestParameters reqParams, OutputStream outputStream) {
        super(clientSocket, reqParams, outputStream);
    }

    private String guessContentType(String path) {
        if (path.endsWith(".html") || path.endsWith(".htm"))
            return "text/html";
        else if (path.endsWith(".txt") || path.endsWith(".java"))
            return "text/plain";
        else if (path.endsWith(".gif"))
            return "image/gif";
        else if (path.endsWith(".class"))
            return "application/octet-stream";
        else if (path.endsWith(".jpg") || path.endsWith(".jpeg"))
            return "image/jpeg";
        else
            return "text/plain";
    }

    //This function send the file to client
    private void sendFile(InputStream file) {
        try {
            byte[] buffer = new byte[1000];
            while (file.available() > 0)
                out.write(buffer, 0, file.read(buffer));
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
