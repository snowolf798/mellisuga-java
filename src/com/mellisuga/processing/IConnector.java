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

import com.mellisuga.designpattern.IPipe;

/**
 * 定义连接器接口，所有的连接器必须实现该接口
 * 
 * <p>连接器可以形象地理解为连接两个数据处理构件的数据通道，数据处理构件的输出数据可以通过与该处理构件连接的连接器，
 * 传递到连接器另一端所连接的处理构件，作为这个处理构件的输入数据。<br>
 * <p>因此，连接器可以承载数据，同时连接着具有数据联系的处理构件，并负责数据的传递以及必要的数据类型的转换。<br>
 * <p>一个处理构件具有多个输入参数和输出参数，但是一个连接器只能对应两个处理构件间的一对参数关系，
 * 因此，要建立两个处理构件间的多对参数对应关系就需要多个连接器来连接。
 * 
 * @version 1.0
 * @author snowolf798@gmail.com
 * @created 10-1-2008 21:11:04
 */
public interface IConnector extends IPipe {	
	
	/**
	 * 设置连接器的 ID 号
	 * 
	 * <p>ID 号用来唯一标识一个连接器，即用来唯一标识连接器对应的一对处理构件间的参数关系。
	 * @param value ID标识
	 */
	public void setid(int value);
	/**
	 * 获取连接器的 ID 号
	 * 
	 * <p>ID 号用来唯一标识一个连接器，即用来唯一标识连接器对应的一对处理构件间的参数关系。
	 * @return ID标识
	 */
	public int getid();

	/**	 
	 * 获取可以写入连接器的数据类型<br>
	 *
	 * <p>连接器为连接两个处理构件间参数传递的通道，起到承载数据、传递数据或者转换数据的作用，
	 * 因此，在一个处理构件执行完成后，可以将该处理构件的某个输出参数对应的输出结果数据写入与该处理构件连接的连接器中，
	 * 而与该连接器相连的另一个处理构件，就可以通过获取连接器中已放置的上一个处理构件的输出数据，
	 * 作为这个处理构件的一个输入参数，即为该处理构件提供输入数据；
	 * 按照这种方式类推，就可以将多个处理构件通过连接器构成一个数据处理工作流，实现数据处理的流程化和自动化。<br>
	 * 
	 * @return 写入连接器的数据的数据类型
	 */
	public String inputParaType();

	/**
	 * 获取从连接器中可以获取的数据的数据类型
	 * 
     * <p>该属性用来获取从连接器中可以获取的数据的数据类型，如果写入连接器的数据的数据类型与从该连接器可以取出的数据的数据类型不一致，
     * 连接器还会进行相应的数据转换，转换为连接器可以取出的数据类型。	
	 * @return 从连接器中可以获取的数据的数据类型
	 */
	public String outputParaType();

	/**
	 * 克隆
	 * @return 连接器的拷贝
	 */
	public IConnector clone();
}