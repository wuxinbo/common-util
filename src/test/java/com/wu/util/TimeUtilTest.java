package com.wu.util;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

/**
 * 单元测试
 * Created by wuxinbo on 16-2-2.
 */
public class TimeUtilTest {
    Logger logger = Logger.getLogger(getClass());

    @Test
    public void testGetTime() {
        logger.info(TimeUtil.getCurrentTime());
        System.out.println(TimeUtil.getCurrentTime());
    }

}
