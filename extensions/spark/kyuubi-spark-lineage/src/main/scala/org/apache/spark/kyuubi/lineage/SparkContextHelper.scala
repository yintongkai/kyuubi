/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.kyuubi.lineage

import org.apache.spark.SparkContext
import org.apache.spark.internal.config.ConfigEntry
import org.apache.spark.scheduler.SparkListenerEvent
import org.apache.spark.sql.SparkSession

object SparkContextHelper {

  def globalSparkContext: SparkContext = SparkSession.active.sparkContext

  def postEventToSparkListenerBus(
      event: SparkListenerEvent,
      sc: SparkContext = globalSparkContext) {
    sc.listenerBus.post(event)
  }

  def getConf[T](entry: ConfigEntry[T]): T = {
    globalSparkContext.getConf.get(entry)
  }

  def setConf[T](entry: ConfigEntry[T], value: T): Unit = {
    globalSparkContext.conf.set(entry, value)
  }
}
