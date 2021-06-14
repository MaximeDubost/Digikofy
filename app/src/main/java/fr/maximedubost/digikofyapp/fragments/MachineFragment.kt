package fr.maximedubost.digikofyapp.fragments

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
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.repositories.MachineRepository.Singleton.machineList

class MachineFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_machine, container, false)

        /*
        val machineList = arrayListOf(
            MachineModel("1", "Maison - Cuisine", "Modèle standard", "Disponible"),
            MachineModel("2", "Société - Cuisine", "Modèle standard", "Disponible"),
            MachineModel("3", "Société - Salle de pause", "Modèle standard", "Indisponible")
        )
        */

        val lavNoMachine = view.findViewById<LottieAnimationView>(R.id.lav_no_machine)
        val tvNoMachine = view.findViewById<TextView>(R.id.tv_no_machine)
        lavNoMachine.visibility = if (machineList.size == 0) View.VISIBLE else View.GONE
        tvNoMachine.visibility = if (machineList.size == 0) View.VISIBLE else View.GONE

        val rvMachines = view.findViewById<RecyclerView>(R.id.rv_machines)
        rvMachines.adapter = MachineAdapter(machineList, this.activity as MainActivity)

        return view
    }

}