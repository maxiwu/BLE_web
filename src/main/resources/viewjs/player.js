$(function() {
	$("#slider-vertical").slider({
		orientation : "vertical",
		range : "min",
		min : 0,
		max : 100,
		value : 0,
	// slide: function( event, ui ) {
	// $( "#amount" ).val( ui.value );
	// }
		change: function( event, ui ) 
		{
			$.ajax({
				url : "/UCloudPortal/player/setVolume/" + getSerialNumber() + "?vol=" + ui.value
			});
		}
	});
	$("#amount").val($("#slider-vertical").slider("value"));
	

	// add event to buttons
	$("#playBtn").click(avPlay);
	$("#stopBtn").click(avStop);
	$("#shuffleBtn").click(avShuffle);
	$("#repeatBtn").click(avRepeat);
	$("#forwardBtn").click(avForward);
	$("#backwardBtn").click(avBackward);
	$("#albumsBtn").click(dkAlbum);
	$("#filesBtn").click(dkFile);

	// get info after page load
	function updatePlaybackInfo() {
		$.ajax({
			url : "/UCloudPortal/player/playbackinfo/" + getSerialNumber()
		}).then(function(pbinfo) {
			// server read too fast, need fix
			// $("#statusText").text(pbinfo.Status);

			// playbackinfo don't know max of track, use tag attr to remember
			// and calulate
			// use playbackinfo Position
			var TotalTime = $("#progressBarDiv").attr("aria-valuemax");
			var seekval = (pbinfo.Position / TotalTime * 100).toFixed(0);
			$("#progressBarDiv").css("width", seekval + "%");

			var trackLength = secondsTimeSpanToHMS(TotalTime);
			var trackSeek = secondsTimeSpanToHMS(pbinfo.Position);
			$("#progressBarText").text(trackSeek + " / " + trackLength);

			guiUpdateShuffleState(pbinfo.ShuffleState);
			guiUpdateRepeatState(pbinfo.RepeatState);
		});
	}

	function updateProgress() {
		$.ajax({
			url : "/UCloudPortal/player/playbackinfo/" + getSerialNumber()
		}).then(function(pbinfo) {
			var TotalTime = $("#progressBarDiv").attr("aria-valuemax");
			var seekval = (pbinfo.Position / TotalTime * 100).toFixed(0);
			$("#progressBarDiv").css("width", seekval + "%")
			$("#statusText").text(pbinfo.Status);

			var trackLength = secondsTimeSpanToHMS(TotalTime);
			var trackSeek = secondsTimeSpanToHMS(pbinfo.Position);
			$("#progressBarText").text(trackSeek + " / " + trackLength);
		});
	}
	
	function updateVol()
	{
		$.ajax({
			url : "/UCloudPortal/player/getVolume/" + getSerialNumber()
		}).then(function(vol) {
			//change value
			$("#slider-vertical").slider("option","value",vol);
		});
	}

	// update progress every 5 secs
	window.setInterval(function() {
		updateProgress();
	}, 5000);

	updatePlaybackInfo();
	// update trackinfo too!
	avGetCurrentTrackInfo();
	updateVol();

	// GUI update methods
	function guiNoShuffle() {
		$("#shuffleBtn").switchClass("btn-primary", "btn-default");
	}
	function guiShuffle() {
		$("#shuffleBtn").switchClass("btn-default", "btn-primary");
	}
	function guiUpdateShuffleState(state) {
		switch (state) {
		case "no_shuffle":
			guiNoShuffle()
			break;
		case "shuffle":
			guiShuffle()
			break;
		}
	}
	function guiUpdateRepeatState(state) {
		switch (state) {
		case "no_repeat":
			$("#repeatBtn").switchClass("btn-primary", "btn-default");
			break;
		case "repeat_one":
			$("#repeatBtn").switchClass("btn-default", "btn-primary");
			$("#repeatIcon").switchClass("glyphicon-refresh",
					"glyphicon-repeat");
			break;
		case "repeat_all":
			$("#repeatBtn").switchClass("btn-default", "btn-primary");
			$("#repeatIcon").switchClass("glyphicon-repeat",
					"glyphicon-refresh");
			break;
		}
	}

	// REST methods
	function avPlay() {
		$.ajax({
			url : "/UCloudPortal/player/play/" + getSerialNumber()
		}).then(function(data) {
			guiUpdateTrackInfo(data);
			// update status!
			updatePlaybackInfo();

			// patch server sync problem
			$("#statusText").text("playing");
		});
	}
	function avStop() {
		$.ajax({
			url : "/UCloudPortal/player/stop/" + getSerialNumber()
		}).then(function(data) {
			guiUpdateTrackInfo(data);
			// update status!
			updatePlaybackInfo();

			// patch server sync problem
			$("#statusText").text("stopped");
		});
	}
	function avForward() {
		$.ajax({
			url : "/UCloudPortal/player/forward/" + getSerialNumber()
		}).then(function(data) {
			guiUpdateTrackInfo(data);
			// update status!
			updatePlaybackInfo();
		});
	}
	function avBackward() {
		$.ajax({
			url : "/UCloudPortal/player/backward/" + getSerialNumber()
		}).then(function(data) {
			guiUpdateTrackInfo(data);
			// update status!
			updatePlaybackInfo();
		});
	}

	function avGetCurrentTrackInfo() {
		$.ajax(
				{
					url : "/UCloudPortal/player/getCurrentTrackInfo/"
							+ getSerialNumber()
				}).then(function(data) {
			// update track max here!
			$("#progressBarDiv").attr("aria-valuemax", data.TotalTime);

			guiUpdateTrackInfo(data);
		});
	}

	function avShuffle() {
		$.ajax({
			url : "/UCloudPortal/player/shuffle/" + getSerialNumber()
		}).then(function(data) {
			guiUpdateTrackInfo(data);
			guiUpdateShuffleState(data.ShuffleState);
		});
	}

	function avRepeat() {
		$.ajax({
			url : "/UCloudPortal/player/repeat/" + getSerialNumber()
		}).then(function(data) {
			guiUpdateTrackInfo(data);
			guiUpdateRepeatState(data.RepeatState);
		});
	}

	function dkAlbum() {
		//window.location.replace("/UCloudPortal/player/getAlbums/" + getSerialNumber());
		window.location.replace("/UCloudPortal/media/albums/" + getSerialNumber());
	}

	function dkFile(){
		///getFiles/{serialnumber}/{path}/		
		window.location.replace("/UCloudPortal/player/files/" + getSerialNumber()  );
	}
	
	function guiUpdateTrackInfo(data) {
		$("#artistText").text(data.Artist);
		$("#titleText").text(data.Title);
		$("#albumText").text(data.Album);
	}

	function secondsTimeSpanToHMS(s) {
		var h = Math.floor(s / 3600); // Get whole hours
		s -= h * 3600;
		var m = Math.floor(s / 60); // Get remaining minutes
		s -= m * 60;
		return h + ":" + (m < 10 ? '0' + m : m) + ":" + (s < 10 ? '0' + s : s); // zero
		// padding
		// on
		// minutes
		// and
		// seconds
	}

	function getSerialNumber() {
		return $("#serialnumberVal").val();
	}
});