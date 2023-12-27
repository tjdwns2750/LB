var roomList = /*[[${roomList}]]*/[];

console.log("roomList :", roomList);

var str = '';
if (roomList) {
	roomList.forEach(function(room) {
		str += '<div>';
		str += '<form action="../chat/BoardchatRoom" method="post">';
		str += '<input type="hidden" name="bbno" value=' + room.bbno + '>';
		str += '<input type="hidden" name="roomId" value=' + room.roomId + '>';
		str += '<input type="submit" class="list-group-item list-group-item-action bg-light" value="' + room.roomId + '님과 채팅하기"></input>';
		str += '</form>';
		str += '</div>';
	});
}



$("#menu-toggle").click(function(e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");
});
$("#list").append(str);