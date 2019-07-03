package com.heeexy.example.util;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.util.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页工具
 * @author jie
 * @date 2018-12-10
 */
public class PageUtil extends cn.hutool.core.util.PageUtil {

    /**
     * List 分页
     * @param page
     * @param size
     * @param list
     * @return
     */
    public static List toPage(int page, int size , List list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;

        if(fromIndex > list.size()){
            return new ArrayList();
        } else if(toIndex >= list.size()) {
            return list.subList(fromIndex,list.size());
        } else {
            return list.subList(fromIndex,toIndex);
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     * @param page
     * @return
     */
    public static Map toPage(Page page) {
        Map map = successJson();
        Map info = new HashMap<>();
		info.put("list", page.getContent());
		info.put("totalCount", page.getTotalElements());
		info.put("totalPage", page.getTotalPages());
		map.put("info", info);
        return map;
    }

    /**
     * @param object
     * @param totalElements
     * @return
     */
    public static Map toPage(Object object, Object totalElements) {
        Map map = new HashMap();

        map.put("content",object);
        map.put("totalElements",totalElements);

        return map;
    }
    
	/**
	 * 返回一个info为空对象的成功消息的json
	 */
	public static JSONObject successJson() {
		return successJson(new JSONObject());
	}

	/**
	 * 返回一个返回码为100的json
	 */
	public static JSONObject successJson(Object info) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("code", Constants.SUCCESS_CODE);
		resultJson.put("msg", Constants.SUCCESS_MSG);
		resultJson.put("info", info);
		return resultJson;
	}


}
