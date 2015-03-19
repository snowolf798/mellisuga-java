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

import java.util.ArrayList;
import java.util.List;
import com.mellisuga.processing.IConnector;

public class Toolkit {
	public static String[] StringArrayToStrings(ArrayList<String> array){
		if (null == array){
			return new String[0];
		}
		String[] strs = new String[array.size()]; 
		array.toArray(strs);
		return strs;
	}
	
	public static IConnector[] arrayToConnectors(ArrayList<IConnector> array){
		if (null == array){
			return new IConnector[0];
		}
		IConnector[] strs = new IConnector[array.size()]; 
		array.toArray(strs);
		return strs;
	}
	
	public static boolean containString(List<String> list,String str){
		if (null == list || null == str || str.isEmpty()){
			return false;
		}
		int i = 0;
		String s = null;
		for (i = 0; i<list.size(); ++i){
			s = list.get(i);
			if (str.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean containString(String[] list,String str){
		if (null == list || null == str || str.isEmpty()){
			return false;
		}
		int i = 0;
		String s = null;
		for (i = 0; i<list.length; ++i){
			s = list[i];
			if (str.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNum(String str){		
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");	
	}
	
	public static int[] stringsToInts(String[] strs){
		if (null == strs){
			return new int[0];
		}
		int[] ints = new int[strs.length]; 
		for (int i = 0; i < strs.length; ++i){
			ints[i] = Integer.parseInt(strs[i]);
		}
		return ints;
	}
	
	public static ArrayList<Integer> stringsToIntArray(String[] strs){
		if (null == strs){
			return new ArrayList<Integer>(0);
		}
		ArrayList<Integer> ints = new ArrayList<Integer>(); 
		for (int i = 0; i < strs.length; ++i){
			if (strs[i].isEmpty()){
				continue;
			}
			ints.add(Integer.parseInt(strs[i]));
		}
		return ints;
	}
	
	public static long[] stringsToLongs(String[] strs){
		if (null == strs){
			return new long[0];
		}
		long[] longs = new long[strs.length]; 
		for (int i = 0; i < strs.length; ++i){
			longs[i] = Long.parseLong(strs[i]);
		}
		return longs;
	}
}
