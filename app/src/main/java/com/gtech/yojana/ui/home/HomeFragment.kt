package com.gtech.yojana.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gtech.yojana.R
import com.gtech.yojana.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var mAdapter: YojanaAdapter
    private lateinit var mRecyclerView: RecyclerView
    private val TAG = "HomeFragment"
    private lateinit var mNavController: NavController
    private var _binding: FragmentHomeBinding? = null
    val mList = ArrayList<YojnaModel>()
    var mDatabaseRef = FirebaseDatabase.getInstance().reference.child("yojna")
    lateinit var mListener: ValueEventListener
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = YojanaAdapter(requireContext(), mList, mNavController)
        mRecyclerView = binding.yojnaRecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter
fetchData()
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