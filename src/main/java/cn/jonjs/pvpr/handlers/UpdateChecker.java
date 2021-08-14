package cn.jonjs.pvpr.handlers;

import cn.jonjs.pvpr.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateChecker {

    static String versionChecker = "https://gitee.com/jonjs2333/pvprewards/raw/master/VersionCheck.txt";

    public static String getWebVersion() {
        return getTextFromURL(versionChecker);
    }

    public static String getThisVersion() {
        return Main.getInst().getDescription().getVersion();
    }

    public static boolean hasNewerVersion() {
        if( ! getThisVersion().equalsIgnoreCase(getWebVersion())) {
            return true;
        } else {
            return false;
        }
    }

    public static String getTextFromURL(String link) {
        try {
            //建立连接
            URL url = new URL(link);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            //获取输入流
            InputStream input = httpUrlConn.getInputStream();
            //将字节输入流转换为字符输入流
            InputStreamReader read = new InputStreamReader(input, "utf-8");
            //为字符输入流添加缓冲
            BufferedReader br = new BufferedReader(read);
            // 读取返回结果
            String data = br.readLine();
            br.close();
            read.close();
            input.close();
            httpUrlConn.disconnect();
            return data;
            // 释放资源
        } catch (MalformedURLException e) {
            return "地址错误";
        } catch (IOException e) {
            return "I/O流错误";
        }
    }

}
