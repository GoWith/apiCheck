package cn.fireface.check.filter.strategy;

import com.alibaba.fastjson.JSON;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Create by maoyi on 2018/12/28
 * don't worry be happy!
 *
 * @author maoyi
 * @date 2018-12-28 10:02
 */
public abstract class AbstractFilterStrategy implements FilterStrategy{
    protected static final int RESULT_CODE_SUCCESS = 1;
    protected static final int RESULT_CODE_ERROR = 2;
    protected static String returnJSONResult(int resultCode, Object content) {
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("ResultCode", resultCode);
        dataMap.put("Content", content);
        return JSON.toJSONString(dataMap);
    }
}
