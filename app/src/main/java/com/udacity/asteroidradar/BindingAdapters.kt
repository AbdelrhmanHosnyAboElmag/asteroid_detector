package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.contentDescription=String.format(context.getString(R.string.astronomical_unit_format), number)
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.contentDescription=String.format(context.getString(R.string.km_unit_format), number)
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.contentDescription= String.format(context.getString(R.string.km_s_unit_format), number)
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
@BindingAdapter("todaydate")
fun todayDate(textView: TextView, closeApproachDate: String) {
    textView.text = closeApproachDate
}

@BindingAdapter("urlImage")
fun bindUrlImage(view: ImageView, imageUrl: PictureOfDay?) {
    if (imageUrl != null) {
        Picasso.get()
            .load(imageUrl.url)
            .fit()
            .centerCrop()
            .into(view)
        view.contentDescription=imageUrl.title
    } else {
        view.setImageBitmap(null)
    }
}