package com.example.movietheatre.feature_payment.presentation.screen.add_card

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentAddCardBinding
import com.example.movietheatre.feature_payment.domain.model.CardType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCardFragment : BaseFragment<FragmentAddCardBinding>(FragmentAddCardBinding::inflate) {
    private val viewModel: AddCardViewModel by viewModels()
    override fun setUp() {
        collectLatestFlow(viewModel.uiState) { updateUiState(it) }
        collectLatestFlow(viewModel.sideEffect) { getSideEffects(it) }
    }

    override fun clickListeners() {
        binding.etCardHolder.addTextChangedListener { text ->
            viewModel.onEvent(AddCardEvent.CardHolderNameChanged(text.toString()))
        }
        binding.etCardNumber.addTextChangedListener { text ->
            viewModel.onEvent(AddCardEvent.CardNumberChanged(text.toString()))
        }
        binding.etCardExpiresDate.addTextChangedListener { text ->
            viewModel.onEvent(AddCardEvent.ExpiryDateChanged(text.toString()))
        }
        binding.etCardCCV.addTextChangedListener { text ->
            viewModel.onEvent(AddCardEvent.CVVChanged(text.toString()))
        }

        binding.btnAddCard.setOnClickListener {
            viewModel.onEvent(AddCardEvent.AddCardClicked)
        }

        binding.rbGroups.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rbVisaCard -> viewModel.onEvent(AddCardEvent.CardTypeChanged(CardType.VISA))
                R.id.rbMasterCard -> viewModel.onEvent(AddCardEvent.CardTypeChanged(CardType.MASTERCARD))

            }
        }
        
    }

    private fun getSideEffects(effect: AddCardSideEffect) {
        when (effect) {
            is AddCardSideEffect.ShowError -> {
                binding.root.showSnackBar(getString(effect.message), backgroundColor = R.color.red)
            }

            is AddCardSideEffect.CardAddedSuccessfully -> {
                findNavController().navigateUp()
            }
        }
    }

    private fun updateUiState(state: AddCardUiState) {

        val img =
            if (state.cardTypeSelected == CardType.VISA) R.drawable.ic_visa_card else R.drawable.ic_master_card

        binding.imgCard.setImageResource(img)

        binding.txtCardDateDisplay.text = state.expiryDate
        binding.txtCardNumberDisplay.text = state.cardNumber
        binding.txtCardHolderNameDisplay.text = state.cardHolderName


        binding.apply {
            txtCardHolderNameError.text = state.cardHolderNameError?.let { getString(it) }
            txtCardHolderNameError.isVisible = state.cardHolderNameError != null

            txtCardNumberError.text = state.cardNumberError?.let { getString(it) }
            txtCardNumberError.isVisible = state.cardNumberError != null

            txtCardExpiresError.text = state.expiryDateError?.let { getString(it) }
            txtCardExpiresError.isVisible = state.expiryDateError != null

            txtCardCCVError.text = state.cvvError?.let { getString(it) }
            txtCardCCVError.isVisible = state.cvvError != null

            btnAddCard.isEnabled = state.isAddCardEnabled
        }
    }

}