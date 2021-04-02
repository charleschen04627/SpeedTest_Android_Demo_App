package com.foxconn.charles_speed_test;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SpeedTestTask speedTest = null;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private HandlerThread wifiInfo_handlerThread;
    private Handler wifiInfo_handler;

    final Runnable wifiInfo_runnable = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(() -> {
                setWifiInfo();
                if(wifiInfo_handler != null) {
                    wifiInfo_handler.postDelayed(wifiInfo_runnable, Utility.WIFI_INFO_UPDATE);
                } else{
                    initHandlerThread();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processView();
        initHandlerThread();
    }

    private void initHandlerThread()
    {
        deInitHandlerThread();

        wifiInfo_handlerThread = new HandlerThread("Wifi info HandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
        wifiInfo_handlerThread.start();
        wifiInfo_handler = new Handler(wifiInfo_handlerThread.getLooper());
        wifiInfo_handler.post(wifiInfo_runnable);
    }

    private void deInitHandlerThread()
    {
        if(wifiInfo_handlerThread != null) {
            wifiInfo_handlerThread.quit();
            Thread moribund = wifiInfo_handlerThread;
            wifiInfo_handlerThread = null;
            moribund.interrupt();
        }
    }

    private void processView()
    {
        Button btnDL;
        Button btnUL;

        btnDL = findViewById(R.id.button_DL);
        btnDL.setText(getResources().getString(R.string.btn_DL_name));
        btnDL.setOnClickListener(btnListener);

        btnUL = findViewById(R.id.button_UL);
        btnUL.setText(getResources().getString(R.string.btn_UL_name));
        btnUL.setOnClickListener(btnListener);

        setDialog();
    }

    private void setWifiInfo()
    {
        TextView textView = findViewById(R.id.wifi_info);
        textView.setText(gteWifiInfo());
    }

    private String gteWifiInfo()
    {
        String strTmp = ConnectivityUtils.getAllWifiInfo(this);
        String[] strArr = strTmp.split(",");

        strTmp = "";
        for (String item : strArr){
            strTmp = String.format("%s\n%s", strTmp, item);
        }

        return (strTmp);
    }

    private void setDialog()
    {
        builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
    }

    private void init_speed_test()
    {
        TextView textView = findViewById(R.id.hello_wold);
        textView.setText("");

        if(speedTest != null) {
            speedTest.cancel(true);
        }
        speedTest = null;
    }

    private void run_speed_test()
    {
        init_speed_test();

        speedTest = new SpeedTestTask();
        speedTest.initSpeedTestTask(mSpeedTestCallback);
        speedTest.execute();
    }

    private void handle_ProgressDialog(boolean show) {
        if (show){
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        deInitHandlerThread();

        if(speedTest != null) {
            speedTest.cancel(true);
        }
        speedTest = null;
        dialog = null;
        builder = null;
    }

    OnSpeedTestCallback mSpeedTestCallback = new OnSpeedTestCallback() {
        @Override
        public void onCompletion(String str) {
            finish_speedTest(str);
        }

        @Override
        public void onProgress(String str) {
            if(dialog != null) {
                runOnUiThread(() -> {
                    TextView textView = dialog.findViewById(R.id.loading_msg);
                    textView.setText(str);
                });
            }
        }

        @Override
        public void onError(String str) {
            finish_speedTest(str);
        }
    };

    private void finish_speedTest(String str) {
        runOnUiThread(() -> {
            TextView textView;
            textView = findViewById(R.id.hello_wold);
            textView.setText(str);

            if(dialog == null) {
                return;
            }

            textView = dialog.findViewById(R.id.loading_msg);
            textView.setText(getResources().getString(R.string.progress_preparing));
        });
        handle_ProgressDialog(false);
    }

    private final View.OnClickListener btnListener = v -> {
        run_speed_test();
        handle_ProgressDialog(true);
    };
}