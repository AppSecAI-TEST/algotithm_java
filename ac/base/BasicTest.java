package base;

import org.junit.Test;

/**
 * description:
 *
 * @author liyazhou
 * @since 2017-07-14 8:20
 */
public class BasicTest {


    /**
     * 最大公约数 greatest common divisor, gcd
     * @param m 较大整数
     * @param n 较小整数
     * @return 最大公约数
     */
    public int gcd(int m, int n){
        while (n != 0){
            int tmp = n;
            n = m % n;
            m = tmp;
        }
        return m;
    }

    public int recursiveGcd(int m, int n){
        if (n == 0) return m;
        int r = m % n;
        return gcd(n, r);
    }

    @Test
    public void testGcd(){
        int gcd = gcd(12, 18);
        int gcd2 = recursiveGcd(12, 18);
        System.out.println(gcd + " :: " + gcd2);
    }


    /**
     * 最小公倍数，就是两者相乘之后除以最大公约数，即 m * n / gcd
     */


    /**
     * 阿里面试真题 2017-07-08 10:46
     * 在第一个字符数组中，删除在第二个字符数组中出现的字符
     * 要求：空间复杂度为 1，时间复杂度越小越好
     * @param chars1 第一个字符数组
     * @param chars2 第二个字符数组
     */
    public void deleteCommonchars(char[] chars1, char[] chars2){
        int count = 0;
        for (int i = 0; i < chars1.length; i ++){
            if (!contains(chars2, chars1[i])) continue;
            count++;
            for (int j = i+1; j < chars1.length; j ++)
                chars1[j-1] = chars1[j];
            i --;
        }
        for (int k = 0; k < count; k ++)
            chars1[chars1.length-1-k] = '\0';
    }

    private boolean contains(char[] chars, char ch){
        for (int i = 0; i < chars.length; i ++){
            if (chars[i] == ch) return true;
        }
        return false;
    }

    @Test
    public void test(){
        char[] chars1 = "hello world".toCharArray();
        System.out.println(chars1.length);
        char[] chars2 = "er".toCharArray();
        deleteCommonchars(chars1, chars2);
        System.out.println(chars1);
        System.out.println(chars1.length);
    }
}
