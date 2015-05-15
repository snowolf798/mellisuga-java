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

import com.mellisuga.designpattern.IConnectableObject;
import com.mellisuga.processing.IConnector;

public class ConnectorJSONArrayToJSONObject implements IConnector {

	private String _guid = "";
	private int _id = 0;
	private com.alibaba.fastjson.JSONArray jsa = null;
	
	public void setid(int value) {
		_id = value;
	}

	public int getid() {
		return _id;
	}

	public String inputParaType() {
		return "JSONArray";
	}

	public String outputParaType() {
		return "JSONObject";
	}
	
	public Object pop() {
		try{
			if (null == jsa || jsa.size() <= 0){
				return null;
			}
			return jsa.get(0);
		}
		catch(Exception ex){			
		}
		return null;
	}

	public void push(Object value) {
		jsa = (com.alibaba.fastjson.JSONArray)value;
	}

	public String getGuid() {
		return _guid;
	}

	public void setGuid(String value) {
		_guid = value;
	}

	public IConnectableObject getInputConnectableObject() {
		return null;
	}

	public void setInputConnectableObject(IConnectableObject value) {

	}

	public IConnectableObject getOutputConnectableObject() {
		return null;
	}

	public void setOutputConnectableObject(IConnectableObject value) {

	}	

	public IConnector clone(){
		return null;
	}
}
