package com.rex.boot.elastic;

import com.alibaba.fastjson.JSON;
import com.rex.boot.Entity.User;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class Apis {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put(null, null);
        System.out.println(map.get(null));

        Hashtable table = new Hashtable();
        table.put("1", null);
        System.out.println(table.get("1"));

    }


    @Test
    void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("rex");
        CreateIndexResponse response = restHighLevelClient.indices().create(
                request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test
    void getIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices("rex");
        boolean exsit = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exsit);
    }

    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("rex");
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @Test
    void addDocument() throws IOException {
        User user = new User("rexjava1", 23);
        IndexRequest request = new IndexRequest("rexdb");
        request.id("1");
        request.type("doc");
//        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        request.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        System.out.println(response.status());// created update
    }

    @Test
    void updateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.index("rexdb");
        request.type("doc");
        request.id("1");
        User user = new User("rex121", 22);
        request.timeout("1s");
        request.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        System.out.println(response.status());// created update
    }

    @Test
    void isExsit() throws IOException {
        GetRequest request = new GetRequest("rexdb");
        request.id("1");
//        request.fetchSourceContext(new FetchSourceContext(false));
//        request.storedFields("_none_");
        boolean exist = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        System.out.println(exist);
    }

    @Test
    void getDocument() throws IOException {
        GetRequest request = new GetRequest("rexdb");
        request.id("1");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println(response);
        System.out.println(response.getSource());
        System.out.println(response.getSourceAsString());
    }

    @Test
    void deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.index("rexdb");
        request.type("doc");
        request.id("1");
        request.timeout("1s");
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        System.out.println(response.status());// created update
    }

    /**
     * @param
     * @return $return
     * @author rex
     * @description //TODO 批量插入数据
     * @date
     */
    @Test
    void insertBulkDocument() throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout("10s");
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("rex1rex", 221));
        list.add(new User("rex2rex", 271));
        list.add(new User("rex3rex", 251));
        list.add(new User("rex4rex", 241));
        list.add(new User("rex5", 211));
        list.add(new User("rex6", 211));
        list.add(new User("rex7", 231));
        list.add(new User("rex8", 221));
        list.add(new User("rex9", 221));
        list.add(new User("rex10", 221));
        list.add(new User("rex11", 221));

        for (int i = 0; i < list.size(); i++) {
            request.add(new UpdateRequest().index("rexdb").type("doc")
                    .doc(JSON.toJSONString(list.get(i)), XContentType.JSON)
                    .id("" + (i + 1)));
        }

        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
    }


    @Test
    void search() throws IOException {
        SearchRequest request = new SearchRequest("test3");

        SearchSourceBuilder builder = new SearchSourceBuilder();

//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name","rex");

        MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("name","java");
//        termQueryBuilder.analyzer("standard");

        builder.query(termQueryBuilder);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        builder.from();
//        builder.size();
//        builder.highlighter();

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.postTags("</span>");
        builder.highlighter(highlightBuilder);

        request.source(builder);

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response.getHits()));

        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit: response.getHits().getHits()) {
            Map<String, HighlightField> map = hit.getHighlightFields();
            HighlightField name = map.get("name");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if (name != null) {
                Text[] fragments = name.fragments();
                String newName = "";
                for (Text text : fragments) {
                    newName += text;
                }
                sourceAsMap.put("name", newName);
            }
            list.add(sourceAsMap);
        }
        System.out.println(list);
    }
}
