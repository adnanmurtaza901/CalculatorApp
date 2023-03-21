package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    lateinit var outputTextView: TextView
    var lastNumeric: Boolean = false
    var stateError: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        outputTextView = findViewById(R.id.tvData)
    }

    fun onDigit(view: View) {
        if (stateError) {
            outputTextView.text = (view as Button).text
            stateError = false
        } else {
            outputTextView.append((view as Button).text)
        }
        lastNumeric = true
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !stateError && !lastDot) {
            outputTextView.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !stateError) {
            if ((view as Button).text.equals("+/-")) {
                val text = outputTextView.text.toString()
                val ft = text.toFloat()
                if (ft > 0.0) {
                    outputTextView.append("-")
                } else {
                    outputTextView.append("+")
                }
            } else if ((view as Button).text.equals("÷")) {
                outputTextView.append("/")
            } else if ((view as Button).text.equals("×")) {
                outputTextView.append("*")
            } else {
                outputTextView.append((view as Button).text)
            }
            lastNumeric = false
            lastDot = false
        }
    }


    fun onClear(view: View) {
        this.outputTextView.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    fun onEqual(view: View) {

        if (lastNumeric && !stateError) {
            val text = outputTextView.text.toString()
            val expression = ExpressionBuilder(text).build()
            try {
                val result = expression.evaluate()
                outputTextView.text = result.toString()
                lastDot = true
            } catch (ex: Exception) {
                outputTextView.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

}