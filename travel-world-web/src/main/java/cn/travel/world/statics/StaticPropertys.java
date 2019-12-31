package cn.travel.world.statics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticPropertys {
	public static int PAGE_LIMIT=10;
	public static String HTTP_HEADERS_DEFAULT_FILE_PATH="headers/default_headers.txt";
	
	@Value("${travel.world.pageLimit:10}")
	public static void setPageLimit(int pageLimit) {
		PAGE_LIMIT = pageLimit;
	}
	@Value("${travel.world.headerFilePath:headers/default_headers.txt}")
	public static void setHeaderFilePath(String headerFilePath) {
		HTTP_HEADERS_DEFAULT_FILE_PATH = headerFilePath;
	}
	
	
}
