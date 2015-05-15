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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志实现类
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:08:03
 *
 */
public class RunningLoggerImpl implements IRunningLogger {
	
	Log _log = null;
	
	
	/*private RunningLoggerImpl(){		
		_log = LogFactory.getLog(RunningLoggerImpl.class);
	}*/

	public RunningLoggerImpl(Class<?> clazz){
		_log = LogFactory.getLog(clazz);
	}	
	
	public void debug(String message) {
		_log.debug(message);
	}

	public void error(String message) {
		_log.error(message);
	}

	public void fatal(String message) {
		_log.fatal(message);
	}

	public void info(String message) {
		_log.info(message);
	}

	public void warn(String message) {
		_log.warn(message);
	}

}



	