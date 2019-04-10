package server;

import exceptions.UnknownRequestException;
import files.FileConcurencyManager;
import helpers.StringsHelpers;
import requests.*;
import utilities.RequestUtilities;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WorkerRunnable implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;
    protected RequestType requestType = null;
    protected RequestParameters requestParameters = null;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    protected FileConcurencyManager fileManager;



    public WorkerRunnable(Socket clientSocket, String serverText, FileConcurencyManager fileConcurencyManager) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        this.fileManager = fileConcurencyManager;
    }

    //main workflow for server clients
    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(input));

            RequestParameters reqParams = RequestUtilities.processRequest(in);
            logger.info(reqParams.toString());

            if(reqParams.isValidReq())
            {
                //Decide request type
                RequestHandlerStrategy requestHandler = decideRequestStrategy(clientSocket, reqParams, output);
                while(fileManager.isFileInProcessing(reqParams.getFilePath()))
                {
                    logger.log(Level.INFO, StringsHelpers.INFO_FILE_IN_PROCESSING);
                }
                fileManager.processFile(reqParams.getFilePath());
                requestHandler.handleRequest();
                fileManager.releaseFile(reqParams.getFilePath());
            }
            output.close();
            input.close();


        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        } catch (UnknownRequestException e) {
            e.printStackTrace();
        }
    }

    //this function return tailored handlers for each request
    RequestHandlerStrategy decideRequestStrategy(Socket socketClient, RequestParameters requestParameters, OutputStream outputStream) throws UnknownRequestException {
        RequestHandlerStrategy requestHandler = null;
        switch (requestParameters.getRequestType()) {
            case GET:
                requestHandler = new GetRequestHandler(socketClient, requestParameters, outputStream);
                break;
            case PUT:
                requestHandler = new PutRequestHandler(socketClient, requestParameters, outputStream);
                break;
            case POST:
                requestHandler = new PostRequestHandler(socketClient, requestParameters, outputStream);
                break;
            case DELETE:
                requestHandler = new DeleteRequestHandler(socketClient, requestParameters, outputStream);
                break;
            case UNKNOWN:
                throw new UnknownRequestException(StringsHelpers.REQUEST_UNKNOWN_EXCEPTION);
        }
        return requestHandler;
    }

}