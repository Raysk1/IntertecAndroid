package com.raysk.intertec.tutorial


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.gigamole.quatrograde.QuatroGradeView
import com.google.android.material.button.MaterialButton
import com.raysk.intertec.LoginActivity
import com.raysk.intertec.R
import com.raysk.intertec.util.Color.Companion.textColor
import com.raysk.intertec.util.background.GradientAnimBackground
import com.raysk.intertec.util.preferences.Preferences


class OnBoardingActivity : AppCompatActivity() {
    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var layoutOnboardingIndicator: LinearLayout
    private lateinit var buttonOnboardingAction: MaterialButton
    private lateinit var btnSaltar: MaterialButton
    private var tutorialComplete = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        layoutOnboardingIndicator = findViewById(R.id.layoutOnboardingIndicators)
        buttonOnboardingAction = findViewById(R.id.buttonOnBoardingAction)
        btnSaltar = findViewById(R.id.btnSkip)
        val quatroGradeView = findViewById<QuatroGradeView>(R.id.gradeView)
        tutorialComplete = Preferences(this).tutorialComplete

        GradientAnimBackground(quatroGradeView).setupUi()
        setOnboardingItems()

        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingAdapter


        setOnboadingIndicator()
        setCurrentOnboardingIndicators(0)

        onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnboardingIndicators(position)

            }
        })

        buttonOnboardingAction.setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < onboardingAdapter.itemCount) {
                onboardingViewPager.currentItem = onboardingViewPager.currentItem + 1
            } else {
                finalizar()
            }
        }

        btnSaltar.setOnClickListener {
            finalizar()
        }
    }

    private fun finalizar(){
        if (!tutorialComplete){
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        Preferences(this).tutorialComplete = true
        finish()
    }


    private fun setOnboadingIndicator() {
        val indicators = arrayOfNulls<ImageView>(onboardingAdapter.getItemCount())
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.onboarding_indicator_inactive
                )
            )
            indicators[i]!!.layoutParams = layoutParams
            layoutOnboardingIndicator.addView(indicators[i])
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentOnboardingIndicators(index: Int) {
        val childCount = layoutOnboardingIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = layoutOnboardingIndicator.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.onboarding_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.onboarding_indicator_inactive
                    )
                )
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.text = "Iniciar"
        } else {
            buttonOnboardingAction.text = "Siguiente"
        }
    }

    private fun setOnboardingItems() {
        val onBoardingItems: MutableList<OnBoardingItem> = ArrayList()
        val i1 = OnBoardingItem(
            R.drawable.logo, "Intertec App Movil", "Ahora al alcance de un solo click"
        )
        val i2 = OnBoardingItem(
            R.drawable.ic_baseline_account_circle_24,
            "Datos del Alumno",
            "La información que necesitas, cuando la necesitas: al alcance de tus dedos."
        )
        val i3 = OnBoardingItem(
            R.drawable.ic_baseline_access_time_24,
            "Horario",
            "Visualización que inspira: haz que tu horario sea visualmente atractivo y fácil de entender."
        )
        val i3_1 = OnBoardingItem(
            R.drawable.horarioejemplo,
            "Mantente al tanto de tus clases",
            "Destacando asignaturas, horarios y ubicaciones de aulas de manera prominente."
        )
        val i3_2 = OnBoardingItem(
            R.drawable.detalleshorarioejemplo,
            "Atento a los detalles",
            "Pulsa sobre una materia para ver sus detalles."
        )

        val i4 = OnBoardingItem(
            R.drawable.ic_baseline_school_24,
            "Notas",
            "Una forma sencilla y efectiva de seguir tu progreso académico."
        )
        val i4_1 = OnBoardingItem(
            R.drawable.calificacionesejemplo,
            "Diseño intuitivo y amigable",
            "Calificaciones con diseño intuitivo y amigable para una comprensión fácil."
        )

        val descripcion = "Colores que indican los estados de las materias:\n" +
                textColor("Verde: Cursado", R.color.INNcolor, this) + "<br>" +
                textColor("Naranja: Repite", R.color.IIALcolor, this) + "<br>" +
                textColor("Rojo: Reprobado", R.color.IGEcolor, this) + "<br>" +
                textColor("Azul: En Curso", R.color.ISCcolor, this) + "<br>" +
                textColor("Gris: Por Cursar", R.color.IIAScolor, this) + "<br>"

        val i4_2 = OnBoardingItem(
            R.drawable.calificacioneestadossejemplo,
            "Estados de colores",
            descripcion
        )
        val i4_3 = OnBoardingItem(
            R.drawable.detallescalificacionesejemplo,
            "Una vista mas detallada",
            "Accede a detalles precisos al tocar las calificaciones."
        )

        val i5 = OnBoardingItem(
            R.drawable.ic_baseline_attach_money_24,
            "Servicios",
            "Crea y descarga fácilmente tus recibos de pago escolares."
        )
        val i5_1 = OnBoardingItem(
        R.drawable.servicioejemplo,
            "Gestion de recibos",
            "Administra tus recibos de forma rápida y sencilla."
        )
        val i5_2 = OnBoardingItem(
            R.drawable.catalogoservicioejemplo,
            "Agrega nuevos recibos",
            "Añade nuevos recibos de forma aún más sencilla."
        )


        onBoardingItems.add(i1)
        onBoardingItems.add(i2)
        onBoardingItems.add(i3)
        onBoardingItems.add(i3_1)
        onBoardingItems.add(i3_2)
        onBoardingItems.add(i4)
        onBoardingItems.add(i4_1)
        onBoardingItems.add(i4_2)
        onBoardingItems.add(i4_3)
        onBoardingItems.add(i5)
        onBoardingItems.add(i5_1)
        onBoardingItems.add(i5_2)
        onboardingAdapter = OnboardingAdapter(onBoardingItems)
    }


}