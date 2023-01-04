package com.example.hangit


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import com.example.hangit.databinding.ActivityGameBinding
import com.example.hangit.hangman.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.*
import kotlin.concurrent.timerTask

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameInfo: ResponseCreateGame
    private lateinit var letterInfo: ResponseGuessLetter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var hintInfo: ResponseHint
    private lateinit var solutionInfo: ResponseSolution
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    private var failGuess: Int = 0
    private val MAX_ERRORS: Int = 10
    private var gameOver: Boolean = false
    private var hasSeenAd: Boolean = false
    private var notificationOn: Boolean = false
    private var soundOn: Boolean = true
    private var ad: InterstitialAd? = null
    private var correctWords: Int = 0

    private lateinit var mp: MediaPlayer
    private lateinit var sp: MediaPlayer

    //private lateinit var timer: Timer
    private lateinit var timer: CountDownTimer
    private var millisLeft: Long = 0

    lateinit var shared: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private var pausedGame: Boolean = false

    companion object {
        const val CHANNEL_ID = "NOTIFICATIONS_CHANNEL_GAME"
        var score: Long = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseAnalytics.logEvent(
            FirebaseAnalytics.Event.LEVEL_START,
            bundleOf(
                FirebaseAnalytics.Param.LEVEL to correctWords
            )
        )

        score = 0

        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.scoreText.text = score.toString()

        shared = PreferenceManager.getDefaultSharedPreferences(this)
        editor = shared.edit()


        if (shared.getBoolean("audioOn", soundOn)) {
            mp = MediaPlayer.create(this@GameActivity, R.raw.lletra_correcta)
            //mp.start()
            //mp.stop()

            sp = MediaPlayer.create(this@GameActivity, R.raw.song)
            sp.start()
            sp.isLooping = true
        }

        //Create channel notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    GameActivity.CHANNEL_ID,
                    "VictoryNotifications",
                    NotificationManager.IMPORTANCE_LOW
                )

            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        //Load ad
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this@GameActivity,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    ad = null
                }

                override fun onAdLoaded(loadedAd: InterstitialAd) {
                    ad = loadedAd
                }
            })

        supportActionBar?.hide()
        binding.pauseMenu.isActivated = false
        binding.pauseMenu.setVisibility(View.GONE)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://hangman.enti.cat:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        createGame(retrofit)

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timeText.text = (millisUntilFinished / 1000).toString()
                millisLeft = millisUntilFinished
            }

            override fun onFinish() {
                if (shared.getBoolean("audioOn", soundOn)) {
                    if(mp.isPlaying)
                        mp.stop()
                    sp.stop()
                    mp = MediaPlayer.create(this@GameActivity, R.raw.you_lose)
                    mp.start()
                }

                //Wait 2 sec and go to the Lost Screen
                Handler().postDelayed(
                    {
                        val intent =
                            Intent(this@GameActivity, YouLoseActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, 2000
                )
            }
        }
        timer.start()

        //Player guessed letter A
        binding.letterA.setOnClickListener {
            guessLetter(retrofit, "A", binding.letterA)
        }

        //Player guessed letter B
        binding.letterB.setOnClickListener {
            guessLetter(retrofit, "B", binding.letterB)
        }

        //Player guessed letter C
        binding.letterC.setOnClickListener {
            guessLetter(retrofit, "C", binding.letterC)
        }

        //Player guessed letter D
        binding.letterD.setOnClickListener {
            guessLetter(retrofit, "D", binding.letterD)
        }

        //Player guessed letter E
        binding.letterE.setOnClickListener {
            guessLetter(retrofit, "E", binding.letterE)
        }

        //Player guessed letter F
        binding.letterF.setOnClickListener {
            guessLetter(retrofit, "F", binding.letterF)
        }

        //Player guessed letter G
        binding.letterG.setOnClickListener {
            guessLetter(retrofit, "G", binding.letterG)
        }

        //Player guessed letter H
        binding.letterH.setOnClickListener {
            guessLetter(retrofit, "H", binding.letterH)
        }

        //Player guessed letter I
        binding.letterI.setOnClickListener {
            guessLetter(retrofit, "I", binding.letterI)
        }

        //Player guessed letter J
        binding.letterJ.setOnClickListener {
            guessLetter(retrofit, "J", binding.letterJ)
        }

        //Player guessed letter K
        binding.letterK.setOnClickListener {
            guessLetter(retrofit, "K", binding.letterK)
        }

        //Player guessed letter L
        binding.letterL.setOnClickListener {
            guessLetter(retrofit, "L", binding.letterL)
        }

        //Player guessed letter M
        binding.letterM.setOnClickListener {
            guessLetter(retrofit, "M", binding.letterM)
        }

        //Player guessed letter N
        binding.letterN.setOnClickListener {
            guessLetter(retrofit, "N", binding.letterN)
        }

        //Player guessed letter O
        binding.letterO.setOnClickListener {
            guessLetter(retrofit, "O", binding.letterO)
        }

        //Player guessed letter P
        binding.letterP.setOnClickListener {
            guessLetter(retrofit, "P", binding.letterP)
        }

        //Player guessed letter Q
        binding.letterQ.setOnClickListener {
            guessLetter(retrofit, "Q", binding.letterQ)
        }

        //Player guessed letter R
        binding.letterR.setOnClickListener {
            guessLetter(retrofit, "R", binding.letterR)
        }

        //Player guessed letter S
        binding.letterS.setOnClickListener {
            guessLetter(retrofit, "S", binding.letterS)
        }

        //Player guessed letter T
        binding.letterT.setOnClickListener {
            guessLetter(retrofit, "T", binding.letterT)
        }

        //Player guessed letter U
        binding.letterU.setOnClickListener {
            guessLetter(retrofit, "U", binding.letterU)
        }

        //Player guessed letter V
        binding.letterV.setOnClickListener {
            guessLetter(retrofit, "V", binding.letterV)
        }

        //Player guessed letter W
        binding.letterW.setOnClickListener {
            guessLetter(retrofit, "W", binding.letterW)
        }

        //Player guessed letter X
        binding.letterX.setOnClickListener {
            guessLetter(retrofit, "X", binding.letterX)
        }

        //Player guessed letter Y
        binding.letterY.setOnClickListener {
            guessLetter(retrofit, "Y", binding.letterY)
        }

        //Player guessed letter Z
        binding.letterZ.setOnClickListener {
            guessLetter(retrofit, "Z", binding.letterZ)
        }

        //Player guessed letter SPACE
        binding.letterSpace.setOnClickListener {
            guessLetter(retrofit, " ", binding.letterSpace)
        }

        //Go to the pause screen
        binding.pauseButtonGame.setOnClickListener {
            binding.root.forEach { it ->
                if (it is Button) {
                    it.isActivated = false
                    it.setVisibility(View.GONE)
                }
            }
            binding.pauseMenu.isActivated = true
            binding.pauseMenu.setVisibility(View.VISIBLE)

            timer.cancel()
        }

        //Restart game
        binding.replayPauseButton.setOnClickListener {
            binding.pauseMenu.isActivated = false
            binding.pauseMenu.setVisibility(View.GONE)
            it.setVisibility(View.VISIBLE)
            val intent = Intent(this@GameActivity, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Go back to main
        binding.goBackPauseButton.setOnClickListener {
            binding.pauseMenu.isActivated = false
            binding.pauseMenu.setVisibility(View.GONE)
            it.setVisibility(View.VISIBLE)
            val intent = Intent(this@GameActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Continue game
        binding.continuePauseButton.setOnClickListener {
            binding.pauseMenu.isActivated = false
            binding.pauseMenu.setVisibility(View.GONE)
            binding.root.forEach { it ->
                if (it is Button) {
                    it.isActivated = true
                    it.setVisibility(View.VISIBLE)
                }

                timer = object : CountDownTimer(millisLeft, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.timeText.text = (millisUntilFinished / 1000).toString()
                        millisLeft = millisUntilFinished
                    }

                    override fun onFinish() {
                        if (shared.getBoolean("audioOn", soundOn)) {
                            if(mp.isPlaying)
                                mp.stop()
                            mp = MediaPlayer.create(this@GameActivity, R.raw.you_lose)
                            sp.stop()
                            mp.start()
                        }

                        //Wait 2 sec and go to the Lost Screen
                        Handler().postDelayed(
                            {
                                val intent =
                                    Intent(this@GameActivity, YouLoseActivity::class.java)
                                startActivity(intent)
                                finish()
                            }, 2000
                        )
                    }
                }
                timer.start()
            }
        }
    }


    //API Hangman
    fun createGame(retrofit: Retrofit) {

        createLetters()

        val call = retrofit.create(ApiHangman::class.java)
        call.createGame().enqueue(object : Callback<ResponseCreateGame> {
            override fun onResponse(
                call: Call<ResponseCreateGame>,
                response: Response<ResponseCreateGame>
            ) {
                //Save the game info and check if null
                gameInfo = response.body() ?: ResponseCreateGame("", "")
                binding.word.text = gameInfo.hangman

                if (gameInfo.hangman == "") {
                    Toast.makeText(
                        this@GameActivity,
                        getString(R.string.error_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                getSolution(retrofit)
                gameOver = false

            }

            override fun onFailure(call: Call<ResponseCreateGame>, t: Throwable) {
                Toast.makeText(
                    this@GameActivity,
                    getString(R.string.error_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    //User enters a letter
    fun guessLetter(retrofit: Retrofit, letter: String, button: Button) {
        val call = retrofit.create(ApiHangman::class.java)
        call.guessLetter(letter, gameInfo.token)
            .enqueue(object : Callback<ResponseGuessLetter> {
                override fun onResponse(
                    call: Call<ResponseGuessLetter>,
                    response: Response<ResponseGuessLetter>
                ) {
                    if (!gameOver) {
                        //Save the letter info and check if null
                        letterInfo = response.body() ?: ResponseGuessLetter("", "", false)
                        gameInfo.token = letterInfo.token
                        gameInfo.hangman = letterInfo.hangman

                        if (response.body()?.correct == false) {
                            //Decrease score
                            score -= 50
                            if (score < 0) score = 0
                            binding.scoreText.text = score.toString()

                            // "tree animation" plays and sound
                            binding.treeRope.scaleY += 0.2f
                            binding.treeRope.y += 5.5f
                            binding.treeWheel.y += 10.5f

                            if (shared.getBoolean("audioOn", soundOn)) {
                                if(mp.isPlaying)
                                    mp.stop()

                                mp = MediaPlayer.create(this@GameActivity, R.raw.lletra_mal);
                                mp.start();
                            }

                            //Updated the number of errors and check if user lost
                            failGuess++
                            if (failGuess >= MAX_ERRORS) {

                                if (!hasSeenAd) {
                                    timer.cancel()
                                    hasSeenAd = true
                                    binding.adMenu.setVisibility(View.VISIBLE)
                                    binding.root.forEach { it ->
                                        if (it is Button) {
                                            it.isActivated = false
                                            it.setVisibility(View.GONE)
                                        }
                                    }
                                    binding.acceptAdButton.setOnClickListener {

                                        firebaseAnalytics.logEvent(
                                            FirebaseAnalytics.Event.NEW_CHANCE,
                                            bundleOf(
                                                FirebaseAnalytics.Param.VIEW_AD to true
                                            )
                                        )

                                        ad?.fullScreenContentCallback =
                                            object : FullScreenContentCallback() {
                                                override fun onAdShowedFullScreenContent() {
                                                    timer = object : CountDownTimer(millisLeft, 1000) {
                                                        override fun onTick(millisUntilFinished: Long) {
                                                            binding.timeText.text = (millisUntilFinished / 1000).toString()
                                                            millisLeft = millisUntilFinished
                                                        }

                                                        override fun onFinish() {
                                                            if (shared.getBoolean("audioOn", soundOn)) {
                                                                if(mp.isPlaying)
                                                                    mp.stop()
                                                                mp = MediaPlayer.create(this@GameActivity, R.raw.you_lose)
                                                                sp.stop()
                                                                mp.start()
                                                            }

                                                            //Wait 2 sec and go to the Lost Screen
                                                            Handler().postDelayed(
                                                                {
                                                                    val intent =
                                                                        Intent(this@GameActivity, YouLoseActivity::class.java)
                                                                    startActivity(intent)
                                                                    finish()
                                                                }, 2000
                                                            )
                                                        }
                                                    }
                                                    timer.start()

                                                    ad = null
                                                    //failGuess--
                                                    binding.treeRope.scaleY -= 0.2f
                                                    binding.treeRope.y -= 5.5f
                                                    binding.treeWheel.y -= 10.5f
                                                    binding.adMenu.setVisibility(View.GONE)
                                                    binding.root.forEach { it ->
                                                        if (it is Button) {
                                                            it.isActivated = true
                                                            it.setVisibility(View.VISIBLE)
                                                        }
                                                    }

                                                    firebaseAnalytics.logEvent(
                                                        FirebaseAnalytics.Event.SHOW_AD,
                                                        bundleOf(
                                                            FirebaseAnalytics.Param.LEVEL to correctWords
                                                        )
                                                    )
                                                }
                                            }
                                        ad?.apply {
                                            show(this@GameActivity)
                                        }
                                    }
                                    binding.notAcceptAdButton.setOnClickListener {
                                        gameOver = true
                                        binding.adMenu.setVisibility(View.GONE)
                                        binding.root.forEach { it ->
                                            if (it is Button) {
                                                it.isActivated = true
                                                it.setVisibility(View.VISIBLE)
                                            }
                                        }

                                        if (gameOver) {
                                            getSolution(retrofit)

                                            if (shared.getBoolean("audioOn", soundOn)) {
                                                if(mp.isPlaying)
                                                    mp.stop()
                                                mp = MediaPlayer.create(this@GameActivity, R.raw.you_lose)
                                                sp.stop()
                                                mp.start()
                                            }

                                            //Wait 2 sec and go to the Lost Screen
                                            Handler().postDelayed(
                                                {
                                                    val intent =
                                                        Intent(
                                                            this@GameActivity,
                                                            YouLoseActivity::class.java
                                                        )
                                                    startActivity(intent)
                                                    finish()
                                                }, 2000
                                            )
                                        }
                                    }
                                }
                                else
                                {
                                    timer.cancel()
                                    gameOver = true
                                }
                                if (gameOver) {
                                    timer.cancel()
                                    getSolution(retrofit)


                                    if (shared.getBoolean("audioOn", soundOn)) {
                                        if(mp.isPlaying)
                                            mp.stop()
                                        mp = MediaPlayer.create(this@GameActivity, R.raw.you_lose)
                                        sp.stop()
                                        mp.start()
                                    }

                                    //Wait 2 sec and go to the Lost Screen
                                    Handler().postDelayed(
                                        {
                                            val intent =
                                                Intent(
                                                    this@GameActivity,
                                                    YouLoseActivity::class.java
                                                )
                                            startActivity(intent)
                                            finish()
                                        }, 2000
                                    )
                                }
                            }


                        } else {
                            //Add score
                            score += (15 * (millisLeft / 1000).toInt())
                            binding.scoreText.text = score.toString()

                            //Add sound
                            if (shared.getBoolean("audioOn", soundOn)) {
                                if(mp.isPlaying)
                                    mp.stop()
                                mp = MediaPlayer.create(this@GameActivity, R.raw.lletra_correcta)
                                mp.start()
                            }

                            //Add letter to the solution
                            binding.word.text = letterInfo.hangman

                            //Check  if user has won
                            if (letterInfo.hangman == solutionInfo.solution) {
                                correctWords++
                                if (shared.getBoolean("audioOn", soundOn)) {
                                    if(mp.isPlaying)
                                        mp.stop()
                                    mp = MediaPlayer.create(this@GameActivity, R.raw.you_win)
                                    sp.stop()
                                    mp.start()
                                }

                                if (shared.getBoolean("notificationOn", notificationOn)) {
                                    val builder = NotificationCompat.Builder(
                                        this@GameActivity,
                                        GameActivity.CHANNEL_ID
                                    )
                                        .setSmallIcon(R.drawable.gametree)
                                        .setContentTitle("Hang It!")
                                        .setContentText("CONGRATS YOU WIN 🥵 ").build()

                                    with(NotificationManagerCompat.from(this@GameActivity)) {
                                        notify(System.currentTimeMillis().toInt(), builder)
                                    }
                                }

                                timer.cancel()
                                gameOver = true



                                //Wait 2 sec and go to the Win Screen
                                Handler().postDelayed(
                                    {

                                        val intent =
                                            Intent(this@GameActivity, YouWinActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, 2000
                                )
                            }
                        }

                        //We discard the letter
                        button.background.alpha = 0
                        button.text = " "
                        button.foreground.alpha = 80

                        //The user can not guess a letter that has been guessed before
                        button.isClickable = false

                    }
                }

                override fun onFailure(call: Call<ResponseGuessLetter>, t: Throwable) {
                    Toast.makeText(
                        this@GameActivity,
                        getString(R.string.error_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    //User can ask for a hint (not implemented now)
    fun getHint(retrofit: Retrofit) {
        val call = retrofit.create(ApiHangman::class.java)
        call.getHint(gameInfo.token).enqueue(object : Callback<ResponseHint> {
            override fun onResponse(call: Call<ResponseHint>, response: Response<ResponseHint>) {
                //Save the game info and check if null
                hintInfo = response.body() ?: ResponseHint("", "")
                gameInfo.token = hintInfo.token
                //guessLetter(retrofit, hintInfo.letter, button)
            }

            override fun onFailure(call: Call<ResponseHint>, t: Throwable) {
                Toast.makeText(
                    this@GameActivity,
                    getString(R.string.error_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    //Get the solution: the word
    fun getSolution(retrofit: Retrofit) {
        val call = retrofit.create(ApiHangman::class.java)
        call.getSolution(gameInfo.token).enqueue(object : Callback<ResponseSolution> {
            override fun onResponse(
                call: Call<ResponseSolution>,
                response: Response<ResponseSolution>
            ) {
                solutionInfo = response.body() ?: ResponseSolution("", "")
                gameInfo.token = solutionInfo.token

                if (failGuess >= MAX_ERRORS) {
                    gameInfo.hangman = solutionInfo.solution
                    binding.word.text = gameInfo.hangman
                }

            }

            override fun onFailure(call: Call<ResponseSolution>, t: Throwable) {
                Toast.makeText(
                    this@GameActivity,
                    getString(R.string.error_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun createLetters() {
        //We set the letter parameters
        binding.root.forEach { it ->
            if (it is Button) {
                if (it.text == "Button") {
                    it.background.alpha = 0
                    it.text = " "
                    it.foreground.alpha = 255
                    it.isClickable = true
                    it.setVisibility(View.VISIBLE)
                    it.isActivated = true
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
        sp.pause()

        pausedGame = true
    }

    override fun onResume() {
        super.onResume()
        if(pausedGame) {
            pausedGame = false
            timer = object : CountDownTimer(millisLeft, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.timeText.text = (millisUntilFinished / 1000).toString()
                    millisLeft = millisUntilFinished
                }

                override fun onFinish() {
                    if (shared.getBoolean("audioOn", soundOn)) {
                    if(mp.isPlaying)
                        mp.stop()
                    mp = MediaPlayer.create(this@GameActivity, R.raw.you_lose)
                    sp.stop()
                    mp.start()
                }

                    Handler().postDelayed(
                        {
                            val intent =
                                Intent(this@GameActivity, YouLoseActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 2000
                    )
                }
            }
            timer.start()
            sp.start()
        }
    }
}