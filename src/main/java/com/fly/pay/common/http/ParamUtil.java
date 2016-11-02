package com.fly.pay.common.http;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 参数处理类
 * 
 * @author panxf
 * @version V1.0 创建时间：2012-7-13
 */
public class ParamUtil {
    public static final Logger log = Logger.getLogger(ParamUtil.class);
    /**
     * 获取得到排序好的查询字符串
     * 
     * @param params
     *            请求参数
     * @param isContainSignature
     *            是否包含signature参数
     * @return
     */
    public static String getSortQueryString(List<ParamEntry> params) throws Exception {
        Collections.sort(params, new Comparator<ParamEntry>() {
            @Override
            public int compare(ParamEntry object1, ParamEntry object2) {
                return object1.getKey().compareTo(object2.getKey());
            }
        });
        StringBuffer sb = new StringBuffer();
        for (ParamEntry pe : params) {
            sb.append(pe.getKey()).append("=").append(pe.getValue()).append("&");
        }

        String text = sb.toString();
        if (text.endsWith("&")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    /**
     * 获取得到排序好的查询字符串
     * 
     * @param params
     *            请求参数
     * @param isContainSignature
     *            是否包含signature参数
     * @return
     */
    protected String getSortQueryString(List<ParamEntry> params, boolean isContainSignature) {
        Collections.sort(params, new Comparator<ParamEntry>() {
            @Override
            public int compare(ParamEntry object1, ParamEntry object2) {
                return object1.getKey().compareTo(object2.getKey());
            }
        });
        StringBuffer sb = new StringBuffer();
        for (ParamEntry pe : params) {
            // if (!isContainSignature &&
            // ParameterName.signature.toString().equals(pe.getKey())) {
            //
            // } else {
            // sb.append(pe.getKey()).append("=").append(pe.getValue()).append("&");
            // }
        }

        String text = sb.toString();
        if (text.endsWith("&")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    public static String convertToHttpRequestString(List<ParamEntry> postParams) throws UnsupportedEncodingException {
        String result = "";
        if (postParams.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (ParamEntry pp : postParams) {
                sb.append(pp.getKey()).append("=").append(URLEncoder.encode(pp.getValue(), "UTF-8")).append("&");
            }
            result = sb.toString();
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
