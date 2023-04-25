package com.gtech.yojana.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gtech.yojana.ItemClickListener
import com.gtech.yojana.R
import com.gtech.yojana.databinding.ItemArticleBinding
import com.gtech.yojana.ui.home.YojanaAdapter
import com.gtech.yojana.ui.home.YojnaModel
import java.util.ArrayList

class ArticlesAdapter(
    val mContext: Context,
    private val mList: ArrayList<YojnaModel>,
    val mNavController: NavController
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        val title = binding.title

        //        val descriptor = binding.description
        val description = binding.description
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
        val view = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!mList.isEmpty()) {
            val md = mList[holder.adapterPosition]
            holder.title.text = md.heading
            holder.date.text = md.getdateasformatted(md.date!!)
            md?.description?.let { holder.description.text = md.description }

//        holder.description.text=md.description

            holder.setItemOnClickListener(object : ItemClickListener {
                override fun onItemClick(i: Int) {
                    val bundle = bundleOf("parcel" to md)
                    mNavController.navigate(
                        R.id.action_navigation_dashboard_to_articleDetailFragment,
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