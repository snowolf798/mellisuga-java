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
package com.mellisuga.processing;

import java.util.Enumeration;

/**
 * 上下文管理器
 * 
 * <p>应用程序中的所有环境变量以及唯一对象都缓存在上下文中进行统一管理，实现系统解耦
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:11:10
 */
public interface IContext {

	/**
	 * 初始化
	 */
	public void initialize();
		
	/**
	 * 设置上下文环境值
	 * @param key 环境变量关键字
	 * @param variable 环境变量值
	 */
	public void setVariable(String key, Object variable);

	/**
	 * 获取上下文环境值
	 * @param key 环境变量关键字
	 * @return 环境变量值
	 */
	public Object getVariable(String key);

	/**
	 * 获取所有上下文环境变量关键字
	 * @return 关键字数组
	 */
	public Enumeration<String> getVariableKeys();	

	/**
	 * 上下文变量数目
	 * @return 上下文变量个数
	 */
	public int count();

	/**
	 * 清除所有的上下文环境变量
	 */
	public void clear();
}