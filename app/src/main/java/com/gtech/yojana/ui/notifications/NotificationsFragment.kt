package com.gtech.yojana.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gtech.yojana.R
import com.gtech.yojana.databinding.FragmentNotificationsBinding
import com.gtech.yojana.ui.login.LoginActivity
class UserModel{
    var name:String?=null
    var number:String?=null
    var email:String?=null
    var password:String?=null
}

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val user = FirebaseAuth.getInstance().currentUser?.uid.toString()
    var mDatabaseRef = FirebaseDatabase.getInstance().reference.child("users").child(user)
    lateinit var mListener: ValueEventListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     val email =view.findViewById<TextView>(R.id.useremail)
        email.text =FirebaseAuth.getInstance().currentUser?.email
        val logout = view.findViewById<Button>(R.id.logout)
        fetchUserDetails()

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "User Logged Out!", Toast.LENGTH_SHORT).show()
            val i = Intent(activity,LoginActivity::class.java)
        requireActivity().finish()
            startActivity(i)

        }

    }
    fun fetchUserDetails(){
        mListener = object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
val md = snapshot.getValue(UserModel::class.java)
                binding.useremail.text = md?.email
                binding.username.text = md?.name
                binding.usernumber.text = md?.number
                Log.d("USER   =>", "onDataChange:${md?.email} ")
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        mDatabaseRef.addListenerForSingleValueEvent(mListener)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        mDatabaseRef.removeEventListener(mListener)
        _binding = null
    }
}