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
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import com.mellisuga.graph.IEdge;
import com.mellisuga.graph.INode;

/**
 * 
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2011 21:10:38
 */
public final class Toolkit {

	public void finalize() throws Throwable {

	}

	public Toolkit(){

	}

	static boolean validate(){
		return false;
	}
		
	/**
	 * 
	 * @param keys
	 * @param key
	 */
	public static boolean contains(String[] keys, String key){
		return false;
	}

	/**
	 * 
	 * @param pathName
	 */
	public static String getFullPathName(String pathName){
		return "";
	}

	/**
	 * 
	 * 
	 * @returns
	 * 
	 * @param parameters
	 * @param parameterName
	 */
	public static ParaItem getParaItem(ParaItem[] parameters, String parameterName){
		if (null == parameters || parameters.length <= 0 || null == parameterName || parameterName.isEmpty()){
			return null;
		}
		ParaItem pi = null;
		for (int i = 0; i < parameters.length; ++i){
			pi = parameters[i];
			if (null == pi){
				continue;
			}
			if (parameterName.equalsIgnoreCase(pi.getName())){
				return pi;
			}
		}
		return null;
	}

	public static String getNodeName(INode n){
		try{
			if (null == n){
				return "";
			}			
			ProcessDescription pd = (ProcessDescription)(n.getData());
			return pd.getName();
		}
		catch(Exception ex){
			ex.printStackTrace();		
		}
		return "";
	}
	
	public static String getNodeType(INode n){
		try{
			if (null == n){
				return "";
			}			
			ProcessDescription pd = (ProcessDescription)(n.getData());
			return pd.getProperty("type");
		}
		catch(Exception ex){
			ex.printStackTrace();		
		}
		return "";
	}
	
	public static Map<String, String> getAssigns(IEdge e){
		try{
			if (null == e){
				return null;
			}
						
			Map<String, String> kws = new HashMap<String,String>(); 
			LinkDescription ld = (LinkDescription)(e.getData());
			if (null == ld){
				return null;
			}
			Assignment[] ams = ld.assignments();
			if (null == ams){
				return null;
			}
			Assignment am = null;
			for (int i = 0; i < ams.length; ++i){
				am = ams[i];
				if (null == am){
					continue;
				}
				kws.put(am.getSourceVariable(), am.getTargetVariable());				
			}
			return kws;
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Toolkit.getAssigns:" + ex.getMessage());
		}
		return null;
	}
	
	public static ArrayList<String> listFile(String dir, String filter){
		try{
			ArrayList<String> files = new ArrayList<String>();

			File root = new File(dir);
			if(!root.isDirectory()){
				files.add(dir);
				return files;
			}
			
			String[] exts = filter.split("|");
			File[] fList = root.listFiles();
			String fname = "";
			String ext = "";
			for(int j=0;j<fList.length;j++){
				if(fList[j].isDirectory()){
					files.addAll(listFile(fList[j].getPath(), filter));
				}
				else{
					fname = fList[j].getPath();
					ext = fname.substring(fname.lastIndexOf("."));
					if (isInStrings(exts, ext)){
						files.add(fname);
					}
				}
			}
			return files;
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Toolkit.listFile:" + ex.getMessage());
		}
		return null;
	}

	public static boolean isInStrings(String[] strs, String str){
		if (null == strs || null == str || str.isEmpty()){
			return false;
		}
	
		for (int i = 0; i < strs.length; ++i){
			if (str.compareToIgnoreCase(strs[i]) == 0){
				return true;
			}
		}
		return false;
	}
		
	public static IProcess getProcessById(ArrayList<IProcessChain> pcs, int id){
		try{
			if (null == pcs){
				return null;
			}
			int i = 0;
			IProcessChain pc = null;
			IProcess p = null;
			for (i = 0; i < pcs.size(); ++i){
				pc = pcs.get(i);
				if (null == pc){
					continue;
				}
				p = pc.getProcessById(id);
				if (null != p){
					return p;
				}				
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}