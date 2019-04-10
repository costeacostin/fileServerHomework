import helpers.HTTPServerHelper;
import helpers.StringsHelpers;
import server.ThreadPooledServer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger( Main.class.getName() );

    public static void main(String[] args) {
        readProgramParams(args);
        File f = new File(HTTPServerHelper.fileHome);
        if (f.exists() && f.isDirectory()) {
            ThreadPooledServer server = new ThreadPooledServer(HTTPServerHelper.port);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
        else{
            logger.log(Level.SEVERE, StringsHelpers.ERROR_SERVER_HOME_INVALID);
            System.exit(-1);
        }
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
