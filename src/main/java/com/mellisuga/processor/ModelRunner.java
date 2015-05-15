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
package com.mellisuga.processor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mellisuga.core.InstanceManager;
import com.mellisuga.processing.IContext;
import com.mellisuga.processing.IPMDispatcher;
import com.mellisuga.processing.IPMParser;
import com.mellisuga.processing.IProcess;
import com.mellisuga.processing.IProcessingManager;
import com.mellisuga.processing.ParaItem;
import com.mellisuga.processing.ProcessBase;
//import com.mellisuga.util.LicenseProvider;

public class ModelRunner extends ProcessBase {
	public ModelRunner(){
		this.setGuid("Mellisuga.ModelRunner");
		this.setName("Mellisuga.ModelRunner");
		this.setTitle("流程运行器");
		this.setAbstracts("流程运行器");
		
		ParaItem[] ipis = new ParaItem[2];
		ParaItem pi = null;
		pi = new ParaItem("Name", "String", "名称", false, "String");
		ipis[0] = pi;
		pi = new ParaItem("Parameter", "JSONObject", "参数", false, "JSONObject");
		ipis[1] = pi;
		this.setInputParameters(ipis);
		
		ParaItem[] opis = new ParaItem[1];
		pi = new ParaItem("Result", "JSONObject", "结果", false, "");
		opis[0] = pi;
		this.setOutputParameters(opis);	
	}
	public boolean isReady(){
		try{						
			Object obj = this.getInput("Name");
			if (null == obj  || !(obj instanceof String)){
				return false;
			}
			else{
				String name = (String)obj;
				if (name.isEmpty()){
					return false;
				}
			}
			
	        return true;
        }
        catch(Exception e){        	
        	e.printStackTrace();            
        }            
        return false;
	}	
	
	@Override
	public boolean execute() {
		try{						
			String name = "";
			Object obj = this.getInput("Name");	
			if (obj instanceof String){
				name = (String)obj;
			}
			if (null == name || name.isEmpty()){
				System.out.print("ModelRunner.execute:获取流程名称参数失败");
					return false;			
			}
			
			com.alibaba.fastjson.JSONObject parameters = null;
			obj = this.getInput("Parameter");
			if (null == obj){			
				parameters = new com.alibaba.fastjson.JSONObject();
				Set<String> keys = this._imports.keySet();
				Iterator<String> it = keys.iterator();
				String key = "";			
				while (it.hasNext()){
					key = it.next();
					parameters.put(key,  this._imports.get(key).toString());
				}
				
				keys = this._dictionaryIncoming.keySet();
				it = keys.iterator();
				while (it.hasNext()){
					key = it.next();
					parameters.put(key,  this._dictionaryIncoming.get(key).pop().toString());
				}
			}
			else if(obj instanceof com.alibaba.fastjson.JSONObject){
				parameters = (com.alibaba.fastjson.JSONObject)obj;				
			}			
			else if (obj instanceof String){
				parameters = com.alibaba.fastjson.JSON.parseObject((String)obj);
			}
			if (null == parameters){
				System.out.print("ModelRunner.execute:流程参数对象为空");				
			}
			System.out.print("ModelRunner.execute:parameters=" + parameters);	
						
			IProcessingManager _manager = InstanceManager.getInstance().processingManager();
			if (null == _manager){
				System.out.print("ModelRunner.execute:获取管理器失败");
				return false;
			}
				
			IPMParser parser = _manager.newParser();
			if (null == parser){
				System.out.print("ModelRunner.execute:获取解析器失败");
				return false;
			}
				
			IContext context = InstanceManager.getInstance().getContext();
			if (null == context){
				System.out.print("ModelRunner.execute:获取上下文失败");
				return false;
			}
			String root = (String)(context.getVariable("BaseDirectory"));	
							
			//String mpath = root + "/config/models/" + name + ".xml";
			String mpath = root + "/models/" + name + ".xml";
			parser.setFile(mpath);
			IPMDispatcher pd = _manager.newDispatcher();
			if (null == pd){
				System.out.println("ModelRunner.execute:获取派遣器失败");
				return false;
			}
			pd.setModelParser(parser);
			Map<String,String> paramap = jsonToMaps(parameters);
			Object objResult = pd.dispatch(null,paramap);
			System.out.println("ModelRunner.execute:objResult=" + objResult);
			if (objResult instanceof com.alibaba.fastjson.JSONObject){
				this.setOutput("Result", objResult);
			}
			else if (objResult instanceof com.alibaba.fastjson.JSONArray){
				this.setOutput("Result", objResult);
			}
			else{				
				com.alibaba.fastjson.JSONObject jso =  (com.alibaba.fastjson.JSONObject)_manager.createObject("JSONObject", objResult);									
				if (null == jso){
					System.out.println("转换未找到，字符化，再转JSON");
					jso = com.alibaba.fastjson.JSON.parseObject(objResult.toString());									
				}
				
				this.setOutput("Result", jso);
			}
			return true;
		}
		catch(Exception ex){
			System.out.print("ModelRunner.execute:" + ex.getMessage());			
		}
		return false;
	}

	@Override
	public IProcess clone() {
		return null;
	}

	private Map<String,String> jsonToMaps(com.alibaba.fastjson.JSONObject args){
		try{
			Map<String,String> map = new HashMap<String,String>();
			Iterator it = args.keySet().iterator();
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
}
