$(function() {
	if (/android/i.test(navigator.userAgent)) {
		$('.phone-message').show()
	}
	$('.boxclose').click(function() {
		$('.phone-message').hide()
	});
});