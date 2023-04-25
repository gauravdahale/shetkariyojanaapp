package com.gtech.yojana.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gtech.yojana.R
import com.gtech.yojana.databinding.FragmentDashboardBinding
import com.gtech.yojana.databinding.FragmentHomeBinding
import com.gtech.yojana.ui.home.YojanaAdapter
import com.gtech.yojana.ui.home.YojnaModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var mAdapter: ArticlesAdapter
    private lateinit var mRecyclerView: RecyclerView
    private val TAG = "HomeFragment"
    private lateinit var mNavController: NavController
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val mList = ArrayList<YojnaModel>()
    var mDatabaseRef = FirebaseDatabase.getInstance().reference.child("articles")
    lateinit var mListener: ValueEventListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val root: View = binding.root


        return root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            mNavController = Navigation.findNavController(view)
            initRecyclerView()
        }

        private fun initRecyclerView() {
            mAdapter = ArticlesAdapter(requireContext(), mList, mNavController)
            mRecyclerView = binding.articleRecyclerView
            mRecyclerView.layoutManager = LinearLayoutManager(context)
            mRecyclerView.adapter = mAdapter
     try {
         fetchData()

     }catch (e:java.lang.Exception){
         Log.d(TAG, "initRecyclerView() returned: ${e.message}")
     }
        }

        private fun fetchData() {
            mListener =object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    mList.clear()
                    snapshot.children.forEach {
                        val md = it.getValue(YojnaModel::class.java)
                        mList.add(md!!)
                    }
                    mAdapter.notifyDataSetChanged()

                }
                override fun onCancelled(error: DatabaseError) {

                }

            }
            mDatabaseRef.addValueEventListener(mListener)
        }


        override fun onDestroyView() {
            super.onDestroyView()
            mDatabaseRef.removeEventListener(mListener)
            _binding = null
        }
    }