// Garden Gnome Software - Skin
// Pano2VR 6.0.6/17336
// Filename: car.ggsk
// Generated 2019-12-12T10:03:22

function pano2vrSkin(player,base) {
	var me=this;
	var skin=this;
	var flag=false;
	var hotspotTemplates={};
	var skinKeyPressed = 0;
	this.player=player;
	this.player.skinObj=this;
	this.divSkin=player.divSkin;
	this.ggUserdata=player.userdata;
	this.lastSize={ w: -1,h: -1 };
	var basePath="";
	// auto detect base path
	if (base=='?') {
		var scripts = document.getElementsByTagName('script');
		for(var i=0;i<scripts.length;i++) {
			var src=scripts[i].src;
			if (src.indexOf('skin.js')>=0) {
				var p=src.lastIndexOf('/');
				if (p>=0) {
					basePath=src.substr(0,p+1);
				}
			}
		}
	} else
	if (base) {
		basePath=base;
	}
	this.elementMouseDown=[];
	this.elementMouseOver=[];
	var cssPrefix='';
	var domTransition='transition';
	var domTransform='transform';
	var prefixes='Webkit,Moz,O,ms,Ms'.split(',');
	var i;
	var hs,el,els,elo,ela,elHorScrollFg,elHorScrollBg,elVertScrollFg,elVertScrollBg,elCornerBg;
	if (typeof document.body.style['transform'] == 'undefined') {
		for(var i=0;i<prefixes.length;i++) {
			if (typeof document.body.style[prefixes[i] + 'Transform'] !== 'undefined') {
				cssPrefix='-' + prefixes[i].toLowerCase() + '-';
				domTransition=prefixes[i] + 'Transition';
				domTransform=prefixes[i] + 'Transform';
			}
		}
	}
	
	player.setMargins(0,0,0,0);
	
	this.updateSize=function(startElement) {
		var stack=[];
		stack.push(startElement);
		while(stack.length>0) {
			var e=stack.pop();
			if (e.ggUpdatePosition) {
				e.ggUpdatePosition();
			}
			if (e.hasChildNodes()) {
				for(var i=0;i<e.childNodes.length;i++) {
					stack.push(e.childNodes[i]);
				}
			}
		}
	}
	
	this.callNodeChange=function(startElement) {
		var stack=[];
		stack.push(startElement);
		while(stack.length>0) {
			var e=stack.pop();
			if (e.ggNodeChange) {
				e.ggNodeChange();
			}
			if (e.hasChildNodes()) {
				for(var i=0;i<e.childNodes.length;i++) {
					stack.push(e.childNodes[i]);
				}
			}
		}
	}
	player.addListener('configloaded', function() { me.callNodeChange(me.divSkin); });
	player.addListener('changenode', function() { me.ggUserdata=player.userdata; me.callNodeChange(me.divSkin); });
	
	var parameterToTransform=function(p) {
		var hs='translate(' + p.rx + 'px,' + p.ry + 'px) rotate(' + p.a + 'deg) scale(' + p.sx + ',' + p.sy + ')';
		return hs;
	}
	
	this.findElements=function(id,regex) {
		var r=[];
		var stack=[];
		var pat=new RegExp(id,'');
		stack.push(me.divSkin);
		while(stack.length>0) {
			var e=stack.pop();
			if (regex) {
				if (pat.test(e.ggId)) r.push(e);
			} else {
				if (e.ggId==id) r.push(e);
			}
			if (e.hasChildNodes()) {
				for(var i=0;i<e.childNodes.length;i++) {
					stack.push(e.childNodes[i]);
				}
			}
		}
		return r;
	}
	
	this.addSkin=function() {
		var hs='';
		this.ggCurrentTime=new Date().getTime();
		el=me._video_1=document.createElement('div');
		me._video_1.seekbars = [];
		me._video_1.ggInitMedia = function(media) {
			var notifySeekbars = function() {
				for (var i = 0; i < me._video_1.seekbars.length; i++) {
					var seekbar = me.findElements(me._video_1.seekbars[i]);
					if (seekbar.length > 0) seekbar[0].connectToMediaEl();
				}
			}
			while (me._video_1.hasChildNodes()) {
				me._video_1.removeChild(me._video_1.lastChild);
			}
			if (me._video_1__vid) {
				me._video_1__vid.pause();
			}
			if(media == '') {
				notifySeekbars();
				me._video_1.ggVideoNotLoaded = true;
				return;
			}
			me._video_1.ggVideoNotLoaded = false;
			me._video_1__vid=document.createElement('video');
			me._video_1__vid.className='ggskin ggskin_video';
			me._video_1__vid.setAttribute('width', '100%');
			me._video_1__vid.setAttribute('height', '100%');
			me._video_1__vid.setAttribute('autoplay', '');
			me._video_1__vid.setAttribute('controls', '');
			me._video_1__source=document.createElement('source');
			me._video_1__source.setAttribute('src', media);
			me._video_1__vid.setAttribute('playsinline', 'playsinline');
			me._video_1__vid.setAttribute('style', ';');
			me._video_1__vid.appendChild(me._video_1__source);
			me._video_1.appendChild(me._video_1__vid);
			var videoEl = player.registerVideoElement('Video 1', me._video_1__vid);
			videoEl.autoplay = true;
			notifySeekbars();
			me._video_1.ggVideoSource = media;
		}
		el.ggId="Video 1";
		el.ggDx=-151;
		el.ggDy=106;
		el.ggParameter={ rx:0,ry:0,a:0,sx:1,sy:1 };
		el.ggVisible=false;
		el.className="ggskin ggskin_video ";
		el.ggType='video';
		hs ='';
		hs+='height : 103px;';
		hs+='left : -10000px;';
		hs+='position : absolute;';
		hs+='top : -10000px;';
		hs+='visibility : hidden;';
		hs+='width : 297px;';
		hs+='pointer-events:auto;';
		el.setAttribute('style',hs);
		el.style[domTransform + 'Origin']='50% 50%';
		me._video_1.ggIsActive=function() {
			if (me._video_1__vid != null) {
				return (me._video_1__vid.paused == false && me._video_1__vid.ended == false);
			} else {
				return false;
			}
		}
		el.ggElementNodeId=function() {
			return player.getCurrentNode();
		}
		me._video_1.ggUpdatePosition=function (useTransition) {
			if (useTransition==='undefined') {
				useTransition = false;
			}
			if (!useTransition) {
				this.style[domTransition]='none';
			}
			if (this.parentNode) {
				var pw=this.parentNode.clientWidth;
				var w=this.offsetWidth;
					this.style.left=(this.ggDx + pw/2 - w/2) + 'px';
				var ph=this.parentNode.clientHeight;
				var h=this.offsetHeight;
					this.style.top=(this.ggDy + ph/2 - h/2) + 'px';
			}
		}
		me.divSkin.appendChild(me._video_1);
		me._video_1.ggVideoSource = 'media/06.mp4';
		me._video_1.ggVideoNotLoaded = true;
		player.addListener('sizechanged', function() {
			me.updateSize(me.divSkin);
		});
	};
	this.hotspotProxyClick=function(id, url) {
	}
	this.hotspotProxyDoubleClick=function(id, url) {
	}
	me.hotspotProxyOver=function(id, url) {
	}
	me.hotspotProxyOut=function(id, url) {
	}
	player.addListener('changenode', function() {
		me.ggUserdata=player.userdata;
	});
	me.skinTimerEvent=function() {
		me.ggCurrentTime=new Date().getTime();
	};
	player.addListener('timer', me.skinTimerEvent);
	function SkinHotspotClass_hotspot_1(parentScope,hotspot) {
		var me=this;
		var flag=false;
		var hs='';
		me.parentScope=parentScope;
		me.hotspot=hotspot;
		var nodeId=String(hotspot.url);
		nodeId=(nodeId.charAt(0)=='{')?nodeId.substr(1, nodeId.length - 2):'';
		me.ggUserdata=skin.player.getNodeUserdata(nodeId);
		me.elementMouseDown=[];
		me.elementMouseOver=[];
		me.findElements=function(id,regex) {
			return skin.findElements(id,regex);
		}
		el=me._hotspot_1=document.createElement('div');
		el.ggId="Hotspot 1";
		el.ggDx=-168;
		el.ggDy=-75;
		el.ggParameter={ rx:0,ry:0,a:0,sx:1,sy:1 };
		el.ggVisible=true;
		el.className="ggskin ggskin_hotspot ";
		el.ggType='hotspot';
		hs ='';
		hs+='height : 0px;';
		hs+='left : -10000px;';
		hs+='position : absolute;';
		hs+='top : -10000px;';
		hs+='visibility : inherit;';
		hs+='width : 0px;';
		hs+='pointer-events:auto;';
		el.setAttribute('style',hs);
		el.style[domTransform + 'Origin']='50% 50%';
		me._hotspot_1.ggIsActive=function() {
			return player.getCurrentNode()==this.ggElementNodeId();
		}
		el.ggElementNodeId=function() {
			return me.hotspot.url.substr(1, me.hotspot.url.length - 2);
		}
		me._hotspot_1.onclick=function (e) {
			skin.hotspotProxyClick(me.hotspot.id, me.hotspot.url);
		}
		me._hotspot_1.ondblclick=function (e) {
			skin.hotspotProxyDoubleClick(me.hotspot.id, me.hotspot.url);
		}
		me._hotspot_1.onmouseover=function (e) {
			player.setActiveHotspot(me.hotspot);
			skin.hotspotProxyOver(me.hotspot.id, me.hotspot.url);
		}
		me._hotspot_1.onmouseout=function (e) {
			player.setActiveHotspot(null);
			skin.hotspotProxyOut(me.hotspot.id, me.hotspot.url);
		}
		me._hotspot_1.ggUpdatePosition=function (useTransition) {
		}
		el=me._butt_1=document.createElement('div');
		els=me._butt_1__img=document.createElement('img');
		els.className='ggskin ggskin_butt_1';
		hs='data:image/png;base64,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';
		els.setAttribute('src',hs);
		els.ggNormalSrc=hs;
		els.setAttribute('style','position: absolute;top: 0px;left: 0px;width: 100%;height: 100%;-webkit-user-drag:none;pointer-events:none;;');
		els.className='ggskin ggskin_button';
		els['ondragstart']=function() { return false; };
		player.checkLoaded.push(els);
		el.appendChild(els);
		el.ggSubElement = els;
		el.ggId="butt_1";
		el.ggDx=-3;
		el.ggDy=3;
		el.ggParameter={ rx:0,ry:0,a:0,sx:1,sy:1 };
		el.ggVisible=true;
		el.className="ggskin ggskin_button rd-1";
		el.ggType='button';
		hs ='';
		hs+='cursor : pointer;';
		hs+='height : 62px;';
		hs+='left : -10000px;';
		hs+='position : absolute;';
		hs+='top : -10000px;';
		hs+='visibility : inherit;';
		hs+='width : 206px;';
		hs+='pointer-events:auto;';
		el.setAttribute('style',hs);
		el.style[domTransform + 'Origin']='50% 50%';
		me._butt_1.ggIsActive=function() {
			if ((this.parentNode) && (this.parentNode.ggIsActive)) {
				return this.parentNode.ggIsActive();
			}
			return false;
		}
		el.ggElementNodeId=function() {
			if ((this.parentNode) && (this.parentNode.ggElementNodeId)) {
				return this.parentNode.ggElementNodeId();
			}
			return me.ggNodeId;
		}
		me._butt_1.onclick=function (e) {
			me._text_1.ggVisible = !me._text_1.ggVisible;
			var flag=me._text_1.ggVisible;
			me._text_1.style[domTransition]='none';
			me._text_1.style.visibility=((flag)&&(Number(me._text_1.style.opacity)>0||!me._text_1.style.opacity))?'inherit':'hidden';
			me._svg_1.ggVisible = !me._svg_1.ggVisible;
			var flag=me._svg_1.ggVisible;
			me._svg_1.style[domTransition]='none';
			me._svg_1.style.visibility=((flag)&&(Number(me._svg_1.style.opacity)>0||!me._svg_1.style.opacity))?'inherit':'hidden';
			me._svg_2.ggVisible = !me._svg_2.ggVisible;
			var flag=me._svg_2.ggVisible;
			me._svg_2.style[domTransition]='none';
			me._svg_2.style.visibility=((flag)&&(Number(me._svg_2.style.opacity)>0||!me._svg_2.style.opacity))?'inherit':'hidden';
		}
		me._butt_1.ggUpdatePosition=function (useTransition) {
			if (useTransition==='undefined') {
				useTransition = false;
			}
			if (!useTransition) {
				this.style[domTransition]='none';
			}
			if (this.parentNode) {
				var pw=this.parentNode.clientWidth;
				var w=this.offsetWidth;
					this.style.left=(this.ggDx + pw/2 - w/2) + 'px';
				var ph=this.parentNode.clientHeight;
				var h=this.offsetHeight;
					this.style.top=(this.ggDy + ph/2 - h/2) + 'px';
			}
		}
		me._hotspot_1.appendChild(me._butt_1);
		el=me._svg_1=document.createElement('div');
		els=me._svg_1__img=document.createElement('img');
		els.className='ggskin ggskin_svg';
		hs='data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTc1ODU3NDg0OTE0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIwODQiIGRhdGEtc3BtLWFuY2hvci1pZD0iYTMxM3guNzc4MTA2OS4wLmkwIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluay'+
			'Igd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTg5NiAxMjhjLTM1LjM2IDAtNjQgMjguNjQtNjQgNjQgMCAxNS40ODggNS43MjggMjkuNTA0IDE0Ljg4IDQwLjU3NmwtMTY4LjA2NCAzMTIuMDk2QzY3Ni41NDQgNTQ0LjQ0OCA2NzQuMzM2IDU0NCA2NzIgNTQ0Yy0xNC40MzIgMC0yNy42MTYgNC45Ni0zOC4zMzYgMTMuMDI0bC0yMTguNjU2LTEzMS4yQzQxNS41MiA0MjIuNTkyIDQxNiA0MTkuMzYgNDE2IDQxNmMwLTM1LjM2LTI4LjY0LTY0LTY0LTY0cy02NCAyOC42NC02NCA2NGMwIDE1LjQ4OCA1Ljcy'+
			'OCAyOS41MDQgMTQuODggNDAuNTc2bC0xNjguMDY0IDMxMi4wOTZDMTMyLjU3NiA3NjguNDQ4IDEzMC4zMzYgNzY4IDEyOCA3NjhjLTM1LjM2IDAtNjQgMjguNjQtNjQgNjQgMCAzNS4zNiAyOC42NCA2NCA2NCA2NHM2NC0yOC42NCA2NC02NGMwLTE1LjQ4OC01LjcyOC0yOS41MDQtMTQuODgtNDAuNTc2bDE2OC4wNjQtMzEyLjA5NkMzNDcuNDU2IDQ3OS41NTIgMzQ5LjY2NCA0ODAgMzUyIDQ4MGMxNC40MzIgMCAyNy42MTYtNC45NiAzOC4zMzYtMTMuMDI0bDIxOC42NTYgMTMxLjJDNjA4LjQ4IDYwMS40MDggNjA4IDYwNC42NCA2MDggNjA4YzAgMzUuMzYgMjguNjQgNjQgNjQgNjRzNjQtMjguNj'+
			'QgNjQtNjRjMC0xNS40ODgtNS43MjgtMjkuNTA0LTE0Ljg4LTQwLjU3NmwxNjguMDY0LTMxMi4wOTZDODkxLjQyNCAyNTUuNTUyIDg5My42NjQgMjU2IDg5NiAyNTZjMzUuMzYgMCA2NC0yOC42NCA2NC02NEM5NjAgMTU2LjY0IDkzMS4zNiAxMjggODk2IDEyOHoiIHAtaWQ9IjIwODUiIGRhdGEtc3BtLWFuY2hvci1pZD0iYTMxM3guNzc4MTA2OS4wLmkxIiBjbGFzcz0iIj48L3BhdGg+PC9zdmc+';
		me._svg_1__img.setAttribute('src',hs);
		els.setAttribute('style','position: absolute;top: 0px;left: 0px;width: 100%;height: 100%;-webkit-user-drag:none;pointer-events:none;svg_1;');
		els['ondragstart']=function() { return false; };
		el.appendChild(els);
		el.ggSubElement = els;
		el.ggId="Svg 1";
		el.ggDx=8;
		el.ggDy=-88;
		el.ggParameter={ rx:0,ry:0,a:0,sx:1,sy:1 };
		el.ggVisible=false;
		el.className="ggskin ggskin_svg ";
		el.ggType='svg';
		hs ='';
		hs+='height : 50px;';
		hs+='left : -10000px;';
		hs+='position : absolute;';
		hs+='top : -10000px;';
		hs+='visibility : hidden;';
		hs+='width : 79px;';
		hs+='pointer-events:auto;';
		el.setAttribute('style',hs);
		el.style[domTransform + 'Origin']='50% 50%';
		me._svg_1.ggIsActive=function() {
			if ((this.parentNode) && (this.parentNode.ggIsActive)) {
				return this.parentNode.ggIsActive();
			}
			return false;
		}
		el.ggElementNodeId=function() {
			if ((this.parentNode) && (this.parentNode.ggElementNodeId)) {
				return this.parentNode.ggElementNodeId();
			}
			return me.ggNodeId;
		}
		me._svg_1.ggUpdatePosition=function (useTransition) {
			if (useTransition==='undefined') {
				useTransition = false;
			}
			if (!useTransition) {
				this.style[domTransition]='none';
			}
			if (this.parentNode) {
				var pw=this.parentNode.clientWidth;
				var w=this.offsetWidth;
					this.style.left=(this.ggDx + pw/2 - w/2) + 'px';
				var ph=this.parentNode.clientHeight;
				var h=this.offsetHeight;
					this.style.top=(this.ggDy + ph/2 - h/2) + 'px';
			}
		}
		me._hotspot_1.appendChild(me._svg_1);
		el=me._svg_2=document.createElement('div');
		els=me._svg_2__img=document.createElement('img');
		els.className='ggskin ggskin_svg';
		hs='data:image/svg+xml;base64,PCEtLSBCeSBTYW0gSGVyYmVydCAoQHNoZXJiKSwgZm9yIGV2ZXJ5b25lLiBNb3JlIEAgaHR0cDovL2dvby5nbC83QUp6YkwgLS0+Cjxzdmcgd2lkdGg9IjQ1IiBoZWlnaHQ9IjQ1IiB2aWV3Qm94PSIwIDAgNDUgNDUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgc3Ryb2tlPSIjYjBkNWVmIj4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMSAxKSIgc3Ryb2tlLXdpZHRoPSIyIj4KICAgICAgICA8Y2lyY2xlIGN4PSIyMiIgY3k9IjIyIiByPSI2IiBzdHJva2Utb3BhY2l0eT0iMCI+CiAgICAgICAgICAgIDxhbmltYX'+
			'RlIGF0dHJpYnV0ZU5hbWU9InIiCiAgICAgICAgICAgICAgICAgYmVnaW49IjEuNXMiIGR1cj0iM3MiCiAgICAgICAgICAgICAgICAgdmFsdWVzPSI2OzIyIgogICAgICAgICAgICAgICAgIGNhbGNNb2RlPSJsaW5lYXIiCiAgICAgICAgICAgICAgICAgcmVwZWF0Q291bnQ9ImluZGVmaW5pdGUiIC8+CiAgICAgICAgICAgIDxhbmltYXRlIGF0dHJpYnV0ZU5hbWU9InN0cm9rZS1vcGFjaXR5IgogICAgICAgICAgICAgICAgIGJlZ2luPSIxLjVzIiBkdXI9IjNzIgogICAgICAgICAgICAgICAgIHZhbHVlcz0iMTswIiBjYWxjTW9kZT0ibGluZWFyIgogICAgICAgICAgICAgICAgIHJlcGVhdENvdW50'+
			'PSJpbmRlZmluaXRlIiAvPgogICAgICAgICAgICA8YW5pbWF0ZSBhdHRyaWJ1dGVOYW1lPSJzdHJva2Utd2lkdGgiCiAgICAgICAgICAgICAgICAgYmVnaW49IjEuNXMiIGR1cj0iM3MiCiAgICAgICAgICAgICAgICAgdmFsdWVzPSIyOzAiIGNhbGNNb2RlPSJsaW5lYXIiCiAgICAgICAgICAgICAgICAgcmVwZWF0Q291bnQ9ImluZGVmaW5pdGUiIC8+CiAgICAgICAgPC9jaXJjbGU+CiAgICAgICAgPGNpcmNsZSBjeD0iMjIiIGN5PSIyMiIgcj0iNiIgc3Ryb2tlLW9wYWNpdHk9IjAiPgogICAgICAgICAgICA8YW5pbWF0ZSBhdHRyaWJ1dGVOYW1lPSJyIgogICAgICAgICAgICAgICAgIGJlZ2luPS'+
			'IzcyIgZHVyPSIzcyIKICAgICAgICAgICAgICAgICB2YWx1ZXM9IjY7MjIiCiAgICAgICAgICAgICAgICAgY2FsY01vZGU9ImxpbmVhciIKICAgICAgICAgICAgICAgICByZXBlYXRDb3VudD0iaW5kZWZpbml0ZSIgLz4KICAgICAgICAgICAgPGFuaW1hdGUgYXR0cmlidXRlTmFtZT0ic3Ryb2tlLW9wYWNpdHkiCiAgICAgICAgICAgICAgICAgYmVnaW49IjNzIiBkdXI9IjNzIgogICAgICAgICAgICAgICAgIHZhbHVlcz0iMTswIiBjYWxjTW9kZT0ibGluZWFyIgogICAgICAgICAgICAgICAgIHJlcGVhdENvdW50PSJpbmRlZmluaXRlIiAvPgogICAgICAgICAgICA8YW5pbWF0ZSBhdHRyaWJ1dGVO'+
			'YW1lPSJzdHJva2Utd2lkdGgiCiAgICAgICAgICAgICAgICAgYmVnaW49IjNzIiBkdXI9IjNzIgogICAgICAgICAgICAgICAgIHZhbHVlcz0iMjswIiBjYWxjTW9kZT0ibGluZWFyIgogICAgICAgICAgICAgICAgIHJlcGVhdENvdW50PSJpbmRlZmluaXRlIiAvPgogICAgICAgIDwvY2lyY2xlPgogICAgICAgIDxjaXJjbGUgY3g9IjIyIiBjeT0iMjIiIHI9IjgiPgogICAgICAgICAgICA8YW5pbWF0ZSBhdHRyaWJ1dGVOYW1lPSJyIgogICAgICAgICAgICAgICAgIGJlZ2luPSIwcyIgZHVyPSIxLjVzIgogICAgICAgICAgICAgICAgIHZhbHVlcz0iNjsxOzI7Mzs0OzU7NiIKICAgICAgICAgICAgIC'+
			'AgICBjYWxjTW9kZT0ibGluZWFyIgogICAgICAgICAgICAgICAgIHJlcGVhdENvdW50PSJpbmRlZmluaXRlIiAvPgogICAgICAgIDwvY2lyY2xlPgogICAgPC9nPgo8L3N2Zz4=';
		me._svg_2__img.setAttribute('src',hs);
		els.setAttribute('style','position: absolute;top: 0px;left: 0px;width: 100%;height: 100%;-webkit-user-drag:none;pointer-events:none;svg_2;');
		els['ondragstart']=function() { return false; };
		el.appendChild(els);
		el.ggSubElement = els;
		el.ggId="svg_2";
		el.ggDx=-3;
		el.ggDy=79;
		el.ggParameter={ rx:0,ry:0,a:0,sx:1,sy:1 };
		el.ggVisible=false;
		el.className="ggskin ggskin_svg ";
		el.ggType='svg';
		hs ='';
		hs+='height : 45px;';
		hs+='left : -10000px;';
		hs+='position : absolute;';
		hs+='top : -10000px;';
		hs+='visibility : hidden;';
		hs+='width : 45px;';
		hs+='pointer-events:auto;';
		el.setAttribute('style',hs);
		el.style[domTransform + 'Origin']='50% 50%';
		me._svg_2.ggIsActive=function() {
			if ((this.parentNode) && (this.parentNode.ggIsActive)) {
				return this.parentNode.ggIsActive();
			}
			return false;
		}
		el.ggElementNodeId=function() {
			if ((this.parentNode) && (this.parentNode.ggElementNodeId)) {
				return this.parentNode.ggElementNodeId();
			}
			return me.ggNodeId;
		}
		me._svg_2.onclick=function (e) {
			skin._video_1.ggVisible = !skin._video_1.ggVisible;
			var flag=skin._video_1.ggVisible;
			if (skin._video_1.ggVideoNotLoaded) {
				skin._video_1.ggInitMedia(skin._video_1.ggVideoSource);
			}
			else {
				skin._video_1.ggInitMedia('');
			}
			skin._video_1.style[domTransition]='none';
			skin._video_1.style.visibility=((flag)&&(Number(skin._video_1.style.opacity)>0||!skin._video_1.style.opacity))?'inherit':'hidden';
		}
		me._svg_2.ggUpdatePosition=function (useTransition) {
			if (useTransition==='undefined') {
				useTransition = false;
			}
			if (!useTransition) {
				this.style[domTransition]='none';
			}
			if (this.parentNode) {
				var pw=this.parentNode.clientWidth;
				var w=this.offsetWidth;
					this.style.left=(this.ggDx + pw/2 - w/2) + 'px';
				var ph=this.parentNode.clientHeight;
				var h=this.offsetHeight;
					this.style.top=(this.ggDy + ph/2 - h/2) + 'px';
			}
		}
		me._hotspot_1.appendChild(me._svg_2);
		el=me._container_1=document.createElement('div');
		el.ggId="Container 1";
		el.ggDx=248;
		el.ggDy=30;
		el.ggParameter={ rx:0,ry:0,a:0,sx:1,sy:1 };
		el.ggVisible=true;
		el.className="ggskin ggskin_container ";
		el.ggType='container';
		hs ='';
		hs+='height : 194px;';
		hs+='left : -10000px;';
		hs+='position : absolute;';
		hs+='top : -10000px;';
		hs+='visibility : inherit;';
		hs+='width : 147px;';
		hs+='pointer-events:none;';
		el.setAttribute('style',hs);
		el.style[domTransform + 'Origin']='50% 50%';
		me._container_1.ggIsActive=function() {
			if ((this.parentNode) && (this.parentNode.ggIsActive)) {
				return this.parentNode.ggIsActive();
			}
			return false;
		}
		el.ggElementNodeId=function() {
			if ((this.parentNode) && (this.parentNode.ggElementNodeId)) {
				return this.parentNode.ggElementNodeId();
			}
			return me.ggNodeId;
		}
		me._container_1.ggUpdatePosition=function (useTransition) {
			if (useTransition==='undefined') {
				useTransition = false;
			}
			if (!useTransition) {
				this.style[domTransition]='none';
			}
			if (this.parentNode) {
				var pw=this.parentNode.clientWidth;
				var w=this.offsetWidth;
					this.style.left=(this.ggDx + pw/2 - w/2) + 'px';
				var ph=this.parentNode.clientHeight;
				var h=this.offsetHeight;
					this.style.top=(this.ggDy + ph/2 - h/2) + 'px';
			}
		}
		el=me._text_1=document.createElement('div');
		els=me._text_1__text=document.createElement('div');
		el.className='ggskin ggskin_textdiv';
		el.ggTextDiv=els;
		el.ggId="text_1";
		el.ggDx=-19;
		el.ggDy=-12;
		el.ggParameter={ rx:0,ry:0,a:0,sx:1,sy:1 };
		el.ggVisible=false;
		el.className="ggskin ggskin_text ";
		el.ggType='text';
		hs ='';
		hs+='height : 194px;';
		hs+='left : -10000px;';
		hs+='opacity : 0.59999;';
		hs+='position : absolute;';
		hs+='top : -10000px;';
		hs+='visibility : hidden;';
		hs+='width : 147px;';
		hs+='pointer-events:auto;';
		el.setAttribute('style',hs);
		el.style[domTransform + 'Origin']='50% 50%';
		hs ='position:absolute;';
		hs += 'box-sizing: border-box;';
		hs+='cursor: default;';
		hs+='left: 0px;';
		hs+='top:  0px;';
		hs+='width: 147px;';
		hs+='height: 194px;';
		hs+='background: #ffffff;';
		hs+='border: 0px solid #000000;';
		hs+='color: #000000;';
		hs+='text-align: center;';
		hs+='white-space: nowrap;';
		hs+='padding: 0px 1px 0px 1px;';
		hs+='overflow: hidden;';
		els.setAttribute('style',hs);
		me._text_1.ggUpdateText=function() {
			var hs="text"+me.ggUserdata.title;
			if (hs!=this.ggText) {
				this.ggText=hs;
				this.ggTextDiv.innerHTML=hs;
				if (this.ggUpdatePosition) this.ggUpdatePosition();
			}
		}
		me._text_1.ggUpdateText();
		el.appendChild(els);
		me._text_1.ggIsActive=function() {
			if ((this.parentNode) && (this.parentNode.ggIsActive)) {
				return this.parentNode.ggIsActive();
			}
			return false;
		}
		el.ggElementNodeId=function() {
			if ((this.parentNode) && (this.parentNode.ggElementNodeId)) {
				return this.parentNode.ggElementNodeId();
			}
			return me.ggNodeId;
		}
		me._text_1.onclick=function (e) {
			player.openNext("{node2}","");
		}
		me._text_1.ggUpdatePosition=function (useTransition) {
			if (useTransition==='undefined') {
				useTransition = false;
			}
			if (!useTransition) {
				this.style[domTransition]='none';
			}
			if (this.parentNode) {
				var pw=this.parentNode.clientWidth;
				var w=this.offsetWidth + 0;
					this.style.left=(this.ggDx + pw/2 - w/2) + 'px';
				var ph=this.parentNode.clientHeight;
				var h=this.offsetHeight;
					this.style.top=(this.ggDy + ph/2 - h/2) + 'px';
			}
		}
		me._container_1.appendChild(me._text_1);
		me._hotspot_1.appendChild(me._container_1);
			me.hotspotTimerEvent=function() {
				setTimeout(function() { me.hotspotTimerEvent(); }, 10);
				me._text_1.ggUpdateText();
			}
			me.hotspotTimerEvent();
		me.__div = me._hotspot_1;
	};
	me.addSkinHotspot=function(hotspot) {
		var hsinst = null;
		{
			hotspot.skinid = 'Hotspot 1';
			hsinst = new SkinHotspotClass_hotspot_1(me, hotspot);
			if (!hotspotTemplates.hasOwnProperty(hotspot.skinid)) {
				hotspotTemplates[hotspot.skinid] = [];
			}
			hotspotTemplates[hotspot.skinid].push(hsinst);
		}
		return hsinst;
	}
	me.removeSkinHotspots=function() {
		if(hotspotTemplates['Hotspot 1']) {
			var i;
			for(i = 0; i < hotspotTemplates['Hotspot 1'].length; i++) {
				hotspotTemplates['Hotspot 1'][i] = null;
			}
		}
		hotspotTemplates = [];
	}
	me.addSkin();
	me.skinTimerEvent();
};