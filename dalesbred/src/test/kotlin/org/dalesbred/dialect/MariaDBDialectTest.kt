/*
 * Copyright (c) 2024 Evident Solutions Oy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.dalesbred.dialect

import org.dalesbred.TestDatabaseProvider
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MariaDBDialectTest {

    @Test
    fun detectMariaDbDialect() {
        val dialect = Dialect.detect(TestDatabaseProvider.createMariaDBConnectionProvider())
        assertTrue { dialect is MariaDBDialect }
    }

    @Test
    fun `MariaDB ConnectorJ 3_5_1 or earlier should not override java_sql_Blob returned by ResultSetMetaData`() {
        val dialect = MariaDBDialect().apply { setBlobsAsByteArrays(false) }
        assertNull(dialect.overrideResultSetMetaDataType("java.sql.Blob"))
    }

    @Test
    fun `MariaDB ConnectorJ 3_5_2 or later should override java_sql_Blob returned by ResultSetMetaData with byte array`() {
        val dialect = MariaDBDialect().apply { setBlobsAsByteArrays(true) }
        assertEquals(ByteArray::class.java, dialect.overrideResultSetMetaDataType("java.sql.Blob"))
    }

}
