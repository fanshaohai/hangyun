package org.kechuang.common.utils.webcrawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * <p>HtmlSucker 的入口</p>
 * <p>使用方法:</p>
 * <code>
 * HtmlSucker.select(HtmlSucker.MAX_TEXT_EXTRACTOR).parse(html);
 * </code>
 *
 * @author Winter Lau (javayou@gmail.com)
 */
public class HtmlSucker {

	public final static byte MAX_TEXT_EXTRACTOR = 0x01; //最大文本长度抽取
	public final static byte TEXT_DENSITY_EXTRACTOR = 0x02; //文本密度算法抽取

	/**
	 * 选择不同的算法
	 *
	 * @param extrator
	 * @return
	 */
	public final static HtmlSucker select(byte extrator) {
		ContentExtractor extractor = null;
		switch (extrator) {
			case MAX_TEXT_EXTRACTOR:
				extractor = new MaxTextContentExtractor();
				break;
			case TEXT_DENSITY_EXTRACTOR:
				extractor = new TextDensityExtractor();
				break;
			default:
				throw new IllegalArgumentException("Illegal Extractor defined: value = " + extractor);
		}
		return new HtmlSucker(extractor);
	}

	private ContentExtractor extractor;

	private HtmlSucker(ContentExtractor extractor) {
		this.extractor = extractor;
	}

	/**
	 * 根据 URL 来解析文章信息
	 *
	 * @param url
	 * @return
	 */
	public HashMap<String, Object> parse(String url, int timeMillis) throws IOException {
		return parse(Jsoup.parse(new URL(url), timeMillis));
	}

	/**
	 * 根据 html 内容来解析文章信息
	 *
	 * @param html
	 * @return
	 */
	public HashMap<String, Object> parse(String html) {
		return parse(Jsoup.parse(html));
	}

	private HashMap<String, Object> parse(Document doc) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("title", MetadataExtractor.title(doc));
		result.put("keywords", MetadataExtractor.keywords(doc));
		result.put("author", MetadataExtractor.author(doc));
		result.put("create_time", MetadataExtractor.date(doc));
		String content = extractor.content(doc.body());
		try {
			content = URLEncoder.encode(content, "UTF-8");
			content = content.replace("+", "%20");
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("content", content);
		return result;
	}

}
