package com.foxconn.charles_speed_test;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import java.math.BigDecimal;

import fr.bmartel.speedtest.model.SpeedTestMode;

public class Utility extends Application {

//    public static final String LOG_TAG = "[" + Utility.class.getName() + "]";
    public static final String LOG_TAG = "";

    public enum ACTION {
        NONE,
        DOWNLOAD,
        UPLOAD
    }

    /**
     * spedd examples server uri.
     */
    public final static String SPEED_TEST_SERVER_URI_DL = "http://ipv4.ikoula.testdebit.info/10M.iso";

    /**
     * spedd examples server uri.
     */
    public static final String SPEED_TEST_SERVER_URI_UL = "http://ipv4.ikoula.testdebit.info/";

    /**
     * upload 10Mo file size.
     */
    public static final int FILE_SIZE = 10000000;

    /**
     * Update wifi information used in ms.
     */
    public static final int WIFI_INFO_UPDATE = 1000;

    /**
     * socket timeout used in ms.
     */
    public static final int SOCKET_TIMEOUT = 10000;

    /**
     * conversion const for per second value.
     */
    public static final BigDecimal VALUE_PER_SECONDS = new BigDecimal(1000);

    /**
     * conversion const for M per second value.
     */
    public static final BigDecimal MEGA_VALUE_PER_SECONDS = new BigDecimal(1000000);

    @SuppressLint("DefaultLocale")
    public static void PRINTFD(String className, String methodName, int lineNumber, String strMessage) {
        if(BuildConfig.DEBUG_LOG_ENABLE) {
            String str;
            str = String.format("[%s][%s][%d] : %s",
                                 className,
                                 methodName,
                                 lineNumber,
                                 strMessage);
            Log.d(LOG_TAG, str);
        }
    }

    /**
     * print upload/download result.
     *
     * @param mode                        speed examples mode
     * @param packetSize                  packet size received
     * @param transferRateBitPerSeconds   transfer rate in bps
     * @param transferRateOctetPerSeconds transfer rate in Bps
     */
    public static String logFinishedTask(final SpeedTestMode mode,
                                       final long packetSize,
                                       final BigDecimal transferRateBitPerSeconds,
                                       final BigDecimal transferRateOctetPerSeconds)
    {
        String str = "";
        switch (mode) {
            case DOWNLOAD:
                str = str + "======== Download [ OK ] =============\n";
//                Log.v(LOG_TAG, "======== Download [ OK ] =============");
                break;
            case UPLOAD:
                str = str + "======== Upload [ OK ] =============\n";
//                Log.v(LOG_TAG, "======== Upload [ OK ] =============");
                break;
            default:
                break;
        }

//        Log.v(LOG_TAG, "packetSize     : " + packetSize + " octet(s)");
//        Log.v(LOG_TAG, "transfer rate  : " + transferRateBitPerSeconds + " bit/second   | " + transferRateBitPerSeconds.divide(VALUE_PER_SECONDS, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE) + " Kbit/second  | " + transferRateBitPerSeconds.divide(MEGA_VALUE_PER_SECONDS) + " Mbit/second");
//        Log.v(LOG_TAG, "transfer rate  : " + transferRateOctetPerSeconds + " octet/second | " + transferRateOctetPerSeconds.divide(VALUE_PER_SECONDS, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE) + " Koctet/second | " + transferRateOctetPerSeconds.divide(MEGA_VALUE_PER_SECONDS, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE) + " " +  "Moctet/second");
//        Log.v(LOG_TAG, "##################################################################");

//        str = str + "packetSize     : " + packetSize + " octet(s)\n";
        str = str + "packetSize     : " + packetSize + " byte(s)\n\n";
        str = str + "transfer rate  : ";
//        str = str + transferRateBitPerSeconds + " bit/second\n";
//        str = str + transferRateBitPerSeconds.divide(VALUE_PER_SECONDS, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE) + " Kbit/second\n";
        str = str + transferRateBitPerSeconds.divide(MEGA_VALUE_PER_SECONDS) + " Mbit/second\n\n";
//        str = str + "transfer rate  : " + transferRateOctetPerSeconds + " octet/second | " + transferRateOctetPerSeconds.divide(VALUE_PER_SECONDS, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE) + " Koctet/second | " + transferRateOctetPerSeconds.divide(MEGA_VALUE_PER_SECONDS, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE) + " " +  "Moctet/second\n";
//        str = str + "##################################################################\n";
        str = str + "#################################\n";

        return str;
    }
}

