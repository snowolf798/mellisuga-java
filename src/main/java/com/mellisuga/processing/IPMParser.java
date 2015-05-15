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

import com.mellisuga.processing.ProcessDescription;
import org.w3c.dom.Element;

/**
 * 模型解析器
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:13:56
 */
public interface IPMParser {

	/**
	 * 设置要解析的模型文件
	 * @param value 模型文件路径
	 */
	public void setFile(String value);

	/**
	 * 是否单步执行模式
	 * @param value 是否单步
	 */
	public boolean isStepByStep();
	
	/**
	 * 获取模型名称
	 * <p>要求模型名称统一使用英文表示，且在整个系统中不可重复，用户可以使用带有自己标识的前缀进行区分，以避免和别人的名称重复。
	 * @return 模型名称
	 */
	public String getName();
	
	/**
	 * 获取模型标题
	 * <p>模型标题用来设置模型的简单描述信息，用户可以根据本地语言的不同来为模型添加描述信息。在模型列表中，所显示的模型名字为模型的标题。	 
	 * @return 模型标题
	 */
	public String getTitle();
	
	/**
	 * 获取模型的详细、完整的描述信息。
	 * @return 模型描述
	 */
	public String getAbstracts();
	
	/**
	 * 获取模型的输入参数的定义，包括：模型所能接收的输入参数的参数名称、参数类型等信息。      
	 * 
	 * <p>模型可以接收或者处理模型能够正常执行所需要的所有参数列表可以通过该属性来获取，该属性的值为一个{@linkplain com.mellisuga.processing.MParaItem MParaItem}对象数组，
	 * 每一个{@linkplain com.mellisuga.processing.MParaItem MParaItem}对象对应模型的一个输入参数的定义，包括参数的名称、类型、是否为可选参数等信息。
	 * @return 模型输入参数列表
	 */
	public MParaItem[] getInputs();
	
	/**
	 * 获取模型执行结果的输出参数的定义。模型执行完成后，外部所能获取到的结果参数的参数名、参数类型等信息可以通过该属性来获取。       
	 * 
	 * <p>模型执行完成后，外部所能获取的输出参数列表就可以通过该属性来获取，该属性的值为一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象数组，
	 * 每一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象对应模型的一个结果参数的定义，包括结果参数的名称、类型等信息。
	 * @return 模型输出参数列表
	 */
	public MParaItem[] getOutputs();

	/**
	 * 模型中数据处理构件的连接关系描述
	 * @return 连接描述列表
	 */
	public LinkDescription[] linkList();

	/**
	 * 模型使用到的所有数据处理构件的列表
	 * @return 数据处理构件列表
	 */
	public ProcessDescription[] processList();
	
	/**
	 * 模型XML文件中所有数据处理构件的XML列表
	 * @return 数据处理构件XML列表
	 */
	public Element[] processes();
	
	/**
	 * 模型执行结果描述
	 * @return 模型结果描述
	 */
	public ResultDescription resultDescription();

	public void reset();
}