package jsonDatabase;

public class DatabaseLL 
{
	Node head;	
	
	public class Node
	{
		private String serverName;
		private String serverID;
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
		newNode.next = null;
		
		if (currLL.head == null)
		{
			currLL.head = newNode;
		}
		else
		{
			Node insert = currLL.head;
			
			while (insert.next != null)
			{
				insert = insert.next;
			}
			
			insert.next = newNode;
		}
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
	
	public void printLinkedList(DatabaseLL currLL)
	{
		Node currNode = currLL.head;
		
		while (currNode != null)
		{
			System.out.println("Server ID: " + currNode.serverID + " Server Name: " + currNode.serverName);
			
			currNode = currNode.next;
		}
	}
}
