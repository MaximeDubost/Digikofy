package fr.maximedubost.digikofyapp.oldrepositories

abstract class BaseRepository {
    abstract fun updateData(callback: () -> Unit)
}