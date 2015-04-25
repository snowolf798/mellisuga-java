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

import com.mellisuga.designpattern.IFilter;


/**
 * 定义数据处理构件接口，所有的数据处理构件必须实现该接口
 * 
 * <p>用户自己实现的数据处理构件必须实现本接口或者继承{@linkplain com.mellisuga.processing.ProcessBase ProcessBase}类，
 * {@linkplain com.mellisuga.processing.ProcessBase ProcessBase}类已经对本接口进行了一定程度的实现，因此用户的数据处理构件类继承自{@linkplain com.mellisuga.processing.ProcessBase ProcessBase}类，可以节省部分开发工作。<br>
 * <p>数据处理构件是数据流程建模的核心，是数据流程建模结果————模型的基本数据处理单元。
 * 数据处理构件为一个相对独立的、一般不可再分的数据处理操作，
 * 数据处理一般包含若干个输入数据、对输入数据的处理以及若干个处理结果————输出数据。
 * 因此，数据处理构件接口提供了设置输入数据的接口，对输入数据进行处理的接口以及输出数据处理结果的接口。<br>
 * 
 * <p>实现本接口或者继承{@linkplain com.mellisuga.processing.ProcessBase ProcessBase}的类统称为数据处理构件类，数据处理构件类表示具有某种数据处理功能的处理构件，
 * 数据处理构件类对象可以理解为该数据处理构件类所具有的数据处理功能的一个执行实例，同一个数据处理构件类对象的数据处理功能相同， * 但是对这些数据处理构件类对象如果设置了不同的输入数据，执行结果也不同。<br>
 * <p>设置数据处理构件类对象的输入数据和其他相关参数后，即可调用数据处理构件类对象的执行函数，执行对输入数据的处理操作，进而获得处理结果数据。
 * 一个模型中可以包含多个数据处理构件类对象，并且可以包含一个数据处理构件类的多个实例，即该模型所定义的数据处理流程中存在多个这样的数据处理操作任务。<br>
 * 
 * <p>当将多个具有不同数据处理功能的数据处理构件按照某种逻辑顺序顺次执行，即构成一个执行队列，甚至这些数据处理具有一定的关联关系，
 * 即某个数据处理构件的处理结果数据将作为下一个数据处理构件的输入数据，即构成一个数据处理的工作流，实现数据处理的流程化、自动化。<br>
 * 
 * <p>数据流程建模是一个过程，首先，获取可以解决一项问题的若干个数据处理构件；其次，编排这些处理构件的执行逻辑，即执行顺序；
 * 最后，正确设置数据处理构件的参数以及数据处理构件间的参数传递关系；以上这一过程称为数据流程建模，数据流程建模的结果为模型。
 * 一个模型中可以包含一个数据处理构件，即该模型只具有简单的一项数据处理功能，而多个数据处理构件构成的模型也可以作为具有特殊的、复杂数据处理功能的数据处理构件，
 * 嵌入到其他模型中，因此，数据处理构件和模型没有明确的界定，即一个数据处理构件可以看作一个简单的模型，模型也可以看做特殊的数据处理构件。<br>
 * 
 * <p>数据流程建模是构建一个数据处理加工的流程，而数据处理构件就是具有数据加工能力的"工厂"，是数据流程建模的主体，因此，在开始进行数据流程建模时，
 * 首先要准备好模型中所需要的数据处理功能，即数据处理构件。每一个数据处理构件都可以单独执行，而数据流程建模的结果模型的执行，
 * 实质就是按照模型所构建数据处理构件的有序执行队列，顺次执行每一个数据处理构件。<br>
 * 
 * <p>另外，数据处理构件表示具有特定数据处理功能的数据处理单元，这里的数据处理不仅可以处理 空间与非空间数据，还可以处理业务应用的数据，
 * 此时，数据流程建模过程为构建业务流程的过程，数据流程建模的结果为某个业务应用系统，而模型中的数据处理构件为业务流程中的业务处理单元。
 * @author snowolf798@gmail.com 
 * @version 1.0
 * @created 10-1-2008
 */
public interface IProcess extends IFilter {

	/**
	 * 设置对象的惟一标识     
	 * @param value 对象标识
	 */
    public void setGuid(String value);
    /**	 
     * 获得对象的惟一标识     
	 * @return 对象标识
	 */
    public String getGuid();
    
	/**
	 * 设置数据处理构件的ID号
	 * 
	 * <p>数据处理构件的ID号，用来唯一标识数据处理构件类对象。当一个数据处理流程中存在一个数据处理构件类的多个实例时，
	 * ID唯一标识一个数据处理构件对象，从而避免冲突。<br>
	 * 
	 * <p>当通过建模管理器来创建数据处理构件时，将自动为该数据处理构件对象的ID赋值，并且建议用户最好不要修改由建模管理器生成的处理构件的ID号。<br>
	 * @param  value  ID号 
	 */
	public void setid(int value);
	/**
	 * 获得数据处理构件的ID号
	 * @return 数据处理构件ID
	 */
	public int getid();
	
	/**
	 * 获取数据处理构件的唯一标识名称
	 * 
	 * <p>当通过建模管理器来创建数据处理构件时，建模管理器将根据该名称来创建由该名称唯一标识的数据处理构件类的一个新实例（实际上，数据处理构件的创建是由建模工厂来创建的）。
	 * @return 数据处理构件名称
	 */
	public String name();	
	
	/**
	 * 获取数据处理构件的标题
	 * 
	 * <p>数据处理构件的标题用来设置数据处理构件的简单描述信息，用户可以根据本地语言的不同来为数据处理构件添加描述信息。     
	 * 
	 * <p>如果数据处理构件被加载到建模工具的数据处理构件列表中，则列表中所显示的数据处理构件的名字为数据处理构件的标题。
	 * 
	 * @return 数据处理构件标题
	 */
	public String getTitle();
	/**
	 * 设置数据处理构件的标题
	 * @param value 数据处理构件标题
	 */
	public void setTitle(String value);

	/**
	 * 获取数据处理构件的详细、完整的描述信息
	 * 
	 * @return 数据处理构件的描述
	 */
	public String getAbstracts();
	/**
	 * 设置数据处理构件的完整描述信息
	 * @param value 数据处理构件的描述
	 */
	public void setAbstracts(String value);

	/**
	 * 获取数据处理构件所有的输入参数的定义，包括：数据处理构件所能接收的输入参数的参数名称、参数类型等信息。      
	 * 
	 * <p>数据处理构件可以接收或者数据处理构件能够正常执行所需要的所有参数列表可以通过该属性来获取，该属性的值为一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象数组，
	 * 每一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象对应数据处理构件的一个输入参数的定义，包括参数的名称、类型、是否为可选参数等信息。<br>
	 * 
	 * <p>数据处理构件输入参数的值的设置是通过{@linkplain com.mellisuga.processing.IProcess.setInputValue IProcess.setInputValue}方法实现的，同时，在该方法中还需要实现检查参数赋值的有效性，
	 * 即检查对数据处理构件输入参数的设置是否正确，因此，可以通过本属性获取数据处理构件本身定义的参数列表，
	 * 检查用户设置的参数是否存在于数据处理构件所定义的参数列表中，以及所设置的参数值的类型是否与参数列表中定义的该参数的类型一致，
	 * 如果不一致，则输入参数的赋值失败。
	 * 
	 * @return 数据处理构件输参数列表
	 */
	public ParaItem[] inputSet();
	
	/**
	 * 获取数据处理构件执行结果的输出参数的定义。
	 * 
	 * <p>数据处理构件执行完成后，外部所能获取到的结果参数的参数名、参数类型等信息可以通过该属性来获取。<br>
	 * 
	 * <p>数据处理构件执行完成后，外部所能获取的输出参数列表就可以通过该属性来获取，该属性的值为一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象数组，
	 * 每一个{@linkplain com.mellisuga.processing.ParaItem ParaItem}对象对应数据处理构件的一个结果参数的定义，包括结果参数的名称、类型等信息。<br>
	 * 
	 * <p>通过{@linkplain com.mellisuga.processing.IProcess.getOutputValue IProcess.getOutputValue}方法可以获取数据处理构件的指定输出参数的数据内容。
	 * 
	 * @return 数据处理构件输出参数列表
	 */
	public ParaItem[] outputSet();
	
	/**
	 * 获取数据处理构件的执行条件是否都满足了
	 * 
	 * <p>该属性的值为 true，表示数据处理构件的执行准备就绪，为 false，表示数据处理构件的执行条件没有完全满足。    
	 * 通过实现该属性来检查数据处理构件的执行条件是否都满足了，例如可以检查数据处理构件执行所必须的输入参数是否都进行了赋值。
	 * 
	 * @return 数据处理构件是否准备就绪
	 */
	public boolean isReady();
	
	/**
	 * 将数据处理构件的指定输入参数与指定的连接器建立连接关系。
	 * 
	 * <p>连接器可以形象地理解为连接两个数据处理构件的数据通道，数据处理构件的输出数据可以通过与该数据处理构件连接的连接器，传递到连接器另一端所连接的数据处理构件，
	 * 作为这个数据处理构件的输入数据。因此，连接器可以承载数据，同时连接着具有数据联系的数据处理构件，并负责数据的传递以及必要的数据类型的转换。
	 * 
	 * <p>一个数据处理构件具有多个输入参数和输出参数，但是一个连接器只能对应两个数据处理构件间的一对参数关系，
	 * 因此，要建立两个数据处理构件间的多对参数对应关系就需要多个连接器来连接。
	 * 
	 * <p>该方法就是将指定的连接器与该数据处理构件器特定的输入参数绑定，那么该数据处理构件的这个输入参数就可以从与其连接的连接器中获取输入数据。
	 * 
	 * @param parameter 指定的数据处理构件的输入参数的参数名称
	 * @param connector 指定的连接器
	 * @returns  如果指定的连接器与数据处理构件的指定参数成功建立连接关系，则返回 true；否则，返回 false
	 * 
	 */
	public boolean setInputConnector(String parameter, IConnector connector);

	/**
	 * 将数据处理构件的指定输出参数与指定的连接器建立连接关系
	 * 
	 * <p>连接器可以形象地理解为连接两个数据处理构件的数据通道，数据处理构件的输出数据可以通过与该数据处理构件连接的连接器，传递到连接器另一端所连接的数据处理构件，
	 * 作为这个数据处理构件的输入数据。因此，连接器可以承载数据，同时连接着具有数据联系的数据处理构件，并负责数据的传递以及必要的数据类型的转换。<br>
	 * 
	 * <p>一个数据处理构件具有多个输入参数和输出参数，但是一个连接器只能对应两个数据处理构件间的一对参数关系，
	 * 因此，要建立两个数据处理构件间的多对参数对应关系就需要多个连接器来连接。<br>
	 * 
	 * <p>该方法就是将指定的连接器与该数据处理构件器特定的输出参数绑定，用来接收数据处理构件的输出数据，进而可以通过连接器传递出去。
	 * 
	 * @param parameter 指定的数据处理构件的输出参数的参数名称
	 * @param connector 指定的连接器
	 * 
	 * @returns 如果指定的连接器与数据处理构件的指定参数成功建立连接关系，则返回 true；否则，返回 false。
	 * 
	 */
	public boolean setOutputConnector(String parameter, IConnector connector);

	/**
	 * 为数据处理构件指定的输入参数设置参数值
	 * 
	 * <p>数据处理构件输入参数的值的设置是通过本方法实现的，
	 * 同时，在该方法中还需要实现检查参数赋值的有效性，即检查对数据处理构件输入参数的设置是否正确，
	 * 检查用户设置的参数是否存在于数据处理构件所定义的参数列表中，以及所设置的参数值的类型是否与参数列表中定义的该参数的类型一致，
	 * 如果不一致，则输入参数值设置失败。
	 * 
	 * @param parameter 指定的要设置参数值的数据处理构件输入参数的参数名称
	 * @param value 指定的参数值
	 * 
	 * @returns  如果参数值设置成功，返回 true；否则，返回 false
	 * 
	 */
	public boolean setInputValue(String parameter, Object value);

	/**
	 * 返回数据处理构件指定输出参数的参数值，即数据处理执行后，该输出参数对应的执行结果数据
	 * 
	 * @param parameter 指定的数据处理输出参数的参数名称
	 * 
	 * @returns 返回数据处理构件执行后，指定输出参数输出的结果数据 	 
	 */
	public Object getOutputValue(String parameter);

	/**
	 * 执行数据处理构件的数据处理操作
	 * 
	 * <p>数据处理构件主要的数据处理过程的代码在该方法中实现。数据处理构件执行的入口点即为该方法。
	 * 
	 * @returns  如果数据处理构件执行成功，返回 true；否则，返回 false
	 * 
	 */
	public boolean execute();
	
	public void setData(Object obj);
	
	public Object getData();
}