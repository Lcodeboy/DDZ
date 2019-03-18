package com.game.framework.util;

import com.game.framework.exception.IniFileReaderException;

import java.io.*;
import java.util.HashMap;

/**
 * INI 文件的解析器
 * 
 * 文件格式
 * 	https://baike.baidu.com/item/ini文件/9718973?fr=aladdin
 * 
 * @author zxf
 * @date 2018年1月12日 下午9:07:32
 * email xiaochen_su@126.com
 */
public class IniFileReader {
	
	//	
	private File iniFile;
	//	
	private HashMap<String, HashMap<String, String>> iniMapping = new HashMap<String, HashMap<String, String>>();
		
	public IniFileReader(File iniFile ) {
		this.iniFile = iniFile;
	}
	
	public void reader() throws IOException {
		BufferedReader reader = null;
		
		int lineCount = 0;
		
		try {
			reader = new BufferedReader( new InputStreamReader( new FileInputStream( iniFile ), "utf-8" ));
			
			String line = null;

			HashMap<String, String> sectionMap = null;
			String section = null;
			
			while( (line = reader.readLine()) != null ) {
				line = line.trim();
				lineCount++;
				
				if( line.isEmpty() ) {
					continue;
				}
				
				if( line.startsWith("#") ) {
					continue;
				}
				
				if( line.startsWith(";") ) {
					continue;
				} else {
					int startIndex = line.indexOf("[");
					int stopIndex = line.lastIndexOf("]");
					int kvIndex = line.indexOf("=");
					
					if( startIndex == 0 && stopIndex == line.length() - 1 ) {
						//	section
						section = line.substring(startIndex + 1, stopIndex);
						sectionMap = new HashMap<String, String>();
						iniMapping.put(section, sectionMap);						
					} else if( startIndex >= 0 || stopIndex >= 0 ) {
						throw new IniFileReaderException(iniFile.getName() + " line " + lineCount + " errorcode_1");
					} else if( kvIndex == -1 ) {
						throw new IniFileReaderException(iniFile.getName() + " line " + lineCount + " errorcode_2");
					} else {
						//	key&value
						if( sectionMap == null ) {
							throw new IniFileReaderException(iniFile.getName() + " line " + lineCount + " errorcode_3");
						}
						String[] result = line.split("=");
						sectionMap.put(result[0], result[1]);
					}
					
				}
				
			}
			
		} finally {
			if( reader != null ) {
				reader.close();
			}
		}
	}
	
	public File getIniFile() {
		return iniFile;
	}
	
	public String getParam( String section, String name ) {
		HashMap<String, String> paramMapping = iniMapping.get( section );
		
		if( paramMapping == null ) {
			return null;
		} 
		
		return paramMapping.get( name );
	} 
	
	public String getParam( String section, String name, String defaultValue ) {
		HashMap<String, String> paramMapping = iniMapping.get( section );
		
		if( paramMapping == null ) {
			return null;
		} 
		
		String string = paramMapping.get( name );
		
		return string == null ? defaultValue : string;
	} 
	
	public Long getLongParam( String section, String name, long def ) {
		String param = getParam(section, name);
		if( param == null ) {
			return def;
		}
		
		return Long.valueOf(param);
	}
	
	public Integer getIntegerParam( String section, String name, int def ) {
		String param = getParam(section, name);
		if( param == null ) {
			return def;
		}
		return Integer.valueOf(param);
	}
	
	public Boolean getBooleanParam( String section, String name, boolean def ) {
		String param = getParam(section, name);
		if( param == null ) {
			return def;
		}
		return Boolean.valueOf(param);
	}
	
	public HashMap<String, String> getParamMap( String section ) {
		return iniMapping.get( section );
	}

	public String toString() {
		return "IniFileReader " + iniFile.toString();
	}
	
}
