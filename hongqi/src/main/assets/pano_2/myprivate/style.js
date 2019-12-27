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
		document.documentElement.style.fontSize = deviceWidth / 12.8 + 'px';
	}
	// 移动适配end

	//判断全景图加载完毕 执行自己的代码
	pano.on("imagesready", function() {
		myprivate()
		console.log("Panorama imagesready!");



	});
	// pano.addListener('touchend',function(){
	// 	console.log(1)
	// })

	// pano.addListener("mouseover", function() {

	// 	console.log("Panorama addListener!");
	// });
	// pano.addListener('touchend',function(){
	// 	console.log(1)
	// })
	// console.log(pano.on())
	// 位置改变时
	pano.on("positionchanged", function() {
		// pano.getPan()
		// console.log(pano.isTouching())//当前是否在移动设备上触摸了全景播放器
		// console.log(pano.getLastVisitedNode())
		// console.log(pano.getPositionAngles(1,2))
		// if(!pano.isTouching()){
		// 	var anim = document.getElementsByClassName('anim')[0];
		// 	if (typeof anim != 'undefined') {
		// 		anim.parentNode.removeChild(anim);
		// 	}
		// }
		// console.log(typeof anim)
		// console.log("Panorama positionchanged!");
	});


	function myprivate() {
		// 位置
		// console.log(pano.getPositionAngles())

		// console.log(pano.isTouching())

		// var ggskin_hotspot = document.getElementsByClassName('ggskin_hotspot')[0].parentNode.previousElementSibling.previousElementSibling.firstChild;
		var ggskin_hotspot = document.getElementsByClassName('Hotspot_1')[0].parentNode;
		// console.log(ggskin_hotspot)
		// var host_wrapper_main = document.createElement("div"); //热点容器的容器
		// ggskin_hotspot.appendChild(host_wrapper_main)
		// host_wrapper_main.classList.add('host_wrapper_main');

		// var ggskin_hotspot_main = document.getElementsByClassName('host_wrapper_main')[0]
		// console.log(ggskin_hotspot_main)
		// ggskin_hotspot.onclick = function() {
		// 	console.log(1111);
		// }
		// ggskin_hotspot.onmouseup = function() {
		// 	console.log(22222);
		// }
		var host_1 = document.getElementsByClassName('host_1')[0];
		host_1.onclick = function(e) {
			if (e && e.stopPropagation) { //因此它支持W3C的stopPropagation()方法 
				e.stopPropagation();
			} else {
				//否则，我们需要使用IE的方式来取消事件冒泡 
				window.event.cancelBubble = true;
			}
			var anim = document.getElementsByClassName('anim')[0];
			// console.log(typeof anim)
			if (typeof anim == 'undefined') {
				//热点名称
				var host_list = [{
					'text': '一体屏',
					'id': '123'
				}, {
					'text': '屏幕',
					'id': '456'
				}, {
					'text': '亮度',
					'id': '789'
				}, {
					'text': '显示',
					'id': '1011'
				}, {
					'text': '屏幕',
					'id': '1208'
				}, {
					'text': '一体屏',
					'id': '1805'
				}];
				host_main(host_1, host_list)
			} else {
				anim.parentNode.removeChild(anim);
				return
			}
			var list_class = document.getElementsByClassName('list_class');
			for (var i = 0; i < list_class.length; i++) {
				list_class[i].index = i;
				list_class[i].onclick = function() {
					var host_id = this.getAttribute('data-id')
					// console.log(host_id)
					doJsTest(host_id)
				}
			}
		}
		var host_2 = document.getElementsByClassName('host_2')[0];
		host_2.onclick = function(e) {
			if (e && e.stopPropagation) { //因此它支持W3C的stopPropagation()方法
				e.stopPropagation();
			} else {
				//否则，我们需要使用IE的方式来取消事件冒泡 
				window.event.cancelBubble = true;
			}
			var anim = document.getElementsByClassName('anim')[0];
			// console.log(typeof anim)
			if (typeof anim == 'undefined') {
				//热点名称
				var host_list = [{
					'text': '一体屏2',
					'id': '123'
				}, {
					'text': '屏幕2',
					'id': '456'
				}, {
					'text': '亮度2',
					'id': '789'
				}, {
					'text': '显示2',
					'id': '1011'
				}, {
					'text': '屏幕2',
					'id': '1208'
				}, {
					'text': '一体屏2',
					'id': '1805'
				}];
				host_main(host_2, host_list)
			} else {
				anim.parentNode.removeChild(anim);
				return
			}

			var list_class = document.getElementsByClassName('list_class');
			for (var i = 0; i < list_class.length; i++) {
				// list_class[i].index = i;
				list_class[i].onclick = function() {
					var host_id = this.getAttribute('data-id')
					// console.log(host_id)
					doJsTest(host_id)
				}
			}
		}
		//安卓端传数据
		function doJsTest(type) {
			console.log(type)
			window.Android.JsTest(type)
		}
	}



	//热点弹窗模板
	function host_main(doc, text) {
		var host_wrapper = document.createElement("div"); //热点容器
		var host_line = document.createElement("div"); //热点弹出的线
		var host_list = document.createElement("div"); //热点的弹出框
		host_wrapper.classList.add('anim');
		host_line.classList.add('line');
		host_list.classList.add('list');
		host_list.classList.add('animated');

		// 判断热点位置调整弹窗位置
		var position_t = doc.getBoundingClientRect().top;
		var position_l = doc.getBoundingClientRect().left;
		var position_window_h = document.body.clientHeight;
		var position_window_w = document.body.clientWidth;

		if (position_t > position_window_h / 2 && position_l > position_window_w / 2) {
			//热点在右下	
			host_wrapper.style.transform = 'scale(1) rotateY(180deg) rotateX(0deg)';
			host_list.style.transform = 'scale(1) rotateY(180deg) rotateX(0deg)';
		} else if (position_t < position_window_h / 2 && position_l > position_window_w / 2) {
			//热点在右上
			host_wrapper.style.transform = 'scale(1) rotateY(180deg) rotateX(180deg)';
			host_list.style.transform = 'scale(1) rotateY(180deg) rotateX(180deg)';
		} else if (position_t < position_window_h / 2 && position_l < position_window_w / 2) {
			//热点在左上
			host_wrapper.style.transform = 'scale(1) rotateY(0deg) rotateX(180deg)';
			host_list.style.transform = 'scale(1) rotateY(0deg) rotateX(180deg)';
		} else if (position_t > position_window_h / 2 && position_l < position_window_w / 2) {
			//热点在左下
			host_wrapper.style.transform = 'scale(1) rotateY(0deg) rotateX(0deg)';
			host_list.style.transform = 'scale(1) rotateY(0deg) rotateX(0deg)';
		}
		var x_list_li = ''
		var x_list_li_text = text;
		for (var i = 0; i < x_list_li_text.length; i++) {
			x_list_li += `<li class="list_class" data-id="${x_list_li_text[i].id}">${x_list_li_text[i].text}</li>`;
		}
		var x_list = `<ul>${x_list_li}</ul>`
		host_list.innerHTML = x_list;
		host_wrapper.appendChild(host_line)
		host_wrapper.appendChild(host_list)
		doc.after(host_wrapper);


		//热点线动画 和弹窗动画
		$('.line').animate({
			width: "70px"
		}, function() {
			$('.list').fadeIn()
			// $('.list').addClass('bounceIn').show()
		});
		// 弹窗内滚动
		// $('.list div ul').dragscroll({
		// 	direction: 'scrollTop'
		// });
		// document.getElementsByClassName('list')[0].onclick = function() {
		// 	// console.log(1)
		// }
	}
	// $('.anim').on('click','li',function(){
	// 	console.log($(this).index())
	// })
})()
