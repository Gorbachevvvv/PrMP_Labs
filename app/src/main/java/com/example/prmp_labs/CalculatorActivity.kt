package com.example.prmp_labs

import net.objecthunter.exp4j.ExpressionBuilder
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.prmp_labs.databinding.ActivityCalculatorBinding
import java.util.*
import kotlin.math.*

class CalculatorActivity : AppCompatActivity() {
    private val binding by viewBinding<ActivityCalculatorBinding>(CreateMethod.INFLATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val horizontalScrollView: HorizontalScrollView = findViewById(R.id.horizontalScrollView)
        val operation: TextView = findViewById(R.id.operation)
        val result: TextView = findViewById(R.id.result)

        fun evaluate(expression: String): Double {
            return try {
                evaluateExpression(expression)
            } catch (e: Exception) {
                Double.NaN
            }
        }
        fun scrollToEnd() {
            horizontalScrollView.post {
                horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
            }
        }
        fun isOperator(c: Char): Boolean {
            return c in listOf('+', '-', '*', '/', '%', '^', '.')
        }

        fun appendOperator(operator: String) {
            val expr = operation.text.toString()
            if (expr.isNotEmpty() && !isOperator(expr.last()) && expr.length < 40 && expr != "Ошибка" && expr != "too"&& expr != "Infinity") {
                operation.append(operator)
                scrollToEnd()
            }
        }

        fun appendNumber(number: String) {
            val expr = operation.text.toString()
            if (number == "." && expr.isNotEmpty() && expr.last().isDigit() && !expr.contains(".") && expr.length <40 && expr != "Ошибка" && expr != "too" && expr != "Infinity") {
                operation.append(number)
                scrollToEnd()
            } else if (number != "." && expr.length < 40) {
                operation.append(number)
                scrollToEnd()
            }
        }
        fun isFunction(token: String): Boolean {
            return token in listOf("sin(", "cos(", "tan(", "ctg(", "sqrt(")
        }
        fun appendOperator2(operator: String) {
            val expr = operation.text.toString()
            if (expr.isNotEmpty() && expr.length < 40 && expr != "Ошибка" && expr != "too" && expr != "Infinity") {
                if (!isOperator(expr.last()) || operator == "(" || operator == "e" || operator == "π" || isFunction(operator)) {
                    operation.append(if (isFunction(operator)) "$operator" else operator)
                    scrollToEnd()
                }
            }
        }
        findViewById<TextView>(R.id.b_leftb).setOnClickListener { appendOperator2("(") }
        findViewById<TextView>(R.id.b_rightb).setOnClickListener { appendOperator2(")") }
        findViewById<TextView>(R.id.b_power).setOnClickListener { appendOperator2("^2") }
        findViewById<TextView>(R.id.ac).setOnClickListener {
            operation.text = ""
            result.text = ""
        }
        findViewById<TextView>(R.id.back).setOnClickListener {
            val text = operation.text.toString()
            if (text.isNotEmpty()) operation.text = text.dropLast(1)
        }
        findViewById<TextView>(R.id.percent).setOnClickListener { appendOperator("%") }
        findViewById<TextView>(R.id.devide).setOnClickListener { appendOperator("/") }
        findViewById<TextView>(R.id.b7).setOnClickListener { appendNumber("7") }
        findViewById<TextView>(R.id.b8).setOnClickListener { appendNumber("8") }
        findViewById<TextView>(R.id.b9).setOnClickListener { appendNumber("9") }
        findViewById<TextView>(R.id.multiplication).setOnClickListener { appendOperator("*") }
        findViewById<TextView>(R.id.b4).setOnClickListener { appendNumber("4") }
        findViewById<TextView>(R.id.b5).setOnClickListener { appendNumber("5") }
        findViewById<TextView>(R.id.b6).setOnClickListener { appendNumber("6") }
        findViewById<TextView>(R.id.minus).setOnClickListener { appendOperator("-") }
        findViewById<TextView>(R.id.pi).setOnClickListener { appendOperator2("π") }
        findViewById<TextView>(R.id.b1).setOnClickListener { appendNumber("1") }
        findViewById<TextView>(R.id.b2).setOnClickListener { appendNumber("2") }
        findViewById<TextView>(R.id.b3).setOnClickListener { appendNumber("3") }
        findViewById<TextView>(R.id.plus).setOnClickListener { appendOperator("+") }
        findViewById<TextView>(R.id.e).setOnClickListener { appendOperator2("e") }
        findViewById<TextView>(R.id.tiple).setOnClickListener { appendOperator2("000") }
        findViewById<TextView>(R.id.dot).setOnClickListener { appendNumber(".") }
        findViewById<TextView>(R.id.zero).setOnClickListener { appendNumber("0") }
        findViewById<TextView>(R.id.b_sin).setOnClickListener { appendOperator2("sin(") }
        findViewById<TextView>(R.id.b_cos).setOnClickListener { appendOperator2("cos(") }
        findViewById<TextView>(R.id.b_sqrt).setOnClickListener { appendOperator2("sqrt(") }
        findViewById<TextView>(R.id.b_tan).setOnClickListener { appendOperator2("tan(") }
        findViewById<TextView>(R.id.b_ctg).setOnClickListener { appendOperator2("ctg(") }
        findViewById<TextView>(R.id.equal).setOnClickListener {
            val expr = operation.text.toString()

            val processedExpr = if (expr.endsWith("%")) {
                expr.dropLast(1) + "/100"
            } else {
                expr
            }

            if (processedExpr.contains("/0")) {
                result.text = "Ошибка: деление на 0"
            } else {
                val openBrackets = processedExpr.count { it == '(' }
                val closeBrackets = processedExpr.count { it == ')' }

                if (openBrackets != closeBrackets) {
                    result.text = "Ошибка: незакрытые скобки"
                } else {
                    val fixedExpr = processedExpr
                        .replace("([0-9)])(π|e)".toRegex(), "$1*$2")
                        .replace("([0-9])\\(".toRegex(), "$1*(")
                        .replace("\\)([0-9])".toRegex(), ")*$1")

                    val resultValue = evaluate(fixedExpr)

                    val resultString = if (!resultValue.isNaN()) {
                        val formatted = String.format("%.10f", resultValue).trimEnd('0').trimEnd('.')
                        if (formatted.length > 12) {
                            val parts = formatted.split('.')
                            val intPart = parts[0]
                            val decimalPart = if (parts.size > 1) parts[1] else ""

                            if (intPart.length >= 12) {
                                intPart.take(12)
                            } else {
                                val availableDecimals = 12 - intPart.length
                                if (availableDecimals > 0) {
                                    "$intPart.${decimalPart.take(availableDecimals)}"
                                } else {
                                    intPart
                                }
                            }
                        } else {
                            formatted
                        }
                    } else {
                        "Ошибка"
                    }

                    result.text = resultString
                    operation.text = resultString

                }
            }
        }



    }

    private fun evaluateExpression(expression: String): Double {
        var expr = expression
        expr = expr.replace("πe", "π*e")
        expr = expr.replace("eπ", "π*e")

        expr = expr.replace("π", Math.PI.toString())
        expr = expr.replace("e", Math.E.toString())

        return try {
            evaluatePostfix(infixToPostfix(expr))
        } catch (e: Exception) {
            Double.NaN
        }
    }

    private fun infixToPostfix(expression: String): List<String> {
        val output = mutableListOf<String>()
        val operators = Stack<Char>()
        val precedence = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2, '^' to 3)

        var i = 0
        while (i < expression.length) {
            val c = expression[i]
            when {
                c.isDigit() || c == '.' -> {
                    var num = ""
                    while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                        num += expression[i]
                        i++
                    }
                    output.add(num)
                    continue
                }
                c == '(' -> operators.push(c)
                c == ')' -> {
                    while (operators.isNotEmpty() && operators.peek() != '(') {
                        output.add(operators.pop().toString())
                    }
                    operators.pop()
                }
                c in precedence -> {
                    while (operators.isNotEmpty() && operators.peek() != '(' &&
                        precedence[operators.peek()]!! >= precedence[c]!!
                    ) {
                        output.add(operators.pop().toString())
                    }
                    operators.push(c)
                }
                c.isLetter() -> {  // Обрабатываем функции
                    var func = ""
                    while (i < expression.length && expression[i].isLetter()) {
                        func += expression[i]
                        i++
                    }
                    output.add(func)
                    continue
                }
            }
            i++
        }

        while (operators.isNotEmpty()) {
            output.add(operators.pop().toString())
        }
        return output
    }

    private fun evaluatePostfix(postfix: List<String>): Double {
        return evaluatePostfixRecursive(postfix, 0).first
    }

    private fun evaluatePostfixRecursive(postfix: List<String>, index: Int): Pair<Double, Int> {
        val stack = Stack<Double>()
        var i = index

        while (i < postfix.size) {
            val token = postfix[i]

            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                token in listOf("sin", "cos", "tan", "cot", "sqrt") -> {
                    val (result, nextIndex) = evaluatePostfixRecursive(postfix, i + 1)
                    val value = when (token) {
                        "sin" -> sin(result)
                        "cos" -> cos(result)
                        "tan" -> tan(result)
                        "cot" -> 1 / tan(result)
                        "sqrt" -> sqrt(result)
                        else -> Double.NaN
                    }
                    stack.push(value)
                    i = nextIndex
                }
                else -> {
                    if (stack.size >= 2) {
                        val b = stack.pop()
                        val a = stack.pop()
                        when (token) {
                            "+" -> stack.push(a + b)
                            "-" -> stack.push(a - b)
                            "*" -> stack.push(a * b)
                            "/" -> stack.push(a / b)
                            "%" -> stack.push(a % b)
                            "^" -> stack.push(a.pow(b))
                        }
                    }
                }
            }
            i++
        }

        return Pair(stack.pop(), i)
    }



}