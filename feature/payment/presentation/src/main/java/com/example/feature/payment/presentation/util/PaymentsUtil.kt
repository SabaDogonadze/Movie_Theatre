package com.example.feature.payment.presentation.util

import com.example.feature.payment.presentation.screen.payment.GooglePayFragment
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object PaymentsUtil {


    fun getIsReadyToPayRequest(): JSONObject {
        return JSONObject().apply {
            put("apiVersion", 2)
            put("apiVersionMinor", 0)
            put("allowedPaymentMethods", JSONArray().apply {
                put(getBaseCardPaymentMethod())
            })
        }
    }


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


    private fun getTransactionInfo(price: String): JSONObject {
        return JSONObject().apply {
            put("totalPrice", price)
            put("totalPriceStatus", "FINAL")
            put("currencyCode", GooglePayFragment.CURRENCY_CODE)
            put("countryCode", GooglePayFragment.COUNTRY_CODE)
        }
    }

    private fun getMerchantInfo(): JSONObject {
        return JSONObject().apply {
            put("merchantName", "Example Merchant")
            put("merchantId", "01234567890123456789")
        }
    }
}
