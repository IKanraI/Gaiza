package management;

public class UserFineObject implements Comparable
{
	private long fineValue;
	private String userID;
	
	public UserFineObject()
	{
		
	}
	
	public UserFineObject(String uID, long fValue)
	{
		userID = uID;
		fineValue = fValue;
	}
	
	public int compareThis(UserFineObject userCompareObject) 
	{
		int checkReturn;
		
		checkReturn = Long.compare(this.fineValue, userCompareObject.getFineValue());
		
		return checkReturn;
	}

	@Override
	public int compareTo(Object o) throws ClassCastException
	{
		if (o.getClass().equals(this.getClass()))
		{
			return compareThis((UserFineObject) o);
		}
		else
		{
			throw new ClassCastException("holy fuck you can't do that");
		}
	}
	
	public long getFineValue() 
	{
		return fineValue;
	}
	
	public void setFineValue(long fineValue) 
	{
		this.fineValue = fineValue;
	}
	
	public String getUserID() 
	{
		return userID;
	}
	
	public void setUserID(String userID) 
	{
		this.userID = userID;
	}
}
