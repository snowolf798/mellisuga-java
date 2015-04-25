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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mellisuga.designpattern.IConnectableObject;
import com.mellisuga.util.LicenseProvider;

/** 
 * 数据处理构件类。
 * 
 * <p>用户可以通过继承该类，实现自己的数据处理。
 * 用户自己实现的数据处理必须实现{@linkplain com.mellisuga.processing.IProcess IProcess}接口或者继承{@linkplain com.mellisuga.processing.ProcessBase ProcessBase}类，{@linkplain com.mellisuga.processing.ProcessBase ProcessBase}类已经对{@linkplain com.mellisuga.processing.IProcess IProcess}接口进行了一定程度的实现，因此用户的数据处理类继承自{@linkplain com.mellisuga.processing.ProcessBase ProcessBase}类，可以节省部分开发工作。<br>
 * 
 * <p>数据处理是数据流程建模的核心，是数据流程建模结果————模型的基本数据处理单元。数据处理为一个相对独立的、一般不可再分的数据处理操作，即对数据进行处理的操作，数据处理一般包含若干个输入数据、对输入数据的处理以及若干个处理结果————输出数据。因此，数据处理类就代表了这个具有某种数据处理操作功能的数据处理，数据处理类提供了设置输入数据的接口，对输入数据进行处理的接口以及输出数据处理结果的接口。<br>
 * <p>实现{@linkplain com.mellisuga.processing.IProcess IProcess}接口或者继承{@linkplain com.mellisuga.processing.ProcessBase ProcessBase}类统称为数据处理构件类，数据处理构件类表示具有某种数据处理功能的数据处理，数据处理构件类对象可以理解为该数据处理类所具有的数据处理功能的一个执行实例，同一个数据处理构件类对象的数据处理功能相同，但是对这些数据处理类对象如果设置了不同的输入数据，执行结果也不同。设置了数据处理构件类对象的输入数据和其他相关参数后，即可调用数据处理构件类对象的执行函数，执行对输入数据的处理操作，进而获得处理结果数据。一个模型中可以包含多个数据处理构件类对象，并且可以包含一个数据处理构件类的多个实例，即该模型所定义的数据处理流程中存在多个这样的数据处理操作任务。<br>
 * <p>当将多个具有不同数据处理功能的数据处理构件按照某种逻辑顺序顺次执行，即构成一个执行队列，甚至这些数据处理构件具有一定的关联关系，即某个数据处理构件的处理结果数据将作为下一个数据处理的输入数据，即构成一个数据处理的工作流，实现数据处理的流程化、自动化。<br>
 * <p>数据流程建模是一个过程，首先，获取可以解决一项问题的若干个数据处理构件；其次，编排这些数据处理构件的执行逻辑，即执行顺序；最后，正确设置数据处理构件的参数以及数据处理构件间的参数传递关系；以上这一过程称为数据流程建模，数据流程建模的结果为模型。一个模型中可以包含一个数据处理构件，即该模型只具有简单的一项数据处理功能，而多个数据处理构件构成的模型也可以作为具有特殊的、复杂数据处理功能的数据处理，嵌入到其他模型中，因此，数据处理构件和模型没有明确的界定，即一个数据处理构件可以看作一个简单模型，模型也可以看做特殊的数据处理构件。<br>
 * <p>数据流程建模是构建一个数据处理加工的流程，而数据处理构件就是具有数据加工能力的"工厂"，是数据流程建模的主体，因此，在开始进行数据流程建模时，首先要准备好模型中所需要的数据处理功能，即数据处理构件。每一个数据处理构件都可以单独执行，而数据流程建模的结果模型的执行，实质就是按照模型所构建数据处理的有序执行队列，顺次执行每一个数据处理构件。<br>
 * <p>另外，数据处理构件表示具有特定数据处理功能的数据处理单元，这里的数据处理构件不仅可以处理普通数据，还可以处理业务应用的数据，此时，数据流程建模过程为构建业务流程的过程，数据流程建模的结果为某个业务应用系统，而模型中的数据处理为业务流程中的业务处理单元。文档中所提到的所有数据处理为 普通数据处理以及业务数据处理的总称。<br>
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:10:07
 */
public abstract class ProcessBase implements IProcess {

	private String _guid = "";
	private String _name = "";
	private String _title = "";
	private String _abstracts = "";
	private Map<String, IConnector> _dictionaryIncoming = new HashMap<String, IConnector>();
	private List<String> _outgoingKeys = new ArrayList<String>();
	private List<IConnector> _outgoingConnectors = new ArrayList<IConnector>();
	private Map<String, Object> _exports = new HashMap<String, Object>();
	private int _id;
	private Map<String, Object> _imports = new HashMap<String, Object>();	
	private List<ParaItem> _parametersInput = new ArrayList<ParaItem>();
	private List<ParaItem> _parametersOutput = new ArrayList<ParaItem>();	
	private Object _obj = null;

	/**
	 * 默认构造函数
	 */
	public ProcessBase(){

	}
	
	public void setGuid(String value){
		this._guid = value;
	}
    public String getGuid(){
    	return this._guid;
    }
    
	public void setid(int value){
		_id = value;
	}	
    
	public int getid(){
		return _id;
	}

	public String name(){
		return this._name;
	}

	public String getTitle(){
		return this._title;
	}
	
	public void setTitle(String title){
		this._title = title;
	}

	public String getAbstracts(){
		return this._abstracts;
	}

	public void setAbstracts(String abstracts){
		this._abstracts = abstracts;
	}
	
	public ParaItem[] inputSet(){
		try{
			return listToParaItems(this._parametersInput);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public ParaItem[] outputSet(){
		try{
			return listToParaItems(this._parametersOutput);
		}
		catch(Exception ex){			
			ex.printStackTrace();
		}
		return null;		
	}

	public IConnectableObject getInputConnectableObject(){
		return new Connectors(this._dictionaryIncoming.values());
	}
	public void setInputConnectableObject(IConnectableObject value){
	}

    public IConnectableObject getOutputConnectableObject(){
        return new Connectors(this._outgoingConnectors);
    }
    public void setOutputConnectableObject(IConnectableObject value){
    }

	public boolean setInputConnector(String parameter, IConnector connector){
		try{
			this._dictionaryIncoming.put(parameter, connector);
			return true;
		}
		catch (Exception ex){			
			ex.printStackTrace();
		}
		return false;
	}
	
	public IConnector getInputConnector(String parameter){
		try{
	        return this._dictionaryIncoming.get(parameter);
        }
        catch(Exception e){        	
            e.printStackTrace();
        }
        return null;
	}

	public boolean setOutputConnector(String parameter, IConnector connector){
		try{
			this._outgoingKeys.add(parameter);
			this._outgoingConnectors.add(connector);
			return true;
		}
		catch(Exception ex){			
			ex.printStackTrace();
		}
		return false;
	}
	
	public IConnector[] getOutputConnector(String parameter){
		try{
			ArrayList<IConnector> cs = new ArrayList<IConnector>();
			int i = 0;
			String str = "";
			for (i = 0; i < _outgoingKeys.size() && i < _outgoingConnectors .size(); ++i){
				str = _outgoingKeys.get(i);
				if (null == str || str.isEmpty()){
					continue;
				}
				if (str.equalsIgnoreCase(parameter)){
					cs.add(_outgoingConnectors.get(i));
				}
			}
			return com.mellisuga.util.Toolkit.arrayToConnectors(cs);
        }
        catch(Exception e){		        
        	e.printStackTrace();
        }
        return null;
	}

	public boolean setInputValue(String parameter, Object value){
		try{
			this._imports.put(parameter, value);
			return true;
		}
		catch(Exception ex){			
			ex.printStackTrace();
		}
		return false;
	}

	public Object getInputValue(String parameter){
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}
		try{
	        return this._imports.get(parameter);
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return null;
	}

	public Object getOutputValue(String parameter){
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}
		try{
			if (parameter.equalsIgnoreCase("all")){
				com.alibaba.fastjson.JSONObject js_all = new com.alibaba.fastjson.JSONObject();
				Set<String> keys = this._exports.keySet();
				java.util.Iterator<String> it = keys.iterator();
				String name = "";
				while (it.hasNext()){
					name = it.next();
					js_all.put(name, this._exports.get(name));
				}
				return js_all;
			}
	        return this._exports.get(parameter);
        }
        catch(Exception e){     
        	e.printStackTrace();
        }
        return null;
	}


	public boolean isReady(){
		return myIsReady();
	}
	private boolean myIsReady(){
		if (!LicenseProvider.getInstance().validate("", "")){
			return false;
		}
		try{
			ParaItem[] paras = this.inputSet();
	        String name = "";
	        int i = 0;
	        ParaItem para = null;
	        for (i = 0; i < paras.length; ++i){
	        	para = paras[i];
		    	if (!para.getOption()){
		    		name = para.getName();
			        if (this._dictionaryIncoming.containsKey(name) && this._imports.containsKey(name)){
			        	return false;
			        }
			        if (this._dictionaryIncoming.containsKey(name)){
				        continue;
			        }
			        if (!this._imports.containsKey(name)){
				        return false;
			        }
		        }
	        }
	
	        return true;
        }
        catch(Exception e){        	
        	e.printStackTrace();            
        }            
        return false;
	}

	public abstract boolean execute();

	public abstract IProcess clone();

	@Override
	public void setData(Object obj){	
		_obj = obj;
	}
	
	@Override
	public Object getData(){
		return _obj;
	}
	
	/**
	 * 设置数据处理构件的唯一标识名称。
	 * 
	 * <p>当通过建模管理器来创建数据处理构件对象时，建模管理器将根据该名称来创建由该名称唯一标识的数据处理构件类的一个新实例（实际上，数据处理构件的创建是由建模工厂来创建的）。<br>
	 * 
	 * @param name 指定的数据处理构件类的唯一标识名称。       
	 */
	protected void setName(String name){
		this._name = name;
	}
	
	/**
	 * 设置数据处理构件的所有输入参数的定义。<br>
	 * <p>数据处理构件所能接收的输入参数的参数名称、参数类型等信息是通过该方法来定义的。<br>
	 * @param items 定义的数据处理输入参数的参数列表，为一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象数组，每一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象对应数据处理构件的一个输入参数的定义，包括参数的名称、类型、是否为可选参数等信息。
	 */
	protected void setInputParameters(ParaItem[] items){
		try{
			if (null == items){
				System.out.println("PMDispatcherImpl.setInputParameters:杈撳叆鍙傛暟涓虹┖");
				return;
			}
			
			this._parametersInput.clear();
			int i = 0;
			for (i = 0; i < items.length; ++i){
				this._parametersInput.add(items[i]);		
			}		
		}
		catch(Exception ex){
			System.out.println("PMDispatcherImpl.setInputParameters:" + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * 设置数据处理构件执行结果的输出参数的定义。
	 * <p>数据处理构件执行完成后，外部所能获取到的结果参数的参数名、参数类型等信息是通过该方法来定义的。<br>
	 *         
	 * @param items 定义的数据处理构件输出参数的参数列表，为一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象数组，每一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象对应数据处理构件的一个输出参数的定义，包括输出参数的名称、类型等信息。
	 */
	protected void setOutputParameters(ParaItem[] items){
		try{
			if (null == items){				
				return;
			}
			this._parametersOutput.clear();
			
			int i = 0;
			for (i = 0; i < items.length; ++i){
				this._parametersOutput.add(items[i]);		
			}		
		}
		catch(Exception ex){			
			ex.printStackTrace();
		}
	}

	/**
	 * 将数据处理构件执行的结果数据赋值给相应的输出参数。<br>
	 * <p>外部用户可以根据具体的输入参数名称获取数据处理构件的执行结果。<br>
	 *       
	 * @param parameter 指定的数据处理构件的输出参数的参数名称。
	 * @param value 指定的参数的值。
	 */
	protected void setOutputValue(String parameter, Object value){
		try{
			this._exports.put(parameter, value);
		}
		catch(Exception ex){			
			ex.printStackTrace();
		}
	}

	/**
	 * 克隆
	 * @param process 克隆对象
	 */
	protected void baseClone(ProcessBase process){
		myBaseClone(process);
	}
	private void myBaseClone(ProcessBase process){
		try{
			this._guid = process._guid;
			this._id = process._id;
	        this._name = process._name;
	        this._title = process._title;
	        this._abstracts = process._abstracts;
	        
	        int k = 0;        
	        IConnector connector = null;
	        String key = "";
	        if (null != process._dictionaryIncoming){
	        	Set<String> keys = process._dictionaryIncoming.keySet();
	            for (k = 0; k < keys.size(); ++k){
			        connector = process._dictionaryIncoming.get(keys.iterator());
			        if (null == connector){
			        	continue;
			        }
			        this._dictionaryIncoming.put(key, connector.clone());
		        }
	        }
	
	        if (null != process._outgoingKeys && null != process._outgoingConnectors){  
	        	this._outgoingKeys.addAll(process._outgoingKeys);
	        	this._outgoingConnectors.addAll(process._outgoingConnectors);	        	
	        }
	
	        if (null != process._exports){
	        	Set<String> keys = process._exports.keySet();
	        	for (k = 0; k < keys.size(); ++k){
			        this._exports.put(key, process._exports.get(key));
		        }
	        }
	
	        if (null != process._imports){
	        	Set<String> keys = process._imports.keySet();
	        	for (k = 0; k < keys.size(); ++k){
			        this._imports.put(key, process._imports.get(key));
		        }
	        }
	
	        if (null != process._parametersInput){
		        this._parametersInput = new ArrayList<ParaItem>(process._parametersInput);		        
	        }
	
	        if (null != process._parametersOutput){
		        this._parametersOutput = new ArrayList<ParaItem>(process._parametersOutput);		       
	        }
		}
		catch(Exception e){	
			e.printStackTrace();
        }
	}
	
	/**
	 * 根据参数关键词获取输入参数
	 * @param parameter 参数关键标识
	 * @return 输入参数
	 */
	protected Object getInput(String parameter){
		try{
			IConnector c = this._dictionaryIncoming.get(parameter);
			Object o = null;
			if (null != c){
				o = c.pop();
			}
			else{
				o = this._imports.get(parameter);				
			}
			return o;
        }
        catch(Exception e){	
        	e.printStackTrace();
        }
        return null;
	}
    /**
     * 设置输出参数
     * @param parameter 参数关键标识
     * @param obj 输出参数
     */
	protected void setOutput(String parameter, Object obj){
		try{
			if (null == parameter || parameter.isEmpty() || null == obj){
				return;
			}
			int i = 0;
			String str = null;
			IConnector c = null;
			IConnector call = null;
			ArrayList<IConnector> calls = new ArrayList<IConnector>();
			boolean isFind = false;
			for (i = 0; i < this._outgoingKeys.size() && i < this._outgoingConnectors.size(); ++i){
				str = this._outgoingKeys.get(i);
				if (parameter.equalsIgnoreCase(str)){
					c = this._outgoingConnectors.get(i);
					if (null != c){
						isFind = true;
						c.push(obj);						
					}
				}
				else if (str.equalsIgnoreCase("all")){
					call = this._outgoingConnectors.get(i);
					if (null != call){
						calls.add(call);						
					}
				}
			}
			
			if (parameter.equalsIgnoreCase("all")){
				js_all.put(parameter, obj);
				for (i = 0; i < calls.size(); ++i){
					call = calls.get(i);
					call.push(js_all);
				}
			}
			
			if (!isFind){
				this._exports.put(parameter, obj);
			}			
        }
        catch(Exception e){	
        	e.printStackTrace();
        }        
	}
	
	private ParaItem[] listToParaItems(List<ParaItem> list){
		try{
			ParaItem[] pis = new ParaItem[list.size()];
			list.toArray(pis);
			return pis;
		}
		catch(Exception e){	        	
        }   
		return null;
	}
	
	private com.alibaba.fastjson.JSONObject js_all = new com.alibaba.fastjson.JSONObject();
}