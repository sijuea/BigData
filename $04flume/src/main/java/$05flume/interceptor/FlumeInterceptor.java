package $05flume.interceptor;


import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author sijue
 * @date 2021/3/26 14:09
 * @describe 实现channel拦截器，区分不同类型数据
 */
public class FlumeInterceptor implements Interceptor {
    /**
     * 初始化
     */
    @Override
    public void initialize() {

    }

    /**
     * 处理单个事件
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {
        byte[] body = event.getBody();
        Map<String, String> headers = event.getHeaders();
        if(body[0]>'0' &&body[0] <'9'){
            headers.put("type","number");
        }else if(body[0]>'a' && body[0] <'z'){
            headers.put("type","letter");
        }
        event.setHeaders(headers);
        return event;
    }

    /**
     * 批量处理多个事件
     * @param list
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> list) {
        for (Event event:list){
            intercept(event);
        }
        return list;
    }

    public void close() {

    }
    public static class IncepBuilder implements Builder{

        @Override
        public Interceptor build() {
            return new FlumeInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }

    public static void main(String[] args) throws IOException {
        String a="a";
        System.out.println(a.hashCode()                                                                                            );
        //       String result = new String(out.toByteArray());
    }
}
