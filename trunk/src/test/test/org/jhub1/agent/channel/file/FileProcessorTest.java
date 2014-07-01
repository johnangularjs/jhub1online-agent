package test.org.jhub1.agent.channel.file;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.jhub1.agent.file.FileProcessorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileProcessorTest {
	
//	private static ConfigFilesIn config;
	private static FileProcessorImpl fp;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		fp = new FileProcessorImpl();
		fp.setPath("my/path/");
	}
	
	@Test
	public void checkGeneratedFileNameAndPath() {
		Date sdate = new Date(); 
		String producedFilename = fp.prepareFilename("SampleName", sdate, "999");
		String expectedFilename = "my/path/SampleName_" + sdate.getTime() + "_999.jhub1";
		assertEquals(expectedFilename, producedFilename);
	}
}
