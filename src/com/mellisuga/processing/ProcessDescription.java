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

/**
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 03-h  -2011 11:28:02
 */
public class ProcessDescription extends java.util.Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _abstracts = null;
	private int _id = -1;
	private ArrayList<ParaItem> _inputs = null;
	private String _name = null;
	private ArrayList<ParaItem> _outputs = null;
	private String _title = null;

	public void finalize() throws Throwable {

	}

	public ProcessDescription(){

	}

	/**
	 * 
	 * @param name
	 * @param id
	 * @param caption
	 * @param description
	 */
	public ProcessDescription(String name, int id, String title, String abstracts){
		this._name = name;
		this._title = title;
		this._id = id;
		this._abstracts = abstracts;
	}

	public void setId(int value){
		_id = value;
	}
	
	public int getId(){
		return _id;
	}
	
	public void setName(String value){
		_name = value;
	}
	
	public String getName(){
		return _name;
	}
	
	public void setTitle(String value){
		_title = value;
	}
	
	public String getTitle(){
		return _title;
	}
	
	public void setAbstracts(String value){
		_abstracts = value;
	}
	
	public String getAbstracts(){
		return _abstracts;
	}

	public void setInputs(ParaItem[] value){		
		_inputs = new ArrayList<ParaItem>();		
		for (int i = 0; i < value.length; ++i){			
			_inputs.add(value[i]);
        }    		
	}
	
	public ParaItem[] getInputs(){	
		ParaItem[] items = new ParaItem[this._inputs.size()];
		this._inputs.toArray(items);
		
		return items;
	}

	public void setOutputs(ParaItem[] value){
		_outputs = new ArrayList<ParaItem>();		
		for (int i = 0; i < value.length; ++i){
			_outputs.add(value[i]);
        }  
	}
	
	public ParaItem[] getOutputs(){
		ParaItem[] items = new ParaItem[this._outputs.size()];
		this._outputs.toArray(items);
		return items;
	}

}