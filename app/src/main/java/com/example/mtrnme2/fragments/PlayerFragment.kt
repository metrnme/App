package com.example.mtrnme2.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mtrnme2.R
import com.example.mtrnme2.adapters.UsersAdapter
import com.example.mtrnme2.databinding.FragmentPlayerBinding
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import com.example.mtrnme2.services.UserService
import com.example.mtrnme2.states.LikeState
import com.example.mtrnme2.states.PlayerState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer

class PlayerFragment : BaseFragment() {
    private lateinit var binding: FragmentPlayerBinding
    private var currentPlayerState: PlayerState = PlayerState.STARTED
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()
    private var pause:Boolean = false

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

                        if(TextUtils.isEmpty(commentString)){
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
        binding.collab.setOnClickListener {
            var listOfUsers = mutableListOf<AllUserResponseItem>()
            var UserService: UserService? = null
            UserService = ServiceBuilder.buildservice()
            var getUsers: Call<AllUserResponseItem> = UserService?.getUser(userName(username = globalMusicData!!.username))!!
            getUsers.enqueue(object : Callback<AllUserResponseItem> {
                override fun onFailure(call: Call<AllUserResponseItem>, t: Throwable) {
                    Log.e("User Err", "Error Occurred : ${t.message}")
                }

                override fun onResponse(
                    call: Call<AllUserResponseItem>,
                    response: Response<AllUserResponseItem>
                ) {

                    // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                    if (response.isSuccessful || response.body() != null) {
                        var responsebody: AllUserResponseItem = response.body()!!
                        Log.e(
                            "app:User Info Response",
                            "Response Body : $responsebody"
                        )
                        //Should get all Instruments from here
                        var navigator = findNavController()
                        assert(navigator!=null)
                        var bundle = Bundle()
                        bundle.putString("data", Gson().toJson(responsebody, AllUserResponseItem::class.java))
                        navigator.navigate(R.id.nav_profile, bundle)
                    }
                }
            })
        }


//

        var allComments: String = getAllComments(trackId) //ArrayList<TrackCommentsItem>

        //if below statement is true then show the heart button with red
        //globalMusicData!!.user_likes.contains(appData.username)
        //imageurl = "https://infinitiliveaboard.com/public/images/infinity_intro.jpg"
        Amplify.Storage.getUrl(imgKey,
            { result ->
                showLog("RESULT:" + result.url.toString())
                imageurl=result.url.toString()
                activity?.runOnUiThread {
                    Glide.with(context ?: requireContext())
                        .load(imageurl) // image url
                        .error(R.drawable.album_art_error)
                        .centerCrop()
                        .placeholder(R.drawable.album_art_background)
                        .into(binding.img)
                }


                var echo = 1+1
            },
            { error -> Log.e("Glide", error.message) })

        Log.d("Glide",imageurl)


        var myGenre = globalMusicData!!.genre;
        var DisplayGenre = ""
        for (i in myGenre) {
            DisplayGenre = DisplayGenre + " " + i
        }


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

        if(pause){
            mediaPlayer!!.start()
            //mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition)
            binding.progressView.visibility = View.GONE
            pause = false
        }else{
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
                            initializeSeekBar()
//                          mediaPlayer?.seekTo(mediaPlayer!!.currentPosition)


                        }
                    }
                } else {

                currentPlayerState = PlayerState.PAUSED
                mediaPlayer?.pause()
                pause = true
                binding.progressView.visibility = View.GONE

            }


        }
        }

//        binding.stopTrack.setOnClickListener {
//            currentPlayerState = PlayerState.STOPPED
//            if (mediaPlayer != null) { //mediaPlayer != null
////               pause = false
////               currentPlayerState = PlayerState.PLAYING
//                mediaPlayer?.stop()
//
//                currentPlayerState = PlayerState.STARTED
//
////               handler.removeCallbacks(runnable)
//                //seek_bar.progress = 0
//                //mediaPlayer?.reset()
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    "Media is null. Please provide",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }

        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer?.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //mediaPlayer?.seekTo(i * 1000)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //mediaPlayer?.seekTo(i * 1000)

            }
        })


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
                showAlertAndGetComment()

       }

    }

//    override fun onPause() {
//        super.onPause()
//        if(mediaPlayer!=null){
//        mediaPlayer?.release()
//        //mediaPlayer!!.reset()
//
//    }}

    // Method to initialize seek bar and audio stats
    private fun initializeSeekBar() {
        seek_bar.max = mediaPlayer!!.seconds
        if(seek_bar!=null) {
            runnable = Runnable {
                seek_bar.progress = mediaPlayer!!.currentSeconds

                tv_pass.text = "${mediaPlayer?.currentSeconds} sec"
                val diff = mediaPlayer?.seconds?.minus(mediaPlayer!!.currentSeconds)
                tv_due.text = "$diff sec"

                handler.postDelayed(runnable, 1000)
            }
            handler.postDelayed(runnable, 1000)
        }
    }

    // Creating an extension property to get the media player time duration in seconds
    val MediaPlayer.seconds:Int
        get() {
            return this.duration / 1000
        }
    // Creating an extension property to get media player current position in seconds
    val MediaPlayer.currentSeconds:Int
        get() {
            return this.currentPosition/1000
        }


    private fun showAlertAndGetComment() {
        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
        val view = View.inflate(context, R.layout.comment_layout, null)

        //builder.setTitle("Post Comment")
        builder.setCancelable(true)
        builder.setView(view)

        val commentText = view.findViewById<EditText>(R.id.comment_edit_text)

        builder.setPositiveButton("Submit Comment"
        ) { dialog, which ->
            if(TextUtils.isEmpty(commentText.text)){
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


    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        if(mediaPlayer!=null) {
            mediaPlayer?.reset()
            handler.removeCallbacks(runnable)
            seek_bar.progress = 0
        }
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


