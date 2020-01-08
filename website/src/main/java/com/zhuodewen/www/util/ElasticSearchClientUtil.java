package com.zhuodewen.www.util;

import java.io.IOException;

/**
 * 自定义ElasticSearch工具类
 */
public class ElasticSearchClientUtil {

    /*public final static String          HOST1   = "127.0.0.1:9200";          //ES服务器ip1
    //public final static String          HOST2   = "192.168.137.1:9200";    //ES服务器ip2
    public static RestHighLevelClient client=null;                           //单例对象

    *//**
     * 创建ElasticSearch对象RestHighLevelClient(单例模式)
     * @return
     *//*
    public static RestHighLevelClient  getESClientConnection(){
        if (client == null) {
            //String[] ips= {HOST1,HOST2};
            String[] ips= {HOST1};
            HttpHost[] httpHosts = new HttpHost[ips.length];
            for(int i=0;i<ips.length;i++){
                httpHosts[i] = HttpHost.create(ips[i]);
            }
            RestClientBuilder builder = RestClient.builder(httpHosts);
            client  = new RestHighLevelClient(builder);
        }
        return client;
    }

    *//**
     *  使用ES插入或更新文档(已存在同样的则更新)
     * @param map           数据
     * @param baseIndex     索引(类似数据库)
     * @param baseType      类型(类似表)
     * @throws Exception
     *//*
    public static Map<String,String> insertOrUpdate(Map map,String baseIndex,String baseType){
        Map<String,String> resultMap=new HashMap<>();
        RestHighLevelClient restHighLevelClient=null;
        try {
            restHighLevelClient=getESClientConnection();
            IndexRequest request = new IndexRequest(baseIndex, baseType, map.get("id")+"");
            request.source(map);
            restHighLevelClient.index(request);

            resultMap.put("result","SUCCESS");
            resultMap.put("code","200");
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("result","FALSE");
            resultMap.put("code","500");
        }finally {
            try {
                if(restHighLevelClient!=null){
                    restHighLevelClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    *//**
     *  根据文档id删除文档
     * @param id            文档id
     * @param baseIndex     索引(类似数据库)
     * @param baseType      类型(类似表)
     * @throws IOException
     *//*
    public static Map<String,String> deleteById(Long id,String baseIndex,String baseType){
        Map<String,String> resultMap=new HashMap<>();
        RestHighLevelClient restHighLevelClient=null;
        try {
            restHighLevelClient=getESClientConnection();
            DeleteRequest request = new DeleteRequest(baseIndex, baseType, id + "");
            restHighLevelClient.delete(request);

            resultMap.put("result","SUCCESS");
            resultMap.put("code","200");
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("result","FALSE");
            resultMap.put("code","500");
        }finally {
            try {
                if(restHighLevelClient!=null){
                    restHighLevelClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    *//**
     * 根据文档id查找文档
     * @param id            文档id
     * @param baseIndex     索引(类似数据库)
     * @param baseType      类型(类似表)
     * @throws IOException
     *//*
    public static Map<String,Object> getById(Long id,String baseIndex,String baseType) throws IOException {
        Map<String,Object> source=new HashMap<>();
        RestHighLevelClient restHighLevelClient=null;
        try {
            restHighLevelClient=getESClientConnection();
            GetRequest request = new GetRequest(baseIndex, baseType, id+"");
            GetResponse response = client.get(request);
            source = response.getSource();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(restHighLevelClient!=null){
                restHighLevelClient.close();
            }
        }
        return source;
    }*/

    //分页查询
    //判断索引是否存在
    //创建索引
    //删除索引
    //判断类型是否存在
    //创建类型
    //删除类型
    //查询索引库中文档总数

}
