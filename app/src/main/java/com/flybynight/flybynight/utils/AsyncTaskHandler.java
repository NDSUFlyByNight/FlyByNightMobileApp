package com.flybynight.flybynight.utils;

import android.os.Handler;
import android.os.Looper;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by closestudios on 10/11/15.
 */
public abstract class AsyncTaskHandler<T> implements Callable<T>, Future<T> {

    private static final Executor DEFAULT_EXECUTOR = Executors.newCachedThreadPool();

    private Handler mHandler;
    private Executor mExecutor;
    private FutureTask<T> mFuture;
    private StackTraceElement[] mLaunchLocation;

    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    public Executor getExecutor() {
        if (mExecutor == null) {
            return DEFAULT_EXECUTOR;
        }
        return mExecutor;
    }

    public void setExecutor(Executor executor) {
        mExecutor = executor;
    }

    public FutureTask<T> getFuture() {
        if (mFuture == null) {
            mFuture = new FutureTask<T>(this);
        }
        return mFuture;
    }

    public void execute() {
        mLaunchLocation = Thread.currentThread().getStackTrace();
        getExecutor().execute(getFuture());
    }

    public void retry(boolean mayInterruptIfRunning) {
        if (mayInterruptIfRunning) {
            try {
                mFuture.cancel(true);
            } catch (Exception e) {
                /* ignore */
            }
        }
        mFuture = null;
        execute();
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        return mFuture != null && mFuture.cancel(mayInterruptIfRunning);
    }

    @Override
    public final boolean isCancelled() {
        return mFuture != null && mFuture.isCancelled();
    }

    @Override
    public final boolean isDone() {
        return mFuture != null && mFuture.isDone();
    }

    @Override
    public final T get() throws InterruptedException, ExecutionException {
        try {
            getExecutor().execute(getFuture());
        } catch (Exception e) {
            /* ignore */
        }
        return getFuture().get();
    }

    @Override
    public final T get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        try {
            getExecutor().execute(getFuture());
        } catch (Exception e) {
            /* ignore */
        }
        return getFuture().get(timeout, unit);
    }

    public void onPreCall() throws Exception {

    }

    public abstract T doInBackground() throws Exception;

    public void onException(Exception e) {

    }

    public void onInterrupted(Exception e) {
        onException(e);
    }

    public void onSuccess(T result) {

    }

    public void onProgress(Object progress) {

    }

    public void onFinally() {

    }

    @Override
    public final T call() throws Exception {
        T result = null;
        Exception exception = null;
        try {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            if (isPreCallOverridden(getClass())) {
                beforeCall();
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            result = doInBackground();
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        } catch (Exception e) {
            exception = e;
        } finally {
            afterCall(result, exception);
        }
        if (exception != null) {
            throw exception;
        }
        return result;
    }

    private void postAndWait(final Callable post) throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final Exception[] exceptions = new Exception[1];

        getHandler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    post.call();
                } catch (Exception e) {
                    exceptions[0] = e;
                } finally {
                    latch.countDown();
                }
            }
        });

        // Wait for runnable to finish
        latch.await();

        if (exceptions[0] != null) {
            throw exceptions[0];
        }
    }

    public void postProgress(final Object progress) throws Exception {
        postAndWait(new Callable() {
            @Override
            public Object call() throws Exception {
                onProgress(progress);
                return null;
            }
        });
    }

    private void beforeCall() throws Exception {
        postAndWait(new Callable() {
            @Override
            public Object call() throws Exception {
                doOnPreCall();
                return null;
            }
        });
    }

    private void afterCall(final T result, final Exception exception) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (exception != null) {
                        if (mLaunchLocation != null) {
                            final ArrayList<StackTraceElement> stack = new ArrayList<StackTraceElement>(Arrays.asList(exception.getStackTrace()));
                            stack.addAll(Arrays.asList(mLaunchLocation));
                            exception.setStackTrace(stack.toArray(new StackTraceElement[stack.size()]));
                        }
                        if (exception instanceof InterruptedException || exception instanceof InterruptedIOException) {
                            onInterrupted(exception);
                        } else {
                            onException(exception);
                        }
                    } else {
                        doOnSuccess(result);
                    }
                } finally {
                    onFinally();
                }
            }
        });
    }

    public boolean isPreCallOverridden(Class<? extends AsyncTaskHandler> subClass) {
        try {
            return subClass.getMethod("onPreCall").getDeclaringClass() != AsyncTaskHandler.class;
        } catch (NoSuchMethodException e) {
            /* ignore */
        }
        return false;
    }

    private void doOnPreCall() {
        try {
            onPreCall();
        } catch (Exception e) {
            throw new RuntimeException(e); // This will halt the UI thread
        }
    }

    private void doOnSuccess(T result) {
        try {
            onSuccess(result);
        } catch (Exception e) {
            throw new RuntimeException(e); // This will halt the UI thread
        }
    }
}
