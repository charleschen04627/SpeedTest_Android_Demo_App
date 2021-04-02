package com.foxconn.charles_speed_test;

public interface OnSpeedTestCallback {
    void onCompletion(String str);
    void onProgress(String str);
    void onError(String str);
}
