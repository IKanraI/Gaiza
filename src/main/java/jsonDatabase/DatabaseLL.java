package jsonDatabase;

public class DatabaseLL 
{
	Node head;	
	
	public class Node
	{
		private String serverName;
		private String serverID;
		private String serverPrefix;
		private Node next;

		public Node(String currID, String currName)
		{
			serverID = currID;
			serverName = currName;
		}
	}
	
	public void insertNewServerInfo(DatabaseLL currLL, String currID, String currName)
	{
		String getID = currID;
		String getName = currName;
		
		Node newNode = new Node(getID, getName);
		Node insert;
		
		newNode.next = null;
		
		if (currLL.head == null)
		{
			currLL.head = newNode;
		}
		else
		{
			insert = currLL.head;
			
			while (insert.next != null)
			{
				insert = insert.next;
			}
			
			insert.next = newNode;
		}
	}
	
	public void printLinkedList(DatabaseLL currLL)
	{
		Node currNode = currLL.head;
		
		while (currNode != null)
		{
			System.out.println("Server ID: " + currNode.serverID + " Server Name: " + currNode.serverName);
			
			currNode = currNode.next;
		}
	}
	
	
	//Setters and Getters
	//Need to add setters for the other variables despite how they are initially set
	
	public void setServerPrefix(DatabaseLL currLL, String newPrefix, int serverSelect)
	{
		Node currNode = currLL.head;
		String getPrefix = newPrefix;
		int getServer = serverSelect;
		int i;
		
		for (i = 0; i < getServer; ++i)
		{
			currNode = currNode.next;
		}
		
		currNode.serverPrefix = getPrefix;
	}
	
	public String getCurrServerID(DatabaseLL currList, int listPosition)
	{
		Node currNode = currList.head;
		int i = 0;
		
		for (i = 0; i < listPosition; ++i)
		{
			currNode = currNode.next;
		}
		
		return currNode.serverID;
	}
	
	public String getCurrServerName(DatabaseLL currList, int listPosition)
	{
		Node currNode = currList.head;
		int i = 0;
		
		for (i = 0; i < listPosition; ++i)
		{
			currNode = currNode.next;
		}
		
		return currNode.serverName;
	}
	
	public String getServerPrefix(DatabaseLL currLL, int serverSelect)
	{
		Node currNode = currLL.head;
		int getServer = serverSelect;
		int i;
		
		for (i = 0; i < getServer; ++i)
		{
			currNode = currNode.next;
		}
		
		return currNode.serverPrefix;
		
	}
	
	public int size(DatabaseLL currList)
	{
		int sizeLL = 0;
		Node currNode = currList.head;
		
		while (currNode != null)
		{
			++sizeLL;
			
			currNode = currNode.next;
		}
		
		return sizeLL;
	}
	
	
}
