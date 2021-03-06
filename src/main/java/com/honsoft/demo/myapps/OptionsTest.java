package com.honsoft.demo.myapps;

import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptionsTest {
	private static Logger logger = LoggerFactory.getLogger(OptionsTest.class);
	
	CommandLineParser parser;
	CommandLine cmd;
	Options options;

	
	private void start(String[] args) {

		options = new Options();
		
		options.addOption("t",false,"display current time");
		
		parser = new DefaultParser();
		
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(cmd.hasOption("t")) {
			System.out.println(new Date());
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(" OptionsTest started. "+ new Date());
		OptionsTest optionsTest = new OptionsTest();
		
		optionsTest.start(args);
		System.out.println(" OptionsTest ended. "+ new Date());

	}
	

}
