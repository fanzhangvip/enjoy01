package com.zero.navigation1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.zero.navigation1.databinding.ConstraintKeyframe1Binding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ConstraintKeyframe1Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val constraintSet = ConstraintSet()
            constraintSet.load(MainActivity@this,R.layout.constraint_keyframe_2)
            TransitionManager.beginDelayedTransition(binding.root as ConstraintLayout)
            constraintSet.applyTo(binding.root as ConstraintLayout)
        }

    }
}
