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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2011 21:08:09
 */
public class Graph implements IGraph {
	
	public Graph(){
	}

	public void finalize() throws Throwable {
	}

	public String id() {
		return _id;
	}
	
	public INode[] nodes(){		
		return arrayListToNodes(_nodes);
	}
	
	public IEdge[] edges(){
		return arrayListToEdges(_edges);
	}
	
	/**
	 * 
	 * @param id
	 * @param data
	 */
	public INode createNode(int id, Object data){
		return mycreateNode(id, data);
	}	
	
	public INode createNode(String sid, Object data) {
        return mycreateNode(sid,data);
    }
	
	public void removeNode(INode n) {
		myRemoveNode(n);
	}
	
	/**
	 * 
	 * @param fromNode
	 * @param toNode
	 * @param data
	 */
	public IEdge link(INode fromNode, INode toNode, Object data){
		return myLink(fromNode, toNode, data);
	}
	
	public void unlink(INode node1, INode node2) {
		myUnlink(node1, node2);
	}
	
	/**
	 * 
	 * @param id
	 */
	public INode nodeById(int id){
		String key = String.valueOf(id);
		if (_nodesById.containsKey(key)){
            return _nodesById.get(key);
        }
        return null;
	}
		
	/**
	 * 
	 * @param n1
	 * @param n2
	 */
	public IEdge getEdge(INode n1, INode n2){
		return myGetEdge(n1, n2);
	}
	
	public void removeEdge(IEdge e) {
		myRemoveEdge(e);
	}		
	
	public int noNodes() {
		return _numberOfNodes;
	}

	/**
	 * @inheritDoc
	 * */
	public int noEdges() {
		return _numberOfEdges;
	}
	
	public void setWalkingDirection(int d){
		d = _walkingDirection;
	}

	public int getWalkingDirection(){
		return _walkingDirection;
	}

	public INode nodeByStringId(String sid) {		
		if(_nodesByStringId.containsKey(sid)) {
			return _nodesByStringId.get(sid);
		} 
		else {
			return null;
		}
	}
	
	public IGTree getTree(INode n,boolean restr, boolean nocache,int direction){
		try{
			//临时策略
			this._root = n;
			initTree();
									
			//原始代码
			// If nocache is set, we just return a new tree
            if(nocache) {
                return new GTree(n,this,restr,direction);
            }
            
            if(!_treeMap.containsKey(n)) {
                _treeMap.put(n, new GTree(n,this,restr,direction));
                // do the init now, not lazy
                ((IGTree)_treeMap.get(n)).initTree();
            }
            return (IGTree)_treeMap.get(n);
		}
		catch(Exception ex){
			
		}
		return null;
	}
	
	public void purgeTrees() {
		_treeMap = new HashMap<INode,IGTree>();
	}
	
	public void purgeGraph() {		
		while(_edges.size() > 0) {
			removeEdge(_edges.get(0));
		}
		
		while(_nodes.size() > 0) {
			removeNode(_nodes.get(0));
		}
		purgeTrees();
	}	
	
	private void initTree(){		
		ArrayList<INode> queue = new ArrayList<INode>();		
		INode dummyParent = new Node(0,null);
		
		initMaps();
		setValues(_root, dummyParent, 0, 0);	
		queue.add(this._root);
	
		INode u = null;
		int childcount = 0;
		int i = 0;
		INode adjacentNode = null;
		while(queue.size() > 0) {
			//删除数组中第一个元素
			u = queue.get(0);
			queue.remove(0);
		
			childcount = 0;
			List<INode> nodesToWalk = u.successors();			
			for (i = 0; i < nodesToWalk.size(); ++i) {
				adjacentNode = nodesToWalk.get(i);
				// check if visited before
				if(_parentMap.get(adjacentNode) == null) {				
					setValues(adjacentNode,u,_distanceMap.get(u) + 1,childcount);				
					queue.add(adjacentNode);				
					++childcount; // we have to increase here (i.e. after setValues)				
				}
			}
			this._nodeNoChildrenMap.put(u, childcount);		
		}
		// reset the dummy to null
		this._parentMap.put(_root, null);
		
		nodeSortChildren();		
	}

	private void initMaps() {				
		this._parentMap = new HashMap<INode, INode>();
		this._childrenMap = new HashMap<INode, HashMap<Integer,INode>>();
		this._distanceMap = new HashMap<INode,Integer>();
		
		_amountNodesWithDistance = new HashMap<Integer,Integer>();
		
		_nodeNoChildrenMap = new HashMap<INode,Number>();
	
		this._maxNumberPerLayer = 0;

		int i = 0;
		for(i = 0; i< _nodes.size(); ++i) {
			_parentMap.put(_nodes.get(i), null);			
		}
	}
	
	private void setValues(INode n, INode p, Integer d, int cindex) {		
		HashMap<Integer,INode> childarray = null;
	
		this._parentMap.put(n, p);
		this._distanceMap.put(n, d);
	
		// increase the count of nodes with that distance
		if(this._amountNodesWithDistance.get(d) == null) {
			this._amountNodesWithDistance.put(d, 0);
		}
		this._amountNodesWithDistance.put(d, _amountNodesWithDistance.get(d)+1);
		
		// update the maximum
		_maxNumberPerLayer = Math.max(_maxNumberPerLayer, _amountNodesWithDistance.get(d));

		// add n as the child of p in its children map
		childarray = this._childrenMap.get(p);
		if(childarray == null) {
			childarray = new HashMap<Integer,INode>();
			this._childrenMap.put(p, childarray);
		}
		
		childarray = this._childrenMap.get(p);
		childarray.put(cindex, n);
	}
	
	

	private void nodeSortChildren() {
	}
	
	//先临时这样吧，后面找一个更好的办法
	public INode[] bfsQueue(){
		return myBFSQueue();
	}
	
	private INode[] arrayListToNodes(ArrayList<INode> nodes){
		try{
			if (null == nodes){
				return null;
			}
			INode[] items = new Node[nodes.size()];
			nodes.toArray(items);
			return items;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private IEdge[] arrayListToEdges(ArrayList<IEdge> edges){
		try{
			if (null == edges){
				return null;
			}
			IEdge[] items = new Edge[edges.size()];
			edges.toArray(items);
			return items;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;		
	}
	
	private INode mycreateNode(int id, Object data){
		Node myNode = new Node(id,data);
		_nodes.add(myNode);
		_nodesById.put(String.valueOf(id), myNode);		
		_nodesByStringId.put(String.valueOf(id), myNode);
		_currentNodeId = Math.max(id, _currentNodeId);
		
		return myNode;
	}
	
	private INode mycreateNode(String sid, Object data) {               
		int myid = ++_currentNodeId;
		String mysid = sid;
       
        if(mysid.isEmpty()) {
            mysid = String.valueOf(myid);
        }
        Node myNode = new Node(myid,data);
		_nodes.add(myNode);
		_nodesById.put(String.valueOf(myid), myNode);
		_nodesByStringId.put(mysid, myNode);
                     
        return myNode;
    }
	
	private void myRemoveNode(INode n) {
		/* we check if inEdges or outEdges
		 * are not empty. This also works for
		 * non directional graphs, even though one
		 * comparison would be sufficient */
		if(n.inEdges().size() != 0 || n.outEdges().size() != 0) {
		   	//throw Error("Attempted to remove Node: "+n.id+" but it still has Edges");
		} else {
			/* XXXX searching like this through arrays takes
			 * LINEAR time, so at one point we might want to add
			 * associative arrays (possibly Dictionaries) to map
			 * the objects back to their index... */
			int myindex = _nodes.indexOf(n);
			
			/* check if node was not found */
			if(myindex == -1) {
				//throw Error("Node: "+n.id+" was not found in the graph's" +
				//"node table while trying to delete it");
			}
			
			// HMMM we assume that the throw will abort the script
			// but I am not sure, we'll see
			
			/* remove node from list */
			_nodes.remove(myindex);// .splice(myindex,1);
			--_numberOfNodes;
			
			/* invalidate trees */
			purgeTrees();
		}
	}

	private IEdge myLink(INode fromNode, INode toNode, Object data){
		try
        {            			
		    if(null == fromNode || null == toNode || null == data) {
			    return null;
		    }
		    
		    IEdge retEdge = null;		    
		    if(fromNode.successors().contains(toNode)) {							    			    
			    List<IEdge> edges = fromNode.outEdges();
			    IEdge edge = null;
			    for (int i = 0; i < edges.size(); ++i){
			    	edge = edges.get(i);
				    if(edge.otherNode(fromNode) == toNode){
					    retEdge = edge;
					    break;
				    }
			    }				   		
            } 
            else {			   
			    int newEid = ++_currentEdgeId;			    
			    Edge newEdge = new Edge(newEid,fromNode,toNode,data);
                _edges.add(newEdge);				   
			
			    /* now register the edge with its nodes */
			    fromNode.addOutEdge(newEdge);
			    toNode.addInEdge(newEdge);
			
			    /* if we are a NON directional graph we would have
			     * to add another edge also vice versa (in the other
			     * direction), but that leaves us with the question
			     * which of the edges to return.... maybe it can be
			     * handled using the same edge, if the in the directional
			     * case, the edge returns always the other node */
			    if(!_directional) {
				    fromNode.addInEdge(newEdge);
				    toNode.addOutEdge(newEdge);					    
			    }
			    retEdge = newEdge;
		    }
								
		    return retEdge;
        }
        catch (Exception ex){
        	ex.printStackTrace();
        }

        return null;
	}

	private void myUnlink(INode node1, INode node2) {
		/* find the corresponding edge first */
		IEdge e = getEdge(node1,node2);
		
		if(e == null) {
		} else {
			removeEdge(e);
		}
	}
	
	private IEdge myGetEdge(INode n1, INode n2){
		List<IEdge> outedges = n1.outEdges();
		IEdge edge = null;
		for (int i = 0; i < outedges.size(); ++i){
			edge = outedges.get(i);
			if(edge.otherNode(n1) == n2) {
				return edge;				
			}
		}			
        return null;
	}
	
	private void myRemoveEdge(IEdge e) {
		INode n1 = e.node1();
		INode n2 = e.node2();
		int edgeIndex = _edges.indexOf(e);
		
		if(edgeIndex == -1) {
			// here we would need to abort the script
		}
		
		n1.removeOutEdge(e);
		n2.removeInEdge(e);
		
		/* if we are NOT directed, we also 
		 * have to remove the other way round */
		if(!_directional) {
			n1.removeInEdge(e);
			n2.removeOutEdge(e);
		}
		
		/* now remove from the list of edges */
		_edges.remove(edgeIndex);
		--_numberOfEdges;
		
		/* invalidate trees */
		purgeTrees();
	}
	private INode[] myBFSQueue(){
		try{
			//开始结点现在要求必须是1，后面再看是否有更好的方法
			_root = this.nodeById(1);
			
			if (null == _childrenMap){
				initTree();
			}
			
			HashMap<INode,Boolean> visitedMap = new HashMap<INode,Boolean>();
			int i = 0;
			for (i = 0; i < _nodes.size(); ++i){
				visitedMap.put(_nodes.get(i), false);
			}
			
			//创建一个队列，先进先出
			ArrayList<INode> queue = new ArrayList<INode>();
			queue.add(_root);
			visitedMap.put(_root, true);

			INode n = null;
			IEdge e = null;
			INode onode = null;
			List<IEdge> elist = null; 
			ArrayList<INode> bfs_queue = new ArrayList<INode>();
			boolean visited = false;
			while (queue.size() > 0){
				n = queue.remove(0);
				
				elist = n.outEdges();
				for (i = 0; i < elist.size(); ++i){
					e = elist.get(i);
					onode = e.otherNode(n);
					if (false == visitedMap.get(onode) && !queue.contains(onode)){
						queue.add(onode);
					}
				}
				
				visited = true;
				elist = n.inEdges();
				for (i = 0; i < elist.size(); ++i){
					e = elist.get(i);
					onode = e.otherNode(n);
					if (false == visitedMap.get(onode)){
						visited = false;
						break;
					}					
				}
				
				if (visited){
					visitedMap.put(n, true);
					bfs_queue.add(n);
				}
				else{
					queue.add(n);
				}
			}	

			return arrayListToNodes(bfs_queue);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	private String _id;
	private int _numberOfNodes;
	private int _numberOfEdges;
	private int _currentNodeId = 1;
	/**
	 * indicator if the graph is directional or not
	 */
	private boolean _directional = true;
	private int _walkingDirection = 0;
	private ArrayList<INode> _nodes = new ArrayList<INode>();
    private ArrayList<IEdge> _edges = new ArrayList<IEdge>();
	private HashMap<String, INode> _nodesById = new HashMap<String, INode>();	
	private HashMap<String, INode> _nodesByStringId = new HashMap<String, INode>();
	
	private int _currentEdgeId = 0;
	
	//Tree
	private INode _root = null;
	private HashMap<INode, INode> _parentMap = null;
	private HashMap<INode,Number> _nodeNoChildrenMap = new HashMap<INode, Number>();
	private HashMap<Integer,Integer> _amountNodesWithDistance = null;
	private int _maxNumberPerLayer = 0;
	private HashMap<INode, HashMap<Integer,INode>> _childrenMap = null;
	private HashMap<INode,Integer> _distanceMap = null;
	private HashMap<INode,IGTree> _treeMap = new  HashMap<INode,IGTree>();
}