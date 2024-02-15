package customer.cap_java_demo.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private Map<Object, Map<String, Object>> books = new HashMap<>();

	CatalogServiceHandler(PersistenceService db){
		this.db = db;
	}

	// 默认的read方法
	@After(event = CqnService.EVENT_READ, entity = Books_.CDS_NAME)
	public void readBooks(Stream<Books> books) {
		System.out.println("----------------------READ----------------------");
	}

	// 写在插入和更新写在一起的方法
	// @Before(event = { CqnService.EVENT_CREATE, CqnService.EVENT_UPDATE }, entity = Books_.CDS_NAME)
	// public void changeBooks(EventContext context) {
	// 	if(context.getEvent().equals(CqnService.EVENT_CREATE)) {
	// 		CdsCreateEventContext ctx = context.as(CdsCreateEventContext.class);
	// 		// ...
	// 	} else {
	// 		CdsUpdateEventContext ctx = context.as(CdsUpdateEventContext.class);
	// 		// ...
	// 	}
	// }

	@On(event = CqnService.EVENT_CREATE, entity = Books_.CDS_NAME)
	public void addBooks(CdsCreateEventContext context, List<Books> books) {
		System.out.println("----------------------CREATE----------------------");
		// try {
		// 	// AddBooks books = reqContext.get(0);
		// 	// req.getParameterInfo().getQueryParams();
		// 	System.out.println(reqContext.getCqn().entries());
		// 	// reqContext.getCqn().entries().forEach(e -> books.put(e.get("ID"), e));
		// 	Map<String, String> paramValuesss = reqContext.getParameterInfo().getQueryParams();
		// 	Map<String, Object> bookInfo = new HashMap<>();
		// 	bookInfo.put("title", "10");
		// 	bookInfo.put("stock", 111);
	
		// 	CqnInsert insert = Insert.into("addBooks").entry(bookInfo);
		// 	List<Map<String, Object>> resultList = new ArrayList<>();
		// 	resultList = insert.entries();
		// 	result = db.run(insert);
		// 如果有namespace的话，表名字前面要加namespace
		// 	CqnInsert insert = Insert.into("my.bookshop.LogsTable").entry(paramValues);
		// 	db.run(insert);
		// 	System.out.println(result);
		// } catch (Exception e) {
		// 	System.out.println(e);
		// }
	}

	@On(event = CqnService.EVENT_UPDATE, entity = Books_.CDS_NAME)
	public void updateBooks(CdsUpdateEventContext context, List<Books> books) {
		System.out.println("----------------------UPDATE----------------------");
	}

	@On(event = CqnService.EVENT_DELETE, entity = Books_.CDS_NAME)
	public void deleteBooks(EventContext context) {
		System.out.println("----------------------DELETE----------------------");
	}

	// @On(event = {CqnService.EVENT_READ}, entity = AddBooks_.CDS_NAME)
	// public void readAddBooks(CdsCreateEventContext reqContext) {
	// 	System.out.println("aaa");
	// }

	// @On(event = CqnService.EVENT_CREATE, entity = "CatalogService.addBooks")
	// public void addBooks1(CdsCreateEventContext reqContext) {
	// 	try {
	// 		// AddBooks books = reqContext.get(0);
	// 		// req.getParameterInfo().getQueryParams();
	// 		System.out.println(reqContext.getCqn().entries());
	// 		// reqContext.getCqn().entries().forEach(e -> books.put(e.get("ID"), e));
	// 		Map<String, String> paramValuesss = reqContext.getParameterInfo().getQueryParams();
	// 		Map<String, Object> bookInfo = new HashMap<>();
	// 		bookInfo.put("title", "10");
	// 		bookInfo.put("stock", 111);
	
	// 		CqnInsert insert = Insert.into("addBooks").entry(bookInfo);
	// 		List<Map<String, Object>> resultList = new ArrayList<>();
	// 		resultList = insert.entries();
	// 		result = db.run(insert);
	// 		System.out.println(result);
	// 	} catch (Exception e) {
	// 		System.out.println(e);
	// 	}
	// }

	// @On(event = CqnService.EVENT_CREATE, entity = "CatalogService.addBooks")
	// public void addBooks(CdsCreateEventContext reqContext) {
	// 	try {
	// 		System.out.println(reqContext.getCqn().entries());

	// 		Map<String, Object> messageInfo = new HashMap<>();
	// 		messageInfo.put("message", "1233445566");
	
	// 		CqnInsert insert = Insert.into("message").entry(messageInfo);
	// 		List<Map<String, Object>> resultList = new ArrayList<>();
	// 		resultList = insert.entries();
	// 		result = db.run(insert);
	// 	} catch (Exception e) {
	// 		System.out.println(e);
	// 	}
	// }


	// @On(event = CqnService.EVENT_CREATE, entity = "CatalogService.updateBooks")
	// public void updateBooks(EventContext req) {
	// 	Map<String, Object> paramValues = new HashMap<>();
	// 	paramValues.put("LogText", "103");
	// 	CqnInsert insert = Insert.into("my.bookshop.LogsTable").entry(paramValues);
	// 	db.run(insert);
	// }

	// @On(event = "*", entity = "CatalogService.deleteBooks")
	// public void deleteBooks(EventContext req) {
	// 	// DeleteBooks deleteBooks = req.get(0);
	// 	// System.out.println(deleteBooks.getTitle());
	// 	// System.out.println(deleteBooks.getStock());
	// 	// Map<String, Object> paramValues = new HashMap<>();
	// 	// Map<String, String> paramValuesss = req.getParameterInfo().getQueryParams();
	// 	// String a  = paramValuesss.get("ID");
	// 	// paramValues.put("LogText", "103");

	// 	// CqnInsert insert = Insert.into("my.bookshop.LogsTable").entry(paramValues);
	// 	// db.run(insert);
	// }

}