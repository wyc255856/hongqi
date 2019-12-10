(function() {
	//	移动式配问题
	var sp;
	sp_sp()
	window.onresize = function() {
		clearTimeout(sp);
		sp = setTimeout(function() {
			sp_sp();
		}, 500);
	}

	function sp_sp() {
		var deviceWidth = document.documentElement.clientWidth;
		//		if(deviceWidth > 640) {
		//			deviceWidth = 640
		//		}
		document.documentElement.style.fontSize = deviceWidth / 7.32 + 'px';
	}
	// 移动适配end
	var dd = setInterval(function() {
		var text_1 = document.getElementsByClassName('text-1')[0]
		console.log(text_1)
		if (text_1 == undefined) {

		} else {
			window.clearInterval(dd)
			data()
		}
	}, 100)


	function data() {
		var rd_1 = document.getElementsByClassName('rd-1')[0]
		console.log(rd_1)
		rd_1.onclick = function(){
            console.log(1)
			$('.text-1').toggleClass("display")
        }
		// .onclick(function() {
			
		// 	
		// })
	}
})()
