import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Path2D
import kotlin.math.PI

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

// 自由変形可能な胴体の描画に必要なメソッド郡
// 三角形の描画方法の定義
fun drawTriangle(ctx: CanvasRenderingContext2D, a: Point, b: Point, c: Point) {
    val path = Path2D()
    path.moveTo(a.x, a.y)
    path.lineTo(b.x, b.y)
    path.lineTo(c.x, c.y)
    path.closePath()
    ctx.fill(path)
    ctx.stroke(path)
}

// 三角形2枚の描画方法の定義
fun drawQuad(ctx: CanvasRenderingContext2D, topLeft: Point, topRight: Point, bottomRight: Point, bottomLeft: Point) {
    // 対角線（左上→右下）で2枚に分割
    drawTriangle(ctx, topLeft, topRight, bottomRight)
    drawTriangle(ctx, topLeft, bottomRight, bottomLeft)
}