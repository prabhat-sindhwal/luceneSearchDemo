package psind.lucene.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Poc {
	
	private static Logger logger = LoggerFactory.getLogger(Poc.class);
	
	public static void main(String[] args) {
		
		logger.debug("slf4j debug log...");
		logger.error("slf4j error log...");
		
	}

}
