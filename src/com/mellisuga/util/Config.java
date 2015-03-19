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
package com.mellisuga.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.mellisuga.core.InstanceManager;
import com.mellisuga.processing.IContext;


public class Config {
	private static Properties _propertie = null;
	private static Document _doc = null;
	
	static {
		try {			
			IContext context = InstanceManager.getInstance().getContext();	
			String strDir = "";
			if (null != context){				
				strDir = (String)(context.getVariable("BaseDirectory"));				
			}			
			FileInputStream inputFile = new FileInputStream(strDir + "/config/config.properties");
			_propertie = new Properties();
			_propertie.load(inputFile);
			inputFile.close();
			
			//config.xml
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder builder = factory.newDocumentBuilder();
        	_doc = builder.parse(new File(strDir + "/config/config.xml"));        	
		} catch (FileNotFoundException ex) {
			_propertie = null;
			System.out.println("Config，静态加载出错。读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			_propertie = null;
			System.out.println("Config，静态加载出错。装载文件--->失败!");
			System.out.println("Config:" + ex.getMessage());
		}
		 catch(Exception ex){  
			 _propertie = null;
			 System.out.println("Config:" + ex.getMessage());
	    }		
	}
	
	
	public static String[] factorys(){
		if (null == _doc){
			System.out.println("Config.factorys:文档非法");
			return null;
		}
		
		try{
			Element factorys = (Element)(_doc.getElementsByTagName("factorys").item(0));
			
			if (null == factorys){	
				System.out.println("Config.factorys:获取工厂列表结点失败");
				return null;
			}
			NodeList nodes = factorys.getElementsByTagName("factory");			
			if (null == nodes){
				System.out.println("Config.factorys:获取工厂列表失败");
				return null;
			}
			
			Node node = null;
			ArrayList<String> strs = new ArrayList<String>(); 
			for (int i = 0; i < nodes.getLength(); ++i){
				node = nodes.item(i);
				if (null == node){
					continue;
				}
				strs.add(node.getTextContent());
			}
			
			String[] arr = new String[strs.size()];			
			strs.toArray(arr);
			strs = null;
			return arr;
		}catch(Exception ex){
			System.out.println("Config.factorys:" + ex.getMessage());			
		}		
		return null;
	}
}
