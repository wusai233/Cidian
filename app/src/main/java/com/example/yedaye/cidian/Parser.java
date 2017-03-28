package com.example.yedaye.cidian;

/**
 * 解析数据
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析数据过程
 */

public class Parser {

    public static final String TAG = Parser.class.getSimpleName();

    public static Result parserData(String re) {

        try {
            JSONObject rootObj = new JSONObject(re);
            if (rootObj.getInt("errorCode") == 0) {
                Result result1 = new Result();
                // 关键字
                if (rootObj.has("query")) {
                    String query = rootObj.getString("query");
                    result1.query = query;
                }

                if (rootObj.has("basic")) {
                    JSONObject basicObj = rootObj.getJSONObject("basic");
                    // 中文发音
                    if (basicObj.has("phonetic")) {
                        String phonetic = basicObj.getString("phonetic");
                        result1.phonetic = phonetic;
                    }
                    // 美式发音
                    if (basicObj.has("us-Phonetic")) {
                        String usPhonetic = basicObj.getString("us-Phonetic");
                        result1.usPhonetic = usPhonetic;
                    }
                    // 英式发音
                    if (basicObj.has("uk-Phonetic")) {
                        String ukPhonetic = basicObj.getString("uk-Phonetic");
                        result1.ukPhonetic = ukPhonetic;
                    }
                    // 基本释义
                    if (basicObj.has("explains")) {
                        JSONArray explainsArray = basicObj.getJSONArray("explains");
                        List<String> explains = new ArrayList<String>();
                        for (int i = 0; i < explainsArray.length(); i++) {
                            explains.add(explainsArray.getString(i));
                        }
                        result1.explains = explains;
                    }
                    // 网络释义
                    if (basicObj.has("web")) {
                        List<Web> webs = new ArrayList<Web>();
                        JSONArray webArray = basicObj.getJSONArray("web");
                        for (int i = 0; i < webArray.length(); i++) {
                            JSONObject webObj = webArray.getJSONObject(i);

                            Web web = new Web();

                            String key = webObj.getString("key");
                            web.key = key;

                            List<String> values = new ArrayList<String>();
                            JSONArray valueArray = webObj.getJSONArray("value");
                            for (int j = 0; j < valueArray.length(); j++) {
                                //  i 还是 j
                                values.add(valueArray.getString(i));
                            }
                            web.values = values;

                            webs.add(web);
                        }

                        result1.webs = webs;
                    }
                }
                return result1;
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException");
        }
        return null;
    }
}