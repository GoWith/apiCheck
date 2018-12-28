package cn.fireface.check.filter.strategy;

import java.util.LinkedHashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;

/**
 *
 * @author fireface
 * @date 2018/10/30
 * don't worry , be happy
 */
public interface FilterStrategy {
    /** 策略执行
     * @param parameters 入参
     * @return 出参
     */
    String execute(Map<String, String[]> parameters);

}
