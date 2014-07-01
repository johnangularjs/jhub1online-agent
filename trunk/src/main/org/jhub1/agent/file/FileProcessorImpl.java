/**
 * $Revision$
 * $Date$
 * 
 * Copyright 2013 SoftCognito.org.
 *
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jhub1.agent.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jhub1.agent.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileProcessorImpl extends AbstractFileProcessor implements
		InputFileProcessor, OutputFileProcessor {

	private static final int LOG_MSG_REP_TIME_PERIOD = 90000;

	private long lastLogOutput = 0L;

	
	private static Logger log = LoggerFactory
			.getLogger(FileProcessorImpl.class);

	public FileProcessorImpl() {

	}

	/**
	 * Partitions the folders paths to make it easier to create
	 * 
	 */
	public void setPath(String path) {
		this.path = Paths.get(path);
		List<String> output = new ArrayList<String>();
		String elems[] = path.split("/");
		String lastElem = "";
		for (String elem : elems) {
			if (lastElem.equals("")) {
				output.add(elem);
				lastElem = elem;
			} else {
				String newElem = lastElem + "/" + elem;
				output.add(lastElem + "/" + elem);
				lastElem = newElem;
			}
		}
		this.pathElems = output;
		this.folder = new File(this.path.toUri());
	}

	@Override
	public List<String> getEndpointMessages() {
		if (isPathValid()) {
			List<String> outList = new ArrayList<String>();
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				if (file.isFile()) {
					List<String> list = processInputFiles(file);
					if (null == list) {
						logPeriodicalError("The file "
								+ file.getName()
								+ " can not be deleted. There can be permission problem. This issue pervents remaining files from processing.");
					} else {
						outList.addAll(list);
						log.trace("Captured content of file: " + file.getName());
					}
				}
			}
			return outList;
		}
		log.trace("Path: '" + path.toString() + "' doesn't exist.");
		return null;
	}

	private boolean pathExists() {
		return Files.exists(path);
	}

	private List<String> processInputFiles(File file) {
		List<String> fileContent = readFile(file);
		if (!deleteFile(file)) {
			return null;
		}
		return fileContent;

	}

	private boolean pathWritable() {
		return Files.isWritable(path);
	}

	private void logPeriodicalError(String message) {
		Date date = new Date();
		if (date.getTime() - lastLogOutput > LOG_MSG_REP_TIME_PERIOD) {
			lastLogOutput = date.getTime();
			log.error(message);
		}
	}

	private boolean deleteFile(File file) {
		log.info("Delete file: " + filePath(file));
		Path filePath = Paths.get(filePath(file));
		try {
			Files.delete(filePath);
		} catch (NoSuchFileException x) {
			return false;
		} catch (DirectoryNotEmptyException x) {
			return false;
		} catch (IOException x) {
			return false;
		}
		return true;
	}

	private String filePath(File file) {
		return path.toString() + "/" + file.getName();
	}

	private List<String> readFile(File file) {
		// This will reference one line at a time
		List<String> lines = new ArrayList<String>();
		String line = null;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(filePath(file));

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				lines.add(StringUtils.trim(line));
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filePath(file) + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filePath(file) + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
		return lines;
	}

	private boolean createPath() {
		try {
			for (String folder : pathElems) {
				Path path = Paths.get(folder);
				if (Files.notExists(path)) {
					File f = new File(folder);
					if (f.mkdir()) {
						log.info("Directory Created: " + folder);
					} else {
						log.error("Directory is not created: " + folder);
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception when creating directory: " + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	protected boolean isPathValid() {
		if (pathExists()) {
			if (pathWritable()) {
				return true;
			}
		} else {
			if (createPath()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void saveEndpointMessages(List<Sample> samples) {
		int securityCount = 0;
		if (null != samples) {
			if (samples.size() > 0) {
				isPathValid();
				for (Sample sample : samples) {
					writeToFile(sample.getName(), sample.getDate(),
							sample.getValue(), securityCount);
					securityCount++;
				}
			}
		}
		log.error("SAMPLES are PROCESSED: " + securityCount);
	}

	private void writeToFile(String name, Date date, String value,
			int securityCounter) {
		String fileName = prepareFilename(name, date,
				prepareCounter(securityCounter));
		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write(value);
			bufferedWriter.newLine();

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}

	public String prepareFilename(String samplename, Date sampledate,
			String counter) {
		StringBuilder sb = new StringBuilder();
		sb.append(path.toString()).append("/").append(samplename).append("_")
				.append(sampledate.getTime()).append("_").append(counter)
				.append(".jhub1");
		return sb.toString();
	}

	private String prepareCounter(int counter) {
		if (counter <= 9) {
			return "00" + counter;
		} else if (counter <= 99 && counter > 9) {
			return "0" + counter;
		} else {
			return "" + counter;
		}
	}
}
