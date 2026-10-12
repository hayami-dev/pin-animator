data class MotionPreset(
    val name: String,
    val periodSec: Double,
    val body: PartMotion,
    val head: PartMotion,
    // TODO: 腕の追加
)

val PRESET_CALM = MotionPreset(
    name = "デフォルト",
    periodSec = 3.0,
    body = PartMotion(moveY = 5.0, moveX = -1.5),
    head = PartMotion(moveX = -4.0, moveY = 5.0, rotateDeg = 3.0, baseRotateDeg = -4.0),
)

val ALL_PRESETS = listOf(PRESET_CALM)