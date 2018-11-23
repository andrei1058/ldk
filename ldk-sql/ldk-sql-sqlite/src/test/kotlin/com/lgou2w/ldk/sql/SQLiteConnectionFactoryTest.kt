/*
 * Copyright (C) 2018 The lgou2w (lgou2w@hotmail.com)
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

package com.lgou2w.ldk.sql

import com.lgou2w.ldk.common.notNull
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.nio.file.Path
import java.nio.file.Paths

class SQLiteConnectionFactoryTest {

    var cf : ConnectionFactory? = null
    lateinit var file : Path

    @Before
    fun init() {
        file = Paths.get("C:/Users/MoonLake/Desktop/sqlite.db")
        cf = SQLiteConnectionFactory(file)
        cf?.initialize()
    }

    @Test
    @Ignore
    fun test() {
        val connection = cf?.openSession().notNull()
        val statement = connection.createStatement()
        statement.execute("""
            CREATE TABLE IF NOT EXISTS test (
                id INT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                value INT NOT NULL
            );
        """.trimIndent())
        val preparedStatement = connection.prepareStatement("INSERT INTO test (id, name, value) VALUES (?, ?, ?);")
        preparedStatement.setInt(1, 1)
        preparedStatement.setString(2, "lgou2w")
        preparedStatement.setInt(3, 123)
        preparedStatement.executeUpdate()

        preparedStatement.setInt(1, 2)
        preparedStatement.setString(2, "sqlite")
        preparedStatement.setInt(3, 233)
        preparedStatement.executeUpdate()
    }

    @After
    fun close() {
        cf?.shutdown()
        cf = null
    }
}
