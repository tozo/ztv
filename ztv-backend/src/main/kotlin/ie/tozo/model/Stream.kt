package ie.tozo.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(StreamId::class)
data class Stream(
    @Id val url: String = "",
    val title: String = "",
    val length: Int = 0,
    @Id @Column(name = "FEED_ID") var feedId: Int = -1
) {

    @Column(name = "TVG_ID")
    var tvgId: String? = ""
    @Column(name = "TVG_SHIFT")
    var tvgShift: Int? = 0
    @Column(name = "TVG_NAME")
    var tvgName: String? = ""
    @Column(name = "TVG_LOGO")
    var tvgLogo: String? = ""
    @Column(name = "GUIDE_NUMBER")
    var guideNumber: Int? = 0
    @Column(name = "AUDIO_TRACK")
    var audioTrack: String? = ""
    @Column(name = "ASPECT_RATIO")
    var aspectRatio: String? = ""
    @Column(name = "GROUP_TITLE")
    var groupTitle: String? = ""
    var feedName: String? = ""

    constructor(
        url: String,
        title: String,
        length: Int,
        tvgId: String,
        tvgShift: Int,
        tvgName: String,
        tvgLogo: String,
        audioTrack: String,
        aspectRatio: String,
        groupTitle: String,
        feedId: Int = 0
    ) : this(url, title, length, feedId) {
        this.tvgId = tvgId
        this.tvgShift = tvgShift
        this.tvgName = tvgName
        this.tvgLogo = tvgLogo
        this.audioTrack = audioTrack
        this.aspectRatio = aspectRatio
        this.groupTitle = groupTitle
    }

}