package cn.gking.gtools;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class GSingleTask<T>{
    Set<SimpleListener<T>> simpleListeners=new HashSet<>();
    TaskCallable<T> taskRunnable;
    private final FutureTask<T> futureTask;
    public GSingleTask(TaskCallable<T> taskRunnable) {
        this.taskRunnable = taskRunnable;
        taskRunnable.setLock(this);
        futureTask = new FutureTask<>(taskRunnable);
    }
    public synchronized void execute(SimpleListener simpleListener){
        futureTask.run();
        if (futureTask.isDone()){
            try {
                simpleListener.onListen(futureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return;
        }
        simpleListeners.add(simpleListener);
    }
    public boolean isDone(){return futureTask.isDone();}
    public static abstract class TaskCallable<T> implements Callable<T>{
        GSingleTask lock;

        public void setLock(GSingleTask lock) {
            this.lock = lock;
        }

        @Override
        public final T call() {
            synchronized (lock){
                T result = task();
                Iterator<SimpleListener> iterator = ((Set<SimpleListener>) lock.simpleListeners).iterator();
                while (iterator.hasNext()){
                    iterator.next().onListen(result);
                    iterator.remove();
                }
                return result;
            }

        }
        public abstract T task();
    }
}
