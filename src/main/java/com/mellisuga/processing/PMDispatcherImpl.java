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
import java.util.Map;

import com.mellisuga.core.InstanceManager;
import com.mellisuga.graph.Graph;
import com.mellisuga.graph.IEdge;
import com.mellisuga.graph.INode;
import com.mellisuga.util.LicenseProvider;

/**
 * 派遣期实例
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:09:40
 *
 */
public class PMDispatcherImpl implements IPMDispatcher {
	private static IRunningLogger _logger = LoggerFactory.CreateLogger();
	private static int _logLevel = 2;	
	private boolean _parser = false;
	private IPMParser _mparser = null;
	private ArrayList<IProcessChain> _chains = null;

	public void setModelParser(IPMParser parser){	
		_parser = false;
		this._mparser = parser;
		parserModel();		
	}

	public Object dispatch(IContext context) {
		return myDispatch(context);
	}
	
	public Object dispatch(IContext context, Object[] args){
		return myDispatch(context, args);
	}
	
	public Object dispatch(IContext context, String[] args){
		return myDispatch(context, args);
	}
	
	public Object dispatch(IContext context, String parameters){
		return myDispatch(context, parameters);
	}
	
	public Object dispatch(IContext context, Map<String,String> parameters){
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}		
		try{			
			if (null != context){
				_logLevel = (Integer)context.getVariable("logLevel");				
			}
			parserModel();
			
			if (null == this._chains){
				error("处理链为空");
				return null;
			}			
			if (null == this._mparser){
				error("模型解析器为空");
				return null;
			}			
			IProcessingManager _gpmanager = InstanceManager.getInstance().processingManager();
			if (null == _gpmanager){
				error("获取管理器失败");
				return null;
			}
			
			IProcessChain chain = null;
			int i = 0;			
			MParaItem[] mpis = this._mparser.getInputs();
			if (null != mpis){			
				MParaItem mpi = null;
				IProcess p = null;
				int j = 0;
				boolean bFind = false;
				Object obj = null;
				int k = 0;
				ArrayList<Integer> ids = null;
				ArrayList<String> paraNames = null;
				MParaItem mpi_all = null;
				com.alibaba.fastjson.JSONObject js_all = new com.alibaba.fastjson.JSONObject();
				for (i = 0; i < mpis.length; ++i){					
					mpi = mpis[i];
					if (null == mpi){
						continue;
					}
					if (mpi.getName().equalsIgnoreCase("all")){
						mpi_all = mpi;
						continue;
					}
					ids = mpi.getProcessIds();
					paraNames = mpi.getParaNames();
					if (!parameters.containsKey(mpi.getName())){						
						continue;
					}		
					
					String strType = mpi.getDataType();
					if (null == strType || strType.isEmpty()){						
						continue;
					}
					String[] strs = strType.split(";");
					if (null == strs || strs.length <= 0){						
						continue;
					}
					
					obj = _gpmanager.createObject(strs[0], parameters.get(mpi.getName()));
					js_all.put(mpi.getName(), obj);
					for (k = 0; k < ids.size() && k < paraNames.size(); ++k){																
						bFind = false;
						for (j = 0; j < this._chains.size(); ++j){						
							chain = this._chains.get(j);
							if (null == chain){
								//输出提示信息
								continue;
							}
													
							p = chain.getProcessById(ids.get(k));
							if (null == p){
								continue;
							}
							bFind = p.setInputValue(paraNames.get(k), obj);
						}
						
						if (!bFind){
							error("输入参数和模型参数不匹配");
							return null;
						}
					}
				}
				if (null != mpi_all){
					if (!setAllInput(mpi_all,js_all)){
						return false;
					}
				}
			}
			
			for (i = 0; i < this._chains.size(); ++i){				
				chain = this._chains.get(i);
				if (null == chain){
					//输出提示信息
					continue;
				}				
				chain.execute();			
			}
			return this.getResult();
		}
		catch(Exception ex){
			error(ex.getMessage());
		}
		
		return null;
	}
	
	private void parserModel(){
		try{
			if (_parser){
				return;
			}
			if (null == this._mparser){
				error("模型解析器为空");
				return;
			}
				
			ProcessDescription[] pdArray = this._mparser.processList();
			LinkDescription[] ldArray = this._mparser.linkList();
			if (null == pdArray || null == ldArray){
				error("流程解析结果为空");
				return;
			}
			
			Graph gpm = new Graph();
			ProcessDescription pd = null;
			LinkDescription ld = null;
			int i = 0;
			for (i = 0; i < pdArray.length; ++i){
				pd = pdArray[i];		
				gpm.createNode(pd.getId(), pd);
			}
			for (i = 0; i < ldArray.length; ++i){
				ld = ldArray[i];
				gpm.link(gpm.nodeById(ld.fromId()), gpm.nodeById(ld.toId()), ld);
			}
			if (null == this._chains){
				this._chains = new ArrayList<IProcessChain>();
			}
			else{
				this._chains.clear();			
			}
					
			this._resultDescription = this._mparser.resultDescription();	
			IProcessingManager _gpmanager = InstanceManager.getInstance().processingManager();
			if (null == _gpmanager){
				return;
			}
			INode[] queue = gpm.bfsQueue();
			INode n = null;
			IProcessChain chain = new ProcessChainImpl();
			IProcess p = null;
			int j = 0;
			ParaItem pi = null;
			Object value = null;
			for (i = 0; i < queue.length; ++i){
				n = queue[i];
				
				if (Toolkit.getNodeName(n).equalsIgnoreCase("start") || Toolkit.getNodeName(n).equalsIgnoreCase("end")){
					continue;
				}
				
				p = chain.addProcess(Toolkit.getNodeName(n), n.getId());
				if (null == p){
					p = chain.addProcess(Toolkit.getNodeType(n), n.getId(),n.getData());
				}
				if (null == p){
					return;
					//continue;
				}
				
				pd = (ProcessDescription)(n.getData());
				if (null == pd){
					continue;
				}
				p.setData(pd);
				
				ParaItem[] pis = pd.getInputs();
				if (null == pis){
					continue;
				}
				for(j = 0; j < pis.length; ++j){
					pi = pis[j];					
					if (null == pi){
						continue;
					}
					value = pi.getValue();
					if (null != value){
						p.setInputValue(pi.getName(), value);
					}
				}
			}
			IEdge[] edges = gpm.edges();
			IEdge e = null;
			for (i = 0; i < edges.length; ++i){
				e = edges[i];		
				chain.connectProcesses(e.fromNode().getId(), e.toNode().getId(), Toolkit.getAssigns(e));							
			}
				
			this._chains.add(chain);
			_parser = true;
		}
		catch(Exception ex){
			error(ex.getMessage());
		}
	}
	
	private ResultDescription _resultDescription = null;
	private Object getResult(){
		try{
			if (null == _resultDescription){
				return null;
			}
			
			IProcess p = Toolkit.getProcessById(this._chains, this._resultDescription.getProcessId());
			if (null == p){
				return null;
			}

			return p.getOutputValue(this._resultDescription.getResultParaName());
		}
		catch(Exception ex){			
			error(ex.getMessage());
		}
		return null;
	}
	
	public String getResultName(){
		try{
			if (null == this._resultDescription){
				return "";
			}						
			
			return this._resultDescription.name();
		}
		catch(Exception ex){
			error(ex.getMessage());
		}
		return "";
	}
	
	public String getResultType(){
		try{
			if (null == this._resultDescription){
				return "";
			}		
			
			IProcess p = Toolkit.getProcessById(this._chains, this._resultDescription.getProcessId());
			if (null == p){
				return "";
			}
					
			ParaItem pi = Toolkit.getParaItem(p.outputSet(), this._resultDescription.getResultParaName());
			if (null == pi){
				return "";
			}
			
			return pi.getDataType();
		}
		catch(Exception ex){
			error(ex.getMessage());
		}
		return "";
	}
	
	public String getResultKind(){
		try{
			if (null == this._resultDescription){
				return null;
			}
			
			return this._resultDescription.getKind();
		}
		catch(Exception ex){
			error(ex.getMessage());
		}
		return "";
	}

	private Object myDispatch(IContext context) {
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}
		try{
			IProcessChain chain = null;
			int i = 0;
			for (i = 0; i < this._chains.size(); ++i){
				chain = this._chains.get(0);
				if (null == chain){
					//输出提示信息
					continue;
				}
				
				chain.execute();
			}
			return this.getResult();
		}
		catch(Exception ex){
			error(ex.getMessage());
		}
		return null;
	}
	
	private Object myDispatch(IContext context, Object[] args){
		try{			
			if (!LicenseProvider.getInstance().validate("", "")){
				return null;
			}
			
			if (null == this._chains){
				System.out.println("PMDispatcherImpl.dispatch:数据处理链为空");
				return null;
			}			
			if (null == this._mparser){
				System.out.println("PMDispatcherImpl.dispatch:模型解析器为空");
				return null;
			}
			
			IProcessChain chain = null;
			int i = 0;			
			MParaItem[] mpis = this._mparser.getInputs();
			if (null != args && null != mpis && args.length == mpis.length){			
				MParaItem mpi = null;
				IProcess p = null;
				int j = 0;
				boolean bFind = false;
				ArrayList<Integer> ids = null;
				ArrayList<String> paraNames = null;
				MParaItem mpi_all = null;
				com.alibaba.fastjson.JSONObject js_all = new com.alibaba.fastjson.JSONObject();
				int k = 0;
				for (i = 0; i < mpis.length; ++i){					
					mpi = mpis[i];
					if (null == mpi){
						continue;
					}
					if (mpi.getName().equalsIgnoreCase("all")){
						mpi_all = mpi;
						continue;
					}
					js_all.put(mpi.getName(), args[i]);
					ids = mpi.getProcessIds();
					paraNames = mpi.getParaNames();
					for (k = 0; k < ids.size() && k < paraNames.size(); ++k){
						bFind = false;
						for (j = 0; j < this._chains.size(); ++j){						
							chain = this._chains.get(j);
							if (null == chain){
								//输出提示信息
								continue;
							}
													
							p = chain.getProcessById(ids.get(k));
							if (null == p){
								continue;
							}
							bFind = p.setInputValue(paraNames.get(k), args[i]);
						}
						
						if (!bFind){			
							return null;
						}
					}
				}
				if (null != mpi_all){
					if (!setAllInput(mpi_all,js_all)){
						return false;
					}
				}
			}
			
			for (i = 0; i < this._chains.size(); ++i){				
				chain = this._chains.get(i);
				if (null == chain){
					//输出提示信息
					continue;
				}				
				chain.execute();				
			}
			
			return this.getResult();
		}
		catch(Exception ex){
			error(ex.getMessage());
		}
		
		return null;
	}
	
	private Object myDispatch(IContext context, String[] args){
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}
		try{
			if (null == this._mparser){
				error("模型解析器为空");
				return null;
			}
			
			Object[] objArgs = null;
			ParaItem[] pis = this._mparser.getInputs();
			if (null != pis){
				if (args.length != pis.length){
					error("输入参数和模型参数不一致");
					return null;
				}
				
				IProcessingManager _gpmanager = InstanceManager.getInstance().processingManager();
				if (null == _gpmanager){
					error("获取流程管理器失败");
					return null;
				}			
				objArgs = new Object[args.length];
				int i = 0;
				ParaItem pi = null;
				String strType = "";
				for(i = 0; i < pis.length; ++i){
					pi = pis[i];
					if (null == pi){
						continue;
					}
					strType = pi.getDataType();
					if (null == strType || strType.isEmpty()){
						continue;
					}
					String[] strs = strType.split(";");
					if (null == strs || strs.length <= 0){
						continue;
					}
					objArgs[i] = _gpmanager.createObject(strs[0], args[i]);
				}
			}
			return dispatch(context, objArgs);
		}
		catch(Exception ex){			
			error(ex.getMessage());
		}
		return null;
	}
	
	private Object myDispatch(IContext context, String parameters){
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}
		try{
			IProcessChain chain = null;
			int i = 0;
			for (i = 0; i < this._chains.size(); ++i){
				chain = this._chains.get(0);
				if (null == chain){
					//输出提示信息
					continue;
				}
				
				chain.execute();
			}
			return this.getResult();
		}
		catch(Exception ex){
			error(ex.getMessage());
		}
		return null;
	}
	
	private boolean setAllInput(MParaItem mpi_all,com.alibaba.fastjson.JSONObject js_all){
		ArrayList<Integer> ids = mpi_all.getProcessIds();
		ArrayList<String> paraNames = mpi_all.getParaNames();
		IProcessChain chain = null;
		boolean bFind = true;
		IProcess p = null;
		for (int k = 0; k < ids.size() && k < paraNames.size(); ++k){
			bFind = false;
			for (int j = 0; j < this._chains.size(); ++j){						
				chain = this._chains.get(j);
				if (null == chain){
					//输出提示信息
					continue;
				}
										
				p = chain.getProcessById(ids.get(k));
				if (null == p){
					continue;
				}
				bFind = p.setInputValue(paraNames.get(k), js_all);
			}
			
			if (!bFind){			
				return false;
			}
		}
		return true;
	}

	private static void error(String message){
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

	private static void debug(String message){
		if (_logLevel < 5){
			return;
		}
		if (null != _logger){
			_logger.debug(message);
		}
		else{
			System.out.println("DEBUG: " + message);
		}
	}
}
