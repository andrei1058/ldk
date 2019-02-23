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

package com.lgou2w.ldk.coroutines

import com.lgou2w.ldk.common.SuspendApplicator
import com.lgou2w.ldk.common.SuspendApplicatorFunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

interface CoroutineFactory {

    val provider : DispatcherProvider

    val context : CoroutineContext

    fun launch(block: SuspendApplicator<CoroutineFactory>) : Job

    // Operating function

    suspend fun <T> with(block: SuspendApplicatorFunction<CoroutineScope, T>) : T

    suspend fun <T> with(ctx: CoroutineContext, block: SuspendApplicatorFunction<CoroutineScope, T>) : T

    suspend fun <T> async(block: SuspendApplicatorFunction<CoroutineScope, T>) : Deferred<T>

    suspend fun <T> async(ctx: CoroutineContext, block: SuspendApplicatorFunction<CoroutineScope, T>) : Deferred<T>
}
