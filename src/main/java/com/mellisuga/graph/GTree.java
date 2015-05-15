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

public class GTree implements IGTree {

	/**
     * If building a spanning tree, walk only forward.
     * */
    public static int WALK_FORWARDS = 0;
    
    /**
     * If building a spanning tree, walk only backwards.
     * */
    public static int WALK_BACKWARDS = 1;
    
    /**
     * If building a spanning tree, walk only both
     * directions
     * */
    public static int WALK_BOTH = 2;
    
    private int _walkingDirection = WALK_BOTH;
    
    private IGraph _graph = null;
    private INode _root = null;
    
    /* max depth of the tree */
    private int _maxDepth = 0;
    
	/* the following are indexed by node objects */
    private HashMap<INode, INode> _parentMap = null;
    private HashMap<INode, INode> _childrenMap = null;
    private HashMap<INode, INode> _distanceMap = null;
	
	/* this is indexed by a distance value and stores
	 * the number of nodes with that distance. Since this
	 * is compact it can be an array */
    private ArrayList<INode> _amountNodesWithDistance = null;
	
	/* this is the maximum of nodes that are in a certain distance */
    private int _maxNumberPerLayer;
	
	/* for some algorithms
	 * we also need to establish a specific order, i.e.
	 * for each node to know that it is the 'i'th child
	 * and it has m siblings */
    private HashMap<INode, INode> _nodeChildIndexMap = null;
    private HashMap<INode, INode> _nodeNoChildrenMap = null;

    /* in some cases we rather build a tree
	 * that is restriced to the _visible_ nodes
	 * we create a flag for that, which has to be
	 * set in the constructor */
    private Boolean _restrictToVisible;
    
	public GTree(INode root, IGraph graph, Boolean restrict, int direction){
		_parentMap = null;
		_childrenMap = null;
		_distanceMap = null;
		_nodeChildIndexMap = null;
		_nodeNoChildrenMap = null;
		_amountNodesWithDistance = null;
		
        _walkingDirection = direction;
        
		_maxNumberPerLayer = 0;
		
		_root = root;
		_graph = graph;
		
		_maxDepth = 0;
		
		_restrictToVisible = restrict;
	}
	
	public boolean restricted() {
		return _restrictToVisible;
	}

	public HashMap<INode, INode> parents() {
		/* we make sure to initialise here */
		if(_parentMap == null) {
			initTree();
		}
		return _parentMap;
	}

	public INode getRoot() {
		return _root;
	}

	public void setRoot(INode r) {
		
	}

	public int maxDepth() {
		return 0;
	}

	public int getDistance(INode n) {
		return 0;
	}

	public int getChildIndex(INode n) {
		return 0;
	}

	public int getNoChildren(INode n) {
		return 0;
	}

	public int getNoSiblings(INode n) {
		return 0;
	}

	public boolean areSiblings(INode n, INode m) {
		return false;
	}

	public INode[] getChildren(INode n) {
		return null;
	}

	public INode getIthChildPerNode(INode n, int i) {
		return null;
	}

	public HashMap<INode, INode> initTree() {
		return null;
	}

	public HashMap<INode, INode> getLimitedNodes(int limit) {
		return null;
	}

	public int getNumberNodesWithDistance(int d) {
		return 0;
	}

	public int maxNumberPerLayer() {
		return 0;
	}

}
