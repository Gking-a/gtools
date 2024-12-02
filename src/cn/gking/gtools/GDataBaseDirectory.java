package cn.gking.gtools;

import java.io.File;

public class GDataBaseDirectory extends GDataBase{
    File dir;
    public GDataBaseDirectory(File dir){
        this.dir =dir;
    }
    public GDataBase autoCreate(String path){
        GDataBase db = getDB(path);
        if(db==null){
            db=new GDataBase((GDataBaseStorageAdapter) new GDataBaseStorageAdapterFile(new File(dir,path)));
            db.create();
        }
        return db;
    }
}
