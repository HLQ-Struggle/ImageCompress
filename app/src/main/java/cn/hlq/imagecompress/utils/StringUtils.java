package cn.hlq.imagecompress.utils;

import android.graphics.Bitmap;
import android.os.Build;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by HLQ on 2017/5/18
 */
public class StringUtils {

    /**
     * 读取文件大小
     *
     * @param size
     * @return
     */
    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 获取毫秒数
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public static String getMS(long starTime, long endTime) {
        DateFormat formatter = new SimpleDateFormat("ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endTime - starTime);
        return "压缩所耗时间为：" + formatter.format(calendar.getTime()) + "ms";
    }

    /**
     * 得到bitmap的大小
     */
    public static String getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return getReadableFileSize(bitmap.getAllocationByteCount());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return getReadableFileSize(bitmap.getByteCount());
        }
        // 在低版本中用一行的字节x高度
        return getReadableFileSize(bitmap.getRowBytes() * bitmap.getHeight());//earlier version
    }

}
