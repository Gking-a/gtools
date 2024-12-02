package cn.gking.gtools;
/*this file is created to use a simple way to
change the stoage way
 */

import java.io.*;
import java.util.List;

public interface GDataBaseStorageAdapter {
    InputStream getInputStream() throws FileNotFoundException;
    OutputStream getOutputStream() throws FileNotFoundException;

    default boolean delete(){return true;};

    default boolean exists(){return true;};

    default boolean isFile(){return true;};

    boolean create();
}
