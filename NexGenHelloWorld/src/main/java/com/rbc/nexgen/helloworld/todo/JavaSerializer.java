package com.rbc.nexgen.helloworld.todo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;

import org.springframework.core.serializer.Serializer;



//https://stackoverflow.com/questions/43612072/error-to-serialize-message-when-sending-to-kafka-topic
public class JavaSerializer implements Serializer<Object> {

	public byte[] serialize(String topic, Object data) {
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(data);
			objectStream.flush();
			objectStream.close();
			return byteStream.toByteArray();
		} catch (IOException e) {
			throw new IllegalStateException("Can't serialize object: " + data, e);
		}
	}

	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	public void close() {

	}

	@Override
	public void serialize(Object object, OutputStream outputStream) throws IOException {
		// TODO Auto-generated method stub
		
	}

}