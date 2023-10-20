import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kader.kotlin_hangman.AlphabetAdapter
import com.kader.kotlin_hangman.FragmentFailed
import com.kader.kotlin_hangman.FragmentSuccess
import com.kader.kotlin_hangman.R

class FragmentGame : Fragment() {

    private var selectedWord: String? = null
    private lateinit var textView2: TextView
    private lateinit var ipucuTextView: TextView
    private lateinit var stepImage: ImageView
    private lateinit var heart1: ImageView
    private lateinit var heart2: ImageView
    private lateinit var heart3: ImageView
    private lateinit var heart4: ImageView
    private lateinit var heart5: ImageView
    private lateinit var heart6: ImageView

    val emptyHeartResource = R.drawable.heart
    val filledHeartResource = R.drawable.heart_del

    private var remainingAttempts = 6

    private var score = 0

    val databaseReference = FirebaseDatabase.getInstance().reference.child("kelimeler")

    private val selectedLetters = mutableSetOf<String>()

    private lateinit var levelText: TextView
    private var currentLevel = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Burada textView2 ve ipucuTextView'yi bulun
        textView2 = view.findViewById(R.id.textView2)
        ipucuTextView = view.findViewById(R.id.textView)
        stepImage = view.findViewById(R.id.stepImage)

        levelText = view.findViewById(R.id.levetText)

        // Initialize heart ImageViews
        heart1 = view.findViewById(R.id.heart1)
        heart2 = view.findViewById(R.id.heart2)
        heart3 = view.findViewById(R.id.heart3)
        heart4 = view.findViewById(R.id.heart4)
        heart5 = view.findViewById(R.id.heart5)
        heart6 = view.findViewById(R.id.heart6)

        heart1.setImageResource(emptyHeartResource)
        heart2.setImageResource(emptyHeartResource)
        heart3.setImageResource(emptyHeartResource)
        heart4.setImageResource(emptyHeartResource)
        heart5.setImageResource(emptyHeartResource)
        heart6.setImageResource(emptyHeartResource)


        val gridView = view.findViewById<GridView>(R.id.gridView)
        val alphabet = listOf("A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z")

        val adapter = AlphabetAdapter(requireContext(), alphabet)
        gridView.adapter = adapter



        gridView.setOnItemClickListener { _, gridViewItem, _, _ ->
            val position: Int = gridView.getPositionForView(gridViewItem)

            val letter = alphabet.getOrNull(position)?.toUpperCase() // Harfi büyük harfe çevir

            if (letter != null && !selectedLetters.contains(letter)) {
                selectedLetters.add(letter)

                if (selectedWord?.toUpperCase()?.contains(letter) == true) { // Hem harfi hem de kelimeyi büyük harfe çevir
                    updateHiddenWord(letter)
                    gridViewItem.setBackgroundResource(R.drawable.custom_success_background)
                    if(!textView2.text.contains("_")){
                        currentLevel++
                        updateLevel()
                    }
                } else {
                    remainingAttempts--
                    gridViewItem.setBackgroundResource(R.drawable.custom_failed_background)
                    stepImage.setImageResource(getWrongImageResource())

                    if (remainingAttempts == 0) {
                        showFailedFragment()
                    }
                }
            } else {
                Log.e("FragmentGame", "Invalid position: $position")
            }
        }



        // Veritabanından kelime çekme
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Veritabanındaki rastgele bir kelimeyi seçme
                    val randomIndex = (0 until dataSnapshot.childrenCount).random()
                    val randomWord = dataSnapshot.children.elementAt(randomIndex.toInt())

                    // Kelime ve açıklamayı alıp TextView'lere atama
                    selectedWord = randomWord.child("kelime").getValue(String::class.java)
                    val aciklama = randomWord.child("aciklama").getValue(String::class.java)

                    // TextView'leri güncelleme
                    textView2.text = "_ ".repeat(selectedWord?.length ?: 0)
                    ipucuTextView.text = "İpucu: $aciklama"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Hata durumunda yapılacak işlemler
                println("Veritabanı okuma hatası: ${databaseError.message}")
            }
        })
    }

    private fun updateHiddenWord(letter: String) {
        val updatedWord = StringBuilder(textView2?.text.toString())

        val indicesToUpdate = mutableListOf<Int>()

        selectedWord?.indices?.forEach { index ->
            if (selectedWord?.get(index).toString().equals(letter, ignoreCase = true)) {
                indicesToUpdate.add(index)
            }
        }

        if (indicesToUpdate.isNotEmpty()) {
            indicesToUpdate.forEach { index ->
                if (index * 2 < updatedWord.length) {
                    updatedWord.setCharAt(index * 2, letter[0])
                } else {
                    Log.e("FragmentGame", "Invalid index: $index, updatedWord length: ${updatedWord.length}")
                }
            }

            textView2?.text = updatedWord.toString()
            Log.d("FragmentGame", "Updated word: $updatedWord")

            if (updatedWord.indexOf('_') == -1) {
                showSuccessFragment()
                score += 10
                updateScoreInFragment()
            }else{
                score+=5
                updateScoreInFragment()
            }
        }
    }

    private fun getWrongImageResource(): Int {
        // Hatalı tıklamalara göre farklı drawable'lar döndür
        when (remainingAttempts) {
            5 -> {
                // Kalp görselini doldurulan haliyle değiştir
                heart6.setImageResource(filledHeartResource)
                score -= 2
            }
            4 -> {
                // Kalp görselini doldurulan haliyle değiştir
                heart5.setImageResource(filledHeartResource)
                score -= 2
            }
            3 -> {
                // Kalp görselini doldurulan haliyle değiştir
                heart4.setImageResource(filledHeartResource)
                score -= 2
            }
            2 -> {
                // Kalp görselini doldurulan haliyle değiştir
                heart3.setImageResource(filledHeartResource)
                score -= 2
            }
            1 -> {
                // Kalp görselini doldurulan haliyle değiştir
                heart2.setImageResource(filledHeartResource)
                score -= 2
            }
            0 -> {
                heart1.setImageResource(filledHeartResource)
                showFailedFragment()
                score -= 2
            }
        }

        if (remainingAttempts == 0) {
            // Hakkımız bittiğinde failed fragment'ı göster ve puanı azalt
            showFailedFragment()
            score -= 2
            updateScoreInFragment()
        }

        // Hatalı tıklamalara göre farklı drawable döndür
        return when (remainingAttempts) {
            5 -> R.drawable.step2_icon
            4 -> R.drawable.step3_icon
            3 -> R.drawable.step4_icon
            2 -> R.drawable.step5_icon
            1 -> R.drawable.step6_icon
            0 -> R.drawable.step7_icon
            else -> R.drawable.step1_icon
        }
    }

    private fun showFailedFragment() {
        // Hakkımız bittiğinde failed fragment'ı göster
        val failedFragment = FragmentFailed()
        val bundle = Bundle()
        bundle.putString("correctWord", selectedWord)
        failedFragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, failedFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showSuccessFragment() {
        val successFragment = FragmentSuccess()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, successFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun updateScoreInFragment() {
        // Eğer FragmentSuccess veya FragmentFailed fragment'ı görüntüleniyorsa puanı güncelle
        val successFragment = requireActivity().supportFragmentManager.findFragmentByTag("FragmentSuccess")
        val failedFragment = requireActivity().supportFragmentManager.findFragmentByTag("FragmentFailed")

        if (successFragment != null) {
            successFragment.view?.findViewById<TextView>(R.id.textView5)?.text = "Score: $score"
        } else if (failedFragment != null) {
            failedFragment.view?.findViewById<TextView>(R.id.textView5)?.text = "Score: $score"
        }

        Log.d("FragmentGame", "Score: $score")
    }


    private fun updateLevel() {
        levelText.text = currentLevel.toString()
    }
}

