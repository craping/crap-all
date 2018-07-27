package org.crap.jrain.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.crap.jrain.core.bean.DateFormat;



  
/**  
* @ClassName: DateUtil  
* @Description: 时间操作类
* @author Crap  
* @date 2017年11月2日  
*    
*/  
    
public class DateUtil {
	static Logger logger = LogManager.getLogger(DateUtil.class);

	public static String getAmountDate(int field, int amount, DateFormat format) {
		return getAmountDate(field, amount, format.toString());
	}

	public static String getAmountDate(int field, int amount, String format) {
		String strDate = new SimpleDateFormat(format).format(getAmountDate(field, amount));
		return strDate;
	}

	public static String getAmountDate(Date date, int field, int amount, DateFormat format) {
		return getAmountDate(date, field, amount, format.toString());
	}

	public static String getAmountDate(Date date, int field, int amount, String format) {
		String strDate = new SimpleDateFormat(format).format(getAmountDate(date, field, amount));
		return strDate;
	}

	public static Date getAmountDate(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.add(field, amount);
		return cal.getTime();
	}

	public static Date getAmountDate(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static String formatDate(DateFormat format, Date date) {
		return formatDate(format.toString(), date);
	}

	public static String formatDate(String format, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setLenient(false);
		try {
			return formatter.format(date);
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
		}
		return null;
	}

	public static String formatDate(String inFormat, String toFormat, String strDate) {
		String formatDate = strDate;
		SimpleDateFormat formatter = new SimpleDateFormat(toFormat);
		formatter.setLenient(false);
		try {
			formatDate = formatter.format(parseDate(strDate, inFormat));
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
		}
		return formatDate;
	}

	public static Date parseDate(String strDate, String format) {
		Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(strDate);
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
		}
		return date;
	}

	public static long parseDatetoLong(String strDate, DateFormat format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format.toString());
		try {
			return simpleDateFormat.parse(strDate).getTime();
		} catch (ParseException e) {
			logger.fatal(e.getMessage(), e);
		}
		return 0;
	}

	public static Date parseDate(String strDate, DateFormat format) {
		return parseDate(strDate, format.toString());
	}

	public static Date parseDate(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String strDate = simpleDateFormat.format(date);
		return parseDate(strDate, format.toString());
	}

	public static long getDistance(Date startDate, Date endDate, int field) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);
		int[] p1 = { c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE), c1.get(Calendar.HOUR) };
		int[] p2 = { c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DATE), c1.get(Calendar.HOUR) };

		long distance = 0;
		switch (field) {
		case Calendar.YEAR:
			distance = p2[0] - p1[0];
			break;
		case Calendar.MONTH:
			distance = p2[0] * 12 + p2[1] - p1[0] * 12 - p1[1];
			break;
		case Calendar.DAY_OF_WEEK:
			distance = (long) Math.ceil((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24 * 7.0));
			break;
		case Calendar.DATE:
			distance = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
			break;
		case Calendar.HOUR:
			distance = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
			break;
		default:
			distance = 0;
			break;
		}
		return distance + 1;
	}

	public static String getTomorrow(String dateFormat) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(date);
	}

	/**
	 * 
	 * getDateTomorrow:(获取指定日期的明天).
	 * 
	 * @author zev
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 * @since JDK 1.8
	 */
	public static String getDateTomorrow(String dateStr, String dateFormat) {
		Date date = parseDate(dateStr, dateFormat);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(date);
	}

	// 获取当前时间所在年的周数
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	// 获取当前时间所在年的最大周数
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**
	 * getDateFromYmdToYmdhms yyyy-MM-dd To yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateI
	 * @return
	 */
	public static Date getDateFromYmdToYmdhms(String dateI) {
		return parseDate(dateI + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * getDateFromYmdToYmdhms yyyy-MM-dd To yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateI
	 * @return
	 */
	public static Date getDateFromYmdToYmd(String dateI) {
		return parseDate(dateI, "yyyy-MM-dd ");
	}

	/**
	 * 
	 * @param dateI
	 * @param dateFormat
	 * @param dis
	 * @return
	 */
	public static Date getDateDistance(Date dateI, String dateFormat, int dis) {
		Date date = parseDate(dateI, dateFormat);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dis);// 把日期往后增加dis.整数往后推,负数往前移动
		return calendar.getTime();
	}

	public static Date getDateDistanceFromYmdToYmdhms(String dateI, int dis, String dateFormat) {
		return getDateDistance(getDateFromYmdToYmdhms(dateI + " 00:00:00"), dateFormat, dis);
	}

	public static String formatMYDDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
		return simpleDateFormat.format(date);
	}

	public static String formatMYDDDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}

	// 获取指定日期间日期字符串数组 ["2017-01-01","2017-01-01","2017-01-03", ...]
	public static String[] getDateArray(String beginDate, String endDate) {
		Calendar beginCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		Date begin = DateUtil.parseDate(beginDate, "yyyy-MM-dd");
		Date end = DateUtil.parseDate(endDate, "yyyy-MM-dd");
		beginCal.setTime(begin);// 设置开始日期
		endCal.setTime(end);// 设置结束日期

		long between_days = (endCal.getTimeInMillis() - beginCal.getTimeInMillis()) / (1000 * 3600 * 24);
		int size = Integer.parseInt(String.valueOf(between_days))+1;
		if(size < 1){
			return null;
		}
		String[] dateArray = new String[size];
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				beginCal.add(Calendar.DAY_OF_MONTH, 1);
			}
			Date begintDate = beginCal.getTime();
			String dateStr = DateUtil.formatMYDDDate(begintDate);
			dateArray[i] = dateStr;
		}
		return dateArray;
	}

	/**
	 * 获取某个时间往后多少天
	 * @param calDate
	 * @param addDate
	 * @return
	 */
	public static Date addCalendarDay(Date calDate, long addDate) {
		long time = calDate.getTime();
		addDate = addDate * 24 * 60 * 60 * 1000;
		time += addDate;
		return new Date(time);
	}
	
	/**
	 * 获取两个时间间隔天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalDays(Date maxDate, Date minDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			minDate = sdf.parse(sdf.format(minDate));
			maxDate = sdf.parse(sdf.format(maxDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(minDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(maxDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 获取两个时间间隔小时
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalHours(Date maxDate, Date minDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			minDate = sdf.parse(sdf.format(minDate));
			maxDate = sdf.parse(sdf.format(maxDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(minDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(maxDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 );
		return Integer.parseInt(String.valueOf(between_days));
	}
}
