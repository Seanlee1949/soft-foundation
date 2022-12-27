package com.example.boot.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lishuai
 * @since 2022/11/26
 */
public class FileUtils {
    public static void appendStringToFile(String filePath, String content) {
        FileWriter writer;
        try {
            writer = new FileWriter(filePath, true);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFileByLine(String filePath) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
//————————————————
//        版权声明：本文为CSDN博主「研程序笔记」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/qq_44779847/article/details/108146989
//————————————————
//        版权声明：本文为CSDN博主「研程序笔记」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/qq_44779847/article/details/108146989
    }
}
