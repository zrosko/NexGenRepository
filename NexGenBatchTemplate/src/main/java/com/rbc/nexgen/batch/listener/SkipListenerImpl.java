package com.rbc.nexgen.batch.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.model.StudentCsv;
import com.rbc.nexgen.batch.model.StudentJson;

@Component
public class SkipListenerImpl implements SkipListener<StudentCsv, StudentJson> {

	@Override
	public void onSkipInRead(Throwable th) {
		if(th instanceof FlatFileParseException) {
			createFile("C:\\Programs\\Spring\\Workspaces\\NexGenBatchTemplate\\Chunk Job\\First Chunk Step\\reader\\SkipInRead.txt", 
					((FlatFileParseException) th).getInput());
		}
	}
	
	@Override
	public void onSkipInProcess(StudentCsv item, Throwable t) {
		createFile("C:\\Programs\\Spring\\Workspaces\\NexGenBatchTemplate\\Chunk Job\\First Chunk Step\\processer\\SkipInProcess.txt", 
				item.toString());
	}

	@Override
	public void onSkipInWrite(StudentJson item, Throwable t) {
		createFile("C:\\Programs\\Spring\\Workspaces\\NexGenBatchTemplate\\Chunk Job\\First Chunk Step\\writer\\SkipInWrite.txt", 
				item.toString());
	}

	public void createFile(String filePath, String data) {
		try(FileWriter fileWriter = new FileWriter(new File(filePath), true)) {
			fileWriter.write(data + "," + new Date() + "\n");
		}catch(Exception e) {
			
		}
	}

}
