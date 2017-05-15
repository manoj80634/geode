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



import java.util.UUID;


import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

import org.apache.geode.cache.EntryEvent;


import org.apache.geode.internal.cache.lru.EnableLRU;


import org.apache.geode.internal.cache.persistence.DiskRecoveryStore;


import org.apache.geode.internal.InternalStatisticsDisabledException;






import org.apache.geode.distributed.internal.membership.InternalDistributedMember;
import org.apache.geode.internal.cache.versions.VersionSource;
import org.apache.geode.internal.cache.versions.VersionStamp;
import org.apache.geode.internal.cache.versions.VersionTag;







import org.apache.geode.internal.util.concurrent.CustomEntryConcurrentHashMap.HashEntry;

// macros whose definition changes this class:
// disk: 1
// lru: LRU
// stats: 1
// versioned: 1
// offheap: OFFHEAP
// One of the following key macros must be defined:
// key object: KEY_OBJECT
// key int: KEY_INT
// key long: KEY_LONG
// key uuid: 1
// key string1: KEY_STRING1
// key string2: KEY_STRING2

/**
 * Do not modify this class. It was generated. Instead modify LeafRegionEntry.cpp and then run
 * ./dev-tools/generateRegionEntryClasses.sh (it must be run from the top level directory).
 */
public class VersionedStatsDiskRegionEntryHeapUUIDKey extends VersionedStatsDiskRegionEntryHeap {
  public VersionedStatsDiskRegionEntryHeapUUIDKey  (RegionEntryContext context, UUID key, 



      Object value



      ) {
    super(context, 

          (value instanceof RecoveredEntry ? null : value)



        );
    // DO NOT modify this class. It was generated from LeafRegionEntry.cpp

    initialize(context, value);








    this.keyMostSigBits = key.getMostSignificantBits();
    this.keyLeastSigBits = key.getLeastSignificantBits();

  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  // common code
  protected int hash;
  private HashEntry<Object, Object> next;
  @SuppressWarnings("unused")
  private volatile long lastModified;
  private static final AtomicLongFieldUpdater<VersionedStatsDiskRegionEntryHeapUUIDKey> lastModifiedUpdater
    = AtomicLongFieldUpdater.newUpdater(VersionedStatsDiskRegionEntryHeapUUIDKey.class, "lastModified");

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
  
  // disk code

  protected void initialize(RegionEntryContext context, Object value) {
    diskInitialize(context, value);
  }
  @Override
  public int updateAsyncEntrySize(EnableLRU capacityController) {
    throw new IllegalStateException("should never be called");
  }


  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  private void diskInitialize(RegionEntryContext context, Object value) {
    DiskRecoveryStore drs = (DiskRecoveryStore)context;
    DiskStoreImpl ds = drs.getDiskStore();
    long maxOplogSize = ds.getMaxOplogSize();
    //get appropriate instance of DiskId implementation based on maxOplogSize
    this.id = DiskId.createDiskId(maxOplogSize, true/* is persistence */, ds.needsLinkedList());
    Helper.initialize(this, drs, value);
  }

  /**
   * DiskId
   * 
   * @since GemFire 5.1
   */
  protected DiskId id;//= new DiskId();
  public DiskId getDiskId() {
    return this.id;
  }
  @Override
  void setDiskId(RegionEntry old) {
    this.id = ((AbstractDiskRegionEntry)old).getDiskId();
  }
//  // inlining DiskId
//  // always have these fields
//  /**
//   * id consists of
//   * most significant
//   * 1 byte = users bits
//   * 2-8 bytes = oplog id
//   * least significant.
//   * 
//   * The highest bit in the oplog id part is set to 1 if the oplog id
//   * is negative.
//   * @todo this field could be an int for an overflow only region
//   */
//  private long id;
//  /**
//   * Length of the bytes on disk.
//   * This is always set. If the value is invalid then it will be set to 0.
//   * The most significant bit is used by overflow to mark it as needing to be written.
//   */
//  protected int valueLength = 0;
//  // have intOffset or longOffset
//  // intOffset
//  /**
//   * The position in the oplog (the oplog offset) where this entry's value is
//   * stored
//   */
//  private volatile int offsetInOplog;
//  // longOffset
//  /**
//   * The position in the oplog (the oplog offset) where this entry's value is
//   * stored
//   */
//  private volatile long offsetInOplog;
//  // have overflowOnly or persistence
//  // overflowOnly
//  // no fields
//  // persistent
//  /** unique entry identifier * */
//  private long keyId;

  



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
  
  private static final AtomicIntegerFieldUpdater<VersionedStatsDiskRegionEntryHeapUUIDKey> hitCountUpdater 
    = AtomicIntegerFieldUpdater.newUpdater(VersionedStatsDiskRegionEntryHeapUUIDKey.class, "hitCount");
  private static final AtomicIntegerFieldUpdater<VersionedStatsDiskRegionEntryHeapUUIDKey> missCountUpdater 
    = AtomicIntegerFieldUpdater.newUpdater(VersionedStatsDiskRegionEntryHeapUUIDKey.class, "missCount");
  
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
  
  // versioned code
  private VersionSource memberID;
  private short entryVersionLowBytes;
  private short regionVersionHighBytes;
  private int regionVersionLowBytes;
  private byte entryVersionHighByte;
  private byte distributedSystemId;

  public int getEntryVersion() {
    return ((entryVersionHighByte << 16) & 0xFF0000) | (entryVersionLowBytes & 0xFFFF);
  }
  
  public long getRegionVersion() {
    return (((long)regionVersionHighBytes) << 32) | (regionVersionLowBytes & 0x00000000FFFFFFFFL);  
  }
  
  
  public long getVersionTimeStamp() {
    return getLastModified();
  }
  
  public void setVersionTimeStamp(long time) {
    setLastModified(time);
  }

  public VersionSource getMemberID() {
    return this.memberID;
  }
  public int getDistributedSystemId() {
    return this.distributedSystemId;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  public void setVersions(VersionTag tag) {
    this.memberID = tag.getMemberID();
    int eVersion = tag.getEntryVersion();
    this.entryVersionLowBytes = (short)(eVersion & 0xffff);
    this.entryVersionHighByte = (byte)((eVersion & 0xff0000) >> 16);
    this.regionVersionHighBytes = tag.getRegionVersionHighBytes();
    this.regionVersionLowBytes = tag.getRegionVersionLowBytes();
    if (!(tag.isGatewayTag()) && this.distributedSystemId == tag.getDistributedSystemId()) {
      if (getVersionTimeStamp() <= tag.getVersionTimeStamp()) {
        setVersionTimeStamp(tag.getVersionTimeStamp());
      } else {
        tag.setVersionTimeStamp(getVersionTimeStamp());
      }
    } else {
      setVersionTimeStamp(tag.getVersionTimeStamp());
    }
    this.distributedSystemId = (byte)(tag.getDistributedSystemId() & 0xff);
  }

  public void setMemberID(VersionSource memberID) {
    this.memberID = memberID; 
  }

  @Override
  public VersionStamp getVersionStamp() {
    return this;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  public VersionTag asVersionTag() {
    VersionTag tag = VersionTag.create(memberID);
    tag.setEntryVersion(getEntryVersion());
    tag.setRegionVersion(this.regionVersionHighBytes, this.regionVersionLowBytes);
    tag.setVersionTimeStamp(getVersionTimeStamp());
    tag.setDistributedSystemId(this.distributedSystemId);
    return tag;
  }

  public void processVersionTag(LocalRegion r, VersionTag tag,
      boolean isTombstoneFromGII, boolean hasDelta,
      VersionSource thisVM, InternalDistributedMember sender, boolean checkForConflicts) {
    basicProcessVersionTag(r, tag, isTombstoneFromGII, hasDelta, thisVM, sender, checkForConflicts);
  }

  @Override
  public void processVersionTag(EntryEvent cacheEvent) {
    // this keeps Eclipse happy. without it the sender chain becomes confused
    // while browsing this code
    super.processVersionTag(cacheEvent);
  }

  /** get rvv internal high byte. Used by region entries for transferring to storage */
  public short getRegionVersionHighBytes() {
    return this.regionVersionHighBytes;
  }
  
  /** get rvv internal low bytes. Used by region entries for transferring to storage */
  public int getRegionVersionLowBytes() {
    return this.regionVersionLowBytes;
  }

  
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  
  // key code

  private final long keyMostSigBits;
  private final long keyLeastSigBits;
  @Override
  public Object getKey() {
    return new UUID(this.keyMostSigBits, this.keyLeastSigBits);
  }
  @Override
  public boolean isKeyEqual(Object k) {
    if (k instanceof UUID) {
      UUID uuid = (UUID) k;
      return uuid.getLeastSignificantBits() == this.keyLeastSigBits
          && uuid.getMostSignificantBits() == this.keyMostSigBits;
    }
    return false;
  }
  

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
}

