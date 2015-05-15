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

import java.util.concurrent.ConcurrentHashMap;

import com.mellisuga.processor.ProcessIterator;
import com.mellisuga.util.LicenseProvider;



/**
 * 建模管理器类<br>
 * <p>负责建模工厂和数据处理构件的管理，它是获取数据处理构件、连接器以及用户类型对象的途径之一。<br>
 * <p>建模管理器负责建模工厂的管理，它负责从建模工厂中查找数据处理构件、连接器、用户类型对象，并返回给用户；建模管理器实现了建模工厂动态注册和反注册，并对其及包含的数据处理构件进行管理中。<br>
 * <p>数据处理构件、连接器以及用户类型对象都是直接通过建模管理器来创建的，但实质是，建模管理器去建模工厂获取创建数据处理构件、连接器和用户类型对象，然后，再通过建模管理器返回给用户。<br>
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:08:03
 */
public class ProcessingManagerImpl implements IProcessingManager {

	private static ConcurrentHashMap<Class<?>, IPMFactory> _factorys = new ConcurrentHashMap<Class<?>, IPMFactory>();
	private static ConcurrentHashMap<String, IPMFactory> _processes = new ConcurrentHashMap<String, IPMFactory>();
	private static ConcurrentHashMap<String, IPMFactory> _objects = new ConcurrentHashMap<String, IPMFactory>();
	private static int _id = 0;		
	
	private static class Holder 
	{    
		public static final ProcessingManagerImpl instance = new ProcessingManagerImpl();    
	}    
	/**
	 * 获取单件实例
	 * @return 唯一实例
	 */
	public static ProcessingManagerImpl instance() 
	{    
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}
		return Holder.instance;
	}

	private ProcessingManagerImpl(){		
	}

	public IPMParser newParser()
	{		
		return new PMParserImpl();
	}
	
	public IPMDispatcher newDispatcher()
	{		
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}
		return new PMDispatcherImpl();
	}
	
	public IConnector connectorConvey(){
		return new ConveyConnector();
	}

	public boolean register(String factoryName){
		if (!LicenseProvider.getInstance().validate("", "")){
			return false;
		}
		return myRegister(factoryName);
	}

	public boolean register(String[] factoryNames){	
		try{				
			if (null == factoryNames){
				return false;
			}
			boolean result = true;
	        for(int i = 0; i < factoryNames.length; i++){
		        result = this.register(factoryNames[i]) && result;
	        }
	        return result;	
		}
		catch(Exception ex) {
			
		}
		return false;
	}

	public boolean unregister(String factoryName){
		try{
			Class<?> c = Class.forName(factoryName);
			
			if (!_factorys.containsKey(c)){
				return false;
			}
			_factorys.remove(c);
			return true;
		}
		catch(Exception ex) {
			
		}
		return false;
	}

	public IProcess createProcess(String name){
		return myCreateProcess(name);
	}
	
	public IProcess createProcess(String type,Object data){
			try{			
		        if (!_processes.containsKey(type)){
		        	System.out.println("'" + type + "'不在管理器中");
			        return null;
		        }
		        IPMFactory factory = _processes.get(type);
		        if (null == factory){
		        	System.out.println("'" + type + "'所在工厂异常");
		        	return null;
		        }
		  
		        IProcess process = factory.createProcess(type,data);
		        if (null != process){
			        process.setid(ProcessingManagerImpl.makeUniqueId());
			        return process;
		        }
	        }      
	        catch (Exception e){
	        	System.out.println("manager.createProcess: exception - " + e.getMessage());
		        return null;
	        }

	        return null;		
/*		
		IProcess p = null;
		if (type.equalsIgnoreCase("iterator")){
			p = new ProcessIterator();
			p.setInputValue("group", gd);
		}
		return null;*/
	}
	
	public IConnector createConnector(String inParaType, String outParaType){
		return myCreateConnector(inParaType, outParaType);
	}
	
	public Object createObject(String type, Object value){
		return myCreateObject(type, value);
	}
	
	public String objectToString(Object obj){
		return myObjectToString(obj);
	}

	public void release(){
		try{						
			IPMFactory factory = null;
	        IPMFactory[] fs = new IPMFactory[_factorys.size()];
	        _factorys.values().toArray(fs);
	        for (int i = 0; i < fs.length; ++i){
	        	factory = fs[i];
	        	if (null == factory){
	        		continue;
	        	}
	        	factory.release();		        
	        }
		}
		catch (Exception e){
			System.out.println("ProcessingManagerImpl.release:" + e.getMessage());
        }
	}
	
	public void loaddata(){
		try{						
			IPMFactory factory = null;
	        IPMFactory[] fs = new IPMFactory[_factorys.size()];
	        _factorys.values().toArray(fs);
	        for (int i = 0; i < fs.length; ++i){
	        	factory = fs[i];
	        	if (null == factory){
	        		continue;
	        	}
	        	factory.loaddata();		        
	        }
		}
		catch (Exception e){
			System.out.println("ProcessingManagerImpl.loaddata:" + e.getMessage());
        }
	}
	
	public void unloaddata(){
		try{						
			IPMFactory factory = null;
	        IPMFactory[] fs = new IPMFactory[_factorys.size()];
	        _factorys.values().toArray(fs);
	        for (int i = 0; i < fs.length; ++i){
	        	factory = fs[i];
	        	if (null == factory){
	        		continue;
	        	}
	        	factory.unloaddata();		        
	        }
		}
		catch (Exception e){
			System.out.println("ProcessingManagerImpl.unloaddata:" + e.getMessage());
        }
	}

	private boolean myRegister(String factoryName){
		try{
			if (null == factoryName || factoryName.isEmpty()){
				System.out.println("manager.register:工厂名字为空!");
				return false;
			}
			System.out.println("准备注册'" + factoryName + "'");
			Class<?> c = null;
			try{			
				c = Class.forName(factoryName);
			}
			catch(Exception ex) {
				System.out.println("manager.register:" + ex.getMessage());
			}
			if (null == c){
				System.out.println("manager.register:未找到类'" + factoryName + "'");
				return false;
			}
			
			if (_factorys.containsKey(c)){
				System.out.println("manager.register:工厂已加载'" + factoryName + "'!");
				return false;
			}
						
			Class<?>[] implInterfaces = c.getInterfaces();
			boolean isFactory = false;
			for (int i = 0; i < implInterfaces.length; ++i){
				if (implInterfaces[i].getName().compareToIgnoreCase("com.mellisuga.processing.IPMFactory") == 0){
					isFactory = true;
					break;
				}
			}
			if (!isFactory){
				System.out.println("manager.register:工厂'" + factoryName + "'非法");
				return false;
			}
			
			Object obj = c.newInstance();
			IPMFactory factory = (IPMFactory)obj;
			if (null == factory){
				System.out.println("manager.register:创建工厂对象'" + factoryName + "'失败!");
				return false;
			}
			_factorys.put(c, factory);
			
			String[] strs = factory.processes();
			if (null != strs){
				for (int i = 0; i < strs.length; ++i){
					if (_processes.containsKey(strs[i])){
						System.out.println("manager.register:数据处理'" + strs[i] + "'在工厂中重复!");
						return false;
					}
					_processes.put(strs[i], factory);
				}
			}

			strs = factory.objectTypes();
			if (null != strs){
				for(int i = 0; i < strs.length; ++i){
					if (_objects.containsKey(strs[i])){
						System.out.println("manager.register:支持对象'" + strs[i] + "'在工厂中重复!");
						return false;
					}
					_objects.put(strs[i], factory);
				}
			}
		
			return true;
		}
		catch(Exception ex) {
			System.out.println("manager.register: exception - '" + ex.getMessage());
		}
		return false;
	}

	private IProcess myCreateProcess(String name){
		try{			
	        if (!_processes.containsKey(name)){
	        	System.out.println("'" + name + "'不在管理器中");
		        return null;
	        }
	        IPMFactory factory = _processes.get(name);
	        if (null == factory){
	        	System.out.println("'" + name + "'所在工厂异常");
	        	return null;
	        }
	  
	        IProcess process = factory.createProcess(name);
	        if (null != process){
		        process.setid(ProcessingManagerImpl.makeUniqueId());
		        return process;
	        }
        }      
        catch (Exception e){
        	System.out.println("manager.createProcess: exception - " + e.getMessage());
	        return null;
        }

        return null;
	}

	private IConnector myCreateConnector(String inParaType, String outParaType){
		IConnector connector = null;
        try{
	        if (inParaType.equalsIgnoreCase(outParaType) || 
                    inParaType.equalsIgnoreCase("any") ||
                    outParaType.equalsIgnoreCase("any") )
	        {
		        return new ConveyConnector();
	        }

	        IPMFactory factory = null;
	        IPMFactory[] fs = new IPMFactory[_factorys.size()];
	        _factorys.values().toArray(fs);
	        for (int i = 0; i < fs.length; ++i){
	        	factory = fs[i];
	        	if (null == factory){
	        		continue;
	        	}
		        connector = factory.createConnector(inParaType, outParaType);
		        if (null != connector){
			        return connector;
		        }
	        }
        }
        catch (Exception e){
        	e.printStackTrace();
	        return null;
        }

        return connector;
	}

	private Object myCreateObject(String type, Object value){
		Object obj = null;

        try{
        	String[] strs = null;
        	if (type.contains(";")){
        		strs = type.split(";");	
        	}
        	else{
        		strs = new String[1];
        		strs[0] = type;
        	}

        	String str;
        	for (int i = 0; i < strs.length; ++i){
        		str = strs[i];
        		str = str.trim();
		        if (!_objects.containsKey(str)){
			        continue;
		        }
		        IPMFactory factory = _objects.get(str);

		        obj = factory.createObject(type, value);
		        if (null != obj){
			        return obj;
		        }
        	}
        	return value;
        }
        catch (Exception e){
	        return null;
        }
	}

	private String myObjectToString(Object obj){
		try{
			if (null == obj){
				return "";
			}
			Class<?> classType = obj.getClass();
			if (null == classType){
				return "";
			}
			String strType = classType.getName();
			if (null == strType){
				return "";
			}
			if (strType.equalsIgnoreCase("java.lang.String")){
				return (String)obj;
			}
			if (strType.equalsIgnoreCase("java.lang.Boolean") || 
					strType.equalsIgnoreCase("java.lang.Integer") ||
					strType.equalsIgnoreCase("java.lang.Long") ||
					strType.equalsIgnoreCase("java.lang.Float") ||
					strType.equalsIgnoreCase("java.lang.Double")){
				return String.valueOf(obj);			
			}						
			if (strType.equalsIgnoreCase("java.lang.Byte")){
				return (String)obj;
			}
			if (strType.equalsIgnoreCase("java.lang.Character")){
				return (String)obj;
			}
			if (strType.equalsIgnoreCase("java.lang.Short")){
				return (String)obj;
			}
			if(obj instanceof String[]){
				String[] strs = (String[])obj;
				if (null == strs || strs.length <= 0){
					return "";
				}
				StringBuilder builder = new StringBuilder();				
				builder.append(strs[0]);
				for (int i = 1; i < strs.length; ++i){
					builder.append(";" + strs[i]);
				}				
				return builder.toString();
			}
			
			IPMFactory factory = null;
	        IPMFactory[] fs = new IPMFactory[_factorys.size()];
	        _factorys.values().toArray(fs);
	        String str = null;
	        for (int i = 0; i < fs.length; ++i){
	        	factory = fs[i];
	        	if (null == factory){
	        		continue;
	        	}
	        	str = factory.objectToString(obj);
		        if (null != str){
			        return str;
		        }
	        }

			return obj.toString();
		}
		catch (Exception e){
			System.out.println("ProcessingManagerImpl.objectToString:" + e.getMessage());
        }
		return "";
	}

	private static int makeUniqueId(){		 
	    return ++ProcessingManagerImpl._id;
	}
}