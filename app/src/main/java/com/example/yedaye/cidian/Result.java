package com.example.yedaye.cidian;

import java.util.List;

/**
 * Created by Yedaye on 2017/3/27.
 */

public class Result {
    public String query; // 关键词
    public String translation;// 有道翻译
    public String phonetic;// 中文音标
    public String usPhonetic;// 美式发音
    public String ukPhonetic;// 英式发音

    List<String> explains; // 基本释义
    List<Web> webs; // 网络释义

    @Override
    public String toString() {

        return "关键字：" + checkIsEmoty(query) +
                "\n" + checkIsEmoty(phonetic) +
                "\n有道词典：" + checkIsEmoty(translation) +
                "\n基本释义：" + getExplains() +
                "\n网络释义" + getWeb();
    }

    public String checkIsEmoty(String word) {
        return word == null ? "无" : word;
    }


    // 基本释义
    public String getExplains() {
        StringBuffer buf = new StringBuffer();
        if (null != explains && explains.size() > 0) {
            for (int i = 0; i < explains.size(); i++) {
                buf.append(explains.get(i)).append(",");
            }
            return buf.deleteCharAt(buf.length() - 1).toString();
        }
        return "无";
    }

    // 基本释义
    public String getWeb() {
        StringBuffer buf = new StringBuffer();
        if (null != webs && webs.size() > 0) {
            for (int i = 0; i < webs.size(); i++) {
                buf.append(webs.get(i).toString()).append("；");
            }
            return buf.deleteCharAt(buf.length() - 1).toString();
        }
        return "无";
    }
}
