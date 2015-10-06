package com.srost_studio.assignment.util;

import android.app.usage.UsageEvents;
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class EventBus extends Bus{
    private static EventBus instance;
    private final Handler mainThread = new Handler(Looper.getMainLooper());

    private EventBus() {

    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    post(event);
                }
            });
        }
    }

    public static synchronized EventBus getInstance() {
        if(instance == null) {
            instance = new EventBus();
        }
        return instance;
    }
}
