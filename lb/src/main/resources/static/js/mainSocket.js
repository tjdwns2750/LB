var username = $('#memberId').val();

$(document).ready(function() {
	function errorAlarm() {
		stomp.send('/pub/alarm/updateCheck', {}, String(username));
	}

	// 클릭 이벤트 리스너 등록
	document.getElementById('clearAlarmButton').addEventListener('click', errorAlarm);
	var alarmDropdown = $('#alarm');

	alarmDropdown.hover(
		function() {
			// 마우스가 올라갔을 때
			alarmDropdown.css('max-height', '500px');
		},
		function() {
			// 마우스가 나갔을 때
			alarmDropdown.css('max-height', ''); // 기존 스타일로 복원
		}
	);
});


var sockJs = new SockJS("/lb/stomp/alarm");
var stomp = Stomp.over(sockJs);

str = ''

console.log("username :", username);

setInterval(function() {
	//stomp.send를 이용하여 메시지 전송
	stomp.send('/pub/alarm/showAlarm', {}, String(username));
	stomp.send('/pub/alarm/alarmNum', {}, String(username));
}, 500); // 1000 밀리초(1초) 간격으로 실행

var alarmList = []; // 알람 리스트를 저장할 배열

function calculateTimeDifference(createdDate) {
	var today = new Date();

	// 두 날짜 사이의 차이 (밀리초 단위)
	var timeDiffMillis = today - createdDate;

	// 차이를 초 단위로 변환
	var timeDiffSeconds = Math.floor(timeDiffMillis / 1000);

	// 시, 분, 초 계산
	var hours = Math.floor(timeDiffSeconds / 3600);
	var minutes = Math.floor((timeDiffSeconds % 3600) / 60);
	var seconds = timeDiffSeconds % 60;

	// 시간 문자열 조합
	var timeString = '';

	if (hours > 0) {
		timeString += hours + '시간 ';
	}

	if (minutes > 0) {
		timeString += minutes + '분 ';
	}

	timeString += seconds + '초';

	return timeString;
}

function displayAlarms() {
	// 기존 알람 목록을 비우고 새로운 알람으로 업데이트
	$('#alarm').empty();

	alarmList.forEach(function(alarm) {

		console.log("alarm : ", alarm.prefix);
		console.log(alarm.bbno)
		console.log(alarm.bno)

		if (alarm.prefix == 'chat') {

			// <p> 태그에서는 href 속성이 지원되지 않으므로 수정
			$('#alarm').append('<p class="dropdown-item">' + alarm.message +
				'<form action="/lb/chat/chatRoom" method="post">' +
				'<input type="hidden" name="bbno" value="' + alarm.bbno + '">' +
				'<input type="hidden" name="boardId" value="' + alarm.id + '">' +
				'<input class="btn btn-secondary" type="submit" value="채팅하기"></input>' +
				'</form>' +
				'</p>');

		} else if (alarm.prefix == 'review') {
			console.log("bno도착", alarm.bno)

			$('#alarm').append('<p class="dropdown-item">' + alarm.message +
				'<form action="/lb/board/read" method="get">' +
				'<input type="hidden" name="bno" value="' + alarm.bno + '">' +
				'<input class="btn btn-secondary" type="submit" value="글 보러가기"></input>' +
				'</form>' +
				'</p>');
			console.log('<a href="./board/read?bno="' + alarm.bno + '"class="btn btn-secondary" > 글보러가기' +
				'</a>');

		}

	});
}

stomp.connect({}, function() {
	console.log("STOMP Connection");


	stomp.subscribe("/sub/layout/main/" + username, function(chat, chatmessage) {
		var content = JSON.parse(chat.body);
		content.forEach(function(alarmItem) {
			if (alarmItem.prefix == 'chat') {
				var createdDate = new Date(alarmItem.created_day);
				var timeDiff = calculateTimeDifference(createdDate);
				alarmList.push({ message: timeDiff + ' 전에 새로운 채팅이 도착했습니다.', bbno: Number(alarmItem.bbno), id: alarmItem.member_id });
				console.log(alarmList);
			} else if (alarmItem.prefix == 'review') {
				var createdDate = new Date(alarmItem.created_day);
				var timeDiff = calculateTimeDifference(createdDate);
				alarmList.push({ message: timeDiff + ' 전에 새로운 리뷰가 도착했습니다.', id: alarmItem.member_id, prefix: alarmItem.prefix, bno: alarmItem.bno });
				console.log(alarmList);
				console.log("도착완료");
			}
		});
		displayAlarms();
		alarmList = [];
	});

	stomp.subscribe("/sub/layout/main/num" + username, function(chat, num) {
		var num = JSON.parse(chat.body);
		console.log("number : ", num);
		$('#alarmNum').text(num);
	});

});