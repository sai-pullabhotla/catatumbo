/*
 * Copyright 2016 Sai Pullabhotla.
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

package com.jmethods.catatumbo.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class ListFields {
	@Identifier
	private long id;

	private ArrayList<String> arrayList;

	private LinkedList<String> linkedList;

	private Stack<String> stack;

	private Vector<String> vector;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the arrayList
	 */
	public ArrayList<String> getArrayList() {
		return arrayList;
	}

	/**
	 * @param arrayList
	 *            the arrayList to set
	 */
	public void setArrayList(ArrayList<String> arrayList) {
		this.arrayList = arrayList;
	}

	/**
	 * @return the linkedList
	 */
	public LinkedList<String> getLinkedList() {
		return linkedList;
	}

	/**
	 * @param linkedList
	 *            the linkedList to set
	 */
	public void setLinkedList(LinkedList<String> linkedList) {
		this.linkedList = linkedList;
	}

	/**
	 * @return the stack
	 */
	public Stack<String> getStack() {
		return stack;
	}

	/**
	 * @param stack
	 *            the stack to set
	 */
	public void setStack(Stack<String> stack) {
		this.stack = stack;
	}

	/**
	 * @return the vector
	 */
	public Vector<String> getVector() {
		return vector;
	}

	/**
	 * @param vector
	 *            the vector to set
	 */
	public void setVector(Vector<String> vector) {
		this.vector = vector;
	}

}
