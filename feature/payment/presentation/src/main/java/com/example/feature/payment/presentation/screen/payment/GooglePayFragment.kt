package com.example.feature.payment.presentation.screen.payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.feature.payment.presentation.databinding.FragmentGooglePayBinding
import com.example.feature.payment.presentation.util.PaymentsUtil
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.google.android.gms.wallet.button.ButtonConstants
import com.google.android.gms.wallet.button.ButtonOptions
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject

@AndroidEntryPoint
class GooglePayFragment : Fragment() {

    private var _binding: FragmentGooglePayBinding? = null
    private val binding get() = _binding!!

    private lateinit var paymentsClient: PaymentsClient
    private var activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

    private val viewModel: PaymentViewModel by lazy {
        (parentFragment as? PaymentFragment)?.viewModel
            ?: ViewModelProvider(requireActivity())[PaymentViewModel::class.java]
    }

    private var defaultPrice = "10.00"

    companion object {
        val SUPPORTED_METHODS = listOf(
            "PAN_ONLY",
            "CRYPTOGRAM_3DS"
        )
        val SUPPORTED_NETWORKS = listOf(
            "AMEX",
            "DISCOVER",
            "JCB",
            "MASTERCARD",
            "VISA"
        )
        const val COUNTRY_CODE = "GE"
        const val CURRENCY_CODE = "GEL"
        const val PAYMENT_GATEWAY = "example"
        const val GATEWAY_MERCHANT_ID = "exampleGatewayMerchantId"


        fun newInstance(): GooglePayFragment {
            return GooglePayFragment()
        }
    }

    fun setActivityResultLauncher(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        activityResultLauncher = launcher
    }

    fun setTotalPrice(price: String) {
        defaultPrice = price
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGooglePayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentsClient = createPaymentsClient(requireActivity())

        binding.googlePayButton.setOnClickListener {
            viewModel.onEvent(PaymentEvent.OnGooglePayClick)
            requestPayment()
        }

        checkGooglePayAvailability()
    }

    private fun createPaymentsClient(activity: Activity): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
            .build()
        return Wallet.getPaymentsClient(activity, walletOptions)
    }

    private fun checkGooglePayAvailability() {

        val isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest()

        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString())

        paymentsClient.isReadyToPay(request)
            .addOnCompleteListener { task ->
                try {
                    if (task.isSuccessful) {
                        initializeGooglePayButton()
                        binding.googlePayButton.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                    binding.googlePayButton.visibility = View.GONE
                }
            }
    }

    private fun initializeGooglePayButton() {
        try {

            val allowedPaymentMethods = PaymentsUtil.getAllowedPaymentMethodsForButton()

            val buttonOptions = ButtonOptions.newBuilder()
                .setButtonType(ButtonConstants.ButtonType.PAY)
                .setAllowedPaymentMethods(allowedPaymentMethods)
                .build()

            binding.googlePayButton.initialize(buttonOptions)
        } catch (_: Exception) {
        }
    }

    private fun requestPayment() {
        val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(defaultPrice)
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        paymentsClient.loadPaymentData(request)
            .addOnCompleteListener { task ->
                try {
                    handlePaymentTask(task)
                } catch (_: Exception) {
                }
            }
    }

    private fun handlePaymentTask(task: Task<PaymentData>): Boolean {
        if (task.isSuccessful) {
            handlePaymentSuccess(task.result)
            return true
        } else {
            val exception = task.exception
            if (exception is ResolvableApiException) {
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    activityResultLauncher?.launch(intentSenderRequest)
                    return true
                } catch (_: Exception) {
                }
            }
            return false
        }
    }

    fun handlePaymentResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let { intent ->
                    PaymentData.getFromIntent(intent)?.let { paymentData ->
                        handlePaymentSuccess(paymentData)
                    }
                }
            }
        }
    }

    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson()
        try {
            val paymentMethodData =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")
            paymentMethodData
                .getJSONObject("tokenizationData")
                .getString("token")
        } catch (_: JSONException) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}