package net.java.practice.nio.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author chenglx
 * @version V1.0
 * @date 2021/1/27 15:38
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2021/1/27 chenglx init
 */
public class BufferToText {

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        String fileName = "BufferToText.data.txt";
        // 写数据
        FileChannel fc = new FileOutputStream(fileName).getChannel();
        String content = "graceful degradation";
        System.out.println("Write to file: " + content);
        // 字符串将默认采用
        fc.write(ByteBuffer.wrap(content.getBytes()));
        fc.close();

        // 读数据
        fc = new FileInputStream(fileName).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        fc.read(buffer);
        buffer.flip();
        // 直接获取对应的CharBuffer，无法正确解码
        System.out.println("buffer.asCharBuffer(): " + buffer.asCharBuffer().toString());
        // 用系统默认字符集解码
        buffer.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("decoded using " + encoding + ": " + Charset.forName(encoding).decode(buffer));

        // 通过指定的字符集进行写入和读出
        fc = new FileOutputStream(fileName).getChannel();
        // 如果这里用非 UTF-16BE 编码，则后面将无法正确解码，因为 Java 默认使用 UTF-16BE 编解码
        fc.write(ByteBuffer.wrap(content.getBytes("UTF-16BE")));
        fc.close();
        fc = new FileInputStream(fileName).getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        // Java 默认使用 UTF-16BE 解码，所以能正确解码
        System.out.println(buffer.asCharBuffer());

        // 将ByteBuffer转换成CharBuffer，再往里面写数据
        fc = new FileOutputStream(fileName).getChannel();
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
        // 此处默认会将字符串按UTF-16BE进行编码
        buffer.asCharBuffer().put(content);
        fc.write(buffer);
        fc.close();;
        // 读取
        fc = new FileInputStream(fileName).getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
    }
}
