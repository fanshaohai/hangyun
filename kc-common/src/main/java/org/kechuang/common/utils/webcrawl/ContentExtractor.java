package org.kechuang.common.utils.webcrawl;

import org.jsoup.nodes.Element;

/**
 * 正文抽取接口
 *  @author Winter Lau (javayou@gmail.com)
 */
public interface ContentExtractor {

    String content(Element body) ;

}
