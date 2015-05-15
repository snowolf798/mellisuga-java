/**
 * Copyright (c) 2008-2015 snowolf798@gmail.com. All rights reserved.
 *
 *
 * Mellisuga is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mellisuga.processing;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 数据处理运行的上下文类实现类
 * 
 * <p>上下文是数据处理运行的环境管理器，负责环境变量、全局变量的注册、删除、获取等管理，每个变量（这里称为上下文变量）在上下文中都使用一个关键字来标识。
 * 你可以重新该类，并替换默认实现。<br>
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:07:04
 */
public final class ContextImpl implements IContext {

		
	private static class Holder 
	{    
		public static final ContextImpl instance = new ContextImpl();    
	}    
	
	/**
	 * 获取{@link ContextImpl}类的唯一单件实例
	 * @return {@link ContextImpl}类实例
	 */
	public static ContextImpl instance() 
	{    
		return Holder.instance;
	}

	private Boolean _initialized = false;
	private ConcurrentHashMap<String, Object> _variablMap = new ConcurrentHashMap<String, Object>();

	/**
	 * 默认构造函数
	 */
	public ContextImpl(){

	}

	
	public void initialize(){		
		myInitialize();
	}
	private void myInitialize(){		
		if (_initialized){
			return;
		}
		
		//if (!Toolkit.validate()){            
        //}
		this.setVariable("logLevel", 2);
		try{
			this.setVariable("logger", LoggerFactory.CreateLogger());
		}
		catch(Exception ex){
			
		}
		
		try{
			String strDir = ContextImpl.class.getClassLoader().getResource("").getPath();
			strDir = strDir.substring(1, strDir.length()-1);
			strDir = strDir.substring(0, strDir.lastIndexOf("/"));
			
			String strTempDir = "";
			try{
				FileInputStream inputFile = new FileInputStream(strDir + "/config.properties");
				Properties propertie = new Properties();
				propertie.load(inputFile);
				String dir = propertie.getProperty("BaseDirectory");
				if (null != dir && !dir.isEmpty()){
					strDir = dir;
				}
				else{					
					strDir = System.getenv("PROCESSERVER_HOME");
					if (null == strDir || strDir.isEmpty()){
						strDir = System.getenv("IMAGESERVER_HOME");
						if (null != strDir && !strDir.isEmpty()){
							File ftest = new File(strDir + "/webapps/ProcessServer/WEB-INF/");
							if (ftest.exists()){
								strDir += "/webapps/ProcessServer/WEB-INF/";	
							}
							else{
								ftest = new File(strDir + "/webapps/JobServer/WEB-INF/");
								if (ftest.exists()){
									strDir += "/webapps/JobServer/WEB-INF/";	
								}	
							}
						}
					}
				}
				strTempDir = propertie.getProperty("TemporaryDirectory");
			}
			catch (Exception ex){
				strDir = System.getenv("PROCESSERVER_HOME");
				if (null == strDir || strDir.isEmpty()){
					strDir = System.getenv("IMAGESERVER_HOME");
					if (null != strDir && !strDir.isEmpty()){
						File ftest = new File(strDir + "/webapps/ProcessServer/WEB-INF/");
						if (ftest.exists()){
							strDir += "/webapps/ProcessServer/WEB-INF/";	
						}
						else{
							ftest = new File(strDir + "/webapps/JobServer/WEB-INF/");
							if (ftest.exists()){
								strDir += "/webapps/JobServer/WEB-INF/";	
							}	
						}					
					}
				}
				if (null == strDir || strDir.isEmpty()){
					System.out.println("未给系统设定默认的config.properties或者未设置环境变量，将采用默认的基本目录");
				}				
			}
			File file = null;
			if (null == strTempDir || strTempDir.isEmpty()){
				strTempDir = strDir + "/Temporary/";
			}
			else{
				file = new File(strTempDir);			  
				if (!file.exists()) {
					strTempDir = strDir + strTempDir;
				}
			}
			file = new File(strTempDir);			  
			if (!file.exists()) {
				file.mkdir();
			}
			System.out.println("系统基准目录:" + strDir);
			this.setVariable("BaseDirectory", strDir);	
			this.setVariable("TemporaryDirectory", strTempDir);
			
			try{				
				FileInputStream inputFile = new FileInputStream(strDir + "/config/config.properties");
				Properties propertie = new Properties();
				propertie.load(inputFile);
				String str = propertie.getProperty("logLevel");
				if (null != str && !str.isEmpty()){
					Integer nlvl = Integer.parseInt(str);
					if (nlvl < 0){
						nlvl = 2;
					}
					else if (nlvl > 5){
						nlvl = 2;
					}					
					this.setVariable("logLevel", nlvl);
				}
				
			}
			catch (Exception ex){
				
			}
			
			_initialized = true;
		}
		catch (Exception ex){
			System.out.println("ContextImpl.initialize:" + ex.getMessage());
		}
	}
	

	/**
	 * 向上下文中添加一个上下文变量
	 * 
	 * @param key   指定的要添加的上下文变量的关键字，关键字用来唯一标识上下文中的上下文变量
	 * @param variable   指定的上下文变量的变量值
	 */
	public void setVariable(String key, Object variable){
		try{
			_variablMap.put(key, variable);
		}
		catch (Exception ex){
			System.out.println("ContextImpl.setVariable:" + ex.getMessage());
		}
	}
	
	/**
	 * 返回指定上下文变量的值
	 * 
	 * @param name   指定的用来唯一标识上下文变量的关键字
	 * 
	 * @returns  返回指定的上下文变量的值
	 */
	public Object getVariable(String key){
		try{
			return _variablMap.get(key);
		}
		catch (Exception ex){
			System.out.println("ContextImpl.getVariable:" + ex.getMessage());
		}
		return null;
	}


	/**
	 * 获取上下文变量的个数
	 * @returns  变量个数
	 */
	public int count(){
		return _variablMap.size();
	}

	/**
	 * 返回上下文中所包含的所有上下文变量的关键字
	 * 
	 * @returns  上下文变量关键字
	 */
	public Enumeration<String> getVariableKeys(){
		return _variablMap.keys();
	}

	
	/**
	 * 清除上下文中的所有上下文变量
	 */
	public void clear(){
		_variablMap.clear();
	}

}