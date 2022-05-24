package com.rbc.nexgen.iipm.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

import com.rbc.nexgen.iipm.model.Application;

public class PojoMapper {
	public static final String RBC_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	public static final String RBC_DATE_FORMAT = "yyyy-MM-dd";
	public static final SimpleDateFormat date_time_format = new SimpleDateFormat(
			RBC_DATE_TIME_FORMAT);
	public static final SimpleDateFormat date_format = new SimpleDateFormat(
			RBC_DATE_FORMAT);
	
	public static void main(String[] args) {
		Application a = new Application();
		a.setAppCode("AAPL");
		a.setAppName("Testa Name");
		mapObjects(a,a);
	}

	public static void mapObjects(Object from, Object to) {
		LinkedHashMap<String, String> _meta_data = new LinkedHashMap<String, String>();
		// Find all fields (name, type)
		Field[] fieldList = to.getClass().getDeclaredFields();
		for (Field name : fieldList) {
			_meta_data.put(name.getName(), name.getType().getCanonicalName());
		}

		for (String name : _meta_data.keySet()) {
			if (_meta_data.get(name).equals("java.util.Date")) {
				
			} else if (_meta_data.get(name).equals("java.lang.String")) {
				System.out.println("String");
			} else if (_meta_data.get(name).equals("double")) {
				System.out.println("double");
			} else if (_meta_data.get(name).equals("int")) {
				System.out.println("int");
			} else if (_meta_data.get(name).equals("boolean")) {
				System.out.println("boolean");
			}
		}
	}
	public java.util.Date getAsDate(String name, String value) {
		if (name == null && value == null)
			return null;
		else {
			java.util.Date date = parseStringDateTimeToDate(value);
			if (date == null)
				return null;
			return date;
		}
	}
	public static java.util.Date parseStringDateTimeToDate(String stringDate) {
		try {
			return date_time_format.parse(stringDate);
		} catch (ParseException e) {
			try {
				return date_format.parse(stringDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}
}