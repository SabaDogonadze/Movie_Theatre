package com.example.movietheatre.feature_payment.presentation.util

import com.example.movietheatre.feature_payment.presentation.screen.payment.GooglePayFragment
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object PaymentsUtil {

    /**
     * Create a JSON object with the parameters needed for the isReadyToPay request.
     */
    fun getIsReadyToPayRequest(): JSONObject {
        return JSONObject().apply {
            put("apiVersion", 2)
            put("apiVersionMinor", 0)
            put("allowedPaymentMethods", JSONArray().apply {
                put(getBaseCardPaymentMethod())
            })
        }
    }

    /**
     * Create a JSON object with the parameters needed for the PaymentDataRequest.
     */
    fun getPaymentDataRequest(price: String): JSONObject {
        return JSONObject().apply {
            put("apiVersion", 2)
            put("apiVersionMinor", 0)
            put("allowedPaymentMethods", JSONArray().apply {
                put(getCardPaymentMethod())
            })
            put("transactionInfo", getTransactionInfo(price))
            put("merchantInfo", getMerchantInfo())
        }
    }

    /**
     * Returns a JSON Object with allowed payment methods for button initialization and isReadyToPay.
     */
    private fun getBaseCardPaymentMethod(): JSONObject {
        return JSONObject().apply {
            put("type", "CARD")
            put("parameters", JSONObject().apply {
                put("allowedAuthMethods", JSONArray(GooglePayFragment.SUPPORTED_METHODS))
                put("allowedCardNetworks", JSONArray(GooglePayFragment.SUPPORTED_NETWORKS))
                // billingAddressRequired is optional
                put("billingAddressRequired", false)
            })
        }
    }

    /**
     * Returns a JSON string containing information about allowed payment methods for the Google Pay button.
     */
    @Throws(JSONException::class)
    fun getAllowedPaymentMethodsForButton(): String {
        return JSONArray().put(
            JSONObject().apply {
                put("type", "CARD")
                put("parameters", JSONObject().apply {
                    put("allowedAuthMethods", JSONArray(GooglePayFragment.SUPPORTED_METHODS))
                    put("allowedCardNetworks", JSONArray(GooglePayFragment.SUPPORTED_NETWORKS))
                })
            }
        ).toString()
    }

    /**
     * Builds a card payment method with tokenization specifications for payment requests.
     */
    private fun getCardPaymentMethod(): JSONObject {
        val cardPaymentMethod = getBaseCardPaymentMethod()
        cardPaymentMethod.put("tokenizationSpecification", JSONObject().apply {
            put("type", "PAYMENT_GATEWAY")
            put("parameters", JSONObject().apply {
                put("gateway", GooglePayFragment.PAYMENT_GATEWAY)
                put("gatewayMerchantId", GooglePayFragment.GATEWAY_MERCHANT_ID)
            })
        })
        return cardPaymentMethod
    }

    /**
     * Creates transaction info for the payment data request.
     */
    private fun getTransactionInfo(price: String): JSONObject {
        return JSONObject().apply {
            put("totalPrice", price)
            put("totalPriceStatus", "FINAL")
            put("currencyCode", GooglePayFragment.CURRENCY_CODE)
            put("countryCode", GooglePayFragment.COUNTRY_CODE)
        }
    }

    /**
     * Creates merchant info for the payment data request.
     */
    private fun getMerchantInfo(): JSONObject {
        return JSONObject().apply {
            put("merchantName", "Example Merchant")
            put("merchantId", "01234567890123456789") // Replace with your actual merchant ID
        }
    }
}
