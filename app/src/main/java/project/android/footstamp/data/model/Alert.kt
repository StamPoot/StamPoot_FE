package project.android.footstamp.data.model

data class Alert(
    val title: Int,
    val message: Int,
    val buttonCount: ButtonCount,
    val errorMessage: String? = null,
    val onPressYes: () -> Unit = {},
    val onPressNo: () -> Unit = {},
)

enum class ButtonCount {
    ONE, TWO
}