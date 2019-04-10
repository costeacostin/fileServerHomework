package files;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is storing the state of server mapped files.
 * If a thread is using one of them, a lock will be added on them in order to not be used by
 * Other threads at the same time
 */
public class FileConcurencyManager {
    private ConcurrentHashMap<String, Boolean> inProcessingFilesMap = new ConcurrentHashMap<>();

    private static FileConcurencyManager fileManager;

    private FileConcurencyManager()
    {

    }

    public boolean isFileInProcessing(String fileName)
    {
        boolean fileStatus;
        synchronized (inProcessingFilesMap) {
            if(inProcessingFilesMap.get(fileName) != null)
            {
                fileStatus = inProcessingFilesMap.get(fileName);
            }
            else
            {
                fileStatus = false;
            }
        }
        return fileStatus;
    }

    public void processFile(String fileName)
    {
        synchronized (inProcessingFilesMap) {
            inProcessingFilesMap.put(fileName, true);
        }
    }

    public void releaseFile(String fileName)
    {
        synchronized (inProcessingFilesMap) {
            inProcessingFilesMap.put(fileName, false);
        }
    }

    public static FileConcurencyManager getInstance()
    {
        if (fileManager == null) {
            fileManager = new FileConcurencyManager();
        }

        return fileManager;
    }
}
