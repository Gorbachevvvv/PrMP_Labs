package com.example.prmp_labs

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.prmp_labs.databinding.ActivitySignUpBinding

class SignUpActivity:AppCompatActivity() {
    private val binding by viewBinding<ActivitySignUpBinding>(CreateMethod.INFLATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.regButton.setOnClickListener {
            if (validateInput()) {
                val intent = Intent(this, CalculatorActivity::class.java)
                startActivity(intent)
        }
    }
        binding.textView2.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


    }

    private fun validateInput(): Boolean {
        val email = binding.emailEditText.text.toString()
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditText.error = "Введите корректный email"
            return false
        }

        if (firstName.length !in 3..255) {
            binding.firstNameEditText.error = "Имя должно содержать от 3 до 255 символов"
            return false
        }
        if (lastName.length !in 3..255) {
            binding.firstNameEditText.error = "Имя должно содержать от 3 до 255 символов"
            return false
        }
        val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{6,50}$")
        if (!password.matches(passwordRegex)) {
            binding.passwordEditText.error = "Пароль должен содержать от 6 до 50 символов, " +
                    "хотя бы одну большую букву, маленькую букву, цифру и спецсимвол"
            return false
        }
        return true
    }
}