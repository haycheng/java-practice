package net.java.practice.nio.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author chenglx
 * @version V1.0
 * @date 2021/1/27 15:26
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2021/1/27 chenglx init
 */
public class TransferTo {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("arguments: sourcefile destfile");
            System.exit(1);
        }

        FileChannel in = new FileInputStream(args[0]).getChannel();
        FileChannel out = new FileOutputStream((args[1])).getChannel();
        in.transferTo(0, in.size(), out);
//        out.transferFrom(in, 0, in.size());
    }
}
