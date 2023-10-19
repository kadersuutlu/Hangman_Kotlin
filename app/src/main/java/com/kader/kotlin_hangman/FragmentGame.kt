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
import com.kader.kotlin_hangman.R

class FragmentGame : Fragment() {

    private var selectedWord: String? = null
    private lateinit var textView2: TextView
    private lateinit var ipucuTextView: TextView
    private lateinit var stepImage: ImageView


    private var remainingAttempts = 6

    val databaseReference = FirebaseDatabase.getInstance().reference.child("kelimeler")

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

        val gridView = view.findViewById<GridView>(R.id.gridView)
        val alphabet = listOf("A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z")

        val adapter = AlphabetAdapter(requireContext(), alphabet)
        gridView.adapter = adapter



        gridView.setOnItemClickListener { _, gridViewItem, _, _ ->
            val position: Int = gridView.getPositionForView(gridViewItem)

            val letter = alphabet.getOrNull(position)?.toUpperCase() // Harfi büyük harfe çevir

            if (letter != null) {
                if (selectedWord?.toUpperCase()?.contains(letter) == true) { // Hem harfi hem de kelimeyi büyük harfe çevir
                    updateHiddenWord(letter)
                    gridViewItem.setBackgroundResource(R.drawable.custom_success_background)
                    stepImage.setBackgroundResource(R.drawable.step1_icon)
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
        }
    }

    private fun getWrongImageResource(): Int {
        // Hatalı tıklamalara göre farklı drawable'lar döndür
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
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, failedFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

