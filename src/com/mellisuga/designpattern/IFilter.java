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
package com.mellisuga.designpattern;

/**
 * 过滤器
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:06:12
 */
public interface IFilter extends IConnectableObject{
	/**
	 * 执行过滤器
	 * @return 执行是否成功
	 */
	public boolean execute();
}