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
 * @created 1-29-2015
 */
public class GroupDescription extends ProcessDescription {
	private ArrayList<MParaItem> _inputs = null;
	private ArrayList<MParaItem> _outputs = null;
	private ArrayList<LinkDescription> _links = null;
	private ArrayList<ProcessDescription> _processes = null;
	private String _type = "";
	
	/**
	 * 
	 * @param name
	 * @param id
	 * @param caption
	 * @param description
	 */
	public GroupDescription(int id, String type){
		this._type = type;
		this.setId(id);
	}
	
	public String getType(){
		return _type;
	}
	
	public void setInputs(MParaItem[] value){		
		_inputs = new ArrayList<MParaItem>();		
		for (int i = 0; i < value.length; ++i){			
			_inputs.add(value[i]);
        }    		
	}
	
	public MParaItem[] getInputs(){	
		MParaItem[] items = new MParaItem[this._inputs.size()];
		this._inputs.toArray(items);		
		return items;
	}
	
	public void setOutputs(MParaItem[] value){
		_outputs = new ArrayList<MParaItem>();		
		for (int i = 0; i < value.length; ++i){
			_outputs.add(value[i]);
        }  
	}
	
	public MParaItem[] getOutputs(){
		MParaItem[] items = new MParaItem[this._outputs.size()];
		this._outputs.toArray(items);
		return items;
	}
	
	public void setLinks(LinkDescription[] value){	
		_links = new ArrayList<LinkDescription>();		
		for (int i = 0; i < value.length; ++i){			
			_links.add(value[i]);
        }    
	}
	
	public LinkDescription[] getLinks(){	
		LinkDescription[] items = new LinkDescription[this._links.size()];
		this._links.toArray(items);		
		return items;
	}

	public void setProcesses(ProcessDescription[] value){	
		_processes = new ArrayList<ProcessDescription>();		
		for (int i = 0; i < value.length; ++i){			
			_processes.add(value[i]);
        }    
	}
	
	public ProcessDescription[] getProcesses(){	
		ProcessDescription[] items = new ProcessDescription[this._processes.size()];
		this._processes.toArray(items);		
		return items;
	}
}
