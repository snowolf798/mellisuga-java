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

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.mellisuga.core.InstanceManager;
import com.mellisuga.designpattern.IConnectableObject;
import com.mellisuga.util.LicenseProvider;

/**
 * 数据处理链类。<br>
 * 
 * <p>数据处理链是若干个数据处理构件队列化执行的容器，在运行时，数据处理链负责数据处理链中所添加的数据处理构件名称，从建模管理器中查找数据处理，并按照顺序将其追加到数据处理队列中，并且，数据处理链负责数据处理队列的执行、中断与恢复等工作。<br>
 * <p>通过处理链，可以将数据处理按照其执行的逻辑顺序添加到数据处理链中，即构成数据处理构件执行队列，并且，可以对数据处理执行队列中的数据处理建立数据传递关系，即数据处理构件间的参数对应关系，这样，数据处理构件的输出数据就可以作为与其连接的数据处理构件执行时的输入数据，这样就真正实现了数据处理的流程化和自动化，处理链可以单独执行，其执行的效果为，处理链中所编排的数据处理队列，将按照其指定的顺序，顺次执行每一个数据处理构件，同时，完成必要的参数传递。<br>
 * <p>处理链类实现了{@linkplain com.mellisuga.processing.IProcessChain IProcessChain}接口，而{@linkplain com.mellisuga.processing.IProcessChain IProcessChain}接口又继承了{@linkplain com.mellisuga.processing.IProcess IProcess}接口。<br>
 * 从这方面来看，数据处理链也是一种模型，处理链也可以作为一种特殊的数据处理构件嵌入到其他模型中。<br>
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:09:41
 */
public class ProcessChainImpl implements IProcessChain {
	static IRunningLogger _logger = LoggerFactory.CreateLogger();
	static int _logLevel = 5;
	private static boolean _isLog4j = false;
	private String _title = "";
	private List<IProcess> _chainPool = new ArrayList<IProcess>();
	private String _abstracts = "";
	private int _id;
	private String _name = "Chain";

	/**
	 * 默认构造函数
	 */
	public ProcessChainImpl(){
		IContext context = InstanceManager.getInstance().getContext();
		if (null != context){
			_logLevel = (Integer)context.getVariable("logLevel");
			_isLog4j = (Boolean)context.getVariable("Log4j");
		}
	}

	public String getGuid(){
		return "";
	}
	public void setGuid(String value){
		
	}

	public IConnectableObject getInputConnectableObject(){
		return null;
	}
	public void setInputConnectableObject(IConnectableObject value){
		
	}

    public IConnectableObject getOutputConnectableObject(){
    	return null;
    }
    public void setOutputConnectableObject(IConnectableObject value){
    	
    }

	public IProcess addProcess(String name){		
		return myAddProcess(name, InstanceManager.getInstance().makeUniqueId());
	}
	
	public IProcess addProcess(String name, int id){
		return myAddProcess(name, id);
	}
	
	public IProcess addProcess(String name, int id,Object data){
		try{		
			if (!(data instanceof GroupDescription)){
				return null;
			}
			IProcessingManager _gpmanager = InstanceManager.getInstance().processingManager();
			if (null == _gpmanager){
				error("chain.add:管理器为空");
				return null;
			}
			IProcess p = _gpmanager.createProcess(name,data);
			if (null == p){
				error("chain.create:创建'" + name + "'失败");
				return null;
			}
			
			p.setid(id);
			_chainPool.add(p);
			return p;
		}
		catch(Exception e){
			error("chain.add: exception - " + e.getMessage());
		}
		return null;				
	}

	public boolean connectProcesses(IProcess prevProcess, IProcess backProcess, Map<String, String> keywords){
		return myConnectProcesses(prevProcess, backProcess, keywords);
	}
	
	public boolean connectProcesses(IProcess prevProcess, IProcess backProcess, String prevParaName, String backParaName){
		return myConnectProcesses(prevProcess, backProcess, prevParaName, backParaName);
	}

	public boolean connectProcesses(int prevID, int backID, String prevParaName, String backParaName){
		try{
			IProcess prevProcess = getProcessById(prevID);
			if (null == prevProcess){
				return false;
			}
			
			IProcess backProcess = getProcessById(backID);
			if (null == backProcess){
				return false;
			}
			
			return connectProcesses(prevProcess, backProcess, prevParaName, backParaName);
		}
		catch(Exception e){
			error(e.getMessage());
		}
		return false;
	}

	public boolean connectProcesses(int prevID, int backID, Map<String, String> keywords){
		try{			
			IProcess prevProcess = getProcessById(prevID);
			if (null == prevProcess){
				return false;
			}
			
			IProcess backProcess = getProcessById(backID);
			if (null == backProcess){
				return false;
			}
			
			return connectProcesses(prevProcess, backProcess, keywords);
		}
		catch(Exception e){
			error(e.getMessage());
		}
		
		return false;
	}

	public int count(){
		if (null == _chainPool)
		{
			return 0;
		}
		else{
			return _chainPool.size();
		}
	}
	
	public IProcess getProcessById(int id){
		try{
			IProcess p = null;
			int i = 0;
			for (; i < _chainPool.size(); ++i)
			{
				p = _chainPool.get(i);
				if (p.getid() == id)
				{
					return p;
				}
			}
		}
		catch(Exception e){
			error(e.getMessage());
		}
		return null;
	}
	
	
	/*
	 * IProcess接口实现
	 */
	
	public void setid(int value){
		_id = value;
	}
	
	public int getid(){
		return _id;
	}

	public String name(){
		return _name;
	}
	
	public String getTitle(){
		return _title;
	}
	
	public void setTitle(String value){
		_title = value;
	}
	
	public String getAbstracts(){
		return _abstracts;
	}
	
	public void setAbstracts(String value){
		_abstracts = value;
	}
	
	public IProcess clone(){
		return null;
	}

	public boolean execute(){
		return myExecute();
	}
	
	public void setData(Object obj){
		
	}

	public Object getOutputValue(String parameter){
		return null;
	}
	
	public ParaItem[] inputSet(){
		return null;
	}

	public boolean isReady(){
		return myIsReady();
	}
	
	public ParaItem[] outputSet(){
		return null;
	}

	public boolean setInputConnector(String parameter, IConnector connector){
		return false;
	}

	public boolean setInputValue(String parameter, Object value){
		return false;
	}

	public boolean setOutputConnector(String parameter, IConnector connector){
		return false;
	}

	private IProcess myAddProcess(String name, int id){
		try{			
			IProcessingManager _gpmanager = InstanceManager.getInstance().processingManager();
			if (null == _gpmanager){
				error("chain.add:管理器为空");
				return null;
			}
			IProcess p = _gpmanager.createProcess(name);
			if (null == p){
				error("chain.create:创建'" + name + "'失败");
				return null;
			}
			
			p.setid(id);
			_chainPool.add(p);
			return p;
		}
		catch(Exception e){
			error("chain.add: exception - " + e.getMessage());
		}
		return null;
	}

	private boolean myConnectProcesses(IProcess prevProcess, IProcess backProcess, Map<String, String> keywords){
		try{			
			if (null == prevProcess || null == backProcess || null == keywords)
			{
				return false;
			}
			Set<String> strSets = keywords.keySet();
			if (null == strSets){
				return false;
			}
			String[] keys = new String[strSets.size()];
			strSets.toArray(keys);			
			String key = "";
			int i = 0;
			for (; i < keys.length; ++i)
			{
				key = keys[i];
				if (null == key)
				{
					continue;
				}
				if (!connectProcesses(prevProcess, backProcess, key, keywords.get(key)))
				{
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

	private boolean myConnectProcesses(IProcess prevProcess, IProcess backProcess, String prevParaName, String backParaName){
		try{
			ParaItem[] paras = prevProcess.outputSet();
			if (null == paras){
				return false;
			}
			
			String prevType = null;
			ParaItem p = null;
			int i = 0;
			for (; i < paras.length; ++i){
				p = paras[i];
				if (null == p){
					continue;
				}
				if (p.getName().equalsIgnoreCase(prevParaName)){
					prevType = p.getDataType();
					break;
				}
			}
			if (null == prevType){
				return false;
			}
			
			paras = backProcess.inputSet();
			if (null == paras){
				return false;
			}
			String backType = null;
			for (i = 0; i < paras.length; ++i){
				p = paras[i];
				if (null == p){
					continue;
				}
				if (p.getName().equalsIgnoreCase(backParaName)){
					backType = p.getDataType();
					if (backType.contains(";")){
						String[] strs = backType.split(";");
						backType = strs[0];
					}
					break;
				}
			}
			if (null == backType){
				return false;
			}
	
			IProcessingManager _gpmanager = InstanceManager.getInstance().processingManager();
			if (null == _gpmanager){
				return false;
			}
			IConnector c = _gpmanager.createConnector(prevType, backType);
			if (null == c){
				return false;
			}
			if (!prevProcess.setOutputConnector(prevParaName, c)){
				return false;
			}
			if (!backProcess.setInputConnector(backParaName, c)){
				return false;
			}
			
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	private boolean myExecute(){
		if (!LicenseProvider.getInstance().validate("", "")){
			error("许可验证失败!");
			return false;
		}
		
		try{
			if (null == _chainPool){
				error("处理链为空");
			}
			IProcess p = null;
			Object obj = null;
			int i = 0;
			for (; i < _chainPool.size(); ++i){
				p = _chainPool.get(i);
				if (null == p){
					info("第" + i + "个节点为空");
					continue;
				}
/*				
				obj = p.getData();
				if (obj instanceof GroupDescription){
					GroupDescription gd = (GroupDescription)obj;
					MParaItem[] mpis = gd.getInputs();
					MParaItem mpi_all = null;
					com.alibaba.fastjson.JSONObject js_all = new com.alibaba.fastjson.JSONObject();
					MParaItem mpi = null;
					for (int j = 0; j < mpis.length; ++j){					
						mpi = mpis[j];
						if (null == mpi){
							continue;
						}
						if (mpi.getName().equalsIgnoreCase("all")){
							mpi_all = mpi;
							continue;
						}
						if (!(p instanceof ProcessBase)){
							break;
						}
						js_all.put(mpi.getName(), ((ProcessBase)p).getInputValue(mpi.getName()));						
					}
					if (!js_all.isEmpty()){
						p.setInputValue("all", js_all);
					}
				}
*/				
				if (!p.execute()){
					error("id为" + p.getid() + "名字为'" + p.name() + "'的节点执行失败");
					return false;
				}
				info(String.format("节点[%d][%s]执行成功", p.getid(),p.name()));
			}
			return true;
		}
		catch(Exception e){
			error(e.getMessage());
		}
		
		return false;
	}

	private boolean myIsReady(){
		try
		{
			IProcess p = null;
			int i = 0;
			for (; i < _chainPool.size(); ++i)
			{
				p = _chainPool.get(i);
				if (null == p)
				{
					continue;
				}
				if (!p.isReady())
				{
					return false;
				}
			}
			return true;
		}
		catch(Exception e){
			error(e.getMessage());
		}
		
		return false;
	}

	private static void error(String message){
		if (_isLog4j && null != _logger){
			_logger.error(message);
			return;
		}
		if (_logLevel < 2){
			return;
		}
		if (null != _logger){
			_logger.error(message);
		}
		else{
			System.out.println("ERROR: " + message);
		}
	}

	private static  void info(String message){
		if (_isLog4j && null != _logger){
			_logger.info(message);
			return;
		}
		if (_logLevel < 4){
			return;
		}
		if (null != _logger){
			_logger.info(message);
		}
		else{
			System.out.println("INFO: " + message);
		}
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}
}