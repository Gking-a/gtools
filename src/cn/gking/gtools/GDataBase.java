package cn.gking.gtools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GDataBase {
    public static final byte TYPE_STRING = 0;
    public static final byte TYPE_STRINGS = 1;
    public static final byte TYPE_DB = 2;
    public static final byte TYPE_DBS = 3;
    public static final byte TYPE_GSV = 4;
    private final Container container;
    private IO io;
    public static Map<Long,GSerialableValue> ada=new HashMap<>();
    public static void setAda(GSerialableValue ada){
        GDataBase.ada.put(ada.id(), ada);
    }
    private static GSerialableValue findAdapter(long tolong) {
        return ada.get(tolong).newInstance();
    }
    public void add(String a, String b) {
        container.add(a,b);
    }
    public void add(String a, GDataBase b) {
        container.add(a,b);
    }
    public void replace(String a, List<String> b) {
        container.replace(a,b);
    }
    public void replaceDBs(String a, List<GDataBase> b) {
        container.replaceDBs(a,b);
    }
    public void remove(String a, byte type) {
        container.remove(a,type);
    }
    public String getString(String s) {
        return container.getString(s);
    }
    public List<String> getStrings(String s) {
        return container.getStrings(s);
    }
    public GDataBase getDB(String s) {
        return container.getDB(s);
    }
    public List<GDataBase> getDBs(String s) {
        return container.getDBs(s);
    }

    public void add(String a,Object o){add(a,o.toString());}
    public void save(){
        io.save(container);
    }
    public GDataBaseStorageAdapter getIO(){return io==null?null:io.getFile();}
    public void setIOAdapter(GDataBaseStorageAdapter gAdapter){io=new IO(gAdapter);container.io=this.io;}
    public byte[] getData(){
        List<byte[]> encode = container.encode(null);
        try {
            int bytesize=0;
            for (int i = 0; i < encode.size(); i++) {
                bytesize+=encode.get(i).length;
            }
            //FileOutputStream outputStream=new FileOutputStream(file);
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream(bytesize);
            for (byte[] bs :
                    encode) {
                outputStream.write(bs);
            }
            outputStream.flush();
            outputStream.close();
            return outputStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean exists(){return io.exists();}
    public boolean create(){return io.create();}
    public boolean delete(){return io.delete();}
    public boolean autoCreate(){
        if(!getFile().exists())return getFile().create();
        else return true;
    }
    public void addInt(String s,int i){
        container.gm.put(s,new BaseTypeAdapter.IntAdapter(i));
    }
    public void addFloat( String s, float f ) {
        container. gm.put(s, new BaseTypeAdapter.FloatAdapter(f));
    }

    public void addDouble(String s, double d) {
        container.gm.put(s, new BaseTypeAdapter.DoubleAdapter(d));
    }

    public void addLong(String s, long l) {
        container.gm.put(s, new BaseTypeAdapter.LongAdapter(l));
    }

    public void addShort(String s, short sh) {
        container.gm.put(s, new BaseTypeAdapter.ShortAdapter(sh));
    }

    public void addByte(String s, byte b) {
        container.gm.put(s, new BaseTypeAdapter.ByteAdapter(b));
    }
    public void addFloatArray(String s, float[] arr) {
        container.gm.put(s, new BaseTypeAdapter.FloatArrayAdapter(arr));
    }

    public void addDoubleArray(String s, double[] dArr) {
        container.gm.put(s, new BaseTypeAdapter.DoubleArrayAdapter(dArr));
    }

    public void addLongArray(String s, long[] lArr) {
        container.gm.put(s, new BaseTypeAdapter.LongArrayAdapter(lArr));
    }

    public void addShortArray(String s, short[] shArr) {
        container.gm.put(s, new BaseTypeAdapter.ShortArrayAdapter(shArr));
    }

    public void addByteArray(String s, byte[] bArr) {
        container.gm.put(s, new BaseTypeAdapter.ByteArrayAdapter(bArr));
    }
    public void addIntArray(String s, int[] iArr) {
        container.gm.put(s, new BaseTypeAdapter.IntArrayAdapter(iArr));
    }
    public int getInt(String s){
        return ((BaseTypeAdapter.IntAdapter) container.gm.get(s)).value;
    }
    public int[] getIntArray(String s){
        return ((BaseTypeAdapter.IntArrayAdapter) container.gm.get(s)).value;
    }
    public float getFloat(String s){
        return ((BaseTypeAdapter.FloatAdapter) container.gm.get(s)).value;
    }

    public float[] getFloatArray(String s){
        return ((BaseTypeAdapter.FloatArrayAdapter) container.gm.get(s)).value;
    }

    public double getDouble(String s){
        return ((BaseTypeAdapter.DoubleAdapter) container.gm.get(s)).value;
    }

    public double[] getDoubleArray(String s){
        return ((BaseTypeAdapter.DoubleArrayAdapter) container.gm.get(s)).value;
    }

    public long getLong(String s){
        return ((BaseTypeAdapter.LongAdapter) container.gm.get(s)).value;
    }

    public long[] getLongArray(String s){
        return ((BaseTypeAdapter.LongArrayAdapter) container.gm.get(s)).value;
    }

    public short getShort(String s){
        return ((BaseTypeAdapter.ShortAdapter) container.gm.get(s)).value;
    }

    public short[] getShortArray(String s){
        return ((BaseTypeAdapter.ShortArrayAdapter) container.gm.get(s)).value;
    }

    public byte getByte(String s){
        return ((BaseTypeAdapter.ByteAdapter) container.gm.get(s)).value;
    }

    public byte[] getByteArray(String s){
        return ((BaseTypeAdapter.ByteArrayAdapter) container.gm.get(s)).value;
    }
    public GDataBaseStorageAdapter getFile(){return io.getFile();}
    public GDataBase(GDataBaseStorageAdapter file) {
        io = new IO(file);
        container = new Container(io);
    }
    public GDataBase(File file){
        this(new GDataBaseStorageAdapterFile(file));
    }
    public Map<Byte,Map> asMap(){
        return new HashMap<Byte,Map>(){{
            put(TYPE_STRING,container.sm);
            put(TYPE_STRINGS,container.tm);
            put(TYPE_DB,container.cm);
            put(TYPE_DBS,container.dm);
        }};
    }

    static {
        setAda(new BaseTypeAdapter.IntArrayAdapter());
        setAda(new BaseTypeAdapter.IntAdapter(0));
        setAda(new BaseTypeAdapter.FloatArrayAdapter(null));
        setAda(new BaseTypeAdapter.FloatAdapter(0f));
        setAda(new BaseTypeAdapter.DoubleAdapter(0.0));
        setAda(new BaseTypeAdapter.DoubleArrayAdapter(null));
        setAda(new BaseTypeAdapter.LongArrayAdapter(null));
        setAda(new BaseTypeAdapter.LongAdapter(0));
        setAda(new BaseTypeAdapter.ShortArrayAdapter(null));
        setAda(new BaseTypeAdapter.ShortAdapter((short) 0));
        setAda(new BaseTypeAdapter.ByteArrayAdapter(null));
        setAda(new BaseTypeAdapter.ByteAdapter((byte) 0));
    }
    @Override
    public String toString() {
        return "{"+container.toString()+"}";
    }
    public GDataBase(){
        container=new Container();
    }
    private GDataBase(InputStream is){
        container=new Container(is);
    }
    private class Container {
        public Map<String, String> sm;
        public Map<String, List<String>> tm;
        public Map<String, GDataBase> cm;
        public Map<String, List<GDataBase>> dm;
        public Map<String, GSerialableValue> gm;
        public String getString(String s) {
            return sm.get(s);
        }
        public List<String> getStrings(String s) {
            return tm.get(s);
        }
        public GDataBase getDB(String s) {
            return cm.get(s);
        }
        public List<GDataBase> getDBs(String s) {
            return dm.get(s);
        }
        public void add(String a, String b) {
            if(sm==null)sm=new HashMap<>();
            sm.remove(a);
            sm.put(a, b);
        }
        public void add(String a, GDataBase b) {
            if(cm==null)cm=new HashMap<>();
            cm.remove(a);
            cm.put(a, b);
        }
        public void add(String a, GSerialableValue b) {
            if(gm==null)gm=new HashMap<>();
            gm.remove(a);
            gm.put(a, b);
        }
        public void replace(String a, List<String> b) {
            if(tm==null)tm=new HashMap<>();
            tm.remove(a);
            tm.put(a, b);
        }
        public void replaceDBs(String a, List<GDataBase> b) {
            if(dm==null)dm=new HashMap<>();
            dm.remove(a);
            dm.put(a, b);
        }
        public void remove(String a, byte type) {
            switch (type) {
                case TYPE_STRING:
                    sm.remove(a);
                    break;
                case TYPE_DB:
                    cm.remove(a);
                    break;
                case TYPE_STRINGS:
                    tm.remove(a);
                    break;
                case TYPE_DBS:
                    dm.remove(a);
                    break;
                case TYPE_GSV:
                    gm.remove(a);
                    break;
            }
        }

        public IO io;
        public Container(IO io) {
            sm=new HashMap<>();
            tm=new HashMap<>();
            dm=new HashMap<>();
            cm=new HashMap<>();
            gm=new HashMap<>();
            this.io = io;
            if(io.exists()) init();
        }
        public Container(){
            sm=new HashMap<>();
            tm=new HashMap<>();
            dm=new HashMap<>();
            cm=new HashMap<>();
            gm=new HashMap<>();
        }
        public Container(InputStream is){
            init(is);
        }
        public boolean initiated = false;
        public void init(InputStream is){
            Coder.decode(this,is);
            initiated = true;
        }
        public void init() {
            Coder.decode(this,io.getInputStream());
            initiated = true;
        }
        public List<byte[]> encode(List<byte[]> bytes) {
            if(bytes==null)bytes=new LinkedList<>();
            return Coder.encode(this,bytes);
        }

        @Override
        public String toString() {
            return "Container{" +
                    "sm=" + sm +
                    ", tm=" + tm +
                    ", cm=" + cm +
                    ", dm=" + dm +
                    ", gm=" + gm +
                    ", io=" + io +
                    ", initiated=" + initiated +
                    '}';
        }
    }
    private class IO {
        public IO(GDataBaseStorageAdapter file) {
            this.file = file;
        }
        private GDataBaseStorageAdapter file;
        public GDataBaseStorageAdapter getFile() { return file; }
        public boolean exists() {
            return file != null && file.exists() && file.isFile();
        }
        public boolean create() {
            if (file == null) return false;
            file.delete();
            return file.create();
        }
        public boolean delete() {
            return file != null && file.delete();
        }
        //Nullable 我就不写什么注解了
        public InputStream getInputStream() {
            if (file == null) {
                return null;
            }
            try {
                return file.getInputStream();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        public void save(Container container){
            List<byte[]> encode = container.encode(null);
            try {
                //FileOutputStream outputStream=new FileOutputStream(file);
                OutputStream outputStream=file.getOutputStream();
                for (byte[] bs :
                        encode) {
                    outputStream.write(bs);
                }
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public static class Coder {
        public static void decode(Container container,InputStream is) {
            byte[] bs = new byte[1];
            try {
                for (int i = 0; i < 5; i++) {
                    if(is==null||is.available()<=0){
                        container.sm=new HashMap<>();
                        container.tm=new HashMap<>();
                        container.cm=new HashMap<>();
                        container.dm=new HashMap<>();
                        container.gm=new HashMap<>();
                        break;
                    }
                    is.read(bs);
                    byte t = bs[0];
                    if (t == TYPE_STRING) ds(container, is);
                    else if (t==TYPE_STRINGS)dt(container, is);
                    else if(t==TYPE_DB)dc(container, is);
                    else if(t==TYPE_DBS)dd(container, is);
                    else if(t==TYPE_GSV)dg(container, is);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private static void dd(Container container, InputStream is) throws IOException {
            HashMap<String, List<GDataBase>> map = new HashMap<>();
            int al = dLen(is);
            for (int i = 0; i < al; i++) {
                int len = dLen(is);
                String s = dStr(is, len);
                List<GDataBase> list=new ArrayList<>();
                int i1 = dLen(is);
                for (int j = 0; j < i1; j++) {
                    list.add(new GDataBase(is));
                }
                map.put(s, list);
            }
            container.dm=map;
        }
        private static void dc(Container container, InputStream is) throws IOException {
            int al = dLen(is);
            HashMap<String, GDataBase> map = new HashMap<>();
            for (int i = 0; i < al; i++) {
                int len = dLen(is);
                String s = dStr(is, len);
                GDataBase gDataBase=new GDataBase(is);
                map.put(s, gDataBase);
            }
            container.cm = map;
        }
        private static void dt(Container container, InputStream is) throws IOException {
            HashMap<String, List<String>> stringListHashMap = new HashMap<>();
            int al = dLen(is);
            for (int i = 0; i < al; i++) {
                int len = dLen(is);
                String s = dStr(is, len);
                List<String> strings=new ArrayList<>();
                int i1 = dLen(is);
                for (int j = 0; j < i1; j++) {
                    len = dLen(is);
                    String s2 = dStr(is, len);
                    strings.add(s2);
                }
                stringListHashMap.put(s, strings);
            }
            container.tm=stringListHashMap;
        }
        private static void dg(Container container, InputStream is) throws IOException {
            int al = dLen(is);
            HashMap<String, GSerialableValue> stringStringHashMap = new HashMap<>();
            for (int i = 0; i < al; i++) {
                int len = dLen(is);
                String s = dStr(is, len);
                byte[] bytesid = new byte[8];
                is.read(bytesid);
                long tolong = BaseTypeAdapter.tolong(bytesid);
                GSerialableValue ada=findAdapter(tolong);
                len = dLen(is);
                byte[] b = new byte[len];
                is.read(b);
                ada.decode(b);
                stringStringHashMap.put(s, ada);
            }
            container.gm = stringStringHashMap;
        }

        private static void ds(Container container, InputStream is) throws IOException {
            int al = dLen(is);
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            for (int i = 0; i < al; i++) {
                int len = dLen(is);
                String s = dStr(is, len);
                len = dLen(is);
                String s2 = dStr(is, len);
                stringStringHashMap.put(s, s2);
            }
            container.sm = stringStringHashMap;
        }
        private static String dStr(InputStream is, int len) throws IOException {
            if(len==0)return null;
            byte[] b = new byte[len];
            is.read(b);
            return new String(b, StandardCharsets.UTF_8);
        }
        private static int dLen(InputStream is) throws IOException {
            int l = 0;
            int x = 1;
            while (true) {
                int a = is.read();
                l += (a & 0x7f) * x;
                x = x * 128;
                if ((a & 0x80) == 0x80) return l;
            }
        }
        public static List<byte[]> encode(Container container,List<byte[]> bytes){
            {
                Iterator<String> iterator=container.sm.keySet().iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    if(container.sm.get(next)==null)iterator.remove();
                }
            }
            {
                byte[] b1=new byte[]{0};
                bytes.add(b1);
                bytes.add(eLen(container.sm.size()));
                for (String s:container.sm.keySet()){
                    encodeSign(bytes, s);
                    String s2=container.sm.get(s);
                    byte[] b4=eStr(s2);
                    byte[] b5=eLen(b4.length);
                    bytes.add(b5);
                    bytes.add(b4);
                }
            }
            {
                byte[] b1=new byte[]{1};
                bytes.add(b1);
                Map<String, List<String>> tm = container.tm;
                bytes.add(eLen(tm.size()));
                for (String s: tm.keySet()){
                    encodeSign(bytes, s);
                    List<String> strings = tm.get(s);
                    bytes.add(eLen(strings.size()));
                    for (String s2 : strings) {
                        if(s2==null){
                            bytes.add(eLen(0));
                            continue;
                        }
                        byte[] b4 = eStr(s2);
                        byte[] b5 = eLen(b4.length);
                        bytes.add(b5);
                        bytes.add(b4);
                    }
                }
            }
            {
                byte[] b1=new byte[]{2};
                bytes.add(b1);
                Map<String, GDataBase> cm = container.cm;
                bytes.add(eLen(cm.size()));
                for(String s:cm.keySet()){
                    encodeSign(bytes,s);
                    GDataBase gDataBase1 = cm.get(s);
                    gDataBase1.container.encode(bytes);
                }
            }
            {
                byte[] b1=new byte[]{3};
                bytes.add(b1);
                Map<String, List<GDataBase>> dm = container.dm;
                bytes.add(eLen(dm.size()));
                for(String s:dm.keySet()){
                    encodeSign(bytes,s);
                    bytes.add(eLen(dm.get(s).size()));
                    for (GDataBase gdb : dm.get(s)) {
                        gdb.container.encode(bytes);
                    }

                }
            }
            {
                byte[] b1=new byte[]{TYPE_GSV};
                bytes.add(b1);
                Map<String, GSerialableValue> gm = container.gm;
                bytes.add(eLen(gm.size()));
                for(String s:gm.keySet()){
                    encodeSign(bytes,s);
                    GSerialableValue value = gm.get(s);
                    bytes.add(BaseTypeAdapter.tobyte(value.id(),8));
                    byte[] encode = value.encode();
                    bytes.add(eLen(encode.length));
                    bytes.add(encode);
                }
            }
            return bytes;
        }
        private static byte[] encodeSign(List<byte[]> bytes, String s) {
            byte[] b3 = eStr(s);
            byte[] b2 = eLen(b3.length);
            bytes.add(b2);
            bytes.add(b3);
            return b3;
        }
        private static byte[] eLen(int l) {
            final int length=l;
            int x=128;
            int count=0;
            while ((l/x)>0){
                l=l/x;
                count++;
            }
            byte[] r = new byte[count+1];
            count=0;
            l=length;
            while (l/x>0){
                r[count]=(byte) (l%x);
                l=l/x;
                count++;
            }
            r[count]=(byte) ((l%x)|0x80);
            return r;
        }
        private static byte[] eStr(String s) {
            return s.getBytes(StandardCharsets.UTF_8);
        }
    }
}
