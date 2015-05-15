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
package com.mellisuga.runner;

import java.io.*;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import com.mellisuga.core.*;
import com.mellisuga.processing.*;
import com.mellisuga.util.Initializer;

public class Runner {
	private static class Holder {
		public static final Runner _instance = new Runner();    
	}    
	   
	public static Runner getInstance() {
		return Holder._instance;
	}
		
	public Runner(){
		Initializer.initialize();
	}
	
	public Boolean runProcess(String name,String[] args){
		try{
			IApplication app = InstanceManager.getInstance().getApplication();
			if (null == app){
				System.out.println("查找应用程序失败");
				return false;
			}
			
			IProcessingManager manager = app.processingManager();
			if (null == manager){
				System.out.println("查找管理器失败");
				return false;
			}
			IProcess process = manager.createProcess(name);
			if (null == process){
				System.out.println("未能找到'" + name + "'处理器");
				return false;
			}
			ParaItem[] pis = process.inputSet();
			if (null == pis){
				if (null != args && args.length > 0){
					System.out.println("参数匹配失败");
					return false;
				}
			}
			else{
				if (pis.length != args.length){
					System.out.println("参数个数不匹配");
					return false;
				}
				ParaItem pi = null;
				for (int i = 0; i < pis.length; ++i){
					pi = pis[i];
					if (null == pi){
						continue;
					}
					if (!process.setInputValue(pi.getName(), manager.createObject(pi.getDataType(), args[i]))){
						System.out.println("设置参数第" + i + "个参数'" + pi.getTitle() + "'失败");
						return false;
					}
				}
			}
			if (!process.execute()){
				System.out.println(name + "执行失败");
				return false;
			}
			return true;
		}
		catch(Exception e){
			System.out.println("Runner:" + e.getMessage());
		}
		return false;
	}
	
	public Boolean runModel(String content,String[] args){
		try{
			IApplication _app = InstanceManager.getInstance().getApplication();
			if (null == _app){
				System.out.println("查找应用程序失败");
				return false;
			}
			
			IProcessingManager _gpmanager = _app.processingManager();
			if (null == _gpmanager){
				System.out.println("查找管理器失败");
				return false;
			}
			
			IPMParser parser = _gpmanager.newParser();
			if (null == parser){
				System.out.println("创建解析器失败");
				return false;
			}
			String filePath = createTempFile(content);
			if (null == filePath || filePath.isEmpty()){
				System.out.println("解析内容失败");
				return false;
			}
			parser.setFile(filePath);
			
			IPMDispatcher pd = _gpmanager.newDispatcher();
			if (null == pd){
				System.out.println("创建执行器失败");
				return false;
			}
			pd.setModelParser(parser);
			IContext context = InstanceManager.getInstance().getContext();			
			pd.dispatch(context,args);
			return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public Boolean runModelFile(String path,String[] args){
		try{
			IApplication _app = InstanceManager.getInstance().getApplication();
			if (null == _app){
				System.out.println("查找应用程序失败");
				return false;
			}
			
			IProcessingManager _gpmanager = _app.processingManager();
			if (null == _gpmanager){
				System.out.println("查找管理器失败");
				return false;
			}
			
			IPMParser parser = _gpmanager.newParser();
			if (null == parser){
				System.out.println("创建解析器失败");
				return false;
			}

			parser.setFile(path);			
			IPMDispatcher pd = _gpmanager.newDispatcher();
			if (null == pd){
				System.out.println("创建执行器失败");
				return false;
			}
			pd.setModelParser(parser);
			IContext context = InstanceManager.getInstance().getContext();
			pd.dispatch(context,args);
			return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public com.alibaba.fastjson.JSONObject runModelFile(String mname,com.alibaba.fastjson.JSONObject args,boolean onlyObject){
		try{						
			Map<String,String> parameters = jsonToMaps(args);
			IProcessingManager _manager = InstanceManager.getInstance().processingManager();
				if (null == _manager){
					return null;
				}
				
				IPMParser parser = _manager.newParser();
				if (null == parser){
					return null;
				}
				
				IContext context = InstanceManager.getInstance().getContext();
				if (null == context){
					return null;
				}
				String root = (String)(context.getVariable("BaseDirectory"));	
							
				String mpath = root + "/config/models/" + mname + ".xml";
				parser.setFile(mpath);
				IPMDispatcher pd = _manager.newDispatcher();
				if (null == pd){
					System.out.println("ModelExecuteServlet.doGet:PMDispatcher为空");
					return null;
				}
				pd.setModelParser(parser);
				Object objResult = pd.dispatch(null,parameters);
				if (onlyObject){
					com.alibaba.fastjson.JSONObject obj =  (com.alibaba.fastjson.JSONObject)_manager.createObject("json", objResult);									
					if (null != obj){
						return obj;
					}
					else{
						return com.alibaba.fastjson.JSON.parseObject(objResult.toString());
					}					
				}
				else{
					return createJsonResult(pd.getResultName(), pd.getResultKind(), objResult);
				}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;		
	}
	
	public void release(){
		Initializer.release();
	}

	private String createTempFile(String content){
		try{
			UUID uuid = UUID.randomUUID();
			IContext context = InstanceManager.getInstance().getContext();
		    if (null == context){
		     	return "";
		    }
		    String baseDir = (String)(context.getVariable("BaseDirectory"));
		    String filePath = baseDir + "/temp/model/" + uuid.toString() + ".xml";
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				if (!file.getParentFile().mkdirs()) {
					System.out.println("ProjectJobExecuteServlet.createTempFile:创建文件'" + filePath + "'所在目录失败！");
				    return "";
				}
			}
			if (!file.createNewFile()) {		
				System.out.println("ProjectJobExecuteServlet.createTempFile:创建文件'" + filePath + "'失败！");
				return "";
			}
			PrintWriter writer = new PrintWriter(file,"UTF-8");			
			content = "<?xml version = \"1.0\" encoding = \"UTF-8\" ?>" + content;
			writer.println(content);			
			writer.close();
			return filePath;
		}
		catch(Exception ex){
			System.out.println("ProjectJobExecuteServlet.createTempFile:" + ex.getMessage());		
		}
		return "";
	}
	
	private Map<String,String> jsonToMaps(com.alibaba.fastjson.JSONObject args){
		try{
			Map<String,String> map = new HashMap<String,String>();
			Iterator<String> it = args.keySet().iterator();
			String key = "";
			while (it.hasNext()){
				key = (String)it.next();
				map.put(key, args.get(key).toString());
			}
			return map;
		}
		catch(Exception ex){
			
		}
		return null;
	}
	
	private  com.alibaba.fastjson.JSONObject createJsonResult(String name, String kind, Object objResult){
		com.alibaba.fastjson.JSONObject resultObj = new com.alibaba.fastjson.JSONObject();
		try{
			resultObj.put("name", name);
			resultObj.put("kind", kind);
			IProcessingManager _manager = InstanceManager.getInstance().processingManager();
			Object obj = _manager.createObject("json", objResult);
			if (null != obj){
				resultObj.put("value", obj);
			}
			else{
				resultObj.put("value", objResult);
			}
		}
		catch(Exception e){
			System.out.println("ModelExecuteServlet.2:" + e.getMessage());
		}
		return resultObj;
	}
	
	//直接调用运行流程
	//运行作业，给定定时器
	//发送消息，直接发，由消息总线中转
	
	//run -process ...
	//run -model ...
	//run -file ...
	//submit -job
	//delete -job
	//pause -job
	//restart -job
/*	
	public static void main(String[] args){
		try{
			if (args.length < 2){
				System.out.println("参数个数不够!");
				return;
			}
			Initializer.initialize();
			String cmd = args[0];	
			Date tm_begin =new Date();
			if (cmd.equalsIgnoreCase("run")){
				String option = args[1];
				if (option.equalsIgnoreCase("file")){
					if (args.length < 3){
						System.out.println("未输入文件参数!");
						return;
					}
					String path = args[2];
					File file = new File(path);
					if (!file.exists()){
						IContext context = InstanceManager.getInstance().getContext();
						if (null == context){
							return;
						}
						String root = (String)(context.getVariable("BaseDirectory"));	
						path = root + "/config/models/" + path + ".xml";
						file = new File(path);
					}
					if (!file.exists()){
						System.out.println("文件'" + path + "'不存在!");
						return;
					}
					String[] strs = new String[args.length-3];
					System.arraycopy(args, 3, strs, 0, args.length-3);
					if (!Runner.getInstance().runModelFile(path, strs)){
						System.out.println("执行失败!");
						Runner.getInstance().release();
						return;
					}
				}
				else if (option.equalsIgnoreCase("process")){
					if (args.length < 3){
						System.out.println("未输入数据处理名称!");
						return;
					}					
					String[] strs = new String[args.length-3];
					System.arraycopy(args, 3, strs, 0, args.length-3);
					if (!Runner.getInstance().runProcess(args[2], strs)){
						System.out.println("'" + args[2] + "'执行失败!");
						Runner.getInstance().release();
						return;
					}
				}
				else if (option.equalsIgnoreCase("model")){
					if (args.length < 3){
						System.out.println("未输入文件参数!");
						return;
					}
					String content = args[2];					
					String[] strs = new String[args.length-3];
					System.arraycopy(args, 3, strs, 0, args.length-3);
					if (!Runner.getInstance().runModel(content, strs)){
						System.out.println("执行失败!");
						Runner.getInstance().release();
						return;
					}
				}
				else{
					System.out.println("不支持的命令'" + option + "'");
					return;
				}
			}
			else{
				System.out.println("不支持的命令'" + cmd + "'");
			}
			Date tm_end =new Date();
			long l = tm_end.getTime()-tm_begin.getTime();   
			long day=l/(24*60*60*1000);   
			long hour=(l/(60*60*1000)-day*24);   
			long min=((l/(60*1000))-day*24*60-hour*60);   
			double s=(l/1000.0-day*24*60*60-hour*60*60-min*60);   
			System.out.println("总共花费时间："+day+"天"+hour+"小时"+min+"分"+s+"秒");
		}
		catch(Exception e){
			Runner.getInstance().release();
			System.out.println(e.getMessage());			
		}
	}
*/
}
