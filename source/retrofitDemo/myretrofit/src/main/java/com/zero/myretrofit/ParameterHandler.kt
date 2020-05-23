package com.zero.myretrofit

import androidx.annotation.Nullable
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Array.getLength
import java.lang.reflect.Method
import java.util.*
import java.lang.reflect.Array as JavaArray


internal abstract class ParameterHandler<T> {
    @Throws(IOException::class)
    abstract fun apply(
        builder: RequestBuilder?,
        @Nullable value: T?
    )

    fun iterable(): ParameterHandler<Iterable<T?>?>? {
        return object : ParameterHandler<Iterable<T?>?>() {
            @Throws(IOException::class)
            override fun apply(
                builder: RequestBuilder?,
                @Nullable values: Iterable<T?>?
            ) {
                if (values == null) return  // Skip null values.
                for (value in values) {
                    this@ParameterHandler.apply(builder, value)
                }
            }
        }
    }

    fun array(): ParameterHandler<Object> {
        return object : ParameterHandler<Object>() {

            @Throws(IOException::class)
            override fun apply(builder: RequestBuilder?, values: Object?) {
                if (values == null) return  // Skip null values.
                var i = 0
                val size: Int = JavaArray.getLength(values)
                while (i < size) {
                    this@ParameterHandler.apply(builder, JavaArray.get(values, i) as T?)
                    i++
                }
            }
        }
    }

    internal class RelativeUrl(method: Method?, p: Int) :
        ParameterHandler<Any?>() {
        private val method: Method?
        private val p: Int
        override fun apply(
            builder: RequestBuilder?,
            @Nullable value: Any?
        ) {

            builder!!.setRelativeUrl(value!!)
        }

        init {
            this.method = method
            this.p = p
        }
    }


    internal class Query<T>(
        name: String?,
        valueConverter: Converter<T?, String?>?,
        encoded: Boolean
    ) :
        ParameterHandler<T?>() {
        private val name: String?
        private val valueConverter: Converter<T?, String?>?
        private val encoded: Boolean

        @Throws(IOException::class)
        override fun apply(
            builder: RequestBuilder?,
            @Nullable value: T?
        ) {
            if (value == null) return  // Skip null values.
            val queryValue: String = valueConverter?.convert(value) ?: return
            // Skip converted but null values
            builder!!.addQueryParam(name, queryValue, encoded)
        }

        init {
            this.name = Objects.requireNonNull(name, "name == null")
            this.valueConverter = valueConverter
            this.encoded = encoded
        }
    }


    internal class Field<T>(
        name: String?,
        valueConverter: Converter<T?, String?>?,
        encoded: Boolean
    ) :
        ParameterHandler<T?>() {
        private val name: String?
        private val valueConverter: Converter<T?, String?>?
        private val encoded: Boolean

        @Throws(IOException::class)
        override fun apply(
            builder: RequestBuilder?,
            @Nullable value: T?
        ) {
            if (value == null) return  // Skip null values.
            val fieldValue: String = valueConverter?.convert(value) ?: return
            // Skip null converted values
            builder?.addFormField(name, fieldValue, encoded)
        }

        init {
            this.name = Objects.requireNonNull(name, "name == null")
            this.valueConverter = valueConverter
            this.encoded = encoded
        }
    }


}
