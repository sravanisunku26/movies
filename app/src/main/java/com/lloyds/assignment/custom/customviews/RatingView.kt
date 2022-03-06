package com.lloyds.assignment.custom.customviews

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator

class RatingView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private var mViewWidth = 0
    private var mViewHeight = 0
    private val mStartAngle = -90f // Always start from top (default is: "3 o'clock on a watch.")
    private var mSweepAngle = 0f // How long to sweep from mStartAngle
    private val mMaxSweepAngle = 360f // Max degrees to sweep = full circle
    private var mStrokeWidth = 20f // Width of outline
    private val mAnimationDuration = 400 // Animation duration for progress change
    private val mMaxProgress = 100 // Max progress to use
    private var mDrawText = true // Set to true if progress text should be drawn
    private var mRoundedCorners =
        true // Set to true if rounded corners should be applied to outline ends
    private var mProgressColor: Int = Color.BLACK // Outline color
    private var mTextColor: Int = Color.BLACK // Progress text color
    private val mPaint // Allocate paint outside onDraw to avoid unnecessary object creation
            : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initMeasurments()
        drawOutlineArc(canvas)
        if (mDrawText) {
            drawText(canvas)
        }
    }

    private fun initMeasurments() {
        mViewWidth = width
        mViewHeight = height
    }

    private fun drawOutlineArc(canvas: Canvas) {
        val diameter = Math.min(mViewWidth, mViewHeight)
        val pad = mStrokeWidth / 2.0.toFloat()
        val outerOval = RectF(pad, pad, diameter - pad, diameter - pad)
        mPaint.color = mProgressColor
        mPaint.strokeWidth = mStrokeWidth
        mPaint.isAntiAlias = true
        mPaint.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(outerOval, mStartAngle, mSweepAngle, false, mPaint)
    }

    private fun drawText(canvas: Canvas) {
        mPaint.textSize = Math.min(mViewWidth, mViewHeight) / 5f
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.strokeWidth = 0f
        mPaint.color = mTextColor

        // Center text
        val xPos: Float = canvas.width / 2f
        val yPos = (canvas.height / 2 - (mPaint.descent() + mPaint.ascent()) / 2)
        canvas.drawText(
            calcProgressFromSweepAngle(mSweepAngle).toString() + "%",
            xPos,
            yPos,
            mPaint
        )
    }

    private fun calcSweepAngleFromProgress(progress: Int): Float {
        return mMaxSweepAngle / mMaxProgress * progress
    }

    private fun calcProgressFromSweepAngle(sweepAngle: Float): Int {
        return (sweepAngle * mMaxProgress / mMaxSweepAngle).toInt()
    }

    /**
     * Set progress of the circular progress bar.
     * @param progress progress between 0 and 100.
     */
    fun setProgress(progress: Int) {
        val animator = ValueAnimator.ofFloat(mSweepAngle, calcSweepAngleFromProgress(progress))
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDuration.toLong()
        animator.addUpdateListener { valueAnimator ->
            mSweepAngle = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    fun setProgressColor(color: Int) {
        mProgressColor = color
        invalidate()
    }

    fun setTextColor(color: Int) {
        mTextColor = color
        invalidate()
    }

}
