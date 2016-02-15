

import java.util.AbstractMap;
import java.util.Map;



public class BinaryTrieClass {
	private TrieNode min;

	private class TrieNode{
	    
		public Integer getValue() { 
			//getter method to get value
			return null;}
	
	public void changetoRight(TrieNode tnode){
			
		}
	
		public void changetoLeft(TrieNode tnode){ }
	
		public String getKey(){	
			//getter method to return key
			return null;}
	
		public TrieNode extractRight(){	
			return null;}
	
		public TrieNode extractLeft(){
			return null;} 
	}


	private class TrieNodeElement extends TrieNode{
		// class Trie Node Element
		private String key;
		private Integer value;
		
		public TrieNodeElement(String key, Integer val){
			//constructor
			this.value = val;
			this.key = key;
		}
		
		public String getKey(){
			// getter method for return key
			return key;
		}
		
		public Integer getValue(){
			// getter method to return value
			return value;
		}
		
		
	}
	

	public BinaryTrieClass(){
		//method to construct binary trie class
		min = (TrieNode) new TrieNodeBranch();
		min.changetoRight(null);
		min.changetoLeft(null);
	}
	
	private class TrieNodeBranch extends TrieNode{
		//class trienodebranch
		private TrieNode leftnode;
		private TrieNode rightnode;
		
		public TrieNodeBranch(){
			//constructor
			leftnode = null;
			rightnode = null;
		}
		
		@Override
		public TrieNode extractRight(){
			//method to return rightnode
			return rightnode;
		}
		
		@Override
		public TrieNode extractLeft(){
			//method to return left node
			return leftnode;
		}
		
		public void changetoRight(TrieNode r){
			//method to assign rightnode
			rightnode = r;
		}
		
		public void changetoLeft(TrieNode r){
			//method to assign leftnode
			leftnode = r;
		}
	}
	
	
	private void nodeVisit(TrieNode n1, TrieNode n2, int temp){
		// method to visit nodes of trie
		if(n2.getKey() == null)
		{
			if(n2.extractLeft() != null && n2.extractRight() != null)
			{
				if(n2.extractLeft().getValue() != null && n2.extractRight().getValue() != null)
				{
					if(n2.extractLeft().getValue().equals(n2.extractRight().getValue()))
					{
						if(temp == 1)
						{
							n1.changetoRight(n2.extractLeft());
						}
						else
						{
							n1.changetoLeft(n2.extractLeft());
						}
					}
				}
			}
			else
			{
				if(n2.extractLeft() == null && n2.extractRight() != null)
				{
					if( n2.extractRight().getValue() != null)
					{
						
						if(temp == 1)
						{
							n1.changetoRight(n2.extractRight());
						}
						else
						{
							n1.changetoLeft(n2.extractRight());
						}
						
					}
				}
				else
				{
					if(n2.extractLeft() != null && n2.extractRight() == null)
					{
						if(n2.extractLeft().getValue() != null)
						{
							if(temp == 1)
							{
								n1.changetoRight(n2.extractLeft());
							}
							else
							{
								n1.changetoLeft(n2.extractLeft());
							}
						}
					}
				}				
			}
		}		
	}
	
	private void postOrderTrie(TrieNode n1, TrieNode n2, int val){
		 //method to perform post order traversal of trie
		if (n2 != null){
			postOrderTrie(n2, n2.extractLeft(), 0);
			postOrderTrie(n2, n2.extractRight(), 1);
			nodeVisit(n1, n2, val);
		}
			
	}
	
	public void traverseTrie()
	{
		//method to traverse through trie
		postOrderTrie(null, min, -1);
	}
	
	public void nodeInsert (String keyval, Integer val) throws Exception{
		//method to insert node into Trie
		TrieNode tn1 = min;
		TrieNode tn2 = null;
		TrieNode tn3 = null;
		int level = 0;
		
		while(tn1 != null){
			tn3 = tn2;
			tn2 = tn1;
			if(keyval.substring(level, level+1).equals("0")){
				tn1 = tn1.extractLeft();	
			}
			else{
				tn1 = tn1.extractRight();	
			}
			level += 1;
		}
		
		if(tn2.getKey() == null){ 
			TrieNode newNode = new TrieNodeElement(keyval, val);
			if(keyval.substring(level-1, level).equals("0")){
				tn2.changetoLeft(newNode);
			}
			else{
				tn2.changetoRight(newNode);
			}
		}else{ 
			if(tn2.getKey().equals(keyval)){
				throw new Exception("Inserting duplicated IP key");
			}
			else{
				TrieNode n = new TrieNodeBranch();
				
				if(keyval.substring(level-2, level-1).equals("0")){
					tn3.changetoLeft(n);
				}
				else{
					tn3.changetoRight(n);
				}
				level = level -1;
				while(tn2.getKey().substring(level, level+1).equals(keyval.substring(level, level+1))){
					TrieNode n2 = new TrieNodeBranch();
					
					if(keyval.substring(level, level+1).equals("0"))
						n.changetoLeft(n2);
					else
						n.changetoRight(n2);
					
					n = n2;
					level = level + 1;
				}
				
				if(keyval.substring(level, level+1).equals("0")){
					n.changetoLeft(new TrieNodeElement(keyval, val));
					n.changetoRight(tn2);
				}
				else{
					n.changetoRight(new TrieNodeElement(keyval, val));
					n.changetoLeft(tn2);
				}
			}			
		}
	}
	
	public boolean nodeSearch(String value){
		// method search for node
		return false;
	}
	
	public Map.Entry<Integer, String> nodeFindlpm(String keyval){
		//method to find longest prefix match of ip address
		String matchstr ="";
		Integer map;
		TrieNode pq = null;
		TrieNode q = min;
		int i = 0;
		while(q != null && i < 32){
			pq = q;
			if(keyval.substring(i, i+1).equals("0")){
				q = q.extractLeft();
				if(q != null){matchstr = matchstr+"0";i += 1;}
			}else{
				q = q.extractRight();
				if(q != null){matchstr = matchstr+"1";i += 1;}
			}
			
		}
		if(i == 32) map = q.getValue();
		else map = pq.getValue();
		
		
		Map.Entry<Integer,String> result = new AbstractMap.SimpleEntry<Integer, String>(map ,matchstr );
		return result;
	}
	

}
