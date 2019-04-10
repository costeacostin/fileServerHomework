import helpers.HTTPServerHelper;
import helpers.StringsHelpers;
import server.ThreadPooledServer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger( Main.class.getName() );

    public static void main(String[] args) {
        readProgramParams(args);
        ThreadPooledServer server = new ThreadPooledServer(HTTPServerHelper.port);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    //read program arguments (port and fileHome of File server)
    public static void readProgramParams(String[] args) {
        if (args.length != 2) {
            logger.log(Level.SEVERE, StringsHelpers.ERROR_USAGE_OF_SERVICE);
            System.exit(-1);
        }
        HTTPServerHelper.port = Integer.parseInt(args[0]);
        HTTPServerHelper.fileHome = args[1];
    }

}
