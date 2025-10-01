package com.example.norestart;

import android.content.Context;
import android.content.Intent;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class NoRestartHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.example.targetapp")) return;
        try {
            XposedHelpers.findAndHookMethod(
                "android.content.BroadcastReceiver",
                lpparam.classLoader,
                "onReceive",
                Context.class,
                Intent.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Intent intent = (Intent) param.args[1];
                        if (intent != null && "android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                            XposedBridge.log("NoRestartHook: blocked Bluetooth STATE_CHANGED for " + lpparam.packageName);
                            param.setResult(null);
                        }
                    }
                }
            );
        } catch (Throwable t) {
            XposedBridge.log("NoRestartHook error: " + t.toString());
        }
    }
}
