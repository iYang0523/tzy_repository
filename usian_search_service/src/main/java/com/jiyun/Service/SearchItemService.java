package com.jiyun.Service;

import com.github.pagehelper.PageHelper;
import com.jiyun.mapper.SearchItemMapper;
import com.jiyun.util.EsUtil;
import com.jiyun.utils.JsonUtils;
import com.jiyun.vo.ItemSearchVo;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SearchItemService {

   @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private RestHighLevelClient esClient;

    @Autowired
    private EsUtil esUtil;

    // 一键导入的核心代码
    public boolean importAll()  {
        try {
            int pageNum=1;
            while (true){
                //1. DB查询数据  分页查询？  页数？  页容量（10000）
                PageHelper.startPage(pageNum,10000);
              //数据
                List<ItemSearchVo> itemSearchVos = searchItemMapper.getItemList();
                if(itemSearchVos==null || itemSearchVos.size()<=0){ //读不到
                    break;
                }
                //2. 数据添加到ES
                //2.1 判断存放的容器是否存在（index--type  索引下的列表 表）
    //
                if(!esUtil.existIndex("usian")){
                    //2.3 如果不存在，需要创建所需的容器
    //
                    esUtil.createIndex("usian",1,0,"item", "{\n" +
                            "  \"_source\": {\n" +
                            "    \"excludes\":[\"item_desc\"]\n" +
                            "  },\n" +
                            "  \"properties\": {\n" +
                            "    \"id\":{\n" +
                            "      \"type\": \"long\"\n" +
                            "    },\n" +
                            "    \"item_title\":{\n" +
                            "      \"type\": \"text\",\n" +
                            "   \"analyzer\": \"ik_max_word\",\n" +
                            "   \"search_analyzer\": \"ik_smart\"\n" +
                            "    },\n" +
                            "    \"item_sell_point\":{\n" +
                            "      \"type\": \"text\",\n" +
                            "       \"analyzer\": \"ik_max_word\",\n" +
                            "   \"search_analyzer\": \"ik_smart\"\n" +
                            "    },\n" +
                            "     \"item_price\":{\n" +
                            "       \"type\": \"double\"\n" +
                            "     },\n" +
                            "     \"item_image\":{\n" +
                            "       \"type\": \"text\",\n" +
                            "       \"index\": false\n" +
                            "     },\n" +
                            "     \"item_category_name\":{\n" +
                            "         \"type\": \"text\",\n" +
                            "       \"analyzer\": \"ik_max_word\",\n" +
                            "   \"search_analyzer\": \"ik_smart\"\n" +
                            "     },\n" +
                            "     \"item_desc\":{\n" +
                            "         \"type\": \"text\",\n" +
                            "       \"analyzer\": \"ik_max_word\",\n" +
                            "      \"search_analyzer\": \"ik_smart\"\n" +
                            "     }\n" +
                            "  }\n" +
                            "}");
                }
                // 数据保存到es
                BulkRequest bulkRequest = new BulkRequest();
                // 遍历从mysql数据库获取到的数据
                for (ItemSearchVo itemSearchVo : itemSearchVos) {  // 对象
                    bulkRequest.add(new IndexRequest("usian", "item").source(JsonUtils.objectToJson(itemSearchVo), XContentType.JSON));  // JSON
                }
                esClient.bulk(bulkRequest,RequestOptions.DEFAULT);

                pageNum++;// 查询下一页
            }
        } catch (IOException e) {

            return false;
        }
        return true;
    }
// 首頁搜索
    /*
    *  q 用户录入的查询关键字
    *
    * */
//    public List<ItemSearchVo> selectByq(String q) throws IOException {
//   List<ItemSearchVo> searchVoList=new ArrayList<>();
//        // 搜索请求对象
//        SearchRequest searchRequest = new SearchRequest("usian");
//        searchRequest.types("item");
//        // 构建查询条件的
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(q,"item_category_name","item_sell_point","item_title"));
//
//         //构建高亮的字段
//        //设置高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<font style='color:red; font-size:30px'>");
//        highlightBuilder.postTags("</font>");
//        List<HighlightBuilder.Field> fields = highlightBuilder.fields();
//        fields.add(new HighlightBuilder.Field("item_category_name"));// 分类
//        fields.add(new HighlightBuilder.Field("item_sell_point"));// 卖点
//        fields.add(new HighlightBuilder.Field("item_title"));//  标题
//        searchSourceBuilder.highlighter(highlightBuilder);
//
//        // 设置搜索源
//        searchRequest.source(searchSourceBuilder);
//        // 执行搜索
//        SearchResponse    searchResponse = esClient.search(searchRequest,RequestOptions.DEFAULT);
//
//
//        // 搜索匹配结果
//        SearchHits hits = searchResponse.getHits();
//        // 匹配的文档
//        SearchHit[] searchHits = hits.getHits();
//
//        for (SearchHit hit : searchHits) {
//            // 源文档内容
//            String source = hit.getSourceAsString();
//            ItemSearchVo itemSearchVo = JsonUtils.jsonToPojo(source, ItemSearchVo.class);
//            searchVoList.add(itemSearchVo);
////   设置 关键词在vo中字段  加上修饰后的css样式
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            if (highlightFields != null) {
//                // 关键字是否在分类字段
//                HighlightField highlightField = highlightFields.get("item_category_name");
//                if(highlightField!=null){
//                    Text[] fragments = highlightField.getFragments();
//                    if(fragments!=null && fragments.length>=0){
//                        itemSearchVo.setItem_category_name(fragments[0].toString());
//                    }
//                }
//               // 卖点
//                 highlightField = highlightFields.get("item_sell_point");
//                if(highlightField!=null){
//                    Text[]  fragments = highlightField.getFragments();
//                    if(fragments!=null && fragments.length>=0){
//                        itemSearchVo.getItem_sell_point(fragments[0].toString());
//                    }
//                }
//                //标题
//               highlightField = highlightFields.get("item_title");
//                if(highlightField!=null){
//                    Text[] fragments = highlightField.getFragments();
//                    if(fragments!=null && fragments.length>=0){
//                        itemSearchVo.getItem_title(fragments[0].toString());
//                    }
//                    searchVoList.add(itemSearchVo);
//                }
//            }
//            return searchVoList;
//        }
//        return null;
//    }


    public List<ItemSearchVo> selectByq(String q, Integer page, Integer pageSize) {
        /*
         * 查询索引库中包含 q的信息
         * */

        try {
            //创建一个请求对象
            SearchRequest searchRequest = new SearchRequest("usian");
            searchRequest.types("item");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            /*
             * 查询索引库中包含q的商品
             * */
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(q,new String[]{
                    "item_title","item_desc","item_sell_point","item_category_name"
            }));
            /*
             * 进行分页
             * */
            Integer from = (page - 1) * pageSize;
            searchSourceBuilder.from(from.intValue());
            searchSourceBuilder.size(pageSize);
            /*
             * 高亮
             * */
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("<font color='red'>");
            highlightBuilder.postTags("</font>");
            highlightBuilder.field("item_title");
            searchSourceBuilder.highlighter(highlightBuilder);

            searchRequest.source(searchSourceBuilder);
            SearchResponse response = esClient.search(
                    searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();

            /*
             * 返回查询结果
             * */
            List<ItemSearchVo> searchItems = new ArrayList<>();
            /*
             * 循环存入list集合中返回
             * */
            for (int i = 0; i < hits.length; i++) {
                SearchHit hit = hits[i];
                ItemSearchVo searchItem =
                        JsonUtils.jsonToPojo(hit.getSourceAsString(),ItemSearchVo.class);
                Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
                if (highlightFieldMap == null || highlightFieldMap.size() > 0){
                    searchItem.setItem_title
                            (highlightFieldMap.get("item_title").getFragments()[0].toString());
                }
                searchItems.add(searchItem);
            }
            return searchItems;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertDocument(String itemId) throws IOException {
        // 1、根据商品id查询商品信息。
        ItemSearchVo searchItem = searchItemMapper.getItemById(Long.valueOf(itemId));

        //2、添加商品到索引库
        IndexRequest indexRequest = new IndexRequest("ES_INDEX_NAME", "ES_TYPE_NAME");
        indexRequest.source(JsonUtils.objectToJson(searchItem), XContentType.JSON);
        IndexResponse indexResponse =
                esClient.index(indexRequest,RequestOptions.DEFAULT);
        return indexResponse.getShardInfo().getFailed();
    }
  }

