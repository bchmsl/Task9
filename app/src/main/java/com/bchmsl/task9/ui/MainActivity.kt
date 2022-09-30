package com.bchmsl.task9.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bchmsl.task9.adapters.ButtonsAdapter
import com.bchmsl.task9.adapters.DotsAdapter
import com.bchmsl.task9.databinding.ActivityMainBinding
import com.bchmsl.task9.model.buttons.ButtonModel
import com.bchmsl.task9.model.dots.DotModel

class MainActivity : AppCompatActivity() {
    companion object {
        private const val CURRENT_PASSWORD = "0934"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val dotsAdapter by lazy { DotsAdapter() }
    private val buttonsAdapter by lazy { ButtonsAdapter() }

    private var passcodeDigits = mutableListOf<Int?>(null, null, null, null)
    private var dotsState = mutableListOf(DotModel(0), DotModel(1), DotModel(2), DotModel(3))
    private var digitsEntered = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        setupRecyclers()
    }

    private fun setupRecyclers() {
        setupDotsRecycler()
        setupButtonsRecycler()
    }

    private fun setupDotsRecycler() {
        binding.rvDots.adapter = dotsAdapter
        dotsAdapter.submitList(dotsState)
    }

    private fun setupButtonsRecycler() {
        binding.rvButtons.adapter = buttonsAdapter
        buttonsAdapter.submitList(ButtonModel.buttonsList)
        listeners()
    }

    private fun listeners() {
        buttonsAdapter.itemClick = {
            when (it.isNumeric) {
                true -> handleNumber(it.value)
                false -> {
                    if (it.isFingerprint) {
                        handleFingerprint()
                    } else if (it.isBackspace) {
                        handleBackspace()
                    }
                }
            }
            Log.wtf("TAG", passcodeDigits.toString())
            Log.wtf("TAG", dotsState.toString())
        }
    }

    private fun handleNumber(value: Int) {
        if (digitsEntered < 4) {
            val lastDigitPosition = digitsEntered
            passcodeDigits[lastDigitPosition] = value
            dotsState[lastDigitPosition] = DotModel(lastDigitPosition, true)
            dotsAdapter.submitList(dotsState.toList())
            digitsEntered++
        }
        if (digitsEntered == 4) {
            val passcode = passcodeDigits.joinToString("")
            Log.wtf("TAG", passcode)
            if (passcode == CURRENT_PASSWORD) {
                handleSuccess()
            } else {
                handleError()
            }
        }
    }

    private fun handleError() {
        Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show()
        clearPassword()
    }

    private fun handleSuccess() {
        Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
        clearPassword()
    }

    private fun handleFingerprint() {
        Toast.makeText(this, "Fingerprint clicked", Toast.LENGTH_SHORT).show()
    }

    private fun handleBackspace() {
        if (digitsEntered in 1..4) {
            val lastDigitPosition = digitsEntered - 1
            passcodeDigits[lastDigitPosition] = null
            dotsState[lastDigitPosition] = DotModel(lastDigitPosition, false)
            dotsAdapter.submitList(dotsState.toList())
            digitsEntered--
        }
    }
    private fun clearPassword(){
        passcodeDigits = mutableListOf(null, null, null, null)
        dotsState = mutableListOf(DotModel(0), DotModel(1), DotModel(2), DotModel(3))
        dotsAdapter.submitList(dotsState.toList())
        digitsEntered = 0
    }
}