
/* ---------------------------------------------------
	QUANTITY MINUS AND PLUS
-------------------------------------------------- */
$(function ($) {
    "use strict";
	//Quantity plus minus 
    $.initQuantity = function ($control) {
        $control.each(function () {
            var $this = $(this),
                data = $this.data("inited-control"),
                $plus = $(".input-group-addon:last", $this),
                $minus = $(".input-group-addon:first", $this),
                $value = $(".form-control", $this);
            if (!data) {
                $control.attr("unselectable", "on").css({
                    "-moz-user-select": "none",
                    "-o-user-select": "none",
                    "-khtml-user-select": "none",
                    "-webkit-user-select": "none",
                    "-ms-user-select": "none",
                    "user-select": "none"
                }).bind("selectstart", function () {
                    return false
                });
                $plus.click(function () {
                    var val =
                        parseInt($value.val()) + 1;
                    $value.val(val);
                    return false
                });
                $minus.click(function () {
                    var val = parseInt($value.val()) - 1;
                    $value.val(val > 0 ? val : 1);
                    return false
                });
                $value.blur(function () {
                    var val = parseInt($value.val());
                    $value.val(val > 0 ? val : 1)
                })
            }
        })
    };
    $.initQuantity($(".quantity-control"));
    $.initSelect = function ($select) {
        $select.each(function () {
            var $this = $(this),
                data = $this.data("inited-select"),
                $value = $(".value", $this),
                $hidden = $(".input-hidden", $this),
                $items = $(".dropdown-menu li > a", $this);
            if (!data) {
                $items.click(function (e) {
                    if ($(this).closest(".sort-isotope").length >
                        0) e.preventDefault();
                    var data = $(this).attr("data-value"),
                        dataHTML = $(this).html();
                    $this.trigger("change", {
                        value: data,
                        html: dataHTML
                    });
                    $value.html(dataHTML);
                    if ($hidden.length) $hidden.val(data)
                });
                $this.data("inited-select", true)
            }
        })
    };
    $.initSelect($(".btn-select"));
	
	if(!window.startRangeValues) return;
	var startValues = window.startRangeValues,
		min = startValues[0].toFixed(2),
		max = startValues[1].toFixed(2);

	$('.filter_reset').on('click', function(){

		var form = $(this).closest('form'),
			range = form.find('.range');

			console.log(startValues);

		// form.find('#slider').slider('option','values', startValues);

		form.find('#slider').slider('values', 0, min);
		form.find('#slider').slider('values', 1, max);

		form.find('.options_list').children().eq(0).children().trigger('click');

		range.children('.min_value').val(min).next().val(max);

		range.children('.min_val').text('$' + min).next().text('$' + max);

	});
	
	
});

