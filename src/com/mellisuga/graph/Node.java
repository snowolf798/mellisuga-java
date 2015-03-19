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

import java.util.List;
import java.util.ArrayList;

/**
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2011 21:09:28
 */
public class Node implements INode{
	private Object _data = null;
	private int _id = -1;
	private List<IEdge> _inEdges = new ArrayList<IEdge>();
	private List<IEdge> _outEdges = new ArrayList<IEdge>();
	private List<INode> _predecessors = new ArrayList<INode>();
	private List<INode> _successors = new ArrayList<INode>();

	public void finalize() throws Throwable {
	}

	public Node(){
	}

	/**
	 * 
	 * @param id
	 * @param data
	 */
	public Node(int id, Object data){
		this._id = id;
		this._data = data;
	}

	/**
	 * 
	 * @param e
	 */
	public void addInEdge(IEdge e){
		myAddInEdge(e);
	}
	
	/**
	 * 
	 * @param e
	 */
	public void addOutEdge(IEdge e){
		myAddOutEdge(e);
	}
	
	public void removeInEdge(IEdge e) {
		myRemoveInEdge(e);
	}
	
	/**
	 * @inheritDoc
	 * */
	public void removeOutEdge(IEdge e) {
		myRemoveOutEdge(e);
	}

	/**
	 * @inheritDoc
	 */
	public int getId(){
		return this._id;
	}
	
	public void setData(Object o){
		this._data = o;
	}
	
	public Object getData(){
		return this._data;
	}

	public List<IEdge> inEdges(){
		return this._inEdges;
	}

	public List<IEdge> outEdges(){
		return this._outEdges;
	}

	public List<INode> predecessors(){
		return _predecessors;
	}

	public List<INode> successors(){
		return _successors;
	}

	private void myAddInEdge(IEdge e){
		if(e.otherNode(this) == null){
			return;
		}
		
		this._inEdges.add(e);
		this._predecessors.add(e.otherNode(this));
	}

	private void myAddOutEdge(IEdge e){
		if(e.otherNode(this) == null){
			return;
		}
		
		this._outEdges.add(e);
		this._successors.add(e.otherNode(this));
	}
	
	private void myRemoveInEdge(IEdge e) {
		/* get the other node, as it must be deleted
		 * from the predecessor list */
		INode otherNode = e.otherNode(this); // because it is an IN edge
		int theEdgeIndex = _inEdges.indexOf(e);
		int theNodeIndex = _predecessors.indexOf(otherNode);
		
		if(theEdgeIndex == -1) {			
		} else {
			_inEdges.remove(theEdgeIndex);
		}
		if(otherNode == null) {
		}
		if(theNodeIndex  == -1) {
		} else {
			_predecessors.remove(theNodeIndex);
		}
	}

	private void myRemoveOutEdge(IEdge e) {
		/* get the other node, as it must be deleted
		 * from the successor list */
		INode otherNode = e.otherNode(this); // because it is an OUT edge
		int theEdgeIndex = _outEdges.indexOf(e);
		int theNodeIndex = _successors.indexOf(otherNode);
		
		if(theEdgeIndex == -1) {
		} else {
			_outEdges.remove(theEdgeIndex);
		}
		
		if(theNodeIndex  == -1) {
		} else {
			_successors.remove(theEdgeIndex);
		}
	}
}