import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Path2D


private val HEAD_MOTION = PartMotion(
    moveX = -4.0,
    moveY = BREATH_AMPLITUDE,
    rotateDeg = 3.0,
    baseRotateDeg = -4.0
)
private val HEAD_PIVOT = Point(257.0, 143.0)

fun drawCharacter(ctx: CanvasRenderingContext2D, breath: Double) {

    // 右腕(向かって左)
    drawRotatedRect(ctx, x = 183.0, y = 172.0, w = 17.0, h = 112.0, angleDeg = 25.0, pivotX = 191.5, pivotY = 228.0)

    // 胴体
    drawBody(ctx, breath)

    // 左腕(向かって右)
    drawRotatedRect(
        ctx, x = 316.13239, y = 173.0, w = 17.0, h = 112.0, angleDeg = -25.0, pivotX = 324.632, pivotY = 229.0
    )

    // 左脚(向かって右)
    drawRect(ctx, x = 273.13239, y = 349.0, w = 17.0, h = 112.0)

    // 右脚(向かって左)
    drawRect(ctx, x = 222.42186, y = 349.0, w = 17.0, h = 112.0)

    // 頭部
    drawHead(ctx, breath)
}

// 胴体の描画
fun drawBody(ctx: CanvasRenderingContext2D, breath: Double) {
    val breathOffsetY = breath * BREATH_AMPLITUDE
    val breathOffsetX = breathOffsetY * 0.3

    val topLeft = Point(207.0 - breathOffsetX, 159.0 + breathOffsetY)
    val topRight = Point(307.0 - breathOffsetX, 159.0 + breathOffsetY)     // 207 + 100(幅)
    val bottomRight = Point(307.0, 334.0)   // 159 + 175(高さ)
    val bottomLeft = Point(207.0, 334.0)

    drawQuad(ctx, topLeft, topRight, bottomRight, bottomLeft)
}

// 頭部の描画
fun drawHead(ctx: CanvasRenderingContext2D, breath: Double) {
    val m = HEAD_MOTION
// 頭部とその中身パーツを同時に胴に合わせて移動させる
    ctx.withSavedState {
        ctx.translate(m.moveX * breath, m.moveY * breath)   // 移動

        ctx.translate(HEAD_PIVOT.x, HEAD_PIVOT.y)           // 支点へ移動
        ctx.rotate((m.baseRotateDeg + m.rotateDeg * breath).toRad())       // 回転
        ctx.translate(-HEAD_PIVOT.x, -HEAD_PIVOT.y)         // 戻す

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

        ctx.withSavedState {
            ctx.translate(242.349, 112.002)
            ctx.rotate(90.0.toRad())
            ctx.translate(-242.349, -112.002)
            ctx.fill(mouthPath)
            ctx.stroke(mouthPath)
        }
    }
}
