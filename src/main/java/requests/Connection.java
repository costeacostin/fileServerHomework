package requests;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Logger;

/*
This class act as a repository for each connection (is storing connection parameters and streams
 */
public class Connection {
    protected Socket clientSocket;
    protected RequestParameters reqParams;
    protected PrintStream pout;
    protected OutputStream out;
    protected Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public Connection() {

    }

    public Connection(Socket clientSocket, RequestParameters reqParams, OutputStream out) {
        this.clientSocket = clientSocket;
        this.reqParams = reqParams;
        this.out = out;
        this.pout = new PrintStream(out);

    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public RequestParameters getReqParams() {
        return reqParams;
    }

    public void setReqParams(RequestParameters reqParams) {
        this.reqParams = reqParams;
    }

    public PrintStream getPout() {
        return pout;
    }

    public void setPout(PrintStream pout) {
        this.pout = pout;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }
}
