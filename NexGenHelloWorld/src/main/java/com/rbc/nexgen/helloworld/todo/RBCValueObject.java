package com.rbc.nexgen.helloworld.todo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class RBCValueObject implements Serializable {
	private static final long serialVersionUID = 1L;
	protected LinkedHashMap<String, Object> _data = new LinkedHashMap<String, Object>();
    public final static int _DEFAULT_COLLECTION_SIZE = 20;
	public final static String _ZERO = "0";
	public static final String AS2_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	public static final String AS2_DATE_FORMAT = "yyyy-MM-dd";
	public static final SimpleDateFormat date_time_format = new SimpleDateFormat(
			AS2_DATE_TIME_FORMAT);
	public static final SimpleDateFormat date_format = new SimpleDateFormat(
			AS2_DATE_FORMAT);
    
	public RBCValueObject() {
	}

	public RBCValueObject(int size) {
		_data = new LinkedHashMap<String, Object>(size);
	}

	@SuppressWarnings("unchecked")
	public RBCValueObject(LinkedHashMap<String, Object> properties) {
		this();
		if (properties != null)
			_data = (LinkedHashMap<String, Object>) properties.clone();
	}

	@SuppressWarnings("unchecked")
	public RBCValueObject(RBCValueObject value) {
		this();
		if (value != null) {
			_data = (LinkedHashMap<String, Object>) value.getProperties().clone();
		}
	}

	public int size(){
		return _data != null ?_data.size() : 0;
	}
	
	public void clear(){
		_data.clear();
	}
	
	public LinkedHashMap<String, Object> getProperties() {
		if (_data == null)
			_data = new LinkedHashMap<String, Object>(
					_DEFAULT_COLLECTION_SIZE);
		return _data;
	}

	public LinkedHashMap<String, Object> getProperties(int size) {
		if (_data == null)
			_data = new LinkedHashMap<String, Object>(size);
		return _data;
	}

	@SuppressWarnings("unchecked")
	public void setProperties(LinkedHashMap<String, Object> value) {
		if (value == null) {
			_data = value;
			getProperties();
		} else
			_data = (LinkedHashMap<String, Object>) value.clone();
	}

	public Set<String> getOrder() {
		return _data.keySet();
	}

	public void append(RBCValueObject addition) {
		this.getProperties().putAll(addition.getProperties());
	}

	public void clearAll(LinkedHashMap<String, Object> addition) {
		Set<String> E = addition.keySet();
		for (String name: E){
			this.delete(name);
		}
	}

	// getters and setters
	public Object getAsObject(String name) {
		return getProperties().get(name);
	}
	public Object getProperty(String name) {
		return getProperties().get(name);
	}
	// Byte
	public byte[] getAsBytes(String name) {
		return (byte[]) getProperties().get(name);
	}
	public void setBytes(String name, byte[] value) {
		set(name, value);
	}
	public void appendBytes(String name, byte[] value){
		byte[] old_value = getAsBytes(name);
		if (old_value == null) {
			setBytes(name, value);
		} else if (value != null) {
			byte[] novi = new byte[old_value.length + value.length];
			System.arraycopy(old_value, 0, novi, 0, old_value.length);
			System.arraycopy(value, 0, novi, old_value.length, value.length);
			setBytes(name,novi);
		}
	}

	public String get(String name) {
		return getAsStringOrEmpty(name);
	}
	public String getAsStringOrEmpty(String name) {
		Object o = getProperties().get(name);
		if (o == null)
			return "";
		else
			return o.toString();
	}
	public String getAsString(String name) {
		Object o = getProperties().get(name);
		if (o == null)
			return "";
		else
			return o.toString();
	}

	public String getAsString(String name, String default_) {
		Object o = getProperties().get(name);
		if (o != null)
			return o.toString();
		return default_;
	}
	public String getAsStringOrNull(String name) {
		Object o = getProperties().get(name);
		if (o == null)
			return null;
		else
			return o.toString();
	}
	public String getAsStringOrBlank(String name, int len) {
		Object o = getProperties().get(name);
        if (o == null)
            return " "; 
        else {
            String s = o.toString();
            if (s.length() < len)
                return s;
            return s.substring(0, len);
        }
	}
	public String getAsStringOrBlank(String name) {
		Object o = getProperties().get(name);
        if (o == null)
            return " "; 
        else {
             return o.toString();
        }
	}
	public String getAsStringOrZero(String propertyName) {
        Object o = getProperties().get(propertyName);
        if (o != null) {
            String s = o.toString().trim();
            if (s.length() > 0)
                return o.toString();
            else
                return _ZERO; 
        } else
            return _ZERO; 
    }
	// Date

	 public String getHHMMSS(String name) {
	        if (get(name).length() <= 0)
	            return "";
	        StringBuffer sb = new StringBuffer();
	        sb.append(get(name).substring(11, 13));
	        sb.append(":");
	        sb.append(get(name).substring(14, 16));
	        sb.append(":");
	        sb.append(get(name).substring(17, 19));
	        return sb.toString();
	    }
	    public String getDDMMYYYY(String name) {
	        if (get(name).length() <= 0)
	            return "";
	        StringBuffer sb = new StringBuffer();
	        sb.append((get(name)).substring(8, 10));
	        sb.append(".");
	        sb.append((get(name)).substring(5, 7));
	        sb.append(".");
	        sb.append((get(name)).substring(0, 4));
	        return sb.toString();
	    }
	    public String getDDMMYY(String name) {
	        try {
	            StringBuffer sb = new StringBuffer();
	            sb.append((get(name)).substring(8, 10));
	            //sb.append(".");
	            sb.append((get(name)).substring(5, 7));
	            //sb.append(".");
	            sb.append((get(name)).substring(2, 4));
	            return sb.toString();
	        } catch (Exception e) {
	            return "";
	        }
	    }

	// Integer
	public int getAsInt(String name) {
        try {
            return Integer.parseInt(getAsString(name,_ZERO));
        } catch (Exception e) {
            return 0;
        }
    }
	public int getAsInt(String name, int default_value) {
		int value;
        try {
            value = Integer.parseInt(getAsString(name,_ZERO));
            if(value == 0)
            	return default_value;
        } catch (Exception e) {
        	value = 0;
        }
        return value;
    }
    public Integer getAsIntegerObject(String name) {
    	try {
            return new Integer(Integer.parseInt(getAsStringOrZero(name)));
        } catch (Exception e) {
            return new Integer(0);
        }
    }
    // Short
    public short getAsShort(String propertyName) {
        try {
            return Short.parseShort(getAsString(propertyName,_ZERO));
        } catch (Exception e) {
            return 0;
        }
    }
    public Short getAsShortObject(String name) {
        try {
            return new Short(Short.parseShort(getAsStringOrZero(name)));
        } catch (Exception e) {
            return new Short((short) 0);
        }
    }
    // Long
    public long getAsLong(String name) {
        return Long.parseLong(getAsStringOrZero(name));
    }
    public Long getAsLongObject(String name) {
        return new Long(getAsStringOrZero(name));
    }
    // Float
    public float getAsFloat(String name) {
        return Float.parseFloat(getAsStringOrZero(name));
    }
    public Float getAsFloatObject(String name) {
        return new Float(getAsStringOrZero(name));
    }
	// Double
	public double getAsDouble(String propertyName) {
        try {
            //Double.parseDouble(getAsString(propertyName,_ZERO));
            return new Double(getAsString(propertyName,_ZERO)).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }
	 public Double getAsDoubleObject(String name) {
	        return new Double(getAsStringOrZero(name));
	    }
	 // BigDecimal
	 public BigDecimal getAsBigDecimal(String name) {
	        return new BigDecimal(getAsStringOrZero(name));
	    }
	 // Boolean
	public boolean getAsBoolean(String propertyName, boolean b) {
		try {
			b = new Boolean(getAsString(propertyName)).booleanValue();
		} catch (Exception e) {
		}
		return b;
	}
	public Boolean getAsBooleanObject(String name) {
		try {
			return new Boolean(getAsString(name)).booleanValue();
		} catch (Exception e) {
			return new Boolean(false);
		}
	}
	public boolean getAsBooleanOrFalse(String propertyName) {
        return getAsBoolean(propertyName, false);
    }
    public boolean getAsBooleanOrTrue(String propertyName) {
        boolean b = true;
        if (getAsString(propertyName) == null)
            return b;
        return getAsBoolean(propertyName, false);
    }
    //Date
  
	public java.util.Date getAsDate(String name) {
		if (getAsObject(name) == null)
			return null;
		else {
			java.util.Date date = parseStringDateTimeToDate(getAsString(name));
			if (date == null)
				return null;
			return date;
		}
	}
	
	public java.util.Date getAsUtilDate(String name) {
		if(getAsObject(name)==null)
			return null;
		else{
			java.util.Date date = parseStringDateTimeToDate(getAsString(name));
			if(date==null)
				return null;
			return date;
		}
	}
	
	public java.sql.Date getAsSqlDate(String name) {
		if(getAsObject(name)==null)
			return null;
		else{
			java.util.Date date = parseStringDateTimeToDate(getAsString(name));
			if(date==null)
				return null;
			return new java.sql.Date(date.getTime());
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

	public java.sql.Timestamp getAsSqlTimestamp(String name) {
		String value  = get(name);
		if (value == null || value.trim().length() == 0)
			return null;
		
		java.sql.Timestamp timestamp = null;

		try {
			timestamp = java.sql.Timestamp.valueOf(value);
		} catch (Exception e) {
			try {
				timestamp = new java.sql.Timestamp(date_time_format
						.parse(value).getTime());
			} catch (Exception e1) {
				java.util.Date temp_date = parseStringDateTimeToDate(value);
				timestamp = new java.sql.Timestamp(temp_date.getTime());
			}
		}
		return timestamp;
	}
	public void delete(String name) {
		getProperties().remove(name);
	}

	public void set(String name, Object value) {
		if (name != null && value != null) {
			getProperties().put(name, value);
		}else if (value == null){
			getProperties().remove(name);

		}
	}

	public void set(String name, int value) {
		set(name, "" + value);
	}

	public void set(String name, long value) {
		set(name, "" + value);
	}

	public void set(String name, float value) {
		set(name, "" + value);
	}

	public void set(String name, short value) {
		set(name, "" + value);
	}

	public void set(String name, boolean value) {
		set(name, "" + value);
	}
    public void setReplaceComma(String name, String value) {
        value = value.replace(',', '.');
        set(name, value);
    }
	public boolean containsValue(String value){
		return _data.containsValue(value);
	}
	public boolean containsKey(String value){
		return _data.containsKey(value);
	}
	public boolean exists(String name) {
		Object value = getAsObject(name);
		if (value == null)
			return false;
		else
			return true;
	}

	public Set<String> keys() {
		if (_data != null)
			return _data.keySet();
		else
			return null;
	}
	public Set<Entry<String,Object>> datas() {
		if (_data != null)
			return _data.entrySet();
		else
			return null;
	}
	public Iterator<String> iteratorKeys() {
		if(keys()!=null)
			return keys().iterator();
		else
			return new LinkedHashMap<String, Object>().keySet().iterator();//Dummy 
	}
	public void clearEmptyFields() {
		Set<String> E = keys();
		for (String name : E) {
			Object value = get(name);
			if (value instanceof java.lang.String) {
				String string_value = (String) value;
				if (string_value.length() <= 0) {
					this.delete(name);
				}
			} else if (value == null) {
				this.delete(name);
			}
		}
	}
	public void fromHashtable(Hashtable<String, Object> table) {
		Set<String> E = table.keySet();
		for (String name : E) {
			Object value = table.get(name);
			_data.put(name, value);
		}
	}
	public Hashtable<String, Object> toHashtable() {
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		Set<String> E = _data.keySet();
		for (String name : E) {
			Object value = _data.get(name);
			ht.put(name, value);
		}
		return ht;
	}
	public Object getAsJavaType(String type, String key) {
        if (type.equals("java.lang.Double")) { 
            return getAsDoubleObject(key);
        } else if (type.equals("java.lang.String")) {
            return getAsString(key);
/*        } else if (type.equals("java.util.Date")) { 
            java.sql.Timestamp t = null;//getAsSqlTimestamp(key);
            return new java.util.Date(t.getTime());
        } else if (type.equals("java.sql.Timestamp")) {
            return null;//getAsSqlTimestamp(key);
*/        } else if (type.equals("java.lang.Integer")) {
            return getAsIntegerObject(key);
        } else if (type.equals("java.lang.Short")) {
            return getAsShortObject(key);
        } else if (type.equals("java.lang.Float")) {
            return getAsFloatObject(key);
        }else if (type.equals("java.math.BigDecimal")) {
        	return getAsBigDecimal(key);
        }else if (type.equals("java.lang.Long")) {
        	return getAsLongObject(key);
        }
        return "";
    }
	
	public RBCValueObject changeKeys(String prefix, String suffix){
		RBCValueObject new_record = new RBCValueObject();
		Set<?> keySet = _data.keySet();
		Iterator<?> it = keySet.iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			StringBuffer sb = new StringBuffer();
			sb.append(prefix);
			sb.append(key);
			sb.append(suffix); //key
			new_record.set(sb.toString(), this.getAsObject(key).toString());
		}
		return new_record;
	}
	   /* Returns just required fields. */
    public RBCValueObject getAttributes(RBCValueObject _filter) {
    	RBCValueObject ret = new RBCValueObject();
        for(String name : _filter.keys()) {
            Object value = this.get(name);
            ret.set(name,value);            
        }
        return ret;
    }
    @Override
	public String toString() {
		StringBuffer buf = new StringBuffer(9000);
		buf.append("\nEN2Row=");
		Set<?> entrySet = _data.entrySet();
		Iterator<?> it = entrySet.iterator();
		while(it.hasNext()){
			buf.append(it.next().toString());
			buf.append(";");
		}
		return buf.toString();
	}
/**
	public static Object loadAgent(Object from, Object to) {
		LinkedHashMap<String, String> _meta_data = new LinkedHashMap<String, String>();
		Field[] fieldList = to.getClass().getDeclaredFields();
	// Create entry for each Agent field in _meta_data
	// collection
		for (Field name : fieldList) {
			_meta_data.put(name.getName(), name.getType().getCanonicalName());
		}
	// Now let's use the field types (above)
	// to cast the source data to Anylogic agent's data
	// parameters
	// TODO in the call to "setParameter" below, the
	// third parameter
	// is set to false,
		String[] parameters = a.getParameterNames();
		for (String name : parameters) {
			if (_meta_data.get(name).equals("java.util.Date")) {
				a.setParameter(name, this.getAsDate(name), false);
			} else if (_meta_data.get(name).equals("java.lang.String")) {
				a.setParameter(name, this.getAsString(name), false);
			} else if (_meta_data.get(name).equals("double")) {
				a.setParameter(name, this.getAsDoubleObject(name), false);
			} else if (_meta_data.get(name).equals("int")) {
				a.setParameter(name, this.getAsIntegerObject(name), false);
			} else if (_meta_data.get(name).equals("boolean")) {
				a.setParameter(name, this.getAsBooleanObject(name), false);
			}
		}
		return a;
	}**/

    public static void main(String[] args) {
 /*   	LinkedHashMap<String, String> _meta_data = new LinkedHashMap<String, String>();
    	Tank2 t2 = new Tank2();
    	Field[] fieldList = t2.getClass().getDeclaredFields();
  	
    	for(Field name: fieldList) {
    		_meta_data.put(name.getName(), name.getType().getCanonicalName());
    	}*/
    	RBCValueObject row = new RBCValueObject();
    	row.set("abc", "false");
		/*System.out.println(_meta_data.get("_date"));
		System.out.println(_meta_data.get("_name"));
		System.out.println(_meta_data.get("_test"));
		System.out.println(_meta_data.get("_pumpable_volume"));*/
	}
}