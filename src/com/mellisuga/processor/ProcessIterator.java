package com.mellisuga.processor;

import java.util.ArrayList;

import com.mellisuga.core.InstanceManager;
import com.mellisuga.graph.Graph;
import com.mellisuga.graph.IEdge;
import com.mellisuga.graph.INode;
import com.mellisuga.processing.GroupDescription;
import com.mellisuga.processing.IProcess;
import com.mellisuga.processing.IProcessChain;
import com.mellisuga.processing.IProcessingManager;
import com.mellisuga.processing.LinkDescription;
import com.mellisuga.processing.MParaItem;
import com.mellisuga.processing.ParaItem;
import com.mellisuga.processing.ProcessBase;
import com.mellisuga.processing.ProcessChainImpl;
import com.mellisuga.processing.ProcessDescription;
import com.mellisuga.processing.Toolkit;

/*流程中前一个Process输出结果为数组，支持每次传入数组中一个元素，执行后续的一个group流程，从而实现迭代计算。迭代结构可以嵌套，支持group节点。迭代支持如下方式：
(1)部分值传递
(2)值列表
(3)数组迭代
(4)JSON值迭代
*/
public class ProcessIterator extends ProcessBase {
	private GroupDescription _data = null;
	//private ArrayList<IProcessChain> _chains = null;
	
	public ProcessIterator(){
		this.setGuid("ProcessIterator");
		this.setName("ProcessIterator");
		this.setTitle("迭代器");
		this.setAbstracts("迭代器");
				
		ParaItem[] ipis = new ParaItem[2];
		ParaItem pi = null;
		pi = new ParaItem("group", "GroupDescription", "", false, "GroupDescription");
		ipis[0] = pi;
		pi = new ParaItem("args", "JSONObject", "", false, "JSON");
		ipis[1] = pi;	
		this.setInputParameters(ipis);
		
		ParaItem[] opis = new ParaItem[1];
		pi = new ParaItem("result", "JSON", "Any", true, "Any");
		opis[0] = pi;
		this.setOutputParameters(opis);	
	}
	
	@Override
	public boolean execute() {
		try{
			com.alibaba.fastjson.JSONObject args = null;				
			Object obj = this.getInput("args");			
			if (obj instanceof com.alibaba.fastjson.JSONObject){
				args = (com.alibaba.fastjson.JSONObject)obj;				
			}
			else if (obj instanceof String){
				args = com.alibaba.fastjson.JSON.parseObject((String)obj); 
			}
			
			obj = this.getInput("group");	
			if (obj instanceof GroupDescription){
				_data = (GroupDescription)obj;				
			}
			
			if (null == args || null == _data){
				return false;
			}			
			GroupDescription gd = _data;
			ProcessDescription[] pdArray = gd.getProcesses();
			LinkDescription[] ldArray = gd.getLinks();
			if (null == pdArray || null == ldArray){				
				return false;
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
			
			IProcessingManager _gpmanager = InstanceManager.getInstance().processingManager();
			if (null == _gpmanager){
				return false;
			}
			INode[] queue = gpm.bfsQueue();
			INode n = null;
			IProcessChain chain = new ProcessChainImpl();
			IProcess p = null;
			int j = 0;
			ParaItem pi = null;
			Object value = null;
			String name = "";
			boolean group = false;
			for (i = 0; i < queue.length; ++i){
				n = queue[i];
				
				group = false;
				name = Toolkit.getNodeName(n);
				if (name.isEmpty()){
					name = Toolkit.getNodeType(n);
				}
				if (name.equalsIgnoreCase("start") || name.equalsIgnoreCase("end")){
					continue;
				}
				
				p = chain.addProcess(name, n.getId());
				if (null == p){
					p = chain.addProcess(name, n.getId(),n.getData());
					if (null != p){
						group = true;
					}
				}
				if (null == p){
					return false;
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
				
				if (group){
					com.alibaba.fastjson.JSONObject jo = new com.alibaba.fastjson.JSONObject();  
					for(j = 0; j < pis.length; ++j){
						pi = pis[j];					
						if (null == pi){
							continue;
						}
						value = pi.getValue();
						if (null != value){							
							jo.put(pi.getName(), value);
						}
					}
					p.setInputValue("args", jo);
				}
				else{
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
			}
			IEdge[] edges = gpm.edges();
			IEdge e = null;
			for (i = 0; i < edges.length; ++i){
				e = edges[i];		
				chain.connectProcesses(e.fromNode().getId(), e.toNode().getId(), Toolkit.getAssigns(e));							
			}				
						
			if (!gd.containsKey("iterator")){
				return false;
			}
			obj = gd.get("iterator");
			String varname = null;
			if (obj instanceof com.alibaba.fastjson.JSONObject){				
				com.alibaba.fastjson.JSONObject jvar = (com.alibaba.fastjson.JSONObject)obj;
				if (jvar.containsKey("variables")){
					
				}
				else{
					varname = jvar.getString(_XMLTag.g_AttributionName);	
				}				
			}
			else if(obj instanceof com.alibaba.fastjson.JSONArray){
				
			}
			
			MParaItem[] mpis = gd.getInputs();
			MParaItem mpiter = null;
			MParaItem mpi = null;
			ArrayList<Integer> ids = null;
			ArrayList<String> paraNames = null;							
			if (null != mpis){
				boolean bFind = false;
				int k = 0;
				for (i = 0; i < mpis.length; ++i){					
					mpi = mpis[i];
					if (null == mpi){
						continue;
					}
					ids = mpi.getProcessIds();
					paraNames = mpi.getParaNames();
					if (!args.containsKey(mpi.getName())){
						continue;
					}
					
					if (varname.equalsIgnoreCase(mpi.getName())){
						mpiter = mpi;
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
					
					obj = _gpmanager.createObject(strs[0], args.get(mpi.getName()));
					for (k = 0; k < ids.size() && k < paraNames.size(); ++k){																
						bFind = false;
						p = chain.getProcessById(ids.get(k));
						if (null == p){
							continue;
						}
						bFind = p.setInputValue(paraNames.get(k), obj);
						
						if (!bFind){							
							return false;
						}
					}
				}				
			}
			
			if (null == mpiter){
				return false;
			}
			String strType = mpiter.getDataType();
			if (null == strType || strType.isEmpty()){						
				return false;
			}
			String[] strs = strType.split(";");
			if (null == strs || strs.length <= 0){						
				return false;
			}				
			com.alibaba.fastjson.JSONArray jrefs = com.alibaba.fastjson.JSON.parseArray(mpiter.getProperty("reference"));
			
			strType = strs[0];
			ArrayList iterobjs = new ArrayList();
			if (strType.equalsIgnoreCase("JSONArray")){
				obj = _gpmanager.createObject(strType, args.get(mpiter.getName()));
				if (!(obj instanceof com.alibaba.fastjson.JSONArray)){
					return false;
				}
				
				String str = jvar.getString("element");
				if (!str.equalsIgnoreCase("..{value}")){
					return false;
				}

				com.alibaba.fastjson.JSONArray ja = (com.alibaba.fastjson.JSONArray)obj;
				for (i = 0; i < ja.size(); ++i){
					iterobjs.add(ja.get(i));
				}
			}
			else if (strType.startsWith("Array.")){
				if (strType.equalsIgnoreCase("Array.Int") || strType.equalsIgnoreCase("Array.Integer")){
					Integer[] items = (Integer[])(_gpmanager.createObject(strType, args.get(mpiter.getName())));					
					addToArray(iterobjs, items);
				}
				else if (strType.equalsIgnoreCase("Array.String")){
					String[] items = (String[])(_gpmanager.createObject(strType, args.get(mpiter.getName())));					
					addToArray(iterobjs, items);
				}
				else if (strType.equalsIgnoreCase("Array.Long")){
					Long[] items = (Long[])(_gpmanager.createObject(strType, args.get(mpiter.getName())));					
					addToArray(iterobjs, items);
				}
				else if (strType.equalsIgnoreCase("Array.Float")){
					Float[] items = (Float[])(_gpmanager.createObject(strType, args.get(mpiter.getName())));					
					addToArray(iterobjs, items);
				}
				else if (strType.equalsIgnoreCase("Array.Double")){
					Double[] items = (Double[])(_gpmanager.createObject(strType, args.get(mpiter.getName())));					
					addToArray(iterobjs, items);
				}
				else if (strType.equalsIgnoreCase("Array.Boolean")){
					Boolean[] items = (Boolean[])(_gpmanager.createObject(strType, args.get(mpiter.getName())));					
					addToArray(iterobjs, items);
				}
				else if (strType.equalsIgnoreCase("Array.Byte")){
					Byte[] items = (Byte[])(_gpmanager.createObject(strType, args.get(mpiter.getName())));					
					addToArray(iterobjs, items);				
				}
				else if (strType.equalsIgnoreCase("Array.Character")){
					Character[] items = (Character[])(_gpmanager.createObject(strType, args.get(mpiter.getName())));					
					addToArray(iterobjs, items);
				}
				else if (strType.equalsIgnoreCase("Array.Short")){
					Short[] items = (Short[])(_gpmanager.createObject(strType, args.get(mpiter.getName())));					
					addToArray(iterobjs, items);
				}
			}
			
			ids = mpiter.getProcessIds();
			paraNames = mpiter.getParaNames();
			com.alibaba.fastjson.JSONObject jref = null;
			for (i = 0; i < iterobjs.size(); ++i){
				for (j = 0; j < ids.size() && j < paraNames.size(); ++j){
					p = chain.getProcessById(ids.get(j));	
					//jrefs					
					jref = getReference(jrefs,ids.get(j),paraNames.get(j));
					obj = iterobjs.get(i);
					if (null != jref && jref.containsKey("part")){
							obj = iterobjs.get(i);
							if(!(obj instanceof com.alibaba.fastjson.JSONObject)){
								return false;
							}
							com.alibaba.fastjson.JSONObject jobj = (com.alibaba.fastjson.JSONObject)obj;
							if (!jobj.containsKey(jref.getString("part"))){
								return false;
							}
							obj = jobj.get(jref.getString("part"));
					}
					if (!p.setInputValue(paraNames.get(j), obj)){
						return false;
					}
				}
				if (!chain.execute()){
					return false;
				}
			}
			
			mpis = gd.getOutputs();
			Object result = null;
			if (0 == mpis.length){
				result = null;
			}
			else if (1 == mpis.length){
				mpi = mpis[0];
				ids = mpi.getProcessIds();
				paraNames = mpi.getParaNames();
				if (0 == ids.size() || 0 == paraNames.size()){
					result = null;
				}
				else if (1 == ids.size()){
					p = chain.getProcessById(ids.get(0));
					if (null == p){
						return false;
					}					
					result = p.getOutputValue(paraNames.get(0));
				}
				else{
					com.alibaba.fastjson.JSONObject jo = new com.alibaba.fastjson.JSONObject();
					for (i = 0; i < ids.size() && i < paraNames.size(); ++i){
						p = chain.getProcessById(ids.get(i));
						obj = p.getOutputValue(paraNames.get(i));
						if (null == p || null == obj){
							return false;
						}					
						jo.put(paraNames.get(i), obj);
					}
					result = jo;
				}
			}
			else{
				com.alibaba.fastjson.JSONObject jo = new com.alibaba.fastjson.JSONObject();
				for (i = 0; i < mpis.length; ++i){
					mpi = mpis[i];
					ids = mpi.getProcessIds();
					paraNames = mpi.getParaNames();
					if (0 == ids.size() || 0 == paraNames.size()){
						return false;
					}
					else if (1 == ids.size()){
						p = chain.getProcessById(ids.get(0));
						if (null == p){
							return false;
						}					
						result = p.getOutputValue(paraNames.get(0));
						jo.put(mpi.getName(), result);
					}
					else{
						com.alibaba.fastjson.JSONObject jsub = new com.alibaba.fastjson.JSONObject();
						for (j = 0; j < ids.size() && j < paraNames.size(); ++j){
							p = chain.getProcessById(ids.get(j));
							obj = p.getOutputValue(paraNames.get(j));
							if (null == p || null == obj){
								return false;
							}					
							jsub.put(paraNames.get(j), obj);
						}
						jo.put(mpi.getName(), jsub);
					}
				}
				result = jo;
			}
			this.setInputValue("result", result);
			return true;
		}
		catch(Exception ex){
			
		}
		return false;
	}

	@Override
	public IProcess clone() {
		// TODO Auto-generated method stub
		return null;
	}

	private com.alibaba.fastjson.JSONObject getReference(com.alibaba.fastjson.JSONArray ja,int id,String name){
		if (null == ja){
			return null;
		}
		int i = 0;
		com.alibaba.fastjson.JSONObject jo = null;
		for (i = 0; i < ja.size(); ++i){
			jo = ja.getJSONObject(i);
			if (!jo.containsKey(_XMLTag.g_AttributionId) && !jo.containsKey(_XMLTag.g_AttributionName)){
				continue;
			}
			if (jo.getInteger(_XMLTag.g_AttributionId) == id && name.equalsIgnoreCase(jo.getString(_XMLTag.g_AttributionName))){
				break;
			}
			jo = null;
		}
		return jo;
	}
	
	public <T> void addToArray(ArrayList arrsy, T[] items) {	
		for (int j = 0; j < items.length; ++j){
			arrsy.add(items[j]);
		}	
	}
}
