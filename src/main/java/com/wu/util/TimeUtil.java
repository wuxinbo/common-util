package com.wu.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间计算帮助方法
 * Created by wuxinbo on 16-2-2.
 */
public class TimeUtil extends BaseUtil {
    protected static Logger logger = Logger.getLogger(TimeUtil.class);

    /**
     * 获取当前时间
     */
    public static Date getCurrentTime() {
        return new Date();
    }

    /**
     * 获取该年的一月。
     *
     * @param Month 月份格式为 yyyy-MM
     * @return
     */
    public static String getFirstMonthOfYear(String Month) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(Month);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        sdf = new SimpleDateFormat("yyyy/MM");
        cal.setTime(date);
        cal.add(Calendar.MONTH, -cal.get(Calendar.MONTH));
        return sdf.format(cal.getTime());
    }
}
