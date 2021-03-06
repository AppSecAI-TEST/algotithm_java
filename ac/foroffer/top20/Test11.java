package foroffer.top20;

/**
 * Created by liyazhou on 2017/5/25.
 * 面试题11：数值的整数次方
 * 题目：
 *      实现函数 double Power(double base, int exponent)，求 base 的 exponent 次方。
 *      不得使用库函数，同时不需要考虑大数问题。
 *
 * 问题：
 *      1. 指数 exponent可能为 0 或者负数
 *      2. 底数为 0 的特殊情况, 0^0 = 1，0^n = 0
 *      3. 判断 double 型底数是否为 0，虽然double 类型不是精确类型，但是Java语言支持使用等号判断
 *
 * 思路：
 *      1、exponent == 0, 返回 1
 *      2. base == 0, 返回 0
 *      3. base!=0, exponent < 0, 累乘 1/base abs(exponenet)次
 *      4. base!=0, exponent > 0, 累乘 1/base exponent 次
 *
 */
public class Test11 {

    /**
     * 一般做法
     * @param base 底数
     * @param exponent 幂次，指数
     * @return 幂运算结果
     */
    public static double power(double base, int exponent){
        if (exponent == 0) return 1.0;  // 任何数的零次幂结果均为 1
        if (base == 0) return 0.0;

        if (exponent < 0) {
            base = 1 / base;
            exponent = -exponent;
        }
        return powerWithPositiveExponent(base, exponent);
    }

    private static double powerWithPositiveExponent(double base, int exponent) {
        double result = 1.0;
        for (int i = 0; i < exponent; i++)
            result *= base;
        return result;
    }


    /**
     * 优化的方法，折半的思想，可以减少 abs(exponent)/2 次循环
     * 如果exponent较大，可以多次折半
     * @param base 底数
     * @param exponent 幂次，指数
     * @return 幂运算结果
     */
    public static double power2(double base, int exponent){
        if (exponent == 0) return 1.0;  // 任何数的零次幂结果均为 1
        if (base == 0) return 0.0;

        if (exponent < 0) {
            base = 1 / base;
            exponent = -exponent;
        }

        double result = powerWithPositiveExponent(base, exponent>>1);  // 折半计算，减少循环次数
        result *= result;
        if ((exponent & 1) == 1) result *= base;  // 按位与 1，判断整数为奇数还是偶数
        return result;
    }

    public static void main(String[] args){
        double[] bases  =  {0, 10, -1, 5};
        int[] exponents =  {0, -5, -3, 4};
        for (int i = 0; i < bases.length; i++){
            System.out.println(String.format("power(%.6f, %d) = %.6f", bases[i], exponents[i], Test11.power(bases[i], exponents[i])));
            System.out.println(String.format("power2(%.6f, %d) = %.6f", bases[i], exponents[i], Test11.power2(bases[i], exponents[i])));
        }
    }
}
