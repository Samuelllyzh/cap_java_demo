package customer.cap_java_demo.handlers;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.sap.cds.Result;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.cqn.CqnInsert;
import com.sap.cds.services.EventContext;
import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.cds.CdsUpdateEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.catalogservice.CatalogService_;
import cds.gen.catalogservice.Books;
import cds.gen.catalogservice.Books_;

@Component
@ServiceName(CatalogService_.CDS_NAME)
public class CatalogServiceHandler implements EventHandler {

	Result result;
	CqnService service;
	private final PersistenceService db;
	private Map<Object, Map<String, Object>> bookListMap = new HashMap<>();

	CatalogServiceHandler(PersistenceService db) {
		this.db = db;
	}

	// 默认的read方法
	@After(event = CqnService.EVENT_READ, entity = Books_.CDS_NAME)
	public void readBooks(Stream<Books> books) {
		System.out.println("----------------------READ----------------------");
	}

	// 写在插入和更新写在一起的方法
	// @Before(event = { CqnService.EVENT_CREATE, CqnService.EVENT_UPDATE }, entity
	// = Books_.CDS_NAME)
	// public void changeBooks(EventContext context) {
	// if(context.getEvent().equals(CqnService.EVENT_CREATE)) {
	// CdsCreateEventContext ctx = context.as(CdsCreateEventContext.class);
	// // ...
	// } else {
	// CdsUpdateEventContext ctx = context.as(CdsUpdateEventContext.class);
	// // ...
	// }
	// }

	@On(event = CqnService.EVENT_CREATE, entity = Books_.CDS_NAME)
	public void addBooks(CdsCreateEventContext createContext, List<Books> books) {
		System.out.println("----------------------CREATE----------------------");
		try {
			// 通过createContext获取请求中的参数
			createContext.getCqn().entries().forEach(e -> bookListMap.put(e.get("ID"), e));
			System.out.println(bookListMap);

			// 获取当前时间(GMT+0)
			String nowTimeStr = getNowTimeStr();
			// 操作ログ(get req中各种参数)
			String logStr = "操作ログ「:" + "\r\n" +
					"   ★実行時間		:" + nowTimeStr + "\r\n" +
					"   ★イベント名		:" + createContext.getEvent() + "\r\n" +
					"   ★エンティティ名	:" + createContext.getTarget().getQualifiedName() + "\r\n" +
					"   ★パラメータ		:" + createContext.getCqn().entries() + "\r\n" +
					"   ★ユーザID		:" + createContext.getUserInfo().getId() + "\r\n" +
					"	★ユーザ名称		:" + createContext.getUserInfo().getName() + "\r\n" +
					"」";
			System.out.println(logStr);

			Map<String, Object> logMessageInfo = new HashMap<>();
			logMessageInfo.put("message", logStr);
			/**
			 * 准备insert语句
			 * 如果有namespace的话，表名字前面要加namespace
			 * Insert.into("my.bookshop.LogMessage").entry(logMessageInfo);
			 **/
			CqnInsert insertToLogMessage = Insert.into("LogMessage").entry(logMessageInfo);
			result = db.run(insertToLogMessage);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@On(event = CqnService.EVENT_UPDATE, entity = Books_.CDS_NAME)
	public void updateBooks(CdsUpdateEventContext context, List<Books> books) {
		System.out.println("----------------------UPDATE----------------------");
	}

	@On(event = CqnService.EVENT_DELETE, entity = Books_.CDS_NAME)
	public void deleteBooks(EventContext context) {
		System.out.println("----------------------DELETE----------------------");
	}

	// 获取当前时间(GMT+0)
	public String getNowTimeStr() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")
				.withZone(TimeZone.getTimeZone("GMT+0").toZoneId());
		String nowTimeStr = df.format(Instant.now());
		return nowTimeStr;
	}

}