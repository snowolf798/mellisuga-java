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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mellisuga.designpattern.IConnectableObject;
import com.mellisuga.processing.Connectors;
import com.mellisuga.processing.IConnector;
import com.mellisuga.processing.IProcess;
import com.mellisuga.processing.IRunningLogger;
import com.mellisuga.processing.LoggerFactory;
import com.mellisuga.processing.ParaItem;

public class JsonObjectCombiner implements IProcess {
	private static IRunningLogger log = LoggerFactory.CreateLogger();
	
	private String _guid = "JsonObjectCombiner";
	private int _id;
	private String _title = "Json组合器";
	private String _abstracts = "给定一个雷达文件，以及行政区划，叠加生成可能降雨的区域列表";
	ArrayList<IConnector> _incoming = new ArrayList<IConnector>();
	ArrayList<String> _incomingKeys = new ArrayList<String>();
	private Map<String, IConnector> _dictionaryOutgoing = new HashMap<String, IConnector>();
	private Map<String, Object> _exports = new HashMap<String, Object>();
	ArrayList<Object> _imports = new ArrayList<Object>();
	ArrayList<String> _importsKeys = new ArrayList<String>();
	
	public JsonObjectCombiner(){		
	}
	
	public void setGuid(String value){
		this._guid = value;
	}
    public String getGuid(){
    	return this._guid;
    }
    
    /**
	 * <p>
	 * 获取或设置数据处理类对象的 ID 号。数据处理类对象的 ID 号，用来唯一标识数据处理类对象。当一个数据处理流程中存在一个数据处理类的多个实例，ID 唯一标识一个数据处理类对象，从而避免冲突。
     * 当通过数据流程建模管理器来创建数据处理类对象时，将自动为该数据处理类对象的 ID 赋值，并且建议用户最好不要修改由数据流程建模管理器生成的数据处理类对象的 ID 号。
     * </p>
	 */
	public void setid(int value){
		_id = value;
	}	
    
	public int getid(){
		return _id;
	}
	
	public String name(){
		return "JsonObjectCombiner";
	}
	
	/**
	 * <p>
	 * 获取数据处理类的标题。数据处理类的标题用来设置数据处理的简单描述信息，用户可以根据本地语言的不同来为数据处理添加描述信息。
     * 如果该数据处理类作为具有某种数据处理操作功能的数据处理被加载到数据流程建模工具的数据处理列表中，则列表中所显示的数据处理的名字为数据处理类的标题。
     * </p>
	 */
	public String getTitle(){
		return this._title;
	}
	
	/**
	 * <p>
	 * 设置数据处理类的标题。数据处理类的标题用来设置数据处理的简单描述信息，用户可以根据本地语言的不同来为数据处理添加描述信息。
	 * </p>
	 * 
	 * <p>
	 * 如果该数据处理类作为具有某种数据处理操作功能的数据处理被加载到数据流程建模工具的数据处理列表中，则列表中所显示的数据处理的名字为数据处理类的标题。
	 * </p>
	 * 
     * @param caption 指定的数据处理类的标题内容。
     * @see 
	 */
	public void setTitle(String title){
		this._title = title;
	}
	
	/**
	 * <p>
	 * 获取或设置数据处理类对象的详细、完整的描述信息。
	 * </p>
	 */
	public String getAbstracts(){
		return this._abstracts;
	}

	public void setAbstracts(String abstracts){
		this._abstracts = abstracts;
	}
	
	/**
	 * <p>
	 * 获取数据处理类对象的所有输入参数的定义，包括：数据处理类对象所能接收的输入参数的参数名称、参数类型等信息。
     * 数据处理可以接收或者数据处理能够正常执行所需要的所有参数列表可以通过该属性来获取，该属性的值为一个 <c>ParaItem</c> 对象数组，每一个 <c>ParaItem</c> 对象对应数据处理的一个输入参数的定义，包括参数的名称、类型、是否为可选参数等信息。
     * 数据处理输入参数的值的设置是通过"ProcessBase.SetInputValue()"方法设置的，在该方法中实现了检查参数赋值的有效性，即检查对数据处理输入参数的设置是否正确，因此，可以通过 <c>InputParameters</c> 属性获取数据处理本身定义的参数列表，检查用户设置的参数是否存在于数据处理所定义的参数列表中，以及所设置的参数值的类型是否与参数列表中定义的该参数的类型一致，如果不一致，则输入参数的赋值失败。
     * </p>
	 */
	public ParaItem[] inputSet(){
		try{
			ParaItem[] ipis = new ParaItem[1];
			ParaItem pi = new ParaItem("Any", "Any", "Any", true, "Any");
			ipis[0] = pi;				
			return ipis;
		}
		catch(Exception ex){
			log.error(ex.getMessage());			
		}
		return null;
	}
	
	/**
	 * <p>
	 * 获取数据处理执行结果的输出参数的定义。数据处理执行完成后，外部所能获取到的结果参数的参数名、参数类型等信息可以通过该属性来获取。
     * 数据处理执行完成后，外部所能获取的输出参数列表可以通过该属性来获取，该属性的值为一个"ParaItem"对象数组，每一个"ParaItem"对象对应数据处理的一个结果参数的定义，包括结果参数的名称、类型等信息。
     * 通过"ProcessBase.GetOutputValue()"方法可以获取数据处理的指定输出参数的数据内容。
     * </p>
	 */
	public ParaItem[] outputSet(){
		try{
			ParaItem[] opis = new ParaItem[1];	
			ParaItem pi = new ParaItem("JSONObject", "JSONObject", "Any", true, "Any");
			opis[0] = pi;			
			return opis;
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		return null;		
	}
	
	/**
     * <p>
     * 关联的输入连接对象
     * </p>
     */
	public IConnectableObject getInputConnectableObject(){
		return new Connectors(this._incoming);
	}
	public void setInputConnectableObject(IConnectableObject value){
		//不需要实现
	}

    /**
     * <p>
     * 关联的输出连接对象
     * </p>
     */
    public IConnectableObject getOutputConnectableObject(){
        return new Connectors(this._dictionaryOutgoing.values());
    }
    public void setOutputConnectableObject(IConnectableObject value){
		//不需要实现
    }

	public boolean isReady(){
		if (this._incoming.size() > 0 && this._imports.size() > 0){
        	return true;
        }
		return false;
	}
	
	/**
	 * <p>
	 * 将数据处理的指定输入参数与指定的连接器建立连接关系。
	 * </p>
	 * 
	 * <p>
	 * 连接器可以形象地理解为连接两个数据处理的数据通道，数据处理的输出数据可以通过与该数据处理连接的连接器，传递到连接器另一端所连接的数据处理，作为这个数据处理的输入数据。因此，连接器可以承载数据，同时连接着具有数据联系的数据处理，并负责数据的传递以及必要的数据类型的转换。
     * 一个数据处理具有多个输入参数和输出参数，但是一个连接器只能对应两个数据处理间的一对参数关系，因此，要建立两个数据处理间的多对参数对应关系就需要多个连接器来连接。</para>
     * 该方法就是将指定的连接器与该数据处理器特定的输入参数绑定，那么该数据处理的这个输入参数就可以从与其连接的连接器中获取输入数据。
	 * </p>
	 * 
     * @param parameter 指定的数据处理的输入参数的参数名称。
     * @param connector 指定的连接器，即"IConnector"对象。
     * @return 如果指定的连接器与数据处理的指定参数成功建立连接关系，则返回 true；否则，返回 false。
     * @see 
	 */
	public boolean setInputConnector(String parameter, IConnector connector){
		try{
			this._incoming.add(connector);
			this._incomingKeys.add(parameter);
			return true;
		}
		catch (Exception ex){
			log.error(ex.getMessage());
		}
		return false;
	}
	
	/**
	 * <p>
	 * 将数据处理的指定输出参数与指定的连接器建立连接关系。
	 * </p>
	 * 
	 * <p>
	 * 连接器可以形象地理解为连接两个数据处理的数据通道，数据处理的输出数据可以通过与该数据处理连接的连接器，传递到连接器另一端所连接的数据处理，作为这个数据处理的输入数据。因此，连接器可以承载数据，同时连接着具有数据联系的数据处理，并负责数据的传递以及必要的数据类型的转换。
     * 一个数据处理具有多个输入参数和输出参数，但是一个连接器只能对应两个数据处理间的一对参数关系，因此，要建立两个数据处理间的多对参数对应关系就需要多个连接器来连接。
     * 该方法就是将指定的连接器与该数据处理器特定的输出参数绑定，用来接收数据处理的输出数据，进而可以通过连接器传递出去。
	 * </p>
	 * 
     * @param parameter 指定的数据处理的输出参数的参数名称。
     * @param connector 指定的连接器，即"IConnector"对象。
     * @return 如果指定的连接器与数据处理的指定参数成功建立连接关系，则返回 true；否则，返回 false。
     * @see
	 */
	public boolean setOutputConnector(String parameter, IConnector connector){
		try{
			this._dictionaryOutgoing.put(parameter, connector);
			return true;
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		return false;
	}
	
	/**
	 * <p>
	 * 为数据处理指定的输入参数设置参数值。
	 * </p>
	 * 
     * @param parameter 指定的要设置参数值的数据处理输入参数的参数名称。
     * @param value 指定的参数值。
     * @return 如果参数值设置成功，返回 true；否则，返回 false。
     * 数据处理输入参数的值的设置是通过"Process.SetInputValue()"方法设置的，在该方法中实现了检查参数赋值的有效性，检查用户设置的参数是否存在于数据处理所定义的参数列表中，以及所设置的参数值的类型是否与参数列表中定义的该参数的类型一致，如果不一致，则输入参数值设置失败，即"Process.SetInputValue()"方法返回值为 false。
	 */
	public boolean setInputValue(String parameter, Object value){
		try{
			this._imports.add(value);
			this._importsKeys.add(parameter);
			return true;
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		return false;
	}
	
	/**
	 * <p>
	 * 返回数据处理指定输出参数的参数值，即数据处理执行后，该输出参数对应的执行结果数据。
	 * </p>
	 * 
	 * @param parameter 指定的数据处理输出参数的参数名称。
	 * @return 返回数据处理执行后，指定输出参数输出的结果数据。
	 * 
	 */
	public Object getOutputValue(String parameter){
		try{
	        return this._exports.get(parameter);
        }
        catch(Exception e){     
        	log.error(e.getMessage());
        }
        return null;
	}
	
	
	public boolean execute() {
		try{
			com.alibaba.fastjson.JSONObject jso = new com.alibaba.fastjson.JSONObject();	
			Object obj = null;
			for (int i = 0; i < _imports.size(); ++i){
				obj = this._imports.get(i);
				if (obj instanceof com.alibaba.fastjson.JSONObject){
					jso = combiner(jso, (com.alibaba.fastjson.JSONObject)obj);
				}
				else if (obj instanceof com.alibaba.fastjson.JSONArray){
					jso.put(this._importsKeys.get(i), obj);
				}				
			}
			
			IConnector connector = null;
			for (int i = 0; i < this._incoming.size(); ++i){
				connector = this._incoming.get(i);
				obj = connector.pop();
				if (obj instanceof com.alibaba.fastjson.JSONObject){
					jso = combiner(jso, (com.alibaba.fastjson.JSONObject)obj);
				}
				else if (obj instanceof com.alibaba.fastjson.JSONArray){
					jso.put(this._incomingKeys.get(i), obj);
				}					
			}
			if (this._dictionaryOutgoing.containsKey("EarlyWarning")){
				connector  = this._dictionaryOutgoing.get("EarlyWarning");
				connector.push(jso);
			}
			else{
				this._exports.put("EarlyWarning", jso);
			}			
			return true;
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
		return false;
	}

	@Override
	public IProcess clone() {
		return null;
	}
	
	public void setData(Object obj){
		
	}
	
	public Object getData(){
		return null;
	}

	private com.alibaba.fastjson.JSONObject combiner(com.alibaba.fastjson.JSONObject js, com.alibaba.fastjson.JSONObject append){
		if (null == append || append.size() <= 0){
			return js;
		}
		try{
			Set<String> keys = append.keySet();
			Iterator<String> it = keys.iterator();
			String name = "";
			while (it.hasNext()){
				name = it.next();
				js.put(name, append.get(name));				
			}	
		}
		catch(Exception ex){			
		}
		return js;
	}
}
