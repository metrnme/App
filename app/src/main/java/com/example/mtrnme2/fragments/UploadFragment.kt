package com.example.mtrnme2.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.amplifyframework.core.Amplify
import com.developer.filepicker.model.DialogConfigs
import com.developer.filepicker.model.DialogProperties
import com.developer.filepicker.view.FilePickerDialog
import com.example.mtrnme2.R
import com.example.mtrnme2.databinding.FragmentUploadBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.io.File


class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding
    private lateinit var dialog: FilePickerDialog
    var properties = DialogProperties()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        properties.selection_type = DialogConfigs.FILE_SELECT
        dialog = FilePickerDialog(this.context, properties)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnBrowseart.setOnClickListener { view ->
            Snackbar.make(view, "BROWSE!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        binding.btnBrowseaudio.setOnClickListener { view ->
            dialog.setDialogSelectionListener {


                Toast.makeText(
                    this.context,
                    "File Selected: ${it.size} has been selected",
                    Toast.LENGTH_SHORT
                ).show()
                uploadFile(it[0].toString())
            }
            dialog.setTitle("Select a File")
            dialog.show()

        }

        binding.fabUpl.setOnClickListener { view ->
            Snackbar.make(view, "UPLOAD!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

    }

    private fun uploadFile(path: String) {
        val fileToUpload = File(path)
        Log.e("File Path", fileToUpload.absolutePath)
        Amplify.Storage.uploadFile(
            "Example Key",
            fileToUpload,
            { result ->
                Toast.makeText(
                    this.context,
                    "File SuccessFully Uploaded + ${result.getKey()}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()) },
            { error ->
                Toast.makeText(
                    this.context,
                    "Upload Failed",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("MyAmplifyApp", "Upload failed", error) }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (dialog != null) {   //Show dialog if the read permission has been granted.
                        dialog.show()
                    }
                } else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(
                        context,
                        "Permission is Required for getting list of files",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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