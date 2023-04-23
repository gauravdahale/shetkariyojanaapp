package com.gtech.yojana.ui.notifications

import android.content.Intent
import android.os.Bundle
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
import com.gtech.yojana.R
import com.gtech.yojana.databinding.FragmentNotificationsBinding
import com.gtech.yojana.ui.login.LoginActivity


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     val email =view.findViewById<TextView>(R.id.useremail)
        email.text =FirebaseAuth.getInstance().currentUser?.email
        val logout = view.findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "User Logged Out!", Toast.LENGTH_SHORT).show()
            val i = Intent(activity,LoginActivity::class.java)
        requireActivity().finish()
            startActivity(i)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}