package com.example.calculator

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

enum class Operators {
	ADD,
	SUBTRACT,
	DIVIDE,
	MULTIPLY,
	NONE_SELECTED,
	COMPLETE
}

class MainActivity : AppCompatActivity() {

	var primary = ""
	var secondary = ""
	var mode: Operators = Operators.NONE_SELECTED
	val factDB = Facts
	var quack: MediaPlayer? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		quack = MediaPlayer.create(this, R.raw.quack)
	}

	fun onDigit(view: View) {
		quack?.start()
		val num = (view as Button).text
		when (mode) {
			Operators.NONE_SELECTED -> {
				primary += num
				displayResults(primary)
			}
			Operators.COMPLETE -> {
				primary = num.toString()
				displayResults(primary)
				mode = Operators.NONE_SELECTED
			}
			else -> {
				secondary += num
				displayResults(secondary)
			}
		}
	}

	fun onOperator(view: View) {
		when ((view as Button).text) {
			"/" -> {
				mode = Operators.DIVIDE
			}
			"*" -> {
				mode = Operators.MULTIPLY
			}
			"+" -> {
				mode = Operators.ADD
			}
			"-" -> {
				mode = Operators.SUBTRACT
			}
		}
	}

	fun onClear(view: View) {
		primary = ""
		secondary = ""
		mode = Operators.NONE_SELECTED
		displayResults(primary)
		fact.text = ""
		label.text = ""
	}

	fun onRun(view: View) {
		var results = 0.00F
		when (mode) {
			Operators.DIVIDE -> {
				results = primary.toFloat() / secondary.toFloat()
			}
			Operators.MULTIPLY -> {
				results = primary.toFloat() * secondary.toFloat()
			}
			Operators.ADD -> {
				results = primary.toFloat() + secondary.toFloat()
			}
			Operators.SUBTRACT -> {
				results = primary.toFloat() - secondary.toFloat()
			}
			else -> {
			}
		}
		mode = Operators.COMPLETE
		primary = results.toString()
		secondary = ""
		displayResults(primary)
		fact.text = factDB.getRandomFact()
		label.text = "but did you know ...?"
	}

	private fun displayResults(num: String) {
		results.text = num
	}
}