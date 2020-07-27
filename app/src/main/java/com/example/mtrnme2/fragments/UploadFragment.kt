package com.example.mtrnme2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mtrnme2.R
import com.example.mtrnme2.databinding.FragmentUploadBinding
import com.google.android.material.snackbar.Snackbar


class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnBrowseart.setOnClickListener { view ->
            Snackbar.make(view, "BROWSE!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show() }

        binding.btnBrowseaudio.setOnClickListener { view ->
            Snackbar.make(view, "MUSIC!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show() }

        binding.fabUpl.setOnClickListener { view ->
            Snackbar.make(view, "UPLOAD!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show() }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadBinding.inflate(inflater)
        return binding.root
    }



    companion object {
        fun getNewInstance(): UploadFragment {
            return UploadFragment()
        }
    }
}