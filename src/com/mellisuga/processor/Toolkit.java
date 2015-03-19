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

import java.io.File;
import java.util.ArrayList;


/**
 * 
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2011 21:10:38
 */
final class Toolkit {
	public void finalize() throws Throwable {

	}

	public Toolkit(){

	}

	public static ArrayList<String> listFile(String dir, String filter){
		try{
			ArrayList<String> files = new ArrayList<String>();

			File root = new File(dir);
			if(!root.isDirectory()){
				files.add(dir);				
				return files;
			}
			
			String[] exts = filter.split(",");
			File[] fList = root.listFiles();
			String fname = "";
			String ext = "";
			for(int j=0;j<fList.length;j++){
				if(fList[j].isDirectory()){
					files.addAll(listFile(fList[j].getPath(), filter));
				}
				else{
					fname = fList[j].getPath();
					ext = fname.substring(fname.lastIndexOf(".") + 1);
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
				  
				  
}