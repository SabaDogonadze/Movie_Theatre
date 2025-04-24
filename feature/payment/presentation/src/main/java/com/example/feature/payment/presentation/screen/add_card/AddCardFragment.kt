package com.example.feature.payment.presentation.screen.add_card


import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.payment.domain.model.CardType
import com.example.feature.payment.presentation.databinding.FragmentAddCardBinding
import com.example.resource.R
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

        binding.etCardCCV.addTextChangedListener { text ->
            viewModel.onEvent(AddCardEvent.CVVChanged(text.toString()))
        }

        binding.btnAddCard.setOnClickListener {
            viewModel.onEvent(AddCardEvent.AddCardClicked)
        }
        expiryDateTextWatcher()

        binding.rbGroups.setOnCheckedChangeListener { _, i ->
            when (i) {
                com.example.feature.payment.presentation.R.id.rbVisaCard -> viewModel.onEvent(
                    AddCardEvent.CardTypeChanged(CardType.VISA)
                )

                com.example.feature.payment.presentation.R.id.rbMasterCard -> viewModel.onEvent(
                    AddCardEvent.CardTypeChanged(CardType.MASTERCARD)
                )

            }
        }

    }

    private fun expiryDateTextWatcher() {
        binding.etCardExpiresDate.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            private var isDeleting = false
            private var previousLength = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                previousLength = s?.length ?: 0
                isDeleting = after < count
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return

                s?.let {
                    if (!isDeleting && it.length == 2 && !it.contains("/")) {
                        isFormatting = true
                        val formattedText = getString(R.string.expire_date_format, it)
                        binding.etCardExpiresDate.setText(formattedText)
                        binding.etCardExpiresDate.setSelection(formattedText.length)
                        isFormatting = false
                    }
                    if (isDeleting && previousLength == 3 && it.length == 2) {
                        isFormatting = true
                        binding.etCardExpiresDate.setText(it.toString().replace("/", ""))
                        binding.etCardExpiresDate.setSelection(2)
                        isFormatting = false
                    }

                    viewModel.onEvent(AddCardEvent.ExpiryDateChanged(it.toString()))
                }
            }
        })
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
        Log.d("adduistate", state.toString())
        val img =
            if (state.cardTypeSelected == CardType.VISA) R.drawable.ic_visa_card else R.drawable.ic_master_card


        binding.apply {

            progressBar.isVisible = state.isLoading

            imgCard.setImageResource(img)
            txtCardDateDisplay.text = state.expiryDate
            txtCardNumberDisplay.text = state.cardNumber.chunked(4).joinToString("  ")
            txtCardHolderNameDisplay.text = state.cardHolderName

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