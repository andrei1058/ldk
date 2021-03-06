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

package com.lgou2w.ldk.security

import com.lgou2w.ldk.common.ComparisonChain

/**
 * ## Email (电子邮箱）
 *
 * @since LDK 0.2.1
 */
data class Email(
  val id: String,
  val secondaryDomain: String,
  val topDomain: String
) : Comparable<Email> {

  val fullyQualifiedEmail = id + AT + secondaryDomain + DOT + topDomain

  override fun compareTo(other: Email): Int {
    return ComparisonChain.start()
      .compare(topDomain, other.topDomain)
      .compare(secondaryDomain, other.secondaryDomain)
      .compare(id, other.id)
      .result
  }

  companion object {

    const val AT = '@'
    const val DOT = '.'

    /**
     * @see [SimpleEmailParser]
     * @since LDK 0.2.1
     */
    @Throws(IllegalArgumentException::class)
    @JvmStatic fun fromSimple(fullyQualifiedEmail: String): Email
      = SimpleEmailParser.parse(fullyQualifiedEmail)

    /**
     * @see [SimpleEmailParser]
     * @since LDK 0.2.1
     */
    @JvmStatic fun fromSimpleSafely(fullyQualifiedEmail: String?): Email?
      = SimpleEmailParser.parseSafely(fullyQualifiedEmail)

    /**
     * @see [StandardEmailParser]
     * @since LDK 0.2.1
     */
    @Throws(IllegalArgumentException::class)
    @JvmStatic fun fromStandard(fullyQualifiedEmail: String): Email
      = StandardEmailParser.parse(fullyQualifiedEmail)

    /**
     * @see [StandardEmailParser]
     * @since LDK 0.2.1
     */
    @JvmStatic fun fromStandardSafely(fullyQualifiedEmail: String?): Email?
      = StandardEmailParser.parseSafely(fullyQualifiedEmail)
  }
}
