package cn.fireface.check.filter;

import cn.fireface.check.LocalHost;
import cn.fireface.check.Utils;
import cn.fireface.check.beans.BeanMap;
import cn.fireface.check.filter.strategy.FilterStrategy;
import cn.fireface.check.filter.strategy.factory.FilterStrategyFactory;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Create by fireface on 2018/11/19
 * don't worry be happy!
 *
 * @author fireface
 */
public class WebFilter implements Filter {
    private static final String PATH_SPLITER = "/";
    private static final String DOT = ".";
    private static final String JSON_TAIL = ".json";
    private static final String HTML_TAIL = ".html";
    private static final String JS_TAIL = ".js";
    private static final String CSS_TAIL = ".css";
    private static final String JPG_TAIL = ".jpg";
    private static final String SVG_TAIL = ".svg";
    private static final String RESOURCE_PATH = "/check";

    private static boolean accept = true;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("WEB-INF/api/check/config.properties");
            if (resourceAsStream!=null) {
                Properties prop = new Properties();
                prop.load(resourceAsStream);
                System.out.println(JSON.toJSONString(prop));
                String ips = prop.getProperty("api.check.pass.ip");
                System.out.println(ips);
                System.out.println(LocalHost.getHostAddress());
                accept = ips == null || (!StringUtils.isEmpty(LocalHost.getHostAddress()) && ips.contains(LocalHost.getHostAddress()));
            }else {
                accept = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (!accept) {
            return;
        }

        String encoding = "UTF-8";
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);

        BeanMap.load(servletRequest.getServletContext());
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            request = (HttpServletRequest) servletRequest;
            response = (HttpServletResponse) servletResponse;
        }
        /*
          http://x.y.com/a/b/c/....
          uri /a/b/c/.....
         */
        String requestURI = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();

        if (requestURI.contains(JSON_TAIL)) {
            FilterStrategy strategy = FilterStrategyFactory.produce(requestURI);
            String execute = null;
            if (null != strategy) {
                execute = strategy.execute(parameterMap);
            }
            response.getWriter().print(execute);
            return;
        }

        returnResourceFile(requestURI.substring(requestURI.lastIndexOf(PATH_SPLITER)), requestURI, (HttpServletResponse) servletResponse);
    }

    private String getFilePath(String filePath) {
        if (!filePath.startsWith(RESOURCE_PATH)) {
            return RESOURCE_PATH + filePath;
        }
        return filePath;
    }

    private void returnResourceFile(String fileName, String uri, HttpServletResponse response) throws IOException {

//        String filePath = getFilePath(uri);
        String filePath = uri;

        if (filePath.endsWith(HTML_TAIL)) {
            response.setContentType("text/html; charset=utf-8");
        }
        if (fileName.endsWith(JPG_TAIL)) {
            byte[] bytes = Utils.readByteArrayFromResource(filePath);
            if (bytes != null) {
                response.getOutputStream().write(bytes);
            }

            return;
        }

        String text = Utils.readFromResource(filePath);
        if (text == null) {
            response.sendRedirect(uri + "/callChain.html");
            return;
        }
        if (fileName.endsWith(SVG_TAIL)) {
            response.setContentType("image/svg+xml");
        }
        if (fileName.endsWith(CSS_TAIL)) {
            response.setContentType("text/css;charset=utf-8");
        } else if (fileName.endsWith(JS_TAIL)) {
            response.setContentType("text/javascript;charset=utf-8");
        }
        response.getWriter().write(text);
    }

    @Override
    public void destroy() {

    }
}
