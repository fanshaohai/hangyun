package org.kechuang.common.utils.sensitive;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 敏感词过滤器
 * @author Rambo
 * @date 2020年4月2日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SensitiveWordFilter {
    /**
     * 敏感词库
     */
    @SuppressWarnings("rawtypes")
    private static Map sensitiveWordMap;
	/**
	 * 替换词库
	 */
	@SuppressWarnings("rawtypes")
	private static Map replaceWordMap;
    /**
     * 最小匹配规则
     */
    public static int minMatchType = 1;
    /**
     * 最大匹配规则
     */
    public static int maxMatchType = 2;
    /**
     * 私有静态变量
     */
    private static volatile SensitiveWordFilter instance;


	public static void setSensitiveWordMap(Map sensitiveWordMap) {
		SensitiveWordFilter.sensitiveWordMap = sensitiveWordMap;
	}

	public static void setReplaceWordMap(Map replaceWordMap) {
		SensitiveWordFilter.replaceWordMap = replaceWordMap;
	}

	/**
     * 获取实例（双重检测锁）
	 * @param sensitiveWordMap 敏感词库
	 * @param replaceWordMap 替换词库
     * @return SensitivewordFilter
     */
    public static SensitiveWordFilter getInstance(Map sensitiveWordMap, Map replaceWordMap) {
        if (instance == null) {
            synchronized (SensitiveWordFilter.class) {
                if (instance == null) {
                    instance = new SensitiveWordFilter();
                }
            }
        }
		setSensitiveWordMap(sensitiveWordMap);
        setReplaceWordMap(replaceWordMap);
        return instance;
    }

    /**
     * 获取文字中的敏感词
     * @param txt       文字
     * @param matchType 匹配规则。1：最小匹配规则，2：最大匹配规则
     * @return Set
     */
    public Set<String> getSensitiveWord(String txt, int matchType) {
        return getSensitiveWordSets(txt, 0, matchType);
    }

	/**
	 * 获取文字中替换后的文本
	 * @param txt       文字
	 * @param matchType 匹配规则。1：最小匹配规则，2：最大匹配规则
	 * @return String
	 */
	public String getReplacedWord(String txt, int matchType) {
		return replaceSensitiveWord(txt, 0, matchType);
	}

    /**
     * 替换敏感字字符 默认替换为''
     * @param txt         文本
     * @param matchType   匹配规则
     * @return String
     */
    private String replaceSensitiveWord(String txt, int beginIndex,  int matchType) {
    	//从新组装String
		String resultTxt = "";
		for (int n = beginIndex; n < txt.length(); n++) {
			// 判断是否包含敏感字符
			int length = judgeSensitiveWithIndex(txt, n, matchType);
			if (length > 0) {
				// 存在,加入list中
				String sensitiveStr = txt.substring(n, n + length);
				// 替换字符
				String replaceString = replaceWordMap.get(sensitiveStr) == null ?
					"" : replaceWordMap.get(sensitiveStr).toString();
				resultTxt = resultTxt + replaceString;

				// 减1的原因，是因为for会自增
				n = n + length - 1;
			}else{
				resultTxt = resultTxt + txt.charAt(n);
			}
		}

        return resultTxt;
    }

	/**
	 * 获取字符串中存在的敏感词列表
	 * @param txt 文本
	 * @param beginIndex 开始位置
	 * @param matchType 匹配规则
	 * @return
	 */
    private Set<String> getSensitiveWordSets(String txt, int beginIndex, int matchType) {
        Set<String> sensitiveWordSets = new HashSet<>();
        for (int n = beginIndex; n < txt.length(); n++) {
            // 判断是否包含敏感字符
            int length = judgeSensitiveWithIndex(txt, n, matchType);
            if (length > 0) {
                // 存在,加入list中
                sensitiveWordSets.add(txt.substring(n, n + length));
                // 减1的原因，是因为for会自增
                n = n + length - 1;
            }
        }
        return sensitiveWordSets;
    }

    /**
     * 根据指定位置是否是敏感词的开始
     * @param txt        文本
     * @param beginIndex 开始位置
     * @param matchType  匹配规则
     * @return int
     */
    private int judgeSensitiveWithIndex(String txt, int beginIndex, int matchType) {
        // 匹配标识数默认为0
		boolean  flag = false;
        int matchFlag = 0;
        char word;
        Map nowMap = sensitiveWordMap;
        int swap = 0;
        for (int i = beginIndex; i < txt.length(); i++) {
	        try {
		        word = txt.charAt(i);
		        // 获取指定key
		        nowMap = (Map) nowMap.get(word);
	        } catch (Exception e) {
		        e.printStackTrace();
		        break;
	        }

            // 存在，则判断是否为最后一个
            if (nowMap != null) {
                // 找到相应key，匹配标识+1
                matchFlag++;
                if ("1".equals(nowMap.get("isEnd"))) {
					flag = true;       //结束标志位为true
					swap = matchFlag;
					if(SensitiveWordFilter.minMatchType == matchType){    //最小规则，直接返回,最大规则还需继续查找
						break;
					}
                }
            } else {
                // 不存在，直接返回
                break;
            }
        }
        // 长度必须大于等于1，为词
        if (matchFlag < 2  && !flag) {
            matchFlag = 0;
        }

        //e.g. 例如123和 12345敏感词  txt为1234 最大规则会匹配成1234 应为匹配123
        if(matchFlag != swap){
			matchFlag = swap;
		}

        return matchFlag;
    }

}
