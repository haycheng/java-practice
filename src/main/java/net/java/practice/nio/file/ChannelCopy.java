package net.java.practice.nio.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author chenglx
 * @version V1.0
 * @date 2021/1/27 15:08
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2021/1/27 chenglx init
 */
public class ChannelCopy {

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("arguments: sourcefile destfile");
            System.exit(1);
        }
        System.out.println(new File("./test.txt").getAbsolutePath());

        FileChannel in = new FileInputStream(args[0]).getChannel();
        FileChannel out = new FileOutputStream((args[1])).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        while (in.read(buffer) != -1) {
            // 为了在下面的write方法从buffer读取数据
            buffer.flip();
            out.write(buffer);
            // 为了下一次将数据写入buffer
            buffer.clear();
        }
    }
}
