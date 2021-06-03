package $01hdfs;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author sijue
 * @date 2021/3/3 20:39
 * @describe
 */
public class HdfsClient {
    static class demo{
        public int fenzi;
        public int fenmu;

        public demo() {
        }

        public demo(int fenzi, int fenmu) {
            this.fenzi = fenzi;
            this.fenmu = fenmu;
        }

        public int getFenzi() {
            return fenzi;
        }

        public void setFenzi(int fenzi) {
            this.fenzi = fenzi;
        }

        public int getFenmu() {
            return fenmu;
        }

        public void setFenmu(int fenmu) {
            this.fenmu = fenmu;
        }
        public double  chufa(int fenmu,int fenzi){
            return Math.round(fenzi/fenmu);

        }
    }
    public static void main(String[] args) {
        double remains;
        double sum = 0 ;
        double left = 0;
        int  k = 0, j = 0;
        Map<Integer,demo> map1=new HashMap<Integer, demo>();
        map1.put(2,new demo(2,7));
        map1.put(4,new demo(3,7));
        map1.put(6,new demo(2,7));
        int p[]={2,4,6};
        Map<Integer,demo> map2=new HashMap<Integer, demo>();
        map2.put(2,new demo(1,7));
        map2.put(4,new demo(2,7));
        map2.put(6,new demo(2,7));
        map2.put(7,new demo(2,7));
        int q[]={2,4,6,7};
        for ( int i=0;i<p.length;i++) {
            demo demo = map1.get(p[i]);
            double chufa = demo.chufa(demo.getFenzi(), demo.getFenmu());
            if (left > chufa) {
                //dij==d(i,j)
                sum = sum + p[i] * d(p[i],q[k]);
                left =  (left - chufa);
            } else {
                remains = chufa;

                while (j < q.length) {
                    double chufa1 = new demo().chufa(map2.get(q[j]).getFenzi(),
                            map2.get(q[j]).getFenmu());
                    if (!(left + chufa1 < remains)) break;
                    //dij==d(i,j)
                    //dik==d(i,k)

                    sum = sum + left * d(i,k) + chufa1 * d(i,j);
                    remains = remains - left - chufa1;
                    left = 0;
                    k = j;
                    j++;
                    if (j<q.length&&left+chufa1>=remains){
                        sum+=left*d(i,k)+(remains-left)+d(i,j);
                        left=left+chufa1;k=j;
                        j++;
                        break;
                    }
                }
            }
        }
        System.out.println(sum);
    }
    public static int d(int i ,int j){
        return Math.abs(i-j)/5;
    }
}
