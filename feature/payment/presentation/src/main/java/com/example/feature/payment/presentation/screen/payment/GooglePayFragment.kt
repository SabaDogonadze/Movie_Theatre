package com.example.feature.payment.presentation.screen.payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.feature.payment.presentation.databinding.FragmentGooglePayBinding
import com.example.feature.payment.presentation.util.PaymentsUtil
import com.google.android.gms.common.api.CommonStatusCodes
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

    // A default price for testing in USD
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

        private const val TAG = "GooglePayFragment"

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
            Log.d(TAG, "Google Pay button clicked")
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
                    val result = task.isSuccessful && task.result == true
                    Log.d(
                        TAG,
                        "isReadyToPay result: $result, task successful: ${task.isSuccessful}"
                    )

                    if (task.isSuccessful) {
                        initializeGooglePayButton(result)

                        binding.googlePayButton.visibility = View.VISIBLE
                        Log.d(TAG, "Set button visibility to VISIBLE")
                    } else {
                        Log.e(TAG, "isReadyToPay error: ${task.exception}")

                        binding.googlePayButton.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                    binding.googlePayButton.visibility = View.VISIBLE
                }
            }
    }

    private fun initializeGooglePayButton(isReadyToPay: Boolean) {
        try {

            val allowedPaymentMethods = PaymentsUtil.getAllowedPaymentMethodsForButton()

            val buttonOptions = ButtonOptions.newBuilder()
                .setButtonType(ButtonConstants.ButtonType.PAY)
                .setAllowedPaymentMethods(allowedPaymentMethods)
                .build()

            binding.googlePayButton.initialize(buttonOptions)
        } catch (e: Exception) {
            Log.d("exception", e.toString())
        }
    }

    private fun requestPayment() {
        val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(defaultPrice)
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        // Use new ActivityResult API instead of AutoResolveHelper
        paymentsClient.loadPaymentData(request)
            .addOnCompleteListener { task ->
                try {
                    if (!handlePaymentTask(task)) {

                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Exception in loadPaymentData task", e)
                }
            }
    }

    private fun handlePaymentTask(task: Task<PaymentData>): Boolean {
        if (task.isSuccessful) {
            // Process the PaymentData directly
            handlePaymentSuccess(task.result)
            return true
        } else {
            val exception = task.exception
            if (exception is ResolvableApiException) {
                try {
                    // Now we're using the ActivityResultLauncher from the parent fragment
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    activityResultLauncher?.launch(intentSenderRequest)
                    return true
                } catch (e: Exception) {
                    Log.e(TAG, "Error launching resolution", e)
                }
            }
            return false
        }
    }

    // Called from PaymentHostFragment's ActivityResultLauncher callback
    fun handlePaymentResult(resultCode: Int, data: Intent?) {

        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let { intent ->
                    PaymentData.getFromIntent(intent)?.let { paymentData ->
                        handlePaymentSuccess(paymentData)
                    } ?: run {
                        //  binding.paymentStatus.text = "Error: Unable to get payment data"
                    }
                } ?: run {
                    //  binding.paymentStatus.text = "Error: No data returned"
                }
            }

            Activity.RESULT_CANCELED -> {
                //    binding.paymentStatus.text = "Payment cancelled"
            }

            else -> {
                //    binding.paymentStatus.text = "Payment failed with code: $resultCode"
            }
        }
    }

    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson()
        try {
            val paymentMethodData =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")
            val paymentToken = paymentMethodData
                .getJSONObject("tokenizationData")
                .getString("token")
            //binding.paymentStatus.text = "Payment processed successfully!"
            Log.d(TAG, "PaymentToken: $paymentToken")
        } catch (e: JSONException) {
            //   binding.paymentStatus.text = "Error processing payment: ${e.message}"
        }
    }

    private fun handleError(statusCode: Int) {
        val message = when (statusCode) {
            CommonStatusCodes.NETWORK_ERROR -> "Network error"
            CommonStatusCodes.DEVELOPER_ERROR -> "Developer error"
            CommonStatusCodes.INTERNAL_ERROR -> "Internal error"
            else -> "Unknown error (code: $statusCode)"
        }
        //   binding.paymentStatus.text = "Payment failed: $message"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}