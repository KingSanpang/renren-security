package io.renren;

/**
 * <dl>
 * <dt>Test</dt>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2017-10-25</dd>
 * </dl>
 *
 * @author Administrator
 */
public class Test {
    public static void main(String[] args) {
        String a = "abc_%s";
        System.out.println(String.format(a, "234"));
    }
}
