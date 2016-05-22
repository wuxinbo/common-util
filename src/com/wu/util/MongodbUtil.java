package com.wu.util;

import org.bson.Document;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

/**
 * mongdb nosql 工具类
 * @author wuxinbo
 *@version 1.0
 *
 */
public class MongodbUtil {
	/**
	 * mongo instance
	 */
	private static MongoClient mongoClient =null;
	private static MongoDatabase db =null;
	/**
	 * 获取MongoDb 实例
	 * @return
	 */
	static{
		init();
	}
	public static MongoClient getMongoClient(){
		if (mongoClient==null) {
			mongoClient =new MongoClient(new MongoClientURI("mongodb://192.168.1.108:27017"));
		}
		return mongoClient;
	}
	private static void init(){
		db =getMongoClient().getDatabase("test");
	}
	public static void test(){
		FindIterable<Document> iterable = db.getCollection("test").find();
		for (Document data : iterable) {
			System.out.println(data.toJson());
		}
	}
	public static void insert(){
		
		db.getCollection("test").insertOne(new Document().append("name", "lisi"));
	}
	public static void insert(String json){
//		DBObject dbObject=
	}
}
