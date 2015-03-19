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
	
	@Override
	public void setid(int value) {
		_id = value;
	}

	@Override
	public int getid() {
		return _id;
	}

	@Override
	public String inputParaType() {
		return "JSONArray";
	}

	@Override
	public String outputParaType() {
		return "JSONObject";
	}
	
	@Override
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

	@Override
	public void push(Object value) {
		jsa = (com.alibaba.fastjson.JSONArray)value;
	}

	@Override
	public String getGuid() {
		return _guid;
	}

	@Override
	public void setGuid(String value) {
		_guid = value;
	}

	@Override
	public IConnectableObject getInputConnectableObject() {
		return null;
	}

	@Override
	public void setInputConnectableObject(IConnectableObject value) {

	}

	@Override
	public IConnectableObject getOutputConnectableObject() {
		return null;
	}

	@Override
	public void setOutputConnectableObject(IConnectableObject value) {

	}	

	public IConnector clone(){
		return null;
	}
}
