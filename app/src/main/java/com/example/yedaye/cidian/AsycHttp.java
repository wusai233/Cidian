package com.example.yedaye.cidian;


import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 网络
 */

public class AsycHttp {
    public static final String TAG = AsycHttp.class.getSimpleName();

    private static AsycHttp mAsycHttp;

    private AsycHttp() {

    }

    public static AsycHttp getInstnce() {
        if (null == mAsycHttp) {
            synchronized (AsycHttp.class) {
                if (null == mAsycHttp) {
                    mAsycHttp = new AsycHttp();
                }
            }
        }
        return mAsycHttp;
    }

    /**
     * GIT 请求
     *
     * @param path  路径
     * @param input 输入关键字
     */
    public void requestForGet(String path, String input, onResponseLister monResponseLister) {
        if (null != input || "".equals(input)) {
            StringBuffer buf = new StringBuffer();
            buf.append(path).append(input);
            HttpGet request = new HttpGet(buf.toString());
            // 执行请求
            new DictAsycTak(monResponseLister).execute(request);
        }
    }

    class DictAsycTak extends AsyncTask<HttpUriRequest, Void, String> {
        int resultCode;
        onResponseLister monResponseLister;

        public DictAsycTak(onResponseLister monResponseLister) {
            this.monResponseLister = monResponseLister;
        }

        @Override
        protected String doInBackground(HttpUriRequest... params) {
            // 执行网路请求
            HttpClient client = new DefaultHttpClient();
            try {
                HttpResponse response = client.execute(params[0]);
                resultCode = response.getStatusLine().getStatusCode();
                if (resultCode == HttpStatus.SC_OK) {
                    //读取响应的数据
                    HttpEntity entity = response.getEntity();
                    return EntityUtils.toString(entity);
                } else {
                    params[0].abort();
                }
            } catch (ClientProtocolException e) {
                Log.e(TAG, "ClientProtocolException");
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (null != result) {
                // 成功
                Log.e(TAG, "结果:------------------------------" + result);
                monResponseLister.onSuccess(result);
            } else {
                // 失败
                Log.e(TAG, "失败:" + resultCode);
                monResponseLister.onFauled("error---->" + resultCode);
            }
        }
    }

    //    //回调接口
    public interface onResponseLister {
        void onSuccess(String result);

        void onFauled(String error);
    }
}

