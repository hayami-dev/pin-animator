import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Path2D
import kotlin.math.PI
import kotlin.math.sin

private const val CANVAS_HEIGHT = 500.0
private const val BREATH_PERIOD_SEC = 2.5       // 1呼吸の周期(秒)
private const val BREATH_AMPLITUDE = CANVAS_HEIGHT * 0.025  // 振幅：画像高さの2.5%

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
    val breathOffsetY = sin(2 * PI * timeSec / BREATH_PERIOD_SEC) * BREATH_AMPLITUDE

    ctx.save()
    ctx.translate(0.0, breathOffsetY) // 胴体のピンを基準に...というより、キャラ全体をY方向にだけずらす
    // キャラクター部分の描画
    drawCharacter(ctx)
    ctx.restore()

    window.requestAnimationFrame { next -> animate(next) }
}

private fun Double.toRad() = this * PI / 180.0

fun drawCharacter(ctx: CanvasRenderingContext2D) {

    // 右腕(向かって左)
    drawRotatedRect(ctx, x = 183.0, y = 172.0, w = 17.0, h = 112.0, angleDeg = 25.0, pivotX = 191.5, pivotY = 228.0)

    // 胴体
    drawRect(ctx, x = 207.0, y = 159.0, w = 100.0, h = 175.0)

    // 左腕(向かって右)
    drawRotatedRect(
        ctx, x = 316.13239, y = 173.0, w = 17.0, h = 112.0, angleDeg = -25.0, pivotX = 324.632, pivotY = 229.0
    )

    // 左脚(向かって右)
    drawRect(ctx, x = 273.13239, y = 349.0, w = 17.0, h = 112.0)

    // 左脚(向かって左)
    drawRect(ctx, x = 222.42186, y = 349.0, w = 17.0, h = 112.0)

    // 頭
    drawEllipse(ctx, cx = 257.0, cy = 90.0, rx = 53.0, ry = 53.0)

    // 右目(向かって左)
    drawEllipse(ctx, cx = 219.77887, cy = 86.66667, rx = 12.0, ry = 12.0)

    // 左目(向かって右)
    drawEllipse(ctx, cx = 263.78181, cy = 86.66667, rx = 12.0, ry = 12.0)

    // 口（曲線を含むためPath2Dで再現）
    val mouthPath = Path2D(
        "m237.3495,100.89114l5,0l0,0c2.76142,0 5,4.97461 5,11.11111" + "c0,6.1365 -2.23857,11.11111 -5,11.11111l-5,0l0,-22.22223z"
    )
    ctx.save()
    ctx.translate(242.349, 112.002)
    ctx.rotate(90.0.toRad())
    ctx.translate(-242.349, -112.002)
    ctx.fill(mouthPath)
    ctx.stroke(mouthPath)
    ctx.restore()
}

fun drawEllipse(ctx: CanvasRenderingContext2D, cx: Double, cy: Double, rx: Double, ry: Double) {
    ctx.beginPath()
    ctx.ellipse(cx, cy, rx, ry, 0.0, 0.0, 2 * PI)
    ctx.fill()
    ctx.stroke()
}


fun drawRect(ctx: CanvasRenderingContext2D, x: Double, y: Double, w: Double, h: Double) {
    ctx.beginPath()
    ctx.rect(x, y, w, h)
    ctx.fill()
    ctx.stroke()
}

fun drawRotatedRect(
    ctx: CanvasRenderingContext2D,
    x: Double,
    y: Double,
    w: Double,
    h: Double,
    angleDeg: Double,
    pivotX: Double,
    pivotY: Double
) {
    ctx.save()
    ctx.translate(pivotX, pivotY)
    ctx.rotate(angleDeg.toRad())
    ctx.translate(-pivotX, -pivotY)
    drawRect(ctx, x, y, w, h)
    ctx.restore()
}