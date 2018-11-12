/*
 * VANESSA ZETINO GRANADOS
 * PROJECT 6
 * BINARY SEARCH TREE
 */

//package TreePackage;

import java.util.Iterator;

//import TreePackage.BinaryTree.InorderIterator;

/**
 * A class that implements the ADT binary search tree by extending BinaryTree.
 * Recursive version.
 */
public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> {
	/**
	 * Searches for a specific entry in this tree.
	 * 
	 * @param entry
	 *            An object to be found.
	 * @return True if the object was found in the tree.
	 */
	public boolean contains(T entry) {
		return getEntry(entry) != null;
	}

	/**
	 * Retrieves a specific entry in this tree.
	 * 
	 * @param entry
	 *            An object to be found.
	 * @return Either the object that was found in the tree or null if no such
	 *         object exists.
	 */
	public T getEntry(T entry) {
		return findEntry(getRootNode(), entry);
	}

	private T findEntry(BinaryNode<T> rootNode, T entry) {
		T result = null;
		if (rootNode != null) {
			T rootEntry = rootNode.getData();
			if (entry.equals(rootEntry))
				result = rootEntry;
			else if (entry.compareTo(rootEntry) < 0)
				result = findEntry(rootNode.getLeftChild(), entry);
			else
				result = findEntry(rootNode.getRightChild(), entry);
		}
		return result;
	};

	/**
	 * Adds a new entry to this tree, if it does not match an existing object in
	 * the tree. Otherwise, replaces the existing object with the new entry.
	 * 
	 * @param newEntry
	 *            An object to be added to the tree.
	 * @return Either null if newEntry was not in the tree already, or the
	 *         existing entry that matched the parameter newEntry and has been
	 *         replaced in the tree.
	 */
	public T add(T newEntry) {
		T result = null;

		if (isEmpty())
			setRootNode(new BinaryNode<>(newEntry));
		else
			result = addEntry(getRootNode(), newEntry);

		return result;
	}

	private T addEntry(BinaryNode<T> rootNode, T newEntry) {
		assert rootNode != null;
		T result = null;
		int compare = newEntry.compareTo(rootNode.getData());

		if (compare == 0) {
			result = rootNode.getData();
			rootNode.setData(newEntry);
		} else if (compare < 0) {
			if (rootNode.hasLeftChild())
				result = addEntry(rootNode.getLeftChild(), newEntry);
			else
				rootNode.setLeftChild(new BinaryNode<>(newEntry));
		} else {
			assert compare > 0;

			if (rootNode.hasRightChild())
				result = addEntry(rootNode.getRightChild(), newEntry);
			else
				rootNode.setRightChild(new BinaryNode<>(newEntry));
		}

		return result;
	}

	/**
	 * Removes a specific entry from this tree.
	 * 
	 * @param entry
	 *            An object to be removed.
	 * @return Either the object that was removed from the tree or null if no
	 *         such object exists.
	 */
	public T remove(T entry) {
		ReturnObject oldEntry = new ReturnObject(null);
		BinaryNode<T> newRoot = removeEntry(getRootNode(), entry, oldEntry);
		setRootNode(newRoot);

		return oldEntry.get();
	}

	private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T entry, ReturnObject oldEntry) {
		if (rootNode != null) {
			T rootData = rootNode.getData();
			int compare = entry.compareTo(rootData);

			if (compare == 0) {
				oldEntry.set(rootData);
				rootNode = removeFromRoot(rootNode);
			} else if (compare < 0) {
				BinaryNode<T> leftChild = rootNode.getLeftChild();
				BinaryNode<T> subtreeRoot = removeEntry(leftChild, entry, oldEntry);
				rootNode.setLeftChild(subtreeRoot);
			} else {
				BinaryNode<T> rightChild = rootNode.getRightChild();
				rootNode.setRightChild(removeEntry(rightChild, entry, oldEntry));
			}
		}

		return rootNode;
	}

	private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode) {
		if (rootNode.hasLeftChild() && rootNode.hasRightChild()) {
			BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
			BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);
			rootNode.setData(largestNode.getData());
			rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
		} else if (rootNode.hasRightChild())
			rootNode = rootNode.getRightChild();
		else
			rootNode = rootNode.getLeftChild();

		return rootNode;
	}

	private BinaryNode<T> findLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild())
			rootNode = findLargest(rootNode.getRightChild());

		return rootNode;
	}

	private BinaryNode<T> removeLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild()) {
			BinaryNode<T> rightChild = rootNode.getRightChild();
			rightChild = removeLargest(rightChild);
			rootNode.setRightChild(rightChild);
		} else
			rootNode = rootNode.getLeftChild();
		return rootNode;
	}

	private class ReturnObject {
		private T item;

		private ReturnObject(T entry) {
			item = entry;
		}

		public T get() {
			return item;
		}

		public void set(T entry) {
			item = entry;
		}
	}

	/**
	 * Creates an iterator that traverses all entries in this tree.
	 * 
	 * @return An iterator that provides sequential and ordered access to the
	 *         entries in the tree.
	 */
	// public Iterator<T> getInorderIterator(){
	// return getInorderIterator();
	// }
} // end BinarySearchTree