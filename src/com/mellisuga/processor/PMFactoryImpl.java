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

import java.util.ArrayList;
import java.util.Date;
import com.mellisuga.processing.IConnector;
import com.mellisuga.processing.IPMFactory;
import com.mellisuga.processing.IProcess;

public class PMFactoryImpl implements IPMFactory {
	private ArrayList<String> _proceses = null;
	
	@Override
	public String abstracts() {
		return null;
	}

	@Override
	public String author() {
		return null;
	}

	@Override
	public IConnector createConnector(String inParaType, String outParaType) {
		if (null == inParaType || null == outParaType){
			return null;
		}
		if (inParaType.equalsIgnoreCase("JSONArray") && (outParaType.equalsIgnoreCase("JSONObject") || outParaType.equalsIgnoreCase("JSON"))){
			return new ConnectorJSONArrayToJSONObject();
		}
		if (inParaType.equalsIgnoreCase("Array.String") && outParaType.equalsIgnoreCase("String")){
			return new ConnectorStringsToString();
		}
		return null;
	}

	@Override
	public Object createObject(String type, Object value) {
		if (null == type || type.isEmpty() || null == value){
			return null;
		}
		if (value instanceof String){
			return myCreateObject(type, (String)value);
		}
		else{
			return myCreateObject(type, value);
		}		
	}
	
	public String objectToString(Object obj){
		String str = myObjectToString(obj);
		if (null != str){
			return str;
		}
		return null;
	}

	@Override
	public IProcess createProcess(String name) {
		return myCreateProcess(name);
	}
	
	public IProcess createProcess(String type,Object data){
		if (null == type || type.isEmpty()){
			return null;
		}
		IProcess p = null;
		if (type.equalsIgnoreCase("iterator")){
			p = new ProcessIterator();
			p.setInputValue("group", data);
		}
		
		return p;
	}
	
	private IProcess myCreateProcess(String name) {
		if (null == name || name.isEmpty()){
			return null;
		}
			
		if (name.equalsIgnoreCase("JsonObjectCombiner")){
			return new JsonObjectCombiner();
		}
		if (name.equalsIgnoreCase("TwoJsonObjectCombiner")){
			return new TwoJsonObjectCombiner();
		}
		if (name.equalsIgnoreCase("Mellisuga.ModelRunner")){
			return new ModelRunner();
		}
		return null;
	}

	@Override
	public Date date() {
		return null;
	}

	@Override
	public String guid() {
		return null;
	}

	@Override
	public String[] objectTypes() {
		String[] _objectTypes = new String[3];		
		_objectTypes[0] = "JSON";
		_objectTypes[1] = "JSONObject";
		_objectTypes[2] = "JSONArray";
		return _objectTypes;
	}

	@Override
	public String[] processes() {
		return myProcesses();
	}
	private String[] myProcesses() {
		if (null == _proceses)
		{
			_proceses = new ArrayList<String>();
			_proceses.add("JsonObjectCombiner");
			_proceses.add("TwoJsonObjectCombiner");		
			_proceses.add("Mellisuga.ModelRunner");
			_proceses.add("iterator");
		}
		
		if (null != _proceses)
		{
			//转换成数组   
			String[] arrString = new String[_proceses.size()];   
			for( int i = 0 ; i < _proceses.size() ; i ++ ){   
				arrString[i] = (String)(_proceses.get(i));   
			}
			return arrString;
		}  

		return null;
	}

	@Override
	public String version() {
		return null;
	}

	public void release(){		
	}
		
	public void loaddata(){
		
	}
	
	public void unloaddata(){
		
	}
	
	private Object myCreateObject(String type, String value) {
		try{
			if (type.equalsIgnoreCase("JSON") || type.equalsIgnoreCase("JSONObject")){
				if (value.isEmpty()){
					return null;
				}
				return com.alibaba.fastjson.JSON.parseObject(value);			
			}	
			if(type.equalsIgnoreCase("JSONArray")){
				if (value.isEmpty()){
					return null;
				}
				return com.alibaba.fastjson.JSON.parseArray(value);
			}
		}
		catch(Exception ex){			
		}
		return null;
	}

	private Object myCreateObject(String type, Object value) {
		try{
			if (type.equalsIgnoreCase("JSON") || type.equalsIgnoreCase("JSONObject")){
				if (value instanceof com.alibaba.fastjson.JSONObject){
					return value;
				}				
				return com.alibaba.fastjson.JSON.toJSON(value);
			}	
			if (type.equalsIgnoreCase("JSONArray")){
				if (value instanceof com.alibaba.fastjson.JSONArray){
					return value;
				}				
				return com.alibaba.fastjson.JSON.parseArray((String)value);			
			}	
		}
		catch(Exception ex){			
		}
		return null;
	}
	
	private String myObjectToString(Object obj){
		if (obj instanceof com.alibaba.fastjson.JSONObject){
			com.alibaba.fastjson.JSONObject jo = (com.alibaba.fastjson.JSONObject)obj;
			return jo.toString();
		}
		
		if (obj instanceof com.alibaba.fastjson.JSONArray){
			com.alibaba.fastjson.JSONArray jo = (com.alibaba.fastjson.JSONArray)obj;
			return jo.toString();
		}
		return null;
	}
}
