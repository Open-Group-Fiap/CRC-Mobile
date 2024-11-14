package br.com.opengroup.crc.api

import com.google.firebase.firestore.FirebaseFirestore

fun logFirebase(mensagem: String, userEmail: String) {
    val bancoDados = FirebaseFirestore.getInstance()
    bancoDados.collection("Logs").document("$userEmail-${System.currentTimeMillis()}")
        .set(mapOf("mensagem" to mensagem))
}