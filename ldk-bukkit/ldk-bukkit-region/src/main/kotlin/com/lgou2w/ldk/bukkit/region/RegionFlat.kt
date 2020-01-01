/*
 * Copyright (C) 2016-2020 The lgou2w <lgou2w@hotmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lgou2w.ldk.bukkit.region

/**
 * ## RegionFlat (平面区域)
 *
 * @see [Region]
 * @author lgou2w
 */
interface RegionFlat : Region {

  /**
   * * The minimum Y coordinate of this flat region.
   * * 此平面区域的最小 Y 坐标.
   */
  val minimumY : Int

  /**
   * * The maximum Y coordinate of this flat region.
   * * 此平面区域的最大 Y 坐标.
   */
  val maximumY : Int

  fun asFlat(): Iterable<RegionVector2D>
}
