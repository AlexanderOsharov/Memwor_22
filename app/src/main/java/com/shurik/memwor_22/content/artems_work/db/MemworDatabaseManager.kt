package com.example.kotlinprojecttest2.db

import android.util.Log
import com.example.kotlinprojecttest2.Domain
import com.example.kotlinprojecttest2.MemworViewModel
import com.example.kotlinprojecttest2.utils.Constants
import com.google.firebase.database.*
import com.shurik.memwor_22.content.artems_work.db.Community
import kotlinx.coroutines.*

class MemworDatabaseManager {

    private var db: DatabaseReference
    private var const: Constants = Constants()
    private val COMMUNITY_KEY: String = "Community"

    init{
        db = FirebaseDatabase.getInstance().getReference(COMMUNITY_KEY)
    }

//    fun getVklist() = runBlocking{
//        getVkDomains()
//    }


    fun addNewCommunity(platform: String, domain: String, name: String, category: String){
        val id = db.key.toString()
        val newCommunity = Community(id, platform, domain, name, category)
        db.push().setValue(newCommunity)
    }

    fun getVkDomains() {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch{
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var _vkData: MutableList<Domain> = ArrayList()
                    //if (vkData.size > 0) vkData.clear()
                    for (ds : DataSnapshot in dataSnapshot.getChildren()){
                        val community: Community? = ds.getValue(Community::class.java)
                        val domain = Domain()
                        if (community != null) {
                            community.category?.let { domain.category = it }
                            community.domain?.let { domain.domain = it }
                            community.name?.let { domain.name = it }
                            community.platform?.let { domain.platform = it }
                        }
                        if(!domain.name.isNullOrEmpty() || !domain.domain.isNullOrEmpty() || !domain.category.isNullOrEmpty() || !domain.platform.isNullOrEmpty() ){
                            _vkData.add(domain)
                        }
                    }
                    //Log.e("WITH LOVE FROM DB", _vkData.toString())
                    MemworViewModel.vkDomainsLiveData.setValueToVkDomains(_vkData)
                }
                //Log.e("SUCCESS DATABASE TAG", _vkData.toString())
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("DATABASE ERROR TAG", "Failed to read value.", error.toException())
                }
            })
        }

        //result.join()
        //Log.e("SUCCEED res DB", res.toString())
        //Log.e("SUCCEED vkData DB", vkData.toString())
    }



}
