//package com.jiyun.Service;
//import com.github.pagehelper.PageHelper;
//
//import com.jiyun.mapper.SearchItemMapper;
//import com.jiyun.utils.JsonUtils;
//import com.jiyun.vo.ItemSearchVo;
//
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.IndicesClient;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//@Service
//public class ZYSearchItemService {
//
//
//    @Autowired
//    private SearchItemMapper searchItemMapper;
//
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
//
//    @Value("${ES_INDEX_NAME}")
//    private String ES_INDEX_NAME;
//
//    @Value("${ES_TYPE_NAME}")
//    private String ES_TYPE_NAME;
//
//    public Boolean importAll() {
//        try {
//            if(!isExistsIndex()){
//                createIndex();
//            }
//            int page=1;
//            while (true){
//                /**分页每次导入一千条*/
//                PageHelper.startPage(page,1000);
//                //1、查询mysql中的商品信息
//                List<ItemSearchVo> esDocumentList = searchItemMapper.getItemList();
//                if(esDocumentList==null || esDocumentList.size()==0){
//                    break;
//                }
//                BulkRequest bulkRequest = new BulkRequest();
//                for (int i = 0; i < esDocumentList.size(); i++) {
//                    ItemSearchVo searchItem =  esDocumentList.get(i);
//                    //2、把商品信息添加到es中
//                    bulkRequest.add(new IndexRequest(ES_INDEX_NAME, ES_TYPE_NAME).
//                            source(JsonUtils.objectToJson(searchItem), XContentType.JSON));
//                }
//                restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
//                page++;
//            }
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 索引库是否存在
//     * @return
//     * @throws IOException
//     */
//    private boolean isExistsIndex() throws IOException {
//        GetIndexRequest request = new GetIndexRequest();
//        request.indices(ES_INDEX_NAME);
//        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
//    }
//
//    /**
//     * 创建索引库
//     * @return
//     * @throws IOException
//     */
//    private boolean createIndex() throws IOException {
//        //创建索引请求对象，并设置索引名称
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest(ES_INDEX_NAME);
//        //设置索引参数
//        createIndexRequest.settings(Settings.builder().put("number_of_shards",2)
//                .put("number_of_replicas",1));
//        createIndexRequest.mapping(ES_TYPE_NAME, "{\n" +
//                "  \"_source\": {\n" +
//                "    \"excludes\": [\n" +
//                "      \"item_desc\"\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"properties\": {\n" +
//                "    \"item_title\": {\n" +
//                "      \"type\": \"text\",\n" +
//                "      \"analyzer\": \"ik_max_word\",\n" +
//                "      \"search_analyzer\": \"ik_smart\"\n" +
//                "    },\n" +
//                "    \"item_sell_point\": {\n" +
//                "      \"type\": \"text\",\n" +
//                "      \"analyzer\": \"ik_max_word\",\n" +
//                "      \"search_analyzer\": \"ik_smart\"\n" +
//                "    },\n" +
//                "    \"item_price\": {\n" +
//                "      \"type\": \"float\"\n" +
//                "    },\n" +
//                "    \"item_image\": {\n" +
//                "      \"type\": \"text\",\n" +
//                "      \"index\": false\n" +
//                "    },\n" +
//                "    \"item_category_name\": {\n" +
//                "      \"type\": \"keyword\"\n" +
//                "    },\n" +
//                "    \"item_desc\": {\n" +
//                "      \"type\": \"text\",\n" +
//                "      \"analyzer\": \"ik_max_word\",\n" +
//                "      \"search_analyzer\": \"ik_smart\"\n" +
//                "    }\n" +
//                "  }\n" +
//                "}", XContentType.JSON);
//        //创建索引操作客户端
//        IndicesClient indices = restHighLevelClient.indices();
//
//        //创建响应对象
//        CreateIndexResponse createIndexResponse =
//                indices.create(createIndexRequest,RequestOptions.DEFAULT);
//        //得到响应结果
//        return createIndexResponse.isAcknowledged();
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    /*
//     * 商品搜索
//     * */
//    public List<ItemSearchVo> list(String q, Integer page, Integer pageSize) {
//        /*
//         * 查询索引库中包含 q的信息
//         * */
//
//        try {
//            //创建一个请求对象
//            SearchRequest searchRequest = new SearchRequest("usian");
//            searchRequest.types("item");
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            /*
//             * 查询索引库中包含q的商品
//             * */
//            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(q,new String[]{
//                    "item_title","item_desc","item_sell_point","item_category_name"
//            }));
//            /*
//             * 进行分页
//             * */
//            Integer from = (page - 1) * pageSize;
//            searchSourceBuilder.from(from.intValue());
//            searchSourceBuilder.size(pageSize);
//            /*
//             * 高亮
//             * */
//            HighlightBuilder highlightBuilder = new HighlightBuilder();
//            highlightBuilder.preTags("<font color='red'>");
//            highlightBuilder.postTags("</font>");
//            highlightBuilder.field("item_title");
//            searchSourceBuilder.highlighter(highlightBuilder);
//
//            searchRequest.source(searchSourceBuilder);
//            SearchResponse response = restHighLevelClient.search(
//                    searchRequest, RequestOptions.DEFAULT);
//            SearchHit[] hits = response.getHits().getHits();
//
//            /*
//             * 返回查询结果
//             * */
//            List<ItemSearchVo> searchItems = new ArrayList<>();
//            /*
//             * 循环存入list集合中返回
//             * */
//            for (int i = 0; i < hits.length; i++) {
//                SearchHit hit = hits[i];
//                ItemSearchVo searchItem =
//                        JsonUtils.jsonToPojo(hit.getSourceAsString(),ItemSearchVo.class);
//                Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
//                if (highlightFieldMap == null || highlightFieldMap.size() > 0){
//                    searchItem.setItem_title
//                            (highlightFieldMap.get("item_title").getFragments()[0].toString());
//                }
//                searchItems.add(searchItem);
//            }
//            return searchItems;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
////
////    /*
////     * 判断索引库是否存在
////     * */
////    private boolean isExistsIndex() throws IOException {
////        //添加条件
////        GetIndexRequest getIndexRequest = new GetIndexRequest();
////        getIndexRequest.indices("usian");
////        //判断索引库是否存在
////        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
////        return exists;
////    }
////
////    /*
////     * 创建一个新的索引库
////     * */
////    private boolean createIndex() throws IOException {
////        //创建索引请求对象，并设置索引名称
////        CreateIndexRequest createIndexRequest = new CreateIndexRequest("usian");
////        //设置索引参数
////        createIndexRequest.settings(Settings.builder().put("number_of_shards",2)
////                .put("number_of_replicas",1));
////        createIndexRequest.mapping("item", "", XContentType.JSON);
////        //创建索引操作客户端
////        IndicesClient indices = restHighLevelClient.indices();
////
////        //创建响应对象
////        CreateIndexResponse createIndexResponse =
////                indices.create(createIndexRequest,RequestOptions.DEFAULT);
////        //得到响应结果
////        return createIndexResponse.isAcknowledged();
////    }
//}
