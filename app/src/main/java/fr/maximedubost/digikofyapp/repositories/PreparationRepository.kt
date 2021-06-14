package fr.maximedubost.digikofyapp.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.models.PreparationModel
import fr.maximedubost.digikofyapp.repositories.MachineRepository.Singleton.machineDatabaseReference
import fr.maximedubost.digikofyapp.repositories.PreparationRepository.Singleton.preparationDatabaseReference
import fr.maximedubost.digikofyapp.repositories.PreparationRepository.Singleton.preparationList

class PreparationRepository : BaseRepository() {

    object Singleton {
        val preparationDatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("preparation")

        val preparationList = arrayListOf<PreparationModel>()
    }

    override fun updateData(callback: () -> Unit) {
        preparationDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                preparationList.clear()
                snapshot.children.forEach {
                    val preparation = it.getValue(PreparationModel::class.java)
                    if(preparation != null) preparationList.add(preparation)
                }
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun deletePreparation(preparation: PreparationModel) =
        machineDatabaseReference.child(preparation.id).removeValue()
}