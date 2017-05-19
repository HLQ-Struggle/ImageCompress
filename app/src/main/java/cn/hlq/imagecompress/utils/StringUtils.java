package cn.hlq.imagecompress.utils;

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
     * @param starTime
     * @param endTime
     * @return
     */
    public static String getMS(long starTime, long endTime) {
        DateFormat formatter = new SimpleDateFormat("ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endTime - starTime);
        return "压缩所耗时间为：" + formatter.format(calendar.getTime())+"ms";
    }

}
