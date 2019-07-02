package com.app.f49.extension

import kotlin.reflect.KClass

inline fun <reified T: Any> getValue() : T? = getValue(T::class)

/* We have no way to guarantee that an empty constructor exists, so must return T? instead of T */
fun <T: Any> getValue(clazz: KClass<T>) : T? {
    clazz.constructors.forEach { con ->
        if (con.parameters.size == 0) {
            return con.call()
        }
    }
    return null
}