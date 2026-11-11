package com.example.quiz_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class results : Fragment() {
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
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    private val args: resultsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nomeUtente = args.playerName
        val puntiTotali = args.points

        val tvRisultato: TextView = view.findViewById(R.id.tv_final_result)
        val btnReplay: Button = view.findViewById(R.id.replay)

        tvRisultato.text = "$nomeUtente!\nHai totalizzato $puntiTotali / 3"

        val databaseDAO = userDatabase.getInstance(requireContext().applicationContext).getDao()
        myCoroutineScope.launch{
            val utenti: Array<User> = databaseDAO.getUserByName(nomeUtente)
            var record = puntiTotali

            if (utenti.isEmpty()) {
                val nuovoUtente = User(name = nomeUtente, highScore = puntiTotali)
                databaseDAO.insert(nuovoUtente)
            } else {
                val utenteEsistente = utenti[0]

                if (puntiTotali > utenteEsistente.highScore) {
                    utenteEsistente.highScore = puntiTotali
                    databaseDAO.update(utenteEsistente)
                    record = puntiTotali
                } else {
                    record = utenteEsistente.highScore
                }
            }

            withContext(Dispatchers.Main) {
                tvRisultato.append("\nIl Record è: $record / 3")
            }

        }

        btnReplay.setOnClickListener {
            val action = resultsDirections.actionResultsToHome2()
            view.findNavController().navigate(action)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            results().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}