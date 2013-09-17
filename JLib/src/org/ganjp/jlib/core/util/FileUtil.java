/**
 * DialogUtil.java
 *
 * Created by Gan Jianping on 17/9/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 *
 */
package org.ganjp.jlib.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p>File Utility</p>
 *
 * @author GanJianping
 * @since 1.0
 */
public class FileUtil {
	
	public static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
}
