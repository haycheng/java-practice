package net.java.practice.nio.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author chenglx
 * @version V1.0
 * @date 2021/1/27 14:46
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2021/1/27 chenglx init
 */
public class GetChannel {

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {

        String fileName = "data.txt";
        // init a file
        FileChannel fc = new FileOutputStream(fileName).getChannel();
        fc.write(ByteBuffer.wrap("Graceful degradation\n".getBytes()));
        fc.close();

        // append to the end of the file
        fc = new RandomAccessFile(fileName, "rw").getChannel();
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("is necessary sometimes.\n".getBytes()));
        fc.close();

        // read the file
        fc = new FileInputStream(fileName).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        System.out.println("Before reading: buffer.capacity=" + buffer.capacity() + ", buffer.limit=" + buffer.limit() + ", buffer.position=" + buffer.position());
        fc.read(buffer);
        System.out.println("After reading: buffer.capacity=" + buffer.capacity() + ", buffer.limit=" + buffer.limit() + ", buffer.position=" + buffer.position());
        // 为了让后面从buffer中读取数据做准备
        buffer.flip();
        System.out.println("After flipping: buffer.capacity=" + buffer.capacity() + ", buffer.limit=" + buffer.limit() + ", buffer.position=" + buffer.position());

        while (buffer.hasRemaining()) {
            System.out.print((char)buffer.get());
        }
    }
}
