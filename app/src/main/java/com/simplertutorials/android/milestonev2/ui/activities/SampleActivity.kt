package com.simplertutorials.android.milestonev2.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import com.simplertutorials.android.milestonev2.R
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        rxPlayGround()
    }

    private fun rxPlayGround() {

        val titleObservable = RxTextView.textChanges(title_editText)
                .map { toString() }
        val messageObservable = RxTextView.textChanges(message_editText)
                .map { toString() }

        val combinedObservable = Observable.combineLatest(
                titleObservable,
                messageObservable,
                BiFunction { Pair() })
    }
}
