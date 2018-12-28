package cn.fireface.check.filter.strategy;

import cn.fireface.check.beans.BeanMap;
import cn.fireface.check.filter.strategy.AbstractFilterStrategy;
import cn.fireface.check.filter.strategy.FilterStrategy;

import java.util.Arrays;
import java.util.Map;

/**
 * Create by maoyi on 2018/12/28
 * don't worry be happy!
 *
 * @author maoyi
 * @date 2018-12-28 09:57
 */
public class InitFilterStrategy extends AbstractFilterStrategy {
    @Override
    public String execute(Map<String, String[]> parameters) {
        String[] names = BeanMap.getBeanNames();
        Arrays.sort(names);
        return returnJSONResult(RESULT_CODE_SUCCESS, names);
    }
}
