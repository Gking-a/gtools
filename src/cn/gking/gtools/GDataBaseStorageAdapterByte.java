package cn.gking.gtools;

import java.io.*;
/*
after the reform of getData method,this is not necessary
Also,it will READ ALL BYTE IN A STREAM EVEN IF IT DONT NEED SUCH DATA
it is STRONGLY RECOMMENDED to use AdapterStream
 */
@Deprecated
public class GDataBaseStorageAdapterByte implements GDataBaseStorageAdapter {
    int bytesize=0;
    byte[] bytedata;
    public GDataBaseStorageAdapterByte(int bytesize) throws IOException {
        this(new ByteArrayInputStream(new byte[bytesize]),bytesize);
    }
    public GDataBaseStorageAdapterByte(InputStream inputStream) throws IOException {
        bytedata=new byte[inputStream.available()];
        inputStream.read(bytedata);
        bytesize=bytedata.length;
    }
    public GDataBaseStorageAdapterByte(byte[] bytedata) throws IOException {
        this(new ByteArrayInputStream(bytedata));
    }
    public GDataBaseStorageAdapterByte(InputStream inputStream, int bytesize) throws IOException {
        bytedata=new byte[inputStream.available()];
        inputStream.read(bytedata);
        this.bytesize=bytesize;
    }
    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new ByteArrayInputStream(bytedata);
    }

    public OutputStream getOutputStream() throws FileNotFoundException {
        return new ByteArrayOutputStream(bytesize);
    }

    @Override
    public boolean create() {
        return bytedata!=null;
    }
}
