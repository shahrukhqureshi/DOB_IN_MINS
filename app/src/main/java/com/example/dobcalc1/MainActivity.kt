package com.example.dobcalc1

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    private var tvAgeInHours : TextView? = null
    private var tvAgeInDays : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        clickDatePicker()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun clickDatePicker() {
        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate =  findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes =  findViewById(R.id.tvAgeInMinutes)
        tvAgeInHours =  findViewById(R.id.tvAgeInHours)
        tvAgeInDays =  findViewById(R.id.tvAgeInDays)
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)
        btnDatePicker.setOnClickListener{
        val dpd = DatePickerDialog(this,
            {_, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(
                    this, "year: $selectedYear Month: ${selectedMonth+1} Day: $selectedDayOfMonth",
                    Toast.LENGTH_LONG).show()
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let{
                val selectedDateInMinutes = theDate.time/60000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let{
                val currentDateInMinutes = currentDate.time/60000
                val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                val differenceInHours = differenceInMinutes/60
                val differenceInDays = differenceInHours/24
                tvAgeInMinutes?.text = differenceInMinutes.toString()
                tvAgeInHours?.text = differenceInHours.toString()
                tvAgeInDays?.text = differenceInDays.toString()
                 }
                }
            },
            year,
            month,
            day
            )

            dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
            dpd.show()

        }
    }


}