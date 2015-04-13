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

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/**
 * 数据流程XML格式模型文件解析器实现类
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:09:40
 */
public class PMParserImpl implements IPMParser {
	 private boolean _bLoaded = false;	//文件是否已经加载解析过了的标识
     private String _file = "";
     private boolean _bStepByStepMode = false;
     private java.util.Properties _properties = new java.util.Properties();
     private ArrayList<ProcessDescription> _processList = null;
     private ArrayList<Element> _processes = null;     
     private ArrayList<LinkDescription> _linkList = null;
     private ResultDescription _resultDescription = null;
     
     private String _name = null;
     private String _abstracts = null;
     private ArrayList<MParaItem> _inputs = null;
     private ArrayList<ParaItem> _outputs = null;
     private String _title = null;
     
	@Override
	public void setFile(String value) {
		_file = value;
        _bLoaded = false;
	}

	@Override
	public boolean isStepByStep() {		
		try{
			Load();
			return _bStepByStepMode;
		}
		catch(Exception ex){
			
		}
        return false;
	}
	
	public String getName(){
		if (!_bLoaded)
		{
			Load();
		}
		return _name;
	}
	
	public String getTitle(){
		if (!_bLoaded)
		{
			Load();
		}
		return _title;
	}
	
	public String getAbstracts(){
		if (!_bLoaded)
		{
			Load();
		}
		return _abstracts;
	}
	
	public String getProperties(String key){
		if (_properties.containsKey(key)){
			return _properties.getProperty(key);
		}
		return null;
	}
	
	public MParaItem[] getInputs(){
		try{
			if (!_bLoaded){
				Load();
			}
			if (null == this._inputs){
				System.out.println("PMParserImpl.getInputs:无模型输入参数");
				return null;
			}
			
			MParaItem[] items = new MParaItem[this._inputs.size()];
			this._inputs.toArray(items);
			return items;
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("PMParserImpl.getInputs:" + ex.getMessage());
		}
		return null;
	}
	
	public ParaItem[] getOutputs(){
		try{
			if (!_bLoaded)
			{
				Load();
			}
			if (null == this._outputs){
				System.out.println("PMParserImpl.getOutputs:无模型输出参数");
				return null;
			}
			
			ParaItem[] items = new ParaItem[this._outputs.size()];
			this._outputs.toArray(items);
			return items;
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("PMParserImpl.getOutputs:" + ex.getMessage());
		}
		return null;
	}


	@Override
	public LinkDescription[] linkList() {	
		try{			
			Load();
			if (null == this._linkList){
				return null;
			}

			LinkDescription[] items = new LinkDescription[this._linkList.size()];
			_linkList.toArray(items);
			return items;	
		}
		catch(Exception ex){
			
		}
		
		return null;
	}

	@Override
	public ProcessDescription[] processList() {
		try{			
			Load();
			if (null == this._processList){
				return null;
			}

			ProcessDescription[] items = new ProcessDescription[this._processList.size()];
			_processList.toArray(items);
			return items;
		}
		catch(Exception ex){
			
		}
		
		return null;
	}
	
	public Element[] processes(){
		try{			
			Load();
			if (null == this._processes){
				return null;
			}

			Element[] items = new Element[this._processes.size()];
			_processes.toArray(items);
			return items;
		}
		catch(Exception ex){
			System.out.println("PMParser.processes:" + ex.getMessage());
		}
		
		return null;
	}
	
	public ResultDescription resultDescription(){
		try{			
			Load();
			return _resultDescription;
		}
		catch(Exception e){
			System.out.println("PMParserImpl.resultDescription:" + e.getMessage());
		}
		
		return null;		
	}

	@Override
	public void reset(){
		_file = "";
		_bStepByStepMode = false;
		if (null != _processList){
			_processList.clear();
		}
		_processList = null;
		if (null != _processes){
			_processes.clear();
		}
		_processes = null;
		if (null != _linkList){
			_linkList.clear();
		}
		_linkList = null;
		_resultDescription = null;
		_name = null;
		_abstracts = null;
		if (null != _inputs){
			_inputs.clear();
		}
		_inputs = null;
		if (null != _outputs){
			_outputs.clear();
		}
		_outputs = null;
		_title = null;
		_bLoaded = false;
	}
	
	private void Load(){
		try{			
			if (_bLoaded){
                return;
            }
            else{
            	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            	DocumentBuilder builder = factory.newDocumentBuilder();
            	Document document = null;
            	if (null != _file && !_file.isEmpty()){
            		document = builder.parse(new File(_file));	
            	}
            	else{
            		System.out.println("PMParser.Load:文件参数非法");
            		return;
            	}	
            	Element rootElement = document.getDocumentElement();            	
            	if (null == rootElement){
            		System.out.println("PMParser.Load:文档根节点获取失败");
                    return;
                }            	
                _bStepByStepMode = Boolean.parseBoolean(rootElement.getAttribute("stepByStep"));
                
                
              //解析扩展属性
                NamedNodeMap nnm = rootElement.getAttributes();
            	if (null != nnm){
            		for (int m = 0; m < nnm.getLength(); ++m){
            			org.w3c.dom.Node an = nnm.item(m);
            			String astr = an.getNodeName();
            			if (astr.equalsIgnoreCase("stepByStep") || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract)){
            				continue;
            			}
            			_properties.setProperty(astr, an.getNodeValue());
            		}
            	}
                
                Element head = (Element)(rootElement.getElementsByTagName(_XMLTag.g_NodeHead).item(0));
                if (null != head){
                	_name = head.getElementsByTagName(_XMLTag.g_NodeIdentifier).item(0).getTextContent();
                	parserModelParameter(head);
                }                
                
                _processList = new ArrayList<ProcessDescription>();
                _processes = new ArrayList<Element>();
                //强制类型转换后面跟一个空格
                Element nodeStart = (Element) rootElement.getElementsByTagName("start").item(0);
                if (null == nodeStart){
                	System.out.println("PMParser.Load:获取开始节点失败");
                    return;
                }         
                ProcessDescription process = new ProcessDescription("start", Integer.parseInt(nodeStart.getAttribute("id")), "", "");
                this._processList.add(process);
                this._processes.add(nodeStart);
                
                IProcessingManager gpManager = com.mellisuga.core.InstanceManager.getInstance().processingManager();                
                if (null == gpManager){
                	System.out.println("PMParser.Load:获取管理器失败");
                	return;
                }              
         
                Element element = null;
                Element elePara = null;
                ArrayList<ParaItem> inputs = null;
                ParaItem para = null;
                MParaItem mpi = null;
                NodeList nodes = rootElement.getElementsByTagName("operation");
                NodeList nlist = null;
                ParaItem[] paras = null;
                ArrayList<ParaItem> piArray = null;                
                int i = 0;
                int j = 0;
                for (i = 0; i < nodes.getLength(); i++){
                	element = (Element) (nodes.item(i));
                	
                	process = new ProcessDescription(element.getAttribute("name"),
                			Integer.parseInt(element.getAttribute("id")), element.getAttribute("title"), element.getAttribute("abstracts"));
                	//解析扩展属性
                	nnm = element.getAttributes();
                	if (null != nnm){
                		for (int m = 0; m < nnm.getLength(); ++m){
                			org.w3c.dom.Node an = nnm.item(m);
                			String astr = an.getNodeName();
                			if (astr.equalsIgnoreCase(_XMLTag.g_AttributionId) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract)){
                				continue;
                			}
                			process.setProperty(astr, an.getNodeValue());
                		}
                	}
                	
                    inputs = new ArrayList<ParaItem>();
                    nlist = element.getElementsByTagName("inputs");
                    if (null == nlist || nlist.getLength() <= 0){                
                    	continue;                    	
                    }
                    elePara = (Element) (nlist.item(0));
                    nlist = elePara.getElementsByTagName("input");
                          
                    for (j = 0; j < nlist.getLength(); j++){                    	
                    	elePara = (Element)(nlist.item(j));
                    	if (null == elePara){                    		
                    		continue;
                    	}
                         	
                    	if (elePara.hasAttribute(_XMLTag.g_AttributionBinding)){                    		
                    		mpi = findMParser(elePara.getAttribute(_XMLTag.g_AttributionBinding));
                    		if (null != mpi){
                    		}
                    	}
                    	else{                    		
	                    	para = new ParaItem(elePara.getAttribute(_XMLTag.g_AttributionName), elePara.getAttribute(_XMLTag.g_AttributionType));
	                    	para.setDataType(elePara.getAttribute(_XMLTag.g_AttributionType));
	                    	
	                    	String strType = para.getDataType();
	    					if (null == strType || strType.isEmpty()){
	    						System.out.println("PMParser.Load:获取模型参数类型非法");
	    						continue;
	    					}	    					
	    					if (strType.contains(";")){
	    						String[] strs = strType.split(";");
		    					if (null == strs || strs.length <= 0){
		    						System.out.println("PMParser.Load:解析模型参数类型非法");
		    						continue;
		    					}
		    					strType = strs[0];
	    					}
	    					
	                    	para.setValue(gpManager.createObject(strType, elePara.getAttribute("variable")));
	                    	
	                    	//解析扩展属性
	                    	nnm = elePara.getAttributes();
	                    	if (null != nnm){
	                    		for (int m = 0; m < nnm.getLength(); ++m){
	                    			org.w3c.dom.Node an = nnm.item(m);
	                    			String astr = an.getNodeName();
	                    			if (astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionType) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract) || astr.equalsIgnoreCase(_XMLTag.g_AttributionVariable)){
	                    				continue;
	                    			}
	                    			para.setProperty(astr, an.getNodeValue());
	                    		}
	                    	}
	                    	inputs.add(para);
                    	}
                    }   
                    paras = new ParaItem[inputs.size()];
                    inputs.toArray(paras);
                    process.setInputs(paras);
                    
                    piArray = new ArrayList<ParaItem>();
                    nlist = element.getElementsByTagName("outputs");
                    if (null == nlist || nlist.getLength() <= 0){                
                    	continue;                    	
                    }
                    elePara = (Element) (nlist.item(0));
                    nlist = elePara.getElementsByTagName("output");
                    for (j = 0; j < nlist.getLength(); j++){                    	
                    	elePara = (Element)(nlist.item(j));
                    	if (null == elePara){                    		
                    		continue;
                    	}
                         	
                    	para = new ParaItem(elePara.getAttribute(_XMLTag.g_AttributionName), elePara.getAttribute(_XMLTag.g_AttributionType));
	                    para.setDataType(elePara.getAttribute(_XMLTag.g_AttributionType));
	                    	
	                    String strType = para.getDataType();
	    				if (null == strType || strType.isEmpty()){
	    					System.out.println("PMParser.Load:获取模型参数类型非法");
	    					continue;
	    				}	    					
	    				if (strType.contains(";")){
	    					String[] strs = strType.split(";");
		    				if (null == strs || strs.length <= 0){
		    					System.out.println("PMParser.Load:解析模型参数类型非法");
		    					continue;
		    				}
		    				strType = strs[0];
	    				}
	    						                    	
	                    //解析扩展属性
	                    nnm = elePara.getAttributes();
	                    if (null != nnm){
	                    	for (int m = 0; m < nnm.getLength(); ++m){
	                    		org.w3c.dom.Node an = nnm.item(m);
	                    		String astr = an.getNodeName();
	                    		if (astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionType) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract) || astr.equalsIgnoreCase(_XMLTag.g_AttributionVariable)){
	                    			continue;
	                    		}
	                    		para.setProperty(astr, an.getNodeValue());
	                    	}
	                    }
	                    piArray.add(para);                    	
                    }   
                    paras = new ParaItem[piArray.size()];
                    piArray.toArray(paras);
                    process.setOutputs(paras);
                    
                    this._processList.add(process);
                    this._processes.add(element);
                }
                
                //子流程
                GroupDescription group = null;
                nodes = rootElement.getElementsByTagName("group");                
                for (i = 0; i < nodes.getLength(); i++){
                	element = (Element) (nodes.item(i));
                	
                	group = new GroupDescription(Integer.parseInt(element.getAttribute("id")),element.getAttribute("type"));
                	if (element.hasAttribute(_XMLTag.g_AttributionName)){
                		group.setName(element.getAttribute(_XMLTag.g_AttributionName));
                	}
                	if (element.hasAttribute(_XMLTag.g_AttributionTitle)){
                		group.setTitle(element.getAttribute(_XMLTag.g_AttributionTitle));
                	}
                	if (element.hasAttribute(_XMLTag.g_AttributionAbstract)){
                		group.setAbstracts(element.getAttribute(_XMLTag.g_AttributionAbstract));
                	}
                	//解析扩展属性
                	nnm = element.getAttributes();
                	if (null != nnm){
                		for (int m = 0; m < nnm.getLength(); ++m){
                			org.w3c.dom.Node an = nnm.item(m);
                			String astr = an.getNodeName();
                			if (astr.equalsIgnoreCase(_XMLTag.g_AttributionId) || astr.equalsIgnoreCase(_XMLTag.g_Attribution_type) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract)){
                				continue;
                			}
                			group.setProperty(astr, an.getNodeValue());
                		}
                	}
                	
                	parserGroup(group,element);
                	this._processList.add(group);
                    this._processes.add(element);
                }
                
                Element nodeEnd = (Element) rootElement.getElementsByTagName("end").item(0);
                if (null == nodeEnd){
                	System.out.println("PMParser.Load:获取开始节点失败");
                    return;
                }         
                process = new ProcessDescription("end", Integer.parseInt(nodeEnd.getAttribute("id")), "", "");
                this._processList.add(process);
                this._processes.add(nodeEnd);
                                
                this._linkList = new ArrayList<LinkDescription>();
                LinkDescription link = null;
                nlist = rootElement.getElementsByTagName(_XMLTag.g_NodeFlow);
                if (null == nlist || nlist.getLength() <= 0){
                	System.out.println("PMParser.Load:获取流程节点失败");
                	return;
                }                
                element = (Element) (nlist.item(0));
                nlist = element.getElementsByTagName(_XMLTag.g_NodeLinks);
                if (null == nlist || nlist.getLength() <= 0){
                	System.out.println("PMParser.Load:获取连接节点失败");
                	return;
                }                
                element = (Element) (nlist.item(0));        
                nodes = element.getElementsByTagName(_XMLTag.g_NodeLink);                           
                NodeList assigns = null;
                Element assignNode = null;
                Element ftNode = null;
                Assignment assign = null;
                String event = null;
                for (i = 0; i < nodes.getLength(); i++){                	
                	element = (Element) (nodes.item(i));                	
                	if (null == element){
                		continue;
                	}
                	
                	link = new LinkDescription(Integer.parseInt(element.getAttribute(_XMLTag.g_AttributionFrom)), 
                			Integer.parseInt(element.getAttribute(_XMLTag.g_AttributionTo)));
                	//解析扩展属性
                    nnm = element.getAttributes();
                    if (null != nnm){
                    	for (int m = 0; m < nnm.getLength(); ++m){
                    		org.w3c.dom.Node an = nnm.item(m);
                    		String astr = an.getNodeName();
                    		if (astr.equalsIgnoreCase(_XMLTag.g_AttributionFrom) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTo)){
                    			continue;
                    		}
                    		link.setProperty(astr, an.getNodeValue());
                    	}
                    }
                	
                	assigns = element.getElementsByTagName(_XMLTag.g_NodeAssign);                	
                	for (j = 0; j < assigns.getLength(); ++j){
                		assignNode = (Element) (assigns.item(j));
                		if (null == assignNode){
                			continue;
                		}
                		assign = new  Assignment();
                		assign.setSourceVariable(assignNode.getElementsByTagName(_XMLTag.g_NodeFrom).item(0).getTextContent());
                		assign.setTargetVariable(assignNode.getElementsByTagName(_XMLTag.g_NodeTo).item(0).getTextContent());
                		
                		ftNode = (Element)(assignNode.getElementsByTagName(_XMLTag.g_NodeFrom).item(0));
                		nnm = ftNode.getAttributes();
                        if (null != nnm){
                        	for (int m = 0; m < nnm.getLength(); ++m){
                        		org.w3c.dom.Node an = nnm.item(m);
                        		String astr = an.getNodeName();
                        		assign.setProperty("from." + astr, an.getNodeValue());
                        	}
                        }
                        ftNode = (Element)(assignNode.getElementsByTagName(_XMLTag.g_NodeTo).item(0));
                		nnm = ftNode.getAttributes();
                        if (null != nnm){
                        	for (int m = 0; m < nnm.getLength(); ++m){
                        		org.w3c.dom.Node an = nnm.item(m);
                        		String astr = an.getNodeName();
                        		assign.setProperty("to." + astr, an.getNodeValue());
                        	}
                        }
                    	                    		
                		link.add(assign);
                	}
                    this._linkList.add(link);
                }
        
                nlist = rootElement.getElementsByTagName(_XMLTag.g_Nodereply);
                if (null != nlist && nlist.getLength() == 1){
                	element = (Element) (nlist.item(0));
                	if (null != element){
                		event = null;
                		if (element.hasAttribute(_XMLTag.g_AttributionEvent))
                		{
                			event = element.getAttribute(_XMLTag.g_AttributionEvent);
                		}
                		this._resultDescription = new ResultDescription(element.getAttribute(_XMLTag.g_AttributionKind), element.getAttribute(_XMLTag.g_AttributionName), element.getAttribute(_XMLTag.g_Attribution_operation), Integer.parseInt(element.getAttribute(_XMLTag.g_AttributionId)), element.getAttribute(_XMLTag.g_AttributionVariable),event);
                	}
                }                
                     
                _bLoaded = true;
            }
		}
		catch(Exception ex){
			System.out.println("PMParser.Load:" + ex.getMessage());
		}		
	}
	
	private void parserModelParameter(Element nodeProcesshead){
		if(null == nodeProcesshead){
			return;
		}

		try{
/*			
			IContext context = InstanceManager.getInstance().getContext();
			if (null == context){			
				return;
			}*/
			
			if (null == _name || _name.isEmpty()){
				System.out.println("name is null");
				return;
			}

	    	Element nodeTitle = (Element)(nodeProcesshead.getElementsByTagName(_XMLTag.g_NodeTitle).item(0));
			if (null == nodeTitle){
				return;
			}
			this._title = nodeTitle.getTextContent();

			Element nodeAbstract = (Element)(nodeProcesshead.getElementsByTagName(_XMLTag.g_NodeAbstract).item(0));
			if (null == nodeAbstract){
				return;
			}
			this._abstracts = nodeAbstract.getTextContent();
			
			NodeList inputs = nodeProcesshead.getElementsByTagName(_XMLTag.g_NodeInput);
			this._inputs = new ArrayList<MParaItem>();
			int i = 0;
			Element nodeInput = null;
			MParaItem mpi = null;
			Element node = null;
			NodeList references = null;
			int j = 0;
			for (i = 0; i < inputs.getLength(); ++i){
				nodeInput = (Element)(inputs.item(i));
				mpi = new MParaItem("","");
				node = (Element)(nodeInput.getElementsByTagName(_XMLTag.g_NodeIdentifier).item(0));
				if (null == node){
					continue;	
				}				
				mpi.setName(node.getTextContent());
				node = (Element)(nodeInput.getElementsByTagName(_XMLTag.g_NodeTitle).item(0));
				if (null != node){
					mpi.setTitle(node.getTextContent());	
				}
				
				node = (Element)(nodeInput.getElementsByTagName(_XMLTag.g_NodeAbstract).item(0));
				if (null != node){
					mpi.setAbstracts(node.getTextContent());
				}
				
				node = (Element)(nodeInput.getElementsByTagName(_XMLTag.g_NodeDataType).item(0));
				if (null == node){
					continue;
				}		 
				mpi.setDataType(node.getTextContent());
				
				references = nodeInput.getElementsByTagName(_XMLTag.g_NodeReference);
				if (null != references){
					com.alibaba.fastjson.JSONArray ja = new com.alibaba.fastjson.JSONArray();
					for (j = 0; j < references.getLength(); ++j){
						node = (Element)references.item(j);
						mpi.addRefProcess(Integer.parseInt(node.getAttribute(_XMLTag.g_AttributionId)),
								"",
								node.getAttribute(_XMLTag.g_AttributionName));
						//扩展信息
						NamedNodeMap nnm = node.getAttributes();
	                	if (null != nnm){
	                		com.alibaba.fastjson.JSONObject jitem = new com.alibaba.fastjson.JSONObject();
	                		for (int m = 0; m < nnm.getLength(); ++m){
	                			org.w3c.dom.Node an = nnm.item(m);
	                			String astr = an.getNodeName();
	                			if (astr.equalsIgnoreCase(_XMLTag.g_AttributionId) || astr.equalsIgnoreCase(_XMLTag.g_Attribution_type) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract)){
	                				continue;
	                			}
	                			jitem.put(astr, an.getNodeValue());
	                		}
	                		if (jitem.size() > 0){
	                			jitem.put(_XMLTag.g_AttributionId, node.getAttribute(_XMLTag.g_AttributionId));
	                			jitem.put(_XMLTag.g_AttributionName, node.getAttribute(_XMLTag.g_AttributionName));
	                			ja.add(jitem);
	                		}
	                	}	                	
					}
					if (ja.size() > 0){
						mpi.setProperty("reference", ja.toJSONString());
					}
				}
				this._inputs.add(mpi);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("PMParser.parserModelParameter:" + ex.getMessage());
		}	
	}
	
	private void parserGroup(GroupDescription group,Element nodeGroup){
		String type = nodeGroup.getAttribute("type");
		NodeList nlist = null;
		Element emt = null;
		if (type.equalsIgnoreCase("iterator")){
			nlist = nodeGroup.getElementsByTagName("iterator");
	        if (null == nlist || nlist.getLength() <= 0){                
	        	return;                    	
	        }
	        emt = (Element) (nlist.item(0));
	        nlist = emt.getElementsByTagName("variables");
	        if (null == nlist || nlist.getLength() <= 0){                
	        	return;                    	
	        }
	        Element vars = (Element) (nlist.item(0));
	        nlist = vars.getElementsByTagName("variable");
	        com.alibaba.fastjson.JSONArray jsa = new com.alibaba.fastjson.JSONArray();
	        com.alibaba.fastjson.JSONObject jvars = new com.alibaba.fastjson.JSONObject();
	        for (int j = 0; j < nlist.getLength(); ++j){
	        	emt = (Element)nlist.item(j);	        	
	        	if (emt.hasAttribute(_XMLTag.g_AttributionName) && emt.hasAttribute(_XMLTag.g_Attribution_element)){                
	        		com.alibaba.fastjson.JSONObject jsvar = new com.alibaba.fastjson.JSONObject();
		        	jsvar.put(_XMLTag.g_AttributionName, emt.getAttribute(_XMLTag.g_AttributionName));
		        	jsvar.put(_XMLTag.g_Attribution_element, emt.getAttribute(_XMLTag.g_Attribution_element));
		        	group.put("iterator", jsvar);
		        	break;//应该只有一个吧，有多个了再修改;                    	
		        }
	        	else if (emt.hasAttribute(_XMLTag.g_AttributionName) && emt.hasAttribute("part")){
	        		com.alibaba.fastjson.JSONObject jsvar = new com.alibaba.fastjson.JSONObject();
		        	jsvar.put(_XMLTag.g_AttributionName, emt.getAttribute(_XMLTag.g_AttributionName));
		        	jsvar.put("part", emt.getAttribute("part"));
		        	jsa.add(jsvar);
	        	}
	        	else if(!emt.getTextContent().isEmpty()){
	        		if (jvars.isEmpty()){
	        			if (!vars.hasAttribute(_XMLTag.g_AttributionId) || !vars.hasAttribute(_XMLTag.g_AttributionName)){
	        				return;
	        			}
	        			jvars.put(_XMLTag.g_AttributionId, Integer.parseInt(vars.getAttribute(_XMLTag.g_AttributionId)));
	        			jvars.put(_XMLTag.g_AttributionName, vars.getAttribute(_XMLTag.g_AttributionName));
	        		}
	        		jsa.add(emt.getTextContent());
	        	}
	        }
	        if (jsa.size() > 0){
	        	if (jvars.isEmpty()){
	        		group.put("iterator", jsa);	
	        	}
	        	else{
	        		jvars.put("variables", jsa);
	        		group.put("iterator", jvars);
	        	}
	        }
		}
		else if (type.equalsIgnoreCase("while")){
			nlist = nodeGroup.getElementsByTagName("condition");
	        if (null == nlist || nlist.getLength() <= 0){                
	        	return;                    	
	        }
	        emt = (Element) (nlist.item(0));
	        if (!emt.hasAttribute(_XMLTag.g_AttributionId) || !emt.hasAttribute(_XMLTag.g_AttributionName)){                
	        	return;                    	
	        }
	        com.alibaba.fastjson.JSONObject jswhile = new com.alibaba.fastjson.JSONObject();
	        jswhile.put(_XMLTag.g_AttributionId, Integer.parseInt(emt.getAttribute(_XMLTag.g_AttributionId)));
	        jswhile.put(_XMLTag.g_AttributionName, emt.getAttribute(_XMLTag.g_AttributionName));
	        group.put("while", jswhile);
		}
		else if (type.equalsIgnoreCase("Storm")){
			//不需要额外记录
		}
		
		//group parameter
		nlist = nodeGroup.getElementsByTagName("inputs");
        if (null == nlist || nlist.getLength() <= 0){                
        	return;                    	
        }
        emt = (Element) (nlist.item(0));
        nlist = emt.getElementsByTagName("input");
        
        IProcessingManager gpManager = com.mellisuga.core.InstanceManager.getInstance().processingManager();                
        if (null == gpManager){
        	System.out.println("PMParser.Load:获取管理器失败");
        	return;
        }   
           
        int j = 0;
        ArrayList<MParaItem> minputs = new ArrayList<MParaItem>();
		int i = 0;
		MParaItem mpi = null;
		NamedNodeMap nnm = null;
		NodeList references = null;
		Element node = null;
        for (i = 0; i < nlist.getLength(); i++){                    	
        	emt = (Element)(nlist.item(i));
        	if (null == emt){                    		
        		continue;
        	}
        	String strType = emt.getAttribute(_XMLTag.g_AttributionType);
        	mpi = new MParaItem(emt.getAttribute(_XMLTag.g_AttributionName), strType);
            	
			if (null == strType || strType.isEmpty()){
				System.out.println("PMParser.Load:获取模型参数类型非法");
				continue;
			}	    					
			if (strType.contains(";")){
				String[] strs = strType.split(";");
				if (null == strs || strs.length <= 0){
					System.out.println("PMParser.Load:解析模型参数类型非法");
					continue;
				}
				strType = strs[0];
			}
				
			mpi.setValue(gpManager.createObject(strType, emt.getAttribute("variable")));
            	
            //解析扩展属性
            nnm = emt.getAttributes();
            if (null != nnm){
            	for (int m = 0; m < nnm.getLength(); ++m){
            		org.w3c.dom.Node an = nnm.item(m);
            		String astr = an.getNodeName();
            		if (astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionType) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract) || astr.equalsIgnoreCase(_XMLTag.g_AttributionVariable)){
            			continue;
            		}
            		mpi.setProperty(astr, an.getNodeValue());
            	}
            }
            
            //reference
            references = emt.getElementsByTagName(_XMLTag.g_NodeReference);
			if (null != references){
				com.alibaba.fastjson.JSONArray ja = new com.alibaba.fastjson.JSONArray();
				for (j = 0; j < references.getLength(); ++j){
					node = (Element)references.item(j);
					mpi.addRefProcess(Integer.parseInt(node.getAttribute(_XMLTag.g_AttributionId)),
							"",
							node.getAttribute(_XMLTag.g_AttributionName));
					//扩展信息
					nnm = node.getAttributes();
                	if (null != nnm){
                		com.alibaba.fastjson.JSONObject jitem = new com.alibaba.fastjson.JSONObject();
                		for (int m = 0; m < nnm.getLength(); ++m){
                			org.w3c.dom.Node an = nnm.item(m);
                			String astr = an.getNodeName();
                			if (astr.equalsIgnoreCase(_XMLTag.g_AttributionId) || astr.equalsIgnoreCase(_XMLTag.g_Attribution_type) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract)){
                				continue;
                			}
                			jitem.put(astr, an.getNodeValue());
                		}
                		if (jitem.size() > 0){
                			jitem.put(_XMLTag.g_AttributionId, Integer.parseInt(node.getAttribute(_XMLTag.g_AttributionId)));
                			jitem.put(_XMLTag.g_AttributionName, node.getAttribute(_XMLTag.g_AttributionName));
                			ja.add(jitem);
                		}
                	}	                	
				}
				if (ja.size() > 0){
					mpi.setProperty("reference", ja.toJSONString());
				}
			}			
            
            minputs.add(mpi);        	
        }   
        MParaItem[] mparas = new MParaItem[minputs.size()];
        minputs.toArray(mparas);
        group.setInputs(mparas);
        
        
        ArrayList<ParaItem> piArray = new ArrayList<ParaItem>();
        nlist = nodeGroup.getElementsByTagName("outputs");
        if (null == nlist || nlist.getLength() <= 0){                
        	return;                    	
        }
        emt = (Element) (nlist.item(0));
        nlist = emt.getElementsByTagName("output");
        ParaItem para = null;
        for (j = 0; j < nlist.getLength(); j++){                    	
        	emt = (Element)(nlist.item(j));
        	if (null == emt){                    		
        		continue;
        	}
             	
        	String strType = emt.getAttribute(_XMLTag.g_AttributionType);
        	para = new ParaItem(emt.getAttribute(_XMLTag.g_AttributionName), strType);
			if (null == strType || strType.isEmpty()){
				System.out.println("PMParser.Load:获取模型参数类型非法");
				continue;
			}	    					
			if (strType.contains(";")){
				String[] strs = strType.split(";");
				if (null == strs || strs.length <= 0){
					System.out.println("PMParser.Load:解析模型参数类型非法");
					continue;
				}
				strType = strs[0];
			}
					                    	
            //解析扩展属性
            nnm = emt.getAttributes();
            if (null != nnm){
            	for (int m = 0; m < nnm.getLength(); ++m){
            		org.w3c.dom.Node an = nnm.item(m);
            		String astr = an.getNodeName();
            		if (astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionType) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract) || astr.equalsIgnoreCase(_XMLTag.g_AttributionVariable)){
            			continue;
            		}
            		para.setProperty(astr, an.getNodeValue());
            	}
            }
            piArray.add(para);                    	
        }   
        ParaItem[] paras = new ParaItem[piArray.size()];
        piArray.toArray(paras);
        group.setOutputs(paras);
        
        //process
        ArrayList<ProcessDescription> processList = new ArrayList<ProcessDescription>();
        //强制类型转换后面跟一个空格
        nlist = nodeGroup.getElementsByTagName("flow");
        if (null == nlist || nlist.getLength() <= 0){                
        	return;                    	
        }
        emt = (Element) (nlist.item(0));
        Element nodeStart = (Element) emt.getElementsByTagName("start").item(0);
        if (null == nodeStart){
        	System.out.println("PMParser.Load:获取开始节点失败");
            return;
        }         
        ProcessDescription process = new ProcessDescription("start", Integer.parseInt(nodeStart.getAttribute("id")), "", "");
        processList.add(process);        
        
        
        Element element = null;
        Element elePara = null;
        ArrayList<ParaItem> inputs = null;
        NodeList nodes = emt.getElementsByTagName("operation");
        for (i = 0; i < nodes.getLength(); i++){
        	element = (Element) (nodes.item(i));
        	
        	process = new ProcessDescription(element.getAttribute("name"),
        			Integer.parseInt(element.getAttribute("id")), element.getAttribute("title"), element.getAttribute("abstracts"));
        	//解析扩展属性
        	nnm = element.getAttributes();
        	if (null != nnm){
        		for (int m = 0; m < nnm.getLength(); ++m){
        			org.w3c.dom.Node an = nnm.item(m);
        			String astr = an.getNodeName();
        			if (astr.equalsIgnoreCase(_XMLTag.g_AttributionId) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract)){
        				continue;
        			}
        			process.setProperty(astr, an.getNodeValue());
        		}
        	}
        	
            inputs = new ArrayList<ParaItem>();
            nlist = element.getElementsByTagName("inputs");
            if (null == nlist || nlist.getLength() <= 0){                
            	continue;                    	
            }
            elePara = (Element) (nlist.item(0));
            nlist = elePara.getElementsByTagName("input");
                  
            for (j = 0; j < nlist.getLength(); j++){                    	
            	elePara = (Element)(nlist.item(j));
            	if (null == elePara){                    		
            		continue;
            	}
                 	
            	if (elePara.hasAttribute(_XMLTag.g_AttributionBinding)){                    		
            		mpi = findMParser(elePara.getAttribute(_XMLTag.g_AttributionBinding));
            		if (null != mpi){
            		}
            	}
            	else{                    		
                	para = new ParaItem(elePara.getAttribute(_XMLTag.g_AttributionName), elePara.getAttribute(_XMLTag.g_AttributionType));
                	para.setDataType(elePara.getAttribute(_XMLTag.g_AttributionType));
                	
                	String strType = para.getDataType();
					if (null == strType || strType.isEmpty()){
						System.out.println("PMParser.Load:获取模型参数类型非法");
						continue;
					}	    					
					if (strType.contains(";")){
						String[] strs = strType.split(";");
    					if (null == strs || strs.length <= 0){
    						System.out.println("PMParser.Load:解析模型参数类型非法");
    						continue;
    					}
    					strType = strs[0];
					}
					
                	para.setValue(gpManager.createObject(strType, elePara.getAttribute("variable")));
                	
                	//解析扩展属性
                	nnm = elePara.getAttributes();
                	if (null != nnm){
                		for (int m = 0; m < nnm.getLength(); ++m){
                			org.w3c.dom.Node an = nnm.item(m);
                			String astr = an.getNodeName();
                			if (astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionType) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract) || astr.equalsIgnoreCase(_XMLTag.g_AttributionVariable)){
                				continue;
                			}
                			para.setProperty(astr, an.getNodeValue());
                		}
                	}
                	inputs.add(para);
            	}
            }   
            paras = new ParaItem[inputs.size()];
            inputs.toArray(paras);
            process.setInputs(paras);
            
            piArray = new ArrayList<ParaItem>();
            nlist = element.getElementsByTagName("outputs");
            if (null == nlist || nlist.getLength() <= 0){                
            	continue;                    	
            }
            elePara = (Element) (nlist.item(0));
            nlist = elePara.getElementsByTagName("output");
            for (j = 0; j < nlist.getLength(); j++){                    	
            	elePara = (Element)(nlist.item(j));
            	if (null == elePara){                    		
            		continue;
            	}
                 	
            	para = new ParaItem(elePara.getAttribute(_XMLTag.g_AttributionName), elePara.getAttribute(_XMLTag.g_AttributionType));
                para.setDataType(elePara.getAttribute(_XMLTag.g_AttributionType));
                	
                String strType = para.getDataType();
				if (null == strType || strType.isEmpty()){
					System.out.println("PMParser.Load:获取模型参数类型非法");
					continue;
				}	    					
				if (strType.contains(";")){
					String[] strs = strType.split(";");
    				if (null == strs || strs.length <= 0){
    					System.out.println("PMParser.Load:解析模型参数类型非法");
    					continue;
    				}
    				strType = strs[0];
				}
						                    	
                //解析扩展属性
                nnm = elePara.getAttributes();
                if (null != nnm){
                	for (int m = 0; m < nnm.getLength(); ++m){
                		org.w3c.dom.Node an = nnm.item(m);
                		String astr = an.getNodeName();
                		if (astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionType) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract) || astr.equalsIgnoreCase(_XMLTag.g_AttributionVariable)){
                			continue;
                		}
                		para.setProperty(astr, an.getNodeValue());
                	}
                }
                piArray.add(para);                    	
            }   
            paras = new ParaItem[piArray.size()];
            piArray.toArray(paras);
            process.setOutputs(paras);
            
            processList.add(process);
        }
        
        //子group
        nodes = emt.getElementsByTagName("group");
        GroupDescription subgroup = null;
        for (i = 0; i < nodes.getLength(); i++){
        	element = (Element) (nodes.item(i));          	
        	subgroup = new GroupDescription(Integer.parseInt(element.getAttribute("id")),element.getAttribute("type"));
        	if (element.hasAttribute(_XMLTag.g_AttributionName)){
        		group.setName(element.getAttribute(_XMLTag.g_AttributionName));
        	}
        	if (element.hasAttribute(_XMLTag.g_AttributionTitle)){
        		group.setTitle(element.getAttribute(_XMLTag.g_AttributionTitle));
        	}
        	if (element.hasAttribute(_XMLTag.g_AttributionAbstract)){
        		group.setAbstracts(element.getAttribute(_XMLTag.g_AttributionAbstract));
        	}
        	//解析扩展属性
        	nnm = element.getAttributes();
        	if (null != nnm){
        		for (int m = 0; m < nnm.getLength(); ++m){
        			org.w3c.dom.Node an = nnm.item(m);
        			String astr = an.getNodeName();
        			if (astr.equalsIgnoreCase(_XMLTag.g_AttributionId) || astr.equalsIgnoreCase(_XMLTag.g_Attribution_type) || astr.equalsIgnoreCase(_XMLTag.g_AttributionName) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTitle) || astr.equalsIgnoreCase(_XMLTag.g_AttributionAbstract)){
        				continue;
        			}
        			group.setProperty(astr, an.getNodeValue());
        		}
        	}
        	
        	parserGroup(subgroup,element);
        	processList.add(subgroup);
        }
        
        Element nodeEnd = (Element) emt.getElementsByTagName("end").item(0);
        if (null == nodeEnd){
        	System.out.println("PMParser.Load:获取开始节点失败");
            return;
        }         
        process = new ProcessDescription("end", Integer.parseInt(nodeEnd.getAttribute("id")), "", "");
        processList.add(process);
        
        ProcessDescription[] pds = new ProcessDescription[processList.size()];
        processList.toArray(pds);
        group.setProcesses(pds);
        
        ArrayList<LinkDescription> linkList = new ArrayList<LinkDescription>();
        LinkDescription link = null;
        nlist = emt.getElementsByTagName(_XMLTag.g_NodeFlow);
        if (null == nlist || nlist.getLength() <= 0){
        	System.out.println("PMParser.Load:获取流程节点失败");
        	return;
        }                
        element = (Element) (nlist.item(0));
        nlist = element.getElementsByTagName(_XMLTag.g_NodeLinks);
        if (null == nlist || nlist.getLength() <= 0){
        	System.out.println("PMParser.Load:获取连接节点失败");
        	return;
        }                
        element = (Element) (nlist.item(0));        
        nodes = element.getElementsByTagName(_XMLTag.g_NodeLink);                           
        NodeList assigns = null;
        Element assignNode = null;
        Element ftNode = null;
        Assignment assign = null;
        for (i = 0; i < nodes.getLength(); i++){                	
        	element = (Element) (nodes.item(i));                	
        	if (null == element){
        		continue;
        	}
        	
        	link = new LinkDescription(Integer.parseInt(element.getAttribute(_XMLTag.g_AttributionFrom)), 
        			Integer.parseInt(element.getAttribute(_XMLTag.g_AttributionTo)));
        	//解析扩展属性
            nnm = element.getAttributes();
            if (null != nnm){
            	for (int m = 0; m < nnm.getLength(); ++m){
            		org.w3c.dom.Node an = nnm.item(m);
            		String astr = an.getNodeName();
            		if (astr.equalsIgnoreCase(_XMLTag.g_AttributionFrom) || astr.equalsIgnoreCase(_XMLTag.g_AttributionTo)){
            			continue;
            		}
            		link.setProperty(astr, an.getNodeValue());
            	}
            }
        	
        	assigns = element.getElementsByTagName(_XMLTag.g_NodeAssign);                	
        	for (j = 0; j < assigns.getLength(); ++j){
        		assignNode = (Element) (assigns.item(j));
        		if (null == assignNode){
        			continue;
        		}
        		assign = new  Assignment();
        		assign.setSourceVariable(assignNode.getElementsByTagName(_XMLTag.g_NodeFrom).item(0).getTextContent());
        		assign.setTargetVariable(assignNode.getElementsByTagName(_XMLTag.g_NodeTo).item(0).getTextContent());
        		
        		ftNode = (Element)(assignNode.getElementsByTagName(_XMLTag.g_NodeFrom).item(0));
        		nnm = ftNode.getAttributes();
                if (null != nnm){
                	for (int m = 0; m < nnm.getLength(); ++m){
                		org.w3c.dom.Node an = nnm.item(m);
                		String astr = an.getNodeName();
                		assign.setProperty("from." + astr, an.getNodeValue());
                	}
                }
                ftNode = (Element)(assignNode.getElementsByTagName(_XMLTag.g_NodeTo).item(0));
        		nnm = ftNode.getAttributes();
                if (null != nnm){
                	for (int m = 0; m < nnm.getLength(); ++m){
                		org.w3c.dom.Node an = nnm.item(m);
                		String astr = an.getNodeName();
                		assign.setProperty("to." + astr, an.getNodeValue());
                	}
                }
            	                    		
        		link.add(assign);
        	}
            linkList.add(link);
        }

        LinkDescription[] lds = new LinkDescription[linkList.size()];
        linkList.toArray(lds);
        group.setLinks(lds);        
	}
	
	private MParaItem findMParser(String name){
		try{
			if (null == this._inputs){
				return null;
			}
			
			int i = 0;
			MParaItem mpi = null;
			for (i = 0; i < this._inputs.size(); ++i){
				mpi = this._inputs.get(i);
				if (null == mpi){
					continue;
				}
				if (mpi.getName().equalsIgnoreCase(name)){
					return mpi;
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}
}
