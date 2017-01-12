

jQuery.showBlade = function(options) {
	_options = {
		bladeSize : ko.observable('small'),
		bladeStyle : ko.observable('maximized'),
		title : ko.observable('标题'),
		bladeCommands : ko.observableArray(),
		actionCommands : ko.observableArray(),
		url : ko.observable(),
		subtitle:ko.observable(""),
		state : ko.observable('max'),
	};

	_initOptions = function() {
		if (options.bladeSize)
			_options.bladeSize(options.bladeSize);

		if (options.bladeStyle)
			_options.bladeStyle(options.bladeStyle);

		if (options.title)
			_options.title(options.title);
		
		if (options.subtitle)
			_options.subtitle(options.subtitle);
		
		if (options.bladeCommands)
			_options.bladeCommands(options.bladeCommands);
		
		if (options.actionCommands) {
			var length = options.actionCommands.length;
			for ( var i = 0; i < length; i++) {
				_options.actionCommands.push(options.actionCommands[i]);
			}
		}

		if (options.url)
			_options.url(options.url);

		if (options.state)
			_options.state(options.state);
	};

	_initOptions();

	var blade = new Blade(_options);
	$('.fxs-journey-layout').append(blade);
	return blade;
};

jQuery.ensureElementIntoView = function(blade) {
	var content = $('.fxs-portal-content');
	var left = 0;
	blade.prevAll().each(function(idx, ele) {
		left = left + $(this).width();
	});
	content.animate({
		scrollLeft : left
	});
};

jQuery.closeAllBlades = function() {
	$('.fxs-journey-layout').children().remove();
};
//jQuery==$
(function($) {
	$.fn.setBladeHtml = function(html) {
		$('div.fxs-blade-content.fxs-pannable', this).html(html);
	};
	/*$.fn.restoreBlade = function() {
		var dc = ko.dataFor($('.fxs-commandBar-itemList', this)[0]);
		dc.state('restore');
	};
	$.fn.maxBlade = function() {
		var dc = ko.dataFor($('.fxs-commandBar-itemList', this)[0]);
		dc.state('max');
	};*/
	$.fn.ensureElementIntoView = function() {
		$.ensureElementIntoView(this);
	};
	$.fn.closeNextAllBlades = function() {
		this.nextAll('.fxs-blade').remove();
	};
	$.fn.setBladeCommand = function(commandData) {
		var dc = ko.dataFor($('.fxs-commandBar-itemList', this)[0]);
		dc.actionCommands(commandData);
	};
	$.fn.refresh = function(func) {
		$(".fxs-blade-refresh", this).show();
		$(".fxs-blade-refresh", this).off().on('click', func);
	};
	$.fn.refreshBlade = function() {
		$(".fxs-blade-refresh", this).click();
	};
	$.fn.findBlade = function() {
		return this.parents('section.fxs-blade');
	};
})(jQuery);