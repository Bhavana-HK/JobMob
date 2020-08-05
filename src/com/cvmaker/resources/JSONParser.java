package com.cvmaker.resources;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.json.JsonWriter;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class JSONParser {

	public static Object jsonToBean(String jsonInput, Class class1)
			throws Exception {
		JsonObject jsonObj = Json.createReader(new StringReader(jsonInput))
				.readObject();

		Object object = null;
		try {

			object = class1.newInstance();
			Field fieldArray[] = class1.getDeclaredFields();

			for (Field field : fieldArray) {
				// To know the Json type of the field
				// System.out.println(jsonObj.get(field.getName())+"  "+jsonObj.get(field.getName()).getValueType());
				field.setAccessible(true);
				if (jsonObj.containsKey(field.getName())) {
					// Sets the value for field type
					// Number,String,boolean,Object[Calendar,AnyUserdefined
					// Object]
					setFieldData(jsonObj, field, object);

					if (jsonObj.get(field.getName()).getValueType()
							.equals(ValueType.ARRAY)) {

						System.out.println(field.getName() + " "
								+ field.getType().getCanonicalName());

						if (field.getType().getCanonicalName()
								.contains("java.util.Map")) {
							// JsonArray
							// jsonArray=jsonObj.getJsonArray(field.getName());

							ParameterizedType listType = (ParameterizedType) field
									.getGenericType();
							Class<?> keyClass = (Class<?>) listType
									.getActualTypeArguments()[0];
							Class<?> valueClass = (Class<?>) listType
									.getActualTypeArguments()[1];
							System.out.println("hi" + keyClass + " "
									+ valueClass);

						}
						// If object type is list
						else if (field.getType().getCanonicalName()
								.contains("java.util.List")) {

							// Get the ParameterizedType of List
							ParameterizedType listType = (ParameterizedType) field
									.getGenericType();
							Class<?> listClass = (Class<?>) listType
									.getActualTypeArguments()[0];

							JsonArray jsonArray = jsonObj.getJsonArray(field
									.getName());
							List list = new ArrayList();
							// List of String
							if (listClass.getCanonicalName().contains("String")) {
								for (int i = 0; i < jsonArray.size(); i++) {
									list.add(jsonArray.getString(i));
								}
							}
							// List of Integer
							else if (listClass.getCanonicalName().contains(
									"Integer")) {
								for (int i = 0; i < jsonArray.size(); i++) {
									list.add(jsonArray.getInt(i));
								}
							}
							// list of user defined object
							else {
								for (int i = 0; i < jsonArray.size(); i++) {
									JsonObject jsonObject = jsonArray
											.getJsonObject(i);
									Object obj = getUserDefinedObject(listClass, jsonObject, field);
									list.add(obj);
								}
							}
							field.set(object, list);
						}
					}
				}
			}
			System.out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Problem in input data", e);
		}
		return object;
	}

	private static Object getUserDefinedObject(Class listClass,
			JsonObject jsonObject, Field field) throws Exception {

		Object obj = Class.forName(listClass.getName()).newInstance();
		Field fieldBean[] = listClass.getDeclaredFields();

		for (Field field2 : fieldBean) {
			field2.setAccessible(true);
			if (jsonObject.containsKey(field2.getName())) {
			setFieldData(jsonObject, field2, obj);
			}
		}
		return obj;
	}

	// Getting Calender object from JsonObject
	private static Calendar getCalendarFromJSON(JsonObject jsonObj, Field field) {
		JsonObject calJsonObj = jsonObj.getJsonObject(field.getName());

		Calendar actualCalObject = Calendar.getInstance();
		actualCalObject.set(Calendar.DATE, calJsonObj.getInt("DATE"));
		actualCalObject.set(Calendar.MONTH, calJsonObj.getInt("MONTH"));
		actualCalObject.set(Calendar.YEAR, calJsonObj.getInt("YEAR"));
		return actualCalObject;
	}

	// For setting primitive types,Calendar, User defined
	private static void setFieldData(JsonObject jsonObj, Field field,
			Object object) throws Exception {
		// System.out.println(jsonObj.get(field.getName())+"  "+jsonObj.get(field.getName()).getValueType());

		if (jsonObj.get(field.getName()).getValueType().equals(ValueType.STRING)) {
			
			field.set(object, jsonObj.getString(field.getName()));
		} else if (jsonObj.get(field.getName()).getValueType().equals(ValueType.NUMBER)) {
			if (field.getType().getCanonicalName().contains("Integer"))
				field.set(object, jsonObj.getInt(field.getName()));
			else if (field.getType().getCanonicalName().contains("Long")) {
				Long value = Long.parseLong(jsonObj.get(field.getName())
						.toString());
				field.set(object, value);
			} else if (field.getType().getCanonicalName().contains("Double")) {
				Double value = Double.parseDouble(jsonObj.get(field.getName())
						.toString());
				field.set(object, value);
			} else if (field.getType().getCanonicalName().contains("Float")) {
				Float value = Float.parseFloat(jsonObj.get(field.getName())
						.toString());
				field.set(object, value);
			} else if (field.getType().getCanonicalName().contains("Short")) {
				Short value = Short.parseShort(jsonObj.get(field.getName())
						.toString());
				field.set(object, value);
			} else if (field.getType().getCanonicalName().contains("Byte")) {
				Byte value = Byte.parseByte(jsonObj.get(field.getName())
						.toString());
				field.set(object, value);
			}
		} else if (jsonObj.get(field.getName()).getValueType()
				.equals(ValueType.TRUE)) {
			field.set(object, jsonObj.getBoolean(field.getName()));
		} else if (jsonObj.get(field.getName()).getValueType()
				.equals(ValueType.FALSE)) {
			field.set(object, jsonObj.getBoolean(field.getName()));
		} else if (jsonObj.get(field.getName()).getValueType()
				.equals(ValueType.OBJECT)) {
			// Object of Calendar type with only date,month,year
			if (field.getType().getCanonicalName()
					.contains("java.util.Calendar")) {
				Calendar actualCalObject = getCalendarFromJSON(jsonObj, field);
				field.set(object, actualCalObject);
			}

			else {
				JsonObject jsonObject2 = jsonObj.getJsonObject(field.getName());
				Object resultObj = getUserDefinedObject(field.getType(),
						jsonObject2, field);
				field.set(object, resultObj);
			}
		}
	}

	// Convert given object into Json String
	public static String beanToJson(Object input) {
		String jsonString = null;

		JsonObjectBuilder mainObjectBuilder = Json.createObjectBuilder();
		
		try {
			Class class1 = input.getClass();
			
			if(class1.getCanonicalName().contains("List")|| class1.getCanonicalName().contains("ArrayList")){
				List list=(List)input;
				System.out.println(list.get(0).getClass());
				jsonString=addListObject( list.get(0).getClass(), input).toString();
			}
			else{
			Field fields[] = input.getClass().getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					Object fieldValue = field.get(input);

					if (fieldValue != null) {
						// System.out.println(field.getType().getCanonicalName()+field.getName());
						addFieldData(mainObjectBuilder, field.getType()
								.getCanonicalName(), field.getName(),
								fieldValue, field);

					} else {
						mainObjectBuilder.add(field.getName(), JsonValue.NULL);
					}
				}
				
			
			StringWriter stringWriter = new StringWriter();
			JsonWriter jsonWriter = Json.createWriter(stringWriter);
			jsonWriter.writeObject(mainObjectBuilder.build());
			jsonString = stringWriter.getBuffer().toString();
		}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

	private static void addFieldData(JsonObjectBuilder builder, String type,
			String name, Object input, Field field) {

		if (type.contains("String")) {
			builder.add(name, input.toString());
		} else if (type.contains("Integer")) {
			builder.add(name, Integer.parseInt(input + ""));
		} else if (type.contains("Long")) {
			builder.add(name, Long.parseLong(input.toString()));
		} else if (type.contains("Double")) {
			builder.add(name, Double.parseDouble(input.toString()));
		} else if (type.contains("Float")) {
			builder.add(name, Float.parseFloat(input.toString()));
		} else if (type.contains("Short")) {
			builder.add(name, Short.parseShort(input.toString()));
		} else if (type.contains("Byte")) {
			builder.add(name, Byte.parseByte(input.toString()));
		} else if (type.equalsIgnoreCase("Boolean")) {
			builder.add(name, Boolean.parseBoolean(input.toString()));
		} else if (type.equalsIgnoreCase("Character")
				|| type.equalsIgnoreCase("char")) {
			builder.add(name, input.toString().charAt(0));
		} else if (type.contains("java.util.Calendar")) {
			addCalendarObject(builder, name, input);
		} else if (type.contains("java.util.ArrayList")
				|| type.contains("java.util.List")) {
			ParameterizedType listType = (ParameterizedType) field.getGenericType();
			Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
			JsonArray array=addListObject(listClass, input);
			builder.add(field.getName(), array);
		} else {
			builder.add(name, generateJsonObject(input));
		}

	}

	private static JsonArray addListObject( Class<?> listClass,
			Object input) {
		List list = (List) input;

		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		 //System.out.println("Type "+listClass.getCanonicalName());
		String name = listClass.getCanonicalName();
		for(Object object : list) {
			System.out.println(object.toString());
		}
		for (Object object : list) {
			if (name.contains("String")) {
				arrayBuilder.add(object.toString());
			} else if (name.contains("Integer")) {
				arrayBuilder.add(Integer.parseInt(object.toString()));
			} else if (name.contains("Long")) {
				arrayBuilder.add(Long.parseLong(object.toString()));
			} else if (name.contains("Float")) {
				arrayBuilder.add(Float.parseFloat(object.toString()));
			} else if (name.contains("Double")) {
				arrayBuilder.add(Double.parseDouble(object.toString()));
			} else if (name.contains("Byte")) {
				arrayBuilder.add(Byte.parseByte(object.toString()));
			}

			else {
				arrayBuilder.add(generateJsonObject(object));
			}

		}
		return arrayBuilder.build();

	}

	private static void addCalendarObject(JsonObjectBuilder builder,
			String name, Object input) {
		Calendar calendar = (Calendar) input;

		JsonObjectBuilder calObjBuilder = Json.createObjectBuilder();
		calObjBuilder.add("DATE", calendar.get(Calendar.DATE))
				.add("MONTH", calendar.get(Calendar.MONTH))
				.add("YEAR", calendar.get(Calendar.YEAR));
		builder.add(name, calObjBuilder.build());

	}

	private static JsonObject generateJsonObject(Object input) {
		JsonObject jsonObject = null;

		Field fields[] = input.getClass().getDeclaredFields();
		//System.out.println(input.getClass()+" inside generate JSon Object()");
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				Object fieldValue = field.get(input);

				if (fieldValue != null) {
					addFieldData(jsonObjectBuilder, field.getType()
							.getCanonicalName(), field.getName(), fieldValue,
							field);
				} else {
					jsonObjectBuilder.add(field.getName(), JsonValue.NULL);
				}
			}
			jsonObject = jsonObjectBuilder.build();
		} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
		}

		// System.out.println(jsonObject);
		return jsonObject;
	}

}
