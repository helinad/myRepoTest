/*
 * Copyright (C) 2012 Sreekumar SH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.constant_therapy.util;

/**
 * Implement to handle removing items.
 * An adapter handling the underlying data 
 * will most likely handle this interface.
 * 
 * @author <a href="http://sreekumar.sh" >Sreekumar SH </a> (sreekumar.sh@gmail.com)
 */
public interface RemoveListener {
	
	/**
	 * Called when an item is to be removed
	 * @param which - indicates which item to remove.
	 */
	void onRemove(int[] which);
}
