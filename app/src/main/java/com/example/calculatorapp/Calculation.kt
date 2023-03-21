package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.operator.Operators
import kotlin.math.roundToInt

class Calculation : AppCompatActivity() {

    lateinit var tvData: TextView
    var isOperators: Boolean = false
    var isNum: Boolean = false
    var preSigned = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvData = findViewById(R.id.tvData)
    }

    fun onDigit(view: View) {
        tvData.append((view as Button).text)
        isOperators = false
        isNum = true
    }

    fun onEqual(view: View) {
        if (isNum) {
            var value = tvData.text.toString()
            if (value.startsWith("+")) {
                preSigned = "+"
                value = value.substring(1)
            }
            if (value.startsWith("-")) {
                preSigned = "-"
                value = value.substring(1)
            }
            if (value.contains("+")) {
                addValues(value)
            } else if (value.contains("-")) {
                subtractValues(value)
            } else if (value.contains("×")) {
                multiplyValues(value)
            } else if (value.contains("÷")) {
                divideValues(value)
            } else if (value.contains("%")) {
                remainderValues(value)
            } else if (value.contains("+/-")) {
                remainderValues(value)
            }
        }
    }


    fun onOperator(view: View) {
        if (!isOperators) {
           val check= tvData.text.toString()
            val str=(view as Button).text.toString()
            val splitValue=check.split(str)
            if(splitValue.size<=1){
//            val val1=splitValue[0]
//            val val2=splitValue[1]
                if ((view as Button).text.equals("+/-")) {
                    if (tvData.text.contains("-")) {
                        val value = tvData.text.toString()
                        val res = value.substring(1)
                        tvData.text = ""
                        tvData.text = "+$res"
                    } else if (tvData.text.contains("+") || tvData.text.contains("")) {
                        lateinit var value: String
                        if (tvData.text.contains("+")) {
                            value = tvData.text.toString()
                        } else {
                            value = "+" + tvData.text.toString()
                        }
                        val res = value.substring(1)
                        tvData.text = ""
                        tvData.text = "-$res"
                    }
                    isOperators=false
                    isNum=true
                } else {
                    tvData.append((view as Button).text)
                    isOperators = true
                    isNum=false
                }
            }

        }
    }

    fun onClear(view: View) {
        tvData.text = ""
        isOperators = false
        isNum=false
    }

    fun onDecimalPoint(view: View) {
        tvData.append(".")
        isNum=false
    }

    private fun remainderValues(inputValue: String) {
        val res = inputValue.split("%")
        var num1 = res[0]
        val num2 = res[1]
        if (preSigned.isNotEmpty()) {
            num1 = preSigned + num1
        }
        if (num1.isNotEmpty() && num2.isNotEmpty()) {
            val sum = num1.toDouble() % num2.toDouble()
            tvData.text = sum.toString()
        }
    }

    private fun divideValues(inputValue: String) {
        val res = inputValue.split("÷")
        var num1 = res[0]
        val num2 = res[1]
        if (preSigned.isNotEmpty()) {
            num1 = preSigned + num1
        }
        if (num1.isNotEmpty() && num2.isNotEmpty()) {
            val sum = num1.toDouble() / num2.toDouble()
            tvData.text = sum.toString()
        }
    }

    private fun multiplyValues(inputValue: String) {
        val res = inputValue.split("×")
        val num1 = res[0]
        val num2 = res[1]
        if (num1.isNotEmpty() && num2.isNotEmpty()) {
            val sum = num1.toDouble() * num2.toDouble()
            tvData.text = sum.toString()
        }
    }

    private fun subtractValues(inputValue: String) {
        val res = inputValue.split("-")
        var num1 = res[0]
        val num2 = res[1]
        if (preSigned.isNotEmpty()) {
            num1 = preSigned + num1
        }
        if (num1.isNotEmpty() && num2.isNotEmpty()) {
            val sum = num1.toDouble() - num2.toDouble()
            tvData.text = sum.toString()
        }
    }

    private fun addValues(inputValue: String) {
        val res = inputValue.split("+")
        var num1 = res[0]
        val num2 = res[1]
        if (preSigned.isNotEmpty()) {
            num1 = preSigned + num1
        }
        if (num1.isNotEmpty() && num2.isNotEmpty()) {
            val sum = num1.toDouble() + num2.toDouble()
            tvData.text = sum.toString()
        }
    }
}

