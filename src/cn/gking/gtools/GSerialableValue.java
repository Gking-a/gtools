package cn.gking.gtools;

import java.io.InputStream;

public interface GSerialableValue {
    int len();
    byte[] encode();
    void decode(byte[] data);
    long id();
    GSerialableValue newInstance();
}
