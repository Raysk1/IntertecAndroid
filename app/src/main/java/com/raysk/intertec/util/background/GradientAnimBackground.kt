package com.raysk.intertec.util.background


import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.model.IntegerHSLColor
import com.gigamole.quatrograde.GradeModel
import com.gigamole.quatrograde.QuatroGradeView
import com.raysk.intertec.util.Color.Companion.randomColor
import kotlin.random.Random

class GradientAnimBackground(private val animationQuatroGradeView: QuatroGradeView) {
    var color = randomColor(true)

    private val topGrades = listOf(
        GradeModel(getRandomMidColor(color), 0.0F),
        GradeModel(getRandomMidColor(color), 0.33F),
        GradeModel(getRandomMidColor(color), 1.0F)
    )
    private val bottomGrades = listOf(
        GradeModel(getRandomMidColor(color), 0.0F),
        GradeModel(getRandomMidColor(color), 0.33F),
        GradeModel(getRandomMidColor(color), 1.0F)
    )

    private val argbEvaluator = ArgbEvaluator()
    private val animatorTop = ValueAnimator()
    private val animatorBottom = ValueAnimator()


    fun setupUi() {


        animationQuatroGradeView.setGrades(topGrades, bottomGrades)

        setupAnimator(animatorTop, animationQuatroGradeView.getTopGrades()[0])
        setupAnimator(
            animatorTop,
            animationQuatroGradeView.getTopGrades()[1],
            forMidColorGradeModel = animationQuatroGradeView.getTopGrades()[0]
        )
        setupAnimator(animatorTop, animationQuatroGradeView.getTopGrades()[2])
        setupAnimator(animatorBottom, animationQuatroGradeView.getBottomGrades()[0])
        setupAnimator(
            animatorBottom,
            animationQuatroGradeView.getBottomGrades()[1],
            forMidColorGradeModel = animationQuatroGradeView.getBottomGrades()[2]
        )
        setupAnimator(animatorBottom, animationQuatroGradeView.getBottomGrades()[2])

        animatorTop.addUpdateListener {
            animationQuatroGradeView.refresh()
        }
    }

    private fun setupAnimator(
        animator: ValueAnimator,
        gradeModel: GradeModel,
        forMidColorGradeModel: GradeModel? = null,
    ) {
        randomizeAnimator(forMidColorGradeModel, animator, gradeModel)

        animator.interpolator = LinearInterpolator()
        animator.setEvaluator(argbEvaluator)
        animator.addListener(
            object : Animator.AnimatorListener {

                override fun onAnimationStart(p0: Animator) = Unit

                override fun onAnimationEnd(p0: Animator) {
                    randomizeAnimator(forMidColorGradeModel, animator, gradeModel)
                    animator.start()
                }

                override fun onAnimationCancel(p0: Animator) = Unit

                override fun onAnimationRepeat(p0: Animator) = Unit


            }
        )
        animator.addUpdateListener {
            gradeModel.color = it.animatedValue as Int
        }

        animator.start()
    }

    internal fun randomizeAnimator(
        forMidColorGradeModel: GradeModel?,
        animator: ValueAnimator,
        gradeModel: GradeModel,
    ) {
        if (forMidColorGradeModel != null) {
            animator.setIntValues(gradeModel.color, getRandomMidColor(forMidColorGradeModel.color))
        } else {
            animator.setIntValues(gradeModel.color, randomColor(true))
        }

        animator.duration = getRandomDuration()
    }

    private fun getRandomMidColor(fromColor: Int) = IntegerHSLColor().apply {
        setFromColorInt(fromColor)
        intH += Random.nextInt(-36, 36)
    }.toColorInt()

    private fun getRandomDuration() = (3000 + Random.nextInt(3000)).toLong()
}