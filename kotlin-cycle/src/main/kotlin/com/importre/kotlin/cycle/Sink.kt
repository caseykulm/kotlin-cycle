package com.importre.kotlin.cycle

import rx.Observable

open class Sink(val name: String,
                val stream: Observable<*>,
                val error: ((error: Throwable) -> Unit)? = null)