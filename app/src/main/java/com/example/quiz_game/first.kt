package com.example.quiz_game

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

import kotlinx.coroutines.*
import java.net.URL


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class first : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private val myCoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    val args: firstArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nomeUtente = args.playerName
        val navController = view.findNavController()
        val tv: TextView = view.findViewById(R.id.request)
        val imageViewFlag: ImageView = view.findViewById(R.id.flag1)
        val button_Opzione1: Button = view.findViewById(R.id.opzione1)
        val button_Opzione2: Button = view.findViewById(R.id.opzione2)

        tv.text = "Loading..."
        button_Opzione1.isEnabled = false
        button_Opzione2.isEnabled = false
        button_Opzione1.text = "..."
        button_Opzione2.text = "..."

        myCoroutineScope.launch {
            try {
                val download = API.retrofitService.getAllCountries()

                PartitaCorrente.downloaded = download.shuffled()
                val nazioneEsatta = PartitaCorrente.downloaded[0]
                val nazioneSbagliata = PartitaCorrente.downloaded[10]

                val opzioni =
                    listOf(nazioneEsatta.name.common, nazioneSbagliata.name.common).shuffled()

                val urlImmagine = URL(nazioneEsatta.flags.png)
                val connessione = urlImmagine.openConnection()
                val immagineBitmap = BitmapFactory.decodeStream(connessione.getInputStream())

                withContext(Dispatchers.Main) {
                    tv.text = "$nomeUtente, che bandiera è questa?"
                    imageViewFlag.setImageBitmap(immagineBitmap)

                    button_Opzione1.text = opzioni[0]
                    button_Opzione2.text = opzioni[1]
                    button_Opzione1.isEnabled = true
                    button_Opzione2.isEnabled = true

                    button_Opzione1.setOnClickListener {
                        val count =
                            if (button_Opzione1.text.toString() == nazioneEsatta.name.common) 1 else 0
                        val action = firstDirections.actionFirstToSecond(nomeUtente, count)
                        navController.navigate(action)
                    }

                    button_Opzione2.setOnClickListener {
                        val count =
                            if (button_Opzione2.text.toString() == nazioneEsatta.name.common) 1 else 0
                        val action = firstDirections.actionFirstToSecond(nomeUtente, count)
                        navController.navigate(action)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    tv.text = "Errore di connessione"
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            first().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}