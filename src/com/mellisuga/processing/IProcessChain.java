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
 * 定义数据处理链接口，所有处理链必须实现该接口。该接口继承了{@linkplain com.mellisuga.processing.IProcess IProcess}接口。
 * 
 * <p>处理链是若干个数据处理构件队列化执行的容器，在运行时，处理链负责处理链中所添加的数据处理构件的名称，从建模管理器中查找数据处理构件，
 * 并按照顺序将其追加到处理队列中，并且，处理链负责处理队列的执行、中断与恢复等工作。<br>
 * <p>通过处理链，可以将数据处理按照其执行的逻辑顺序添加到处理链中，即构成处理执行队列，并且，可以对处理执行队列中的数据处理构件建立数据传递关系，
 * 即数据处理构件间的参数对应关系，这样，数据处理构件的输出数据就可以作为与其连接的数据处理构件执行时的输入数据，这样就真正实现了数据处理的流程化和自动化，
 * 处理链可以单独执行，其执行的效果为，处理链中所编排的处理队列，将按照其指定的顺序，顺次执行每一个数据处理构件，同时，完成必要的参数传递。<br>
 * <p>处理链类实现了数据处理链接口，而数据处理链接口又继承了{@linkplain com.mellisuga.processing.IProcess IProcess}接口。
 * 从这方面来看，处理链也是一种模型，处理链也可以作为一种特殊的数据处理构件嵌入到其他模型中。
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:12:56
 */
public interface IProcessChain extends IProcess {

	/**
	 * 获取处理链中所添加的数据处理构件类对象的总个数
	 * @return 数据处理构件个数
	 */
	public int count();

	/**
	 * 根据指定的数据处理构件类的唯一标识名称，创建一个该数据处理构件类的新实例，并将创建的数据处理构件对象追加到处理链中，即追加到处理链对应的处理队列中。
	 * 
	 * <p>该方法将根据指定的数据处理构件类的唯一标识名称，通过建模管理器创建该数据处理构件类的新实例，然后将创建的数据处理构件对象追加到处理链所对应的处理执行队列中，
	 * 将数据处理构件类对象追加到处理链的顺序即为处理链执行时，其中的数据处理构件对象执行的顺序。<br>
	 * <p>可以向处理链中创建和追加同一个数据处理构件类的多个对象实例，即表示该处理链所编排的数据处理流程中包含多个这样的数据处理单元。
	 * 这些同一个数据处理构件类的多个实例，将根据数据处理构件类对象的 ID 号唯一标识。<br>
	 * 
	 * @param name  指定的数据处理构件类的唯一标识名称。
	 * @returns 返回创建并追加后的数据处理构件类对象。 
	 */
	public IProcess addProcess(String name);

	/**
	 * 根据指定的数据处理构件类的唯一标识名称，创建一个该数据处理构件类的新实例，并将创建的数据处理构件对象追加到处理链中，即追加到处理链对应的处理队列中。
	 * 
	 * <p>该方法将根据指定的数据处理构件类的唯一标识名称，通过建模管理器创建该数据处理构件类的新实例，然后将创建的数据处理构件对象追加到处理链所对应的处理执行队列中，
	 * 将数据处理构件类对象追加到处理链的顺序即为处理链执行时，其中的数据处理构件对象执行的顺序。通过该方法追加到处理链的数据处理构件对象，
	 * 可以指定数据处理构件类对象的 ID 号，如果不指定数据处理构件类对象的 ID 号，则在建模管理器创建该数据处理构件类对象时自动指定。<br>
	 * <p>可以向处理链中创建和追加同一个数据处理构件类的多个对象实例，即表示该处理链所编排的数据处理流程中包含多个这样的数据处理单元。
	 * 这些同一个数据处理构件类的多个实例，将根据数据处理构件类对象的 ID 号唯一标识。
	 * 
	 * @param name 指定的数据处理构件类的唯一标识名称。
	 * @param id 指定的数据处理构件类对象的 ID 号，用来区分处理链中同一个数据处理构件类的多个实例。
	 * 
	 * @returns  返回创建并追加后的处理构件类对象。
	 */
	public IProcess addProcess(String name, int id);
	
	public IProcess addProcess(String name, int id,Object data);

	/**
	 * 建立处理链中两个数据处理构件类对象间的参数对应关系。该方法一次可以建立数据处理构件类对象间的多对参数对应关系。
	 * 
	 * <p>追加到处理链的数据处理构件类对象，只是确定了这些数据处理构件类对象的执行顺序，在处理链中，某个数据处理构件的输出数据可以作为下一个数据处理构件执行的输入数据，
	 * 从而实现数据处理的流程化和自动化，因此，可以通过该方法建立指定数据处理构件的输出参数与同它有数据联系的另一个指定数据处理构件的输入参数之间的对应关系，
	 * 即A数据处理构件的哪个输出参数的数据作为 B数据处理构件哪个输入参数的参数值，有了这样的对应关系后，A数据处理构件执行完成后，通过参数对应关系列表，
	 * B数据处理构件的输入参数将从 A数据处理构件对应的输出参数中获得数据，然后再执行。<br>	 
	 * 
	 * @param prevProcess 指定的建立参数对应关系的第一个数据处理构件类对象，这里称为 A 数据处理构件。
	 * @param backProcess 指定的建立参数对应关系的第二个数据处理构件类对象，这里称为 B 数据处理构件。
	 * @param keywords 两个数据处理构件类对象的参数对应关系列表。
	 * 
	 * @returns 如果数据处理构件类对象间的参数对应关系设置成功，返回 true；否则，返回 false。
	 */
	public boolean connectProcesses(IProcess prevProcess, IProcess backProcess, Map<String, String> keywords);

	/**
	 * 建立处理链中两个数据处理构件类对象间的参数对应关系。该方法一次只能建立数据处理构件类对象间的一对参数对应关系。
	 * 
	 * <p>追加到处理链的数据处理构件类对象，只是确定了这些数据处理构件类对象的执行顺序，在处理链中，某个数据处理构件的输出数据可以作为下一个数据处理构件执行的输入数据，
	 * 从而实现数据处理的流程化和自动化，因此，可以通过该方法建立指定数据处理构件的输出参数与同它有数据联系的另一个指定数据处理构件的输入参数之间的对应关系，
	 * 即 A处理构件的哪个输出参数的数据作为 B 处理构件哪个输入参数的参数值，有了这样的对应关系后，A 处构件理执行完成后，通过参数对应关系列表，
	 * B 处理构件的输入参数将从 A 处理构件对应的输出参数中获得数据，然后再执行。<br>
	 * 
	 * @param prevProcess 指定的建立参数对应关系的第一个数据处理构件类对象，这里称为 A 处理构件。
	 * @param backProcess 指定的建立参数对应关系的第二个数据处理构件类对象，这里称为 B 处理构件。
	 * @param prevParaName  指定的 A 处理构件的一个输出参数的参数名称，它将与该方法中<c>backParaName</c>参数指定的 B 处理构件的输入参数建立对应关系。
	 * @param backParaName  指定的 B 处理构件的一个输入参数的参数名称，它将与该方法中<c>prevParaName</c>参数指定的 A 处理构件的输出参数建立对应关系。
	 * 
	 * @returns 如果数据处理构件类对象间的参数对应关系设置成功，返回 true；否则，返回 false。
	 */
	public boolean connectProcesses(IProcess prevProcess, IProcess backProcess, String prevParaName, String backParaName);

	/**
	 * 建立处理链中两个数据处理构件类对象间的参数对应关系。该方法一次只能建立数据处理构件类对象间的一对参数对应关系。
	 * 
	 * <p>追加到处理链的数据处理构件类对象，只是确定了这些数据处理构件类对象的执行顺序，在处理链中，某个数据处理构件的输出数据可以作为下一个数据处理构件执行的输入数据，
	 * 从而实现数据处理的流程化和自动化，因此，可以通过该方法建立指定数据处理构件的输出参数与同它有数据联系的另一个指定数据处理构件的输入参数之间的对应关系，
	 * 即 A 处理构件的哪个输出参数的数据作为 B 处理构件哪个输入参数的参数值，有了这样的对应关系后，A 构件处理执行完成后，通过参数对应关系列表，
	 * B 处理构件的输入参数将从 A 处理构件对应的输出参数中获得数据，然后再执行。<br>
	 * 
	 * @param prevID 指定的建立参数对应关系的第一个数据处理构件类对象的 ID 号，这里称为 A 处理构件。
	 * @param backID 指定的建立参数对应关系的第二个数据处理构件类对象的 ID 号，这里称为 B 处理构件。
	 * @param prevParaName 指定的 A 处理构件的一个输出参数的参数名称，它将与该方法中<c>backParaName</c>参数指定的 B 处理构件的输入参数建立对应关系。
	 * @param backParaName 指定的 B 处理构件的一个输入参数的参数名称，它将与该方法中<c>prevParaName</c>参数指定的 A 处理构件的输出参数建立对应关系。
	 * 
	 * @returns 如果数据处理构件类对象间的参数对应关系设置成功，返回 true；否则，返回 false。
	 */
	public boolean connectProcesses(int prevID, int backID, String prevParaName, String backParaName);

	/**
	 * 建立处理链中两个数据处理构件类对象间的参数对应关系。该方法一次可以建立数据处理构件类对象间的多对参数对应关系。
	 * 
	 * <p>追加到处理链的数据处理构件类对象，只是确定了这些数据处理构件类对象的执行顺序，在处理链中，某个数据处理构件的输出数据可以作为下一个数据处理构件执行的输入数据，
	 * 从而实现数据处理的流程化和自动化，因此，可以通过该方法建立指定数据处理构件的输出参数与同它有数据联系的另一个指定数据处理构件的输入参数之间的对应关系，
	 * 即 A 处理构件的哪个输出参数的数据作为 B 处理构件哪个输入参数的参数值，有了这样的对应关系后，A 处理构件执行完成后，通过参数对应关系列表，
	 * B 处理构件的输入参数将从 A 处理构件对应的输出参数中获得数据，然后再执行。<br>
	 * <p>两个处理构件类对象的参数对应关系列表，是通过<c>Map&lt;String, String&gt;</c>类对象实现的。<br>
	 * 
	 * @param prevID 指定的建立参数对应关系的第一个数据处理构件类对象的 ID 号，这里称为 A 处理构件。
	 * @param backID 指定的建立参数对应关系的第二个数据处理构件类对象的 ID 号，这里称为 B 处理构件。
	 * @param keywords 两个数据处理构件类对象的参数对应关系列表。
	 * 
	 * @returns 如果数据处理构件类对象间的参数对应关系设置成功，返回 true；否则，返回 false。
	 */
	public boolean connectProcesses(int prevID, int backID, Map<String, String> keywords);

	/**
	 * 在处理链中，通过ID号返回对应的数据处理构件类对象。
	 * 
	 * @param id 指定的数据处理构件类对象的ID号，ID号用来区分处理链中同一个数据处理构件类的多个实例。
	 * 
	 * @returns 返回指定 ID 号的数据处理构件类对象。
	 */
	public IProcess getProcessById(int id);
}