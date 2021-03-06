/*
 * Copyright (C) 2015 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.kechuang.common.utils.webcrawl;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于文本密度算法的正文抽取
 * 代码截取自 <a href="https://gitee.com/webcollector/WebCollector/blob/master/WebCollector/src/main/java/cn/edu/hfut/dmic/contentextractor/ContentExtractor.java" target="_blank">WebCollector</a>
 */
public class TextDensityExtractor implements ContentExtractor {

    @Override
    public String content(Element body) {
        return getContentElement(body).html();
    }

    private static void clean(Element doc) {
        doc.select("script,noscript,style,iframe,br").remove();
    }

    /**
     * 节点的文本密度计算
     * @param node
     * @return
     */
    private static CountInfo computeNodeInfo(Node node, Map<Element, CountInfo> infoMap) {

        if (node instanceof Element) {
            Element tag = (Element) node;

            CountInfo countInfo = new CountInfo();
            for (Node childNode : tag.childNodes()) {
                CountInfo childCountInfo = computeNodeInfo(childNode, infoMap);
                countInfo.textCount += childCountInfo.textCount;
                countInfo.linkTextCount += childCountInfo.linkTextCount;
                countInfo.tagCount += childCountInfo.tagCount;
                countInfo.linkTagCount += childCountInfo.linkTagCount;
                countInfo.leafList.addAll(childCountInfo.leafList);
                countInfo.densitySum += childCountInfo.density;
                countInfo.pCount += childCountInfo.pCount;
            }
            countInfo.tagCount++;
            String tagName = tag.tagName();
            if (tagName.equals("a")) {
                countInfo.linkTextCount = countInfo.textCount;
                countInfo.linkTagCount++;
            } else if (tagName.equals("p")) {
                countInfo.pCount++;
            }

            int pureLen = countInfo.textCount - countInfo.linkTextCount;
            int len = countInfo.tagCount - countInfo.linkTagCount;
            if (pureLen == 0 || len == 0) {
                countInfo.density = 0;
            } else {
                countInfo.density = (pureLen + 0.0) / len;
            }

            infoMap.put(tag, countInfo);

            return countInfo;
        } else if (node instanceof TextNode) {
            TextNode tn = (TextNode) node;
            CountInfo countInfo = new CountInfo();
            String text = tn.text();
            int len = text.length();
            countInfo.textCount = len;
            countInfo.leafList.add(len);
            return countInfo;
        } else {
            return new CountInfo();
        }
    }

    private double computeScore(Element tag, Map<Element, CountInfo> infoMap) {
        CountInfo countInfo = infoMap.get(tag);
        double var = Math.sqrt(computeVar(countInfo.leafList) + 1);
        double score = Math.log(var) * countInfo.densitySum * Math.log(countInfo.textCount - countInfo.linkTextCount + 1) * Math.log10(countInfo.pCount + 2);
        return score;
    }

    private static double computeVar(ArrayList<Integer> data) {
        if (data.size() == 0) {
            return 0;
        }
        if (data.size() == 1) {
            return data.get(0) / 2;
        }
        double sum = 0;
        for (Integer i : data) {
            sum += i;
        }
        double ave = sum / data.size();
        sum = 0;
        for (Integer i : data) {
            sum += (i - ave) * (i - ave);
        }
        sum = sum / data.size();
        return sum;
    }

    private Element getContentElement(Element body) {
        Map<Element, CountInfo> infoMap = new HashMap<>();
        clean(body);
        computeNodeInfo(body, infoMap);
        double maxScore = 0;
        Element content = null;
        for (Map.Entry<Element, CountInfo> entry : infoMap.entrySet()) {
            Element tag = entry.getKey();
            if (tag.tagName().equals("a") || tag == body) {
                continue;
            }
            double score = computeScore(tag, infoMap);
            if (score > maxScore) {
                maxScore = score;
                content = tag;
            }
        }
        return content;
    }

    static class CountInfo {
        int textCount = 0;
        int linkTextCount = 0;
        int tagCount = 0;
        int linkTagCount = 0;
        double density = 0;
        double densitySum = 0;
        double score = 0;
        int pCount = 0;
        ArrayList<Integer> leafList = new ArrayList<Integer>();

    }

}
