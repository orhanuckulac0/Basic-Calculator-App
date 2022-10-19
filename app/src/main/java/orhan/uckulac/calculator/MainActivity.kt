package orhan.uckulac.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import orhan.uckulac.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tvInput: TextView? = null

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvInput = binding.InputNumber
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View){
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot)
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
    }

    fun onOperator(view: View){
        // does tvInput.text exists, then execute
        tvInput?.text?.let {
             if (lastNumeric && !isOperatorAdded(it.toString())){
                 tvInput?.append((view as Button).text)
                 lastNumeric = false
                 lastDot = false

            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View){
        if (lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                // subtraction
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split('-')

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                // addition
                } else if (tvValue.contains("+")){
                    val splitValue = tvValue.split('+')

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                // division
                } else if (tvValue.contains("/")){
                    val splitValue = tvValue.split('/')

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                // multiplication
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split('*')

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    // if calculation returns a float or double with .0, subtract .0 and return Int
    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if (value.contains(".0")){
            value = result.substring(0, result.length - 2)  // from start to last index -2
        }
        return value
    }

    // check if operator is selected
    // if number is below 0, (-2342.03) this can cause a problem because we are splitting the numbers by operation
    // so if that's the case, return false and change prefix on the way
    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/") ||
                    value.contains("*") ||
                    value.contains("+") ||
                    value.contains("-")

        }
    }
}