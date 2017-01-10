var MutationObserver = this.MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;

mutationObserverSupport = !!MutationObserver;

var options = {
	'childList' : true,
};

var dom = $(".fxs-journey-layout")[0];

var callback = function(records, instance) {

	records.map(function(record) {
		if (record.type === "childList") {
			$.gs.blades.data.removeAll();
			$(".fxs-journey-layout>section.fxs-blade-placeholder").each(function() {
				var title = $(".fxs-blade-title-titleText", this).get(0);
				var nodes = title.childNodes;
				var names = [];
				for (var nIdx = 0; nIdx < nodes.length; nIdx++) {
					if (nodes[nIdx].nodeType == 1) {
						names.push(nodes[nIdx].innerText);
					}
				}
				$.gs.blades.data.push({
					name : names.join(" —— "),
					blade : this
				});
			});
		//
		//			for (var idx = 0; idx < record.removedNodes.length; idx++) {
		//				var title = $(".fxs-blade-title-titleText", record.removedNodes[idx]).get(0);
		//				var nodes = title.childNodes;
		//				var names = [];
		//				for (var nIdx = 0; nIdx < nodes.length; nIdx++) {
		//					if (nodes[nIdx].nodeType == 1) {
		//						names.push(nodes[nIdx].innerText);
		//					}
		//				}
		//				var _name = names.join(" —— ");
		//				var dataLen = $.gs.blades.data().length;
		//				for (var i = dataLen - 1; i >= 0; i--) {
		//					var d = $.gs.blades.data()[i];
		//					if (d.name === _name) {
		//						$.gs.blades.data.remove($.gs.blades.data()[i]);
		//					}
		//				}
		//			}
		//			for (var idx = 0; idx < record.addedNodes.length; idx++) {
		//				var add = record.addedNodes[idx];
		//				var title = $(".fxs-blade-title-titleText", add).get(0);
		//				var nodes = title.childNodes;
		//				var names = [];
		//				for (var nIdx = 0; nIdx < nodes.length; nIdx++) {
		//					if (nodes[nIdx].nodeType == 1) {
		//						names.push(nodes[nIdx].innerText);
		//					}
		//				}
		//				$.gs.blades.data.push({
		//					name : names.join(" —— "),
		//					blade : add
		//				});
		//			}
		}
	});
};
var observe = function() {
	if (dom.nodeType !== 1)
		return false;
	var muta = new MutationObserver(callback.bind(this));
	muta.observe(dom, options);
};

observe();