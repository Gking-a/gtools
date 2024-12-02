package cn.gking.gtools;
//import util.GTimer;
//import managers.GHolder;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class GSingleThreadTask
{
//    public abstract void execute(GHolder args);
    public GSingleThreadTask newInstance(){
        Class clazz=this.getClass();
        Constructor c=clazz.getConstructors()[0];
        try {
            GSingleThreadTask newInstance=(GSingleThreadTask)c.newInstance();
            return newInstance;
        } catch (IllegalAccessException e) {} catch (InvocationTargetException e) {} catch (InstantiationException e) {} catch (IllegalArgumentException e) {}
        return null;
    }
    public static class ThreadManager{
        private static int ThreadLength=0;
        private static ArrayList<Proxy> queue=new ArrayList<>();
        private static ArrayList<RuntimeThread> pool=new ArrayList<>();
        public static RuntimeThread require(Proxy p,int outTime){
            queue.add(p);
//            GTimer timer = new GTimer();
//            do {
//                if (pool.size() != 0) {
//                    if (p.equals(queue.get(0))) {
//                        queue.remove(0);
//                        RuntimeThread r = pool.get(0);
//                        pool.remove(0);
//                        return r;
//                    }
//                }
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                }
//            } while (!timer.compareBigger(outTime));
            return null;
        }
        public static void setThreadLength(int length){
            if(ThreadLength<length){
                for(int i=0;i<length-ThreadLength;i++){
                    RuntimeThread rt=new RuntimeThread();
                    rt.start();
                    pool.add(rt);
                }
            }
            ThreadLength=length;
            if(ThreadLength<pool.size()){
                for(int i=0;i<ThreadLength-pool.size();i++){
                    pool.remove(i);
                }
            }
        }
        public static void back(RuntimeThread rt){
            if(ThreadLength>pool.size()){
                pool.add(rt);
            }else{
                rt.release();
            }
        }
    }

    public static class RuntimeThread extends Thread{
        Proxy proxy;
        private boolean isReleased=false;
        public void setProxy(Proxy p){
            this.proxy=p;
        }
        public Proxy getProxy(){
            return proxy;
        }
        public void release(){
            isReleased=true;
        }
        public void run(){
            while(true){
                if(isReleased){
                    break;
                }
//                if(proxy!=null){
//                    GHolder args;
//                    if(proxy.getQueue().size()!=0&&(args=proxy.getTask())!=null){
//                        proxy.newInstance().execute(args);
//                        if(proxy.finished()){
//                            proxy=null;
//                        }
//                    }
//                }
                try {
                    sleep(100);
                } catch (InterruptedException e) {}
            }
            stop();
        }
    }

    public static class Proxy {
        public Proxy(GSingleThreadTask obj){
            target=obj;
        }
        private RuntimeThread runtimeThread=null;
        private GSingleThreadTask target;
        public static final int RUNNING=0;
        public static final int WAITING=1;
        public static final int STOPPING=2;
        public static final int POLICY_FIFO=0;
        public static final int POLICY_KEEP_WITTING=0;
        public static final int POLICY_FINISH_STOPPING=1;
//        private ArrayList<GHolder> queue=new ArrayList<>();
//        private ArrayList<GHolder> priorityQueue=new ArrayList<>();
//        private int State=2;
//        private int Execute_Order=0;
//        private int Task_Finished=1;
//        public int getState(){
//            return State;
//        }
//        public Proxy setFinishPolicy(int policy){
//            Task_Finished=policy;
//            return this;
//        }
//        public void addTask(GHolder args){
//            queue.add(args);
//        }
//        public void addSpecialTask(GHolder args){
//            priorityQueue.add(args);
//        }
//        public ArrayList<GHolder> getQueue(){
//            return queue;
//        }
//        public GSingleThreadTask newInstance(){
//            return target.newInstance();
//        }
//        public GSingleThreadTask getInstance(){
//            return target;
//        }
//        public GHolder getTask(){
//            if(Execute_Order==POLICY_FIFO){
//                GHolder task=null;
//                if(priorityQueue.size()!=0){
//                    task=priorityQueue.get(0);
//                    priorityQueue.remove(0);
//                }
//                if(queue.size()!=0){
//                    task=queue.get(0);
//                    queue.remove(0);
//                }
//                return task;
//            }
//            return null;
//        }
//        public boolean finished(){
//            if(Task_Finished==POLICY_FINISH_STOPPING){
//                ThreadManager.back(runtimeThread);
//                return true;
//            }
//            if(Task_Finished==POLICY_KEEP_WITTING)
//                return false;
//            return true;
//        }
//        public void add(int count){
//            for (int i = 0; i < count; i++) {
//                restart();
//            }
//        }

        public int getOutTime() {
            return outTime;
        }

        public void setOutTime(int outTime) {
            this.outTime = outTime;
        }

        private int outTime=10*1000;
        public boolean restart(){
            runtimeThread= ThreadManager.require(this,outTime);
            if(runtimeThread!=null){
                runtimeThread.setProxy(this);
                return true;
            }
            return false;
        }
    }
}
