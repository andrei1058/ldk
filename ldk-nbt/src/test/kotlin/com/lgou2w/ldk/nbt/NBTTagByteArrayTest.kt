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

package com.lgou2w.ldk.nbt

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEqualTo
import org.junit.Test

class NBTTagByteArrayTest {

  @Test fun `NBTTagByteArray - toJson`() {
    val nbt = NBTTagByteArray(value = byteArrayOf(0, 1, 1))
    nbt.toJson() shouldBeEqualTo "[0,1,1]"
    nbt.toMojangson() shouldBeEqualTo "[B; 0B,1B,1B]"
    nbt.toMojangsonWithColor() shouldBeEqualTo "[§cB§r; §60§cB§r, §61§cB§r, §61§cB§r]"
  }

  @Test fun `NBTTagByteArray - toJson - Empty values should only be parentheses`() {
    val nbt = NBTTagByteArray(value = byteArrayOf())
    nbt.toJson() shouldBeEqualTo "[]"
  }

  @Suppress("ReplaceCallWithBinaryOperator")
  @Test fun `NBTTagByteArray - equals`() {
    val nbt1 = NBTTagByteArray(name = "[b")
    val nbt2 = NBTTagByteArray(name = "[b")
    nbt1.value = byteArrayOf(0, 1)
    nbt2.value = byteArrayOf(1, 0)
    nbt1.value shouldNotBeEqualTo nbt2.value
    nbt1.value.size shouldBeEqualTo nbt2.value.size
    nbt1.equals(nbt2) shouldBeEqualTo false
    nbt2.value = nbt1.value // array clone, not reference
    nbt1.equals(nbt2) shouldBeEqualTo true
    nbt2.value = byteArrayOf(1, 0) // Does not affect the value of nbt1
    nbt1.equals(nbt2) shouldBeEqualTo false
  }
}
