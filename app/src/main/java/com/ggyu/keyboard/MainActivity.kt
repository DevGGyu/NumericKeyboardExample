package com.ggyu.keyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_numeric_key.view.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val df = DecimalFormat("#,###")

        rvNumericKeyboard.adapter = NumericAdapter { select: String ->
            try {
                if(txtCost.length() == 14) return@NumericAdapter
                txtCost.text = when (select) {
                    "DEL" -> {
                        val newCost = txtCost.text.toString().replace(",", "").dropLast(1)
                        if (newCost.isEmpty()) null
                        else df.format(newCost.toLong())
                    }
                    else -> {
                        val newCost = txtCost.text.toString().replace(",", "") + select
                        df.format(newCost.toLong())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    inner class NumericAdapter(val select: (String) -> Unit) :
        RecyclerView.Adapter<NumericAdapter.NumericViewHolder>() {

        private val numericKey =
            arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9") + arrayListOf("00", "0", "DEL")

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NumericViewHolder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.view_numeric_key, p0, false)
            return NumericViewHolder(view)
        }

        override fun getItemCount(): Int = numericKey.size

        override fun onBindViewHolder(p0: NumericViewHolder, p1: Int) {
            p0.bind(numericKey[p1]) { selectKey ->
                select(selectKey)
            }
        }

        inner class NumericViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(key: String, select: (String) -> Unit) {
                with(itemView) {
                    this.txtKeypad.text = key
                    this.btnKeypad.setOnClickListener { select(key) }
                }
            }
        }
    }
}