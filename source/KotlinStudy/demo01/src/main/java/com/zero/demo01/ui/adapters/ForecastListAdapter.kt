package com.zero.demo01.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zero.demo01.R
import com.zero.demo01.domain.model.Forecast
import com.zero.demo01.domain.model.ForecastList
import com.zero.demo01.extensions.ctx
import kotlinx.android.extensions.LayoutContainer
import com.zero.demo01.extensions.*
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(private val weekForecast: ForecastList,
                          private val itemClick: (Forecast) -> Unit) :
    RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount() = weekForecast.size

    class ViewHolder(override val containerView: View,  val itemClick: (Forecast) -> Unit)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindForecast(forecast: Forecast) {
            with(containerView) {
                Picasso.with(itemView.ctx).load(forecast.iconUrl).into(icon)
                dateText.text = forecast.date.toDateString()
                descriptionText.text = forecast.description
                maxTemperature.text = "${forecast.high}º"
                minTemperature.text = "${forecast.low}º"
                itemView.setOnClickListener { itemClick(forecast) }
            }
        }
    }
}