package com.gtech.yojana.ui.articledetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.gtech.yojana.R
import com.gtech.yojana.databinding.FragmentArticleDetailBinding
import com.gtech.yojana.databinding.FragmentYojnaDetailBinding
import com.gtech.yojana.ui.home.YojnaModel

class ArticleDetailFragment : Fragment() {
    private lateinit var mNavController: NavController
    private val TAG = "YojnaDetailFragment"
    lateinit var model: YojnaModel
    var _binding: FragmentArticleDetailBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArticleDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        var dd = arguments?.getSerializable("parcel") as YojnaModel
//        Toast.makeText(context, dd.description, Toast.LENGTH_SHORT).show()
        binding.title.text = dd.heading
        binding.description.text = dd.description
        binding.date.text = dd.getdateasformatted(dd.date!!)
        binding.description.text = dd.description

    }

}