package cn.gking.gtools;

import java.io.*;
//好冒险
public class GDataBaseStorageAdapterFile implements GDataBaseStorageAdapter{

    private File file;

    public GDataBaseStorageAdapterFile(String pathname) {
        this.file = new File(pathname);
    }

    public GDataBaseStorageAdapterFile(File file) {
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public OutputStream getOutputStream() throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    @Override
    public boolean delete() {
        return file.delete();
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public boolean create() {
        if (file.getParentFile().exists() || file.getParentFile().mkdir()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}