package net.java.practice.nio.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author chenglx
 * @version V1.0
 * @date 2021/1/28 11:16
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2021/1/28 chenglx init
 */
public class EncodingTest {

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        System.out.println("args: " + Arrays.toString(args));
        System.out.println("property java.class.path: " + System.getProperty("java.class.path"));
        System.out.println();

        // 内存中的字符串编、解码
        System.out.println("测试String相关的编解码：");
        Charset defaultCharset = Charset.defaultCharset();
        System.out.println("Charset.defaultCharset().name(): " + defaultCharset.name());
        // 以默认方式获取编码
        byte[] bytes = "敏捷编程：Agile programming".getBytes();
        // 解码
        System.out.println("默认方式解码String的结果：" + new String(bytes));
        System.out.println("UTF-16BE解码String的结果：" + new String(bytes, "UTF-16BE"));
        System.out.println("UTF-8解码String的结果：" + new String(bytes, "UTF-8"));
        System.out.println();

        // 获取文件系统的默认编码
        System.out.println("测试文件的编解码：");
        String defaultFileEncoding = System.getProperty("file.encoding");
        System.out.println("系统默认的文件存储编码：file.encoding=" + defaultFileEncoding);
        String newFileEncoding = "UTF-8";
        String prevFileEncoding = System.setProperty("file.encoding", newFileEncoding);
        System.out.println("原先的 file.encoding=" + prevFileEncoding + "，更改后 file.encoding=" + System.getProperty("file.encoding"));
        System.out.println("更改 file.encoding 后的 Charset.defaultCharset()：" + Charset.defaultCharset());
        System.out.println();

        // 读写文件测试
        File file = new File("encoding_test.txt");
        // 通过FileWriter写文件
        System.out.println("按系统默认编码写入文件后，测试文件的读取：");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("世界，你好！hello world! ");
        writer.close();
        // 以默认编码读文件
        char[] content = new char[32];
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        reader.read(content);
        System.out.println("不指定编码时读文件: " + new String(content));
        reader = new InputStreamReader(new FileInputStream(file), defaultFileEncoding);
        reader.read(content);
        System.out.println("以系统原先默认的编码读文件: " + new String(content));
        reader = new InputStreamReader(new FileInputStream(file), newFileEncoding);
        reader.read(content);
        System.out.println("以系统新设定的编码读文件: " + new String(content));
        String anotherEncoding = "utf-32";
        reader = new InputStreamReader(new FileInputStream(file), anotherEncoding);
        reader.read(content);
        System.out.println("以 " + anotherEncoding + " 编码读文件: " + new String(content));
        BufferedReader br = new BufferedReader(new FileReader(file));
        System.out.println("通过 Reader 读文件: " + br.readLine());
        br.close();
        System.out.println();

        // 以指定字符集写文件
        System.out.println("按UTF-8编码写入文件后，测试文件的读取：");
        OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream(file), newFileEncoding);
        streamWriter.write("道可道, but not a plain dau");
        streamWriter.close();
        reader = new InputStreamReader(new FileInputStream(file));
        reader.read(content);
        System.out.println("不指定编码时读文件: " + new String(content));
        reader = new InputStreamReader(new FileInputStream(file), defaultFileEncoding);
        reader.read(content);
        System.out.println("以系统原先默认的编码读文件: " + new String(content));
        reader = new InputStreamReader(new FileInputStream(file), newFileEncoding);
        reader.read(content);
        System.out.println("按设定的编码读文件: " + new String(content));
        reader.close();
        System.out.println();


        // 通过指定的字符集进行写入和读出
        System.out.println("按UTF-16LE编码（与系统默认编码不同）写入文件后，测试文件的读取：");
        FileChannel fc = new FileOutputStream(file).getChannel();
        // 如果这里用非 UTF-16BE 编码，则后面将无法正确解码，因为 Java 默认使用 UTF-16BE 编解码
        String charset = "UTF-16";
        String testStr = "区块链， block chain";
        System.out.println("testStr:" + testStr + "|length=" + testStr.length());
        fc.write(ByteBuffer.wrap(testStr.getBytes(charset)));
        fc.close();
        fc = new FileInputStream(file).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.order(ByteOrder.LITTLE_ENDIAN); // UTF-16LE编码时，则打开该语句
        System.out.println("ByteBuffer order=" + buffer.order() + ", isDirect=" + buffer.isDirect());
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        // 用asCharBuffer()解码
        CharBuffer charBuffer = buffer.asCharBuffer();
        String str = charBuffer.toString();
        System.out.println("通过ByteBuffer.asCharBuffer读取: " + str);
        // 用String解码
        byte[] arr1 = buffer.array();
        String s1 = new String(buffer.array(), charset);
        System.out.println("String按传入byte[]相同的解码: " + s1 + "|length=" + s1.length());
        // 用String解码
        byte[] arr2 = Arrays.copyOf(arr1, testStr.length() * 2);
        String s2 = new String(arr2, charset);
        System.out.println("String按传入byte[]相同的解码（有截断）: " + s2 + "|length=" + s2.length());
        System.out.println();

        // UTF-16 与 UTF-16BE测试
        System.out.println("abc 按 UTF-16BE 进行编码: ");
        System.out.println("byte[]: " + Arrays.toString("abc".getBytes("UTF-16BE")));
        System.out.println("长度：" + "abc".getBytes("UTF-16BE").length);
        System.out.println("abc 按 UTF-16 进行编码: ");
        System.out.println("byte[](有BOM 0xFEFF): " + Arrays.toString("abc".getBytes("UTF-16")));
        System.out.println("===========" + ((int)("abc".getBytes("UTF-16")[0])));
        System.out.println("长度：" + "abc".getBytes("UTF-16").length);
        // UTF-16编解码转换
        System.out.println("UTF-16编码，UTF-16解码（会按默认的big endian）：" + new String("abc".getBytes("UTF-16"), "UTF-16"));
        System.out.println("UTF-16编码，UTF-16BE解码（会按默认的big endian）：" + new String("abc".getBytes("UTF-16"), "UTF-16BE"));
        System.out.println("UTF-16编码，UTF-16LE解码：" + new String("abc".getBytes("UTF-16"), "UTF-16LE"));
        // Java中UTF-16默认按big endian，所以能正确解码
        System.out.println("UTF-16BE编码，UTF-16解码：" + new String("abc".getBytes("UTF-16BE"), "UTF-16"));
        // Java中UTF-16默认按big endian，故这里的le不能正确解码
        System.out.println("UTF-16LE编码，UTF-16解码：" + new String("abc".getBytes("UTF-16LE"), "UTF-16"));
        System.out.println();

        // char[] 及 字符串String 中保存的是字符的 Unicode 编码
        char ch = '程';
        System.out.println(ch + ", " + (int)ch);
        ch = 'a';
        System.out.println(ch + ", " + (int)ch);
        System.out.println();
    }



}
