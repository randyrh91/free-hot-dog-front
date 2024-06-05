package com.hot_dog_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hot_dog_app.R
import com.hot_dog_app.model.CompanyResponse
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


class AdapterListView(
    private val context: Context?,
    private val dataList: List<CompanyResponse?>?
) : RecyclerView.Adapter<AdapterListView.CustomViewHolder>() {

    class CustomViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        var companyName: TextView = mView.findViewById(R.id.company_name)
        var companyDescription: TextView = mView.findViewById(R.id.company_description)
        var companyAddress: TextView = mView.findViewById(R.id.company_address)
        var coverImage: ImageView = mView.findViewById(R.id.company_photo)
        var donateButton: Button = mView.findViewById(R.id.danate_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.custom_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.companyName.text = dataList?.get(position)?.name
        holder.companyDescription.text = dataList?.get(position)?.description
        holder.companyAddress.text = dataList?.get(position)?.address
        holder.donateButton.setOnClickListener{
            Toast.makeText(
                context,
                "Gracias por donar $5 a nuestra causa.",
                Toast.LENGTH_LONG
            ).show()
        }
        val builder: Picasso.Builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(dataList!![position]?.photo)
            .placeholder((R.mipmap.ic_launcher_round))
            .error(R.mipmap.ic_launcher_round)
            .into(holder.coverImage)
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }
}