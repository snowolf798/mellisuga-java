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

import java.util.Map;

/**
 * 流程派遣器
 * 
 * <p>实现模型的执行和控制
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:11:37
 */
public interface IPMDispatcher {
	/**
	 * 设置模型解析器
	 * @param parser 模型解析器
	 */
	public void setModelParser(IPMParser parser);

	/**
	 * 派遣执行
	 * @param context  上下文
	 * @return 执行结果
	 */
	public Object dispatch(IContext context);
	/**
	 * 派遣执行
	 * @param context 上下文
	 * @param args 模型参数数组
	 * @return 执行结果
	 */
	public Object dispatch(IContext context, Object[] args);
	/**
	 * 派遣执行
	 * @param context 上下文
	 * @param args 模型参数数组
	 * @return 执行结果
	 */
	public Object dispatch(IContext context, String[] args);

	/**
	 * 派遣执行
	 * @param context 上下文
	 * @param parameters 模型参数
	 * @return 执行结果
	 */
	public Object dispatch(IContext context, String parameters);
	
	/**
	 * 派遣执行
	 * @param context 上下文
	 * @param parameters 模型参数列表
	 * @return 执行结果
	 */
	public Object dispatch(IContext context, Map<String,String> parameters);
		
	/**
	 * 获取模型执行结果名称
	 * @return 结果名称
	 */
	public String getResultName();

	/**
	 * 获取模型执行结果类型
	 * @return 结果类型
	 */
	public String getResultType();
	/**
	 * 获取模型执行结果类别
	 * @return 结果类别
	 */
	public String getResultKind();
}
