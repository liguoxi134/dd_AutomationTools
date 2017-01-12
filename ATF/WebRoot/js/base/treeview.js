
(function($) {
	$.fn.treeview = function(json) {
		var element = this;
		_createRoot = function() {
			return $("<div class=\"treeview\" data-role=\"treeview\"><ul/></div>");
		};
		group = 0;
		_createLeaf = function(name, value) {
			var _val = "",
				nodeText = "";

			if (value === undefined) {
				nodeText = name;
				_val = name;
			} else if (value === null) {
				_val = "null";
				nodeText = name + " : " + _val;
			} else {
				_val = value;
				var pref = "",
					suff = ""
				if ((typeof value) == 'string') {
					pref = "\"";
					suff = "\"";
				}
				nodeText = name + " : " + pref + _val + suff;
			}

			var span = $("<span/>").addClass("leaf").text(nodeText);
			span.attr("title", nodeText);
			span.attr("data-value", _val);
			span.attr("data-node", name);
			span.attr("data-group", group);
			return $("<li/>").append(span);
		}
		_createNode = function(name) {
			return $("<li class=\"node\"><span class=\"leaf\">" + name + "</span><span class=\"node-toggle\"></span><ul/></li>");
		}

		_buildTree = function(data, root) {
			if ((typeof data) == 'object' && data != null) {
				if (data instanceof Array) {
					if (data.length > 0) {
						for (var i = 0; i < data.length; i++) {
							group++;
							var value = data[i];
							var node = _createNode("[" + i + "]");
							root.append(node);
							_buildTree(value, node.find("ul"));
						}
					} else {
						root.append(_createLeaf("[EMPTY ARRAY]", undefined));
					}
					return;
				} else {
					group++;
					for (var key in data) {
						var value = data[key];
						if ( ((typeof value) == 'object' && value != null) ) {
							var node = _createNode(key);
							root.append(node);
							_buildTree(value, node.find("ul"));
						} else {
							root.append(_createLeaf(key, value));
						}
					}
				}
			} else {
				root.append(_createLeaf(data, undefined));
			}
		}
		var root = _createRoot();
		$(element).children().remove();
		$(element).append(root);
		_buildTree(json, root.find("ul"));

		element.on("click", ".leaf", function() {
			$('li.active', element).removeClass('active');
			$(this).parent("li").addClass('active');
		});
		element.on("click", ".node-toggle", function(e) {
			var leaf = $(this).siblings('.leaf'),
				parent = $(this).parent('li');
			parent.toggleClass('collapsed');
			e.stopPropagation();
			e.preventDefault();
		});
		element.on("dblclick", ".leaf", function() {
			var leaf = $(this),
				parent = leaf.parent('li');
			parent.toggleClass('collapsed');
			e.stopPropagation();
			e.preventDefault();
		});
	}
})(jQuery);