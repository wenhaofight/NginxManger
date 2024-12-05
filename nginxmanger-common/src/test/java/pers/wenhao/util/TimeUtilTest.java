package pers.wenhao.util;

import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;

class TimeUtilTest {

    @Test
    void test() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        System.out.println(zero);
        String yesterday = TimeUtil.date2FormatYYYYMMDDHHmmss(TimeUtil.addDateHour(new Date(), -24));
        System.out.println("yesterday： " + yesterday);
        String onehourago = TimeUtil.date2FormatYYYYMMDDHHmmss(TimeUtil.addDateHour(new Date(), -1));
        System.out.println("onehourago： " + onehourago);

    }
}