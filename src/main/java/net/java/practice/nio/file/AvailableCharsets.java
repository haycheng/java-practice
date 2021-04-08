package net.java.practice.nio.file;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * 打印当前JVM中可用的字符集，及其别名
 * @author chenglx
 * @version V1.0
 * @date 2021/1/28 10:29
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2021/1/28 chenglx init
 */
public class AvailableCharsets {

    public static void main(String[] args) {
        SortedMap<String, Charset> charsetMap = Charset.availableCharsets();
        Iterator<String> iterator = charsetMap.keySet().iterator();
        // 打印所有可用的字符集名称，及其别名
        while (iterator.hasNext()) {
            String name = iterator.next();
            Iterator<String> it = charsetMap.get(name).aliases().iterator();
            StringBuilder builder = new StringBuilder(name);
            builder.append(": ");
            while (it.hasNext()) {
                builder.append(it.next()).append(", ");
            }
            System.out.println(builder.toString().substring(0, builder.length() - 2));
        }
    }
}
