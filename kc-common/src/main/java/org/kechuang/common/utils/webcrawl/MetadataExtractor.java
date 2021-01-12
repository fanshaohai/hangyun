package org.kechuang.common.utils.webcrawl;

import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 网页的元数据抽取
 */
public class MetadataExtractor {

	private static final String WHITESPACE = "[ \r\t\n]+";

	private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取标题
	 */
	static String title(Document doc) {
		try {
			String title= new HeuristicString(null)
				.or(innerTrim(doc.select("head title").text()))//新浪、凤凰网
				//.or(innerTrim(doc.select("meta[property=og:title]").attr("content")))//新浪、凤凰网
				.toString();
			return cleanTitle(title);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 关键词
	 */
	static String keywords(Document doc) {
		String content = innerTrim(doc.select("meta[name=keywords]").attr("content"));//新浪、凤凰网
		if (content.startsWith("[") && content.endsWith("]"))
			content = content.substring(1, content.length() - 1);
		String[] split = content.split("[(\\s*,\\s*)( +)]");
		if (split.length > 1 || (split.length > 0 && !"".equals(split[0])))
			return String.join(",", split);
		return "";
	}

	/**
	 * 作者
	 */
	static String author(Document doc) {
		try {
			return new HeuristicString(null)
				.or(innerTrim(doc.select("meta[property=article:author]").attr("content")))//新浪
				.or(innerTrim(doc.select("meta[name=og:category]").attr("content")))//凤凰网
				.or(textRelp(innerTrim(doc.select("meta[name=source]").attr("content"))))//人民网
				.toString();
		} catch (HeuristicString.CandidateFound candidateFound) {
			return cleanTitle(candidateFound.candidate);
		}
	}

	private static String[] DATE_FORMATS = {"yyyy-MM-dd'T'hh:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};

	/**
	 * 发布时间
	 */
	static String date(Document doc) {
		Date date = parseDate(doc);
		try {
			return new SimpleDateFormat(dateFormat).format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	static Date parseDate(Document doc) {
		String sdate;
		try {
			sdate = new HeuristicString(null)
				.or(doc.select("meta[property=article:published_time]").attr("content"))//新浪
				.or(doc.select("meta[name=og:time]").attr("content"))//凤凰网
				.or(doc.select("meta[name=publishdate]").attr("content"))//人民网
				.toString();
		} catch (HeuristicString.CandidateFound candidateFound) {
			sdate = candidateFound.candidate;
		}
		Date date = null;
		while (date == null) {
			for (String fmt : DATE_FORMATS) {
				try {
					date = new SimpleDateFormat(fmt).parse(sdate);
					break;
				} catch (ParseException e) {
					date = null;
				}
			}
			break;
		}
		return date;
	}


	/**
	 * remove more than two spaces or newlines
	 */
	public static String innerTrim(String str) {
		return str.replaceAll(WHITESPACE, " ").trim();
	}

	public static String textRelp(String str) {
		return str.replaceAll("[来源：]", "");
	}

	public static String cleanTitle(String title) {
		title = title.trim();
		String[] datas = title.split(" ");
		return datas[0];
        /*StringBuilder res = new StringBuilder();
        int index = title.lastIndexOf("|");
        if (index > 0 && title.length() / 2 < index)
            title = title.substring(0, index + 1);

        int counter = 0;
        String[] strs = title.split("\\|");
        for (String part : strs) {
            if (counter == strs.length - 1 && res.length() > part.length()) {
                continue;
            }
            if (counter > 0) {
                res.append("|");
            }
            res.append(part);
            counter++;
        }
        return innerTrim(res.toString());*/
	}
}
