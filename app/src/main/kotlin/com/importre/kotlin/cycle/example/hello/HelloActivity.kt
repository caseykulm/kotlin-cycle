package com.importre.kotlin.cycle.example.hello

import android.os.Bundle
import com.importre.kotlin.cycle.*
import com.importre.kotlin.cycle.example.BaseActivity
import com.importre.kotlin.cycle.example.R
import kotlinx.android.synthetic.main.activity_hello.*
import rx.Observable

class HelloActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        Cycle.run(main, DomSource())
    }

    /**
     * Purpose: interpret DOM events as user’s intended actions
     * Input: DOM source
     * Output: Action Streams
     */
    private val intent = { domSource: DomSource ->
        domSource.select(helloEdit).textChanges()
    }

    /**
     * Purpose: manage state
     * Input: Action Streams
     * Output: State Stream
     */
    private val model = { actionStream: Observable<CharSequence> ->
        actionStream.map(::greeting)
    }

    /**
     * Purpose: visually represent state from the Model
     * Input: State Stream
     * Output: Stream of Virtual DOM nodes as the DOM Driver sink
     */
    private val view = { stateStream: Observable<String> ->
        stateStream.map { message -> { helloText.text = message } }
    }

    private val main = { sources: Sources ->
        val intent = intent(sources.dom())
        val model = model(intent)
        val view = view(model)
        Sinks(DomSink(view))
    }
}
