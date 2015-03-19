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

public interface IEdge extends IDataItem {
	/**
	 * Indicates if the graph that contains this edge is directional.
	 * @return if the graph that contains this edge is directional.
	 * */
	boolean isDirectional();

	/**
	 * The first node associated with this edge.
	 * @return The first node associated with this edge.
	 * */
	INode node1();
	
	/**
	 * The second node associated with this edge.
	 * @return The second node associated with this edge.
	 * */
	INode node2();

	/**
	 * returns the source node (fromNode) of the edge.
	 * Only available if the graph is directional as otherwise
	 * there is no designated source node.
	 * @return The source node of this edge.
	 * @throws Error that the graph is not directional.
	 * */
	INode fromNode();
	
	/**
	 * returns the target node (toNode) of the edge.
	 * Only available if the graph is directional as otherwise
	 * there is no designated target node.
	 * @return The target node of this edge.
	 * @throws Error that the graph is not directional.
	 * */
	INode toNode();

	/**
	 * This method returns the other node than the node
	 * given as parameter, so it can be used to follow along
	 * the edge.
	 * @param node The node that is known (implicit source node)
	 * @return The node that is unknown (implicit target node)
	 * */
	INode otherNode(INode node);
}
