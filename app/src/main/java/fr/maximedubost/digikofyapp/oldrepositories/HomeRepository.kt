package fr.maximedubost.digikofyapp.oldrepositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.oldrepositories.PreparationRepository.Singleton.preparationList

class HomeRepository : BaseRepository() {

    object Singleton {
        val preparationDatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("preparation")

        val preparationList = arrayListOf<MachineModel>()
    }

    override fun updateData(callback: () -> Unit) {
        MachineRepository.Singleton.machineDatabaseReference.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                preparationList.clear()
                snapshot.children.forEach {

                }
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

}