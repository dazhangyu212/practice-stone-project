package com.stone.ordering.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import android.util.Log;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author raible
 * @version $Revision: 1.2 $ $Date: 2005/10/14 09:44:31 $
 */
public class DateUtil {
	public final static String TAG = "DateUtil";
	// ~ Static fields/initializers
	// =============================================

	/**
	 * 日期格式
	 */
	private static String datePattern = "yyyy-MM-dd";
	/**
	 * 时间格式
	 */
	private static String timePattern = "HH:mm:ss";

	// ~ Methods
	// ================================================================

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "1900-01-01";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 *             转换异常
	 */
	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		// if (log.isDebugEnabled()) {
		// log.debug("converting '" + strDate + "' to date with mask '"
		// + aMask + "'");
		// }

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 *             转换异常
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "1900-01-01";

		if (aDate == null) {
			// log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		// String value = null;
		// try{
		// String hour = aDate.getHours()+"";
		// String[] dateValueArr = returnValue.split(" ");
		// String[] timeValueArr =dateValueArr[1].split(":");
		// value = dateValueArr[0]+" "+hour+":"+timeValueArr[1];
		// }catch(Exception e){
		// LogUtil.insertSysLogTOFile(e);
		// }
		// //如果日期为空，则给个默认值
		// if(value == null){
		// value = "1900-01-01 00:00";
		// }
		return returnValue;
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(datePattern, aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {
			// if (log.isDebugEnabled()) {
			// log.debug("converting date with pattern: " + datePattern);
			// }

			aDate = convertStringToDate(datePattern, strDate);
		} catch (ParseException pe) {
			// log.error("Could not convert '" + strDate
			// + "' to a date, throwing exception");
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	/**
	 * 如果date1>date2 返回 1 = 0 < -1
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return 比较结果
	 */
	public static int compareDate(Date date1, Date date2) {
		String d1 = getDateTime(datePattern, date1);
		String d2 = getDateTime(datePattern, date2);

		if (d1 == null && d2 != null)
			return -1;
		else if (d1 != null && d2 == null)
			return 1;
		else if (d1 == null && d2 == null)
			return 0;
		else
			return d1.compareTo(d2);
	}

	/**
	 * 得到星期几
	 * 
	 * @param weekday
	 *            int
	 * @return String
	 */
	public static String getWeekDay(int weekday) {
		String weekDayStr = "";
		switch (weekday) {
		case 2:
			weekDayStr = "星期一";
			break;
		case 3:
			weekDayStr = "星期二";
			break;
		case 4:
			weekDayStr = "星期三";
			break;
		case 5:
			weekDayStr = "星期四";
			break;
		case 6:
			weekDayStr = "星期五";
			break;
		case 7:
			weekDayStr = "星期六";
			break;
		default:
			weekDayStr = "星期日";
			break;
		}
		return weekDayStr;
	}

	/**
	 * 得到当前时间 pattern 2011年6月13日 星期一
	 * 
	 * @return String
	 */
	public static String getCurrentDate() {
		String datePattern = null;
		Calendar cal;
		try {
			cal = getToday();
			String weekdayStr = getWeekDay(cal.get(Calendar.DAY_OF_WEEK));
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			datePattern = year + "年" + month + "月" + day + "日" + " " + weekdayStr;
		} catch (ParseException e) {
			return null;
		}
		return datePattern;
	}

	/**
	 * 转换日期格式
	 * 
	 * @param datetime
	 *            2011-06-25 14:02
	 * @return fmtStr 2011年6月25日14时2分
	 */
	public static String formatDateTimeToCnPattern(String datetime) {
		String fmtStr = "";
		try {
			if (datetime != null) {
				String date = datetime.substring(0, 10);
				String time = datetime.substring(11);
				String[] dateArray = date.split("-");
				String[] timeArray = time.split(":");
				if (dateArray[0] != null) {
					fmtStr += dateArray[0] + "年";
				}
				if (dateArray[1] != null) {
					if (dateArray[1].startsWith("0")) {
						fmtStr += dateArray[1].substring(1) + "月";
					} else {
						fmtStr += dateArray[1] + "月";
					}
				}
				if (dateArray[2] != null) {
					if (dateArray[2].startsWith("0")) {
						fmtStr += dateArray[2].substring(1) + "日";
					} else {
						fmtStr += dateArray[2] + "日";
					}
				}
				if (timeArray[0] != null) {
					if (timeArray[0].startsWith("0")) {
						fmtStr += timeArray[0].substring(1) + "时";
					} else {
						fmtStr += timeArray[0] + "时";
					}
				}
				if (timeArray[1] != null) {
					if (timeArray[1].startsWith("0")) {
						fmtStr += timeArray[1].substring(1) + "分";
					} else {
						fmtStr += timeArray[1] + "分";
					}
				}
			}
		} catch (Exception e) {
			return null;
		}

		return fmtStr;
	}

	/**
	 * 转换日期格式
	 * 
	 * @param datetimeCn
	 *            2011年06月30日 14:02
	 * @return 日期 2011-06-30 14:02
	 */
	public static String formatDatetime(String datetimeCn) {
		String fmtStr = "";
		try {
			if (datetimeCn != null) {
				datetimeCn = datetimeCn.replace("年", "-");
				datetimeCn = datetimeCn.replace("月", "-");
				datetimeCn = datetimeCn.replace("日", "");
				fmtStr = datetimeCn;
			}
		} catch (Exception e) {
			return null;
		}
		return fmtStr;
	}

	/**
	 * 转换日期格式
	 * 
	 * @param datetime
	 *            2011-06-25 14:02
	 * @return fmtStr 2011年06月25日 14:02
	 */
	public static String formatDateTimeToCnPattern2(String datetime) {
		String fmtStr = "";
		try {
			if (datetime != null) {
				String date = datetime.substring(0, 10);
				String time = datetime.substring(11);
				String[] dateArray = date.split("-");
				if (dateArray[0] != null) {
					fmtStr += dateArray[0] + "年";
				}
				if (dateArray[1] != null) {
					fmtStr += dateArray[1] + "月";
				}
				if (dateArray[2] != null) {
					fmtStr += dateArray[2] + "日 ";
				}
				fmtStr += time;
			}
		} catch (Exception e) {
			return null;
		}
		return fmtStr;
	}

	/**
	 * date转String
	 * 
	 * @param aDate
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static final String formatDateToString(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "1900-01-01";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern + " " + timePattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

}
