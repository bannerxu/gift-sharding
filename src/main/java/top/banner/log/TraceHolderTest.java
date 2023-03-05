package top.banner.log;

import java.util.concurrent.TimeUnit;

public class TraceHolderTest {
    public static void main(String[] args) {

        //统计方法
        TraceWatch<?> traceWatch = new TraceWatch<>(TraceHolderTest.class);
        TraceHolder.run(traceWatch, "function1", "收款", i -> sleep(1));
        TraceHolder.run(traceWatch, "function2", "收款", i -> sleep(3));
        TraceHolder.run(traceWatch, "function3", "收款", i -> sleep(4));
        final String result = TraceHolder.run(traceWatch, "function4", () -> sleepReturn(4));


        //统计一段代码好使
        try {
            traceWatch.start("统计一段代码耗时");
            TimeUnit.SECONDS.sleep(2); // 模拟业务代码
            traceWatch.stop();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout); // 模拟业务代码
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static String sleepReturn(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout); // 模拟业务代码
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "success";
    }


    //    耗时监控 ==> [QiniuServiceImpl.token()] => [获取七牛云上传凭证] => [耗时：90ms] => [开始时间：2021-07-30 14:37:57:259，结束时间：2021-07-30 14:37:57:349]
}

/* output: 
{"function2":[{"data":1004,"taskName":"function2"}],"function1":[{"data":1001,"taskName":"function1"},{"data":1002,"taskName":"function1"}]}
*/