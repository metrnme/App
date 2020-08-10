package com.example.mtrnme2.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.example.mtrnme2.R
import com.example.mtrnme2.databinding.FragmentPlayerBinding
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import com.example.mtrnme2.states.LikeState
import com.example.mtrnme2.states.PlayerState
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlayerFragment : BaseFragment() {
    private lateinit var binding: FragmentPlayerBinding
    private var currentPlayerState: PlayerState = PlayerState.STARTED
    private var mediaPlayer: MediaPlayer? = null
    var imageurl = ""
    var currentLikeState: LikeState = LikeState.Like



    companion object {
        fun getNewInstance(): PlayerFragment {
            return PlayerFragment()
        }

        private var globalMusicData: AllTrackResponseItem? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerBinding.inflate(inflater)
        binding.comments.isSelected = true
        return binding.root
    }

    fun getAllComments(id: Int): String {
        //   var listOfComments  = ArrayList<TrackCommentsItem>()
        var allComments: String = " "
        var TrackService: TrackService? = null
        TrackService = ServiceBuilder.buildTrackService()
        var getTrackComment: Call<TrackComments> =
            TrackService?.getTrackComments(trackID(track_id = id))!!
        getTrackComment.enqueue(
            object : Callback<TrackComments> {
                override fun onFailure(call: Call<TrackComments>, t: Throwable) {
                    Log.e("app:", "Error Occurred : ${t.message}")
                }

                override fun onResponse(
                    call: Call<TrackComments>,
                    response: Response<TrackComments>
                ) {

                    if (response.isSuccessful || response.body() != null) {
                        var responsebody: TrackComments = response.body()!!
                        Log.d(
                            "Comment",
                            "Response Body : $responsebody"
                        )
                        //Should get all Comments
                        var commentString = ""
                        for (i in responsebody) {
                            var x: String = i.content
                            var y: String = i.username
                            commentString += "- ${x}"
                            commentString += "@ ${y}"
                        }

                        if(commentString.isNullOrEmpty()){
                            binding.comments.text = "No Comments Available"
                        }else{
                            binding.comments.text = commentString
                            binding.comments.isSelected = true
                        }
                    }
                }
            }
        )
        return allComments
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        globalMusicData =
            Gson().fromJson(arguments?.getString("data"), AllTrackResponseItem::class.java)

        binding.title.text = globalMusicData?.name.toString()
        binding.tUsername.text = globalMusicData?.username.toString()
        var trackId = globalMusicData!!.track_id
        var imgKey = globalMusicData!!.image_url

        binding.likeTrack.isChecked = globalMusicData?.user_likes?.contains(appData.username)!! ?: false

        //var b: Boolean = lakes.contains(appData.username)


//

        var allComments: String = getAllComments(trackId) //ArrayList<TrackCommentsItem>

        //if below statement is true then show the heart button with red
        //globalMusicData!!.user_likes.contains(appData.username)


        Amplify.Storage.getUrl(imgKey,
            { result ->
                imageurl = result.url.toString()
                showLog(imageurl)

                imageurl = "https://lh3.googleusercontent.com/6UgEjh8Xuts4nwdWzTnWH8QtLuHqRMUB7dp24JYVE2xcYzq4HA8hFfcAbU-R-PC_9uA1=w288-h288-n-rw"
                /*Glide.with(this)
                    .load(imageurl) // image url
                    .placeholder(R.drawable.album_art_background) // any placeholder to load at start
                    .error(R.drawable.album_art_error)  // any image in case of error
                    .override(350, 350) // resizing
                    .into(binding.img);*/


            },
            { error -> Log.e("error", error.message) })


        var myGenre = globalMusicData!!.genre;
        var DisplayGenre = ""
        for (i in myGenre) {
            DisplayGenre = DisplayGenre + " " + i
        }


        Glide.with(binding.img.context)
                .load(imageurl) // image url
                .error(R.drawable.album_art_error)
                .centerCrop()
                .placeholder(R.drawable.album_art_background) // any placeholder to load at start / any image in case of error esizing
                .into(binding.img)
        var myInst = globalMusicData!!.inst_used;
        var DisplayInst = "";
        for (i in myInst) {
            DisplayInst = DisplayInst + " " + i
        }

        var track_url = "";
        Amplify.Storage.getUrl(globalMusicData!!.url.toString(),
            { result -> track_url = result.url.toString() },
            { error -> Log.e("error", error.message.toString()) })

        binding.inst.text = DisplayInst
        binding.genre.text = DisplayGenre

        Log.d("Comments", allComments)
        binding.comments.text = allComments

        binding.playTrack.setOnCheckedChangeListener { _, p1 ->
            binding.progressView.visibility = View.VISIBLE
            if (p1) {
                currentPlayerState = PlayerState.PLAYING
                mediaPlayer = MediaPlayer()

                if (track_url != "") {
                    mediaPlayer?.setDataSource(track_url)
                    mediaPlayer?.prepareAsync()
                    //mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer?.setOnPreparedListener { p0 ->
                        p0?.start()
                        binding.progressView.visibility = View.GONE
                    }
                }
            } else {
                binding.progressView.visibility = View.GONE
                currentPlayerState = PlayerState.PAUSED
                mediaPlayer?.pause()
            }
        }

        binding.stopTrack.setOnClickListener {
            currentPlayerState = PlayerState.STOPPED
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Media is null. Please provide",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

//        binding.likeTrack.setOnClickListener{
//            //Check the current state of the button lets suppose its 1
//            if(false){
//                unLike()
//            }else{
//                Like()
//            }
//        }

        binding.likeTrack.setOnCheckedChangeListener { p0, p1 ->

            if(p1){
                Like()
                currentLikeState = LikeState.Like

            }
            else{
                unLike()
                currentLikeState = LikeState.Unlike

            }
        }
        //Comment Track here
        binding.commentTrack.setOnClickListener{
            var Comment = "What a beautiful track @"+appData.username
            showAlertAndGetComment()

            postComment(Comment)
        }

    }

    private fun showAlertAndGetComment() {
        val builder = AlertDialog.Builder(context)
        val view = View.inflate(context, R.layout.comment_layout, null)

        builder.setTitle("Post Comment")
        builder.setCancelable(true)
        builder.setView(view)
        val commentText = view.findViewById<EditText>(R.id.comment_edit_text)

        builder.setPositiveButton("Submit Comment"
        ) { dialog, which ->
            if(commentText.text.isNullOrEmpty()){
                showToast("Please provide comment")
                return@setPositiveButton
            }

            postComment(commentText.text.toString())
            var allComments: String = getAllComments(globalMusicData?.track_id!!)
            binding.comments.text = allComments
            binding.comments.isSelected = true
            dialog.dismiss()
        }

        builder.create()
        builder.show()
    }

    fun postComment(Comment:String){
        //          Implement dialoag here and get comment and replace in network call
        var TrackService: TrackService? = null
        TrackService = ServiceBuilder.buildTrackService()
        var like: Call<GenericResponse> = TrackService!!.postComment(CommentModel(comment = Comment,track_id = globalMusicData!!.track_id,username = appData.username))
        like.enqueue(object : Callback<GenericResponse> {
            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("app:","Error Occurred : ${t.message}")
            }
            override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
            ) {

                // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: GenericResponse = response.body()!!
                    Log.e(
                            "Track Liked!",
                            "Response Body : $responsebody"
                    )
                }
            }})

    }


    fun unLike(){
        var TrackService: TrackService? = null
        TrackService = ServiceBuilder.buildTrackService()
        var unlike: Call<GenericResponse> = TrackService?.UnlikeaTrack(LikeModel(track_id = globalMusicData!!.track_id,username = appData.username))
        unlike.enqueue(object : Callback<GenericResponse> {
            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("app:","Error Occurred : ${t.message}")
            }
            override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
            ) {

                // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: GenericResponse = response.body()!!
                    Log.e(
                            "Track Unliked",
                            "Response Body : $responsebody"
                    )
                }
            }})

    }
    fun Like(){
        //Like the Track here
        var TrackService: TrackService? = null
        TrackService = ServiceBuilder.buildTrackService()
        var like: Call<GenericResponse> = TrackService?.LikeaTrack(LikeModel(track_id = globalMusicData!!.track_id,username = appData.username))
        like.enqueue(object : Callback<GenericResponse> {
            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("app:","Error Occurred : ${t.message}")
            }
            override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
            ) {

                // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: GenericResponse = response.body()!!
                    Log.e(
                            "Track Liked!",
                            "Response Body : $responsebody"
                    )
                }
            }})

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        if (globalMusicData!!.user_likes.contains(appData.username)){
//            currentLikeState = LikeState.Like
//        }else{
//            currentLikeState = LikeState.Unlike
//        }
//    }
}


