package com.kundansonuj.cache;
/**
 * 
 * @author kundansonuj
 * @email :kundansonuj2@gmail.com
 *
 */

public class CacheUsingHashMapTest {
	/*Check for data availability in cache
	 * If not available in cache get from database set to cache and return
	 * Clear from cache very 30 minutes  
	 * 
	 */

	public static void main(String args[]){
		int groupId=1;
		int userId=1234;
		String data="Once upon a time...";

		CachedObject o = (CachedObject)CacheManager.getCache(groupId);

		/* Create an instance of CachedObject, set the minutesToLive to 1
			minute.  Give the
			       object some unique identifier.
		 */
		if(o==null||o.object.get(userId)==null){
			CachedObject co = new CachedObject(data, groupId,userId, 1); // lifespan of cache is set to 1 minutes
			/* Place the object into the cache! */
			CacheManager.putCache(co,userId,data);
			o=(CachedObject)CacheManager.getCache(groupId);

		}  

		if(o!=null)
			data=o.object.get(userId).toString();

	}
}

