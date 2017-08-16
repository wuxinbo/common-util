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
			mongoClient =new MongoClient(new MongoClientURI("mongodb://192.168.0.152:27017"));
		}
		return mongoClient;
	}
	private static void init(){
		db =getMongoClient().getDatabase("test");
	}
	public static void test(){
		FindIterable<Document> iterable = db.getCollection("EcardType").find();
		for (Document data : iterable) {
			System.out.println(data.toJson());
		}
	}

	public static void insert(){
		Document ecardType =Document.parse("{" +
				"   \"name\" : \"bob\"," +
				"   \"age\" : 42," +
				"   \"type\" : 1," +
				"   \"status\" : \"A\"," +
				"   \"favorites\" : { \"artist\" : \"Miro\" }," +
				"   \"finished\" : [ 11, 25 ]," +
				"   \"badges\" : [ \"green\" ]," +
				"   \"points\" : [ { \"points\" : 85, \"bonus\" : 20 }, { \"points\" : 64, \"bonus\" : 12 } ]" +
				"}");
		db.getCollection("EcardType").insertOne(ecardType);
	}
	public static void insert(String json){
//		DBObject dbObject=
	}
}
