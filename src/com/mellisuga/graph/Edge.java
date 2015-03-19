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
package com.mellisuga.graph;

/**
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2011 21:07:34
 */
public class Edge implements IEdge {
	
	public Edge(){
	}
	
	public Edge(int id, INode fromNode, INode toNode, Object data){
        _id = id;
        _node1 = fromNode;
        _node2 = toNode;
        this._data = data;               
    }	

	public void finalize() throws Throwable {

	}

	public boolean isDirectional(){
		return _directional;
	}

	/**
	 * The first node associated with this edge.
	 * @return The first node associated with this edge.
	 * */
	public INode node1(){
		return _node1;
	}
	
	/**
	 * The second node associated with this edge.
	 * @return The second node associated with this edge.
	 * */
	public INode node2(){
		return _node2;
	}

    /**
	 * The id of this edge.
	 * @return the id of this edge
	 * */
	public int getId(){        
		return _id;       
	}
	
	public INode fromNode(){		
		if(_directional) {
			return _node1;
		}
		return null;
	}

	public INode toNode(){
		if(_directional) {
			return _node2;
		}
		return null;
	}
	
	/**
	 * 
	 * @param node
	 */
	public INode otherNode(INode node){
		if (node == _node1){
            return _node2;
        }
        else if (node == _node2){
            return _node1;
        }
        else{
            return null;
        }
	}

	public void setData(Object o) {
		_data = o;
	}
	
	public Object getData()	{
		return _data;
	}

	private int _id = 0;
    private INode _node1 = null;
    private INode _node2 = null;
    private Object _data = null;
    private boolean _directional = true;
}