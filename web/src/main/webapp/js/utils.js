function Utils() {
}

Utils.wrapCallback = function(callback, wrappedCallback) {
	var callbacks = {
		callback : callback,
		wrappedCallback : wrappedCallback
	};
	return $.proxy(function() {
		this.callback(arguments);
		if (this.wrappedCallback) {
			this.wrappedCallback(arguments);
		}
	}, callbacks);
};

Utils.isUndefinedOrEmpty = function(array) {
	return angular.isUndefined(array) || array == null || array.length == 0;
};

Utils.isNotUndefinedOrEmpty = function(array) {
	return !Utils.isUndefinedOrEmpty(array);
};

Utils.parseDate = function(value) {
	var result = null;
	if (value) {
		var dateParts = value.split("/");
		result = new Date(dateParts[2], (parseFloat(dateParts[1]) - 1), dateParts[0]);
	}
	return result;
};

Utils.formatDate = function(value) {
	var result = null;
	if (value) {
		result = value.getDate() + "/" + (value.getMonth() + 1) + "/" + value.getFullYear();
	}
	return result;
};

Utils.removeItemById = function(item, array) {
	angular.forEach(array, function(object, index) {
		if (object.id === item.id) {
			array.splice(index, 1);
		}
	});
};

Utils.replaceItemById = function(item, array) {
	angular.forEach(array, function(object, index) {
		if (object.id === item.id) {
			array[index] = item;
		}
	});
};

Utils.addItem = function(item, array) {
	array.push(item);
};