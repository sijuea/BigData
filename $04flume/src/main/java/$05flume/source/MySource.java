package $05flume.source;

import org.apache.flume.Context;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sijue
 * @date 2021/3/26 15:12
 * @describe
 */
public class MySource extends AbstractSource implements Configurable, PollableSource {
    //定义配置文件将来要读取的字段
    private Long delay=0L;
    private String field=null;

    /**
     *
     * @return
     * @throws EventDeliveryException
     */
    public Status process() throws EventDeliveryException {
        try {
            //创建事件头信息
            Map<String, String> hearderMap = new HashMap<String, String>();
            //创建事件
            SimpleEvent event = new SimpleEvent();
            //循环封装事件
            for (int i = 0; i < 5; i++) {
                //给事件设置头信息
                event.setHeaders(hearderMap);
                //给事件设置内容：数据库内容，rest内容，或者文件内容
                event.setBody((field + i).getBytes());
                //将事件写入channel
                getChannelProcessor().processEvent(event);
                Thread.sleep(delay);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Status.BACKOFF;
        }
        return Status.READY;
    }

    public long getBackOffSleepIncrement() {
        return 0;
    }

    public long getMaxBackOffSleepInterval() {
        return 0;
    }

    /**
     * 初始化配置信息
     * @param context
     */
    public void configure(Context context) {
        delay = context.getLong("delay");
        field = context.getString("field", "Hello!");
    }
}
