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
package com.mellisuga.core;

import java.text.DateFormat;
import java.util.ArrayList;

import com.mellisuga.processing.ConveyConnector;
import com.mellisuga.processing.GroupDescription;
import com.mellisuga.processing.IConnector;
import com.mellisuga.processing.IPMDispatcher;
import com.mellisuga.processing.IPMParser;
import com.mellisuga.processing.IProcess;
import com.mellisuga.processing.IProcessingManager;
import com.mellisuga.util.LicenseProvider;

/**
 * 流程管理器 实例
 * @version 1.0
 * @created 10-1-2008 21:08:03
 *
 */
public class PManagerImpl implements IProcessingManager {
		
	public IConnector connectorConvey() {
		return new ConveyConnector();
	}

	public IConnector createConnector(String inParaType, String outParaType) {
		try{
			return PManagerImpl.manager().createConnector(inParaType, outParaType);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Object createObject(String type, Object value) {		
		try{
			if (null == type || type.isEmpty() || null == value){
				return null;
			}
			String[] stypes = type.split(";");
			for (int i = 0; i < stypes.length; ++i){
				if (value instanceof String){
					String str = (String)value;
					if (str.isEmpty()){
						return null;
					}
					
					Object obj = myCreateObject(stypes[i], str);
					if (null != obj){
						return obj;
					}
				}
				
				Object obj = PManagerImpl.manager().createObject(stypes[i], value);
				if (null != obj){
					return obj;
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public String objectToString(Object obj){
		try{
			return PManagerImpl.manager().objectToString(obj);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public IProcess createProcess(String name) {
		try{			
			return PManagerImpl.manager().createProcess(name);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public IProcess createProcess(String type,Object data){
		try{			
			return PManagerImpl.manager().createProcess(type,data);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public IPMDispatcher newDispatcher() {
		try{
			if (!LicenseProvider.getInstance().validate("", "")){
				return null;
			}
			return PManagerImpl.manager().newDispatcher();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public IPMParser newParser() {
		try{
			return PManagerImpl.manager().newParser();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public boolean register(String factoryName) {		
		return false;
	}

	public boolean register(String[] factoryNames) {		
		return false;
	}

	public boolean unregister(String factoryName) {
		return false;
	}

	public void release(){
		PManagerImpl.manager().release();
	}
	
	public void loaddata(){
		PManagerImpl.manager().loaddata();
	}
	
	public void unloaddata(){
		PManagerImpl.manager().unloaddata();
	}
	
	private static IProcessingManager manager(){
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}
		return InstanceManager.getInstance().getApplication().processingManager();
	}
	
	private Object myCreateObject(String type, String str) {
		try{
			if (type.equalsIgnoreCase("string")){
				return str;
			}
			if (type.equalsIgnoreCase("number")){
				return (Number)(Integer.parseInt(str));
			}
			if (type.equalsIgnoreCase("int")){
				return Integer.parseInt(str);
			}
			if (type.equalsIgnoreCase("float")){
				return Float.parseFloat(str);
			}
			if (type.equalsIgnoreCase("double")){
				return Double.parseDouble(str);
			}
			if (type.equalsIgnoreCase("boolean") || type.equalsIgnoreCase("bool")){
				return Boolean.parseBoolean(str);
			}
			if (type.equalsIgnoreCase("date")){
				return DateFormat.getDateInstance().parse(str);
			}
			if (type.equalsIgnoreCase("StringArray") || type.equalsIgnoreCase("Array.String")){			
				ArrayList<String> strs= new ArrayList<String>();
				int indexStart = 0;
				int pos = str.indexOf(";", indexStart);
				String s = null;
				while (-1 != pos){
					s = str.substring(indexStart,pos);
					s.trim();
					strs.add(s);
					indexStart = pos+1;
					pos = str.indexOf(";", indexStart);
				}
				s = str.substring(indexStart,str.length());
				s.trim();
				strs.add(s);
				
				String[] values = new String[strs.size()];
				strs.toArray(values);
				return values;
			}
		}
		catch(Exception ex){
			
		}
		return null;
	}
	
	
}
