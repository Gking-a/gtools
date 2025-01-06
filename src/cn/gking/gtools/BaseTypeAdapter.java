package cn.gking.gtools;

import java.nio.*;

public abstract class BaseTypeAdapter<T> implements GSerialableValue{
    T value;


    public static class IntAdapter extends BaseTypeAdapter<Integer>{
        public IntAdapter(int v) {
            value=v;
        }
        @Override
        public int len() {
            return 4;
        }
        @Override
        public byte[] encode() {
            return tobyte(value,len());
        }

        @Override
        public void decode(byte[] data) {
            value=(int)tolong(data);
        }

        @Override
        public long id() {
            return 0;
        }

        @Override
        public GSerialableValue newInstance() {
            return new IntAdapter(0);
        }
    }
    public static class LongAdapter extends BaseTypeAdapter<Long>{
        public LongAdapter(long v) {
            value=v;
        }
        @Override
        public int len() {
            return 8;
        }
        @Override
        public byte[] encode() {
            return tobyte(value,len());
        }

        @Override
        public void decode(byte[] data) {
            value=tolong(data);
        }

        @Override
        public long id() {
            return 1;
        }

        @Override
        public GSerialableValue newInstance() {
            return new LongAdapter(0);
        }
    }
    public static class ShortAdapter extends BaseTypeAdapter<Short>{
        public ShortAdapter(short v) {
            value=v;
        }
        @Override
        public int len() {
            return 2;
        }
        @Override
        public byte[] encode() {
            return tobyte(value,len());
        }

        @Override
        public void decode(byte[] data) {
            value=(short)tolong(data);
        }

        @Override
        public long id() {
            return 2;
        }

        @Override
        public GSerialableValue newInstance() {
            return new ShortAdapter((short) 0);
        }
    }
    public static class ByteAdapter extends BaseTypeAdapter<Byte>{
        public ByteAdapter(byte v) {
            value=v;
        }
        @Override
        public int len() {
            return 1;
        }
        @Override
        public byte[] encode() {
            return tobyte(value,len());
        }

        @Override
        public void decode(byte[] data) {
            value=(byte)tolong(data);
        }

        @Override
        public long id() {
            return 3;
        }

        @Override
        public GSerialableValue newInstance() {
            return new ByteAdapter((byte) 0);
        }
    }
    static byte[] tobyte(long l,int len){
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[len-1-i]=(byte)((l>>(i*8))&0xff);
        }
        return bytes;
    }
    static long tolong(byte[] data){
        long l=0;
        for (int i = 0; i < data.length; i++) {
            byte tmp=data[data.length-1-i];
            long tmp2=tmp<0?256+tmp:tmp;
            l=l|(tmp2<<(i*8));
        }
        return l;
    }
    public static class FloatAdapter extends BaseTypeAdapter<Float>{
        public FloatAdapter(Float v) {
            value=v;
        }
        @Override
        public int len() {
            return 4;
        }
        @Override
        public byte[] encode() {
            ByteBuffer byteBuffer=ByteBuffer.allocate(4);
            byteBuffer.putFloat(value);
            return byteBuffer.array();
        }

        @Override
        public void decode(byte[] data) {
            ByteBuffer wrap = ByteBuffer.wrap(data);
            value=wrap.getFloat();
        }

        @Override
        public long id() {
            return 4;
        }

        @Override
        public GSerialableValue newInstance() {
            return new FloatAdapter(0f);
        }
    }
    public static class DoubleAdapter extends BaseTypeAdapter<Double>{
        public DoubleAdapter(double v) {
            value=v;
        }
        @Override
        public int len() {
            return 8;
        }
        @Override
        public byte[] encode() {
            ByteBuffer byteBuffer=ByteBuffer.allocate(len());
            byteBuffer.putDouble(value);
            return byteBuffer.array();
        }

        @Override
        public void decode(byte[] data) {
            ByteBuffer wrap = ByteBuffer.wrap(data);
            value=wrap.getDouble();
        }

        @Override
        public long id() {
            return 5;
        }

        @Override
        public GSerialableValue newInstance() {
            return new DoubleAdapter(0.0);
        }
    }
    public static class DoubleArrayAdapter extends BaseTypeAdapter<double[]>{
        public DoubleArrayAdapter(double[] v) {
            value=v;
        }
        @Override
        public int len() {
            return 8* value.length;
        }
        @Override
        public byte[] encode() {
            ByteBuffer byteBuffer=ByteBuffer.allocate(len());
            DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
            doubleBuffer.put(value);
            return byteBuffer.array();
        }

        @Override
        public void decode(byte[] data) {
            ByteBuffer wrap = ByteBuffer.wrap(data);
            DoubleBuffer buffer = wrap.asDoubleBuffer();
            double[] dst = new double[buffer.limit()];
            buffer.get(dst);
            value =dst;
        }

        @Override
        public long id() {
            return 6;
        }

        @Override
        public GSerialableValue newInstance() {
            return new DoubleArrayAdapter(null);
        }
    }
    public static class FloatArrayAdapter extends BaseTypeAdapter<float[]> {

        public FloatArrayAdapter(float[] v) {
            value = v;
        }

        @Override
        public int len() {
            return 4 * value.length; // float 的长度是 4 字节
        }

        @Override
        public byte[] encode() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(len());
            FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
            floatBuffer.put(value);
            return byteBuffer.array();
        }

        @Override
        public void decode(byte[] data) {
            ByteBuffer wrap = ByteBuffer.wrap(data);
            FloatBuffer floatBuffer = wrap.asFloatBuffer();
            float[] dst = new float[floatBuffer.limit()];
            floatBuffer.get(dst);
            value =dst;
        }

        @Override
        public long id() {
            return 7;
        }

        @Override
        public GSerialableValue newInstance() {
            return new  FloatArrayAdapter(null);
        }
    }
// ByteArrayAdapter.java

    public static class ByteArrayAdapter extends BaseTypeAdapter<byte[]> {

        public ByteArrayAdapter(byte[] v) {
            value = v;
        }

        @Override
        public int len() {
            return value.length; // byte 的长度是 1 字节
        }

        @Override
        public byte[] encode() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(len());
            byteBuffer.put(value);
            return byteBuffer.array();
        }

        @Override
        public void decode(byte[] data) {
            value = data;
        }

        @Override
        public long id() {
            return 8;
        }

        @Override
        public GSerialableValue newInstance() {
            return new ByteArrayAdapter(null);
        }
    }
// IntArrayAdapter.java

    public static class IntArrayAdapter extends BaseTypeAdapter<int[]> {
        public IntArrayAdapter() {
        }

        public IntArrayAdapter(int[] v) {
            value = v;
        }

        @Override
        public int len() {
            return 4 * value.length; // int 的长度是 4 字节
        }

        @Override
        public byte[] encode() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(len());
            IntBuffer intBuffer = byteBuffer.asIntBuffer();
            intBuffer.put(value);
            return byteBuffer.array();
        }

        @Override
        public void decode(byte[] data) {
            ByteBuffer wrap = ByteBuffer.wrap(data);
            value = new int[wrap.remaining() / 4];
            IntBuffer intBuffer = wrap.asIntBuffer();
            intBuffer.get(value);
        }

        @Override
        public long id() {
            return 9;
        }

        @Override
        public GSerialableValue newInstance() {
            return new IntArrayAdapter(null);
        }
    }
// ShortArrayAdapter.java

    public static class ShortArrayAdapter extends BaseTypeAdapter<short[]> {

        public ShortArrayAdapter(short[] v) {
            value = v;
        }

        @Override
        public int len() {
            return 2 * value.length; // short 的长度是 2 字节
        }

        @Override
        public byte[] encode() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(len());
            ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
            shortBuffer.put(value);
            return byteBuffer.array();
        }

        @Override
        public void decode(byte[] data) {
            ByteBuffer wrap = ByteBuffer.wrap(data);
            value = new short[wrap.remaining() / 2];
            ShortBuffer shortBuffer = wrap.asShortBuffer();
            shortBuffer.get(value);
        }

        @Override
        public long id() {
            return 10;
        }

        @Override
        public GSerialableValue newInstance() {
            return new ShortArrayAdapter(null);
        }
    }
// LongArrayAdapter.java

    public static class LongArrayAdapter extends BaseTypeAdapter<long[]> {

        public LongArrayAdapter(long[] v) {
            value = v;
        }

        @Override
        public int len() {
            return 8 * value.length; // long 的长度是 8 字节
        }

        @Override
        public byte[] encode() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(len());
            LongBuffer longBuffer = byteBuffer.asLongBuffer();
            longBuffer.put(value);
            return byteBuffer.array();
        }

        @Override
        public void decode(byte[] data) {
            ByteBuffer wrap = ByteBuffer.wrap(data);
            value = new long[wrap.remaining() / 8];
            LongBuffer longBuffer = wrap.asLongBuffer();
            longBuffer.get(value);
        }

        @Override
        public long id() {
            return 11;
        }

        @Override
        public GSerialableValue newInstance() {
            return new LongArrayAdapter(null);
        }
    }
}
