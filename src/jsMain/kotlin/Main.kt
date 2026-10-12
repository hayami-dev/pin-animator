import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.PI
import kotlin.math.sin

private const val CANVAS_HEIGHT = 500.0
private const val BREATH_PERIOD_SEC = 3       // 1呼吸の周期(秒)
const val BREATH_AMPLITUDE = CANVAS_HEIGHT * 0.01  // 振幅：画像高さの1%

lateinit var ctx: CanvasRenderingContext2D


fun main() {
    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    ctx.strokeStyle = "#000"
    ctx.fillStyle = "#fff"

    window.requestAnimationFrame { firstTimestamp -> animate(firstTimestamp) }
}

fun animate(timestampMs: Double) {
    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble()) // 前フレームを消す

    val timeSec = timestampMs / 1000.0
    val breath = sin(2 * PI * timeSec / BREATH_PERIOD_SEC)

    drawCharacter(ctx, breath) // キャラクター部分の描画

    window.requestAnimationFrame { next -> animate(next) }
}
