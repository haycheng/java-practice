package net.java.practice.test;


/**
 * @author chenglx
 * @version V1.0
 * @date 2021/5/10 9:31
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2021/5/10 chenglx init
 */
public class SwapTest {

    public static void main(String[] args) {
        String original = "a b c d e f g h i j k l m n";
        String[] words = original.split(" ");
        System.out.println("Original:       " + getPrintStr(words));
//        transformBySwap(words);
//        System.out.println("Transformed:    " + getPrintStr(words));
        restoreBySwap(words);
        String restored = getPrintStr(words);
        System.out.println("Restored:       " + restored);
//        System.out.println("The original equals to the restored: " + original.equals(restored));
    }

    private static String getPrintStr(String[] arr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; ++i) {
            builder.append(arr[i]).append(" ");
        }
        String result = builder.toString();
        return result.substring(0, result.length() - 1);
    }

    private static void transformBySwap(String[] arr) {
        swap(arr, 0, 3);
        swap(arr, 1, 2);
        swap(arr, 2, 4);
        swap(arr, 5, 9);
        swap(arr, 6, 10);
        swap(arr, 7, 11);
        swap(arr, 8, 0);
    }

    private static void restoreBySwap(String[] arr) {
        swap(arr, 8, 0);
        swap(arr, 7, 11);
        swap(arr, 6, 10);
        swap(arr, 5, 9);
        swap(arr, 2, 4);
        swap(arr, 1, 2);
        swap(arr, 0, 3);
    }

    private static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
