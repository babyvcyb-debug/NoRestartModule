package com.example.norestart;

import android.app.ActivityThread;
import android.os.IBinder;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class NoRestartModule implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // Hook restart activity
        findAndHookMethod(ActivityThread.class,
                "performRestartActivity",
                IBinder.class,
                boolean.class,
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        // Chặn restart
                        param.setResult(null);
                    }
                });
    }
}
