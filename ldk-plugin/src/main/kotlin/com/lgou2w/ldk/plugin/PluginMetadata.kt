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

package com.lgou2w.ldk.plugin

/**
 * @author lgou2w
 * @since LDK 0.1.9
 */
class PluginMetadata(
  name: String,
  val main: String,
  val version: String,
  val website: String?,
  val description: String?,
  val authors: List<String>
) {
  val name = name.replace(" ", "_")
  val fullName get() = "$name $version"
}
