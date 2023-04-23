package com.gtech.yojana.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.gtech.yojana.ItemClickListener
import com.gtech.yojana.R
import com.gtech.yojana.databinding.ItemYojnaBinding

import android.text.format.DateFormat
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class YojnaModel : java.io.Serializable {
    val heading: String? = null
    val description: String? = null
    val image: String? = null
    val webUrl: String? = null
    val date: Long? = null
    fun getdateasformatted(date: Long): String {

        val formatter = SimpleDateFormat("dd-mm-yyyy")
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = date
        val date: String = DateFormat.format("hh:mm:aa dd-MM-yyyy ", cal).toString()
        return date
    }
}

class YojanaAdapter(
    val mContext: Context,
    private val mList: ArrayList<YojnaModel>,
    val mNavController: NavController
) : RecyclerView.Adapter<YojanaAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemYojnaBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        val title = binding.title

        //        val descriptor = binding.description
        val image = binding.image
        val date = binding.date
        private var itemClickListener: ItemClickListener? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClickListener?.onItemClick(adapterPosition)
        }

        fun setItemOnClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemYojnaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!mList.isEmpty()) {
            val md = mList[holder.adapterPosition]
            holder.title.text = md.heading
            holder.date.setText(md?.getdateasformatted(md.date!!))
            md?.image?.let { Glide.with(mContext).load(md?.image).into(holder.image) }

//        holder.description.text=md.description

            holder.setItemOnClickListener(object : ItemClickListener {
                override fun onItemClick(i: Int) {
                    val bundle = bundleOf("parcel" to md)
                    mNavController.navigate(
                        R.id.action_navigation_home_to_yojnaDetailFragment,
                        bundle
                    )
                }

            })
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}