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
        fc.write(ByteBuffer.wrap("Graceful degradation\n".getBytes()));
        fc.close();

        // 读数据
        fc = new FileInputStream(fileName).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        fc.read(buffer);
        buffer.flip();
        // 无法正确解码
        System.out.println(buffer.asCharBuffer());
        // 用系统默认字符集解码
        buffer.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("decoded using " + encoding + ": " + Charset.forName(encoding).decode(buffer));

        // 通过指定的字符集进行写入和读出
        fc = new FileOutputStream(fileName).getChannel();
        fc.write(ByteBuffer.wrap("block chain".getBytes("UTF-16BE")));
        fc.close();
        fc = new FileInputStream(fileName).getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());


    }
}
