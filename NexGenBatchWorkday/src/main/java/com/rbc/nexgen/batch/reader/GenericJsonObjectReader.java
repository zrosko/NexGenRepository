package com.rbc.nexgen.batch.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.rbc.nexgen.batch.exception.RBCNexGenException;

import lombok.extern.log4j.Log4j2;

/*
 * https://www.petrikainulainen.net/programming/spring-framework/spring-batch-tutorial-creating-a-custom-itemreader/
 * https://www.petrikainulainen.net/programming/spring-framework/spring-batch-tutorial-reading-information-from-a-rest-api/
 * https://medium.com/@join_vineet/deploy-spring-boot-application-with-pcf-and-apigee-504b36967ab6
 * 
 * This class follows the structure and functions similar to JacksonJsonObjectReader, with 
 * the difference that it expects a object as root node, instead of an array.
 * Use the constructor to pass in "targetPath" withing the json file (array).
 */
@Service
@Log4j2
public class GenericJsonObjectReader<T> implements JsonObjectReader<T>{

ObjectMapper mapper = new ObjectMapper();
private JsonParser jsonParser;
private InputStream inputStream;

private ArrayNode targetNode;
private Class<T> targetType;
private String targetPath;
private List<Object> list;

public GenericJsonObjectReader() {
    super();
}
public GenericJsonObjectReader(Class<T> targetType, String targetPath) {
    super();
    this.targetType = targetType;
    this.targetPath = targetPath;
}

public JsonParser getJsonParser() {
    return jsonParser;
}

public void setJsonParser(JsonParser jsonParser) {
    this.jsonParser = jsonParser;
}

public ArrayNode getDatasetNode() {
    return targetNode;
}

public void setList(List<Object> list) {
	this.list = list;
}

public List<Object> getList() {
	return list;
}
/*
 * JsonObjectReader interface has an empty default method and must be implemented in this case to set 
 * the mapper and the parser
 */
@Override
public void open(Resource resource) throws Exception {
    log.info("Opening json object reader");
    this.inputStream = resource.getInputStream();
    JsonNode jsonNode = this.mapper.readTree(this.inputStream).findPath(targetPath);
    if (!jsonNode.isMissingNode()) {
        this.jsonParser = startArrayParser(jsonNode);
        log.info("Reader open with parser reference: " + this.jsonParser);
        this.targetNode = (ArrayNode) jsonNode; // for testing purposes
    } else {
        log.error("Couldn't read target node " + this.targetPath);
        throw new RBCNexGenException("701");
    }
}

@Override
public T read() throws Exception {
    try {
        if (this.jsonParser.nextToken() == JsonToken.START_OBJECT) {
            T result = this.mapper.readValue(this.jsonParser, this.targetType);
            log.info("Object read: " + result.hashCode());
            return result;
        }
    } catch (IOException e) {
        throw new ParseException("Unable to read next JSON object", e);
    }
    return null;
}

/**
 * Creates a new parser from an array node
 */
private JsonParser startArrayParser(JsonNode jsonArrayNode) throws IOException {
    JsonParser jsonParser = this.mapper.getFactory().createParser(jsonArrayNode.toString());
    if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
        return jsonParser;
    } else {
        throw new RBCNexGenException("700");
    }
}

public List<Object> getObjectsList() {
	try {
		list = new ArrayList<>();
	
		T obj = read();
		while (obj != null) {
			list.add(obj);
			obj = read();
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	return list;
}

@Override
public void close() throws Exception {
    this.inputStream.close();
    this.jsonParser.close();
}
}