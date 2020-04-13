package jsonDatabase;

public class DatabaseLL 
{
	Node head;	
	
	public class Node
	{
		private String serverName;
		private String serverID;
		private String serverPrefix;
		private String welcomeMessageText;
		private String welcomeMessageEnabled;
		private String welcomeChannel;
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
	
	public void removeNode(DatabaseLL currLL, int serverSelect)
	{
		Node currNode = currLL.head;
		Node tempNode = currNode;
		int serverToRemove = serverSelect;
		int i;
		
		for (i = 0; i < serverToRemove; ++i)
		{
			tempNode = currNode;
			currNode = currNode.next;
		}
		
		if (!(tempNode == null))
		{
			tempNode.next = currNode.next;
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
	
	public void setWelcomeEnabled(DatabaseLL currLL, String enableCheck, int serverSelect)
	{
		Node currNode = currLL.head;
		String isEnabled = enableCheck;
		int getServer = serverSelect;
		int i;
		
		for (i = 0; i < getServer; ++i)
		{
			currNode = currNode.next;
		}
		
		currNode.welcomeMessageEnabled = isEnabled;
	}
	
	public void setWelcomeMessage(DatabaseLL currLL, String newWelcomeMessage, int serverSelect)
	{
		Node currNode = currLL.head;
		String welcomeMessage = newWelcomeMessage;
		int getServer = serverSelect;
		int i;
		
		for (i = 0; i < getServer; ++i)
		{
			currNode = currNode.next;
		}
		
		currNode.welcomeMessageText = welcomeMessage;
	}
	
	public void setWelcomeChannel(DatabaseLL currLL, String newWelcomeChannel, int serverSelect)
	{
		Node currNode = currLL.head;
		String welcomeChannel = newWelcomeChannel;
		int getServer = serverSelect;
		int i;
		
		for (i = 0; i < getServer; ++i)
		{
			currNode = currNode.next;
		}
		
		currNode.welcomeChannel = welcomeChannel;
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
	
	public String getServerWelcomeChannel(DatabaseLL currLL, int serverSelect)
	{
		Node currNode = currLL.head;
		int getServer = serverSelect;
		int i;
		
		for (i = 0; i < getServer; ++i)
		{
			currNode = currNode.next;
		}
		
		return currNode.welcomeChannel;
	}
	
	
	public String getServerWelcomeEnabled(DatabaseLL currLL, int serverSelect)
	{
		Node currNode = currLL.head;
		int getServer = serverSelect;
		int i;
		
		for (i = 0; i < getServer; ++i)
		{
			currNode = currNode.next;
		}
		
		return currNode.welcomeMessageEnabled;
	}
	
	public String getServerWelcomeMessage(DatabaseLL currLL, int serverSelect)
	{
		Node currNode = currLL.head;
		int getServer = serverSelect;
		int i;
		
		for (i = 0; i < getServer; ++i)
		{
			currNode = currNode.next;
		}
		
		return currNode.welcomeMessageText;
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
