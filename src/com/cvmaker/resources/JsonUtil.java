package com.cvmaker.resources;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {

	private static ObjectMapper mapper;
	static {
		mapper = new ObjectMapper();
		
	}
	public static String convertJavaToJson(Object object) {
		String jsonResult="";
		try {
			jsonResult = mapper.writeValueAsString(object);
		}
		catch(JsonGenerationException e) {
			e.printStackTrace();
		}
		catch(JsonMappingException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object convertJsonToJava(String jsonString, Class class1) {
		Object result = null;
		try {
			result = mapper.readValue(jsonString, class1);
		}
		catch(JsonParseException e) {
			System.out.println("Exception occured while converting the json into java : "+e.getMessage());
		}
		catch(JsonMappingException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
