package $05flume.offlineinterceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;
import java.util.Map;

public class MyTimestampInterceptor implements Interceptor {

    /**
     * 初始化
     */
    @Override
    public void initialize() {

    }

    /**
     * 针对单挑数据操作
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {

        //获取数据时间
        String json = new String(event.getBody());
        JSONObject obj = JSON.parseObject(json);
        Long ts = obj.getLong("ts");
        //将时间添加到header中
        Map<String, String> headers = event.getHeaders();
        headers.put("timestamp",ts+"");
        //返回数据
        return event;
    }

    /**
     * 针对一个批次数据操作
     * @param events
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> events) {

        for(Event event:events){
            intercept(event);
        }

        return events;
    }

    /**
     * 关闭
     */
    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {
        @Override
        public Interceptor build() {
            return new MyTimestampInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
