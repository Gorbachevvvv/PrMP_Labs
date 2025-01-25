package com.example.prmp_labs;

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.prmp_labs.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by viewBinding<ActivityLoginBinding>(CreateMethod.INFLATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.returntext.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)  // Не забывайте запускать активность
        }

        binding.loginButton.setOnClickListener {
            if (validateInput()) {
                val intent = Intent(this, WelcomeActivity::class.java) // Переход на другую активность после успешного логина
                startActivity(intent)
            }
        }
    }

    private fun validateInput(): Boolean {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()  // Используйте text.toString() для получения текста

        if (email.isEmpty()) {
            binding.emailEditText.error = "Поле должно быть заполнено"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditText.error = "Введите корректный email"
            return false
        }

        if (password.isEmpty()) {
            binding.passwordEditText.error = "Поле должно быть заполнено"
            return false
        }

        // Можно добавить дополнительные проверки для пароля, если требуется

        return true
    }
}
