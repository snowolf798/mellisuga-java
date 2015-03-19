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
package com.mellisuga.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import com.mellisuga.core.IApplication;
import com.mellisuga.core.InstanceManager;
import com.mellisuga.independent.UniApplication;
import com.mellisuga.processing.ContextImpl;
import com.mellisuga.processing.IContext;
import com.mellisuga.processing.IProcessingManager;
import com.mellisuga.processing.IRunningLogger;

/**
 * 系统初始化器
 * @version 1.0
 * @created 10-1-2008 21:08:04
 */
final public class Initializer {		
	static IRunningLogger log = null;
	Initializer()
	{		
	}
	
	private static boolean _inited = false;
	/**
	 * 系统初始化
	 * 
	 */
	public static void initialize()
	{
		if (_inited){
			if (null != log){
				log.debug("Initializer.init:已经初始化");
			}
			return;
		}							         		
		InstanceManager.getInstance().setContext(ContextImpl.instance());
		ContextImpl.instance().initialize();	
		String strDir = System.getenv("IMAGESERVER_HOME");
		if (null == strDir || strDir.isEmpty()){
			strDir = System.getenv("PROCESSERVER_HOME");
		}
		if (null == strDir || strDir.isEmpty()){
			try{
				FileInputStream inputFile = new FileInputStream("config.properties");
				Properties _propertie = new Properties();
				_propertie.load(inputFile);
				strDir = (String)_propertie.get("BaseDirectory");
				inputFile.close();
			}
			catch(Exception ex){
				if (null != log){
					log.debug("Initializer.init:环境变量异常");
				}
				return;
			}
		}
		else{
			File file = new File(strDir + "/webapps/ProcessServer/WEB-INF/");			  
			if (file.exists()) {
				ContextImpl.instance().setVariable("BaseDirectory", strDir + "/webapps/ProcessServer/WEB-INF/");
			}
			else{
				file = new File(strDir + "/webapps/JobServer/WEB-INF/");			  
				if (file.exists()) {
					ContextImpl.instance().setVariable("BaseDirectory", strDir + "/webapps/JobServer/WEB-INF/");
				}
			}
		}
		ContextImpl.instance().setVariable("Log4j", false);
		
		IApplication _app = new UniApplication();
		InstanceManager.getInstance().setApplication(_app);
		if (null != log){
			log.debug("Initializer.initialize:应用程序设置完毕");
		}
		IProcessingManager _gpmanager = _app.processingManager();
		if (null == _gpmanager){			
			if (null != log){
				log.debug("Initializer.init:获取默认应用程序的数据处理管理器为空");
			}
			return;
		}
		_gpmanager.register(Config.factorys());

		_inited = true;
	}
	
	/**
	 * 系统初始化
	 * 
	 * <p>由外部程序指定系统运行目录
	 * @param root 系统运行目录
	 */
	public static void initialize(String root){
		if (_inited){
			if (null != log){
				log.debug("Initializer.init:已经初始化");
			}
			return;
		}							         
		
		InstanceManager.getInstance().setContext(ContextImpl.instance());		
		ContextImpl.instance().initialize();
		ContextImpl.instance().setVariable("BaseDirectory", root);
		
		IApplication _app = new UniApplication();		
		InstanceManager.getInstance().setApplication(_app);		

		IProcessingManager _gpmanager = _app.processingManager();
		if (null == _gpmanager){
			if (null != log){
				log.debug("Initializer.init:获取默认应用程序的数据处理管理器为空");
			}
			return;
		}
		_gpmanager.register(Config.factorys());

		_inited = true;
	}
	
	/**
	 * 释放环境
	 * 
	 * <p>用于整个系统退出之前，调用该函数，释放系统资源
	 */
	public static void release(){
		IProcessingManager manager = InstanceManager.getInstance().processingManager();
		manager.release();
		IContext context = InstanceManager.getInstance().getContext();
		context.clear();
	}

	/**
	 * 加载系统数据
	 */
	public static void load(){
		InstanceManager.getInstance().processingManager().loaddata();
	}
	
	/**
	 * 卸载系统数据
	 */
	public static void unload(){
		InstanceManager.getInstance().processingManager().unloaddata();		
	}
}
