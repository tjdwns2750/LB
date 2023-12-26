var HOME_PATH = window.HOME_PATH || '.';

/* 랜덤좌표
var MARKER_SPRITE_X_OFFSET = 29,
	MARKER_SPRITE_Y_OFFSET = 50,
	MARKER_SPRITE_POSITION = {
		"A0": [0, 0],
		"B0": [MARKER_SPRITE_X_OFFSET, 0],
		"C0": [MARKER_SPRITE_X_OFFSET * 2, 0],
		"D0": [MARKER_SPRITE_X_OFFSET * 3, 0],
		"E0": [MARKER_SPRITE_X_OFFSET * 4, 0],
		"F0": [MARKER_SPRITE_X_OFFSET * 5, 0],
		"G0": [MARKER_SPRITE_X_OFFSET * 6, 0],
		"H0": [MARKER_SPRITE_X_OFFSET * 7, 0],
		"I0": [MARKER_SPRITE_X_OFFSET * 8, 0],

		"A1": [0, MARKER_SPRITE_Y_OFFSET],
		"B1": [MARKER_SPRITE_X_OFFSET, MARKER_SPRITE_Y_OFFSET],
		"C1": [MARKER_SPRITE_X_OFFSET * 2, MARKER_SPRITE_Y_OFFSET],
		"D1": [MARKER_SPRITE_X_OFFSET * 3, MARKER_SPRITE_Y_OFFSET],
		"E1": [MARKER_SPRITE_X_OFFSET * 4, MARKER_SPRITE_Y_OFFSET],
		"F1": [MARKER_SPRITE_X_OFFSET * 5, MARKER_SPRITE_Y_OFFSET],
		"G1": [MARKER_SPRITE_X_OFFSET * 6, MARKER_SPRITE_Y_OFFSET],
		"H1": [MARKER_SPRITE_X_OFFSET * 7, MARKER_SPRITE_Y_OFFSET],
		"I1": [MARKER_SPRITE_X_OFFSET * 8, MARKER_SPRITE_Y_OFFSET],

		"A2": [0, MARKER_SPRITE_Y_OFFSET * 2],
		"B2": [MARKER_SPRITE_X_OFFSET, MARKER_SPRITE_Y_OFFSET * 2],
		"C2": [MARKER_SPRITE_X_OFFSET * 2, MARKER_SPRITE_Y_OFFSET * 2],
		"D2": [MARKER_SPRITE_X_OFFSET * 3, MARKER_SPRITE_Y_OFFSET * 2],
		"E2": [MARKER_SPRITE_X_OFFSET * 4, MARKER_SPRITE_Y_OFFSET * 2],
		"F2": [MARKER_SPRITE_X_OFFSET * 5, MARKER_SPRITE_Y_OFFSET * 2],
		"G2": [MARKER_SPRITE_X_OFFSET * 6, MARKER_SPRITE_Y_OFFSET * 2],
		"H2": [MARKER_SPRITE_X_OFFSET * 7, MARKER_SPRITE_Y_OFFSET * 2],
		"I2": [MARKER_SPRITE_X_OFFSET * 8, MARKER_SPRITE_Y_OFFSET * 2]
	};
*/

// controller에서 받은 데이터를 좌표로 수정
document.addEventListener('DOMContentLoaded', function() {
	// JavaScript에서 Thymeleaf를 통해 전달된 데이터 사용
	if (boardList) {
		boardList.forEach(function(board) {
			// 각 게시글의 주소를 좌표로 변환
			if (!board.address) {
				console.error('게시글', board.bno, '의 주소가 없습니다.');
				return; // 주소가 없으면 처리 중단
			}

			console.log('게시글', board.bbno, '의 주소:', board.address);

			// 주소를 좌표로 변환
			geocodeAddress(board.address, function(latlng) {
				if (latlng) {
					// 좌표를 이용하여 지도에 마커 추가
					addMarkerToMap(latlng, board);
				}
			});
		});
	}
});

function geocodeAddress(address, callback) {
	naver.maps.Service.geocode({
		query: address
	}, function(status, response) {
		if (status === naver.maps.Service.Status.ERROR) {
			console.error('Error during geocoding for address:', address, 'Error message:', response.message);
			return callback(null);
		}

		// response.result.items 대신에 response.v2.addresses를 사용합니다.
		var items = response.v2.addresses;

		if (items && items.length > 0) {
			var item = items[0];
			var latlng = new naver.maps.LatLng(item.y, item.x);
			callback(latlng);
		} else {
			console.error('No result items found for address:', address);
			return callback(null);
		}
	});
}

/*
function addMarkerToMap(latlng, board) {
	var marker = new naver.maps.Marker({
		map: map,
		position: latlng,
		title: '게시글 ' + board.bno,
		// 여기에 추가적인 마커 설정을 할 수 있습니다.
	});

	var infoWindow = new naver.maps.InfoWindow({
		content: '<div style="width:150px;text-align:center;padding:10px;">' +
			'<h2>게시글' + board.bno + ' - ' + '</h2>' + board.title + '</div>'
	});

	naver.maps.Event.addListener(marker, 'click', function() {
		if (infoWindow.getMap()) {
			infoWindow.close();
		} else {
			infoWindow.open(map, marker);
		}
	});
}*/


var map = new naver.maps.Map("map", {

	zoom: 17,
	mapTypeControl: true
});

var infoWindow = new naver.maps.InfoWindow({
	anchorSkew: true
});

map.setCursor('pointer');

function addMarkerToMap(latlng, board) {
	console.log(board.address);
	var marker = new naver.maps.Marker({
		map: map,
		position: latlng,
		title: '게시글 ' + board.bno,
		// 여기에 추가적인 마커 설정을 할 수 있습니다.
		icon: {
			url: HOME_PATH + '/img/books.ico',
			size: new naver.maps.Size(24, 37),
			anchor: new naver.maps.Point(12, 37)
		}
	});
	console.log(board.bbno);
	console.log(board.id);

	var infoWindow = new naver.maps.InfoWindow({
		content: '<div style="padding:10px;min-width:200px;line-height:150%;">' +
			'<h3>' + '판매' +'</h3>' +
			'<h3>' + board.name + '</h3>' +
			'<a id="boardtitle" style="font-size:20px" href="./bookBoard/read?boardnum=' + board.bbno + '">' + '제목 :' + board.title + '</a>' +
			'<img id="bookBoardImg" src="' + board.thumbnail + '">' +
			'<p id="boardcontent">' + '내용 : ' + board.content + '</p>' +
			'<p id="boardprice">' + '가격 : ' + board.price + '</p>' +
			'<p id="boardaddress">' + '거래 주소 :' + board.address + '</p>' +
			'<p id createdDay>' + '작성일 :' + board.created_day + '</p>' +
			'<form action="./chat/chatRoom" method="post">' +
			'<input type="hidden" name="bbno" value=' +board.bbno + '>' +
			'<input type="hidden" name="boardId" value=' +board.id.toString() + '>' +
			'<input type="submit" value="채팅하기"></input>' +
			'</form>' +
			// '<a href="chat/chatRoom?bbno=' +board.bbno +"&id=" +board.id + '"> 채팅하기' + '</a>' +
			'</div>'
	});

	naver.maps.Event.addListener(marker, 'click', function() {
		if (infoWindow. getMap()) {
			infoWindow.close();
		} else {
			infoWindow.open(map, marker);
		}
	});
}

function searchCoordinateToAddress(latlng) {

	infoWindow.close();

	naver.maps.Service.reverseGeocode({
		coords: latlng,
		orders: [
			naver.maps.Service.OrderType.ADDR,
			naver.maps.Service.OrderType.ROAD_ADDR
		].join(',')
	}, function(status, response) {
		if (status === naver.maps.Service.Status.ERROR) {
			return alert('Something Wrong!');
		}

		var items = response.v2.results,
			address = '',
			htmlAddresses = [];

		for (var i = 0, ii = items.length, item, addrType; i < ii; i++) {
			item = items[i];
			address = makeAddress(item) || '';
			addrType = item.name === 'roadaddr' ? '[도로명 주소]' : '[지번 주소]';

			htmlAddresses.push((i + 1) + '. ' + addrType + ' ' + address);
		}

		infoWindow.setContent([
			'<div style="padding:10px;min-width:200px;line-height:150%;">',
			'<h4 style="margin-top:5px;">검색 좌표</h4><br />',
			'<a href="/">예시</a>',
			htmlAddresses.join('<br />'),
			'</div>'
		].join('\n'));

		infoWindow.open(map, latlng);
	});
}

function searchAddressToCoordinate(address) {

	naver.maps.Service.geocode({
		query: address
	}, function(status, response) {
		if (status === naver.maps.Service.Status.ERROR) {
			return alert('Something Wrong!');
		}

		if (response.v2.meta.totalCount === 0) {
			return alert('입력한 지역이 없습니다. 다시 한번 확인해주세요.');
		}

		var htmlAddresses = [],
			item = response.v2.addresses[0],
			point = new naver.maps.Point(item.x, item.y);

		
		if (item.roadAddress) {
			htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
		}

		if (item.jibunAddress) {
			htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
		}

		if (item.englishAddress) {
			htmlAddresses.push('[영문명 주소] ' + item.englishAddress);
		}
		

		infoWindow.setContent([
			'<div style="padding:10px;min-width:200px;line-height:150%;">',
			'<h4 style="margin-top:5px;">검색 주소 : ' + address + '</h4><br />',
			htmlAddresses.join('<br />'),
			'</div>'
		].join('\n'));

		console.log()
		map.setCenter(point);
		infoWindow.open(map, point);
	});
}

var infowindow = new naver.maps.InfoWindow();

function onSuccessGeolocation(position) {
	var location = new naver.maps.LatLng(position.coords.latitude,
		position.coords.longitude);

	map.setCenter(location); // 얻은 좌표를 지도의 중심으로 설정합니다.
	map.setZoom(17); // 지도의 줌 레벨을 변경합니다.

	infowindow.setContent('<div style="padding:20px;">' + 'geolocation.getCurrentPosition() 위치' + '</div>');

	infowindow.open(map, location);
	console.log('Coordinates: ' + location.toString());
}

function onErrorGeolocation() {
	var center = map.getCenter();

	infowindow.setContent('<div style="padding:20px;">' +
		'<h5 style="margin-bottom:5px;color:#f00;">Geolocation failed!</h5>' + "latitude: " + center.lat() + "<br />longitude: " + center.lng() + '</div>');

	infowindow.open(map, center);
}

function initGeocoder() {

	/*
			'<h2 id="boardtitle">' + '제목 :' + board.title + '</h2>' +
			'<p id="boardcontent">' + '내용 :' + board.content + '</p>' +
			'<p id="boardaddress">' + '거래 주소 :' + board.address + '</p>' +
			'<p id createdDay>' + '작성일 :' + board.created_day + '</p>' +
	*/


	if ($('#getAddress').val() != null) {
		let address = $('#getAddress').val();
		map.addListener('click', function(e) {
			searchCoordinateToAddress(e.coord);
		});

		$('#address').on('keydown', function(e) {
			var keyCode = e.which;

			if (keyCode === 13) { // Enter Key
				searchAddressToCoordinate($('#address').val());
			}
		});

		$('#submit').on('click', function(e) {
			e.preventDefault();

			searchAddressToCoordinate($('#address').val());
		});
		

		searchAddressToCoordinate(address);
	} else {
		if (navigator.geolocation) {
			/**
			 * navigator.geolocation 은 Chrome 50 버젼 이후로 HTTP 환경에서 사용이 Deprecate 되어 HTTPS 환경에서만 사용 가능 합니다.
			 * http://localhost 에서는 사용이 가능하며, 테스트 목적으로, Chrome 의 바로가기를 만들어서 아래와 같이 설정하면 접속은 가능합니다.
			 * chrome.exe --unsafely-treat-insecure-origin-as-secure="http://example.com"
			 */
			navigator.geolocation.getCurrentPosition(onSuccessGeolocation, onErrorGeolocation);
		} else {
			var center = map.getCenter();
			infowindow.setContent('<div style="padding:20px;"><h5 style="margin-bottom:5px;color:#f00;">Geolocation not supported</h5></div>');
			infowindow.open(map, center);
		}
	}

}

/*
var bounds = map.getBounds(),
	southWest = bounds.getSW(),
	northEast = bounds.getNE(),
	lngSpan = northEast.lng() - southWest.lng(),
	latSpan = northEast.lat() - southWest.lat();

var markers = [],
	infoWindows = [];

for (var key in MARKER_SPRITE_POSITION) {

	var position = new naver.maps.LatLng(
		southWest.lat() + latSpan * Math.random(),
		southWest.lng() + lngSpan * Math.random());

	var marker = new naver.maps.Marker({
		map: map,
		position: position,
		title: key,
		icon: {
			 url: HOME_PATH + '/img/about.jpg',
			//url: image,
			size: new naver.maps.Size(24, 37),
			anchor: new naver.maps.Point(12, 37),
			origin: new naver.maps.Point(MARKER_SPRITE_POSITION[key][0], MARKER_SPRITE_POSITION[key][1])
		},
		zIndex: 100
	});

	var infoWindow = new naver.maps.InfoWindow({
		content: '<div style="width:150px;text-align:center;padding:10px;">The Letter is <b>"' + key.substr(0, 1) + '"</b>.</div>'
	});

	markers.push(marker);
	infoWindows.push(infoWindow);
};

naver.maps.Event.addListener(map, 'idle', function() {
	updateMarkers(map, markers);
});

function updateMarkers(map, markers) {

	var mapBounds = map.getBounds();
	var marker, position;

	for (var i = 0; i < markers.length; i++) {

		marker = markers[i]
		position = marker.getPosition();

		if (mapBounds.hasLatLng(position)) {
			showMarker(map, marker);
		} else {
			hideMarker(map, marker);
		}
	}
}

function showMarker(map, marker) {

	if (marker.setMap()) return;
	marker.setMap(map);
}

function hideMarker(map, marker) {

	if (!marker.setMap()) return;
	marker.setMap(null);


// 해당 마커의 인덱스를 seq라는 클로저 변수로 저장하는 이벤트 핸들러를 반환합니다.
function getClickHandler(seq) {
	return function(e) {
		var marker = markers[seq],
			infoWindow = infoWindows[seq];

		if (infoWindow.getMap()) {
			infoWindow.close();
		} else {
			infoWindow.open(map, marker);
		}
	}
}

for (var i = 0, ii = markers.length; i < ii; i++) {
	naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i));
}
}*/

function makeAddress(item) {
	if (!item) {
		return;
	}

	var name = item.name,
		region = item.region,
		land = item.land,
		isRoadAddress = name === 'roadaddr';

	var sido = '', sigugun = '', dongmyun = '', ri = '', rest = '';

	if (hasArea(region.area1)) {
		sido = region.area1.name;
	}

	if (hasArea(region.area2)) {
		sigugun = region.area2.name;
	}

	if (hasArea(region.area3)) {
		dongmyun = region.area3.name;
	}

	if (hasArea(region.area4)) {
		ri = region.area4.name;
	}

	if (land) {
		if (hasData(land.number1)) {
			if (hasData(land.type) && land.type === '2') {
				rest += '산';
			}

			rest += land.number1;

			if (hasData(land.number2)) {
				rest += ('-' + land.number2);
			}
		}

		if (isRoadAddress === true) {
			if (checkLastString(dongmyun, '면')) {
				ri = land.name;
			} else {
				dongmyun = land.name;
				ri = '';
			}

			if (hasAddition(land.addition0)) {
				rest += ' ' + land.addition0.value;
			}
		}
	}

	return [sido, sigugun, dongmyun, ri, rest].join(' ');
}

function hasArea(area) {
	return !!(area && area.name && area.name !== '');
}

function hasData(data) {
	return !!(data && data !== '');
}

function checkLastString(word, lastString) {
	return new RegExp(lastString + '$').test(word);
}

function hasAddition(addition) {
	return !!(addition && addition.value);
}

naver.maps.onJSContentLoaded = initGeocoder;