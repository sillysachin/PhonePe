package me.saket.phonepesaket.data;

/**
 * Handles errors that occur while accessing {@link LocalDataRepository}.
 */
public class DataRepoErrorHandler {

    public static DataRepoErrorHandler sDataRepoErrorHandler;

    public static DataRepoErrorHandler getInstance() {
        if (sDataRepoErrorHandler == null) {
            sDataRepoErrorHandler = new DataRepoErrorHandler();
        }
        return sDataRepoErrorHandler;
    }

    public void handle(Throwable e) {
        // TODO
        e.printStackTrace();
    }

}