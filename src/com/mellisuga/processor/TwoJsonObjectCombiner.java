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

import java.util.Iterator;
import com.mellisuga.processing.IProcess;
import com.mellisuga.processing.IRunningLogger;
import com.mellisuga.processing.LoggerFactory;
import com.mellisuga.processing.ParaItem;
import com.mellisuga.processing.ProcessBase;

public class TwoJsonObjectCombiner extends ProcessBase {
	private static IRunningLogger _logger = LoggerFactory.CreateLogger();
	
	public TwoJsonObjectCombiner(){
		this.setGuid("TwoJsonObjectCombiner");
		this.setName("TwoJsonObjectCombiner");
		this.setTitle("Json组合器");
		this.setAbstracts("给定两个Json对象，将其组合为一个对象");
				
		ParaItem[] ipis = new ParaItem[4];
		ParaItem pi = null;
		pi = new ParaItem("JsonOne", "JSONObject", "对象一", false, "JSON");
		ipis[0] = pi;
		pi = new ParaItem("KeyOne", "String", "标识一", true, "String");
		ipis[1] = pi;		
		pi = new ParaItem("JsonTwo", "JSONObject", "对象二", false, "JSON");
		ipis[2] = pi;
		pi = new ParaItem("KeyTwo", "String", "标识二", true, "String");
		ipis[3] = pi;	
		this.setInputParameters(ipis);
		
		ParaItem[] opis = new ParaItem[1];
		pi = new ParaItem("JSONObject", "JSONObject", "Any", true, "Any");
		opis[0] = pi;
		this.setOutputParameters(opis);	
	}
	
	@Override
	public boolean execute() {
		try{
			com.alibaba.fastjson.JSONObject js1 = null;	
			com.alibaba.fastjson.JSONArray jsa1 = null;
			Object obj = this.getInput("JsonOne");			
			if (obj instanceof com.alibaba.fastjson.JSONObject){
				js1 = (com.alibaba.fastjson.JSONObject)obj;				
			}
			else if(obj instanceof com.alibaba.fastjson.JSONArray){
				jsa1 = (com.alibaba.fastjson.JSONArray)obj;				
			}
			else if (obj instanceof String){
				js1 = com.alibaba.fastjson.JSON.parseObject((String)obj); 
			}
			if (null == js1 && null == jsa1){
				_logger.error("请输入第一个JSON对象");				
				return false;
			}
			
			String keyOne = "";
			obj = this.getInput("KeyOne");			
			if (obj instanceof String){
				keyOne = (String)obj;				
			}
			
			com.alibaba.fastjson.JSONObject js2 = null;	
			com.alibaba.fastjson.JSONArray jsa2 = null;
			obj = this.getInput("JsonTwo");			
			if (obj instanceof com.alibaba.fastjson.JSONObject){
				js2 = (com.alibaba.fastjson.JSONObject)obj;				
			}
			else if(obj instanceof com.alibaba.fastjson.JSONArray){
				jsa2 = (com.alibaba.fastjson.JSONArray)obj;				
			}
			if (null == js2 && null == jsa2){				
				_logger.error("请输入第二个JSON对象");
				return false;
			}
			
			String keyTwo = "";
			obj = this.getInput("KeyTwo");			
			if (obj instanceof String){
				keyTwo = (String)obj;				
			}
			
			com.alibaba.fastjson.JSONObject jso = null;
			if (null != js1 && null != js2){
				jso = combiner(js1, js2);
			}
			else if (null != js1 && null != jsa2){
				if (null == keyTwo || keyTwo.isEmpty()){
					return false;
				}
				js1.put(keyTwo, jsa2);		
				jso = js1;
			}
			else if (null != jsa1 && null != js2){
				if (null == keyOne || keyOne.isEmpty()){
					return false;
				}
				js2.put(keyOne, jsa1);		
				jso = js2;
			}
			else if (null != jsa1 && null != jsa2){
				if (null == keyOne || keyOne.isEmpty() || null == keyTwo || keyTwo.isEmpty()){
					return false;
				}
				jso = new com.alibaba.fastjson.JSONObject();
				jso.put(keyOne, jsa1);		
				jso.put(keyTwo, jsa2);	
			}
			else{				
				return false;
			}
			this.setOutput("JSONObject",jso);
			return true;			
		}
		catch(Exception ex){
			_logger.error(ex.getMessage());
			System.out.println("TwoJsonObjectCombiner.execute:" + ex.getMessage());
		}
		return false;
	}

	@Override
	public IProcess clone() {
		return null;
	}

	private com.alibaba.fastjson.JSONObject combiner(com.alibaba.fastjson.JSONObject js, com.alibaba.fastjson.JSONObject append){
		if (null == append || append.size() <= 0){			
			return js;
		}
		try{
			 Iterator<String> it = append.keySet().iterator();
			 String name = "";
			if (it.hasNext()){
				name = it.next();
				js.put(name, append.get(name));									
			}	
			else{
				System.out.println("TwoJsonObjectCombiner.combiner:第二个js的names为空");
			}
		}
		catch(Exception ex){
			System.out.println("TwoJsonObjectCombiner.combiner:" + ex.getMessage());
		}
		return js;
	}
}
