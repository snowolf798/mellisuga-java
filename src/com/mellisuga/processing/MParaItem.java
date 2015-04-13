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

public class MParaItem extends ParaItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> _pid = new ArrayList<Integer>();	
	private ArrayList<String> _processname = new ArrayList<String>();
	private ArrayList<String> _paraname = new ArrayList<String>();
	
	public MParaItem(String name, String type){
		this.setName(name);
		this.setDataType(type);
	}
	
	public ArrayList<Integer> getProcessIds(){
		return this._pid;
	}
	
	public ArrayList<String> getProcessNames(){
		return this._processname;
	}
	
	public ArrayList<String> getParaNames(){
		return this._paraname;
	}
	
	public void addRefProcess(int id,String name,String para){
		_pid.add(id);
		_processname.add(name);
		_paraname.add(para);
	}
}
