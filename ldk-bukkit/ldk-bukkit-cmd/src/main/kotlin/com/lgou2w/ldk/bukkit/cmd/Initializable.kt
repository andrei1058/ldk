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

package com.lgou2w.ldk.bukkit.cmd

/**
 * ## Initializable (可初始化)
 *
 * @see [StandardCommand]
 * @author lgou2w
 */
interface Initializable {

  /**
   * * Initialize the command.
   * * 进行命令的初始化.
   */
  fun initialize(command: RegisteredCommand)
}
