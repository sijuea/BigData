package com.demo.dao;

import com.demo.entity.Option;
import com.demo.entity.SaleDetail;
import com.demo.entity.Stat;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import net.minidev.json.JSONObject;
import org.apache.zookeeper.Op;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sijue
 * @date 2021/6/2 18:45
 * @describe
 */
@Repository
public class EsDaoImpl  implements ESDao{
    @Autowired
    JestClient jestClient;

    /**
     * 根据需求写查询语句并且处理返回结果
     * 查询使用小米手机的男生和女生的统计以及不同年龄段的统计（age<=20|| age>30|| (20 < age<30)）
     * ES语句：GET /gmall2021_sale_detail2021-06-02/_search
     * {
     *   "query": {
     *     "match": {
     *     "sku_name": "小米手机"
     *   }
     *   },
     *   "from": 0,
     *   "size": 10,
     *   "aggs": {
     *     "gender_count": {
     *       "terms": {
     *         "field": "user_gender.keyword",
     *         "size": 10
     *       }
     *     },
     *     "age_count":{
     *       "terms": {
     *         "field": "user_age",
     *         "size": 150
     *       }
     *     }
     *   }
     * }
     * @return
     */
    @Override
    public JSONObject search(String  date,Integer startpage,Integer size,String keyword) throws IOException {
        String indexName="gmall2021_sale_detail"+date;
        MatchQueryBuilder match = new MatchQueryBuilder("sku_name", "小米手机");

        TermsAggregationBuilder agg1 = AggregationBuilders.terms("gender_count").field("user_gender.keyword").size(10);
        TermsAggregationBuilder agg2 = AggregationBuilders.terms("age_count").field("user_age").size(150);
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(match).aggregation(agg1).aggregation(agg2)
                .from((startpage-1)*size)
                .size(size);
        SearchResult result = jestClient.execute(
                new Search.Builder(builder.toString())
                        .addType("_doc")
                        .addIndex(indexName).build());
        //遍历输出的数据整合为要求返回的数据内容
        //detail
        List<SaleDetail> list = transFormDetail(result);
        //stat：
        Stat statAge = new Stat("用户年龄占比", transFormAgeStat(result));
        Stat statGender = new Stat("用户性别占比", transFormGenderStat(result));
        List<Stat> stats=new ArrayList<>();
        stats.add(statAge);
        stats.add(statGender);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("total",result.getTotal());
        jsonObject.put("stat",stats);
        jsonObject.put("detail",list);
        return jsonObject;


    }

    public List<SaleDetail> transFormDetail(SearchResult result){
        List<SearchResult.Hit<SaleDetail, Void>> hits = result.getHits(SaleDetail.class);
        List<SaleDetail> list=new ArrayList<>();
        //先生成detail【增加一个es_metadata_id字段】
        for (SearchResult.Hit<SaleDetail, Void> hit : hits) {
            SaleDetail source = hit.source;
            source.setEs_metadata_id(hit.id);
            list.add(source);
        }
        return list;
    }

    /**
     * 处理聚合之后年龄的数据
     * age_count
     * @param result
     * @return
     */
    public List<Option> transFormAgeStat(SearchResult result){
        MetricAggregation aggregations = result.getAggregations();
        TermsAggregation ageCount = aggregations.getTermsAggregation("age_count");
        List<TermsAggregation.Entry> buckets = ageCount.getBuckets();
        long le20Count=0;
        long b20t30Count=0;
        long ge30Count=0;
        for (TermsAggregation.Entry bucket : buckets) {
            int key = Integer.parseInt(bucket.getKey());
            if(key <=20){
                le20Count+=bucket.getCount();
            }else if(key>=30){
                ge30Count+=bucket.getCount();
            }else{
                b20t30Count+=bucket.getCount();
            }
        }
        double sum=le20Count+ge30Count+b20t30Count;
        DecimalFormat format = new DecimalFormat("###.00");
        double le20Percent = Double.parseDouble(format.format(le20Count / sum * 100));
        double  ge30Percent = Double.parseDouble(format.format(ge30Count / sum * 100));
        double b20t30Percent = Double.parseDouble(format.format(100-le20Percent-ge30Percent));
        //封装
        Option le20Option = new Option("20岁以下", le20Percent);
        Option b20t30Option = new Option("20岁到30岁",b20t30Percent );
        Option ge30Option = new Option("30岁及30岁以上", ge30Percent);

        List<Option> options=new ArrayList<>();
        options.add(le20Option);
        options.add(b20t30Option);
        options.add(ge30Option);
        return options;

    }

    /**
     * 处理性别的数据
     * @param result
     * @return
     */
    public List<Option> transFormGenderStat(SearchResult result){
        MetricAggregation aggregations = result.getAggregations();
        TermsAggregation genderCount = aggregations.getTermsAggregation("gender_count");
        List<TermsAggregation.Entry> buckets = genderCount.getBuckets();
        long fmale=0;
        long male=0;
        for (TermsAggregation.Entry bucket : buckets) {
            if(bucket.getKey().equals("F") ){
                fmale=bucket.getCount();
            }else {
                male=bucket.getCount();
            }
        }
        double sum=fmale+male;
        DecimalFormat format=new DecimalFormat("###.00");
        double fmalePercent = Double.parseDouble(format.format(fmale / sum * 100));
        double  malePercent = Double.parseDouble(format.format(100-fmalePercent));
        //封装
        Option fmaleOption = new Option("男", malePercent);
        Option maleOption = new Option("女",fmalePercent );

        List<Option> options=new ArrayList<>();
        options.add(fmaleOption);
        options.add(maleOption);
        return options;

    }

}
