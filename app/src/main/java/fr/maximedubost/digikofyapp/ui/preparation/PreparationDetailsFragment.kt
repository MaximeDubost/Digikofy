package fr.maximedubost.digikofyapp.ui.preparation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.databinding.PreparationDetailsFragmentBinding
import fr.maximedubost.digikofyapp.databinding.PreparationFragmentBinding
import fr.maximedubost.digikofyapp.models.PreparationModel
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter

class PreparationDetailsFragment : Fragment() {
    private val args: PreparationDetailsFragmentArgs by navArgs()
    private lateinit var viewModel: PreparationViewModel
    private lateinit var binding: PreparationDetailsFragmentBinding
    private var isEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PreparationDetailsFragmentBinding.inflate(inflater)

        binding.loading.visibility = View.VISIBLE
        binding.ivEdit.visibility = View.INVISIBLE
        binding.body.visibility = View.GONE
        binding.btnDeletePreparation.visibility = View.GONE

        binding.ivBack.setOnClickListener { onClickIvBack() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PreparationViewModel::class.java)

        viewModel.findById(requireActivity().applicationContext, args.preparationId)

        viewModel.preparationFindByIdResponseSuccess.observe(viewLifecycleOwner, {
            val preparation = it.data.body()!!

            showData(preparation)

            binding.ivEdit.setOnClickListener { onClickIvEdit(preparation) }
            binding.btnUsePreparation.setOnClickListener { onClickBtnUsePreparation(preparation) }
            binding.btnDeletePreparation.setOnClickListener { deletePreparation(preparation) }
        })

        viewModel.preparationFindByIdResponseError.observe(viewLifecycleOwner, {
            binding.loading.visibility = View.GONE
            Toast.makeText(
                requireActivity().applicationContext,
                "Impossible d'afficher les détails de la préparation",
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    /**
     * Switch between read mode and edit mode
     *
     * @param preparationName name of preparation
     * @param clearEditText true if EditText should be cleared, else false
     */
    private fun toggleEditMode(preparationName: String = "", clearEditText: Boolean = false) {
        isEditMode = !isEditMode
        binding.ivEdit.setImageResource(if(isEditMode) R.drawable.ic_check else R.drawable.ic_edit)
        binding.ivBack.setImageResource(if(isEditMode) R.drawable.ic_close else R.drawable.ic_back)
        binding.tvPreparationName.visibility = if(isEditMode) View.GONE else View.VISIBLE
        binding.tilPreparationName.visibility = if(isEditMode) View.VISIBLE else View.GONE
        binding.btnUsePreparation.visibility = if(isEditMode) View.GONE else View.VISIBLE
        binding.btnDeletePreparation.visibility = if(isEditMode) View.VISIBLE else View.GONE
        if(isEditMode)
            binding.etPreparationName.setText(preparationName)
        if(clearEditText)
            binding.etPreparationName.setText("")
    }

    /**
     * Action on click ivBack
     */
    private fun onClickIvBack() {
        // TODO : Close keyboard
        if(isEditMode)
            toggleEditMode(clearEditText = true)
        else
            view?.findNavController()?.popBackStack()
    }

    /**
     * Action on click ivEdit
     *
     * @param preparation a preparation
     */
    private fun onClickIvEdit(preparation: PreparationModel) {
        // TODO : Close keyboard
        if(isEditMode && binding.etPreparationName.text.toString() != preparation.name)
            updatePreparation(preparation)
        else
            toggleEditMode(preparation.name!!)
    }

    /**
     * Action on click btnUsePreparation
     *
     * @param preparation a preparation
     */
    private fun onClickBtnUsePreparation(preparation: PreparationModel) {
        // TODO : Start preparation
        Toast.makeText(
            context,
            "Coming soon...",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Show fetched preparation data
     *
     * @param preparation fetched preparation
     */
    private fun showData(preparation: PreparationModel) {
        binding.loading.visibility = View.GONE
        binding.body.visibility = View.VISIBLE
        binding.ivEdit.visibility = View.VISIBLE
        binding.tvPreparationName.text = preparation.name
        binding.tvPreparationWeekdays.text = StringDateTimeFormatter.weekdays(preparation.weekdays!!)
        binding.tvPreparationCreationDate.text = StringDateTimeFormatter.from(preparation.creationDate.toString())
        binding.tvPreparationLastUpdate.text = StringDateTimeFormatter.from(preparation.lastUpdate.toString())
        binding.lvPreparationHours // TODO
    }

    /**
     * Update a preparation
     *
     * @param preparation a preparation
     */
    private fun updatePreparation(preparation: PreparationModel) {
        viewModel.update(
            requireActivity().applicationContext,
            PreparationModel(preparation.id, name = binding.etPreparationName.text.toString()) // TODO : edit others attributes
        )

        viewModel.preparationUpdateResponseSuccess.observe(viewLifecycleOwner, {
            binding.tvPreparationName.text = binding.etPreparationName.text.toString()
            Toast.makeText(requireContext().applicationContext, "Préparation modifiée avec succès", Toast.LENGTH_SHORT).show()
            toggleEditMode()
        })

        viewModel.preparationUpdateResponseError.observe(viewLifecycleOwner, {
            binding.etPreparationName.setText(preparation.name)
            Toast.makeText(requireContext().applicationContext, "Echec de la modification de la préparation", Toast.LENGTH_SHORT).show()
            toggleEditMode()
        })
    }

    /**
     * Delete a preparation
     *
     * @param preparation a preparation
     */
    private fun deletePreparation(preparation: PreparationModel) {
        viewModel.delete(requireActivity().applicationContext, preparation.id!!)

        viewModel.preparationDeleteResponseSuccess.observe(viewLifecycleOwner, {
            Log.d("######## Delete", "Success")
            Toast.makeText(requireActivity().applicationContext, "Preparation supprimée avec succès", Toast.LENGTH_SHORT).show()
            // viewModel.findAll(requireActivity().applicationContext)
            view?.findNavController()?.popBackStack()
        })

        viewModel.preparationDeleteResponseError.observe(viewLifecycleOwner, {
            Log.d("######## Delete", "Error")
            Toast.makeText(requireActivity().applicationContext, "Impossible de suppriméer cette preparation", Toast.LENGTH_SHORT).show()
        })
    }

}