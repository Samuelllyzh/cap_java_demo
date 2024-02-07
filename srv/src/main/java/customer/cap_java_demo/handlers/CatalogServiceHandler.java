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
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.catalogservice.CatalogService_;
import cds.gen.catalogservice.Books;
import cds.gen.catalogservice.AddBooks_;

@Component
@ServiceName(CatalogService_.CDS_NAME)
public class CatalogServiceHandler implements EventHandler {

	Result result;
	CqnService service;
	private final PersistenceService db;

	CatalogServiceHandler(PersistenceService db){
		this.db = db;
	}

	@After(event = CqnService.EVENT_READ)
	public void discountBooks(Stream<Books> books) {
		books.filter(b -> b.getTitle() != null && b.getStock() != null)
		.filter(b -> b.getStock() > 200)
		.forEach(b -> b.setTitle(b.getTitle() + " (discounted)"));
	}

	@On(event = {CqnService.EVENT_CREATE}, entity = AddBooks_.CDS_NAME)
	public void addBooks(EventContext reqContext) {
		try {

			reqContext.getParameterInfo().getQueryParams();
			System.out.println(reqContext.getParameterInfo().getQueryParams());
			Map<String, Object> bookInfo = new HashMap<>();
			bookInfo.put("title", "10");
			bookInfo.put("stock", 111);
	
			CqnInsert insert = Insert.into("addBooks").entry(bookInfo);
			List<Map<String, Object>> resultList = new ArrayList<>();
			resultList = insert.entries();
			result = db.run(insert);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// @On(event = CqnService.EVENT_CREATE, entity = "CatalogService.updateBooks")
	// public void updateBooks(EventContext req) {
	// 	Map<String, Object> paramValues = new HashMap<>();
	// 	paramValues.put("LogText", "103");
	// 	CqnInsert insert = Insert.into("my.bookshop.LogsTable").entry(paramValues);
	// 	db.run(insert);
	// }

	// @On(event = CqnService.EVENT_CREATE, entity = "CatalogService.deleteBooks")
	// public void deleteBooks(EventContext req) {
	// 	Map<String, Object> paramValues = new HashMap<>();
	// 	paramValues.put("LogText", "103");

	// 	CqnInsert insert = Insert.into("my.bookshop.LogsTable").entry(paramValues);
	// 	db.run(insert);
	// }

}