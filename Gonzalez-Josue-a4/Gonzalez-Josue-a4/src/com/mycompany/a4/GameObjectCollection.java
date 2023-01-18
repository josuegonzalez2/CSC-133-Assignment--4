package com.mycompany.a4;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {
	
	private ArrayList<GameObject> objects;
	
	public GameObjectCollection() {
		objects = new ArrayList<GameObject>();
	}
	
	/**
	 * Adds object to collection of objects
	 * @param obj
	 */
	@Override
	public void add(GameObject obj) { objects.add(obj); }
	
	/**
	 * Finds the object in the collection of objects
	 * and removes the object from the collection
	 * @param obj
	 */
	public void remove(GameObject obj) { objects.remove(obj);}
	
	/**
	 * Creates an iterator
	 */
	@Override
	public IIterator getIterator() { return new CollectionIterator(); }
	
	/**
	 * Finds and object in the collection of objects
	 * and returns the object when found
	 * @param obj
	 */
	public GameObject getObj(GameObject obj) {
		for (int i=0; i < objects.size(); i++) { 
			   if (objects.get(i) == obj) { 
				   return objects.get(i); 
			   } 
		}
		
		return null;
	}
	
	/**
	 * returns the object at index given
	 * 
	 * @param idx
	 * @return
	 */
	public GameObject getObjAt(int idx) { return objects.get(idx); }
	
	/**
	 * return the size of the collection
	 * 
	 * @return
	 */
	public int getSize() { return objects.size(); }
	
	
	/**
	 * Find an instance of an object
	 * If an instance of an object is found return true
	 * else return false if not found
	 * @param obj
	 */
	public boolean findObj(GameObject obj) {return objects.contains(obj);}
	
	/**
	 * Clears the collection of objects
	 */
	public void clearObjs() { objects.clear();}
	
	/**
	 * find the last instance of an object and return index
	 * 
	 * @param obj
	 * @return
	 */
	public int getLastInstance(GameObject obj) {
		return objects.lastIndexOf(obj);
	}
	
	/**
	 * Nested Iterator class for collection
	 *
	 */
	private class CollectionIterator implements IIterator {
		private int index;
		
		public CollectionIterator() {
			index = -1;
		}
		
		/**
		 * Checks if there is a list
		 * checks if there is another node
		 * return true if it meets all criteria
		 */
		@Override
		public boolean hasNext() {
			
			if(objects.size() <= 0) {
				return false;
			}
			
			if(index == objects.size()-1) {
				return false;
			}
			
			return true;
		}
		
		/**
		 * get Next object
		 */
		@Override
		public GameObject getNext() {
			index++;
			return objects.get(index);
		}
		
		/**
		 * get current object
		 */
		@Override
		public GameObject getCur() {
			return objects.get(index);
		}
	}
}
