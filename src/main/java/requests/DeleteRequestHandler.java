package requests;

import helpers.HTTPServerHelper;
import helpers.StringsHelpers;
import utilities.MessagesUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.util.logging.Level;

/*
This class process and implement HTTP delete behaviour
 */
public class DeleteRequestHandler extends Connection implements RequestHandlerStrategy {
    @Override
    public void handleRequest() {
        File file = new File(HTTPServerHelper.fileHome + reqParams.getFilePath());
        try {
            if (file.isDirectory()) {
                MessagesUtilities.handleErrorMessage(305, out, clientSocket);
            } else {
                boolean result = Files.deleteIfExists(file.toPath());
                if (result) {
                    logger.log(Level.INFO, file.getName() + StringsHelpers.INFO_DELETE_SUCCESS);
                    MessagesUtilities.handleSuccessMessage(200, out, clientSocket, reqParams.getRequestType());
                } else {
                    MessagesUtilities.handleErrorMessage(404, out, clientSocket);
                    logger.log(Level.INFO, StringsHelpers.ERROR_DELETE_FAILED);
                }
            }
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                MessagesUtilities.handleErrorMessage(404, out, clientSocket);
            } else if (e instanceof FileSystemException) {
                MessagesUtilities.handleErrorMessage(500, out, clientSocket);
            }
            e.printStackTrace();
        }
    }


    public DeleteRequestHandler(Socket clientSocket, RequestParameters reqParams, OutputStream outputStream) {
        super(clientSocket, reqParams, outputStream);
    }
}
