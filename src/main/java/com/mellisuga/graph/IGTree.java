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

import java.util.HashMap;

public interface IGTree {
	/**
	 * Indicating flag if the returned tree is restricted to currently invisible nodes.
	 * */
	boolean restricted();
	
	/**
	 * The map (Dictionary) that contains the parents for each node (keys are node
	 * objects, with their parent node objects as values).
	 * */
	HashMap<INode, INode> parents();
	
	/**
	 * access to the current root node of the tree. If a new root
	 * node is set, this means the tree has to be recalculated.
	 * */
	INode getRoot();
	
	/**
	 * @private
	 * */
	void setRoot(INode r);
	
	/**
	 * The maximum depth of the tree, which is the maximum distance
	 * of any node from the root 
	 * */
	int maxDepth();
	
	/**
	 * Get the distance of a particular node from the root.
	 * @param n The node object for which the distance is requested.
	 * @return The distance from the root in hops.
	 * */
	int getDistance(INode n);
	
	/**
	 * Each node is also the i'th child of it's parent. This index
	 * is called the child index. The child index of each node is stored
	 * and can be looked up by this method.
	 * @param n The node lookup its child index.
	 * @return The child index of this node.
	 * */
	int getChildIndex(INode n);
	
	/**
	 * The number of children of any node in the tree.
	 * @param The parent node.
	 * @return The number of its children.
	 * */
	int getNoChildren(INode n);
	
	/**
	 * Returns the number of siblings of a node, including the node itself
	 * so basically this is the number of the children of the node's parents.
	 * If the node has no parent, it is the root node and therefore
	 * the number of its sibling including itself is 1.
	 * @param n The node for which its number of siblings is required.
	 * @return The number of of siblings plus the node itself.
	 * */
	int getNoSiblings(INode n);
	
	/**
	 * Checks if two nodes are siblings or not.
	 * @param n First node to check.
	 * @param m Potential sibling of n.
	 * @return True if the nodes are siblings, false otherwise.
	 * */
	boolean areSiblings(INode n, INode m);
	
	/**
	 * An array that contains all children of this node
	 * in the tree in the order of each child's child index.
	 * @param n The parent node.
	 * @return The array of children of the given node.
	 * */
	INode[] getChildren(INode n);
	
	/**
	 * This method returns the node, which is the i'th child
	 * of a given (parent) node. Note that 'i' starts with 0,
	 * i.e. the frist child is actually the 0th child.
	 * @param n The parent node.
	 * @param i The child index of the desired child.
	 * @return The node which is it the i'th child of node n.
	 * */
	INode getIthChildPerNode(INode n,int i);
	
	/**
	 * This initialiases the (spanning) tree
	 * using BFS (Breadth first search).
	 * @param walkingDirection The direction in which to walk the graph.
	 * 		  The proper value is GraphWalkingDirectionsEnum.BOTH.
	 * 		  The value for the old functionality is GraphWalkingDirectionsEnum.FORWARD.
	 * @return The map that contains each node's parent node.
	 * */
	HashMap<INode, INode> initTree();
	
	/**
	 * This method returns a map (Object) containing only 
	 * the nodes which are within a certain distance of the
	 * root node.
	 * @param limit The distance limit.
	 * @return An object containing a map of node id's which are within the distance limit.
	 * */
	HashMap<INode, INode> getLimitedNodes(int limit);
	
	/**
	 * This returns the number of nodes that have exactly
	 * the specified distance.
	 * @param d The distance from the root.
	 * @return The number of nodes in the tree with distance d.
	 * */
	int getNumberNodesWithDistance(int d);
	
	/**
	 * This is the maximum number of nodes at any
	 * distance, i.e. the maximum over all distances
	 * of getNumberNodesWithDistance().
	 * @see getNumberNodesWithDistance
	 * */
	int maxNumberPerLayer();
}
