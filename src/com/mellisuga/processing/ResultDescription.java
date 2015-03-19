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

public class ResultDescription {

	private String _name = "";
	private String _processName = "";
	private int _processId = -1;
	private String _resultParaName = "";
	private String _kind = "";	
	private String _event = "";
	
	public ResultDescription(String kind, String name, String processName, int processId, String resultParaName,String event){
		 _name = name;
		_processName = processName;
		_processId = processId;
		_resultParaName = resultParaName;
		_kind = kind;
		_event = event;
	}
	
	public String name(){
		return _name;
	}
	
	public String getProcessName(){
		return _processName;
	}
	
	public int getProcessId(){
		return _processId;
	}
	
	public String getResultParaName(){
		return _resultParaName;
	}
	
	public String getKind(){
		return _kind;
	}
	
	public String getEvent(){
		return _event;
	}
}
