package cn.gking.gtools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GDataBaseIOAdapterBufferedInputStream extends GDataBaseStorageAdapterStream{
    public int buffsize=1024;
    public GDataBaseIOAdapterBufferedInputStream(InputStream inputStream, OutputStream outputStream) {
        super(null, outputStream);
        super.inputStream=new GDBIABufferedInputStream(inputStream);
    }

    class GDBIABufferedInputStream extends InputStream{
        List<byte[]> slice=new ArrayList<byte[]>();
        InputStream purestm;

        public GDBIABufferedInputStream(InputStream purestm) {
            this.purestm = purestm;
        }

        @Override
        public int read() throws IOException {
            return purestm.read();
        }
        @Override
        public int read(byte[] b) throws IOException {
            while (available()<Math.min(buffsize,b.length)){}
            if(b.length<buffsize) return purestm.read(b);
            else{
                int reqbs=b.length;
                int nrd;
                while (reqbs>0){
                    nrd=Math.min(buffsize,reqbs);
                    while (available()< nrd){}
                    byte[] e = new byte[nrd];
                    purestm.read(e);
                    slice.add(e);
                    reqbs-=nrd;
                }
                int i = 0;
                Iterator<byte[]> iterator = slice.iterator();
                while (iterator.hasNext()){
                    byte[] ed = iterator.next();
                    iterator.remove();
                    System.arraycopy(ed, 0, b, i*buffsize, ed.length);
                    i++;
                }
            }
            return b.length;
        }

        @Override
        public int available() throws IOException {
            return purestm.available();
        }
    }
}
