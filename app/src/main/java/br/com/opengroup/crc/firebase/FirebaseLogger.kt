package br.com.opengroup.crc.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

fun logFirebase(mensagem: String, userEmail: String) {
    val bancoDados = FirebaseFirestore.getInstance()
    bancoDados.collection("Logs").document("$userEmail-${System.currentTimeMillis()}")
        .set(mapOf("mensagem" to mensagem))
}

fun logFirebaseApi(mensagem: String, userEmail: String) {
    logFirebase("API: $mensagem", userEmail)
    Log.d("API", mensagem)
}