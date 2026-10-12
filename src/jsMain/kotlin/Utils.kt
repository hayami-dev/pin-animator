import org.w3c.dom.CanvasRenderingContext2D
import kotlin.math.PI

data class Point(val x: Double, val y: Double)

// 波(-1~1)が最大の時の各パーツの移動量
data class PartMotion(
    val moveX: Double = 0.0, // 横
    val moveY: Double = 0.0, // 縦
    val rotateDeg: Double = 0.0, // 回転
    val baseRotateDeg: Double = 0.0, // 基準の傾き（揺れの中心）
)


fun Double.toRad() = this * PI / 180.0


// save,restoreの対になる処理の閉じ忘れを防ぐ
inline fun CanvasRenderingContext2D.withSavedState(block: () -> Unit) {
    save()
    try {
        block()
    } finally {
        restore()
    }
}