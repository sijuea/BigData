package $05flume.offlineinterceptor;

import com.alibaba.fastjson.JSON;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.Iterator;
import java.util.List;

public class ETLInterceptor implements Interceptor {
    /**
     * 初始化
     */
    @Override
    public void initialize() {

    }

    /**
     * 针对单条数据处理
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {

        //取出数据
        byte[] body = event.getBody();
        String data = new String(body);
        //判断数据是否为json
        try{

        JSON.parseObject(data);
            return event;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 针对一个批次数据处理
     *   过滤非json数据
     * @param events
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> events) {

        Iterator<Event> it = events.iterator();

        while (it.hasNext()){
            Event event = it.next();

            if(intercept(event)==null){
                it.remove();
            }
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
        //创建自定义拦截器对象
        @Override
        public Interceptor build() {
            return new ETLInterceptor();
        }
        //获取配置
        @Override
        public void configure(Context context) {

        }
    }
}
