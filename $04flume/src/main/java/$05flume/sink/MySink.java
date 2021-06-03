package $05flume.sink;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sijue
 * @date 2021/3/26 15:23
 * @describe
 */
public class MySink extends AbstractSink implements Configurable {
    private String prefix;
    private String suffix;

    /**
     * 创建Logger对象
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSink.class);

    public Status process() throws EventDeliveryException {
        //声明返回值状态信息
        Status status;
        //获取当前Sink绑定的Channel
        Channel ch = getChannel();
        //获取事务
        Transaction txn = ch.getTransaction();
        //声明事件
        Event event;
        //开启事务
        txn.begin();
        //读取Channel中的事件，直到读取到事件结束循环
        //event不为空就把数据读出来，为空就一直循环
        while (true) {
            event = ch.take();
            if (event != null) {
                break;
            }
        }
        try {
            //处理事件（打印），还可以做其他事情：
            // TODO mysql,redis,kafka,日志文件
            LOG.info(prefix + event.getBody().toString() + suffix);
            //事务提交
            txn.commit();
            status = Status.READY;
        } catch (Exception e) {

            //遇到异常，事务回滚
            txn.rollback();
            status = Status.BACKOFF;
        } finally {

            //关闭事务
            txn.close();
        }
        return status;
    }

    public void configure(Context context) {
        //读取配置文件内容，有默认值
        prefix = context.getString("prefix", "hello:");

        //读取配置文件内容，无默认值
        suffix = context.getString("suffix");
    }
}
