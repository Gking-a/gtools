package cn.gking.gtools;

import java.io.*;
public class GDataBaseStorageAdapterStream implements GDataBaseStorageAdapter {
    InputStream inputStream;
    OutputStream outputStream;

    public GDataBaseStorageAdapterStream(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return inputStream;
    }

    public OutputStream getOutputStream() throws FileNotFoundException {
        return outputStream;
    }

    @Override
    public boolean create() {
        return outputStream!=null;
    }
}
