package com.example.barteryuk.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.barteryuk.R
import com.example.barteryuk.databinding.FragmentRegisterBinding

/**
 * RegisterFragment menangani proses pendaftaran akun pengguna baru.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        // Setup Spannable text for Login link
        val text = "Sudah punya akun? Masuk"
        val spannableString = SpannableString(text)
        
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark)
                ds.isUnderlineText = false
            }
        }

        val start = text.indexOf("Masuk")
        val end = start + "Masuk".length
        
        if (start != -1) {
            spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.tvToLogin.text = spannableString
        binding.tvToLogin.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (validateInput(name, email, password, confirmPassword)) {
                viewModel.register(name, email, password)
                Toast.makeText(requireContext(), "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String, confirm: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.tilName.error = "Nama tidak boleh kosong"
            isValid = false
        } else {
            binding.tilName.error = null
        }

        if (email.isEmpty()) {
            binding.tilEmail.error = "Email tidak boleh kosong"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password tidak boleh kosong"
            isValid = false
        } else if (password.length < 6) {
            binding.tilPassword.error = "Password minimal 6 karakter"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        if (confirm != password) {
            binding.tilConfirmPassword.error = "Password tidak cocok"
            isValid = false
        } else {
            binding.tilConfirmPassword.error = null
        }

        if (!binding.cbTerms.isChecked) {
            Toast.makeText(requireContext(), "Harap setujui Syarat dan Ketentuan", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
