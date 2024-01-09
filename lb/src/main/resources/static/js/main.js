(function($) {
	"use strict";

	document.addEventListener("DOMContentLoaded", function() {
		
		/*
		setTimeout(function() {
			var hiddenText = document.getElementById("hiddenText");
			hiddenText.style.display = "block";
		}, 6000);
		*/
		// 모든 package-item 요소들을 가져옴
		const packageItems = document.querySelectorAll('.package-item');

		// fallingLeaves 요소를 가져오기
		var fallingLeaves = document.querySelector(".falling-leaves");
		var heroHeader = document.querySelector(".hero-header");


		/*
		// fallingLeaves에 animationend 이벤트 리스너 추가
		fallingLeaves.addEventListener("animationend", function() {
			// 떨어지는 낙엽 애니메이션이 끝나면 배경 이미지 변경
			heroHeader.style.transition = "background-image 2s ease-out";
			heroHeader.style.backgroundImage = "url('./img/homeImg3.jpg')";
		});
		*/

		
		// 각 package-item을 1초 간격으로 표시
		packageItems.forEach((item, index) => {
			setTimeout(() => {
				item.style.display = 'block';
				item.classList.add('animated', 'fadeInUp'); // fadeInUp은 애니메이션 클래스를 대체해야 함
			}, index * 300);
		});
		
	});

	// Spinner
	var spinner = function() {
		setTimeout(function() {
			if ($('#spinner').length > 0) {
				$('#spinner').removeClass('show');
			}
		}, 1);
	};
	spinner();


	// Initiate the wowjs
	new WOW().init();


	// Sticky Navbar
	/*
	$(window).scroll(function () {
		if ($(this).scrollTop() > 0) {
			$('.navbar').addClass('sticky-top shadow-sm');
		} else {
			$('.navbar').removeClass('sticky-top shadow-sm');
		}
	});
	*/

	// Dropdown on mouse hover
	const $dropdown = $(".dropdown");
	const $dropdownToggle = $(".dropdown-toggle");
	const $dropdownMenu = $(".dropdown-menu");
	const showClass = "show";

	$(window).on("load resize", function() {
		if (this.matchMedia("(min-width: 992px)").matches) {
			$dropdown.hover(
				function() {
					const $this = $(this);
					$this.addClass(showClass);
					$this.find($dropdownToggle).attr("aria-expanded", "true");
					$this.find($dropdownMenu).addClass(showClass);
				},
				function() {
					const $this = $(this);
					$this.removeClass(showClass);
					$this.find($dropdownToggle).attr("aria-expanded", "false");
					$this.find($dropdownMenu).removeClass(showClass);
				}
			);
		} else {
			$dropdown.off("mouseenter mouseleave");
		}
	});

	// Back to top button
	$(window).scroll(function() {
		if ($(this).scrollTop() > 800) {
			$('.back-to-top').fadeIn('fast');
		} else {
			$('.back-to-top').fadeOut('fast');
		}
	});

	// 맨 위로 올리는 버튼 속도 조절 함수
	$('.back-to-top').click(function() {
		$('html, body').animate({ scrollTop: 0 }, 100, 'easeInOutExpo');
		return false;
	});

	// card 첫번쨰
	$('#cardFirst').click(function() {
		location.href = "./findBoard";
	});

	// card 두번쨰
	$('#cardTwo').click(function() {
		location.href = "./board/boardList";
	});

	// card 세번째
	$('#cardThird').click(function() {
		location.href = "./bookBoard/sellBoard";
	});

	// card 네번째
	$('#cardFour').click(function() {
		location.href = "./findBoard";
	});


	// Testimonials carousel
	$(".testimonial-carousel").owlCarousel({
		autoplay: true,
		smartSpeed: 1000,
		center: true,
		margin: 24,
		dots: true,
		loop: true,
		nav: false,
		responsive: {
			0: {
				items: 1
			},
			768: {
				items: 2
			},
			992: {
				items: 3
			}
		}
	});

})(jQuery);


document.addEventListener('DOMContentLoaded', function() {
	const notificationIcon = document.getElementById('notificationIcon');
	const notificationPopup = document.getElementById('notificationPopup');
	const notification1 = document.getElementById('notification1');
	const notification2 = document.getElementById('notification2');

	if (notificationIcon && notificationPopup && notification1 && notification2) {
		notificationIcon.addEventListener('click', function() {
			notificationPopup.style.display = notificationPopup.style.display === 'none' ? 'block' : 'none';

			// 클릭 시 p 버튼 보이게 처리
			notification1.style.display = 'block';
			notification2.style.display = 'block';
		});
	}
});
