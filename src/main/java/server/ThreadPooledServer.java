package server;

import files.FileConcurencyManager;
import helpers.HTTPServerHelper;
import helpers.StringsHelpers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadPooledServer implements Runnable {

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    protected int serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;
    protected ExecutorService threadPool =
            Executors.newFixedThreadPool(HTTPServerHelper.numberOfThreads);

    public ThreadPooledServer(int port) {
        this.serverPort = port;
    }

    //Main server workflow
    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        logger.log(Level.INFO, StringsHelpers.INFO_SERVER_STARTED + serverPort);

        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                clientSocket.setSoTimeout(10000);
            } catch (IOException e) {
                if (isStopped()) {
                   logger.log(Level.INFO, StringsHelpers.INFO_SERVER_STOPPED);
                    break;
                }
                throw new RuntimeException(
                        StringsHelpers.ERROR_ACCEPT_CLIENT_CONN, e);
            }
            this.threadPool.execute(
                    new WorkerRunnable(clientSocket,
                            StringsHelpers.INFO_SERVER_THREAD, FileConcurencyManager.getInstance()));
        }
        this.threadPool.shutdown();
        logger.log(Level.INFO, StringsHelpers.INFO_SERVER_STOPPED);
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(StringsHelpers.ERROR_CLOSING_SERVER_EXCEPTION, e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException(StringsHelpers.EXCEPTION_PORT_CANNOT_OPEN + serverPort, e);
        }
    }
}