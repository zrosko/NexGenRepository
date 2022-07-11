package com.rbc.nexgen.batch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.core.io.ByteArrayResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbc.nexgen.batch.exception.RBCNexGenException;
import com.rbc.nexgen.batch.model.IIPMApplication;
import com.rbc.nexgen.batch.reader.GenericJsonObjectReader;

@RunWith(BlockJUnit4ClassRunner.class)
public class GenericJsonObjectReaderTest {

GenericJsonObjectReader<IIPMApplication> reader;

@Before
public void setUp() {       
    reader = new GenericJsonObjectReader<IIPMApplication>(IIPMApplication.class, "results");
}

@Test
public void shouldRead_ResultAsRootNode() throws Exception {
    reader.open(new ByteArrayResource("{\"result\":{\"results\":[{\"id\":\"a\"}]}}".getBytes()) {});
    Assert.assertTrue(reader.getDatasetNode().isArray());
    Assert.assertFalse(reader.getDatasetNode().isEmpty());
}

@Test
public void shouldIgnoreUnknownProperty() throws Exception {
    String jsonStr = "{\"result\":{\"results\":[{\"id\":\"a\", \"aDifferrentProperty\":0}]}}";
    reader.open(new ByteArrayResource(jsonStr.getBytes()) {});
    Assert.assertTrue(reader.getDatasetNode().isArray());
    Assert.assertFalse(reader.getDatasetNode().isEmpty());
}

@Test
public void shouldIgnoreNullWithoutQuotes() throws Exception {
    String jsonStr = "{\"result\":{\"results\":[{\"id\":\"a\",\"name\":null}]}}";
    try {
        reader.open(new ByteArrayResource(jsonStr.getBytes()) {});
        Assert.assertTrue(reader.getDatasetNode().isArray());
        Assert.assertFalse(reader.getDatasetNode().isEmpty());
    } catch (Exception e) {
        Assert.fail(e.getMessage());
    }
}

@Test
public void shouldThrowException_OnNullNode() throws Exception {
    boolean exceptionThrown = false;
    try {           
        reader.open(new ByteArrayResource("{}".getBytes()) {});
    } catch (RBCNexGenException e) {
        exceptionThrown = true;
    }
    Assert.assertTrue(exceptionThrown);
}

@Test
public void shouldThrowException_OnNotArrayNode() throws Exception {
    boolean exceptionThrown = false;
    try {           
        reader.open(new ByteArrayResource("{\"result\":{\"results\":{}}}".getBytes()) {});
    } catch (RBCNexGenException e) {
        exceptionThrown = true;
    }
    Assert.assertTrue(exceptionThrown);
}

@Test
public void shouldReadObjectValue() {
    try {
        reader.setJsonParser(new ObjectMapper().createParser("{\"id\":\"a\"}"));
        IIPMApplication dataset = reader.read();
        Assert.assertNotNull(dataset);
        Assert.assertEquals("a", dataset.getAppCode());
    } catch (Exception e) {
        Assert.fail(e.getMessage());
    }
}

}