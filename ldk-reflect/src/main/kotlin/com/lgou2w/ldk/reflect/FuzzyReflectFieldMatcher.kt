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

package com.lgou2w.ldk.reflect

import com.lgou2w.ldk.common.BiFunction
import com.lgou2w.ldk.common.Callable
import com.lgou2w.ldk.common.Predicate
import com.lgou2w.ldk.common.letIfNotNull
import java.lang.reflect.Field
import java.util.*

/**
 * ## FuzzyReflectFieldMatcher (模糊反射字段匹配器)
 *
 * @see [FuzzyReflect]
 * @see [FuzzyReflectMatcher]
 * @see [Field]
 * @author lgou2w
 */
class FuzzyReflectFieldMatcher(
        reflect: FuzzyReflect,
        initialize: Collection<Field>? = null
) : FuzzyReflectMatcher<Field>(reflect, initialize) {

    override fun with(predicate: Predicate<Field>): FuzzyReflectFieldMatcher {
        return super.with(predicate) as FuzzyReflectFieldMatcher
    }

    override fun <U> with(initialize: Callable<U>, predicate: BiFunction<Field, U, Boolean>): FuzzyReflectFieldMatcher {
        return super.with(initialize, predicate) as FuzzyReflectFieldMatcher
    }

    override fun withVisibilities(vararg visibilities: Visibility): FuzzyReflectFieldMatcher {
        return super.withVisibilities(*visibilities) as FuzzyReflectFieldMatcher
    }

    override fun withName(regex: String): FuzzyReflectFieldMatcher {
        return super.withName(regex) as FuzzyReflectFieldMatcher
    }

    override fun <A : Annotation> withAnnotation(annotation: Class<A>): FuzzyReflectFieldMatcher {
        return super.withAnnotation(annotation) as FuzzyReflectFieldMatcher
    }

    override fun <A : Annotation> withAnnotationIf(annotation: Class<A>, block: Predicate<A>): FuzzyReflectFieldMatcher {
        return super.withAnnotationIf(annotation, block) as FuzzyReflectFieldMatcher
    }

    override fun withType(clazz: Class<*>): FuzzyReflectFieldMatcher {
        val primitiveType = DataType.ofPrimitive(clazz)
        values = values.asSequence().filter { primitiveType.isAssignableFrom(it.type) }.toMutableList()
        return this
    }

    override fun withParams(vararg parameters: Class<*>): FuzzyReflectFieldMatcher {
        return this // Field does not support parameters
    }

    override fun withParamsCount(count: Int): FuzzyReflectFieldMatcher {
        return this // Field does not support parameters
    }

    override fun resultAccessors(): List<AccessorField<Any, Any>>
            = results().map { Accessors.ofField<Any, Any>(it) }

    override fun resultAccessor(): AccessorField<Any, Any>
            = resultAccessorAs()

    override fun resultAccessorOrNull(): AccessorField<Any, Any>?
            = resultOrNull()?.letIfNotNull { Accessors.ofField<Any, Any>(this) }

    /**
     * * Get the first valid result accessor for this fuzzy reflection matcher.
     * * 获取此模糊反射匹配器的第一个有效结果访问器.
     *
     * @throws NoSuchElementException If the match result is empty.
     * @throws NoSuchElementException 如果匹配结果为空.
     */
    fun <T, R> resultAccessorAs(): AccessorField<T, R>
            = Accessors.ofField(result())
}
