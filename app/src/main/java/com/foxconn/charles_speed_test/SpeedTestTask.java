package com.foxconn.charles_speed_test;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

public class SpeedTestTask extends AsyncTask<Void, Void, String> {
    public static final String LOG_TAG_CLASS_NAME = SpeedTestTask.class.getName();
    private SpeedTestSocket speedTestSocket = null;
    private OnSpeedTestCallback mSpeedTestCallback;
    private Utility.ACTION speedTestAction;

    @SuppressLint("DefaultLocale")
    ISpeedTestListener speedTestListener = new ISpeedTestListener() {
        @Override
        public void onCompletion(SpeedTestReport report) {
            // called when download/upload is complete
            String str = Utility.logFinishedTask(report.getSpeedTestMode(),
                                                 report.getTotalPacketSize(),
                                                 report.getTransferRateBit(),
                                                 report.getTransferRateOctet());

            mSpeedTestCallback.onCompletion(str);
            deInitSpeedTestTask();
        }

        @Override
        public void onProgress(float percent, SpeedTestReport report) {
            // called to notify download/upload progress
            if(speedTestAction == Utility.ACTION.DOWNLOAD)
            {
                mSpeedTestCallback.onProgress(String.format("Test Download ... (%.2f%%)", percent));
            }
            else
            {
                mSpeedTestCallback.onProgress(String.format("Test Upload ... (%.2f%%)", percent));
            }
        }

        @Override
        public void onError(SpeedTestError speedTestError, String errorMessage) {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            int  lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

            String str = String.format("Error : [%s]\n%s",
                                        speedTestError.toString(),
                                        errorMessage);
            Utility.PRINTFD(LOG_TAG_CLASS_NAME, methodName, lineNumber, str);
            mSpeedTestCallback.onError(str);
            deInitSpeedTestTask();
        }
    };

    public void initSpeedTestTask(OnSpeedTestCallback callback){
        speedTestAction = Utility.ACTION.DOWNLOAD;
        mSpeedTestCallback = callback;
        speedTestSocket = new SpeedTestSocket();
        speedTestSocket.addSpeedTestListener(speedTestListener);

        //set timeout for download
        speedTestSocket.setSocketTimeout(Utility.SOCKET_TIMEOUT);
    }

    public void deInitSpeedTestTask(){
        speedTestSocket.clearListeners();
        speedTestSocket.forceStopTask();
        speedTestSocket = null;
    }

    @Override
    @SuppressLint("DefaultLocale")
    protected String doInBackground(Void... params) {
        if(speedTestSocket == null) {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            int  lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
            Utility.PRINTFD(LOG_TAG_CLASS_NAME, methodName, lineNumber, "null");
            return null;
        }

        if(speedTestAction == Utility.ACTION.DOWNLOAD) {
            speedTestSocket.startDownload(Utility.SPEED_TEST_SERVER_URI_DL);
        } else {
            speedTestSocket.startUpload(Utility.SPEED_TEST_SERVER_URI_UL, Utility.FILE_SIZE);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
