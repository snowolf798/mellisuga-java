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

/**
 * 管理器
 * 
 * <p>管理数据流程建模中所使用的要素，如处理构件等。<br>
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:11:37
 */
public interface IProcessingManager {
	/**
	 * 返回模型解析器实例
	 * 
	 * @return 解析器
	 */
	public IPMParser newParser();
	
	/**
	 * 返回派遣器实例
	 * 
	 * @return 派遣器
	 */
	public IPMDispatcher newDispatcher();
	
	/**
	 * 获取赋值连接器
	 * 
	 * <p>赋值连接器是一种简单的连接器，赋值连接器用来连接不需要进行数据转换，即数据类型一样的一对参数。<br>
	 * <p>赋值连接器不对写入连接器中的数据进行数据类型的转换，即写入连接器的数据在从连接器中取出时，数据类型保持不变，
	 * 这种连接器只负责数据处理构件间一对参数的数据传递，即赋值，而不能进行数据转换。<br>
	 * 
	 * @return 通用连接器
	 */
	public IConnector connectorConvey();

	/**
	 * 根据指定类全名称，对指定的建模工厂进行注册，这里适用于一次注册一个建模工厂。
	 * <p>注册工厂后，就可以通过管理器创建注册工厂中所支持创建的数据处理构件、用户类型的对象以及连接器。<br>
	 * 
	 * @param factoryName 工厂类全名称
	 * 
	 * @return 注册成功返回true，否则返回false
	 */
	public boolean register(String factoryName);

	/**
	 * 根据指定的类全名称，对指定的建模工厂进行注册，该方法可以实现一次注册多个指定的建模工厂。
	 * <p>注册工厂后，就可以通过管理器创建注册工厂中所支持创建的数据处理构件、用户类型的对象以及连接器。<br>
	 * 
	 * @param factoryNames 建模工厂名称数组
	 * 
	 * @return 注册成功返回true，否则返回false
	 */
	public boolean register(String[] factoryNames);

	/**
	 * 取消对指定建模工厂的注册。
	 * 
	 * @param factoryName 工厂全名称
	 * 
	 * @return 注销成功返回true，否则返回false
	 */
	public boolean unregister(String factoryName);

	/**
	 * 根据指定的数据处理构件类的唯一标识名称，返回该数据处理构件类的新实例。
	 * 
	 * <p>该方法是通过建模管理器来创建数据处理构件类对象，建模管理器将根据所指定的数据处理构件类的唯一标识名称来返回由该名称唯一标识的数据处理构件类的一个新实例，
	 * 但实际上，数据处理构件的创建是通过建模工厂的本方法来创建的。<br>
	 * 
	 * @param name 数据处理构件类的唯一标识名称
	 * 
	 * @returns 返回指定数据处理构件类的新实例。
	 */
	public IProcess createProcess(String name);
	
	/**
	 * 根据指定的可以写入连接器的数据的数据类型和从连接器可以取出的数据的数据类型，创建一个连接器对象。
	 * @param inParaType 写入连接器的数据的数据类型
	 * @param outParaType 从连接器取出的数据的数据类型
	 * @returns 指定输入和输出数据类型的连接器
	 */
	public IConnector createConnector(String inParaType, String outParaType);

	/**
	 * 根据指定的对象类型创建一个承载着指定数据的相应类型的对象。
	 * 
	 * <p>该方法是通过建模管理器来创建用户类型的对象，建模管理器将根据指定的对象类型和对象承载的数据返回一个对象，
	 * 但实际上，用户类型的对象的创建是通过建模工厂的createObject(String type, String value)方法来创建的。<br>
	 * 
	 * @param type 对象类型
	 * @param value 对象值
	 * 
	 * @returns 返回承载着指定数据的指定类型的对象。
	 */	
	public Object createObject(String type, Object value);
	
	/**
	 * 将给定的对象转换为字符串
	 * @param obj 给定对象
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
	 * 卸载数据
	 */
	public void unloaddata();
}