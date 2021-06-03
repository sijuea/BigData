package $05flume.offlineinterceptor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonTest {

    public static void main(String[] args) {

        Person person = new Person();
        person.setName("lisi");
        person.setAge(20);

        //对象转json
        String json = JSON.toJSONString(person);
        System.out.println(json);

        //将json转成对象
        //{"age":100,"name":"zhangsan"}
        String jstr = "{\"age\":100,\"name\":\"zhangsan\"";
        // {} []
        JSONObject jsonObject = JSON.parseObject(jstr);
        String name = jsonObject.getString("name");
        int age = jsonObject.getInteger("age");
        System.out.println(name);
        System.out.println(age);

        //将json转成指定类型的对象
        Person p = JSON.parseObject(jstr, Person.class);
        System.out.println(p.getName());
        System.out.println(p.getAge());

    }
}
