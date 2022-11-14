package com.meituan.robust;

import android.util.Log;

/**
 * Created by hedex on 16/6/3.
 * a map record the class name before ProGuard and after ProGuard
 */
public class PatchedClassInfo {
    public String patchedClassName;
    public String patchClassName;

    public PatchedClassInfo(String patchedClassName, String patchClassName) {
        Log.d("robust", "PatchedClassInfo() called with: patchedClassName = [" + patchedClassName + "], patchClassName = [" + patchClassName + "]");
        this.patchedClassName = patchedClassName;
        this.patchClassName = patchClassName;
    }
}
