package com.kundansonuj.cache;


import java.util.HashMap;



public class CachedObject implements Cacheable
{
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /*  This variable will be used to determine if the object is expired.
    */
    private java.util.Date dateofExpiration = null;
    private Integer groupId = null;
    public HashMap<Integer, Object> object = new HashMap<Integer, Object>();
    /*  This contains the real "value".  This is the object which needs to
be
        shared.
    */
  //  public Object object = null;
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public CachedObject(Object obj, Integer groupId, Integer userId, int minutesToLive)
    {
     // this.object = obj;
      this.groupId = groupId;
      try{
      this.object.put(userId, obj) ;
      }
      catch (Exception e) {
		e.printStackTrace();
	}// minutesToLive of 0 means it lives on indefinitely.
      if (minutesToLive != 0)
      {
        dateofExpiration = new java.util.Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(dateofExpiration);
        cal.add(cal.MINUTE, minutesToLive);
        dateofExpiration = cal.getTime();
      }
    }
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public boolean isExpired()
    {
        // Remember if the minutes to live is zero then it lives forever!
        if (dateofExpiration != null)
        {
          // date of expiration is compared.
          if (dateofExpiration.before(new java.util.Date()))
          {
            System.out.println("Cache Expired! EXPIRE TIME: " + dateofExpiration.toString() + " CURRENT TIME: " +
(new java.util.Date()).toString());
            return true;
          }
          else
          {
            System.out.println("Cache Not Expired!");
            return false;
          }
        }
        else // This means it lives forever!
          return false;
    }
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public Integer getIdentifier()
    {
      return groupId;
    }
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}
