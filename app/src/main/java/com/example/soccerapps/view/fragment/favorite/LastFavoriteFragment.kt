package com.example.soccerapps.view.fragment.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccerapps.R
import com.example.soccerapps.db.EventDB
import com.example.soccerapps.db.database
import com.example.soccerapps.view.adapter.FavoriteAdapter
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * A simple [Fragment] subclass.
 */
class LastFavoriteFragment : Fragment() {

    private var lastFavorite: MutableList<EventDB> = mutableListOf()
    private lateinit var listFavorite: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    private var eventDB: EventDB? = null

    companion object {
        fun lastFavoriteInstance(): LastFavoriteFragment = LastFavoriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_last_favorite, container, false)
        listFavorite = view.findViewById(R.id.last_recycler_favorite)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listFavorite.layoutManager = layoutManager
        adapter = FavoriteAdapter(lastFavorite, context)
        listFavorite.adapter = adapter
    }

    private fun showFavorite() {
        lastFavorite.clear()
        context?.database?.use {
            val result = select(EventDB.TABLE_EVENT)
            val favorite = result.parseList(classParser<EventDB>())
            val sort = favorite.filter { it.homeScore != null }
            lastFavorite.addAll(sort)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }
}
