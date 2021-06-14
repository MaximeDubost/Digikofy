package fr.maximedubost.digikofyapp.repositories

abstract class BaseRepository {
    abstract fun updateData(callback: () -> Unit)
}