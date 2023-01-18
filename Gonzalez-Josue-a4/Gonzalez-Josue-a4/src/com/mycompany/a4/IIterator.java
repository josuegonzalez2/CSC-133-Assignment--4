package com.mycompany.a4;

import com.mycompany.a4.GameObject;

public interface IIterator {
	
	public boolean hasNext();
	
	public GameObject getNext();
	
	public GameObject getCur();
}
