package fr.maximedubost.digikofyapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.adapters.MachineAdapter
import fr.maximedubost.digikofyapp.adapters.PreparationAdapter
import fr.maximedubost.digikofyapp.repositories.MachineRepository
import fr.maximedubost.digikofyapp.repositories.PreparationRepository
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PreparationFragment : Fragment() {

    private lateinit var rvPreparations: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_preparation, container, false)
        
        val lavNoPreparation = view.findViewById<LottieAnimationView>(R.id.lav_no_preparation)
        val tvNoPreparation = view.findViewById<TextView>(R.id.tv_no_preparation)
        lavNoPreparation.visibility = if (PreparationRepository.Singleton.preparationList.size == 0) View.VISIBLE else View.GONE
        tvNoPreparation.visibility = if (PreparationRepository.Singleton.preparationList.size == 0) View.VISIBLE else View.GONE

        rvPreparations = view.findViewById(R.id.rv_preparations)
        rvPreparations.adapter = PreparationAdapter(
            PreparationRepository.Singleton.preparationList.filter { it.saved },
            true,
            false,
            false,
            this.activity as MainActivity
        )

        initNavBar(view)

        return view
    }

    fun initNavBar(view: View) {
        var isSavedPreparationsPage = true
        var isNextPreparationsPage = false
        var isPastPreparationsPage = false

        val tvSavedPreparationsTab: TextView = view.findViewById(R.id.tv_saved_preparations_tab)
        val tvNextPreparationsTab: TextView = view.findViewById(R.id.tv_next_preparations_tab)
        val tvPastPreparationsTab: TextView = view.findViewById(R.id.tv_past_preparations_tab)

        tvSavedPreparationsTab.setTextColor(Color.BLACK)

        tvSavedPreparationsTab.setOnClickListener {
            tvSavedPreparationsTab.setTextColor(Color.BLACK)
            tvNextPreparationsTab.setTextColor(Color.GRAY)
            tvPastPreparationsTab.setTextColor(Color.GRAY)
            isSavedPreparationsPage = true
            isNextPreparationsPage = false
            isPastPreparationsPage = false
            rvPreparations.adapter = PreparationAdapter(
                PreparationRepository.Singleton.preparationList.filter { it.saved },
                isSavedPreparationsPage,
                isNextPreparationsPage,
                isPastPreparationsPage,
                this.activity as MainActivity
            )
        }

        tvNextPreparationsTab.setOnClickListener {
            tvSavedPreparationsTab.setTextColor(Color.GRAY)
            tvNextPreparationsTab.setTextColor(Color.BLACK)
            tvPastPreparationsTab.setTextColor(Color.GRAY)
            isSavedPreparationsPage = false
            isNextPreparationsPage = true
            isPastPreparationsPage = false
            rvPreparations.adapter = PreparationAdapter(
                PreparationRepository.Singleton.preparationList.filter { it.isFuturePreparation() },
                isSavedPreparationsPage,
                isNextPreparationsPage,
                isPastPreparationsPage,
                this.activity as MainActivity
            )
        }

        tvPastPreparationsTab.setOnClickListener {
            tvSavedPreparationsTab.setTextColor(Color.GRAY)
            tvNextPreparationsTab.setTextColor(Color.GRAY)
            tvPastPreparationsTab.setTextColor(Color.BLACK)
            isSavedPreparationsPage = false
            isNextPreparationsPage = false
            isPastPreparationsPage = true
            rvPreparations.adapter = PreparationAdapter(
                PreparationRepository.Singleton.preparationList.filter { it.lastTime != null },
                isSavedPreparationsPage,
                isNextPreparationsPage,
                isPastPreparationsPage,
                this.activity as MainActivity
            )
        }

    }

}