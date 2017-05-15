/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.internal.cache;

// DO NOT modify this class. It was generated from LeafRegionEntry.cpp






import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;




import org.apache.geode.internal.cache.lru.EnableLRU;





import org.apache.geode.internal.InternalStatisticsDisabledException;


import org.apache.geode.internal.cache.lru.LRUClockNode;
import org.apache.geode.internal.cache.lru.NewLRUClockHand;

import org.apache.geode.internal.util.concurrent.CustomEntryConcurrentHashMap.HashEntry;

// macros whose definition changes this class:
// disk: DISK
// lru: 1
// stats: 1
// versioned: VERSIONED
// offheap: OFFHEAP
// One of the following key macros must be defined:
// key object: KEY_OBJECT
// key int: KEY_INT
// key long: 1
// key uuid: KEY_UUID
// key string1: KEY_STRING1
// key string2: KEY_STRING2

/**
 * Do not modify this class. It was generated. Instead modify LeafRegionEntry.cpp and then run
 * ./dev-tools/generateRegionEntryClasses.sh (it must be run from the top level directory).
 */
public class VMStatsLRURegionEntryHeapLongKey extends VMStatsLRURegionEntryHeap {
  public VMStatsLRURegionEntryHeapLongKey  (RegionEntryContext context, long key, 



      Object value



      ) {
    super(context, 



          value

        );
    // DO NOT modify this class. It was generated from LeafRegionEntry.cpp








    this.key = key;

  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  // common code
  protected int hash;
  private HashEntry<Object, Object> next;
  @SuppressWarnings("unused")
  private volatile long lastModified;
  private static final AtomicLongFieldUpdater<VMStatsLRURegionEntryHeapLongKey> lastModifiedUpdater
    = AtomicLongFieldUpdater.newUpdater(VMStatsLRURegionEntryHeapLongKey.class, "lastModified");

  private volatile Object value;
  @Override
  protected Object getValueField() {
    return this.value;
  }
  @Override
  protected void setValueField(Object v) {
    this.value = v;
  }

  protected long getLastModifiedField() {
    return lastModifiedUpdater.get(this);
  }
  protected boolean compareAndSetLastModifiedField(long expectedValue, long newValue) {
    return lastModifiedUpdater.compareAndSet(this, expectedValue, newValue);
  }
  /**
   * @see HashEntry#getEntryHash()
   */
  public int getEntryHash() {
    return this.hash;
  }
  protected void setEntryHash(int v) {
    this.hash = v;
  }
  /**
   * @see HashEntry#getNextEntry()
   */
  public HashEntry<Object, Object> getNextEntry() {
    return this.next;
  }
  /**
   * @see HashEntry#setNextEntry
   */
  public void setNextEntry(final HashEntry<Object, Object> n) {
    this.next = n;
  }

  

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  // lru code
  @Override
  public void setDelayedDiskId(LocalRegion r) {





  // nothing needed for LRUs with no disk

  }
  public synchronized int updateEntrySize(EnableLRU capacityController) {
    return updateEntrySize(capacityController, _getValue());  // OFHEAP: _getValue ok w/o incing refcount because we are synced and only getting the size
  }
  
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  public synchronized int updateEntrySize(EnableLRU capacityController,
                                                Object value) {
    int oldSize = getEntrySize();
    int newSize = capacityController.entrySize( getKeyForSizing(), value);
    setEntrySize(newSize);
    int delta = newSize - oldSize;
    return delta;
  }
  public boolean testRecentlyUsed() {
    return areAnyBitsSet(RECENTLY_USED);
  }
  @Override
  public void setRecentlyUsed() {
    setBits(RECENTLY_USED);
  }
  public void unsetRecentlyUsed() {
    clearBits(~RECENTLY_USED);
  }
  public boolean testEvicted() {
    return areAnyBitsSet(EVICTED);
  }
  public void setEvicted() {
    setBits(EVICTED);
  }
  public void unsetEvicted() {
    clearBits(~EVICTED);
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp

  private LRUClockNode nextLRU;
  private LRUClockNode prevLRU;
  private int size;
  public void setNextLRUNode( LRUClockNode next ) {
    this.nextLRU = next;
  }
  public LRUClockNode nextLRUNode() {
    return this.nextLRU;
  }
  public void setPrevLRUNode( LRUClockNode prev ) {
    this.prevLRU = prev;
  }
  public LRUClockNode prevLRUNode() {
    return this.prevLRU;
  }
  public int getEntrySize() {
    return this.size;
  }
  protected void setEntrySize(int size) {
    this.size = size;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  @Override
  public Object getKeyForSizing() {




    // inline keys always report null for sizing since the size comes from the entry size
    return null;

  }



  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  // stats code
  @Override
  public void updateStatsForGet(boolean hit, long time) {
    setLastAccessed(time);
    if (hit) {
      incrementHitCount();
    } else {
      incrementMissCount();
    }
  }
  @Override
  protected void setLastModifiedAndAccessedTimes(long lastModified, long lastAccessed) {
    _setLastModified(lastModified);
    if (!DISABLE_ACCESS_TIME_UPDATE_ON_PUT) { 
      setLastAccessed(lastAccessed);
    }
  }
  private volatile long lastAccessed;
  private volatile int hitCount;
  private volatile int missCount;
  
  private static final AtomicIntegerFieldUpdater<VMStatsLRURegionEntryHeapLongKey> hitCountUpdater 
    = AtomicIntegerFieldUpdater.newUpdater(VMStatsLRURegionEntryHeapLongKey.class, "hitCount");
  private static final AtomicIntegerFieldUpdater<VMStatsLRURegionEntryHeapLongKey> missCountUpdater 
    = AtomicIntegerFieldUpdater.newUpdater(VMStatsLRURegionEntryHeapLongKey.class, "missCount");
  
  @Override
  public long getLastAccessed() throws InternalStatisticsDisabledException {
    return this.lastAccessed;
  }
  private void setLastAccessed(long lastAccessed) {
    this.lastAccessed = lastAccessed;
  }
  @Override
  public long getHitCount() throws InternalStatisticsDisabledException {
    return this.hitCount & 0xFFFFFFFFL;
  }
  @Override
  public long getMissCount() throws InternalStatisticsDisabledException {
    return this.missCount & 0xFFFFFFFFL;
  }
  private void incrementHitCount() {
    hitCountUpdater.incrementAndGet(this);
  }
  private void incrementMissCount() {
    missCountUpdater.incrementAndGet(this);
  }
  @Override
  public void resetCounts() throws InternalStatisticsDisabledException {
    hitCountUpdater.set(this,0);
    missCountUpdater.set(this,0);
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  @Override
  public void txDidDestroy(long currTime) {
    setLastModified(currTime);
    setLastAccessed(currTime);
    this.hitCount = 0;
    this.missCount = 0;
  }
  @Override
  public boolean hasStats() {
    return true;
  }

  

  
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  // key code

  private final long key;
  @Override
  public Object getKey() {
    return this.key;
  }
  @Override
  public boolean isKeyEqual(Object k) {
    if (k instanceof Long) {
      return ((Long) k).longValue() == this.key;
    }
    return false;
  }
  

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
}

