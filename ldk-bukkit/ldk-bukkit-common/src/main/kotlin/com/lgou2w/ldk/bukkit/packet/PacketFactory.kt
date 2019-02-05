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

package com.lgou2w.ldk.bukkit.packet

import com.lgou2w.ldk.bukkit.entity.EntityFactory
import com.lgou2w.ldk.bukkit.reflect.MinecraftReflection
import com.lgou2w.ldk.bukkit.reflect.lazyMinecraftClass
import com.lgou2w.ldk.reflect.AccessorField
import com.lgou2w.ldk.reflect.AccessorMethod
import com.lgou2w.ldk.reflect.FuzzyReflect
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object PacketFactory {

    @JvmStatic val CLASS_PACKET by lazyMinecraftClass("Packet")
    @JvmStatic val CLASS_PACKET_LISTENER by lazyMinecraftClass("PacketListener")
    @JvmStatic val CLASS_NETWORK_MANAGER by lazyMinecraftClass("NetworkManager")
    @JvmStatic val CLASS_PLAYER_CONNECTION by lazyMinecraftClass("PlayerConnection")

    // NMS.EntityPlayer -> public NMS.PlayerConnection playerConnection
    @JvmStatic val FIELD_PLAYER_CONNECTION: AccessorField<Any, Any> by lazy {
        FuzzyReflect.of(EntityFactory.CLASS_ENTITY_PLAYER, true)
                .useFieldMatcher()
                .withType(CLASS_PLAYER_CONNECTION)
                .resultAccessor()
    }

    // NMS.PlayerConnection -> public final NMS.NetworkManager networkManager
    @JvmStatic val FIELD_NETWORK_MANAGER: AccessorField<Any, Any> by lazy {
        FuzzyReflect.of(CLASS_PLAYER_CONNECTION, true)
                .useFieldMatcher()
                .withType(CLASS_NETWORK_MANAGER)
                .resultAccessor()
    }

    // NMS.PlayerConnection -> public void sendPacket(NMS.Packet)
    @JvmStatic val METHOD_PACKET_SEND: AccessorMethod<Any, Any> by lazy {
        FuzzyReflect.of(CLASS_PLAYER_CONNECTION, true)
                .useMethodMatcher()
                .withName("sendPacket")
                .withParams(CLASS_PACKET)
                .resultAccessor()
    }

    // NMS.Packet -> public void process(NMS.PacketListener)
    @JvmStatic val METHOD_PACKET_PROCESS: AccessorMethod<Any, Any> by lazy {
        FuzzyReflect.of(CLASS_PACKET, true)
                .useMethodMatcher()
                .withParams(CLASS_PACKET_LISTENER)
                .resultAccessor()
    }

    @JvmStatic
    fun sendPacket(player: Player, packet: Any) {
        MinecraftReflection.isExpected(packet, CLASS_PACKET)
        val handle = EntityFactory.asNMS(player)
        val connection = FIELD_PLAYER_CONNECTION[handle]
        METHOD_PACKET_SEND.invoke(connection, packet)
    }

    @JvmStatic
    fun processPacket(packet: Any, player: Player) {
        MinecraftReflection.isExpected(packet, CLASS_PACKET)
        val handle = EntityFactory.asNMS(player)
        val connection = FIELD_PLAYER_CONNECTION[handle]
        METHOD_PACKET_PROCESS.invoke(packet, connection)
    }

    @JvmStatic
    fun sendPacketTo(packet: Any, vararg players: Player) {
        MinecraftReflection.isExpected(packet, CLASS_PACKET)
        players.forEach {
            try {
                sendPacket(it, packet)
            } catch (e: Exception) {
            }
        }
    }

    @JvmStatic
    fun sendPacketToAll(packet: Any)
            = sendPacketTo(packet, *Bukkit.getOnlinePlayers().toTypedArray())

    @JvmStatic
    fun sendPacketToNearby(packet: Any, center: Location, range: Double) {
        MinecraftReflection.isExpected(packet, CLASS_PACKET)
        val squared = if (range < 1.0) 1.0 else range * range
        val receivers = center.world.players.filter { it.location.distanceSquared(center) <= squared }
        sendPacketTo(packet, *receivers.toTypedArray())
    }

    /**
     * @since LDK 0.1.7-rc3
     */
    @JvmStatic
    fun processPacketTo(packet: Any, vararg players: Player) {
        MinecraftReflection.isExpected(packet, CLASS_PACKET)
        players.forEach {
            try {
                processPacket(packet, it)
            } catch (e: Exception) {
            }
        }
    }

    /**
     * @since LDK 0.1.7-rc3
     */
    @JvmStatic
    fun processPacketToAll(packet: Any)
            = processPacketTo(packet, *Bukkit.getOnlinePlayers().toTypedArray())

    /**
     * @since LDK 0.1.7-rc3
     */
    @JvmStatic
    fun processPacketToNearby(packet: Any, center: Location, range: Double) {
        MinecraftReflection.isExpected(packet, CLASS_PACKET)
        val squared = if (range < 1.0) 1.0 else range * range
        val senders = center.world.players.filter { it.location.distanceSquared(center) <= squared }
        processPacketTo(packet, *senders.toTypedArray())
    }
}
