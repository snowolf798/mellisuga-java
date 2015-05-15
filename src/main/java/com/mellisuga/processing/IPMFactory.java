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

import java.util.*;

/**
 * 建模工厂接口。建模工厂负责处理构件、连接器以及用户类型对象的创建。
 * 
 * <p>通常用户通过建模管理器来获取处理构件、连接器以及用户类型对象，而实质上处理构件、连接器和用户类型对象的创建是通过建模工厂创建的，
 * 因此，用户开发自己的处理构件，通常要相应地实现自己的建模工厂，从而支持自己开发的处理构件的创建，
 * 即实现 建模工厂接口，从而在用户通过建模管理器获取用户自己开发的处理构件时，可以通过相应的建模工厂进行创建。<br>
 * 
 * <p>用户类型对象的创建主要用在处理外部对处理构件输入参数的设置情况，例如，外部为处理构件的某个输入参数赋值了一个数据集（Dataset），
 * 那么，实际代码的实现过程是，首先，查找处理构件该输入参数对应的数据类型，然后，建模工厂根据这个数据类型创建相应的对象，
 * 接下来找到用户指定的数据集，最后，用户指定的数据集将通过工厂创建的对象通过{@linkplain com.mellisuga.processing.IProcess.setInputValue IProcess.setInputValue}方法完成处理构件指定输入参数的赋值。
 * 
 * @version 1.0
 * @created 10-1-2008 21:11:31
 */
public interface IPMFactory {

	/**
	 * 获取对象唯一标识
	 * @return 对象标识
	 */
	public String guid();
	
	/**
	 * 获取建模工厂的实现者，即创建该建模工厂的作者
	 * @return 建模工厂作者
	 */
	public String author();
	
	/**
	 * 获取建模工厂的详细描述信息
	 * @return 工厂细描述信息
	 */
	public String abstracts();
	
	/**
	 * 获取建模工厂的发布版本信息
	 * @return 工厂版本信息
	 */
	public String version();
	

	/**
	 * 获取建模工厂发布的时间
	 * @return 工厂发布时间
	 */
	public Date date();

	/**
	 * 获取该建模工厂所能创建的所有数据处理构件的名称列表
	 * 
	 * <p>当通过建模管理器来创建处理构件时，建模管理器将根据指定的处理构件的名称来创建由该名称唯一标识的处理构件类的一个新实例
	 * （实际上，处理构件的创建是由建模工厂来创建的）。
	 * @return 数据处理构件列表
	 */
	public String[] processes();
	
	/**
	 * 获取该建模工厂所支持创建的用户类型对象的对象类型列表
	 * @return 用户类型对象创建列表
	 */
	public String[] objectTypes();

	/**
	 * 根据指定的处理构件类的唯一标识名称，返回该处理构件类的新实例
	 * 
	 * <p>当通过建模管理器来创建处理构件类对象时，建模管理器将根据该名称来返回由该名称唯一标识的处理构件类的一个新实例，
	 * 但实际上，处理构件的创建是通过建模工厂的本方法来创建的。
	 * @param name  处理构件的名称
	 * @returns 返回指定处理构件类的新实例
	 */
	public IProcess createProcess(String name);
	
	public IProcess createProcess(String name, Object data);

	/**
	 * 根据指定的可以写入连接器的数据的数据类型和从连接器可以取出的数据的数据类型，创建一个连接器对象
	 * 
	 * <p>连接器可以形象地理解为连接两个处理构件的数据通道，处理构件的输出数据可以通过与该处理构件连接的连接器，
	 * 传递到连接器另一端所连接的处理构件，作为这个处理构件的输入数据。因此，连接器可以承载数据，同时连接着具有数据联系的处理构件，
	 * 并负责数据的传递以及必要的数据类型的转换。<br>
	 * <p>一个处理构件具有多个输入参数和输出参数，但是一个连接器只能对应两个处理构件间的一对参数关系，因此，要建立两个处理构件间的多对参数对应。
	 * 
	 * @param inParaType 输入参数类型
	 * @param outParaType  输出参数类型
	 * @returns 返回连接器对象
	 */
	public IConnector createConnector(String inParaType, String outParaType);

	/**
	 * 根据指定的对象类型创建一个承载着指定数据的相应类型的对象
	 * 
	 * <p>当通过建模管理器来创建用户类型的对象，建模管理器将根据指定的对象类型和对象承载的数据返回一个对象，
	 * 但实际上，用户类型的对象的创建是通过建模工厂的本方法来创建的。
	 * 
	 * @param type 对象的类型
	 * @param value  对象值的字符串格式化表达
	 * @returns 返回承载着指定数据的指定类型的对象
	 */
	public Object createObject(String type, Object value);
	
	/**
	 * 将给定对象转换为字符串
	 * @param obj 给定要转换的对象
	 * @return 转换后的字符串
	 */
	public String objectToString(Object obj);

	/**
	 * 释放资源
	 */
	public void release();
	
	/**
	 * 加载数据
	 */
	public void loaddata();
	
	/**
	 * 卸载数据，目的是在和应用框架结合时，避免独占资源的冲突，每一个独立计算完毕后释放资源
	 */
	public void unloaddata();
}