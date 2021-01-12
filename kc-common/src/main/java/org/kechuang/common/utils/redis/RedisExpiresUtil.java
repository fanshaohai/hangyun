package org.kechuang.common.utils.redis;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * Redis过期时间计算类
 * @author Rambo
 * @date 2020年4月9日
 */
public class RedisExpiresUtil {

	/**
	 * 计算某时间距今的秒数，方便计算redis过期缓存时间，如果传入的时间小于当前时间，则返回1。
	 * 如果返回0 代表没有过期时间限制
	 * @author Rambo
	 * @date 2020年4月9日
	 * @param dateTimeStr
	 * @return
	 */
	public static String secondByNowForRedis(String dateTimeStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			Date now = new Date();
			Date expirationDate = sdf.parse(dateTimeStr);
			if(expirationDate.after(now)){
				Duration duration = Duration.between(now.toInstant(), expirationDate.toInstant());
				return duration.getSeconds() + "";
			}
		}catch (Exception e){}

		return "1";
	}
}
