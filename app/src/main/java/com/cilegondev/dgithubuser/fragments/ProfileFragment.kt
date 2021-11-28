package com.cilegondev.dgithubuser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cilegondev.dgithubuser.R
import com.cilegondev.dgithubuser.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_user_info.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideApp.with(this).load(R.drawable.profile).into(view.imgAvatar)
        view.tvNama.text = "Nanang Sutisna"
        view.tvUsername.text = "emresutisna"
        view.tvLocation.text = "Cilegon"
        view.tvCompany.text = "Pemerintah Kota Cilegon"
        view.btnSave.visibility = View.GONE
    }

}
