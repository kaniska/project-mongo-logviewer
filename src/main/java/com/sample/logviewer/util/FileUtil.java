package com.sample.logviewer.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class FileUtil {
	
  public static File getContextFile(String filename) {
    File f = new File(filename);
    return (f.isAbsolute()) ? f : new File(filename);
  }

	public static void close(Closeable closable) {
		if(closable!=null)
		{
			try {
				closable.close();
			} catch (IOException e) {
				//if error happens, we let it go and just log it
				LogFactory.getLog(FileUtil.class).warn("Error while trying to close",e);
			}
		}
		
	}
}
