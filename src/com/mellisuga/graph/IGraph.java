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

public interface IGraph {
	/**
	 * The id (or name) of the graph
	 * */
	String id();
	
	/**
	 * An Array that contains all nodes of the graph.
	 * */
	INode[] nodes();
	
	/**
	 * An Array that contains all edges of the graph.
	 * */
	IEdge[] edges();
	
	/**
	 * The number of nodes in the graph.
	 * */ 
	int noNodes();
	
	/**
	 * The number of edges in the graph.
	 * */ 
	int noEdges();

	/**
	 * This determines the walking direction when building
	 * a spanning tree. Possible values are
	 * Graph.WALK_FORWARD
	 * Graph.WALK_BACKWARDS
	 * Graph.WALK_BOTH
	 * All make only sense in a directional graph.
	 * Forward means we follow edges in its regular direction
	 * from the root node.
	 * Backwards means, we follow edges back from the root
	 * node (e.g. the root is a sink with multiple sources)
	 * Both obviously means both.
	 * 
	 * This property will be queried by GTree to determine the
	 * walking direction set.
	 * */
	void setWalkingDirection(int d);

	/**
	 * @private
	 * */
	int getWalkingDirection();

	/**
	 * A lookup to find a node by it's string id.
	 * @param sid The node's string id.
	 * @return The node if one was found, null otherwise.
	 * */
	INode nodeByStringId(String sid);
	
	/**
	 * A lookup to find a node by it's (int) id.
	 * @param id The node's id.
	 * @return The node if one was found, null otherwise.
	 * */
	INode nodeById(int id);
	
	/**
	 * Creates a graph node in the graph, optionally takes a string
	 * id for the node and an object to associate the node with.
	 * @param sid A unique string id for the node (if empty the numerical id will be used).
	 * @param o Dataobject to be associated with this node.
	 * @return The created node object.
	 * @throws Error if the string id was already used before (must be unique).
	 * */
	INode createNode(String sid, Object o);
	
	
	/**
	 * Removes a node from the graph. If the node is part of any edge,
	 * and error is thrown (i.e. edges must be removed/nodes unlinked first).
	 * @param n The node object to be removed.
	 * @throws An error if the node is still part of any edge.
	 * @throws An error if the node to be removed cannot be found in the graph.
	 * @throws An error if there exists still a vnode associated with this node.
	 * */
	void removeNode(INode n);

	/**
	 * returns the current BFS tree of the graph, rooted in the given node,
	 * optionally the tree is restricted to only contain currently visible
	 * nodes. 
	 * The trees are cached, that means a tree is only created once and then
	 * stored in a map, unless the "nocache" flag is set! If the flag is set,
	 * then the tree will be created once and returned, the existing cache will
	 * not be touched or overwritten, this is useful if a full tree is needed
	 * for a specific purpose but the cache should not be overwritten or consulted.
	 * @param n The root node of the tree.
	 * @param restr This flag specifies if the resulting tree should be restricted to currently visible nodes.
	 * @param nocache If set, always a new tree will be created and returned and the cache will be untouched.
	 * @param walkingDirection The direction in which the graph must be walked to create the spanning tree.
	 * 						   The value of GraphWalkingDirectionsEnum.FORWARD is the default and it represents the old (buggy) functionality.
	 * 						   GraphWalkingDirectionsEnum.BOTH is the current (proper) functionality.
	 * @return The a GTree object that contains the tree.
	 * */
	IGTree getTree(INode n,boolean restr, boolean nocache,int direction);
	
	/**
	 * Under certain circumstances all cached trees need
	 * to be purged.
	 * */
	void purgeTrees();
	
	/**
	 * Link two nodes together (i.e. create an edge). If the graph is NOT directional
	 * the same edge will be incoming and outgoing for both nodes. If the nodes are
	 * already linked, it will just return the existing edge between them.
	 * @param node1 First node to be linked.
	 * @param node2 Second node to be linked.
	 * @param o Optional data object to be associated with the resulting edge.
	 * @return The resulting edge.
	 * @throws Errors if any node is null.
	 * */
	IEdge link(INode node1, INode node2, Object o);
	
	/**
	 * Unlink two nodes, effectively removing the edge between
	 * them.
	 * @param node1 The first node to be unlinked.
	 * @param node2 The second node to be unlinked.
	 * @throws An error if the nodes were not linked before.
	 * */
	void unlink(INode node1, INode node2);
	
	/**
	 * Find an edge between two nodes.
	 * @param n1 The first node of the edge.
	 * @param n2 The second node of the edge.
	 * @return The resulting edge or null if the nodes were not linked.
	 * */		
	IEdge getEdge(INode n1, INode n2);
	
	/**
	 * Removes an edge between two nodes.
	 * @param e The edge to be removed.
	 * @throws An error if the edge was not part of the graph.
	 * */		
	void removeEdge(IEdge e);			  
	
	/**
	 * Remove all edges and nodes from the Graph.
	 * */
	void purgeGraph();
}
