package com.example.calculatorapp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.example.calculatorapp.utils.logD

class CalculatorActivity : AppCompatActivity() {
    lateinit var tvData: TextView
    val listDigits = mutableListOf<String>()
    val listOperators = mutableListOf<String>()
    var firstNumber: String = ""
    var isOperator: Boolean = false
    var ischanged: Boolean = false
    var isDigit: Boolean = false
    var result: String = ""
    var preSigned = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvData = findViewById(R.id.tvData)
        tvData.movementMethod = ScrollingMovementMethod()
    }

    fun onDigit(view: View) {
        //check to control multiply zeros when no number exist**********************
        if (tvData.text.toString().equals("0")) {
            if ((view as Button).text.toString() != "0") {
                tvData.append((view as Button).text)
                firstNumber += (view as Button).text.toString()
                isDigit = true
                ischanged = false
            }
        } else {
            tvData.append((view as Button).text)
            firstNumber += (view as Button).text.toString()
            isDigit = true
            ischanged = false
        }
    }

    fun onDecimalPoint(view: View) {
        tvData.append(".")
        firstNumber += (view as Button).text.toString()
    }

    fun onOperator(view: View) {
        if (listDigits != null && firstNumber.isNotEmpty() && !ischanged) {
            listDigits.add(firstNumber)
            firstNumber = ""
        }
        //If plus-minus(+/-) button is pressed when only one sign & digit exist in textview**********************
        if ((view as Button).text.equals("+/-")) {
            if (listDigits.size == 1) {
                if (isDigit) {
                    val str = tvData.text.toString()
                    firstNumber = ""
                    if (str.contains("-")) {
                        val res = str.substring(1)
                        tvData.text = ""
                        tvData.text = "+$res"
                    } else {
                        if (str.contains("+")) {
                            val res = str.substring(1)
                            tvData.text = ""
                            tvData.text = "-$res"
                        } else {
                            tvData.text = ""
                            tvData.text = "-$str"
                        }
                    }
                }
                // isDigit = false
            } else {
                val str = tvData.text.toString()
                logD(str.last())
                if (listOperators[listOperators.lastIndex].equals("+")) {
                    val str1 = tvData.text.toString().substring(0, tvData.length() - 2)
                    logD(str1)
                    tvData.text = ""
                    tvData.append("$str1-${str.last()}")
                    listOperators.set(listOperators.lastIndex, "-")
                } else if (listOperators[listOperators.lastIndex].equals("-")) {
                    val str1 = tvData.text.toString().substring(0, tvData.length() - 2)
                    logD(str1)
                    tvData.text = ""
                    tvData.append("$str1+${str.last()}")
                    listOperators.set(listOperators.lastIndex, "+")
                }
            }
        } else {
            firstNumber = ""
            isOperator = true
            //when plus(+) & minus(-) operators pressed when nothing exist in textview*********************
            if (listOperators.size == 0 && !isDigit) {
                if ((view as Button).text.toString().equals("+") || (view as Button).text.toString()
                        .equals("-")
                ) {
                    if (tvData.text.toString() == "+") {
                        tvData.text = ""
                        tvData.append((view as Button).text)
                    } else if (tvData.text.toString() == "-") {
                        tvData.text = ""
                        tvData.append((view as Button).text)
                    } else {
                        tvData.text = ""
                        tvData.append((view as Button).text)
                    }
                    isDigit = false
                } else {
                    tvData.text = ""
                    listOperators.clear()
                    listDigits.clear()
                }
            } else {
                //after digit operator pressed *************************************
                if (isDigit) {
                    tvData.append((view as Button).text)
                    listOperators.add((view as Button).text.toString())
                    isDigit = false
                } else {
                    //when 2nd time operator pressed without pressing any digit************************
                    val str1 = tvData.text.toString()
                    if (str1.endsWith("×") || str1.endsWith("÷") || str1.endsWith("%")) {
                        //when multiply & divide added and want to add minus or plus to digit************************
                        if ((view as Button).text.toString()
                                .equals("+") || (view as Button).text.toString().equals("-")
                        ) {
                            if (!ischanged) {
                                //added new after multiply or divide *************************
                                firstNumber += (view as Button).text.toString()
                                logD(firstNumber)
                                tvData.append((view as Button).text)
                                ischanged = true
                            } else {
                                //replace old one************************
                                val str = tvData.text.toString().substring(0, tvData.length() - 1)
                                tvData.text = ""
                                tvData.append(str + (view as Button).text)
                                firstNumber += (view as Button).text.toString()
                            }

                        } else if ((view as Button).text.toString()
                                .equals("%") || (view as Button).text.toString()
                                .equals("÷") || (view as Button).text.toString().equals("×")
                        ) {
                            val str = tvData.text.toString().substring(0, tvData.length() - 1)
                            logD(str)
                            tvData.text = ""
                            tvData.append(str + (view as Button).text)
                            listOperators.set(
                                listOperators.lastIndex,
                                (view as Button).text.toString()
                            )
                        }
                    } else {
                        if (ischanged) {
                            if ((view as Button).text.toString()
                                    .equals("+") || (view as Button).text.toString().equals("-")
                            ) {
                                //replace old one************************
                                val str = tvData.text.toString().substring(0, tvData.length() - 1)
                                tvData.text = ""
                                tvData.append(str + (view as Button).text)
                                firstNumber += (view as Button).text.toString()
                                logD(firstNumber)
                            }
                        } else {  //when other than * & / pressed then replaced the old one***************************
                            val str = tvData.text.toString().substring(0, tvData.length() - 1)
                            logD(str)
                            tvData.text = ""
                            tvData.append(str + (view as Button).text)
                            listOperators.set(
                                listOperators.lastIndex,
                                (view as Button).text.toString()
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEqual(view: View) {
        if (isDigit) {
            listDigits.add(firstNumber)
            logD("onEqual" + firstNumber)
            firstNumber = ""
            var value = tvData.text.toString()
            if (value.startsWith("+")) {
                preSigned = "+"
            }
            if (value.startsWith("-")) {
                preSigned = "-"
            }
            result = listDigits[0]
            var str = ""

//for DMAS Rule calculations*********************************************
            for (i in 0 until listOperators.size) {
                if (listOperators.indexOf("÷") != -1) {
                    val indexDivide = listOperators.indexOf("÷")
                    logD(indexDivide)
                    str = divideValues(listDigits[indexDivide], listDigits[indexDivide + 1])
                    listDigits.set(indexDivide, str)
                    listDigits.remove(listDigits[indexDivide + 1])
                    listOperators.remove(listOperators[indexDivide])
                } else if (listOperators.indexOf("×") != -1) {
                    val indexMultiply = listOperators.indexOf("×")
                    logD(indexMultiply)
                    str = multiplyValues(listDigits[indexMultiply], listDigits[indexMultiply + 1])
                    listDigits.set(indexMultiply, str)
                    listDigits.remove(listDigits[indexMultiply + 1])
                    listOperators.remove(listOperators[indexMultiply])
                } else if (listOperators.indexOf("%") != -1) {
                    val indexModulus = listOperators.indexOf("%")
                    logD(indexModulus)
                    str = modulusValues(listDigits[indexModulus], listDigits[indexModulus + 1])
                    listDigits.set(indexModulus, str)
                    listDigits.remove(listDigits[indexModulus + 1])
                    listOperators.remove(listOperators[indexModulus])
                } else if (listOperators.indexOf("+") != -1) {
                    val indexPlus = listOperators.indexOf("+")
                    logD(indexPlus)
                    str = addValues(listDigits[indexPlus], listDigits[indexPlus + 1])
                    listDigits.set(indexPlus, str)
                    listDigits.remove(listDigits[indexPlus + 1])
                    listOperators.remove(listOperators[indexPlus])
                } else if (listOperators.indexOf("-") != -1) {
                    val indexMinus = listOperators.indexOf("-")
                    logD(indexMinus)
                    str = subtractValues(listDigits[indexMinus], listDigits[indexMinus + 1])
                    listDigits.set(indexMinus, str)
                    listDigits.remove(listDigits[indexMinus + 1])
                    listOperators.remove(listOperators[indexMinus])
                }
            }

            //for without DMAS Rule calculations*********************************************
//        for (i in 0 until listOperators.size) {
//            listOperators.indexOf("")
//            if (listOperators[i].contains("÷")) {
//                val currentIndex = i
//                str = divideValues(listDigits[currentIndex], listDigits[i + 1])
//                listDigits.remove(listDigits[currentIndex])
//                listDigits.remove(listDigits[i])
//                listDigits.add(currentIndex, str)
//                listOperators.remove(listOperators[currentIndex])
//                break
//            }
//            when (listOperators[i]) {
//                "+" -> result = addValues(result, listDigits[i + 1])
//                "-" -> result = subtractValues(result, listDigits[i + 1])
//                "×" -> result = multiplyValues(result, listDigits[i + 1])
//                "÷" -> result = divideValues(result, listDigits[i + 1])
//                "%" -> result = modulusValues(result, listDigits[i + 1])
//            }
            // }
            logD(listOperators)
            logD(listDigits)
            tvData.text = listDigits.toString().replace("[", "").replace("]", "").replace(",", "")
            listDigits.clear()
            listOperators.clear()
            preSigned = ""
        }
    }

    private fun addValues(num1: String, num2: String): String {
        var num3 = 0.0
        if (preSigned.isNotEmpty()) {
            num3 = (preSigned + num1).toDouble()
            return (num3.toDouble() + num2.toDouble()).toString()
        } else {
            return (num1.toDouble() + num2.toDouble()).toString()
        }
    }

    private fun modulusValues(num1: String, num2: String): String {
        return (num1.toDouble() % num2.toDouble()).toString()
    }

    private fun divideValues(num1: String, num2: String): String {
        return (num1.toDouble() / num2.toDouble()).toString()
    }

    private fun multiplyValues(num1: String, num2: String): String {
        return (num1.toDouble() * num2.toDouble()).toString()
    }

    private fun subtractValues(num1: String, num2: String): String {
        var num3 = 0.0
        if (preSigned.isNotEmpty()) {
            num3 = (preSigned + num1).toDouble()
            return (num3.toDouble() - num2.toDouble()).toString()
        } else {
            return (num1.toDouble() - num2.toDouble()).toString()
        }
    }

    fun onClear(view: View) {
        tvData.text = ""
        listDigits.clear()
        listOperators.clear()
        preSigned = ""
        isDigit = false
        ischanged = false
        firstNumber = ""
    }
}