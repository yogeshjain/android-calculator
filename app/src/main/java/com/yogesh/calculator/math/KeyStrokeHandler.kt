package com.yogesh.calculator.math

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import android.widget.Button
import com.yogesh.calculator.R
import com.yogesh.calculator.data.MainActivityViewModel
import com.yogesh.calculator.ui.MainActivity
import java.security.InvalidParameterException

/**
 * Created by yogesh on 24/2/18.
 */
class KeyStrokeHandler : View.OnClickListener {
    //State constants
    private val STATE_ZERO = 0
    private val STATE_RESULT = 1
    private val STATE_TYPING = 2
    private val STATE_TYPING_FLOAT = 3


    private lateinit var mText: MutableLiveData<String>
    private var state = STATE_ZERO

    fun bind(activity: MainActivity): LiveData<String> {
        val viewModel = ViewModelProviders.of(activity).get(MainActivityViewModel::class.java)
        mText = viewModel.mText
        activity.findViewById<Button>(R.id.btn_0).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_1).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_2).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_3).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_4).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_5).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_6).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_7).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_8).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_9).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_dot).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_ce).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_cl).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_mod).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_pow).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_div).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_mul).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_add).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_sub).setOnClickListener(this)
        activity.findViewById<Button>(R.id.btn_res).setOnClickListener(this)
        return mText
    }

    override fun onClick(button: View?) {
        when (button?.id) {
            R.id.btn_0 -> process0()
            R.id.btn_1 -> processNum(1)
            R.id.btn_2 -> processNum(2)
            R.id.btn_3 -> processNum(3)
            R.id.btn_4 -> processNum(4)
            R.id.btn_5 -> processNum(5)
            R.id.btn_6 -> processNum(6)
            R.id.btn_7 -> processNum(7)
            R.id.btn_8 -> processNum(8)
            R.id.btn_9 -> processNum(9)
            R.id.btn_dot -> if (state != STATE_TYPING_FLOAT) {
                state = STATE_TYPING_FLOAT; postValue(mText.value + ".")
            }
            R.id.btn_ce -> processCE()
            R.id.btn_cl -> {
                postValue("0"); state = STATE_ZERO
            }
            R.id.btn_mod -> {
                postValue(mText.value + "%"); state = STATE_TYPING
            }
            R.id.btn_pow -> {
                postValue(mText.value + "^"); state = STATE_TYPING
            }
            R.id.btn_div -> {
                postValue(mText.value + "/"); state = STATE_TYPING
            }
            R.id.btn_mul -> {
                postValue(mText.value + "*"); state = STATE_TYPING
            }
            R.id.btn_add -> {
                if (state == STATE_ZERO) {
                    postValue("+")
                } else {
                    postValue(mText.value + "+")
                }
                state = STATE_TYPING
            }
            R.id.btn_sub -> {
                if (state == STATE_ZERO) {
                    postValue("-")
                } else {
                    postValue(mText.value + "-")
                }
                state = STATE_TYPING
            }
            R.id.btn_res -> processEval()
        }
    }

    private fun processEval() {
        state = STATE_RESULT
        try {
            var result = Evaluator.eval(mText.value ?: "")
            if (result.endsWith(".0")) {
                result = result.dropLast(2)
            }
            postValue(result)
        } catch (e: InvalidParameterException) {
            postValue(e.message ?: "0")
        } catch (e: NumberFormatException) {
            postValue("Invalid expression " + mText.value)
        }
    }

    private fun processCE() {
        if (state == STATE_ZERO || state == STATE_RESULT) {
            state = STATE_ZERO
            postValue("0")
        } else {
            if (mText.value?.last() == '.') {
                if (mText.value?.dropLast(1)?.equals("0") == true) {
                    state = STATE_ZERO
                } else {
                    state = STATE_TYPING
                }
            } else if (mText.value?.length ?: 0 <= 1) {
                state = STATE_ZERO
                postValue("0")
                return
            }// else no change in state
            postValue(mText.value?.dropLast(1) ?: "0")
        }
    }

    private fun processNum(i: Int) {
        if (state == STATE_ZERO || state == STATE_RESULT) {
            postValue(i.toString())
            state = STATE_TYPING
        } else {
            postValue(mText.value + i)
        }
    }

    private fun process0() {
        if (state == STATE_ZERO) {
            postValue("0")
        } else if (state == STATE_TYPING || state == STATE_TYPING_FLOAT) {
            postValue(mText.value + "0")
        } else if (state == STATE_RESULT) {
            postValue("0")
            state = STATE_ZERO
        }
    }

    private fun postValue(str: String) {
        mText.postValue(str)
    }

}