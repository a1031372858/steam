package com.xu.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Description:时间转换工具类
 * @author: wangwenqi
 * @date: 2018年7月17日
 */
public class DateUtil {

	public static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMES_PATTERN = "HH:mm:ss";
	public static final String NOCHAR_PATTERN = "yyyyMMddHHmmss";
	public static final String NOCHAR_PATTERN2 = "yyyyMMdd HH:mm:ss";
	public static final String SIGN_PATTERN = "yyyy.MM.dd";
	public static final String NOCHAR_PATTERN3 = "yyyyMMdd";


	/**
	 * @Description:日期转换为指定格式的日期字符串
	 * @author: wangwenqi
	 * @date: 2018年7月17日
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (null != date) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @Description:字符串转换为指定格式日期对象
	 * @author: wangwenqi
	 * @date: 2018年7月17日
	 * @param strDate
	 * @param pattern
	 * @return Date
	 */
	public static Date parseDate(String strDate, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date nowDate = format.parse(strDate);
			return nowDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description: 时间转换为 yyyy-MM-dd字符串
	 * @author: wangwenqi
	 * @date: 2018年7月17日
	 * @param date
	 * @return String
	 */
	public static String formatDefaultDate(Date date) {
		return formatDateByFormat(date, DEFAULT_PATTERN);
	}

	/**
	 * 时间转换为 yyyy-MM-dd HH:mm:ss字符串 当日期为空时返回当前日期
	 * 
	 * @auther Hedge
	 * @param date
	 * @return
	 */
	public static String formatTimesTampDateCur(Date date) {
		if (date != null) {
			return formatDateByFormat(date, TIMESTAMP_PATTERN);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Description:转换为HH:mm:ss字符串
	 * @author: wangwenqi
	 * @date: 2018年7月17日
	 * @param: @param
	 *             date
	 * @param: @return
	 * @return: String
	 */
	public static String formatTimesDate(Date date) {
		return formatDateByFormat(date, TIMES_PATTERN);
	}

	/**
	 * @Description:转换为yyyyMMddHHmmss字符串
	 * @author: wangwenqi
	 * @date: 2018年7月17日
	 * @param date
	 * @return String
	 */
	public static String formatNoCharDate(Date date) {
		return formatDateByFormat(date, NOCHAR_PATTERN);
	}

	/**
	 * @Description:转换为yyyyMMdd HH:mm:ss字符串
	 * @author: wangwenqi
	 * @date: 2018年7月17日
	 * @param date
	 * @return String
	 */
	public static String formatNoCharDate2(Date date) {
		return formatDateByFormat(date, NOCHAR_PATTERN2);
	}

	/**
	 * @Description:转换为yyyyMMdd字符串
	 * @author: Hedge
	 * @date: 2018年10月4日
	 * @param date
	 * @return String
	 */
	public static String formatNoCharDate3(Date date) {
		return formatDateByFormat(date, NOCHAR_PATTERN3);
	}


	/**
	 * @Description:字符串转 yyyy-MM-dd HH:mm:ss转为日期对象
	 * @author: wangwenqi
	 * @date: 2018年7月17日
	 * @param date
	 * @return Date
	 */
	public static Date parseTimesTampDate(String date) {
		return parseDate(date, TIMESTAMP_PATTERN);
	}

	/**
	 * @Description:字符串转格式yyyy-MM-dd 转为日期对象
	 * @author: wangwenqi
	 * @date: 2018年7月17日
	 * @param date
	 * @return Date
	 */
	public static Date parseDefaultDate(String date) {
		return parseDate(date, DEFAULT_PATTERN);
	}

	/**
	 * @Description:字符串转格式yyyy-MM-dd HH:mm:ss 转为日期对象
	 * @author: wangwenqi
	 * @date: 2018年7月17日
	 * @param date
	 * @return Date
	 */
	public static Date parseDefaultDateTime(String date) {
		return parseDate(date, TIMESTAMP_PATTERN);
	}

	public static int getTimeField(Date date, int field) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(field);
	}
	
	/**
	 * f1日期类型字符串转化成 - f2日期类型字符串
	 * @author yaofang
	 * @date 2018年9月11日
	 * @return
	 */
	public static String dateStringConvert(String before, String f1, String f2) {
		DateFormat beforeFormat = new SimpleDateFormat(f1);	//转换前日期格式字符串生成format
		Date date = null;
		try {
			date = beforeFormat.parse(before);	//得到传入日期
		} catch (ParseException e) {
			System.out.println("DataUtil"+e.getMessage());
			return null;
		}
		DateFormat afterFormat = new SimpleDateFormat(f2);	//转换后日期格式字符串生成format
		String after = afterFormat.format(date);	//转化成我们需要的日期格式f2字符
		return after;
	}

	/**
	 * 字符串转格式yyyy.MM.dd
	 * @param date
	 * @return
	 */
	public static String parseSignDateTime(Date date) {
		return formatDateByFormat(date, SIGN_PATTERN);
	}

	
	/**
	 * 
	 *@Description: 时间前移后移
	 *@author:yingkangkang
	 *@date:2018年12月3日
	 *@param date  日期
	 *@param filed 推移的单位 Calendar.YEAR —-年  Calendar.MONTH—-月 Calendar.DATE—-日 
	 *                         Calendar.HOUR_OF_DAY—-小时 Calendar.MINUTE—-分 calendar.SECOND—-秒
	 *@param value  长度
	 *@return
	 */
	public static Date getMoveTime(Date date, int filed, int value) {
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(filed, value);
	    return calendar.getTime();
	}
	
	/**
	 * 
	 *@Description: 获取时间 年或月或日
	 *@author:yingkangkang
	 *@date:2018年12月3日
	 *@param date      Calendar.YEAR —-年  Calendar.MONTH—-月 Calendar.DATE—-日 
     *                  Calendar.HOUR_OF_DAY—-小时 Calendar.MINUTE—-分 calendar.SECOND—-秒
	 *@param filed
	 *@return
	 */
	public static Integer getTimeByCalendar(Date date, int filed) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    return calendar.get(filed);
	}
	
	
	/**
	 * 
	 *@Description: 改变时间
	 *@author:yingkangkang
	 *@date:2018年12月3日
	 *@param date
	 *@param filed
	 *@param value
	 *@return
	 */
	public static Date changeTime (Date date, int filed, int value) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(filed, value);
	    return calendar.getTime();
	}
	
//    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//        Date ownerBirthday = sdf2.parse("1977-01-01");
//        Date insuredBrirthday = sdf2.parse("1978-01-11"); 
//        Date signDate = sdf2.parse("2018-12-25");
//        Date chargeDate = sdf2.parse("2019-1-10");
//        System.out.println(getPolicyEffectiveDate(null, ownerBirthday, insuredBrirthday, signDate, chargeDate));
//        
//    }
}
