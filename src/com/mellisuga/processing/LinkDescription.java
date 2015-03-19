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
 * @created 10-1-2011 21:08:55
 */
public class LinkDescription extends java.util.Properties{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _fromId = 0;
	private int _toId = 0;
	private ArrayList<Assignment> _assignments = new ArrayList<Assignment>();
	
	public LinkDescription(){
	}

	public LinkDescription(int fId, int tId){
		_fromId = fId;
		_toId = tId;
	}
	
	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param value
	 */
	public boolean add(Assignment value){
		return this._assignments.add(value);		
	}

	public Assignment[] assignments(){
		Assignment[] items = new Assignment[this._assignments.size()];
		this._assignments.toArray(items);
		return items;
	}

	public int fromId(){
		return this._fromId;
	}

	/**
	 * 
	 * @param value
	 */
	public boolean remove(Assignment value){
		return this._assignments.remove(value);		
	}

	public int toId(){
		return this._toId;
	}

}