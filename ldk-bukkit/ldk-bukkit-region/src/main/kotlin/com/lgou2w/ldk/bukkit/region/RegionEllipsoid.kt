/*
 * Copyright (C) 2018 The lgou2w <lgou2w@hotmail.com>
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

import com.lgou2w.ldk.common.letIfNotNull
import org.bukkit.Bukkit
import org.bukkit.World

open class RegionEllipsoid(
        world: World,
        override var center: RegionVector,
        var radius: RegionVector
) : RegionBase(world) {

    companion object {

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun deserialize(args: Map<String, Any>): RegionEllipsoid {
            val world = args["world"]?.toString().letIfNotNull { Bukkit.getWorld(this) }
                        ?: throw IllegalArgumentException("Unknown world: ${args["world"]}")
            val center = RegionVector.deserialize(args["center"] as Map<String, Any>)
            val radius = RegionVector.deserialize(args["radius"] as Map<String, Any>)
            return RegionEllipsoid(world, center, radius)
        }
    }

    override val minimumPoint : RegionVector
        get() = center - radius

    override val maximumPoint : RegionVector
        get() = center + radius

    override val area : Int
        get() = Math.floor((4 / 3 * Math.PI) * radius.x * radius.y * radius.z).toInt()

    override val width : Int
        get() = (radius.x * 2.0).toInt()

    override val height : Int
        get() = (radius.y * 2.0).toInt()

    override val length : Int
        get() = (radius.z * 2.0).toInt()

    override fun contains(x: Double, y: Double, z: Double): Boolean
            = contains(RegionVector(x, y, z))

    override fun contains(vector: RegionVector): Boolean
            = ((vector - center) / radius).lengthSq() <= 1.0

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result["world"] = world.name
        result["center"] = center.serialize()
        result["radius"] = center.serialize()
        return result
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + center.hashCode()
        result = 31 * result + radius.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other === this)
            return true
        if (other is RegionEllipsoid)
            return super.equals(other) && center == other.center && radius == other.radius
        return false
    }

    override fun toString(): String {
        return "RegionEllipsoid(world=${world.name}, center=$center, radius=$radius)"
    }
}
