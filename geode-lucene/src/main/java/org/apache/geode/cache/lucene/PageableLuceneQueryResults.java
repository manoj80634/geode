/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
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

package org.apache.geode.cache.lucene;

import org.apache.geode.annotations.Experimental;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This interface allows you to retrieve a page of query results at a time, using the
 * {@link #hasNext()} and {@link #next()} methods.
 * </p>
 *
 * Each page is fetched individually from the server, so {@link PageableLuceneQueryResults} cannot
 * be serialized and sent to other members.
 *
 * @see LuceneQuery#findPages()
 *
 * @param <K> The type of the key
 * @param <V> The type of the value
 */
@Experimental
public interface PageableLuceneQueryResults<K, V> extends Iterator<List<LuceneResultStruct<K, V>>> {
  /**
   * @return total number of hits for this query across all pages.
   */
  public int size();

  /**
   * Returns the maximum score value across all pages.
   */
  public float getMaxScore();

  /**
   * Get the next page of results.
   * 
   * @return a page of results, or null if there are no more pages
   */
  public List<LuceneResultStruct<K, V>> next();

  /**
   * True if there another page of results.
   */
  public boolean hasNext();
}
