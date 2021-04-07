package com.jiyun.util;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EsUtil {
    @Autowired
    private RestHighLevelClient esClient;
    /*
    * 判断索引是否存在
    * */
    public Boolean existIndex(String index) throws IOException {
        IndicesClient indicesClient = esClient.indices();
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(index);
        boolean exists = indicesClient.exists(getIndexRequest, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     *创建索引
     */
    public void createIndex(String index,Integer numbeOfShards,Integer numberOfReplicas,String type,String typeSource)
    {
        //创建索引请求对象，并设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        //设置索引参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards",numbeOfShards)
                .put("number_of_replicas",numberOfReplicas));
        createIndexRequest.mapping(type,typeSource,XContentType.JSON);
        //创建索引操作客户端
        IndicesClient indices = esClient.indices();

        //创建响应对象
        try {
            indices.create(createIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
