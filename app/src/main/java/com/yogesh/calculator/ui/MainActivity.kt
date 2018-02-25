package com.yogesh.calculator.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yogesh.calculator.R
import com.yogesh.calculator.math.KeyStrokeHandler
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val keyStrokeHandler = KeyStrokeHandler()
        val resultTextData = keyStrokeHandler.bind(this)

        resultTextData.observe(this, Observer {
            it?.let {
                et_result.setText(it)
                et_result.setSelection(et_result.text.length)
            }
        })
    }
}
