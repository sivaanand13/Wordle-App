package com.example.project1_wordle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.color
import com.github.jinatonic.confetti.CommonConfetti
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    var guessNumber = 1

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val parent = findViewById<LinearLayout>(R.id.parent)
        val guessButton = findViewById<Button>(R.id.guessButton)
        val correctWord = findViewById<TextView>(R.id.correctWord)
        val userGuess = findViewById<EditText>(R.id.userGuess)
        var word = FourLetterWordList.getRandomFourLetterWord()
        correctWord.visibility= View.INVISIBLE
        correctWord.text = word.uppercase()
        guessButton.setOnClickListener{
            val guessHeader : TextView
            val guessedWord: TextView
            val guessCheckHeader: TextView
            val guessCheckWordle: TextView

            val userGuessedWord = userGuess.getText().toString().uppercase()
            correctWord.text = word.uppercase()
            if (userGuessedWord.length != 4  && guessButton.text == "Guess!") {
                Toast.makeText(this, "Guess Must be 4 Characters!", Toast.LENGTH_SHORT).show()
            }
            else if (guessButton.text == "Guess!") {
                if (guessNumber == 1) {
                    guessHeader = findViewById(R.id.guessHeader1)
                    guessedWord = findViewById(R.id.guessedWord1)
                    guessCheckHeader = findViewById(R.id.guessResultHeader1)
                    guessCheckWordle = findViewById(R.id.guessedWordResult1)

                } else if (guessNumber == 2) {
                    guessHeader = findViewById(R.id.guessHeader2)
                    guessedWord = findViewById(R.id.guessedWord2)
                    guessCheckHeader = findViewById(R.id.guessResultHeader2)
                    guessCheckWordle = findViewById(R.id.guessedWordResult2)

                } else {
                    guessHeader = findViewById(R.id.guessHeader3)
                    guessedWord = findViewById(R.id.guessedWord3)
                    guessCheckHeader = findViewById(R.id.guessResultHeader3)
                    guessCheckWordle = findViewById(R.id.guessedWordResult3)
                    correctWord.visibility= View.VISIBLE
                }
                val guessResult = checkGuess(word, userGuessedWord)
                if (userGuessedWord == word.uppercase()) {
                    CommonConfetti.rainingConfetti(this.currentFocus?.rootView as ViewGroup,
                        intArrayOf(Color.RED, Color.GREEN)
                    ).stream(1000)
                    guessNumber=4
                }
                guessHeader.text = "Guess #" + guessNumber
                guessedWord.text = userGuessedWord
                guessCheckHeader.text = "Guess #" + guessNumber + " Check"
                guessCheckWordle.text = guessResult
                guessNumber++

                if (guessNumber > 3) {
                    guessButton.text = "RESTART"
                }

            } else {

                findViewById<TextView?>(R.id.guessHeader1).text=""
                findViewById<TextView?>(R.id.guessedWord1).text=""
                findViewById<TextView?>(R.id.guessResultHeader1).text=""
                findViewById<TextView?>(R.id.guessedWordResult1).text=""
                findViewById<TextView?>(R.id.guessHeader2).text=""
                findViewById<TextView?>(R.id.guessedWord2).text=""
                findViewById<TextView?>(R.id.guessResultHeader2).text=""
                findViewById<TextView?>(R.id.guessedWordResult2).text=""
                findViewById<TextView?>(R.id.guessHeader3).text=""
                findViewById<TextView?>(R.id.guessedWord3).text=""
                findViewById<TextView?>(R.id.guessResultHeader3).text=""
                findViewById<TextView?>(R.id.guessedWordResult3).text=""
                findViewById<TextView?>(R.id.guessedWordResult3).text=""
                word = FourLetterWordList.getRandomFourLetterWord()
                correctWord.text = word.uppercase()
                correctWord.visibility= View.INVISIBLE
                guessNumber = 1
                guessButton.text = "Guess!"
            }
            userGuess.text.clear()
            val thisView = this.currentFocus
            val inputManger = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManger.hideSoftInputFromWindow(thisView?.windowToken, 0)
        }

    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *u  
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(wordToGuess: String, guess: String) : Spannable {
        var result = ""
        val coloredResult = SpannableStringBuilder()
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
                coloredResult.color(Color.GREEN) {append(guess[i])}
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
                coloredResult.color(Color.RED) {append(guess[i])}
            }
            else {
                result += "X"
                coloredResult.append(guess[i])
            }
        }
        return coloredResult
    }
}