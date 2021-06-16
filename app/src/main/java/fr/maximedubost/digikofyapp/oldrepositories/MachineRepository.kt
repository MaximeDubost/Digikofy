package fr.maximedubost.digikofyapp.oldrepositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.oldrepositories.MachineRepository.Singleton.machineDatabaseReference
import fr.maximedubost.digikofyapp.oldrepositories.MachineRepository.Singleton.machineList

class MachineRepository : BaseRepository() {

    object Singleton {
        val machineDatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("machine")

        val machineList = arrayListOf<MachineModel>()
    }

    override fun updateData(callback: () -> Unit) {
        machineDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                machineList.clear()
                snapshot.children.forEach {
                    val machine = it.getValue(MachineModel::class.java)
                    if(machine != null) machineList.add(machine)
                }
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun updateMachine(machine: MachineModel) =
        machineDatabaseReference.child(machine.id).setValue(machine)

    fun deleteMachine(machine: MachineModel) =
        machineDatabaseReference.child(machine.id).removeValue()
}