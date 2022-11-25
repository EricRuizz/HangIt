package com.example.hangit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hangit.databinding.ActivityParticlesBinding
import com.example.hangit.particles.particle
import com.example.hangit.particles.particle.Family.*
import com.google.firebase.firestore.FirebaseFirestore

class ParticlesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParticlesBinding
    private lateinit var firestore: FirebaseFirestore

    private val adapter = ParticlesAdapter(this)
    val  PARTICLES_COLLECTION = "particles"

    private var particles = arrayListOf<particle>().apply {
        add(particle("Quark Up", QUARK, 2200.0, "2/3", "1/2"))
        add(particle("Quark Charm", QUARK, 1280.0, "2/3", "1/2"))
        add(particle("Quark Top", QUARK, 173100.0, "2/3", "1/2"))

        add(particle("Quark Down", QUARK, 4.7, "-1/3", "1/2"))
        add(particle("Quark Strange", QUARK, 96.0, "-1/3", "1/2"))
        add(particle("Quark Bottom", QUARK, 4.18, "-1/3", "1/2"))

        add(particle("Electron", LEPTON, 0.511, "-1", "1/2"))
        add(particle("Muon", LEPTON, 105.66, "-1", "1/2"))
        add(particle("Tau", LEPTON, 1776.8, "-1", "1/2"))

        add(particle("Electron neutrino", LEPTON, 1.0, "0", "1/2"))
        add(particle("Muon neutrino", LEPTON, 0.17, "0", "1/2"))
        add(particle("Tau neutrino", LEPTON, 18.2, "0", "1/2"))

        add(particle("Gluon", GAUGE_BOSON, 0.0, "0", "1"))
        add(particle("Photon", GAUGE_BOSON, 0.0, "0", "1"))
        add(particle("Z boson", GAUGE_BOSON, 91190.0, "0", "1"))
        add(particle("W boson", GAUGE_BOSON, 80930.0, "±1", "1"))

        add(particle("Higgs", SCALAR_BOSON, 124970.0, "0", "0"))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        FirebaseFirestore.setLoggingEnabled(true);

        val collection = firestore.collection(PARTICLES_COLLECTION)
        collection.get().addOnSuccessListener {
             particles = it.documents.map{ dbParticle ->
                dbParticle.toObject(particle::class.java)
            }.filterNotNull() as ArrayList<particle>

            adapter.updateParticesList(particles)
        }

        binding.particlesRecyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()

        val collection = firestore.collection(PARTICLES_COLLECTION)

        particles.forEach{
            collection.document(it.name).set(it).addOnSuccessListener {
                Toast.makeText(
                    this,
                   "he entrat",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    getString(R.string.error_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}