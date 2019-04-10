package requests;

import helpers.HTTPServerHelper;
import utilities.MessagesUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/*
This class process and implement HTTP POST behaviour
 */
public class PutRequestHandler extends Connection implements RequestHandlerStrategy {
    @Override
    public void handleRequest() {
        FileWriter writer = null;
        try {
            File file = new File(HTTPServerHelper.fileHome + reqParams.getFilePath());
            if (!file.isDirectory()) {
                file.getParentFile().mkdirs();
                writer = new FileWriter(file, true);
                writer.write(new String(reqParams.getBody()));
                writer.close();
                MessagesUtilities.handleSuccessMessage(200, out, clientSocket, reqParams.getRequestType());
            } else {
                MessagesUtilities.handleErrorMessage(305, out, clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PutRequestHandler(Socket clientSocket, RequestParameters reqParams, OutputStream outputStream) {
        super(clientSocket, reqParams, outputStream);
    }
}
