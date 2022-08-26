package com.simpleapp.Fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.common.util.CollectionUtils
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.simpleapp.AutocompleteTestActivity
import com.simpleapp.LocationActivity
import com.simpleapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectionFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var yourLocation: TextView
    lateinit var whereTo: TextView
    private val pickupRequestMessage = 1
    private val dropdownRequestMessage = 2
    private var requestCode = 0
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        val data = result.data

        if (result.resultCode == Activity.RESULT_OK) {
            if (requestCode == pickupRequestMessage) {
                data?.let {
                    val place: Place = Autocomplete.getPlaceFromIntent(data)
                    val name = place?.name
                    yourLocation.text = name
                }
                if (requestCode == dropdownRequestMessage) {
                }
                data?.let {
                    val place: Place = Autocomplete.getPlaceFromIntent(data)
                    val name = place?.name
                    whereTo.text = name
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        yourLocation = view.findViewById(R.id.frg_tx1)
        whereTo = view.findViewById(R.id.frg_tx2)

        yourLocation.setOnClickListener {
            val intent = Intent(activity, AutocompleteTestActivity::class.java)
            startActivity(intent)

//            val fields = listOf(Place.Field.ID, Place.Field.NAME)
//            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                .build(activity!!)
//
//            requestCode = pickupRequestMessage
//            launcher.launch(intent)
        }
        whereTo.setOnClickListener {

            val intent = Intent(activity, AutocompleteTestActivity::class.java)
            startActivity(intent)
        }
    }
}
/*
            val fields = listOf(Place.Field.ID, Place.Field.NAME)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(activity!!)


            انا بتعامل علي  Firebase وعايز اكتب Cloud Functions for Firebase
            (دوال بتتنفذ علي السيرفر)
            في لازم اسجل بفيزا عشان افعل الموضوع وجربت فيزا قبض منفعتش ف انا عايز اعمل فيزا من الي موجودين في الصورة بس مش عارف اسمهم اي وبيتعملوا ازاي





            requestCode = dropdownRequestMessage
            launcher.launch(intent)
       */


