package com.example.footstamp.data.model

data class Alert(
    val title: String,
    val message: String,
    val buttonCount: ButtonCount,
    val onPressYes: () -> Unit = {},
    val onPressNo: () -> Unit = {},
)

enum class ButtonCount {
    ONE, TWO
}