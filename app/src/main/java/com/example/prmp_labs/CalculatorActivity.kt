package com.example.prmp_labs

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.prmp_labs.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {
    private val binding by viewBinding<ActivityCalculatorBinding>(CreateMethod.INFLATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val operation: TextView = findViewById(R.id.operation)
        val result: TextView = findViewById(R.id.result)
        findViewById<TextView>(R.id.b_sqrt).setOnClickListener { operation.append("sqrt(") }
        findViewById<TextView>(R.id.b_log2).setOnClickListener { operation.append("log2(") }
        findViewById<TextView>(R.id.b_ln).setOnClickListener { operation.append("ln(") }
        findViewById<TextView>(R.id.b_leftb).setOnClickListener { operation.append("(") }
        findViewById<TextView>(R.id.b_rightb).setOnClickListener { operation.append(")") }
        findViewById<TextView>(R.id.b_power).setOnClickListener { operation.append("^2") }
        findViewById<TextView>(R.id.ac).setOnClickListener {
            operation.text = ""
            result.text = ""
        }
        findViewById<TextView>(R.id.back).setOnClickListener {
            val text = operation.text.toString()
            if (text.isNotEmpty()) operation.text = text.dropLast(1)
        }
        findViewById<TextView>(R.id.percent).setOnClickListener { operation.append("%") }
        findViewById<TextView>(R.id.devide).setOnClickListener { operation.append("/") }
        findViewById<TextView>(R.id.sin).setOnClickListener { operation.append("sin(") }
        findViewById<TextView>(R.id.b7).setOnClickListener { operation.append("7") }
        findViewById<TextView>(R.id.b8).setOnClickListener { operation.append("8") }
        findViewById<TextView>(R.id.b9).setOnClickListener { operation.append("9") }
        findViewById<TextView>(R.id.multiplication).setOnClickListener { operation.append("*") }
        findViewById<TextView>(R.id.cos).setOnClickListener { operation.append("cos(") }
        findViewById<TextView>(R.id.b4).setOnClickListener { operation.append("4") }
        findViewById<TextView>(R.id.b5).setOnClickListener { operation.append("5") }
        findViewById<TextView>(R.id.b6).setOnClickListener { operation.append("6") }
        findViewById<TextView>(R.id.minus).setOnClickListener { operation.append("-") }
        findViewById<TextView>(R.id.pi).setOnClickListener { operation.append("π") }
        findViewById<TextView>(R.id.b1).setOnClickListener { operation.append("1") }
        findViewById<TextView>(R.id.b2).setOnClickListener { operation.append("2") }
        findViewById<TextView>(R.id.b3).setOnClickListener { operation.append("3") }
        findViewById<TextView>(R.id.plus).setOnClickListener { operation.append("+") }
        findViewById<TextView>(R.id.e).setOnClickListener { operation.append("e") }
        findViewById<TextView>(R.id.tiple).setOnClickListener { operation.append("000") }
        findViewById<TextView>(R.id.dot).setOnClickListener { operation.append(".") }
        findViewById<TextView>(R.id.zero).setOnClickListener { operation.append("0") }
        findViewById<TextView>(R.id.equal).setOnClickListener {



        }
    }
}
