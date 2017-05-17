package com.kundansonuj.cache;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;



public class CacheManager {
	/* This is the HashMap that contains all objects in the cache. */
	private static HashMap<Integer,Cacheable> cacheHashMap = new java.util.HashMap<Integer,Cacheable>();
	/* This object acts as a semaphore, which protects the HashMap */
	/* RESERVED FOR FUTURE USE  private static Object lock = new Object(); */
	static
	{
		try
		{
			/* Create background thread, which will be responsible for
purging expired items. */
			Thread threadCleanerUpper = new Thread(
					new Runnable()
					{
						/*  The default time the thread should sleep between scans.
                  The sleep method takes in a millisecond value so 5000 = 5
Seconds.
						 */
						int milliSecondSleepTime = 6000;
						public void run()
						{
							try
							{
								/* Sets up an infinite loop.  The thread will continue looping forever. */
								while (true)
								{
									System.out.println("ThreadCleanerUpper Scanning ~~~~~ No of cached groups"+cacheHashMap.size());				      
									/* Get the set of all keys that are in cache.  These are the unique identifiers */
									java.util.Set keySet = cacheHashMap.keySet();
									/* An iterator is used to move through the Keyset */
									java.util.Iterator keys = keySet.iterator();
									/* Sets up a loop that will iterate through each key in the KeySet */
									while(keys.hasNext())
									{
										/* Get the individual key.  We need to hold on to this 	key in case it needs to be removed */
										Object key = keys.next();
										/* Get the cacheable object associated with the key inside the cache */
										Cacheable value = (Cacheable)cacheHashMap.get(key);
										if(value !=null){
											CachedObject o= (CachedObject)value;
											System.out.println("ThreadCleanerUpper Scanning ~~~~~ No of cached user in group "+key
													+" is "+o.object.size());
										}
										if (value.isExpired())
										{
											cacheHashMap.remove(key);
											System.out.println("ThreadCleanerUpper Running. Found an Expired Object in the Cache.");

										}
									}
									Thread.sleep(this.milliSecondSleepTime);
								}
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							return;
						}
					}); /* End class definition and close new thread definition */

			threadCleanerUpper.setPriority(Thread.MIN_PRIORITY);
			// Starts the thread.
			threadCleanerUpper.start();
		}
		catch(Exception e)
		{
			System.out.println("CacheManager.Static Block: " + e);
		}
	} /* End static block */
	public CacheManager()
	{
	}
	public static void putCache(Cacheable object, int userId, String reportCSV)
	{
		if(!cacheHashMap.containsKey(object.getIdentifier()))
			cacheHashMap.put(object.getIdentifier(),object);
		else {
			CachedObject o=(CachedObject)cacheHashMap.get(object.getIdentifier());
			o.object.put(userId, reportCSV);
			cacheHashMap.put(object.getIdentifier(), o);
		}

		System.out.println("Group Id "+object.getIdentifier()+" values is expired "+object.isExpired() );
	}
	public static Cacheable getCache(int  groupId)
	{
		//synchronized (lock)  
		//{                    
		//Object object=null;
		CachedObject object=null;

		object=(CachedObject)(cacheHashMap.get(groupId));

		// The code to create the object would be placed here.
		//} 


		if (object == null)
			return null;
		if (object.isExpired())
		{
			cacheHashMap.remove(groupId);
			return null;
		}
		else
		{
			return object;
		}
	}
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}