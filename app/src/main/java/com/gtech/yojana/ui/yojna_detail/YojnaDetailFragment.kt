package com.gtech.yojana.ui.yojna_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.gtech.yojana.databinding.FragmentYojnaDetailBinding
import com.gtech.yojana.ui.home.YojnaModel


class YojnaDetailFragment : Fragment() {
    private lateinit var mNavController: NavController
    private val TAG = "YojnaDetailFragment"
    lateinit var model: YojnaModel

    var _binding: FragmentYojnaDetailBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentYojnaDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        var dd = arguments?.getSerializable("parcel") as YojnaModel
//        Toast.makeText(context, dd.description, Toast.LENGTH_SHORT).show()
binding.title.setText(dd.heading)
        binding.description.setText(dd.description)
        binding.date.setText(dd.getdateasformatted(dd.date!!))
        binding.description.setText(dd.description)
        Glide.with(requireContext()).load(dd.image).into(binding.image)
        binding.apply.setOnClickListener {
            val url = dd.webUrl
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }

}
