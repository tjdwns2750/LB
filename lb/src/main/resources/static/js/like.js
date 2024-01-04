var fileName = [
    /*[[@{/image/like.png}]]*/,
    /*[[@{/image/unlike.png}]]*/,
];

$(function () {
    var heartIcon = $('#likeBt');
    var bno = heartIcon.attr('bno');

    // 페이지 로딩 시에 좋아요 여부 확인
    $.ajax({
        url: 'likeCnt',
        type: 'post',
        data: { bno: bno },
        success: function (check) {
            console.log(check);
            updateLikeImage(check);
        },
        error: function () {
            alert('Error checking like status.');
        }
    });

    $('#likeBt').click(function () {
        // Move the check variable inside the click event handler
        var check = /*[[${check}]]*/ '';

        $.ajax({
            url: 'recommend',
            type: 'post',
            data: { bno: bno },
            success: function (n) {
                $('#cnt').html(n);
            },
            error: function () {
                alert('Error recommending.');
            }
        });

        // 이미지 변경 및 check 값 업데이트
        console.log("하기전", check);
        if (check == 0) {
            check = 1;
        } else {
            check = 0;
        }
        console.log(check);
        updateLikeImage(check);
    });
});

function updateLikeImage(check) {
    var heartIcon = $('#likeBt');
    if (parseInt(check) !== 1) {
        heartIcon.attr('src', fileName[1]); // Change the path accordingly
    } else {
        heartIcon.attr('src', fileName[0]); // Change the path accordingly
    }
}
