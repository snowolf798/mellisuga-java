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
 * 管道/过滤器架构模式基类
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:10:26
 */
public interface IConnectableObject {

	/**	 
     * 获得对象的惟一标识     
	 * @return 对象标识
	 */
	public String getGuid();
	/**
	 * 设置对象的惟一标识     
	 * @param value 对象标识
	 */
	public void setGuid(String value);
    
	/**
     * 获取关联的输入连接对象
	 * @return 输入连接对象
	 */
	public IConnectableObject getInputConnectableObject();
	/**
	 * 设置关联的输入连接对象
	 * @param value 输入连接对象
	 */
	public void setInputConnectableObject(IConnectableObject value);

    /**
     * 获取关联的输出连接对象
     * @return 输出连接对象
     */
    public IConnectableObject getOutputConnectableObject();
    /**
     * 设置关联的输出连接对象
     * @param value 输出连接对象
     */
    public void setOutputConnectableObject(IConnectableObject value);
}
