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

public interface INode extends IDataItem{

	/**
	 * The Array containing all incoming edges
	 * of this node. (In a non-directional graph, this is
	 * the same as the outgoing edges.)
	 * */
	List<IEdge> inEdges();
	
	/**
	 * The Array containing all outgoing edges
	 * of this node. (In a non-directional graph, this is
	 * the same as the incoming edges.)
	 * */
	List<IEdge> outEdges();
	
	/**
	 * The Array containing all preceding nodes,
	 * i.e. nodes that are at the other end of an 
	 * outgoing edge. (In a non-directional graph
	 * this is the same as the successor nodes).
	 * */
	List<INode> predecessors();
	
	/**
	 * The Array containing all succeeding nodes,
	 * i.e. nodes that are at the other end of an 
	 * incoming edge. (In a non-directional graph
	 * this is the same as the predecessor nodes).
	 * */
	List<INode> successors();
	
	/**
	 * Registers an incoming edge with this node.
	 * This method does nothing else, and should normally
	 * not be used directly, but is typically used by
	 * the link method of the graph.
	 * @param e The edge to add to this node.
	 * @throws An error if the edge does not connect to another node.
	 * @see com.supermap.graphLayout.data.Graph#link()
	 * */
	void addInEdge(IEdge e);
	
	/**
	 * Registers an outgoing edge with this node.
	 * This method does nothing else, and should normally
	 * not be used directly, but is typically used by
	 * the link method of the graph.
	 * @param e The edge to add to this node.
	 * @throws An error if the edge does not connect to another node.
	 * @see com.supermap.graphLayout.data.Graph#link()
	 * */
	void addOutEdge(IEdge e);
	
	/**
	 * Removes an edge from the list of incoming edges
	 * of this node. Removes also the predecessor node
	 * from the list.
	 * Should not be used directly, but is typically used
	 * by the unlink or removeEdge method of the graph.
	 * @param e The edge to remove to this node.
	 * @throws An error if the edge does not connect to another node.
	 * @throws An error if the edge is not in the list of incoming edges of this node.
	 * @throws An error if the node at the other end of this edge is not in the list of predecessor nodes.
	 * @see com.supermap.graphLayout.data.Graph#unlink()
	 * @see com.supermap.graphLayout.data.Graph#removeEdge()
	 * */
	void removeInEdge(IEdge e);
	
	/**
	 * Removes an edge from the list of outgiong edges
	 * of this node. Removes also the successor node
	 * from the list.
	 * Should not be used directly, but is typically used
	 * by the unlink or removeEdge method of the graph.
	 * @param e The edge to remove to this node.
	 * @throws An error if the edge does not connect to another node.
	 * @throws An error if the edge is not in the list of incoming edges of this node.
	 * @throws An error if the node at the other end of this edge is not in the list of successor nodes.
	 * @see com.supermap.graphLayout.data.Graph#unlink()
	 * @see com.supermap.graphLayout.data.Graph#removeEdge()
	 * */
	void removeOutEdge(IEdge e);
	
}
