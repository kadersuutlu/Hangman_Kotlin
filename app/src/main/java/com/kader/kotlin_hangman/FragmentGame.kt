import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kader.kotlin_hangman.AlphabetAdapter
import com.kader.kotlin_hangman.R
import java.io.BufferedReader
import java.io.InputStreamReader

class FragmentGame : Fragment() {

    private lateinit var wordList: List<String>
    private var selectedWord: String? = null
    private lateinit var textView2: TextView
    private lateinit var ipucuTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordList = getWordListFromRawFile(R.raw.wordlist)
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

        showRandomWord()

        val gridView = view.findViewById<GridView>(R.id.gridView)
        val alphabet = listOf("A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z")

        val adapter = AlphabetAdapter(requireContext(), alphabet)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, gridViewItem, _, _ ->
            val position: Int = gridView.getPositionForView(gridViewItem)

            val letter = alphabet.getOrNull(position)

            if (letter != null) {
                if (selectedWord?.contains(letter, ignoreCase = true) == true) {
                    updateHiddenWord(letter)
                    gridViewItem.setBackgroundResource(R.drawable.custom_success_background)
                } else {
                    gridViewItem.setBackgroundResource(R.drawable.custom_failed_background)
                }
            } else {
                Log.e("FragmentGame", "Invalid position: $position")
            }
        }




    }

    private fun showRandomWord() {
        selectedWord = wordList.random()

        Log.d("FragmentGame", "Selected word: $selectedWord")

        textView2.let {
            if (selectedWord != null) {
                val tanim = selectedWord!!.split(" to ", limit = 2).firstOrNull()?.trimEnd('.', ',')
                val temizlenmisTanim = tanim?.replace(Regex("[^A-Za-z0-9ğüşıöçİĞÜŞÖÇ\\s]"), "")
                val hiddenWord = temizlenmisTanim?.map { if (it.isWhitespace()) " " else "_" }?.joinToString(" ") ?: ""
                it.text = hiddenWord
                Log.d("FragmentGame", "textView2 content: $hiddenWord")
            } else {
                Log.e("FragmentGame", "Selected word is null")
            }
        } ?: Log.e("FragmentGame", "textView2 is null")


        // Tanımı temizle ve İpucu olarak ekle
        val temizlenmisTanim = selectedWord?.split(" to ", limit = 2)?.get(1)?.replace(Regex("[^a-zA-Z0-9üÜğĞıİöÖçÇşŞ\\s]"), "")?.trim()
        view?.findViewById<TextView>(R.id.textView)?.text = "İpucu: $temizlenmisTanim"
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

    private fun getWordListFromRawFile(resourceId: Int): List<String> {
        val wordList = mutableListOf<String>()

        try {
            val inputStream = resources.openRawResource(resourceId)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            bufferedReader.useLines { lines ->
                lines.forEach {
                    wordList.add(it)
                }
            }
        } catch (e: Exception) {
            Log.e("FragmentGame", "Error reading wordlist file", e)
        }

        return wordList
    }
}
