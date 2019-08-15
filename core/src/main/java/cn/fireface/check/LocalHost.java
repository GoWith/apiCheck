package cn.fireface.check;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Create by maoyi on 2019/8/15
 * don't worry be happy!
 *
 * @author maoyi
 */
public class LocalHost {
    private final static Log log = LogFactory.getLog(LocalHost.class);

    private static NetworkInterface localNetworkInterface;
    private static String hostName;


    static {
        try {

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (!networkInterface.getName().equals("lo")) {
                    localNetworkInterface = networkInterface;
                    break;
                }
            }
        } catch (SocketException e) {
            log.error("init LocalHost error!", e);
        }

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("init hostname error!", e);
        }

    }

    /**
     * ȡ�����������ӿ�
     * @return
     */
    public static NetworkInterface getLocalNetworkInterface() {
        return localNetworkInterface;
    }

    /**
     * ȡ�����Ļ�������
     * @return
     */
    public static String getMachineName() {
        return hostName;
    }
}
