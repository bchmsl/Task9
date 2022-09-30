package com.bchmsl.task9.model.buttons

import com.bchmsl.task9.R

data class ButtonModel(
    val value: Int,
    val isNumeric: Boolean = true,
    val isFingerprint: Boolean = false,
    val isBackspace: Boolean = false
){
    companion object{
        val buttonsList = listOf(
            ButtonModel(1),
            ButtonModel(2),
            ButtonModel(3),
            ButtonModel(4),
            ButtonModel(5),
            ButtonModel(6),
            ButtonModel(7),
            ButtonModel(8),
            ButtonModel(9),
            ButtonModel(
                R.drawable.ic_fingerprint,
                isNumeric = false,
                isFingerprint = true
            ),
            ButtonModel(0),
            ButtonModel(
                R.drawable.ic_backspace,
                isNumeric = false,
                isBackspace = true
            ),

        )
    }
}
