document.addEventListener("DOMContentLoaded", function() {

	setTimeout(function() {
		var hiddenText = document.getElementById("hiddenText");
		hiddenText.style.display = "block";
	}, 6000);
	// 모든 package-item 요소들을 가져옴
	const packageItems = document.querySelectorAll('.package-item');

	// fallingLeaves 요소를 가져오기
	var fallingLeaves = document.querySelector(".falling-leaves");
	var heroHeader = document.querySelector(".hero-header");



	// fallingLeaves에 animationend 이벤트 리스너 추가
	fallingLeaves.addEventListener("animationend", function() {
		// 떨어지는 낙엽 애니메이션이 끝나면 배경 이미지 변경
		heroHeader.style.transition = "background-image 2s ease-out";
		heroHeader.style.backgroundImage = "url('./img/homeImg3.jpg')";
	});

	// 각 package-item을 1초 간격으로 표시
	packageItems.forEach((item, index) => {
		setTimeout(() => {
			item.style.display = 'block';
			item.classList.add('animated', 'fadeInUp'); // fadeInUp은 애니메이션 클래스를 대체해야 함
		}, index * 300);
	});

})